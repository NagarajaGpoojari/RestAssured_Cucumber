package com.api.utils;

import io.restassured.response.Response;
import java.util.function.Supplier;

public class RetryHelper {

    public static Response retry(Supplier<Response> request, int maxAttempts, int waitMillis) {
        for (int i = 1; i <= maxAttempts; i++) {
            Response response = request.get();
            int status = response.getStatusCode();

            // Retry only for 403 (Forbidden) or 429 (Too Many Requests)
            if (status != 403 && status != 429) return response;

            try {
                System.out.println("Retry attempt " + i + " after status " + status);
                Thread.sleep(waitMillis);
            } catch (InterruptedException ignored) {}
        }
        return request.get(); // final attempt
    }
}
