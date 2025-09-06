package com.modhtom.linkguardbackend.service;

import com.modhtom.linkguardbackend.DTO.UrlRequestDTO;
import com.modhtom.linkguardbackend.DTO.UrlRespondDTO;
import com.modhtom.linkguardbackend.model.UrlExpansion;
import com.modhtom.linkguardbackend.model.User;
import com.modhtom.linkguardbackend.repository.UrlRepository;
import com.modhtom.linkguardbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UrlServiceIntegrationTest {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }


    @Test
    @WithMockUser(username = "testuser")
    void expandUrl_SavesToDatabaseAndIncrementsAnalytics() throws Exception {
        // --- Setup ---
        String shortUrl = "https://tinyurl.com/34775fb4";
        String finalUrl = "https://modhtom.com/";

        // Setup mock user for the Principal
        User mockUser = new User();
        mockUser.setUsername("testuser");
        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("testuser");
        when(userRepository.findUserByUsername("testuser")).thenReturn(mockUser);

        // Setup mock WebClient responses
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(finalUrl));
        ResponseEntity<Void> redirectResponse = new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        ResponseEntity<Void> finalResponse = new ResponseEntity<>(HttpStatus.OK);

        // Mock the two calls in the redirect loop
        when(responseSpec.toBodilessEntity()).thenReturn(Mono.just(redirectResponse), Mono.just(finalResponse));

        // --- Act ---
        UrlRequestDTO request = new UrlRequestDTO();
        request.setUrl(shortUrl);

        UrlRespondDTO response = urlService.expand(mockPrincipal, request);

        // --- Assert ---
        assertEquals(finalUrl, response.getFinalUrl());
        assertEquals("https://modhtom.com/", response.getDomain());

        // Verify it was saved to the database
        List<UrlExpansion> expansions = urlRepository.findAll();
        assertEquals(1, expansions.size());
        assertEquals(shortUrl, expansions.getFirst().getShortUrl());

        // Verify analytics were incremented in Redis
        Double score = redisTemplate.opsForZSet().score("trending:domains", "https://modhtom.com/");
        assertNotNull(score);
        assertEquals(1.0, score);
    }
}