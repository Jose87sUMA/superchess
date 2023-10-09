package com.superchess.views.pieces;

import com.superchess.views.board.Board;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Tag("Piece")
public abstract class Piece extends VerticalLayout implements DragSource, HasSize {

    public Board.Tile tile;

    public boolean isWhite;
    public String name;
    public int value;


    public Image sprite;
    public Board board;

    public Piece(Board board){

        this.setDraggable(true);

        this.setHeight("60px");
        this.setWidth("60px");
        this.board = board;

        this.addDragStartListener(event -> {
            this.sprite.setVisible(false);
        });

        this.addDragEndListener(event -> {
            this.sprite.setVisible(true);
        });

        this.setDragData(this); // the data can be any object

    }

    public abstract boolean validMove(Board.Tile tile);

    protected boolean diagonalObstruction(Board.Tile tile) {

        int dirX = tile.col > this.tile.col ? 1 : -1;
        int dirY = tile.row > this.tile.row ? 1 : -1;
        for (int i=1; i < Math.abs(tile.col - this.tile.col); ++i) {
            if ((this.tile.col + i*dirX + (this.tile.row + i*dirY)*8 > 0) && (board.getTiles().get(this.tile.col + i*dirX + (this.tile.row + i*dirY)*8)).piece != null) {
                return true;
            }
        }
        return false;
    }

    protected boolean horizontalObstruction(Board.Tile tile) {

        int dirX;
        int dirY;

        if(Math.abs(this.tile.col-tile.col) == 0) {
            dirX = 0;
            dirY = tile.row > this.tile.row ? 1 : -1;
        }else {
            dirX = tile.col > this.tile.col ? 1 : -1;
            dirY = 0;
        }


        for (int i=1; i < Math.max(Math.abs(tile.col - this.tile.col), Math.abs(tile.row - this.tile.row)); ++i) {
            if ((board.getTiles().get(this.tile.col + i*dirX + (this.tile.row + i*dirY)*8)).piece != null) {
                return true;
            }
        }
        return false;

    }

}
