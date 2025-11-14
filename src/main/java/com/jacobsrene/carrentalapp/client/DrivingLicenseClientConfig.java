package com.jacobsrene.carrentalapp.client;

import com.jacobsrene.drivinglicense.api.DefaultApi;
import com.jacobsrene.drivinglicense.invoker.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DrivingLicenseClientConfig {
    @Bean
    public DefaultApi drivingLicenseApi() {
        ApiClient apiClient = new ApiClient();
        return new DefaultApi(apiClient);
    }
}
