package controllers;

import models.Disparo;
import models.logic.Partida;
import play.mvc.*;

import views.html.*;

import play.data.Form;
import java.util.Random;

public class Application extends Controller {

    public static Result index() {
        Partida partida = null;
        int gameId;
        
        try {
            gameId = Integer.parseInt(session("connected"));
            System.out.println("Game already exists!");
            return redirect(controllers.routes.Application.game(gameId));
        } catch (NumberFormatException e) {
            System.out.println("New game");
            gameId = new Random().nextInt(100000);
            session("connected", ""+gameId);
        }
        partida = new Partida(gameId);
        
        return ok(index.render(3, 3, 2));
    }

    public static Result game(int gameId) {
        return ok("TODO");
    }

    public static Result shoots() {
        // eskuratu json DBtik
        Disparo bomb = Form.form(Disparo.class).bindFromRequest().get();

        System.out.println(bomb.y);
        // gordeDB
        return redirect(routes.Application.index());
    }

    public static Result ships() {
        // eskuratu json DBtik
        Disparo bomb = Form.form(Disparo.class).bindFromRequest().get();

        System.out.println(bomb.y);
        // gordeDB
        return redirect(routes.Application.index());
    }
}
