package com.superchess.views.pieces;

import com.superchess.views.board.Board;
import com.vaadin.flow.component.html.Image;

public class Knight extends Piece{

    public Knight(Board board, Board.Tile tile, boolean isWhite){

        super(board);

        this.tile = tile;

        this.isWhite = isWhite;
        this.name = "Knight";

        String colorString = (isWhite ? "White":"Black");
        this.sprite = new Image("themes/superchess/" + colorString + "Knight.png", colorString + "Knight");
        this.add(sprite);

        this.setVisible(true);

    }

    @Override
    public boolean validMove(Board.Tile tile) {

        int X[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int Y[] = { 1, 2, 2, 1, -1, -2, -2, -1 };

        for (int i = 0; i < 8; i++) {
            if((this.tile.col + X[i]) == tile.col && (this.tile.row + Y[i]) == tile.row)
                return true;
        }

        return false;
    }
}
