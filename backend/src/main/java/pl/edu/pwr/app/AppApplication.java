package pl.edu.pwr.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.pwr.app.models.Task;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.repositories.UserRepository;
import pl.edu.pwr.app.repositories.TaskRepository;

import java.util.stream.Stream;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, TaskRepository taskRepository) {
        return args -> {
            Stream.of("Kacper", "Jacek", "Pavel", "Krzysztof").forEach(name -> {
                User user = new User(name, name.toLowerCase() + "@student.pwr.edu.pl");
                userRepository.save(user);
                userRepository.findAll().forEach(System.out::println);
            });

            Stream.of("todo 1", "todo 2").forEach(name -> {
                Task task = new Task(name, name);
                taskRepository.save(task);
                taskRepository.findAll().forEach(System.out::println);
            });
        };
    }
}