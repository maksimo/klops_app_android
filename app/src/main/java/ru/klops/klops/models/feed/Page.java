package ru.klops.klops.models.feed;

import java.util.List;

public class Page{

    private List<News> news;
    private Object page;
    public Currency currency;

    public Page() {
    }

    public Page(List<News> news, Object page, Currency currency) {
        this.news = news;
        this.page = page;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Page{" +
                "news=" + news +
                ", page=" + page +
                ", currency=" + currency +
                '}';
    }
}