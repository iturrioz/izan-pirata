package controllers;

import models.Disparo;
import models.logic.Partida;
import play.mvc.*;

import views.html.*;

import play.data.Form;
import java.util.Random;

public class Application extends Controller {

    public static Result index() {
        int gameId;
        
        try {
            gameId = Integer.parseInt(session("connected"));
            return redirect(controllers.routes.Application.game(gameId));
        } catch (NumberFormatException e) {
        }
        
        return ok(index.render(3, 3, 2));
    }

    public static Result create() {
        // Partida sortu
        // gameId = new Random().nextInt(100000);
        // session("connected", ""+gameId);
        int gameId = 0;
        return ok(controllers.routes.Application.game(gameId).url());
    }

    public static Result game(int gameId) {
        Partida partida = Partida.load(gameId);
        int jugador = 0;
        if (partida != null) {
            return ok(game.render(partida, jugador));
        } else {
            return notFound();
        }
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

    public static Result reset() {
        session().clear();

        return redirect(controllers.routes.Application.index());
    }
}
