package com.re.backendmobile.controller;

import com.re.backendmobile.entity.ParkingTicket;
import com.re.backendmobile.entity.Vehicle;
import com.re.backendmobile.repository.ParkingTicketRepository;
import com.re.backendmobile.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/parking")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ParkingController {

    private final ParkingTicketRepository ticketRepository;
    private final VehicleRepository vehicleRepository;

    @GetMapping("/history")
    public List<ParkingTicket> history(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String keyword
    ) {
        List<ParkingTicket> all = ticketRepository.findAll();

        List<ParkingTicket> result = new ArrayList<>();

        for (ParkingTicket ticket : all) {

            Vehicle vehicle = vehicleRepository.findFirstByPlateNumber(ticket.getPlateNumber());

            String owner = vehicle == null ? "" : vehicle.getOwnerName();

            boolean isAdmin = role != null && role.equalsIgnoreCase("ADMIN");

            boolean ownerMatch = owner != null
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