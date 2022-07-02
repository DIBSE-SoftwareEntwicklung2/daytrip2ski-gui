package com.example.application.views;

import com.example.application.dto.*;
import com.example.application.service.RestPersonService;
import com.example.application.service.RestSkiresortService;
import com.example.application.service.ScoreEvaluator;
import com.example.application.service.WeatherService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "loginView")
@CssImport("./styles/views/loginView.css")
@PageTitle("Login view")
public class LoginView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6051462478629438464L;
	
	private LoginForm login;
	private Image logo;
	
	public LoginView() {
		
		setSizeFull();
		setAlignItems(Alignment.CENTER); 
		setJustifyContentMode(JustifyContentMode.CENTER);
		setClassName("login-form");
		
		//Vaadin default login form
		LoginI18n i18n = LoginI18n.createDefault();

		LoginI18n.Form i18nForm = i18n.getForm();
		i18nForm.setTitle("Login");
		i18nForm.setUsername("Username:");
		i18nForm.setPassword("Password:");
		i18nForm.setSubmit("Login");
		i18nForm.setForgotPassword("Reset password");
		i18n.setForm(i18nForm);

		LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
		i18nErrorMessage.setTitle("Wrong username or password");
		i18nErrorMessage.setMessage("Check your credentials and try again");
		i18n.setErrorMessage(i18nErrorMessage);

		i18n.setAdditionalInformation("If you need more info about this app please send email at info@demo.com");

		login = new LoginForm(i18n);
		login.setForgotPasswordButtonVisible(true);
		login.addLoginListener(e -> {
			
		});
		login.addForgotPasswordListener(e -> {
			
		});
		logo = new Image();
        logo.setSrc("images/logo.png");
        add(logo);
		add(login);



//		RestSkiresortService service = new RestSkiresortService();
//		List<Skiresort> mytest = service.getallSkiresorts();
//
//		RestPersonService testservice = new RestPersonService();
//		Person mypersontest = testservice.getPersonbyId(1);
//
//
//		System.out.println(EvaluateScore(mypersontest, mytest.get(1)));
//
//		System.out.println(mypersontest);
//		mytest.forEach(s-> {
//			System.out.println(s.getName());
//			System.out.println(EvaluateScore(mypersontest, s));
//			WeatherService wservice = new WeatherService();
//			WeatherActualReturn myweather = wservice.getWeatherActual(s);
//			System.out.println(myweather);
//
//			WeatherForecastReturn mysecTest =  wservice.getWeatherForecast(s);
//			System.out.println(mysecTest);
//		});
//		getDistanceMatrix(mypersontest, mytest.get(0));
//		RestPersonService testservice = new RestPersonService();
//		Person mypersontest = testservice.getPersonbyId(1);
//		System.out.println(mypersontest);
//		Score myTestScore = testservice.getScoreFromPerson(mypersontest);
//		System.out.println(myTestScore);
//
//		Person mypersontest2 = testservice.getPersonbyId(2);
//		System.out.println(mypersontest2);
//		Score myTestScore2 = testservice.getScoreFromPerson(mypersontest2);
//		System.out.println(myTestScore2);


//		RestPersonService testservice = new RestPersonService();
//		Person anotherTest = new Person(
//				"Test",
//				"Testermann",
//				"something@test.test",
//				LocalDate.of(2000, Month.APRIL, 1)
//		);
//		anotherTest.setScore(new Score());
//		testservice.postRegisterPerson(anotherTest);
//		anotherTest = testservice.getPersonbyId(3);
//		System.out.println(anotherTest);
//		anotherTest.setFirstName("updated");
//		testservice.savePerson(anotherTest);
//		System.out.println(testservice.getPersonbyId(3));
	}

}
