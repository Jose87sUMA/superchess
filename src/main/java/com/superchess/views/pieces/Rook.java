package com.superchess.views.pieces;

import com.superchess.views.board.Board;
import com.vaadin.flow.component.html.Image;

public class Rook extends Piece{

    public boolean firstMove;

    public Rook(Board board, Board.Tile tile, boolean isWhite){

        super(board);

        this.firstMove = true;

        this.tile = tile;

        this.isWhite = isWhite;
        this.name = "Rook";

        String colorString = (isWhite ? "White":"Black");
        this.sprite = new Image("themes/superchess/" + colorString + "Rook.png", colorString + "Rook");
        this.add(sprite);

        this.setVisible(true);

    }
    @Override
    public boolean validMove(Board.Tile tile) {

        System.out.println(this.tile.col + " " + this.tile.row);
        boolean valid = (Math.abs(this.tile.col-tile.col) == 0 || Math.abs(this.tile.row-tile.row) == 0) && !horizontalObstruction(tile);

        if (valid && firstMove)
            firstMove = false;

        return valid;

    }



}
