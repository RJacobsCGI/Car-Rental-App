package com.jacobsrene.carrentalapp.client;

import com.jacobsrene.carrentalapp.exception.BookingValidationException;
import com.jacobsrene.carrentalapp.exception.ExternalServiceException;
import com.jacobsrene.carrentalpricing.model.RateRequest;
import com.jacobsrene.carrentalpricing.model.RateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PricingClientImpl implements PricingClient {
    private final com.jacobsrene.carrentalpricing.api.DefaultApi api;

    @Override
    public BigDecimal getRentalPrice(String carType) {
        RateRequest.CategoryEnum category;

        try {
            category = RateRequest.CategoryEnum.valueOf(carType);
        } catch (IllegalArgumentException e) {
            throw new BookingValidationException("Invalid car type: " + carType);
        }

        RateResponse rateResponse;

        try {
            rateResponse = api.rentalRatePost(new RateRequest().category(category));
        } catch (RuntimeException e) {
            throw new ExternalServiceException("Car Rental Pricing API is not reachable", e);
        }

        if (rateResponse == null
                || rateResponse.getCategory() == null
                || rateResponse.getRatePerDay() == null
                || rateResponse.getRatePerDay() < 0) {
            throw new ExternalServiceException("Invalid rate returned from the pricing API");
        }

        return BigDecimal.valueOf(rateResponse.getRatePerDay());
    }
}
