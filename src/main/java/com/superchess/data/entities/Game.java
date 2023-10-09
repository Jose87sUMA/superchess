package com.superchess.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "game")
public class Game extends AbstractEntity{

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @ManyToOne
    @JoinColumn(name = "player_1_id")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player_2_id")
    private Player player2;

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
