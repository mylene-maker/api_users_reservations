package com.humanbooster.authentification.services;

import com.humanbooster.authentification.models.Command;
import com.humanbooster.authentification.models.Reservation;
import com.humanbooster.authentification.repositories.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandService {
    @Autowired
    CommandRepository commandRepository;

    public List<Command> findAll() { return this.commandRepository.findAll();}

    public void removeCommand(Command command) {
        this.commandRepository.delete(command);
    }

    public Command saveCommand(Command commandUpdate) {
        this.commandRepository.save(commandUpdate);
        return commandUpdate;
    }
}
