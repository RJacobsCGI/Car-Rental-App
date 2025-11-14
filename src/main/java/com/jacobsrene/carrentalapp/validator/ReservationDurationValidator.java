package com.jacobsrene.carrentalapp.validator;

import com.jacobsrene.carrentalapp.dto.BookingRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.temporal.ChronoUnit;

public class ReservationDurationValidator implements ConstraintValidator<ValidReservationDuration, BookingRequest> {
    @Override
    public boolean isValid(BookingRequest bookingRequest, ConstraintValidatorContext context) {
        long reservationDuration = ChronoUnit.DAYS.between(bookingRequest.reservationStart(), bookingRequest.reservationEnd());

        return reservationDuration <= 30;
    }
}
