package com.example.application.windows;

import org.vaadin.addons.PaperSlider;

import com.example.application.utils.CustomTabs;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


@CssImport("./styles/windows/searchWindow.css")
public class SearchWindow extends Dialog{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3745835111251126118L;
	
    private Div content;
    private Button ok;
    private Button nok;
    private CustomTabs tabbed;
    private VerticalLayout vlArrival;
    private VerticalLayout vlCosts;
    private VerticalLayout vlWheather;
    private VerticalLayout vlSlopes;
    private VerticalLayout vlCapacity;
    private VerticalLayout vlSpecials;

    public SearchWindow(){
        
        setHeaderTitle("Search window");
       
        content = new Div();
        content.setClassName("search-window");
        
        tabbed = new CustomTabs();
        vlArrival = new VerticalLayout();
        vlCosts = new VerticalLayout();
        vlWheather = new VerticalLayout();
        vlSlopes = new VerticalLayout();
        vlCapacity = new VerticalLayout();
        vlSpecials = new VerticalLayout();
        
        PaperSlider arrivalSlider = new PaperSlider(100); 
        arrivalSlider.setLabel("Distance");
        arrivalSlider.showValues();
        arrivalSlider.setMax(100);
        arrivalSlider.setMin(5);
        vlArrival.add(arrivalSlider);
        
        Span spnTraffic = new Span("Traffic");
        Checkbox chkLow = new Checkbox("Low");
        Checkbox chkMedium = new Checkbox("Medium");
        Checkbox chkHigh = new Checkbox("High");
        vlArrival.add(spnTraffic, chkLow, chkMedium, chkHigh);
        
        PaperSlider costsSlider = new PaperSlider(100); 
        costsSlider.setLabel("Daily costs /pP €");
        costsSlider.showValues();
        costsSlider.setMax(150);
        costsSlider.setMin(25);
        vlCosts.add(costsSlider);
        
        Span spnValidTickets = new Span("Valid season tickets");
        Checkbox chkSnowTirol = new Checkbox("Snow card Tirol");
        Checkbox chkSuperSkiCard = new Checkbox("SuperSkiCard Salzburg");
        Checkbox chkFreizeitticket = new Checkbox("Freizeitticket Tirol");
        Checkbox chkKarnten = new Checkbox("TopSkiPass Karnten & Osttirol");
        
        vlCosts.add(spnValidTickets, chkSnowTirol, chkSuperSkiCard, chkFreizeitticket, chkKarnten);
        
        PaperSlider slopesSlider = new PaperSlider(100); 
        slopesSlider.setLabel("Kilometers of slopes");
        slopesSlider.showValues();
        slopesSlider.setMax(150);
        slopesSlider.setMin(25);
        vlSlopes.add(slopesSlider);
        
        Span spnLevels = new Span("Levels");
        Checkbox chkGreen = new Checkbox("Green");
        Checkbox chkBlue = new Checkbox("Blue");
        Checkbox chkRed = new Checkbox("Red");
        Checkbox chkBlack = new Checkbox("Black");
        
        Span spnConditions = new Span("Slope conditions");
        Checkbox chkIcy = new Checkbox("Icy");
        Checkbox chkGrippy = new Checkbox("Grippy");
        Checkbox chkPowder = new Checkbox("Powder");
        Checkbox chkFreshly = new Checkbox("Freshly prepared");
        Checkbox chkBump = new Checkbox("Bump");
        Checkbox chkSlushy = new Checkbox("Slushy");
        
        vlSlopes.add(spnLevels, chkGreen, chkBlue, chkRed, chkBlack, spnConditions, chkIcy, chkGrippy, chkPowder, chkFreshly, chkBump, chkSlushy);
        
        PaperSlider capacitySlider = new PaperSlider(50); 
        capacitySlider.setLabel("Capacity");
        capacitySlider.showValues();
        capacitySlider.setMax(100);
        capacitySlider.setMin(0);
        vlCapacity.add(capacitySlider);
        
        Checkbox chkApresski = new Checkbox("Apresski");
        Checkbox chkKids = new Checkbox("Kidsarea");
        Checkbox chkSkiSchool = new Checkbox("Skiing School");
        Checkbox chkRental = new Checkbox("Skirental");
        Checkbox chkFun = new Checkbox("Funpark");
        Checkbox chkTour = new Checkbox("Skitour");
        Checkbox chkGastronomy = new Checkbox("Gastronomy");
        Checkbox chkNight = new Checkbox("Nigh Skiing");
        
        vlSpecials.add(chkApresski, chkKids, chkSkiSchool, chkRental, chkFun, chkTour, chkGastronomy, chkNight);
        
        tabbed.add("Arrival", vlArrival);
        tabbed.add("Costs", vlCosts);
        tabbed.add("Wheather", vlWheather);
        tabbed.add("Slopes", vlSlopes);
        tabbed.add("Capacity", vlCapacity);
        tabbed.add("Specials", vlSpecials);
        
        ok = new Button("Search");
        nok = new Button("Cancel");

        nok.addClickListener(e->{
            close();
        });

        getFooter().add(ok, nok);

        add(tabbed);
    }
    
}
