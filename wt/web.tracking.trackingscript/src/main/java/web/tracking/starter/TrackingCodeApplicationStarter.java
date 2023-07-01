package web.tracking.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {"web.tracking"})
@EnableMongoRepositories(basePackages = {"web.tracking.db.dao", "web.tracking.activities"})
public class TrackingCodeApplicationStarter {
	public static void main(String[] args) {
		SpringApplication.run(TrackingCodeApplicationStarter.class, args);
	}
}
