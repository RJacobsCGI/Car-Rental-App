package com.jacobsrene.carrentalapp.client;

import com.jacobsrene.carrentalapp.exception.ExternalServiceException;
import com.jacobsrene.drivinglicense.model.LicenseRequest;
import com.jacobsrene.drivinglicense.model.LicenseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrivingLicenseClientImpl implements DrivingLicenseClient {
    private final com.jacobsrene.drivinglicense.api.DefaultApi defaultApi;

    @Override
    public LicenseResponse getDrivingLicenseInfo(String drivingLicenseNumber) {
        LicenseResponse licenseResponse;
        try {
            licenseResponse = defaultApi.licenseDetailsPost(new LicenseRequest().licenseNumber(drivingLicenseNumber));
        } catch (Exception e) {
            throw new ExternalServiceException("Driving License API is not reachable", e);
        }

        if (licenseResponse == null
                || licenseResponse.getExpiryDate() == null
                || licenseResponse.getOwnerName() == null) {
            throw new ExternalServiceException("Invalid license info returned from the driving license API");
        }

        return licenseResponse;
    }
}
