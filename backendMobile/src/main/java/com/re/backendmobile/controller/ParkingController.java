package com.re.backendmobile.controller;

import com.re.backendmobile.dto.ApiResponse;
import com.re.backendmobile.entity.ParkingTicket;
import com.re.backendmobile.entity.Vehicle;
import com.re.backendmobile.repository.ParkingTicketRepository;
import com.re.backendmobile.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/parking")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ParkingController {

    private final ParkingTicketRepository ticketRepository;
    private final VehicleRepository vehicleRepository;

    @PostMapping("/checkin")
    public ApiResponse<ParkingTicket> checkIn(@RequestBody ParkingTicket request) {

        if (ticketRepository
                .findFirstByPlateNumberAndStatusOrderByIdDesc(
                        request.getPlateNumber(),
                        "PARKING"
                )
                .isPresent()) {

            return new ApiResponse<>(
                    false,
                    "Xe này đang gửi trong bãi",
                    null,
                    null
            );
        }

        ParkingTicket ticket = new ParkingTicket();
        ticket.setPlateNumber(request.getPlateNumber());
        ticket.setCheckInTime(LocalDateTime.now());
        ticket.setStatus("PARKING");
        ticket.setTotalFee(0.0);

        ParkingTicket saved = ticketRepository.save(ticket);

        return new ApiResponse<>(
                true,
                "Check-in thành công",
                null,
                saved
        );
    }

    @PostMapping("/checkout")
    public ApiResponse<ParkingTicket> checkOut(@RequestBody ParkingTicket request) {

        ParkingTicket found = ticketRepository
                .findFirstByPlateNumberAndStatusOrderByIdDesc(
                        request.getPlateNumber(),
                        "PARKING"
                )
                .orElseThrow(() -> new RuntimeException("Xe chưa check-in"));

        found.setCheckOutTime(LocalDateTime.now());
        found.setStatus("DONE");

        long hours = Duration
                .between(found.getCheckInTime(), found.getCheckOutTime())
                .toHours();

        if (hours == 0) {
            hours = 1;
        }

        found.setTotalFee(hours * 5000.0);

        ParkingTicket saved = ticketRepository.save(found);

        return new ApiResponse<>(
                true,
                "Check-out thành công",
                null,
                saved
        );
    }

    @GetMapping("/active")
    public List<ParkingTicket> activeTickets() {
        return ticketRepository.findByStatus("PARKING");
    }

    @GetMapping("/history")
    public List<ParkingTicket> history(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String keyword
    ) {
        List<ParkingTicket> all = ticketRepository.findAll();
        List<ParkingTicket> result = new ArrayList<>();

        for (ParkingTicket ticket : all) {

            Vehicle vehicle =
                    vehicleRepository.findFirstByPlateNumber(ticket.getPlateNumber());

            String owner = vehicle == null ? "" : vehicle.getOwnerName();

            boolean isAdmin =
                    role != null && role.equalsIgnoreCase("ADMIN");

            boolean ownerMatch =
                    owner != null
                            && email != null
                            && owner.toLowerCase().contains(email.toLowerCase());

            boolean keywordMatch = true;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String key = keyword.toLowerCase();

                keywordMatch =
                        ticket.getPlateNumber().toLowerCase().contains(key)
                                || owner.toLowerCase().contains(key);
            }

            if (isAdmin) {
                if (keywordMatch) {
                    result.add(ticket);
                }
            } else {
                if (ownerMatch) {
                    result.add(ticket);
                }
            }
        }

        return result;
    }
}