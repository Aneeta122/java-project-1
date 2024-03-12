
package demo;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
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
		ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
//		es.scheduleAtFixedRate(() -> System.out.println("Hello World! from ScheduledExecutorService"), 10000, 10000,
//				TimeUnit.MILLISECONDS);
		ArrayList<Employee> employees = createDummyData();
		Time time = new Time();
		Logger logger = new Logger();
		String toDisplay="";
		for (Employee employee : employees) {
			toDisplay += time.getCurrentTime() + "-> Name: " + employee.name + " with age: " + employee.age
					+ " has a salary of: " + Double.valueOf(new DecimalFormat("#.##").format(employee.salary));
			toDisplay+=" \n ";
		}
		display(toDisplay);
		try {
			logger.writeToFile(toDisplay);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}