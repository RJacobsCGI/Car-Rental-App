package com.jacobsrene.carrentalapp.client;

import com.jacobsrene.carrentalpricing.api.DefaultApi;
import com.jacobsrene.carrentalpricing.model.RateRequest;
import com.jacobsrene.carrentalpricing.model.RateResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PricingApiStub extends DefaultApi {

    @Override
    public RateResponse rentalRatePost(RateRequest rateRequest) {
        RateResponse response = new RateResponse();
        response.setCategory(rateRequest.getCategory().getValue());
        response.setRatePerDay(75F); // fixed price
        return response;
    }
}
