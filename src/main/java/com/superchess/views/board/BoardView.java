package com.superchess.views.board;

import com.superchess.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("SuperChess Board")
@Route(value = "board", layout = MainLayout.class)
@RouteAlias(value = "board", layout = MainLayout.class)
public class BoardView extends HorizontalLayout {

    public BoardView() {

        this.setPadding(true);
        this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Board board = new Board();
        add(board);
    }

}
