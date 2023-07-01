package web.tracking.application.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class Test {

	public static void main(String[] args) {
		String filePath = "C:\\Users\\mohit.sharma\\workspace\\testws\\web.tracking\\src\\main\\java\\web\\tracking\\application\\test\\data.txt";
		try {

			try {
				DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				Date date = dateFormat2.parse("2018-02-23T05:14:00Z");
				long idletime = date.getTime() - System.currentTimeMillis();
				System.out.println(TimeZone.getDefault().getDisplayName());
				System.out.println(TimeZone.getDefault().getID());
				System.out.println(idletime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Scanner scanner = new Scanner(new File(filePath));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String s = "$(\"#pages\").bind(\"" + line + "\", function () {console.log(\"" + line + "\");});";
				System.out.println(s);
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
