package com.nsa.cecobike;

public class Goal {
    private double Goal_Miles;
    private double Average_Miles;
    private double Progress_Miles;

    public Goal(double goal_Miles, double average_Miles) {
        Goal_Miles = goal_Miles;
        Average_Miles = average_Miles;
    }

    public double getGoal_Miles() {
        return Goal_Miles;
    }

    public void setGoal_Miles(double goal_Miles) {
        Goal_Miles = goal_Miles;
    }

    public double getAverage_Miles() {
        return Average_Miles;
    }

    public void setAverage_Miles(double average_Miles) {
        Average_Miles = average_Miles;
    }

    public double getProgress_Miles() {
        return Progress_Miles;
    }

    public void setProgress_Miles(double progress_Miles) {
        Progress_Miles = progress_Miles;
    }

    public Double calculateMiles(Double progress_Miles){
        Double bMiles = getGoal_Miles();
        Double cMiles = getAverage_Miles();

        return progress_Miles;
    }
}
