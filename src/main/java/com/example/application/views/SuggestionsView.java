package com.example.application.views;

import com.example.application.service.GDistanceMatrixService;
import com.example.application.service.RestPersonService;
import com.example.application.service.RestSkiresortService;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Suggestions")
@Route(value = "suggestions", layout = MainLayout.class)
public class SuggestionsView extends ResultsView {
    private static final long serialVersionUID = 1L;

    @Autowired
    public SuggestionsView(RestSkiresortService restSkiresortService, RestPersonService personService, GDistanceMatrixService distanceService) {
        super(restSkiresortService, personService, distanceService);
    }

    @Override
    protected boolean hasScore() {
        return true;
    }
}

