package com.example.application.views;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.map.HashedMap;

import com.example.application.model.Item;
import com.example.application.windowses.SearchWindow;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouterLink;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    /**
     *
     */
    private static final long serialVersionUID = -6832342952232880779L;
    private SearchWindow searchWindow;
    private static List<Item> destinations;


    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, String iconClass, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            link.addClassNames("menu-item-link");
            link.setRoute(view);

            Span text = new Span(menuTitle);
            text.addClassNames("menu-item-text");

            link.add(new LineAwesomeIcon(iconClass), text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

        /**
         * Simple wrapper to create icons using LineAwesome iconset. See
         * https://icons8.com/line-awesome
         */
        @NpmPackage(value = "line-awesome", version = "1.3.0")
        public static class LineAwesomeIcon extends Span {
            /**
             *
             */
            private static final long serialVersionUID = -7066162644675821833L;

            public LineAwesomeIcon(String lineawesomeClassnames) {
                addClassNames("menu-item-icon");
                if (!lineawesomeClassnames.isEmpty()) {
                    addClassNames(lineawesomeClassnames);
                }
            }
        }

    }


    private H1 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
        searchWindow = new SearchWindow();
        generateDestinations();
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        Button btnSearch = new Button("Search");
        btnSearch.addClickListener(e->{
            searchWindow.open();
        });
        btnSearch.getElement().getStyle().set("margin-left", "auto");
        btnSearch.getElement().getStyle().set("margin-right", "10px");
        toggle.addClassNames("view-toggle");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames("view-title");

        Header header = new Header(toggle, viewTitle);
        header.add(btnSearch);
        header.addClassNames("view-header");
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("My App");
        appName.addClassNames("app-name");
        Image logo = new Image();
        logo.addClickListener(e->{
            UI.getCurrent().navigate(HomeView.class);
        });
        logo.setSrc("images/logo-dark.png");
        logo.setWidth("168px");
        logo.setHeight("100px");
        logo.getElement().getStyle().set("margin-left", "auto");
        logo.getElement().getStyle().set("margin-right", "auto");
        logo.getElement().getStyle().set("margin-bottom", "20px");
        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(logo,
                createNavigation(), createFooter());
        section.addClassNames("drawer-section");
        return section;
    }

    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("menu-item-container");
        nav.getElement().setAttribute("aria-labelledby", "views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("navigation-list");
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            list.add(menuItem);

        }
        return nav;
    }

    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{ //
                new MenuItemInfo("HOME", "la la-globe", HomeView.class), //

                new MenuItemInfo("Suggestions", "la la-thumbs-up", SuggestionsView.class), //
                new MenuItemInfo("Favorites", "la la-star", HomeView.class), //
                new MenuItemInfo("My account", "la la-user", MyAccount.class), //

        };
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("footer");

        return layout;
    }

    private void generateDestinations() {
        destinations = new ArrayList<>();
        Map<String, Integer> slopes = new HashedMap<>();
        slopes.put("BLUE", 2);
        slopes.put("RED", 5);
        destinations.add(new Item(1, "Christlum", "Low", "32km", slopes, BigDecimal.valueOf(62), null, null, "Soelden.jpg"));
        destinations.add(new Item(2, "Mayrhofen", "High", "52km", slopes, BigDecimal.valueOf(55), null, null, "Mayrhofen.jpg"));
        destinations.add(new Item(3, "Kitzbuhel", "Low", "44km", slopes, BigDecimal.valueOf(45), null, null, "Kitzbuhel.jpg"));
        destinations.add(new Item(4, "Saint Anton am Arlberg", "Medium", "13km", slopes, BigDecimal.valueOf(79), null, null, "Saint.jpg")); //Seefeld
        destinations.add(new Item(5, "Innsbruck", "Medium", "13km", slopes, BigDecimal.valueOf(79), null, null, "Innsbruck.jpg"));
        destinations.add(new Item(6, "Ishgl", "Medium", "13km", slopes, BigDecimal.valueOf(79), null, null, "Ishgl.jpg"));
        destinations.add(new Item(7, "Kirchberg in Tirol", "Medium", "13km", slopes, BigDecimal.valueOf(79), null, null, "Kirchberg.jpg"));
        destinations.add(new Item(8, "Obergurgl", "Medium", "13km", slopes, BigDecimal.valueOf(79), null, null, "Obergurgl.jpg"));
        destinations.add(new Item(9, "Seefeld", "Medium", "13km", slopes, BigDecimal.valueOf(79), null, null, "Seefeld.jpg"));
    }



    public static List<Item> getDestinations() {
        return destinations;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
