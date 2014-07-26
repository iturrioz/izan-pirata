package controllers;

import models.Bomb;
import play.mvc.*;

import views.html.*;

import play.data.Form;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result bomb() {
        // eskuratu json DBtik
        Bomb bomb = Form.form(Bomb.class).bindFromRequest().get();

        System.out.println(bomb.y);
        // gordeDB
        return redirect(routes.Application.index());
    }
}
