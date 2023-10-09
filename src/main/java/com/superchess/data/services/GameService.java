package com.superchess.data.services;

import com.superchess.data.entities.Game;
import com.superchess.data.entities.Player;
import com.superchess.data.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.superchess.data.entities.GameStatus.*;

@Service
public class GameService {

    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Game createNewGame(Player player1){

        Game game = new Game();
        game.setPlayer1(player1);
        game.setStatus(NEW);

        return game;

    }

    public Game connectToGame(Game game, Player player2) throws GameException {

        if(game.getStatus() == IN_PROGRESS){
            throw new GameException("Already full");
        }else if(game.getStatus() == WHITE_WON || game.getStatus() == BLACK_WON){
            throw new GameException("Already ended");
        }

        game.setPlayer2(player2);
        game.setStatus(IN_PROGRESS);

        return game;

    }

    public Game connectToRandomGame(Player player2) throws GameException {

        Game game = repository.findFirstByPlayer2IsNull();
        if(game == null){
            throw new GameException("No available games");
        }

        game.setPlayer2(player2);
        game.setStatus(IN_PROGRESS);

        return game;

    }

    public List<Game> findGamesAvailable(){return repository.findAllByPlayer2IsNull();}

}
