package com.example.application.views;

import com.vaadin.flow.router.*;

@PageTitle("Suggestions")
@Route(value = "suggestions", layout = MainLayout.class)
public class SuggestionsView extends ResultsView {
    private static final long serialVersionUID = 1L;

    @Override
    protected boolean hasScore() {
        return true;
    }
}

