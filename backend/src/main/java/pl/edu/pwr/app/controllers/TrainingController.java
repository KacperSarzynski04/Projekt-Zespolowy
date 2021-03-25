package pl.edu.pwr.app.controllers;

import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.app.models.Training;
import pl.edu.pwr.app.repositories.TrainingRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TrainingController {

    private final TrainingRepository trainingRepository;

    public TrainingController(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @GetMapping("/trainings")
    public List<Training> getTasks() {
        return (List<Training>) trainingRepository.findAll();
    }

    @PostMapping("/trainings")
    void addTraining(@RequestBody Training training) {
        trainingRepository.save(training);
    }
}
