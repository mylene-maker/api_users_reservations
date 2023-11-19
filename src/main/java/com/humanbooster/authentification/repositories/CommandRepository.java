package com.humanbooster.authentification.repositories;


import com.humanbooster.authentification.models.Command;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends CrudRepository<Command, Long> {

//    public List<Command> findByUser(User user);
}
