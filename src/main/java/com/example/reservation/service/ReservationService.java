package com.example.reservation.service;

import com.example.reservation.model.HistoryAction;
import com.example.reservation.model.Reservation;
import com.example.reservation.model.ReservationHistory;
import com.example.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final ReservationHistoryService reservationHistoryService;

    public ReservationService(ReservationRepository reservationRepository, ReservationHistoryService reservationHistoryService) {
        this.reservationRepository = reservationRepository;
        this.reservationHistoryService = reservationHistoryService;
    }

    private String toJson(Reservation reservation) {
        try {
            return new ObjectMapper().writeValueAsString(reservation);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing reservation", e);
        }
    }


//    NB: I may need a DTO conversion

    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Reservation id " + id + " not found.")
        );
    }

    public List<Reservation> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        if(reservations.isEmpty()) {
            throw new NoSuchElementException("No reservations found.");
        } else {
            return reservations;
        }
    }

    public Reservation create(Reservation reservation) {

        if(reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null.");
        }

        Reservation saved = reservationRepository.save(reservation);

//      create initial history record
        ReservationHistory history = new ReservationHistory();
        history.setAction(HistoryAction.CREATED);
        history.setPreviousState("{}");
        history.setNewState(toJson(saved));
        history.setReservation(saved);

        reservationHistoryService.createHistory(history);

        return saved;
    }

    public Reservation update(Long id, Reservation updatedReservation) {
        Reservation existingReservation = findById(id);

        if (!existingReservation.canBeModified()) {
            return existingReservation;
        }

        String previousState = toJson(existingReservation);

        if(updatedReservation.getTitle() != null) {
            existingReservation.setTitle(updatedReservation.getTitle());
        }
        if(updatedReservation.getStartDateTime() != null) {
            existingReservation.setStartDateTime(updatedReservation.getStartDateTime());
        }
        if(updatedReservation.getEndDateTime() != null) {
            existingReservation.setEndDateTime(updatedReservation.getEndDateTime());
        }
        if(updatedReservation.getStatus() != null) {
            existingReservation.setStatus(updatedReservation.getStatus());
        }

        Reservation saved = reservationRepository.save(existingReservation);

//      create history record for update
        ReservationHistory history = new ReservationHistory();
        history.setAction(HistoryAction.UPDATED);
        history.setPreviousState(previousState);
        history.setNewState(toJson(saved));
        history.setReservation(saved);

        return saved;


    }

    public boolean cancel(Long id) {
        Reservation existingReservation = findById(id);

        if(!existingReservation.canBeModified()) {
            return false;
        }

        String previousState = toJson(existingReservation);

        Reservation saved = reservationRepository.save(existingReservation);

        // create history record for cancellation
        ReservationHistory history = new ReservationHistory();
        history.setAction(HistoryAction.CANCELLED);
        history.setPreviousState(previousState);
        history.setNewState(toJson(saved));
        history.setReservation(saved);

        return true;
        }

    }

}
