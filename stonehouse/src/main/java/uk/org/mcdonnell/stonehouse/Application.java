package uk.org.mcdonnell.stonehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import uk.org.mcdonnell.utility.common.Bootstrap;
import uk.org.mcdonnell.utility.common.BootstrapException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws BootstrapException {
        SpringApplication.run(Application.class, args);
        new Bootstrap();
    }
}
