package com.jacobsrene.carrentalapp.validator;

import com.jacobsrene.carrentalapp.exception.BookingValidationException;
import com.jacobsrene.drivinglicense.model.LicenseResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DrivingLicenseValidator {
    public void validateDrivingLicense(LicenseResponse licenseResponse) {
        // Note: the APi spec mentions expiryDate but the assignment asks for a license that is at least one year old
        // I'm assuming validity of 10 years as is in the Netherlands, but this part does not make sense
        // If the condition is that it should not expire within a year the check would be easier:
        //   if (licenseResponse.getExpiryDate().isBefore(LocalDate.now().plusYears(1)))
        LocalDate issueDate = licenseResponse.getExpiryDate().minusYears(10);
        LocalDate issueThresholdDate = LocalDate.now().minusYears(1);

        if (issueDate.isAfter(issueThresholdDate)) {
            throw new BookingValidationException("Driving license is not older than one year.");
        }
    }
}
