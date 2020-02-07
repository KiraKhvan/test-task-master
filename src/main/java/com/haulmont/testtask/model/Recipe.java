package com.haulmont.testtask.model;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Patient patient;

    @Column(length = 2000)
    private String description;

    @Temporal(TemporalType.DATE)
    private Date dateOfCreation;

    private String deadlines;

    private String priorityR;

    public Recipe(){}

    public Recipe(Doctor doctor, Patient patient, String description,
                  Date dateOfCreation, String deadlines, String priorityR){
        this.doctor = doctor;
        this.patient = patient;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.deadlines = deadlines;
        this.priorityR = priorityR;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getPriorityR() {
        return priorityR;
    }

    public void setPriorityR(String priorityR) {
        this.priorityR = priorityR;
    }


    public String getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(String deadlines) {
        this.deadlines = deadlines;
    }
}
