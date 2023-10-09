package com.superchess.views.lobby;

import com.superchess.data.entities.Game;
import com.superchess.data.entities.Player;
import com.superchess.data.services.GameException;
import com.superchess.data.services.GameService;
import com.superchess.data.services.PlayerService;
import com.superchess.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@PageTitle("lobby")
@Route(value = "lobby", layout = MainLayout.class)
public class LobbyView extends VerticalLayout {

    private final PlayerService playerService;
    private final GameService gameService;

    public LobbyView(PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;

        Player authPlayer = playerService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        setSpacing(false);

        Button createGame = new Button("Create New Game");

        Button randomGame = new Button("Join random");
        createGame.addClickListener(event -> {
            try {
                gameController.connectRandom(authPlayer);
            } catch (GameException e) {
                Notification error = new Notification(e.getMessage());
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                error.open();
            }
        });

        List<Game> availableGames = gameService.findGamesAvailable();
        VerticalLayout gameLayout = new VerticalLayout();

        for(Game game : availableGames){

            Button gameButton = new Button("Game by: " + game.getPlayer1().getUsername());
            createGame.addClickListener(event -> {
                try {
                    gameController.connect(new ConnectRequest(authPlayer, game));
                } catch (GameException e) {
                    Notification error = new Notification(e.getMessage());
                    error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    error.open();
                }
            });

            gameLayout.add(gameButton);

        }

    }

}
