import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import models.Goal;
import models.Goal.Priority;

import org.junit.Before;
import org.junit.Test;


public class GoalTest {
	
	private Goal goal;
	
	@Before
	public void setUp() throws Exception {
		goal = new Goal();
	}

	@Test
	public void shouldHaveTenInitialGoals() {
		assertEquals(goal.totalOfGoals(), 10);
	}
	
	@Test
	public void shouldNotExceedThirdWeekOnStart() {
		List<Integer> testWeek = new ArrayList<Integer>();
		for (Goal testGoal : goal.getAllGoals()) {
			testWeek.add(testGoal.getWeek());
		}
		
		assertTrue(Collections.max(testWeek) < 4);
	}

	@Test
	public void shouldHaveAPositiveNumberWeekAndLessThanSix() {
		for (Goal testGoal : goal.getAllGoals()) {
			assertTrue(testGoal.getWeek() > 0 && testGoal.getWeek() < 7);
		}
	}
	
	@Test
	public void shouldGoalSortByWeekThenPriority() {
		goal.sortGoals();
		assertTrue(goal.getAllGoals().get(0).getDescription().equals("Goal Three"));
		assertTrue(goal.getAllGoals().get(4).getDescription().equals("Goal Seven"));
		assertTrue(goal.getAllGoals().get(9).getDescription().equals("Goal Eight"));
	}
	
	@Test
	public void shouldReturnTotalOfGoalsDoneByWeek() {
		int testGone = 0;
		testGone = goal.totalOfGoalsDoneByWeek(3);
		
		assertEquals(testGone, 0);
		
		goal.getAllGoals().get(1).setDone(true);
		goal.getAllGoals().get(7).setDone(true);
		goal.getAllGoals().get(8).setDone(true);
		
		testGone = goal.totalOfGoalsDoneByWeek(3);
		
		assertEquals(testGone, 3);
		
	}
	
	@Test
	public void shouldReturnTotalOfGoalsNotDoneByWeek() {
		int testNotGone = 0;
		
		for (Goal testGoal : goal.getAllGoals()) {
			testGoal.setDone(true);
		}
		
		testNotGone = goal.totalOfGoalsNotDoneByWeek(3);
		
		assertEquals(testNotGone, 0);
		
		goal.getAllGoals().get(1).setDone(false);
		goal.getAllGoals().get(7).setDone(false);
		goal.getAllGoals().get(8).setDone(false);
		
		testNotGone = goal.totalOfGoalsNotDoneByWeek(3);
		
		assertEquals(testNotGone, 3);
		
	}
	
	@Test
	public void shouldCreateNewGoal() {
		try {
			goal.addNewGoal(new Goal("testGoal", 2, Priority.Low));
		} catch (Exception e) {
			fail();
		}
		assertTrue(goal.getAllGoals().get(10).getDescription().equals("testGoal"));
	}
	
	@Test
	public void shouldDeleteGoal() {
				
		try {
			Goal testGoal = new Goal("testGoal", 2, Priority.Low);
			goal.addNewGoal(testGoal);
			assertEquals(goal.totalOfGoals(), 11);
			goal.deleteGoal(testGoal);
		} catch (Exception e) {
			fail();
		}
		
		assertEquals(goal.totalOfGoals(), 10);
		List<Priority> al = new ArrayList<Priority>();
		for(Priority values : goal.getPriority().values()) {
			System.out.println(values);
		}
	}
	
	
}
