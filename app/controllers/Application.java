package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import models.Person;

import play.data.Form;

import java.util.List;

import play.db.ebean.Model;

import static play.libs.Json.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

//    public static Result addPerson() {
//    	Person person = Form.form(Person.class).bindFromRequest().get();
//    	person.save();
//    	return redirect(routes.Application.index());
//    }

    public static Result bomb(String partidaid, String userid, int column, int row) {
        // TODO deitu Igorren funtzioa
        return redirect(routes.Application.index());
    }
}
