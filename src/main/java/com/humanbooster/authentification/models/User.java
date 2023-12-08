package com.humanbooster.authentification.models;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utilisateur")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotBlank(message = "Veuillez saisir un nom")
    private String lastname;
    @NotBlank(message = "Veuillez saisir un prénom")
    private String firstname;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Le format de votre email n'est pas valide")
    private String email;
    @NotBlank(message = "Selectionner votre pays de résidence")
    private String country;

    @NotNull(message= "Entrez le numero de rue")
    private int street_number;

    @NotBlank(message= "Entrez le nom de la rue")
    private String street_name;

    @NotBlank(message= "Entrez le code postal")
    private String zip_code;
    @NotBlank(message = "Indiquer un mot de passe")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$") //Minimum eight characters, at least one letter and one number
    private String password;
    @Transient
    @JsonIgnore
    private String confirmPassword;

    //    un utilisateur pourra avoir plusieurs roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "utilisateur_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @JsonIgnore
    @OneToMany(targetEntity = Command.class,
              fetch = FetchType.EAGER, mappedBy = "user")
    private List<Command> commands;

    public User(String lastname, String firstname, String email, int street_number, String street_name, String zip_code, String country, String password, String confirmPassword) {
        this.roles = new ArrayList<Role>();
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.street_number = street_number;
        this.street_name = street_name;
        this.zip_code = zip_code;
        this.country = country;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public User() {
        this.roles = new ArrayList<Role>();
    }

    public User(String username, String password) {
    }


    //Methodes pour creer et supprimer les roles
    public void addRole(Role role){
        this.roles.add(role);
    }
    public void removeRole(Role role){
        this.roles.remove(role);
    }

    //Methodes pour creer et supprimer les commandes
    public void addCommand(Command command){
        this.commands.add(command);
    }
    public void removeCommand(Command command){
        this.commands.remove(command);
    }

    //    getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public int getStreet_number() {
        return street_number;
    }

    public void setStreet_number(int street_number) {
        this.street_number = street_number;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
}