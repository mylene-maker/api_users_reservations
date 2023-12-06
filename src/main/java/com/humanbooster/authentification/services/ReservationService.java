package com.humanbooster.authentification.services;

import com.humanbooster.authentification.models.Reservation;
import com.humanbooster.authentification.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    public List<Reservation> findAll() { return this.reservationRepository.findAll();}

    public void removeReservation(Reservation reservation) {
        this.reservationRepository.delete(reservation);
    }

    public Reservation saveReservation(Reservation reservationUpdate) {
        this.reservationRepository.save(reservationUpdate);
        return reservationUpdate;
    }
}
