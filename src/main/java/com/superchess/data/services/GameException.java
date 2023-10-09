package com.superchess.data.services;

public class GameException extends Exception {

    private String message;
    public GameException(String message) {
        this.message = message;
    }

    public GameException() {
    }
}
