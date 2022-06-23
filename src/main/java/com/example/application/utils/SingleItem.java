package com.example.application.utils;

import com.example.application.views.SingleResultView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;

@CssImport("./styles/utils/singleItem.css")
public class SingleItem extends Div{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3268436718729722814L;
	
	public SingleItem(){
        setClassName("single-item");
    }

	
	//Initialization of one destination in result view 
    public Component init(String name, String image, boolean sponsored, Long id){
        H1 title = new H1(name);
        title.setClassName("title");
        getElement().getStyle().set("background-image", "url(images/"+image+")");
        addClickListener(e->{
        	//navigation to single result page and passing ID of destination
            UI.getCurrent().navigate(SingleResultView.class, id);
        });
        if(sponsored){
            
        }

        add(title);
        return this;
    }

}
