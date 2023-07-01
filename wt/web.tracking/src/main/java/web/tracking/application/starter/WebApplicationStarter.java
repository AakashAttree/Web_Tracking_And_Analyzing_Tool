package web.tracking.application.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import web.tracking.application.test.SuperAdminDataCreator;
 
@SpringBootApplication
@ComponentScan(basePackages = { "web.tracking" })
@EnableMongoRepositories(basePackages = { "web.tracking.db.dao", "web.tracking.activities" })
public class WebApplicationStarter {
  
	static class MyShutDownHook extends Thread {
		Process process;

		public MyShutDownHook(Process process) {
			super();
			this.process = process;
		}

		@Override
		public void run() {
			process.destroy();
		}
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(WebApplicationStarter.class, args);
		SuperAdminDataCreator demoDataCreater = new SuperAdminDataCreator(applicationContext);
		demoDataCreater.start();
		System.out.println("------------------------------------------");
		System.out.println("Started");
		System.out.println("------------------------------------------");
	}
}
