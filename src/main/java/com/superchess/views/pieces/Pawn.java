package com.superchess.views.pieces;

import com.superchess.views.board.Board;
import com.vaadin.flow.component.html.Image;

public class Pawn extends Piece{

    boolean firstMove;
    int moves;

    public Pawn(Board board, Board.Tile tile, boolean isWhite){

        super(board);

        this.firstMove = true;
        this.moves = 0;

        this.tile = tile;

        this.isWhite = isWhite;
        this.name = "Pawn";

        String colorString = (isWhite ? "White":"Black");
        this.sprite = new Image("themes/superchess/" + colorString + "Pawn.png", colorString + "Pawn");
        this.add(sprite);

        this.setVisible(true);

    }
    @Override
    public boolean validMove(Board.Tile tile) {

        int direction = isWhite ? -1:1;

        boolean valid =  (!diagonalObstruction(tile) && Math.abs(tile.col - this.tile.col) == 1 && tile.row == this.tile.row + direction && tile.piece != null && tile.piece.isWhite != this.isWhite)
                            ||  (((tile.row == this.tile.row + direction || tile.row == this.tile.row + 2*direction && firstMove) && Math.abs(tile.col - this.tile.col) == 0 && !horizontalObstruction(tile)) && tile.piece == null);

        if (valid && firstMove)
            firstMove = false;

        if(valid){
            if(tile.row == this.tile.row + 2*direction)
                this.moves += 2;
            else
                ++this.moves;


            if(this.moves == 6) {
                tile.addPiece(new Queen(this.board, tile, this.isWhite));
                this.tile.removeAll();
                this.tile.piece = null;
            }
        }

        return valid;
    }

}
