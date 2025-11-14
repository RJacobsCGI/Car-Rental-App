package com.jacobsrene.carrentalapp.client;

import java.math.BigDecimal;

public interface PricingClient {
    BigDecimal getRentalPrice(String carType);
}
