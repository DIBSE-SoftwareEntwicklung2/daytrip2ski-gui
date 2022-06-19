package com.example.application.views;

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



	public ResultsView(){
        setWidthFull();
        setAlignItems(Alignment.CENTER); 
        
    }
    


    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if(parameter!=null){
        	if(parameter.equals("sponsored")) {
        		
        	}else {
        		MainLayout.getDestinations().forEach(d->{
                	SingleItem item = new SingleItem();
                    item.init(d.getName(), d.getImage(), false, d.getId());
                    add(item);
                });
        	}
        	
        }else {
    		MainLayout.getDestinations().forEach(d->{
            	SingleItem item = new SingleItem();
                item.init(d.getName(), d.getImage(), false, d.getId());
                add(item);
            });
    	}
    }
}

