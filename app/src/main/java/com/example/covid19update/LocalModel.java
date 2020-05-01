package com.example.covid19update;

public class LocalModel {
    private String state,cases;

    public LocalModel()
    {

    }

    public LocalModel(String state, String cases) {
        this.state = state;
        this.cases = cases;
    }

    public String getState() {
        return state;
    }

    public void setState(String flag) {
        this.state = state;
    }


    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

}
