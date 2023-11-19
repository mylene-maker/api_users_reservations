package com.humanbooster.authentification.services;

import com.humanbooster.authentification.repositories.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandService {
    @Autowired
    CommandRepository commandRepository;

}
