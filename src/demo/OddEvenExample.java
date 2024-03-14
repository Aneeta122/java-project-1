
package demo;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

class OddEvenExample {

	static void display(String stringToDisplay) {
		System.out.println(stringToDisplay);
	}

	static ArrayList<Employee> createDummyData() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		for (int i = 0; i < 10; i++) {
			Employee e = new Employee();
			e.name = "Name " + (i + 1);
			e.age = new Random().nextInt(50 - 18) + 18;
			e.salary = 12000 + (1000000 - 12000) * new Random().nextDouble();
			employees.add(e);
		}
		return employees;
	}

	public static void main(String args[]) {

		Time time = new Time();
		Logger logger = new Logger();

		while (true) {
			ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
			// TODO: to be a thread
			Future<ArrayList<Employee>> futureTaskGetData = es.submit(() -> {
				ArrayList<Employee> employees = createDummyData();
				return employees;
			});

			while (!futureTaskGetData.isDone()) {
				System.out.println("The task is still in process.....");
				// sleep thread for 2 milliseconds
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			ArrayList<Employee> employees = new ArrayList<Employee>();
			try {
				employees = futureTaskGetData.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String toDisplay = "";
			for (Employee employee : employees) {
				toDisplay += time.getCurrentTime() + "-> Name: " + employee.name + " with age: " + employee.age
						+ " has a salary of: " + Double.valueOf(new DecimalFormat("#.##").format(employee.salary));
				toDisplay += " \n ";
			}
			display(toDisplay);
			final String displayString = toDisplay;
			// TODO: to be a thread
			Future<Boolean> futureTaskWriteData = es.submit(() -> {
				return logger.writeToFile(displayString);
			});

			while (!futureTaskWriteData.isDone()) {
				System.out.println("The task is still in process.....");
				// sleep thread for 2 milliseconds
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			boolean writeOutput = false;
			try {
				writeOutput = futureTaskWriteData.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			display("Write " + (writeOutput ? "Success" : "Failed"));
		}
	}
}