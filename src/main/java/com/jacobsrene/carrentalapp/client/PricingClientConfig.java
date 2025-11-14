package com.jacobsrene.carrentalapp.client;

import com.jacobsrene.carrentalpricing.api.DefaultApi;
import com.jacobsrene.carrentalpricing.invoker.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PricingClientConfig {
    @Bean
    public DefaultApi carRentalPricingApi() {
        ApiClient apiClient = new ApiClient();
        return new DefaultApi(apiClient);
    }
}
