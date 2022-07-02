package com.example.application.views;

import com.example.application.dto.WeatherActualReturn;
import com.example.application.dto.WeatherForecastReturn;
import com.example.application.service.WeatherService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.map.Map;
import com.vaadin.flow.component.map.configuration.Coordinate;
import com.vaadin.flow.component.map.configuration.feature.MarkerFeature;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.dto.Skiresort;
import com.example.application.model.Item;
import com.example.application.service.RestSkiresortService;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMap;
import com.flowingcode.vaadin.addons.googlemaps.LatLon;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@PageTitle("Result")
@Route(value = "single", layout = MainLayout.class)
public class SingleResultView extends VerticalLayout implements HasUrlParameter<Long>{



	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");

	private Skiresort item;
	private H1 title;
	private Image image;

	private List<VerticalLayout> imgWeathers = new ArrayList();
	private Image imgMaps;

	private HorizontalLayout hlAltValley;
	private HorizontalLayout hlAltMountain;
	private HorizontalLayout hlNumOfCogRail;
	private HorizontalLayout hlNumOfFunicular;
	private HorizontalLayout hlNumOfCableCar;
	private HorizontalLayout hlNumOfGondolLift;
	private HorizontalLayout hlNumOfChairLift;
	private HorizontalLayout hlNumOfTBarLift;
	private HorizontalLayout hlNumOfBabyLift;
	private HorizontalLayout hlNumOfMovingCarpet;
	private HorizontalLayout hlDistanceEasy;
	private HorizontalLayout hlDistanceIntermediate;
	private HorizontalLayout hlDistanceDifficult;
	private Paragraph pGeneralSnowCOnditions;
	private HorizontalLayout hlnumberOfRestaurants;
	private HorizontalLayout hlWebCamUrl;
	private HorizontalLayout hlWebSiteUrl;
	private HorizontalLayout hlWeatherActualUrl;
	private HorizontalLayout hlWeatherForecastUrl;

	private HorizontalLayout hlExtras;
	private Checkbox chkSkiRental;
	private Checkbox chkSkiCourse;

	private Checkbox chkFamilyFriendly;

	private HorizontalLayout hlPriceDayTicketAdults;
	private HorizontalLayout hlPriceDayTicketYouth;
	private HorizontalLayout hlPriceDayTicketChildren;

	private HorizontalLayout hlSeasson;
	private Span dtSeasonFrom;
	private Span dtSeasonTo;

	private HorizontalLayout hlHours;
	private Span tOpeningHoursFrom;
	private Span tOpeningHoursTo;
	private Paragraph pOpeningHoursNote;

	private Paragraph pRemark;
	private Paragraph pDescription;

	private HorizontalLayout hlWeather;

	private VerticalLayout vlMaps;

	private GoogleMap gmaps;

	private Map map;

	private MarkerFeature skiResort;

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

//		imgWeather = new Image();
//		imgWeather.setMaxWidth("100%");

		imgMaps = new Image();
		imgMaps.setMaxWidth("100%");


		hlAltValley = new HorizontalLayout(new Span("Alt Valley:"));
		hlAltMountain = new HorizontalLayout(new Span("Alt Mountain:"));
		hlNumOfCogRail  = new HorizontalLayout(new Span("Number of Cog Rail:"));
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
		pGeneralSnowCOnditions = new Paragraph(new Span("General snow conditions:"));
		hlnumberOfRestaurants = new HorizontalLayout(new Span("Number of restaurants:"));
		hlWebCamUrl = new HorizontalLayout(new Span("Webcam url:"));
		hlWebSiteUrl = new HorizontalLayout(new Span("Website url:"));

		hlWeatherActualUrl = new HorizontalLayout(new Span("Actual weather url:"));
		hlWeatherForecastUrl = new HorizontalLayout(new Span("Weather forecast url:"));

		hlExtras = new HorizontalLayout();
		chkSkiRental = new Checkbox("Ski rentals");
		chkSkiCourse = new Checkbox("Ski course");
		chkFamilyFriendly = new Checkbox("Family friendly");

		hlExtras.add(chkSkiRental, chkSkiCourse, chkFamilyFriendly);

		hlPriceDayTicketAdults = new HorizontalLayout(new Span("Price day ticket adults:"));
		hlPriceDayTicketYouth = new HorizontalLayout(new Span("Price day ticket youth:"));
		hlPriceDayTicketChildren = new HorizontalLayout(new Span("Price day ticket children:"));

		hlSeasson = new HorizontalLayout();
		dtSeasonFrom = new Span("Season from:");
		dtSeasonTo = new Span("Season to:");

		hlSeasson.add(dtSeasonFrom, dtSeasonTo);

		hlHours = new HorizontalLayout();
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

		hlWeather = new HorizontalLayout();

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
//			imgWeather.setMaxWidth("100%");
			imgWeathers.add(vlWeather);
		}

		vlMaps = new VerticalLayout(new H1("Maps"));
		vlMaps.setSizeFull();
		vlMaps.setHeight("600px");

		map = new Map();

		add(title, image, hlAltValley, hlAltMountain, hlNumOfCogRail, hlNumOfFunicular, hlNumOfCableCar, hlNumOfGondolLift, hlNumOfChairLift, hlNumOfTBarLift, hlNumOfBabyLift, hlNumOfMovingCarpet, hlDistanceEasy, hlDistanceIntermediate,
				hlDistanceDifficult, pGeneralSnowCOnditions, hlWebCamUrl, hlWebSiteUrl, hlExtras, hlPriceDayTicketAdults, hlPriceDayTicketYouth,
				hlPriceDayTicketChildren, hlSeasson, hlHours, pOpeningHoursNote, pRemark, pDescription, weatherHeader, hlWeather, vlMaps);

	}

	@Override
	public void setParameter(BeforeEvent event,
							 Long parameter) {
		if(parameter!=null){
			item = restSkiresortService.getOne(parameter);
			if(item!=null) {
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
				pGeneralSnowCOnditions.add(item.getGeneralSnowCondition());
				hlnumberOfRestaurants.add(item.getNumberOfRestaurants().toString());
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
				if(item.getPriceDayTicketYouth() == null){
					hlPriceDayTicketYouth.add("Not Available");

				}else {
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


				Coordinate coordinate = Coordinate.fromLonLat(item.getLongitude().doubleValue(), item.getLatitude().doubleValue());
				map.setCenter(coordinate);
				skiResort = new MarkerFeature(coordinate);
				map.getFeatureLayer().addFeature(skiResort);
				map.setZoom(12);
				map.addFeatureClickListener(e->{
					Notification.show(item.getDescription()).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
				});

				vlMaps.add(map);

				WeatherForecastReturn forecastWeather = weatherService.getWeatherForecast(item);

				for(int i = 0; i < 10; i++) {
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
		}else
			UI.getCurrent().navigate(ResultsView.class);
	}

}
