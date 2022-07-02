package com.example.application.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Skiresort {
    private Long id;
    private String name;

    private Long latitude;
    private Long longitude;
    private Long altitudeValley;
    private Long altitudeMountain;

    private Long numberOfCogRailway;
    private Long numberOfFunicular;
    private Long numberOfCableCar;
    private Long numberOfGondolaLift;
    private Long numberOfChairLift;
    private Long numberOfTBarLift;
    private Long numberOfBabyLift;
    private Long numberOfMovingCarpet;

    private Long distanceEasy;
    private Long distanceIntermediate;
    private Long distanceDifficult;

    private String generalSnowCondition;
    private Long numberOfRestaurants;

    private String webcamUrl;
    private String websiteUrl;

    private String weatherActualUrl;
    private String weatherForecastUrl;

    private Boolean skiRental;
    private Boolean skiCourse;

    private Boolean familyFriendly;

    private Double priceDayTicketAdults;
    private Double priceDayTicketYouth;
    private Double priceDayTicketChildren;

    private LocalDate seasonFrom;
    private LocalDate seasonTo;

    private LocalTime openingHoursFrom;
    private LocalTime openingHoursTo;
    private String openingHoursNote;

    private String remark;
    private String description;

    private Boolean isActive;

    private Integer score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getAltitudeValley() {
        return altitudeValley;
    }

    public void setAltitudeValley(Long altitudeValley) {
        this.altitudeValley = altitudeValley;
    }

    public Long getAltitudeMountain() {
        return altitudeMountain;
    }

    public void setAltitudeMountain(Long altitudeMountain) {
        this.altitudeMountain = altitudeMountain;
    }

    public Long getNumberOfCogRailway() {
        return numberOfCogRailway;
    }

    public void setNumberOfCogRailway(Long numberOfCogRailway) {
        this.numberOfCogRailway = numberOfCogRailway;
    }

    public Long getNumberOfFunicular() {
        return numberOfFunicular;
    }

    public void setNumberOfFunicular(Long numberOfFunicular) {
        this.numberOfFunicular = numberOfFunicular;
    }

    public Long getNumberOfCableCar() {
        return numberOfCableCar;
    }

    public void setNumberOfCableCar(Long numberOfCableCar) {
        this.numberOfCableCar = numberOfCableCar;
    }

    public Long getNumberOfGondolaLift() {
        return numberOfGondolaLift;
    }

    public void setNumberOfGondolaLift(Long numberOfGondolaLift) {
        this.numberOfGondolaLift = numberOfGondolaLift;
    }

    public Long getNumberOfChairLift() {
        return numberOfChairLift;
    }

    public void setNumberOfChairLift(Long numberOfChairLift) {
        this.numberOfChairLift = numberOfChairLift;
    }

    public Long getNumberOfTBarLift() {
        return numberOfTBarLift;
    }

    public void setNumberOfTBarLift(Long numberOfTBarLift) {
        this.numberOfTBarLift = numberOfTBarLift;
    }

    public Long getNumberOfBabyLift() {
        return numberOfBabyLift;
    }

    public void setNumberOfBabyLift(Long numberOfBabyLift) {
        this.numberOfBabyLift = numberOfBabyLift;
    }

    public Long getNumberOfMovingCarpet() {
        return numberOfMovingCarpet;
    }

    public void setNumberOfMovingCarpet(Long numberOfMovingCarpet) {
        this.numberOfMovingCarpet = numberOfMovingCarpet;
    }

    public Long getDistanceEasy() {
        return distanceEasy;
    }

    public void setDistanceEasy(Long distanceEasy) {
        this.distanceEasy = distanceEasy;
    }

    public Long getDistanceIntermediate() {
        return distanceIntermediate;
    }

    public void setDistanceIntermediate(Long distanceIntermediate) {
        this.distanceIntermediate = distanceIntermediate;
    }

    public Long getDistanceDifficult() {
        return distanceDifficult;
    }

    public void setDistanceDifficult(Long distanceDifficult) {
        this.distanceDifficult = distanceDifficult;
    }

    public String getGeneralSnowCondition() {
        return generalSnowCondition;
    }

    public void setGeneralSnowCondition(String generalSnowCondition) {
        this.generalSnowCondition = generalSnowCondition;
    }

    public Long getNumberOfRestaurants() {
        return numberOfRestaurants;
    }

    public void setNumberOfRestaurants(Long numberOfRestaurants) {
        this.numberOfRestaurants = numberOfRestaurants;
    }

    public String getWebcamUrl() {
        return webcamUrl;
    }

    public void setWebcamUrl(String webcamUrl) {
        this.webcamUrl = webcamUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWeatherActualUrl() {
        return weatherActualUrl;
    }

    public void setWeatherActualUrl(String weatherActualUrl) {
        this.weatherActualUrl = weatherActualUrl;
    }

    public String getWeatherForecastUrl() {
        return weatherForecastUrl;
    }

    public void setWeatherForecastUrl(String weatherForecastUrl) {
        this.weatherForecastUrl = weatherForecastUrl;
    }

    public Boolean getSkiRental() {
        return skiRental;
    }

    public void setSkiRental(Boolean skiRental) {
        this.skiRental = skiRental;
    }

    public Boolean getSkiCourse() {
        return skiCourse;
    }

    public void setSkiCourse(Boolean skiCourse) {
        this.skiCourse = skiCourse;
    }

    public Boolean getFamilyFriendly() {
        return familyFriendly;
    }

    public void setFamilyFriendly(Boolean familyFriendly) {
        this.familyFriendly = familyFriendly;
    }

    public Double getPriceDayTicketAdults() {
        return priceDayTicketAdults;
    }

    public void setPriceDayTicketAdults(Double priceDayTicketAdults) {
        this.priceDayTicketAdults = priceDayTicketAdults;
    }

    public Double getPriceDayTicketYouth() {
        return priceDayTicketYouth;
    }

    public void setPriceDayTicketYouth(Double priceDayTicketYouth) {
        this.priceDayTicketYouth = priceDayTicketYouth;
    }

    public Double getPriceDayTicketChildren() {
        return priceDayTicketChildren;
    }

    public void setPriceDayTicketChildren(Double priceDayTicketChildren) {
        this.priceDayTicketChildren = priceDayTicketChildren;
    }

    public LocalDate getSeasonFrom() {
        return seasonFrom;
    }

    public void setSeasonFrom(LocalDate seasonFrom) {
        this.seasonFrom = seasonFrom;
    }

    public LocalDate getSeasonTo() {
        return seasonTo;
    }

    public void setSeasonTo(LocalDate seasonTo) {
        this.seasonTo = seasonTo;
    }

    public LocalTime getOpeningHoursFrom() {
        return openingHoursFrom;
    }

    public void setOpeningHoursFrom(LocalTime openingHoursFrom) {
        this.openingHoursFrom = openingHoursFrom;
    }

    public LocalTime getOpeningHoursTo() {
        return openingHoursTo;
    }

    public void setOpeningHoursTo(LocalTime openingHoursTo) {
        this.openingHoursTo = openingHoursTo;
    }

    public String getOpeningHoursNote() {
        return openingHoursNote;
    }

    public void setOpeningHoursNote(String openingHoursNote) {
        this.openingHoursNote = openingHoursNote;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}