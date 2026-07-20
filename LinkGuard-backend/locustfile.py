import random
from locust import HttpUser, task, between

class LinkGuardUser(HttpUser):
    # Simulate realistic user think-time between actions
    wait_time = between(1, 3)

    def on_start(self):
        response = self.client.post("/api/auth/login", json={
            "username": "modhtom",
            "password_unHashed": "password123" 
        })
        
        if response.status_code == 200:
            try:
                # First, try to parse it as JSON
                token = response.json().get("token") 
            except ValueError:
                # If it crashes, it means the API returned the raw string directly
                token = response.text.strip()
                
            self.headers = {
                "Authorization": f"Bearer {token}",
                "Content-Type": "application/json"
            }
        else:
            print(f"Login failed: {response.status_code} | Response: {response.text}")
            self.headers = {"Content-Type": "application/json"}

    @task(3)
    def expand_url(self):
        """
        Hits the secure URL expansion endpoint. 
        Weight is 3, meaning it will execute 3x more often than the analytics task.
        """
        payload = {
            # Injecting random mock short URLs to bypass potential caching layers
            "shortUrl": f"http://bit.ly/mock-{random.randint(10000, 99999)}"
        }
        
        with self.client.post("/api/v1/expand", json=payload, headers=self.headers, catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Expansion failed with {response.status_code}")

    @task(1)
    def check_trending(self):
        """
        Hits the analytics endpoint powered by Redis Sorted Sets.
        """
        with self.client.get("/api/v1/analytics/trending", headers=self.headers, catch_response=True) as response:
             if response.status_code == 200:
                response.success()
             else:
                response.failure(f"Trending fetch failed with {response.status_code}")
