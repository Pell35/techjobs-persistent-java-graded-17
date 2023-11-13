package org.launchcode.techjobs.persistent.models;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Skill extends AbstractEntity {
    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Skill() {
        //no-arg, different name?
    }
}
