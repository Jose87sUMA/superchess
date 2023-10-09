package com.superchess.views.pieces;

import com.superchess.views.board.Board;
import com.vaadin.flow.component.html.Image;

public class King extends Piece{

    boolean firstMove;

    public King(Board board, Board.Tile tile, boolean isWhite){

        super(board);

        this.firstMove = true;

        this.tile = tile;

        this.isWhite = isWhite;
        this.name = "King";

        String colorString = (isWhite ? "White":"Black");
        this.sprite = new Image("themes/superchess/" + colorString + "King.png", colorString + "King");
        this.add(sprite);

        this.setVisible(true);

    }
    @Override
    public boolean validMove(Board.Tile tile) {


        Boolean valid =  (!diagonalObstruction(tile) && Math.abs(this.tile.col-tile.col) == Math.abs(this.tile.row-tile.row) && Math.abs(this.tile.row-tile.row) == 1)
                || (Math.abs(this.tile.col-tile.col) == 0 || Math.abs(this.tile.row-tile.row) == 0) && !horizontalObstruction(tile)
                    && Math.max(Math.abs(this.tile.col-tile.col), Math.abs(this.tile.row-tile.row)) == 1
                || this.firstMove && this.checkCastle(tile);

        if (valid && firstMove)
            firstMove = false;

        return valid;
    }

    private boolean checkCastle(Board.Tile tile) {

        int rookPos = this.tile.col - tile.col > 0 ? 0:7;
        Piece piece = board.getTiles().get(rookPos + (this.tile.row)*8).piece;
        Rook rook;
        Board.Tile rookFinalPosition = board.getTiles().get(this.tile.col + (this.tile.col - tile.col > 0 ? -1:1) + (this.tile.row)*8);

        if(piece instanceof Rook)
            rook = (Rook) piece;
        else
            return false;

        boolean validCastle = Math.abs(this.tile.row-tile.row) == 0 && Math.abs(this.tile.col-tile.col) == 2 && !horizontalObstruction(tile)
                                && rook.firstMove && !rook.horizontalObstruction(rookFinalPosition);

        if(validCastle) {
            this.tile.removeAll();
            this.tile.piece = null;

            rook.firstMove = false;
            rook.tile.piece = null;

            rookFinalPosition.add(rook);
            rookFinalPosition.piece = rook;
            rook.tile = rookFinalPosition;

        }
        return validCastle;

    }

}
