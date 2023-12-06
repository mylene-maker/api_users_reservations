package com.humanbooster.authentification.repositories;

import com.humanbooster.authentification.models.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    @Override
    public List<Reservation> findAll();
}
