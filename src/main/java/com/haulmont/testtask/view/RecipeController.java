package com.haulmont.testtask.view;

import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.DAO.RecipeDAO;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.PriorityR;
import com.haulmont.testtask.model.Recipe;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import java.util.List;

public class RecipeController extends RecipeView implements View{

    private RecipeDAO recipeDAO = new RecipeDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private List<Recipe> recipes = recipeDAO.findAll();

    private Patient filterPatient;

    enum State{
        START, PATIENT_EMPTY, PATIENT_NOT_EMPTY,PATIENT_AND_DESCR_EMPTY, PATIENT_AND_DESCR_NOT_EMPTY ,PATIENT_NOT_EMPTY_DESCR_EMPTY,
        PATIENT_EMPTY_DESC_NOT_EMPTY, END
    }

    public RecipeController() {
        setGrid();
        getPriorityFilterCombobox().setItems(PriorityR.getAsArrayList());
        getAddIButton().addClickListener(clickEvent -> addWindow());
        getRecipeFilterButton().addClickListener(clickEvent -> {
            getDataFromFiltr();
        });
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
    /**method setGrid() inserts data and new columns with buttons, and set ClickListeners to buttons*/
    private void setGrid(){
        setDataToGrid();
        getGrid().addColumn(doctor -> "Delete", deleteItemButtonRenderer());
        getGrid().addColumn(doctor -> "Edit", editItemButtonRenderer());
        getGrid().addColumn(recipe -> recipe.getDoctor().getName() + " "
                + recipe.getDoctor().getSurname() + " "
                + recipe.getDoctor().getPatronymic()).setCaption("Doctor").setId("Doctor");
        getGrid().addColumn(recipe -> recipe.getPatient().getName() + " "
                + recipe.getPatient().getSurname() + " "
                + recipe.getPatient().getPatronymic()).setCaption("Patient").setId("Patient");
        getGrid().setColumnOrder("Doctor","Patient", "description", "dateOfCreation","priorityR"  );
    }


    /**
     * method getDataFromFiltr() check all textfields and create list of recipes, based on their values
     * */
    private void getDataFromFiltr() {
        getShowRecipesButton().setVisible(true);
        getAddIButton().setVisible(false);
        getShowRecipesButton().addClickListener(clickEvent -> {
            getShowRecipesButton().setVisible(false);
            getAddIButton().setVisible(true);
            setDataToGrid();
        });
        State state = State.START;
        while (state != State.END) {
            switch (state) {
                case START:
                    if(isPatientEmpty()){ state = State.PATIENT_EMPTY;break; }
                    if(isPatientNull()){ recipes.clear(); getGrid().setItems(recipes); state = State.END; break; }
                    else { state = State.PATIENT_NOT_EMPTY; break;}
                case PATIENT_EMPTY:
                    if(getDescriptionFilterTextArea().getValue().equals("")){state = State.PATIENT_AND_DESCR_EMPTY;break; }
                    else{ state = State.PATIENT_EMPTY_DESC_NOT_EMPTY;break;}
                case PATIENT_NOT_EMPTY:
                    if(getDescriptionFilterTextArea().getValue().equals("")){state = State.PATIENT_NOT_EMPTY_DESCR_EMPTY;break; }
                    else{ state = State.PATIENT_AND_DESCR_NOT_EMPTY;break;}
                case PATIENT_AND_DESCR_EMPTY:
                    if(getPriorityFilterCombobox().getValue()==null){ state = State.END; break;
                    }else{
                        recipes = recipeDAO.findByPriority(getPriorityFilterCombobox().getValue().toString());
                        getGrid().setItems(recipes);
                        state = State.END;
                        break;
                    }
                case PATIENT_AND_DESCR_NOT_EMPTY:
                    if(getPriorityFilterCombobox().getValue()==null){
                        recipes  = recipeDAO.findByPatientAndDescription(filterPatient, getDescriptionFilterTextArea().getValue());
                        getGrid().setItems(recipes);
                        state = State.END;
                        break;
                    }else{
                        recipes  = recipeDAO.findByPatientAndDescriptionAndPriotity(filterPatient,
                                getDescriptionFilterTextArea().getValue(),
                                getPriorityFilterCombobox().getValue().toString());
                        getGrid().setItems(recipes);
                        state = State.END;
                        break;
                    }
                case PATIENT_EMPTY_DESC_NOT_EMPTY:
                    if(getPriorityFilterCombobox().getValue()==null){
                        recipes  = recipeDAO.findByDescription(getDescriptionFilterTextArea().getValue());
                        getGrid().setItems(recipes);
                        state = State.END;
                        break;
                    }else{
                        recipes  = recipeDAO.findByDescriptionAndPriotity(getDescriptionFilterTextArea().getValue(),
                                getPriorityFilterCombobox().getValue().toString());
                        getGrid().setItems(recipes);
                        state = State.END;
                        break;
                    }
                case PATIENT_NOT_EMPTY_DESCR_EMPTY:
                    if(getPriorityFilterCombobox().getValue()==null){
                        recipes  = recipeDAO.findByPatient(filterPatient);
                        getGrid().setItems(recipes);
                        state = State.END;
                        break;
                    }else{
                        recipes  = recipeDAO.findByPatientAndPriotity(filterPatient,
                                getPriorityFilterCombobox().getValue().toString());
                        getGrid().setItems(recipes);
                        state = State.END;
                        break;
                    }
            }
        }
    }
    private boolean isPatientNull(){
        filterPatient = patientDAO.findByNameAndSurnameAndPatronymic(getFilterNameField().getValue(),
                getFilterSurnameField().getValue(),
                getFilterPatronymicField().getValue());
        if (filterPatient == null) return true;
        else return false;
    }
    private boolean isPatientEmpty(){
        return (getFilterNameField().getValue().equals("")&&  getFilterSurnameField().getValue().equals("")&&getFilterPatronymicField().getValue().equals(""));
    }
    /**
     * method deleteItemButtonRenderer() return new button whith ClickListener
     * If clickEvent - then delete recipe from database and refresh Grid
     */
    private ButtonRenderer deleteItemButtonRenderer(){
        return new ButtonRenderer(clickEvent -> {
                Recipe r = (Recipe) clickEvent.getItem();
                recipeDAO.delete(r);
                setDataToGrid();
        });
    }
    /**
     * method editItemButtonRenderer() return new button whith ClickListener
     * If clickEvent - then it shows modal form for editing recipe
     */
    private ButtonRenderer editItemButtonRenderer(){
        return  new ButtonRenderer(clickEvent -> {
            Recipe recipe = (Recipe) clickEvent.getItem();
            Window window = new Window();
            window.setContent(new EditRecipeController(recipe));
            window.setModal(true);
            window.addCloseListener(closeEvent -> setDataToGrid());
            this.getUI().getUI().addWindow(window);
        });
    }
    /**show modal form for adding new recipe*/
    private void addWindow(){
        final Window window = new Window();
        window.setContent(new AddRecipeController());
        window.setModal(true);
        window.addCloseListener(closeEvent -> setDataToGrid());
        this.getUI().getUI().addWindow(window);
    }
    private void setDataToGrid(){
        recipes = recipeDAO.findAll();
        getGrid().setItems(recipes);
    }

}