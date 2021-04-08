package pl.edu.pwr.app.controllers;

import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.app.models.Training;
import pl.edu.pwr.app.repositories.TrainingRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        //Wyswietlenie trzech najblizszych szkolen dla niezalogowanego uzytkownika
        return getTrainingsAsUser(3);
        //return (List<Training>) trainingRepository.findAll();
    }
    public List<Training> getSortedTrainings(){
        List<Training> trainingList = (List<Training>) trainingRepository.findAll();
        List<Training> sortedTrainingList = new ArrayList<>();
        Collections.sort(trainingList);
        LocalDate localDate = null;
        LocalTime localTime = null;
        for (Training training : trainingList) {
            if(training.getDate().isEqual(localDate.now())){
                if(training.getTime().isAfter(localTime.now())){
                    sortedTrainingList.add(training);
                }
            } else {
            if(training.getDate().isAfter(localDate.now())){
               sortedTrainingList.add(training);
            }
            }
        }
        return sortedTrainingList;
    }
    public List<Training> getTrainingsAsUser(int limit){
        List<Training> sortedTrainingList = getSortedTrainings();

        if(sortedTrainingList.size()<limit){
            limit=sortedTrainingList.size();
        }
        List<Training> trainingListUser = new ArrayList<>();
        int i=0;
        for (Training training : sortedTrainingList) {
                       trainingListUser.add(training);
                       i++;
                       if(i>limit){
                           break;
                       }
                   }
        return trainingListUser;
    }
    @PostMapping("/trainings")
    void addTraining(@RequestBody Training training) {
        trainingRepository.save(training);
    }
}
