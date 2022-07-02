package com.example.application.views;

import com.example.application.dto.Skiresort;
import com.example.application.service.RestSkiresortService;
import com.example.application.utils.SingleItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("Suggestions")
@Route(value = "suggestions", layout = MainLayout.class)
public class SuggestionsView extends ResultsView {

    private static final long serialVersionUID = 1L;

    @Override
    protected boolean hasScore() {
        return true;
    }
}

