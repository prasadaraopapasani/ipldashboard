package io.prasad.ipldashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan("io.prasad.ipldashboard.*")
//@EnableJpaRepositories(basePackageClasses = {MatchRepository.class,TeamRepository.class})
public class IplDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(IplDashboardApplication.class, args);
	}

}
