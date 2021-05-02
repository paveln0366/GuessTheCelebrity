package com.pavelpotapov.guessthecelebrity.model.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_celebrity")
public class Celebrity {
    @PrimaryKey
    private Integer position;
    private Integer rank;
    private String name;
    private String lastName;
    private String uri;
    private String imageUri;
    private Integer age;
    private String source;
    private String industry;
    private String gender;
    private String country;
    private String category;
    private Double earnings;
    private String squareImage;

    public Celebrity(Integer position,
                     Integer rank,
                     String name,
                     String lastName,
                     String uri,
                     String imageUri,
                     Integer age,
                     String source,
                     String industry,
                     String gender,
                     String country,
                     String category,
                     Double earnings,
                     String squareImage) {
        this.position = position;
        this.rank = rank;
        this.name = name;
        this.lastName = lastName;
        this.uri = uri;
        this.imageUri = imageUri;
        this.age = age;
        this.source = source;
        this.industry = industry;
        this.gender = gender;
        this.country = country;
        this.category = category;
        this.earnings = earnings;
        this.squareImage = squareImage;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getEarnings() {
        return earnings;
    }

    public void setEarnings(Double earnings) {
        this.earnings = earnings;
    }

    public String getSquareImage() {
        return squareImage;
    }

    public void setSquareImage(String squareImage) {
        this.squareImage = squareImage;
    }

    public String getImageUrl() {
        return "https" + squareImage;
    }
}