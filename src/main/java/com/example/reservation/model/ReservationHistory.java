package com.example.reservation.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "reservation_history")
public class ReservationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HistoryAction action;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String previousState;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String newState;
    @Column(nullable = false)
    private Instant createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;


    public ReservationHistory() {
        super();
    }

    public ReservationHistory(HistoryAction action, String previousState, String newState, Instant createdAt, Reservation reservation) {
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

    public String getPreviousState() {
        return previousState;
    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
