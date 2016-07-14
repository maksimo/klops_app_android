package ru.klops.klops.models.popular;

import java.util.List;

public class Popular {
    private List<News> news;

    public Popular() {
    }

    public Popular(List<News> news) {
        this.news = news;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "Popular{" +
                "news=" + news +
                '}';
    }
}
