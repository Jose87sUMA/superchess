package com.superchess.security;


import com.superchess.data.entities.Player;
import com.superchess.data.services.PlayerService;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticatedUser {

    private final PlayerService playerService;
    private final AuthenticationContext authenticationContext;

    public AuthenticatedUser(AuthenticationContext authenticationContext, PlayerService playerService) {
        this.playerService = playerService;
        this.authenticationContext = authenticationContext;
    }

    public Optional<Player> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> playerService.findByUsername(userDetails.getUsername()));
    }

    public void logout() {
        authenticationContext.logout();
    }

}
