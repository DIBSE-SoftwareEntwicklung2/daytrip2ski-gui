package com.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "homeView")
@RouteAlias(value = "")
@CssImport("./styles/views/homeView.css")
@PageTitle("Home")
public class HomeView extends VerticalLayout{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1115700137807593311L;
	
	private Div form;
    private Button btnWithAccount;
    private Button btnWoAccount;
    private Button registration;
    private Image logo;

    public HomeView(){

        setSizeFull();
		setAlignItems(Alignment.CENTER); 
		setJustifyContentMode(JustifyContentMode.CENTER);
        setClassName("home-form");
        form = new Div();
        form.setClassName("form-center-bottom");
        btnWithAccount = new Button("With account");
        btnWithAccount.addClickListener(e->{
            UI.getCurrent().navigate(LoginView.class);
        });
        btnWoAccount = new Button("No account");
        btnWoAccount.addClickListener(e->{
            UI.getCurrent().navigate(ResultsView.class, "test");
        });
        registration = new Button("Registration");
        registration.getElement().getStyle().set("background-color", "#f3c604");
        registration.getElement().getStyle().set("margin-left", "auto");
        registration.getElement().getStyle().set("margin-right", "auto");
        logo = new Image();
        logo.setSrc("images/logo.png");
        add(logo);
        form.add(new HorizontalLayout(btnWithAccount, btnWoAccount, registration));
        form.add(new HorizontalLayout());
        add(form);
    }
}
