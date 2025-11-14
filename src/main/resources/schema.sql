CREATE TABLE booking
(
    booking_id UUID PRIMARY KEY,
    driving_license_number VARCHAR(50) NOT NULL,
    customer_name VARCHAR(50) NOT NULL,
    customer_age INT NOT NULL,
    reservation_start DATE NOT NULL,
    reservation_end DATE NOT NULL,
    car_segment VARCHAR(20) NOT NULL,
    rental_price DECIMAL(10,2) NOT NULL
);
