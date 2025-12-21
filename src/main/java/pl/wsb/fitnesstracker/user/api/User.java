package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
/**
 * Entity representing a user of the FitnessTracker system.
 * Stores basic identification and contact information of the user.
 */
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

    /**
     * Database identifier of the user.
     * Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    /**
     * First name of the user.
     */
    @Column
    private String firstName;

    /**
     * Last name of the user.
     */
    @Column
    private String lastName;

    /**
     * Birth date of the user.
     * The value is required.
     */
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    /**
     * E-mail address of the user, unique in the system.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Creates a new user entity using the provided domain data.
     *
     * @param firstName first name of the user
     * @param lastName  last name of the user
     * @param birthdate birth date of the user
     * @param email     e-mail address of the user
     */
    public User(
            final String firstName,
            final String lastName,
            final LocalDate birthdate,
            final String email) {


        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

