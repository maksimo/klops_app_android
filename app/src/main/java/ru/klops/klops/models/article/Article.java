package ru.klops.klops.models.article;

public class Article {
    public Item item;

    public Article(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Article{" +
                "item=" + item +
                '}';
    }
}
