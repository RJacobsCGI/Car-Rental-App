package com.jacobsrene.carrentalapp.dto;

import com.jacobsrene.carrentalapp.validator.ValidReservationDuration;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@ValidReservationDuration
public record BookingRequest(
        @NotBlank(message = "Driving license number is required")
        String drivingLicenseNumber,
        @NotNull(message = "Customer age is required")
        @Min(value = 18, message = "Customer must be at least 18 years old")
        int customerAge,
        @NotNull(message = "Reservation start date is required")
        LocalDate reservationStart,
        @NotNull(message = "Reservation end date is required")
        LocalDate reservationEnd,
        @NotNull(message = "Car segment is required")
        CarSegment carSegment) {
}
