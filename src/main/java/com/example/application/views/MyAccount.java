package com.example.application.views;

import com.example.application.dto.Person;
import com.example.application.service.RestPersonService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.addons.PaperSlider;

import javax.validation.constraints.NotNull;

/**
 * User personal account page, enable the user to change his custom filters and see his personal information
 */
@PageTitle("My account")
@Route(value = "account", layout = MainLayout.class)
public class MyAccount extends VerticalLayout {
    private static final String BUTTON_BACKGROUND_COLOR = "background-color";
    private final transient RestPersonService rps;

    private static final long serialVersionUID = 1L;
    private final BeanValidationBinder<Person> binder = new BeanValidationBinder<>(Person.class);

    // No serialization needed.
    private transient Person person;

    public MyAccount(@NotNull RestPersonService rps) {
        this.rps = rps;
        TextField tfID = new TextField("ID");
        tfID.setEnabled(false);
        add(tfID);

        binder.forField(tfID).withNullRepresentation("").withConverter(new StringToLongConverter("Id must be valid long")).bind("id");

        TextField tfName = new TextField("Name");
        tfName.setEnabled(false);
        add(tfName);
        binder.forField(tfName).bind("firstName");

        TextField tfLastName = new TextField("Last name");
        tfLastName.setEnabled(false);
        add(tfLastName);
        binder.forField(tfLastName).bind("lastName");

        TextField tfEmail = new TextField("Email");
        tfEmail.setEnabled(false);
        add(tfEmail);
        binder.forField(tfEmail).bind("email");

        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("dd.MM.yyyy");
        DatePicker dpBirthDate = new DatePicker("Birth date");
        dpBirthDate.setI18n(singleFormatI18n);
        dpBirthDate.setEnabled(false);
        add(dpBirthDate);
        binder.forField(dpBirthDate).bind("dob");

        Button btnSave = new Button("Save");
        btnSave.setEnabled(false);

        binder.addStatusChangeListener(event -> {
            boolean isValid = event.getBinder().isValid();
            boolean hasChanges = event.getBinder().hasChanges();

            btnSave.setEnabled(hasChanges && isValid);
            if (!isValid) {
                btnSave.getStyle().set(BUTTON_BACKGROUND_COLOR, "red");
                return;
            }

            if (!hasChanges) {
                btnSave.getStyle().set(BUTTON_BACKGROUND_COLOR, "gray");
                return;
            }

            btnSave.getStyle().set(BUTTON_BACKGROUND_COLOR, "cadetblue");
        });

        btnSave.addClickListener(event -> {
            try {
                binder.writeBean(person);
                this.rps.savePerson(person);
                Notification.show("Details saved successfully!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (ValidationException e) {
                Notification.show("Validation error while saving details: " + e.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (Exception e) {
                Notification.show("Fatal error while saving details: " + e.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }

            try {
                person = this.rps.getPersonById(person.getId());
                binder.readBean(person);
            } catch (Exception e) {
                Notification.show("Fatal error while reading details: " + e.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        VerticalLayout vlArrival = new VerticalLayout();
        VerticalLayout vlCosts = new VerticalLayout();
        VerticalLayout vlSlopes = new VerticalLayout();
        VerticalLayout vlCapacity = new VerticalLayout();
        VerticalLayout vlSpecials = new VerticalLayout();

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

        add(new H2("Customize your trip!"));

        PaperSlider varietySlider = new PaperSlider(1);
        varietySlider.setLabel("Variety");
        varietySlider.showValues();
        varietySlider.setMax(10);
        varietySlider.setMin(1);
        add(varietySlider);

        binder.bind(varietySlider, p -> (int) (p.getScore().getVariety() * 10), (p, value) -> p.getScore().setVariety(value * 0.1));

        PaperSlider easyTracksSlider = new PaperSlider(1);
        easyTracksSlider.setLabel("Easy Tracks");
        easyTracksSlider.showValues();
        easyTracksSlider.setMax(10);
        easyTracksSlider.setMin(1);
        add(easyTracksSlider);

        binder.bind(easyTracksSlider, p -> (int) (p.getScore().getAffinityToEasyTracks() * 10), (p, value) -> p.getScore().setAffinityToEasyTracks(value * 0.1));

        PaperSlider intermediateTracksSlider = new PaperSlider(1);
        intermediateTracksSlider.setLabel("Intermediate Tracks");
        intermediateTracksSlider.showValues();
        intermediateTracksSlider.setMax(10);
        intermediateTracksSlider.setMin(1);
        add(intermediateTracksSlider);

        binder.bind(intermediateTracksSlider, p -> (int) (p.getScore().getAffinityToIntermediateTracks() * 10), (p, value) -> p.getScore().setAffinityToIntermediateTracks(value * 0.1));

        PaperSlider difficultTracksSlider = new PaperSlider(1);
        difficultTracksSlider.setLabel("Difficult Tracks");
        difficultTracksSlider.showValues();
        difficultTracksSlider.setMax(10);
        difficultTracksSlider.setMin(1);
        add(difficultTracksSlider);

        binder.bind(difficultTracksSlider, p -> (int) (p.getScore().getAffinityToDifficultTracks() * 10), (p, value) -> p.getScore().setAffinityToDifficultTracks(Double.valueOf(value) * 0.1));

        Checkbox chkRentalRequired = new Checkbox("Rental Required");
        Checkbox chkFamilyFriendly = new Checkbox("Family Friendly");
        add(chkRentalRequired);
        add(chkFamilyFriendly);

        binder.bind(chkRentalRequired, p -> p.getScore().getRequiresRental(), (p, value) -> p.getScore().setRequiresRental(value));
        binder.bind(chkFamilyFriendly, p -> p.getScore().getRequiresFamilyFriendly(), (p, value) -> p.getScore().setRequiresFamilyFriendly(value));

        TextField tfBudget = new TextField("Budget in €");
        add(tfBudget);
        binder.forField(tfBudget).withNullRepresentation("").withConverter(new StringToDoubleConverter("Budget must be valid double")).bind("score.budged");

        TextField tfDistance = new TextField("Distance in km");
        add(tfDistance);
        binder.forField(tfDistance).withNullRepresentation("").withConverter(new StringToDoubleConverter("Distance must be valid double")).bind("score.maxDistance");

        TextField tfDrivingTime = new TextField("Driving Time in h");
        add(tfDrivingTime);
        binder.forField(tfDrivingTime).withNullRepresentation("").withConverter(new StringToDoubleConverter("Driving time must be valid double")).bind("score.maxDrivingTime");

        add(btnSave);
        binder.setBean(person);
    }


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        person = rps.getPersonById(1L);
        binder.readBean(person);
    }
}
