package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.CompareToBuilder;

import play.db.ebean.Model;

@Entity
public class Goal implements Comparable<Goal> {

	@Id
	private Long id;

	private String description;

	private int week;

	private Priority priority;
	
	public enum Priority {
		Low("Low", 1), Medium("Medium", 2), High("High", 3), Critical("Critical", 4);
		
		private int value;
		private String name;
		
		Priority(String name, int value){
			this.name = name;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}

	private List<Goal> allGoals;
	private static final long serialVersionUID = 1L;
	private boolean done;
	
	public static Model.Finder<String, Goal> find = new Model.Finder<String, Goal>(String.class, Goal.class);

	public Goal() throws Exception {
		if(allGoals == null) {			
			initializeListGoals();
		}
	}

	public Goal(String description, int week, Priority priority) throws Exception {
		setDescription(description);
		setWeek(week);
		setPriority(priority);
		setDone(false);
	}

	public void initializeListGoals() throws Exception {
		allGoals = new ArrayList<Goal>() {
			{
				add(new Goal("Goal One", 2, priority.Critical));
				add(new Goal("Goal Two", 3, priority.Low));
				add(new Goal("Goal Three", 1, priority.Low));
				add(new Goal("Goal Four", 2, priority.Medium));
				add(new Goal("Goal Five", 2, priority.Medium));
				add(new Goal("Goal Six", 1, priority.High));
				add(new Goal("Goal Seven", 2, priority.High));
				add(new Goal("Goal Eight", 3, priority.Critical));
				add(new Goal("Goal Nine", 3, priority.Low));
				add(new Goal("Goal Ten", 2, priority.High));
			}
		};
	}

	public void addNewGoal(Goal goal) {
		this.allGoals.add(goal);
	}

	public void deleteGoal(Goal goal) {
		this.allGoals.remove(goal);	
	}
	
	public void deleteGoalByDescription(String description) {
		for (Goal goal : allGoals) {
			if (goal.getDescription().equals(description)) {
				allGoals.remove(goal);
			}
		}
	}

	public int totalOfGoals() {
		return allGoals.size();
	}
	
	public void setGoalDone(Goal goal) {
		goal.setDone(true);
	}

	public int totalOfGoalsByWeek(int week) {
		int totalGoal = 0;

		for (Goal goal : allGoals) {
			if (goal.getWeek() == week) {
				totalGoal++;
			}
		}

		return totalGoal;
	}

	public int totalOfGoalsDoneByWeek(int week) {
		int totalDone = 0;

		for (Goal goal : allGoals) {
			if (goal.isDone() && goal.getWeek() == week) {
				totalDone++;
			}
		}

		return totalDone;
	}

	public int totalOfGoalsNotDoneByWeek(int week) {
		int totalNotDone = 0;

		for (Goal goal : allGoals) {
			if (!goal.isDone() && goal.getWeek() == week) {
				totalNotDone++;
			}
		}

		return totalNotDone;
	}
	
	public static Goal findById(Long id) {
		return find.where().eq("id", id).findUnique();
	}
	
	public static List<Goal> findByWeek(int week) {
		return find.where().eq("week", week).findList();
	}
	
	public Goal findByDescription(String description) {
		for (Goal goal : allGoals) {
			if (goal.getDescription().equals(description)) {
				return goal;
			}
		}
		
		return null;
	}
	
	public List<Integer> getWeekNumber() {
		List<Integer> weeks = new ArrayList<Integer>() {
			{
				add(1);
				add(2);
				add(3);
				add(4);
				add(5);
				add(6);
			}
		};
		
		return weeks;
	}

	public void sortGoals() {
		Collections.sort(allGoals);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) throws Exception {
		if (week > 0 && week < 7) {
			this.week = week;
		} else {
			throw new Exception("Week Value Error");
		}
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public List<Goal> getAllGoals() {
		return allGoals;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public int compareTo(Goal goal) {
		return new CompareToBuilder().append(getWeek(), goal.getWeek())
				.append(getPriority().value, goal.getPriority().value).toComparison();
	}

	@Override
	public String toString() {
		return String.format("%s - %d - %s", description, week, priority.name);
	}

}
