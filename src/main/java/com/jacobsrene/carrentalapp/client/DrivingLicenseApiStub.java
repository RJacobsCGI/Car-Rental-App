package com.jacobsrene.carrentalapp.client;


import com.jacobsrene.carrentalpricing.model.RateRequest;
import com.jacobsrene.carrentalpricing.model.RateResponse;
import com.jacobsrene.drivinglicense.api.DefaultApi;
import com.jacobsrene.drivinglicense.model.LicenseRequest;
import com.jacobsrene.drivinglicense.model.LicenseResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Primary
public class DrivingLicenseApiStub extends DefaultApi {

    @Override
    public LicenseResponse licenseDetailsPost(LicenseRequest licenseRequest) {
        LicenseResponse response = new LicenseResponse();
        response.setOwnerName("John Doe");
        response.setExpiryDate(LocalDate.of(2030, 5, 17));
        return response;
    }
}
