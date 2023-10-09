package com.superchess.views.pieces;

import com.superchess.views.board.Board;
import com.vaadin.flow.component.html.Image;

public class Queen extends Piece{

    public Queen(Board board, Board.Tile tile, boolean isWhite){

        super(board);

        this.tile = tile;

        this.isWhite = isWhite;
        this.name = "Queen";

        String colorString = (isWhite ? "White":"Black");
        this.sprite = new Image("themes/superchess/" + colorString + "Queen.png", colorString + "Queen");
        this.add(sprite);

        this.setVisible(true);

    }
    @Override
    public boolean validMove(Board.Tile tile) {

        return (!diagonalObstruction(tile) && Math.abs(this.tile.col-tile.col) == Math.abs(this.tile.row-tile.row))
                || (Math.abs(this.tile.col-tile.col) == 0 || Math.abs(this.tile.row-tile.row) == 0) && !horizontalObstruction(tile) ;
    }

}
