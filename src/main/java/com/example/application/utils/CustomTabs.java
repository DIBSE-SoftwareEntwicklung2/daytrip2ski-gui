package com.example.application.utils;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

@CssImport("./styles/components/customTabs.css")
public class CustomTabs extends HorizontalLayout {
    private static final long serialVersionUID = 2179033993926552247L;
    private final Tabs header;
    private final Div content;
    private final Map<Tab, Component> tabsToPages;

    public CustomTabs() {
        header = new Tabs();
        header.setOrientation(Tabs.Orientation.VERTICAL);
        content = new Div();
        content.getElement().getStyle().set("padding", "10px");
        addClassName("tabs-vertical-layout");
        tabsToPages = new HashMap<>();
        header.addSelectedChangeListener(e -> {
            if (e.getPreviousTab() != null) {
                tabsToPages.get(e.getPreviousTab()).setVisible(false);
            }
            if (e.getSelectedTab() != null) {
                tabsToPages.get(e.getSelectedTab()).setVisible(true);
            }
        });
        add(header, content);
    }

    public void add(String name, Component... components) {
        Tab tempTab = new Tab(name);
        Div tempDiv = new Div(components);
        tempDiv.addClassName("div-content");
        tempDiv.setVisible(header.getComponentCount() <= 0);
        tabsToPages.put(tempTab, tempDiv);
        content.add(tempDiv);
        header.add(tempTab);
    }

    public void add(Tab tab, Component... components) {
        Div tempDiv = new Div(components);
        tempDiv.setVisible(header.getComponentCount() <= 0);
        tabsToPages.put(tab, tempDiv);
        content.add(tempDiv);
        header.add(tab);
    }

    public Tab getSelectedTab() {
        return header.getSelectedTab();
    }

    @Override
    public void remove(Component... components) {
        if (components.length == 1) {
            Component temp = components[0];
            content.remove(tabsToPages.get(temp));
            header.remove(temp);
        } else {
            super.remove(components);
        }
    }

    public void setSelectedTab(Tab t) {
        header.setSelectedTab(t);
    }

    public Component selectFirstVisible() {
        for (int i = 0; i < header.getComponentCount(); i++) {
            if (header.getComponentAt(i).isVisible()) {
                header.setSelectedIndex(i);
                return header.getComponentAt(i);
            }
        }
        return null;

    }

}
