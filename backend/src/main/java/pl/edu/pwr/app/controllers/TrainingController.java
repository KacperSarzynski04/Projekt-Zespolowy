package pl.edu.pwr.app.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.app.models.Proposal;
import pl.edu.pwr.app.models.Training;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.repositories.TrainingRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = { "/"})
public class TrainingController {

    private final TrainingRepository trainingRepository;

    public TrainingController(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @GetMapping("/trainings")
    public Page<Training> list(@RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Training> pageResult = trainingRepository.findAll(pageRequest);
            List<Training> trainings = pageResult
                    .stream()
                    .map(Training::new)
                    .collect(toList());
            Collections.sort(trainings);
            return new PageImpl<>(trainings, pageRequest, pageResult.getTotalElements());

        }
    @GetMapping("/three_trainings")
    public List<Training> geThreeTasks() {
        //Wyswietlenie trzech najblizszych szkolen dla niezalogowanego uzytkownika
        return getTrainingsAsUser(3);

    }

    @GetMapping("/find_training/{id}")
    public Training getUser(@PathVariable("id") long id) {
        return trainingRepository.findById(id);
    }

    public List<Training> getSortedTrainings(){
        List<Training> trainingList = (List<Training>) trainingRepository.findAll();
        Collections.sort(trainingList);
        return trainingList;
    }
    public List<Training> getTrainingsAsUser(int limit){
        List<Training> sortedTrainingList = getSortedTrainings();
        LocalDate localDate = null;
        LocalTime localTime = null;
        List<Training> trainingListUser = new ArrayList<>();
        List<Training> trainingPassed = new ArrayList<>();
        int i=0;
        for (Training training : sortedTrainingList) {
            if(training.getDate().isAfter(localDate.now())){
                trainingListUser.add(training);
                i++;
            }
            else if (training.getDate() == localDate.now()) {
                    if (training.getTime().isAfter(localTime.now())) {
                        trainingListUser.add(training);
                        i++;
                    }
                }
            else {
                trainingPassed.add(0,training);
            }

            if(i>limit){
                break;
            }
        }
        if(limit>trainingListUser.size()){
            int difference = limit - trainingListUser.size();
            for( int j = 0 ; j<difference;j++){
                if(trainingPassed.size()>j){
                trainingListUser.add(0,trainingPassed.get(j));
                }
            }
        }

        return trainingListUser;
    }
    public List<Training> getTrainingAsAdmin(){
        return (List<Training>) trainingRepository.findAll();
    }
    @PostMapping("/trainings")
    void addTraining(@RequestBody Training training) {
        trainingRepository.save(training);
    }
}