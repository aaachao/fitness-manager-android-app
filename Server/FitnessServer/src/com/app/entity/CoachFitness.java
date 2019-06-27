package com.app.entity;

public class CoachFitness {
    private int Id;
    private int coachId;
    private int fitnessId;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

	public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public int getFitnessId() {
        return fitnessId;
    }

    public void setFitnessId(int fitnessId) {
        this.fitnessId = fitnessId;
    }
}
