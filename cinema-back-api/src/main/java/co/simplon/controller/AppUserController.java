package co.simplon.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.dto.AppUserDto;
import co.simplon.dto.JsonWebToken;
import co.simplon.exception.ExistingUsernameException;
import co.simplon.exception.InvalidCredentialsException;
import co.simplon.model.AppUser;
import co.simplon.service.AppUserService;

/**
 * Ce contr�leur �coute les appels HTTP sur l'url "/api/users"
 * Il g�re, l'enregistrement d'un utilisateur, son authentification
 * et fournit la liste des Utilisateur � l'administrateur (un compte avec le r�le Admin).
 */
@RestController
@RequestMapping("/api/user")
public class AppUserController {
	
	@Autowired
    private AppUserService appUserService;

    /**
     * Methode pour enregistrer un nouvel utilisateur dans la BD.
     * @param user utiliateur.
     * @return un JWT si la connection est OK sinon une mauvaise r�ponse
     */
    @PostMapping("/sign-up")
    public ResponseEntity<JsonWebToken> signUp(@RequestBody AppUser user) {
        try {
            return ResponseEntity.ok(new JsonWebToken(appUserService.signup(user)));
        } catch (ExistingUsernameException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Methode pour se connecter (le user existe d�j�).
     * @param user utilisateur qui doit se connecter.
     * @return un JWT si la connection est OK sinon une mauvaise r�ponse.
     */
    @PostMapping("/sign-in")
    public ResponseEntity<JsonWebToken> signIn(@RequestBody AppUser user) {
        try {
        	// ici on cr�� un JWT en passant l'identifiant et le mot de passe
        	// r�cup�r� de l'objet user pass� en param�tre.
            return ResponseEntity.ok(new JsonWebToken(appUserService.signin(user.getUsername(), user.getPassword())));
        } catch (InvalidCredentialsException ex) {
        	// on renvoie une r�ponse n�gative
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Methode pour retourner tous les utilisateurs de la BD.
     * Cette m�thode est accesible pour les utilisateurs ayant le r�le ROLE_ADMIN.
     * @return la liste de tous les utilisateurs enregistr�s en BD.
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<AppUserDto> getAllUsers() {
        return appUserService.findAllUsers().stream().map(appUser -> new AppUserDto(appUser.getUsername(), appUser.getRoleList())).collect(Collectors.toList());
    }

    /**
     * Methode pour r�cup�rer le user dans la bd � partir de son username.
     * Cette m�thode est uniquement accessible par un user Admin.
     * @param appUserName � chercher.
     * @return un User est trouv� , a not found response code otherwise.
     */
    @GetMapping("/{appUserName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppUserDto> getOneUser(@PathVariable String appUserName) {
        Optional<AppUser> appUser = appUserService.findUserByUserName(appUserName);
        if (appUser.isPresent()) {
            return ResponseEntity.ok(new AppUserDto(appUser.get().getUsername(), appUser.get().getRoleList()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
