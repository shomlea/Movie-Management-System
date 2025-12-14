package com.example.movie_management_system.dto;

public class TheatreFilterDto {

    private String nameQuery;
    private String cityQuery;

    private Integer minParkingCapacity;
    private Integer maxParkingCapacity;


    public String getNameQuery() {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery) {
        this.nameQuery = nameQuery;
    }

    public String getCityQuery() {
        return cityQuery;
    }

    public void setCityQuery(String cityQuery) {
        this.cityQuery = cityQuery;
    }

    public Integer getMinParkingCapacity() {
        return minParkingCapacity;
    }

    public void setMinParkingCapacity(Integer minParkingCapacity) {
        this.minParkingCapacity = minParkingCapacity;
    }

    public Integer getMaxParkingCapacity() {
        return maxParkingCapacity;
    }

    public void setMaxParkingCapacity(Integer maxParkingCapacity) {
        this.maxParkingCapacity = maxParkingCapacity;
    }

    public boolean isEmpty() {
        return (nameQuery == null || nameQuery.trim().isEmpty()) &&
                (cityQuery == null || cityQuery.trim().isEmpty()) &&
                minParkingCapacity == null &&
                maxParkingCapacity == null;
    }
}