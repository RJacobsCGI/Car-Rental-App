package com.jacobsrene.carrentalapp.controller;

import com.jacobsrene.carrentalapp.dto.BookingDetailsResponse;
import com.jacobsrene.carrentalapp.dto.BookingRequest;
import com.jacobsrene.carrentalapp.entity.Booking;
import com.jacobsrene.carrentalapp.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<BookingDetailsResponse> retrieveBookingDetails(@PathVariable("id") UUID bookingId) {
        return bookingService.getBookingDetails(bookingId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/confirm")
    public ResponseEntity<UUID> confirmBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        Booking confirmedBooking = bookingService.confirmBooking(bookingRequest);

        URI bookingLocation = URI.create("/booking/" + confirmedBooking.getBookingId());

        return ResponseEntity.created(bookingLocation).body(confirmedBooking.getBookingId());
    }
}
