package com.praise.recipe;

public class Food {
    String title;
    String imageUrl;
    int id;
    int likes;
    String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Food(String title, String imageUrl, int id) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public Food(String title, String imageUrl, int id, int likes, String source) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.id = id;
        this.likes = likes;
        this.source = source;
    }

    public Food(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
