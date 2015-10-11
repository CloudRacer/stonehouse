package uk.org.mcdonnell.stonehouse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import uk.org.mcdonnell.utility.common.Bootstrap;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException, IOException {
        SpringApplication.run(Application.class, args);
        Bootstrap.start();
    }
}
