package com.haulmont.testtask.view;

import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.model.Patient;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import com.vaadin.ui.renderers.ButtonRenderer;

import java.util.List;

public class PatientController extends PatientView implements View {
    private PatientDAO patientDAO = new PatientDAO();
    private List<Patient> patients = patientDAO.findAll();

    public PatientController() {
        setGrid();
        getAddPatientButton().addClickListener(clickEvent -> {
            addWindow();
        });
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // The view could do something here as well
    }
    /**method setGrid() inserts data and new columns with buttons, and set ClickListeners to buttons*/
    private void setGrid(){
        setDataToGrid();
        getGrid().addColumn(patient -> "Delete", deleteItemButtonRenderer());
        getGrid().addColumn(patient -> "Edit", editItemButtonRenderer());
    }
    private void setDataToGrid(){
        patients = patientDAO.findAll();
        getGrid().setItems(patients);
        getGrid().setColumnOrder("name","surname", "patronymic", "phone" );
    }

    /**
     * method deleteItemButtonRenderer() return new button whith ClickListener
     * If clickEvent - then delete patient from database and refresh Grid
     */
    private ButtonRenderer deleteItemButtonRenderer(){
        return new ButtonRenderer(clickEvent -> {
            try {
                Patient p = (Patient) clickEvent.getItem();
                patientDAO.delete(p);
                setDataToGrid();
            }catch (Exception e){
                Notification.show("Ошибка",
                        "С данным пациентом связаны записи в таблице рецептов.",
                        Notification.Type.HUMANIZED_MESSAGE);
            }
        });
    }
    /**
     * method editItemButtonRenderer() return new button whith ClickListener
     * If clickEvent - then it shows modal form for editing patient
     */
    private ButtonRenderer editItemButtonRenderer(){
        return  new ButtonRenderer(clickEvent -> {
            Patient p = (Patient) clickEvent.getItem();
            Window window = new Window();
            window.setContent(new EditPatientController(p));
            window.setModal(true);
            window.addCloseListener(closeEvent -> setDataToGrid());
            this.getUI().getUI().addWindow(window);
        });
    }
    /**show modal form for adding new Doctor*/
    private void addWindow(){
        Window window = new Window();
        window.setContent(new AddPatientController());
        window.setModal(true);
        window.addCloseListener(closeEvent -> setDataToGrid());
        this.getUI().getUI().addWindow(window);
    }


}