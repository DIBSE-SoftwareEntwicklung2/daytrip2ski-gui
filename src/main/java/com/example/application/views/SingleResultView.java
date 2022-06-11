package com.example.application.views;

import com.example.application.model.Item;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Result")
@Route(value = "single", layout = MainLayout.class)
public class SingleResultView extends VerticalLayout implements HasUrlParameter<Integer>{
    
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Item item;
	private H1 title;
	private Image image;
	private HorizontalLayout hlTraffic;
	private HorizontalLayout hlDistance;
	private HorizontalLayout hlSlopes;
	private HorizontalLayout hlSlopeKm;
	private HorizontalLayout hlCosts;
	private Image imgWheather;
	private Image imgMaps;
	
	
	public SingleResultView() {
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		title = new H1();
		image = new Image();
		image.setMaxWidth("100%");
		
		imgWheather = new Image();
		imgWheather.setMaxWidth("100%");
		
		imgMaps = new Image();
		imgMaps.setMaxWidth("100%");
		
		hlTraffic = new HorizontalLayout(new Span("Traffic"));
		hlDistance = new HorizontalLayout(new Span("Distance"));
		hlSlopes = new HorizontalLayout(new Span("Slopes"));
		hlSlopeKm = new HorizontalLayout(new Span("Kilometers of slopes"));
		hlCosts = new HorizontalLayout(new Span("Costs"));
		
		add(title, image, hlTraffic, hlSlopes, hlSlopeKm, hlCosts, imgWheather, imgMaps);
		
	}
	
	
	
	

	@Override
    public void setParameter(BeforeEvent event,
                             Integer parameter) {
        if(parameter!=null){
        	MainLayout.getDestinations().forEach(i->{
        		if(i.getId()==parameter)
        			item = i;
        	});
        	if(item!=null) {
        		title.add(item.getName());
        		hlTraffic.add(item.getTraffic());
        		hlDistance.add(item.getDistance());
        		item.getSlopes().entrySet().forEach(s->{
        			hlSlopes.add("  " + s.getKey() + ":" + s.getValue());
        		});
        		
        		hlSlopeKm.add(item.getDistance());
        		hlCosts.add(item.getTicketPrice() + "€");
        		image.setSrc("images/"+item.getImage());
        		imgWheather.setSrc("images/wheather.jpg");
        		imgMaps.setSrc("images/maps.jpg");
        	}
        }
    }
	
}
