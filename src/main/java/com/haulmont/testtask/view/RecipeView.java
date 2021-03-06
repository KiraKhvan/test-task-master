package com.haulmont.testtask.view;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class RecipeView extends VerticalLayout {
    private Button addIButton;
    private Grid<com.haulmont.testtask.model.Recipe> grid;
    private Button recipeFilterButton;
    private ComboBox<String> priorityFilterCombobox;
    private TextArea descriptionFilterTextArea;
    private TextField filterNameField;
    private TextField filterSurnameField;
    private TextField filterPatronymicField;
    private Button showRecipesButton;

    public RecipeView() {
        Design.read(this);
    }

    public Button getAddIButton() {
        return addIButton;
    }

    public Grid<com.haulmont.testtask.model.Recipe> getGrid() {
        return grid;
    }

    public Button getRecipeFilterButton() {
        return recipeFilterButton;
    }

    public ComboBox<String> getPriorityFilterCombobox() {
        return priorityFilterCombobox;
    }

    public TextArea getDescriptionFilterTextArea() {
        return descriptionFilterTextArea;
    }

    public TextField getFilterNameField() {
        return filterNameField;
    }

    public TextField getFilterSurnameField() {
        return filterSurnameField;
    }

    public TextField getFilterPatronymicField() {
        return filterPatronymicField;
    }

    public Button getShowRecipesButton() {
        return showRecipesButton;
    }

}
