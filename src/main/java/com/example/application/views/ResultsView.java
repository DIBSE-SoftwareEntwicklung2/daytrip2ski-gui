package com.example.application.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.service.RestSkiresortService;
import com.example.application.utils.SingleItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Results")
@Route(value = "results", layout = MainLayout.class)
public class ResultsView extends VerticalLayout implements HasUrlParameter<String>{



    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private RestSkiresortService restSkiresortService;


    public ResultsView(){
        setWidthFull();
        setAlignItems(Alignment.CENTER);

    }



    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if(parameter!=null){
            if(parameter.equals("sponsored")) {

            }else {
                restSkiresortService.getallSkiresorts().forEach(d->{
                    SingleItem item = new SingleItem();
                    item.init(d.getName(), d.getId() + ".jpeg", false, d.getId());
                    add(item);
                });
            }

        }else {
            restSkiresortService.getallSkiresorts().forEach(d->{
                SingleItem item = new SingleItem();
                item.init(d.getName(), d.getId() + ".jpeg", false, d.getId());
                add(item);
            });
        }
    }
}

