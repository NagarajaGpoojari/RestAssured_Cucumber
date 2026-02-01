package com.api.utils;

import java.util.function.Supplier;

import io.restassured.response.Response;

public class RetryHelper {

    public static Response retry(Supplier<Response> request, int maxAttempts, int waitMillis) {
        for (int i = 1; i <= maxAttempts; i++) {
            Response response = request.get();
            int status = response.getStatusCode();

            if (status == 200) return response;

            if (status == 401 ) {
                System.out.println("401 Unauthorized - refreshing token...");
                AuthManager.refreshToken(); 
                continue;
            }

            if (status == 409 || status == 429) {
                try {
                    System.out.println("Retry attempt " + i + " after status " + status);
                    Thread.sleep(waitMillis * i);
                } catch (InterruptedException ignored) {}
                continue;
            }

            return response;
        }
        return request.get(); 
    }
}
