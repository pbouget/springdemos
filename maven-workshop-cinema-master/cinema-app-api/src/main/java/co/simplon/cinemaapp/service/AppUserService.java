package co.simplon.cinemaapp.service;

import co.simplon.cinemaapp.exception.ExistingUsernameException;
import co.simplon.cinemaapp.exception.InvalidCredentialsException;
import co.simplon.cinemaapp.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AppUserService {

    /**
     * Method that signs a user in the application.
     * @param username the user username.
     * @param password the user password.
     * @return the JWT if credentials are valid, throws InvalidCredentialsException otherwise.
     * @throws InvalidCredentialsException
     */
    String signin(String username, String password) throws InvalidCredentialsException;

    /**
     * Method that signs up a new user in the application.
     * @param user the new user to create.
     * @return the JWT if user username is not already existing.
     * @throws ExistingUsernameException
     */
    String signup(AppUser user) throws ExistingUsernameException;

    /**
     * Method that finds all users from the application database.
     * @return the list of all application users.
     */
    List<AppUser> findAllUsers();

    /**
     * Method that finds a user based on its username.
     * @param username the username to look for.
     * @return an Optional object containing user if found, empty otherwise.
     */
    Optional<AppUser> findUserByUserName(String username);
}
