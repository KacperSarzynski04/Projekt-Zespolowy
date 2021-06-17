package pl.edu.pwr.app.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "training")
public class Training implements Comparable<Training>{

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String topic;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    private String trainer;
    private int durationInMinutes;
    private LocalDate date;
    private LocalTime time;
    private String trainingImageUrl;
    private String trainingFileUrl;
    private String userId;

    public String getTrainingFileUrl() {
        return trainingFileUrl;
    }

    public Training(){

        topic = "no topic";
        description = "no description";
        trainer = "no trainer";
        durationInMinutes = 0;
        date = LocalDate.now();
        time = LocalTime.now();
        trainingImageUrl = null;
        trainingFileUrl = null;
        userId = null;
    }

    public String getUserId() {
        return userId;
    }

    public Training(String topic, String description, String trainer, int durationInMinutes, String trainingImageUrl,
                    String trainingFileUrl) {
        this.topic = topic;
        this.description = description;
        this.trainer = trainer;
        this.durationInMinutes = durationInMinutes;
        this.trainingImageUrl = trainingImageUrl;
        this.trainingFileUrl = trainingFileUrl;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }




    public Training(String topic, String description, String trainer, int durationInMinutes, LocalDate date,
                    LocalTime time, String trainingImageUrl, String trainingFileUrl){
        this.topic = topic;
        this.description = description;
        this.trainer = trainer;
        this.durationInMinutes = durationInMinutes;
        this.trainingImageUrl = trainingImageUrl;
        this.trainingFileUrl = trainingFileUrl;
        this.date = date;
        this.time = time;
    }

    public String getTrainingImageUrl() {
        return trainingImageUrl;
    }

    public Training(Training training) {
        this.id = training.getId();
        this.date = training.getDate();
        this.description = training.getDescription();
        this.durationInMinutes = training.getDurationInMinutes();
        this.time = training.getTime();
        this.topic = training.getTopic();
        this.trainer = training.getTrainer();
        this.trainingImageUrl = training.trainingImageUrl;
        this.trainingFileUrl = training.trainingFileUrl;
    }

    public long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }

    public String getTrainer() {
        return trainer;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTrainingFileUrl(String trainingFileUrl) {
        this.trainingFileUrl = trainingFileUrl;
    }

    public void setTrainingImageUrl(String trainingImageUrl) {
        this.trainingImageUrl = trainingImageUrl;
    }
    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", description='" + description + '\'' +
                ", trainer='" + trainer + '\'' +
                ", durationInMinutes=" + durationInMinutes +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
    @Override
    public int compareTo(Training o) {
        if (this.getDate().isBefore(o.getDate())) return -1;
        if (this.getDate().isAfter(o.getDate())) return 1;
        if (this.getTime().isBefore(o.getTime())) return -1;
        if (this.getTime().isAfter(o.getTime())) return 1;
        else return 0;
    }
}
