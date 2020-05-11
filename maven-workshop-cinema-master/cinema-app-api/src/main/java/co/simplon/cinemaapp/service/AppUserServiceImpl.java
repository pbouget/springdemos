package co.simplon.cinemaapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import co.simplon.cinemaapp.exception.ExistingUsernameException;
import co.simplon.cinemaapp.exception.InvalidCredentialsException;
import co.simplon.cinemaapp.model.AppUser;
import co.simplon.cinemaapp.repository.AppUserRepository;
import co.simplon.cinemaapp.security.JwtTokenProvider;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AppUserServiceImpl(AppUserRepository appUserRepository, BCryptPasswordEncoder passwordEncoder,
                              JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String signin(String username, String password) throws InvalidCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, appUserRepository.findByUsername(username).get().getRoleList());
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public String signup(AppUser user) throws ExistingUsernameException {
        if (!appUserRepository.existsByUsername(user.getUsername())) {
            AppUser userToSave = new AppUser(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getRoleList());
            appUserRepository.save(userToSave);
            return jwtTokenProvider.createToken(user.getUsername(), user.getRoleList());
        } else {
            throw new ExistingUsernameException();
        }
    }

    @Override
    public List<AppUser> findAllUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public Optional<AppUser> findUserByUserName(String username) {
        return appUserRepository.findByUsername(username);
    }
}
