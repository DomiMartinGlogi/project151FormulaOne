package ch.wiss.project151formulaone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class Project151FormulaOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(Project151FormulaOneApplication.class, args);
    }

}
