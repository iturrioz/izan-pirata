package controllers;

import models.Disparo;
import play.mvc.*;

import views.html.*;

import play.data.Form;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result disparo() {
        // eskuratu json DBtik
        Disparo bomb = Form.form(Disparo.class).bindFromRequest().get();

        System.out.println(bomb.y);
        // gordeDB
        return redirect(routes.Application.index());
    }
}
