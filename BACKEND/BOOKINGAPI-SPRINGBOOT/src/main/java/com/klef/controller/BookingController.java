package com.klef.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.klef.entity.Booking;
import com.klef.service.BookingService;

import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/bookingapi")
@CrossOrigin(origins = "http://localhost:5173","http://localhost:7001", allowCredentials = "true")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/")
    public String home() {
        return "Restaurant Booking API is running...";
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBooking(@RequestBody Booking booking) {
        if (booking.getBookingId() == null) {
            return ResponseEntity.badRequest().body("Booking ID must be provided!");
        }
        bookingService.addBooking(booking);
        return ResponseEntity.ok("Booking added successfully!");
    }


    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Booking with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBooking(@RequestBody Booking booking) {
        Booking existing = bookingService.getBookingById(booking.getBookingId());
        if (existing != null) {
            Booking updatedBooking = bookingService.updateBooking(booking);
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot update. Booking with ID " + booking.getBookingId() + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        Booking existing = bookingService.getBookingById(id);
        if (existing != null) {
            bookingService.deleteBookingById(id);
            return new ResponseEntity<>("Booking with ID " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete. Booking with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
