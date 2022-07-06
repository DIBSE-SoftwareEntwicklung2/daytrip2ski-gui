package com.example.application.views;

import com.example.application.service.GDistanceMatrixService;
import com.example.application.service.RestPersonService;
import com.example.application.service.RestSkiresortService;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Get the calculated score from the logged in person and sort the Ski resorts according to this score
 */
@PageTitle("Suggestions")
@Route(value = "suggestions", layout = MainLayout.class)
public class SuggestionsView extends ResultsView {
    private static final long serialVersionUID = 1L;

    /**
     * Get needed information about ski resorts, person, and distance
     *
     * @param restSkiresortService rest skiresort service
     * @param personService        person service
     * @param distanceService      distance service
     */
    @Autowired
    public SuggestionsView(RestSkiresortService restSkiresortService, RestPersonService personService, GDistanceMatrixService distanceService) {
        super(restSkiresortService, personService, distanceService);
    }

    @Override
    protected boolean hasScore() {
        return true;
    }
}

