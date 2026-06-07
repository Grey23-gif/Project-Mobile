package com.re.backendmobile.repository;

import com.re.backendmobile.entity.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Integer> {

    Optional<ParkingTicket> findFirstByPlateNumberAndStatusOrderByIdDesc(
            String plateNumber,
            String status
    );

    List<ParkingTicket> findByStatus(String status);
}