package pl.edu.pwr.app.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Task {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final String name;
    private final String description;

    public Task() {
        this.name = "no name";
        this.description = "no description";
    }

    public Task(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
