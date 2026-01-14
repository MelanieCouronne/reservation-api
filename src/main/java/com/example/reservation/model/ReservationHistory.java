package com.example.reservation.model;

import jakarta.persistence.*;
import org.apache.tomcat.util.json.JSONFilter;

import java.time.Instant;

@Entity
@Table(name = "reservationHistory")
public class ReservationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HistoryAction action;
    @Column(nullable = false)
    private JSONFilter previousState;
    @Column(nullable = false)
    private JSONFilter newState;
    @Column(nullable = false)
    private Instant createdAt;

    @ManyToOne(targetEntity = Reservation.class)
    private Reservation reservation;

    public ReservationHistory() {
        super();
    }

    public ReservationHistory(Long id, HistoryAction action, JSONFilter previousState, JSONFilter newState, Instant createdAt, Reservation reservation) {
        this.id = id;
        this.action = action;
        this.previousState = previousState;
        this.newState = newState;
        this.createdAt = createdAt;
        this.reservation = reservation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HistoryAction getAction() {
        return action;
    }

    public void setAction(HistoryAction action) {
        this.action = action;
    }

    public JSONFilter getPreviousState() {
        return previousState;
    }

    public void setPreviousState(JSONFilter previousState) {
        this.previousState = previousState;
    }

    public JSONFilter getNewState() {
        return newState;
    }

    public void setNewState(JSONFilter newState) {
        this.newState = newState;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
