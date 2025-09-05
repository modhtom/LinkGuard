package com.modhtom.linkguardbackend.service;

import com.modhtom.linkguardbackend.DTO.UrlRequestDTO;
import com.modhtom.linkguardbackend.DTO.UrlRespondDTO;
import com.modhtom.linkguardbackend.model.UrlExpansion;
import com.modhtom.linkguardbackend.repository.UrlRepository;
import com.modhtom.linkguardbackend.repository.UserRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Service
public class UrlService {
    private static final String URL_CACHE = "url_cache";
    private final WebClient webClient;
    private final UrlRepository urlRepo;
    private final UserRepository userRepo;

    public UrlService(WebClient.Builder webClientBuilder, UrlRepository urlRepo, UserRepository userRepo) {
        HttpClient httpClient = HttpClient.create().followRedirect(false);
        this.webClient = webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
        this.urlRepo = urlRepo;
        this.userRepo = userRepo;
    }

    @Cacheable(value = URL_CACHE, key = "#request.url")
    public UrlRespondDTO expand(Principal principal, UrlRequestDTO request) throws IOException, URISyntaxException {
        String url = request.getUrl();
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        String finalUrl = resolveFinalUrl(url);

        assert finalUrl != null;
        String domain = new URI(finalUrl).getHost();
        Document doc = Jsoup.connect(finalUrl).get();
        String title = doc.title();
        String description = doc.select("meta[name=description]").attr("content");
        if (description.isEmpty()) {
            description = "No description found.";
        }
        urlRepo.save(mapToUrlExpansion(principal, title, url, finalUrl, description, domain));

        UrlRespondDTO responseDto = mapToUrlResponseDTO(title, url, finalUrl, description, domain);

        return responseDto;
    }
    private String resolveFinalUrl(String url) {
        String currentUrl = url;
        int redirectLimit = 10;
        Set<String> visitedUrls = new HashSet<>();

        for (int i = 0; i < redirectLimit; i++) {
            if (!visitedUrls.add(currentUrl)) {
                throw new RuntimeException("Circular redirect detected for URL: " + url);
            }

            ResponseEntity<Void> response = this.webClient.get().uri(currentUrl)
                    .retrieve()
                    .toBodilessEntity()
                    .timeout(Duration.ofSeconds(10))
                    .onErrorResume(WebClientResponseException.class, ex -> {
                        return Mono.just(ResponseEntity.status(ex.getStatusCode())
                                .headers(ex.getHeaders())
                                .build());
                    })
                    .block();

            if (response == null) {
                throw new RuntimeException("Failed to get a response from URL: " + currentUrl);
            }

            if (response.getStatusCode().is3xxRedirection()) {
                String location = response.getHeaders().getFirst("Location");
                if (location == null) {
                    return currentUrl;
                }
                try {
                    URI currentUri = new URI(currentUrl);
                    URI locationUri = new URI(location);
                    URI resolvedUri = currentUri.resolve(locationUri);
                    currentUrl = resolvedUri.toString();
                } catch (URISyntaxException e) {
                    throw new RuntimeException("Invalid URL encountered during redirect: " + location, e);
                }
            } else if (response.getStatusCode().is2xxSuccessful()) {
                return currentUrl;
            } else {
                throw new RuntimeException("Failed to resolve URL. Status code: " + response.getStatusCode());
            }
        }
        throw new RuntimeException("Exceeded redirect limit of " + redirectLimit + " for URL: " + url);
    }
    private  UrlRespondDTO mapToUrlResponseDTO(String title, String shortUrl,
                                               String finalUrl, String description, String domain) {
        UrlRespondDTO urlRespondDTO = new UrlRespondDTO();
        urlRespondDTO.setTitle(title);
        urlRespondDTO.setShortUrl(shortUrl);
        urlRespondDTO.setFinalUrl(finalUrl);
        urlRespondDTO.setDescription(description);
        urlRespondDTO.setDomain(domain);
        return urlRespondDTO;
    }
    private UrlExpansion mapToUrlExpansion(Principal principal, String title, String shortUrl,
                                           String finalUrl, String description, String domain) {
        UrlExpansion urlExpansion = new UrlExpansion();
        urlExpansion.setTitle(title);
        urlExpansion.setShortUrl(shortUrl);
        urlExpansion.setFinalUrl(finalUrl);
        urlExpansion.setDescription(description);
        urlExpansion.setDomain(domain);
        urlExpansion.setResolvedBy(userRepo.findUserByUsername(principal.getName()));
        return urlExpansion;
    }
}
