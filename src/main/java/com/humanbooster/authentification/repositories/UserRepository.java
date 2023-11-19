package com.humanbooster.authentification.repositories;

import com.humanbooster.authentification.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    public List<User> findAll();

    public User  findByEmail(String email);

    @Query(value = "FROM User WHERE id != :id AND email = :email")
    public List<User> findByEmailWithoutId(String email, Long id);

    @Query("From User u JOIN u.roles as rolList WHERE u.email = :username AND rolList.roleName = 'ADMIN'")
    public User findByUsernameAndAdminRole(String username);
}
