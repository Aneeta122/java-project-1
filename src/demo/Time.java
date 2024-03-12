package demo;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

	public String getCurrentTime() {
		long millis = System.currentTimeMillis();

		// creating a new object of the class Date
		Date date = new Date(millis);
		Format formatter = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
		String s = formatter.format(date);
		return s;
	}
}
