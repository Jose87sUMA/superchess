package com.superchess.views.board;

import com.superchess.views.pieces.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.dnd.DropEffect;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Board extends VerticalLayout {

    public int tileSize = 75;
    public String tileSizeStr = tileSize + "px";

    final int ROWS = 8;
    final int COLS = 8;

    Row [] rows = new Row[ROWS];

    public ArrayList<Piece> whitePieces = new ArrayList<>();
    public ArrayList<Piece> blackPieces = new ArrayList<>();

    boolean turn;
    boolean end;

    private class Row extends HorizontalLayout implements HasSize {

        Tile [] tiles = new Tile[COLS];

        public Row(){

            this.setHeight(tileSizeStr);
            this.setWidth("600px");
            this.setVisible(true);

            this.setSpacing(false);

        }
    }

    public class Tile extends VerticalLayout implements HasSize, DropTarget<VerticalLayout> {

        public Piece piece;
        public int row;
        public int col;

        public Tile(int row, int col) {

            super();

            this.row = row;
            this.col = col;

            this.setSpacing(false);
            this.setMargin(false);
            this.setPadding(false);
            this.setActive(true);

            this.setAlignItems(Alignment.CENTER);
            this.setJustifyContentMode(JustifyContentMode.CENTER);

            this.setHeight(tileSizeStr);
            this.setWidth(tileSizeStr);

            this.setDropEffect(DropEffect.MOVE);

            this.addDropListener(event -> {
                // move the dragged component to inside the drop target component
                if (event.getDropEffect() == DropEffect.MOVE) {
                    // the drag source is available only if the dragged component is from
                    // the same UI as the drop target

                    event.getDragData().ifPresent(data -> {
                        Piece movedPiece = (Piece) data;

                        if(!end && movedPiece.isWhite == turn && (this.piece == null || this.piece.isWhite != movedPiece.isWhite) && movedPiece.validMove(this)){

                            AtomicReference<Piece> source = new AtomicReference<>();
                            event.getDragSourceComponent().ifPresent(Component::removeFromParent);
                            event.getDragSourceComponent().ifPresent(component -> source.set((Piece) component));
                            source.get().tile.piece = null;
                            source.get().tile = this;

                            if(this.piece instanceof King){
                                Notification notification = Notification.show((turn ? "White":"Black") + " wins!", 5000, Notification.Position.MIDDLE);
                                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                                end = true;
                            }

                            if(this.piece != null && this.piece.isWhite){
                                whitePieces.remove(this.piece);
                            } else if (this.piece != null) {
                                blackPieces.remove(this.piece);
                            }

                            this.addPiece(movedPiece);
                            turn = !turn;

                        }

                    });
                }
            });

        }

        public void addPiece(Piece piece){

            this.removeAll();

            this.add(piece);
            this.piece = piece;
        }

        public Piece getPiece() {
            return this.piece;
        }
    }

    private class BlackTile extends Tile  {
        public BlackTile(int row, int col) {

            super(row, col);

        }
    }

    private class WhiteTile extends Tile{
        public WhiteTile(int row, int col) {

            super(row, col);
            this.addClassNames(LumoUtility.Background.CONTRAST_90);

        }
    }


    public Board() {

        this.setSpacing(false);

        Row row;
        for(int i = 0; i < ROWS; ++i){
            row = new Row();

            for(int j = 0; j < COLS; ++j) {

                if((i+j) % 2 == 1) {
                    row.tiles[j] = new BlackTile(i, j);
                    row.add(row.tiles[j]);
                } else {
                    row.tiles[j] = new WhiteTile(i, j);
                    row.add(row.tiles[j]);
                }
            }
            this.rows[i] = row;
            this.add(row);
        }

        for(int i = 0; i < COLS; ++i){

            this.rows[1].tiles[i].addPiece(new Pawn(this, rows[1].tiles[i], false));
            blackPieces.add(this.rows[1].tiles[i].piece);
            this.rows[6].tiles[i].addPiece(new Pawn(this, rows[6].tiles[i], true));
            whitePieces.add(this.rows[6].tiles[i].piece);

        }

        this.rows[0].tiles[0].addPiece(new Rook(this, rows[0].tiles[0], false));
        this.rows[0].tiles[7].addPiece(new Rook(this, rows[0].tiles[7], false));
        this.rows[0].tiles[1].addPiece(new Knight(this, rows[0].tiles[1], false));
        this.rows[0].tiles[6].addPiece(new Knight(this, rows[0].tiles[6], false));
        this.rows[0].tiles[2].addPiece(new Bishop(this, rows[0].tiles[2], false));
        this.rows[0].tiles[5].addPiece(new Bishop(this, rows[0].tiles[5], false));
        this.rows[0].tiles[3].addPiece(new Queen(this, rows[0].tiles[3], false));
        this.rows[0].tiles[4].addPiece(new King(this, rows[0].tiles[4], false));

        blackPieces.add(this.rows[0].tiles[0].piece);
        blackPieces.add(this.rows[0].tiles[7].piece);
        blackPieces.add(this.rows[0].tiles[1].piece);
        blackPieces.add(this.rows[0].tiles[6].piece);
        blackPieces.add(this.rows[0].tiles[2].piece);
        blackPieces.add(this.rows[0].tiles[5].piece);
        blackPieces.add(this.rows[0].tiles[3].piece);
        blackPieces.add(this.rows[0].tiles[4].piece);


        this.rows[7].tiles[0].addPiece(new Rook(this, rows[7].tiles[0], true));
        this.rows[7].tiles[7].addPiece(new Rook(this, rows[7].tiles[7], true));
        this.rows[7].tiles[1].addPiece(new Knight(this, rows[7].tiles[1], true));
        this.rows[7].tiles[6].addPiece(new Knight(this, rows[7].tiles[6], true));
        this.rows[7].tiles[2].addPiece(new Bishop(this, rows[7].tiles[2], true));
        this.rows[7].tiles[5].addPiece(new Bishop(this, rows[7].tiles[5], true));
        this.rows[7].tiles[3].addPiece(new Queen(this, rows[7].tiles[3], true));
        this.rows[7].tiles[4].addPiece(new King(this, rows[7].tiles[4], true));

        whitePieces.add(this.rows[7].tiles[0].piece);
        whitePieces.add(this.rows[7].tiles[7].piece);
        whitePieces.add(this.rows[7].tiles[1].piece);
        whitePieces.add(this.rows[7].tiles[6].piece);
        whitePieces.add(this.rows[7].tiles[2].piece);
        whitePieces.add(this.rows[7].tiles[5].piece);
        whitePieces.add(this.rows[7].tiles[3].piece);
        whitePieces.add(this.rows[7].tiles[4].piece);

        this.setAlignItems(FlexComponent.Alignment.CENTER);

        turn = true;
        end = false;

        this.setVisible(true);
    }

    public ArrayList<Tile> getTiles() {

        ArrayList<Tile> tiles = new ArrayList<>();

        for(int i = 0; i < ROWS; ++i){

            for(int j = 0; j < COLS; ++j){

                tiles.add(rows[i].tiles[j]);

            }

        }

        return tiles;

    }


}
