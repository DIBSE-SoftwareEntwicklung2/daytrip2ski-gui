package com.example.application.views;

import com.example.application.dto.Person;
import com.example.application.dto.Result;
import com.example.application.dto.Skiresort;
import com.example.application.service.GDistanceMatrixService;
import com.example.application.service.RestPersonService;
import com.example.application.service.RestSkiresortService;
import com.example.application.service.ScoreEvaluator;
import com.example.application.utils.SingleItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Math.abs;

/**
 * shows All the ski resorts including sponsored resorts
 */
@PageTitle("All Ski Resorts")
@Route(value = "results", layout = MainLayout.class)
public class ResultsView extends VerticalLayout implements HasUrlParameter<String> {
    private static final long serialVersionUID = 1L;

    private final transient RestSkiresortService restSkiresortService;

    private final transient RestPersonService personService;

    private final transient ScoreEvaluator scoreEvaluator;

    /**
     * Getting the details of the ski resorts
     *
     * @param restSkiresortService ski resorts
     * @param personService        persons
     * @param distanceService      distance
     */
    @Autowired
    public ResultsView(RestSkiresortService restSkiresortService, RestPersonService personService, GDistanceMatrixService distanceService) {
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        this.restSkiresortService = restSkiresortService;
        this.personService = personService;
        this.scoreEvaluator = new ScoreEvaluator(personService, distanceService, restSkiresortService);
    }

    protected boolean hasScore() {
        return false;
    }

    /**
     * showing the ski resorts on the page including their images
     *
     * @param event     events
     * @param parameter parameter
     */
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter != null) {
            if (parameter.equals("sponsored")) {
                // ToDo - Future feature.
            } else {
                this.getAllSkiresorts().forEach(d -> {
                    SingleItem item = new SingleItem();
                    item.init(d.getName(), d.getId() + ".jpeg", false, d.getId(), this.hasScore(), d.getScore());
                    add(item);
                });
            }

        } else {
            this.getAllSkiresorts().forEach(d -> {
                SingleItem item = new SingleItem();
                item.init(d.getName(), d.getId() + ".jpeg", false, d.getId(), this.hasScore(), d.getScore());
                add(item);
            });
        }
    }

    private List<Skiresort> getAllSkiresorts() {
        List<Skiresort> result = restSkiresortService.getAllSkiresorts();

        if (!this.hasScore()) {
            return result;
        }

        Person person = personService.getPersonById(1L);

        for (Skiresort resort : result) {
            Result scoreResult = scoreEvaluator.evaluateScore(person, resort, LocalDateTime.now());

            if (!scoreResult.isValid()) {
                resort.setScore(-1);
                continue;
            }
            //this is a workaround right now
            if (!scoreResult.isRecommended() && ((scoreResult.getRecommendedErrors().size() > 2) || (scoreResult.getRecommendedErrors().size() == 2 && !(scoreResult.getRecommendedErrors().contains("Resort is out of Season") && scoreResult.getRecommendedErrors().contains("Resort is closed at this time"))) || (scoreResult.getRecommendedErrors().size() == 1 && !(scoreResult.getRecommendedErrors().contains("Resort is out of Season") || scoreResult.getRecommendedErrors().contains("Resort is closed at this time"))))) {
                scoreResult.setScore(scoreResult.getScore() * -1);
            }
            //this ends the workaround
            resort.setScore(scoreResult.getScore());
        }

        result.sort((r1, r2) -> Integer.compare(abs(r2.getScore()), abs(r1.getScore())));
        return result;
    }
}

