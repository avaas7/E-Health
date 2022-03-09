package com.example.appointmentapplication.Newsfeed;

import java.util.ArrayList;

public class mainNews {

    String status;
    String totalResults;
    ArrayList<modelClass> articles;

    public mainNews(String status, String totalResults, ArrayList<modelClass> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<modelClass> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<modelClass> articles) {
        this.articles = articles;
    }


}
