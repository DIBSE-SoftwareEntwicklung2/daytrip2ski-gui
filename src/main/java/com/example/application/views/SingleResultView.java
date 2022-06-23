package com.example.application.views;

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
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Result")
@Route(value = "single", layout = MainLayout.class)
public class SingleResultView extends VerticalLayout implements HasUrlParameter<Long>{



	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Skiresort item;
	private H1 title;
	private Image image;
	private HorizontalLayout hlTraffic;
	private HorizontalLayout hlDistance;
	private HorizontalLayout hlSlopes;
	private HorizontalLayout hlSlopeKm;
	private HorizontalLayout hlCosts;
	private Image imgWeather;
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

	private VerticalLayout vlMaps;

	private GoogleMap gmaps;

	private Map map;

	private MarkerFeature skiResort;

	@Autowired
	private RestSkiresortService restSkiresortService;


	public SingleResultView() {
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		title = new H1();
		image = new Image();
		image.setMaxWidth("100%");

		imgWeather = new Image();
		imgWeather.setMaxWidth("100%");

		imgMaps = new Image();
		imgMaps.setMaxWidth("100%");

		hlTraffic = new HorizontalLayout(new Span("Traffic"));
		hlDistance = new HorizontalLayout(new Span("Distance"));
		hlSlopes = new HorizontalLayout(new Span("Slopes"));
		hlSlopeKm = new HorizontalLayout(new Span("Kilometers of slopes"));
		hlCosts = new HorizontalLayout(new Span("Costs"));

		vlMaps = new VerticalLayout(new H1("Maps"));
		vlMaps.setSizeFull();
		vlMaps.setHeight("600px");

		map = new Map();

		add(title, image, hlTraffic, hlSlopes, hlSlopeKm, hlCosts, imgWeather, vlMaps);

	}





	@Override
	public void setParameter(BeforeEvent event,
							 Long parameter) {
		if(parameter!=null){
			item = restSkiresortService.getOne(parameter);
			if(item!=null) {
				title.add(item.getName());
				hlTraffic.add(item.getNumberOfCogRailway().toString());
				hlDistance.add(item.getDistanceEasy().toString());


				hlSlopeKm.add(item.getNumberOfFunicular().toString());
				hlCosts.add(item.getPriceDayTicketAdults() + "€");
				image.setSrc("images/Soelden.jpg");
				imgWeather.setSrc("images/wheather.jpg");



				Coordinate coordinate = Coordinate.fromLonLat(item.getLongitude().doubleValue(), item.getLatitude().doubleValue());
				map.setCenter(coordinate);
				skiResort = new MarkerFeature(coordinate);
				map.getFeatureLayer().addFeature(skiResort);
				map.setZoom(12);
				map.addFeatureClickListener(e->{
					Notification.show(item.getDescription()).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
				});

				vlMaps.add(map);

			}
		}
	}

}
