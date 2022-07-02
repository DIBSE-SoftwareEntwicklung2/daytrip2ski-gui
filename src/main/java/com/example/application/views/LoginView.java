package com.example.application.views;

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
    private static final long serialVersionUID = 6051462478629438464L;

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
        LoginForm login = new LoginForm(i18n);
        login.setForgotPasswordButtonVisible(true);
        login.addLoginListener(e -> {
            // ToDo
        });
        login.addForgotPasswordListener(e -> {
            // ToDo
        });
        Image logo = new Image();
        logo.setSrc("images/logo.png");
        add(logo);
        add(login);
    }
}
