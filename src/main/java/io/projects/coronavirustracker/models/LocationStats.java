package io.projects.coronavirustracker.models;

public class LocationStats {
    private String state;
    private String country;
    private int latestReportedCases;
    private int diffFromPrevDay;

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public int getDiffFromPrev() { return diffFromPrevDay; }

    public int getLatestReportedCases() {
        return latestReportedCases;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatestReportedCases(int latestReportedCases) {
        this.latestReportedCases = latestReportedCases;
    }

//    public void setDiffFromPrevDay(int diffFromPrevDay) {
//        this.diffFromPrevDay = diffFromPrevDay;
//    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    @Override
    public String toString() {
        return "LocationStats { " +
                "State = " + state + '\'' +
                ", Country = " + country + '\'' +
                ", LatestReportedCases = " + latestReportedCases +
                " }";
    }
}
