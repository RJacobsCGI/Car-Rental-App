package com.jacobsrene.carrentalapp.repository;

import com.jacobsrene.carrentalapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
}
