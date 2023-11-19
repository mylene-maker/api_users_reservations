package com.humanbooster.authentification.services;


import com.humanbooster.authentification.models.Role;
import com.humanbooster.authentification.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role findByName(String roleName){

        return this.roleRepository.findByRoleName(roleName);
    }

    public void saveRole(Role roleName){

        this.roleRepository.save(roleName);
    }
}
