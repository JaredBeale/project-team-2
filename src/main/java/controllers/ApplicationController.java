package controllers;

import com.google.inject.Singleton;
import cs361.battleships.models.*;
import ninja.Context;
import ninja.Result;
import ninja.Results;

@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html();
    }

    public Result newGame() {
        Game g = new Game();
        return Results.json().render(g);
    }

    public Result placeShip(Context context, PlacementGameAction g) {
        Game game = g.getGame();
        Ship ship;
        if (g.getShipType().equalsIgnoreCase("minesweeper"))
            ship = new Minesweeper();
        else if (g.getShipType().equalsIgnoreCase("destroyer"))
            ship = new Destroyer();
        else if (g.getShipType().equalsIgnoreCase("battleship"))
            ship = new Battleship();
        else {
            ship = new Submarine();
            ship.setSubmerged(g.isSubmerged());
        }


        boolean result = game.placeShip(ship, g.getActionRow(), g.getActionColumn(), g.isVertical());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest().json();
        }
    }

    public Result attack(Context context, AttackGameAction g) {
        Game game = g.getGame();
        boolean result = game.attack(g.getActionRow(), g.getActionColumn());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest().json();
        }
    }

    public Result move(Context context, MoveGameAction m) {
        Game game = m.getGame();
        game.move(m.getDirection());

        return Results.json().render(game);
    }

}
