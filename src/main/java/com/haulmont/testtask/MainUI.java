package com.haulmont.testtask;

import com.haulmont.testtask.view.DoctorController;
import com.haulmont.testtask.view.PatientController;
import com.haulmont.testtask.view.RecipeController;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.HashMap;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    protected class NavigableMenuBar extends MenuBar implements ViewChangeListener {
        private MenuItem previous = null; // Previously selected item
        private MenuItem current  = null; // Currently selected item

        // Map view IDs to corresponding menu items
        HashMap<String,MenuItem> menuItems = new HashMap<String,MenuItem>();

        private Navigator navigator = null;

        public NavigableMenuBar(Navigator navigator) {
            this.navigator = navigator;
        }

        /** Navigate to a view by menu selection */
        MenuBar.Command mycommand = new MenuBar.Command() {

            public void menuSelected(MenuItem selectedItem) {
                String viewName = selectItem(selectedItem);
                navigator.navigateTo(viewName);
            }
        };

        public void addView(String viewName, String caption, Resource icon) {
            menuItems.put(viewName, addItem(caption, icon, mycommand));
        }

        /** Select a menu item by its view ID **/
        protected boolean selectView(String viewName) {
            // Check that the menu item exists
            if (!menuItems.containsKey(viewName))
                return false;

            if (previous != null)
                previous.setStyleName(null);
            if (current == null)
                current = menuItems.get(viewName);
            current.setStyleName("highlight");
            previous = current;

            return true;
        }

        /** Selects a new menu item */
        public String selectItem(MenuItem selectedItem) {
            current = selectedItem;

            // Do reverse lookup for the view ID
            for (String key: menuItems.keySet())
                if (menuItems.get(key) == selectedItem)
                    return key;

            return null;
        }

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return selectView(event.getViewName());
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {}
    }


    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);

        // Have a view display controlled by a navigator
        Panel viewDisplay = new Panel();
        viewDisplay.setSizeFull();

        // Create a navigator with some pre-created views
        Navigator navigator = new Navigator(this, viewDisplay);
        navigator.addView("patients", new PatientController());
        navigator.addView("doctors",    new DoctorController());
        navigator.addView("recipes",  new RecipeController());
        navigator.navigateTo("patients");

        // Control and observe navigation by a main menu
        NavigableMenuBar menu = new NavigableMenuBar(navigator);
        menu.addStyleName("mybarmenu");
        layout.addComponent(menu);

        // The view display goes below the menu bar
        layout.addComponent(viewDisplay);
        layout.setExpandRatio(viewDisplay, 1.0f);

        // Update the menu selection when the view changes
        navigator.addViewChangeListener(menu);

        // Add items in the menu and associate them with a view ID
        menu.addView("patients", "Пациенты", null);
        menu.addView("doctors",    "Доктора", null);
        menu.addView("recipes",  "Рецепты", null);


    }


}