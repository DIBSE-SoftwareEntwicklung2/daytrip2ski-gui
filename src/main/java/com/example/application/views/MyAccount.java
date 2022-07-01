package com.example.application.views;

import org.vaadin.addons.PaperSlider;

import com.example.application.utils.CustomTabs;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("My account")
@Route(value = "account", layout = MainLayout.class)
public class MyAccount extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TextField tfID;
	private TextField tfName;
	private TextField tfLastName;
	private TextField tfEmail;
	
	private CustomTabs tabbed;
    private VerticalLayout vlArrival;
    private VerticalLayout vlCosts;
    private VerticalLayout vlWeather;
    private VerticalLayout vlSlopes;
    private VerticalLayout vlCapacity;
    private VerticalLayout vlSpecials;
	
    private Button btnSave;
    
	public MyAccount() {
		
		tfID = new TextField("ID");
		tfID.setEnabled(false);
		add(tfID);
		
		tfName = new TextField("Name");
		tfName.setEnabled(false);
		add(tfName);
		
		tfLastName = new TextField("Last name");
		tfLastName.setEnabled(false);
		add(tfLastName);
		
		tfEmail = new TextField("Email");
		tfEmail.setEnabled(false);
		add(tfEmail);
		
		btnSave = new Button("Save");
		
		tabbed = new CustomTabs();
		
		//creating vertical layouts for elements in tabs
        vlArrival = new VerticalLayout();
        vlCosts = new VerticalLayout();
        vlWeather = new VerticalLayout();
        vlSlopes = new VerticalLayout();
        vlCapacity = new VerticalLayout();
        vlSpecials = new VerticalLayout();
        
        //creating elements for each tab
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
        tabbed.add("Weather", vlWeather);
        tabbed.add("Slopes", vlSlopes);
        tabbed.add("Capacity", vlCapacity);
        tabbed.add("Specials", vlSpecials);
        
        add(new H2("Custom filters"));
        add(tabbed);
		add(btnSave);
	}
	
}
