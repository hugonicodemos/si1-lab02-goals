package controllers;

import models.Goal;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class GoalsApp extends Controller {

	private static Goal goal;
	final static Form<Goal> goalForm = Form.form(Goal.class);

	public static Result list() {
		try {
			if (goal == null) {
				goal = new Goal();
			}
			goal.sortGoals();

		} catch (Exception e) {
			return badRequest(views.html.goals.list.render(
					"Goal Creation ERROR: " + e.getMessage(), goal));
		}
		return ok(views.html.goals.list.render("You app it's READY!", goal));
	}

	public static Result create() {
		return ok(views.html.goals.create.render(goalForm));
	}

	public static Result save() {
		Form<Goal> form = goalForm.bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(views.html.goals.create.render(form));
		}

		Goal newGoal = form.get();
		goal.addNewGoal(newGoal);

		flash("success", "success");
		return found(routes.GoalsApp.list());
	}
	
	public static Result done(String goalDesc) {
		Goal doneGoal = goal.findByDescription(goalDesc);
		
		if (doneGoal == null) {
			flash("error", "error");
			return found(routes.GoalsApp.list());
		}
		
		goal.setGoalDone(doneGoal);
		
		return found(routes.GoalsApp.list());
	}
	
	public static Result delete(String goalDesc) {
		
		Goal deleteGoal = goal.findByDescription(goalDesc);

		if (deleteGoal == null) {
			flash("error", "error");
			return found(routes.GoalsApp.list());
		}

		goal.deleteGoal(deleteGoal);

		flash("success", "success");
		return found(routes.GoalsApp.list());

	}

}
