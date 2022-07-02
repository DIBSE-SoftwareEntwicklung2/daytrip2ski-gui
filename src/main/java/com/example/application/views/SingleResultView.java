package com.example.application.views;

import com.example.application.dto.WeatherForecastReturn;
import com.example.application.service.WeatherService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.map.Map;
import com.vaadin.flow.component.map.configuration.Coordinate;
import com.vaadin.flow.component.map.configuration.feature.MarkerFeature;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.service.RestSkiresortService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@PageTitle("Result")
@Route(value = "single", layout = MainLayout.class)
public class SingleResultView extends VerticalLayout implements HasUrlParameter<Long> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");
    private final H1 title;
    private final Image image;
    private final ArrayList<VerticalLayout> imgWeathers = new ArrayList<>();
    private final HorizontalLayout hlAltValley;
    private final HorizontalLayout hlAltMountain;
    private final HorizontalLayout hlNumOfCogRail;
    private final HorizontalLayout hlNumOfFunicular;
    private final HorizontalLayout hlNumOfCableCar;
    private final HorizontalLayout hlNumOfGondolLift;
    private final HorizontalLayout hlNumOfChairLift;
    private final HorizontalLayout hlNumOfTBarLift;
    private final HorizontalLayout hlNumOfBabyLift;
    private final HorizontalLayout hlNumOfMovingCarpet;
    private final HorizontalLayout hlDistanceEasy;
    private final HorizontalLayout hlDistanceIntermediate;
    private final HorizontalLayout hlDistanceDifficult;
    private final Paragraph pGeneralSnowConditions;
    private final HorizontalLayout hlNumberOfRestaurants;
    private final HorizontalLayout hlWebCamUrl;
    private final HorizontalLayout hlWebSiteUrl;
    private final HorizontalLayout hlWeatherActualUrl;
    private final HorizontalLayout hlWeatherForecastUrl;
    private final Checkbox chkSkiRental;
    private final Checkbox chkSkiCourse;
    private final Checkbox chkFamilyFriendly;
    private final HorizontalLayout hlPriceDayTicketAdults;
    private final HorizontalLayout hlPriceDayTicketYouth;
    private final HorizontalLayout hlPriceDayTicketChildren;
    private final Span dtSeasonFrom;
    private final Span dtSeasonTo;
    private final Span tOpeningHoursFrom;
    private final Span tOpeningHoursTo;
    private final Paragraph pOpeningHoursNote;
    private final Paragraph pRemark;
    private final Paragraph pDescription;
    private final VerticalLayout vlMaps;
    private final Map map;

    @Autowired
    private RestSkiresortService restSkiresortService;

    @Autowired
    private WeatherService weatherService;

    public SingleResultView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        title = new H1();
        image = new Image();
        image.setMaxWidth("100%");

        Image imgMaps = new Image();
        imgMaps.setMaxWidth("100%");

        hlAltValley = new HorizontalLayout(new Span("Alt Valley:"));
        hlAltMountain = new HorizontalLayout(new Span("Alt Mountain:"));
        hlNumOfCogRail = new HorizontalLayout(new Span("Number of Cog Rail:"));
        hlNumOfFunicular = new HorizontalLayout(new Span("Number of Funicular:"));
        hlNumOfCableCar = new HorizontalLayout(new Span("Number of Cable Car:"));
        hlNumOfGondolLift = new HorizontalLayout(new Span("Number of Gondol Lift:"));
        hlNumOfChairLift = new HorizontalLayout(new Span("Number of Chair Lift:"));
        hlNumOfTBarLift = new HorizontalLayout(new Span("Number of T Bar Lift:"));
        hlNumOfBabyLift = new HorizontalLayout(new Span("Number of Baby Lift:"));
        hlNumOfMovingCarpet = new HorizontalLayout(new Span("Number of Moving Carpet:"));
        hlDistanceEasy = new HorizontalLayout(new Span("Distance easy:"));
        hlDistanceIntermediate = new HorizontalLayout(new Span("Distance intermediate:"));
        hlDistanceDifficult = new HorizontalLayout(new Span("Distance difficult:"));
        pGeneralSnowConditions = new Paragraph(new Span("General snow conditions:"));
        hlNumberOfRestaurants = new HorizontalLayout(new Span("Number of restaurants:"));
        hlWebCamUrl = new HorizontalLayout(new Span("Webcam url:"));
        hlWebSiteUrl = new HorizontalLayout(new Span("Website url:"));

        hlWeatherActualUrl = new HorizontalLayout(new Span("Actual weather url:"));
        hlWeatherForecastUrl = new HorizontalLayout(new Span("Weather forecast url:"));

        HorizontalLayout hlExtras = new HorizontalLayout();
        chkSkiRental = new Checkbox("Ski rentals");
        chkSkiCourse = new Checkbox("Ski course");
        chkFamilyFriendly = new Checkbox("Family friendly");

        hlExtras.add(chkSkiRental, chkSkiCourse, chkFamilyFriendly);

        hlPriceDayTicketAdults = new HorizontalLayout(new Span("Price day ticket adults:"));
        hlPriceDayTicketYouth = new HorizontalLayout(new Span("Price day ticket youth:"));
        hlPriceDayTicketChildren = new HorizontalLayout(new Span("Price day ticket children:"));

        HorizontalLayout hlSeasson = new HorizontalLayout();
        dtSeasonFrom = new Span("Season from:");
        dtSeasonTo = new Span("Season to:");

        hlSeasson.add(dtSeasonFrom, dtSeasonTo);

        HorizontalLayout hlHours = new HorizontalLayout();
        tOpeningHoursFrom = new Span("Open from:");
        tOpeningHoursTo = new Span("Open to:");
        hlHours.add(tOpeningHoursFrom, tOpeningHoursTo);

        pOpeningHoursNote = new Paragraph("Hours note:");
        pOpeningHoursNote.setMaxWidth("500px");

        pRemark = new Paragraph("Remark:");
        pDescription = new Paragraph("Description:");
        pDescription.setMaxWidth("500px");

        Paragraph weatherHeader = new Paragraph("Next 10 days:");
        weatherHeader.getStyle().set("font-weight", "bold");
        weatherHeader.getStyle().set("border", "1px solid #ACACAC");
        weatherHeader.getStyle().set("background-color", "#F2F2F2");
        weatherHeader.getStyle().set("padding", "8px");
        weatherHeader.setWidth("100%");

        HorizontalLayout hlWeather = new HorizontalLayout();

        for (int i = 0; i < 10; i++) {
            VerticalLayout vlWeather = new VerticalLayout();
            vlWeather.setSpacing(false);
            vlWeather.setAlignItems(Alignment.CENTER);
            vlWeather.setJustifyContentMode(JustifyContentMode.CENTER);
            vlWeather.add(new Span());

            Image imgWeather = new Image();
            imgWeather.setWidth("75px");
            vlWeather.add(imgWeather);

            vlWeather.add(new Span());

            Span min = new Span();
            min.getStyle().set("font-size", " 11px");
            Span max = new Span();
            max.getStyle().set("font-size", " 11px");
            vlWeather.add(min);
            vlWeather.add(max);
            hlWeather.add(vlWeather);
            imgWeathers.add(vlWeather);
        }

        vlMaps = new VerticalLayout(new H1("Maps"));
        vlMaps.setSizeFull();
        vlMaps.setHeight("600px");

        map = new Map();

        add(title, image, hlAltValley, hlAltMountain, hlNumOfCogRail, hlNumOfFunicular, hlNumOfCableCar, hlNumOfGondolLift, hlNumOfChairLift, hlNumOfTBarLift, hlNumOfBabyLift, hlNumOfMovingCarpet, hlDistanceEasy, hlDistanceIntermediate, hlDistanceDifficult, pGeneralSnowConditions, hlWebCamUrl, hlWebSiteUrl, hlExtras, hlPriceDayTicketAdults, hlPriceDayTicketYouth, hlPriceDayTicketChildren, hlSeasson, hlHours, pOpeningHoursNote, pRemark, pDescription, weatherHeader, hlWeather, vlMaps);
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        if (parameter != null) {
            var item = restSkiresortService.getOne(parameter);
            if (item != null) {
                title.add(item.getName());

                image.setSrc("images/" + item.getId() + ".jpeg");

                hlAltValley.add(item.getAltitudeValley().toString());
                hlAltMountain.add(item.getAltitudeMountain().toString());
                hlNumOfCogRail.add(item.getNumberOfCogRailway().toString());
                hlNumOfFunicular.add(item.getNumberOfFunicular().toString());
                hlNumOfCableCar.add(item.getNumberOfCableCar().toString());
                hlNumOfGondolLift.add(item.getNumberOfGondolaLift().toString());
                hlNumOfChairLift.add(item.getNumberOfChairLift().toString());
                hlNumOfTBarLift.add(item.getNumberOfTBarLift().toString());
                hlNumOfBabyLift.add(item.getNumberOfBabyLift().toString());
                hlNumOfMovingCarpet.add(item.getNumberOfMovingCarpet().toString());
                hlDistanceEasy.add(item.getDistanceEasy().toString());
                hlDistanceIntermediate.add(item.getDistanceIntermediate().toString());
                hlDistanceDifficult.add(item.getDistanceDifficult().toString());
                pGeneralSnowConditions.add(item.getGeneralSnowCondition());
                hlNumberOfRestaurants.add(item.getNumberOfRestaurants().toString());
                hlWebCamUrl.add(item.getWebcamUrl());
                hlWebSiteUrl.add(item.getWebsiteUrl());
                hlWeatherActualUrl.add(item.getWeatherActualUrl());
                hlWeatherForecastUrl.add(item.getWeatherForecastUrl());

                chkSkiRental.setValue(item.getSkiRental());
                chkSkiRental.setEnabled(false);
                chkSkiCourse.setValue(item.getSkiCourse());
                chkSkiCourse.setEnabled(false);
                chkFamilyFriendly.setValue(item.getFamilyFriendly());
                chkFamilyFriendly.setEnabled(false);

                hlPriceDayTicketAdults.add(item.getPriceDayTicketAdults().toString());
                if (item.getPriceDayTicketYouth() == null) {
                    hlPriceDayTicketYouth.add("Not Available");
                } else {
                    hlPriceDayTicketYouth.add(item.getPriceDayTicketYouth().toString());
                }
                hlPriceDayTicketChildren.add(item.getPriceDayTicketChildren().toString());

                dtSeasonFrom.add(item.getSeasonFrom().toString());
                dtSeasonTo.add(item.getSeasonTo().toString());
                tOpeningHoursFrom.add(item.getOpeningHoursFrom().toString());
                tOpeningHoursTo.add(item.getOpeningHoursTo().toString());
                pOpeningHoursNote.add(item.getOpeningHoursNote());
                pRemark.add(item.getRemark());
                pDescription.add(item.getDescription());

                Coordinate coordinate = Coordinate.fromLonLat(item.getLongitude(), item.getLatitude());
                map.setCenter(coordinate);
                MarkerFeature skiResort = new MarkerFeature(coordinate);
                map.getFeatureLayer().addFeature(skiResort);
                map.setZoom(12);
                map.addFeatureClickListener(e -> Notification.show(item.getDescription()).addThemeVariants(NotificationVariant.LUMO_SUCCESS));
                vlMaps.add(map);

                WeatherForecastReturn forecastWeather = weatherService.getWeatherForecast(item);
                for (int i = 0; i < 10; i++) {
                    Object[] childElements = imgWeathers.get(i).getChildren().toArray();
                    com.example.application.dto.apireturn.List weather = forecastWeather.list.get(i);
                    System.out.println(new Date(weather.dt * 1000));
                    ((Span) childElements[0]).setText(simpleDateFormat.format(new Date(weather.dt * 1000)));
                    ((Image) childElements[1]).setSrc("https://openweathermap.org/img/wn/" + weather.weather.get(0).icon + "@2x.png");
                    ((Image) childElements[1]).setTitle(weather.weather.get(0).description);
                    ((Span) childElements[2]).setText(weather.feels_like.day + "°C");
                    ((Span) childElements[3]).setText("Min. " + weather.temp.min + "°C");
                    ((Span) childElements[4]).setText("Max. " + weather.temp.max + "°C");
                }
            }
        } else {
            UI.getCurrent().navigate(ResultsView.class);
        }
    }
}
