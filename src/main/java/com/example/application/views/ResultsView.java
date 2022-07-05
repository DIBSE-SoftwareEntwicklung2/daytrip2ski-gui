package com.example.application.views;

import com.example.application.dto.Person;
import com.example.application.dto.Result;
import com.example.application.dto.Skiresort;
import com.example.application.service.GDistanceMatrixService;
import com.example.application.service.RestPersonService;
import com.example.application.service.ScoreEvaluator;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.service.RestSkiresortService;
import com.example.application.utils.SingleItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;
import java.util.List;

@PageTitle("All Ski Resorts")
@Route(value = "results", layout = MainLayout.class)
public class ResultsView extends VerticalLayout implements HasUrlParameter<String> {
    private static final long serialVersionUID = 1L;

    private final transient RestSkiresortService restSkiresortService;

    private final transient RestPersonService personService;

    private final transient ScoreEvaluator scoreEvaluator;

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

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter != null) {
            if (parameter.equals("sponsored")) {
                // ToDo
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

        Person person = personService.getPersonById(1);

        for (Skiresort resort : result) {
            Result scoreResult = scoreEvaluator.evaluateScore(person, resort, LocalDateTime.now());

            if (!scoreResult.isValid()) {
                resort.setScore(-1);
                continue;
            }

            resort.setScore(scoreResult.getScore());
        }

        result.sort((r1, r2) -> r2.getScore().compareTo(r1.getScore()));
        return result;
    }
}

