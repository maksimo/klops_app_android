package ru.klops.klops.models.feed;

import java.util.List;

public class Page{

    private List<News> news;
    private String page;

    public Page() {
    }

    public Page(List<News> news, String page) {
        this.news = news;
        this.page = page;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Page{" +
                "news=" + news +
                ", page='" + page + '\'' +
                '}';
    }
}