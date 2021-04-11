package pl.edu.pwr.app.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Training implements Comparable<Training>{

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String topic;
    private final String description;
    private final String trainer;
    private final int durationInMinutes;
    private final LocalDate date;
    private final LocalTime time;

    public Training(){

        topic = "no topic";
        description = "no description";
        trainer = "no trainer";
        durationInMinutes = 0;
        date = LocalDate.now();
        time = LocalTime.now();
    }

    public Training(String topic, String description, String trainer, int durationInMinutes) {
        this.topic = topic;
        this.description = description;
        this.trainer = trainer;
        this.durationInMinutes = durationInMinutes;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }


    public Training(String topic, String description, String trainer, int durationInMinutes, LocalDate date,
                    LocalTime time) {
        this.topic = topic;
        this.description = description;
        this.trainer = trainer;
        this.durationInMinutes = durationInMinutes;
        this.date = date;
        this.time = time;
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
        if(this.getDate().isBefore(o.getDate())) return -1;
        if(this.getDate().isAfter(o.getDate())) return 1;
        if(this.getTime().isBefore(o.getTime())) return -1;
        if(this.getTime().isAfter(o.getTime())) return 1;
        else return 0;
    }
}
