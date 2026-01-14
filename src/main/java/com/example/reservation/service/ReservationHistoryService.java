package com.example.reservation.service;

import com.example.reservation.model.ReservationHistory;
import com.example.reservation.repository.ReservationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationHistoryService {

    @Autowired
    private final ReservationHistoryRepository reservationHistoryRepository;

    public ReservationHistoryService(ReservationHistoryRepository reservationHistoryRepository) {
        this.reservationHistoryRepository = reservationHistoryRepository;
    }

    public ReservationHistory createHistory(ReservationHistory history) {
        if(history == null) {
            throw new IllegalArgumentException("Reservation history cannot be null.");
        }

        reservationHistoryRepository.save(history);
        return history;
    }
}
