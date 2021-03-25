package pl.edu.pwr.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.pwr.app.models.Training;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.repositories.TrainingRepository;
import pl.edu.pwr.app.repositories.UserRepository;

import java.util.stream.Stream;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, TrainingRepository trainingRepository) {
        return args -> {
            Stream.of("Kacper", "Jacek", "Pavel", "Krzysztof").forEach(name -> {
                User user = new User(name, name.toLowerCase() + "@student.pwr.edu.pl");
                userRepository.save(user);
                userRepository.findAll().forEach(System.out::println);
            });

            Stream.of("training 1").forEach(name -> {
                Training training = new Training("interesting topic", "even more interesting description",
                        "super trainer", 100);
                trainingRepository.save(training);
                trainingRepository.findAll().forEach(System.out::println);
            });
        };
    }
}