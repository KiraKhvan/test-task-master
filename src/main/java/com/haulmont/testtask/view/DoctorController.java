package com.haulmont.testtask.view;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.DAO.RecipeDAO;
import com.haulmont.testtask.model.Doctor;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;

import java.util.List;

public class DoctorController extends DoctorView implements View  {
    private DoctorDAO doctorDAO = new DoctorDAO();
    private RecipeDAO recipeDAO = new RecipeDAO();
    private List<Doctor> doctors = doctorDAO.findAll();

    public DoctorController() {
        setGrid();
        getAddItemButton().addClickListener(clickEvent -> {
            //show modal form for adding new Doctor
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
        getGrid().addColumn(doctor -> "Delete", deleteItemButtonRenderer());
        getGrid().addColumn(doctor -> "Edit", editItemButtonRenderer());
        getShowSatisticsButton().setCaption("Показать статистику");
        getShowSatisticsButton().addClickListener(clickEvent1 -> setStatisticToGrid());
    }
    /**
     * method setStatisticToGrid() set visible StatisticGrid instead Grid, and set data end buttons
     * whith ClickListeners in StatisticGrid
     */
    private void setStatisticToGrid(){
        getAddItemButton().setVisible(false);
        getGrid().setVisible(false);
        getStatisticGrid().setVisible(true);
        doctors = doctorDAO.findAll();
        getStatisticGrid().setItems(doctors);
        getStatisticGrid().removeAllColumns();
        getStatisticGrid().addColumn(doctor -> doctor.getName() + " "
                + doctor.getSurname() + " "
                + doctor.getPatronymic()).setCaption("Doctor");
        getStatisticGrid().addColumn(doctor ->  recipeDAO.findByDoctor(doctor).size()).setCaption("Statistic");
        getShowSatisticsButton().setCaption("Вернуться к таблице докторов");
        getShowSatisticsButton().addClickListener(clickEvent -> {
            getGrid().setVisible(true);
            getStatisticGrid().setVisible(false);
            getAddItemButton().setVisible(true);
            getShowSatisticsButton().setCaption("Показать статистику");
            getShowSatisticsButton().addClickListener(clickEvent1 -> setStatisticToGrid());
        });

    }
    /**
     * method deleteItemButtonRenderer() return new button whith ClickListener
     * If clickEvent - then delete Doctor from database and refresh Grid
     */
    private ButtonRenderer deleteItemButtonRenderer(){
        return new ButtonRenderer(clickEvent -> {
            try {
                Doctor d = (Doctor) clickEvent.getItem();
                doctorDAO.delete(d);
                setDataToGrid();
            }catch (Exception e){
                Notification.show("Ошибка",
                        "С данным доктором связаны записи в таблице рецептов.",
                        Notification.Type.HUMANIZED_MESSAGE);
            }
        });
    }
    /**
     * method editItemButtonRenderer() return new button whith ClickListener
     * If clickEvent - then it shows modal form for editing doctor
     */
    private ButtonRenderer editItemButtonRenderer(){
        return  new ButtonRenderer(clickEvent -> {
            Doctor d = (Doctor) clickEvent.getItem();
            Window window = new Window();
            window.setContent(new EditDoctorController(d));
            window.setModal(true);
            window.addCloseListener(closeEvent -> setDataToGrid());
            this.getUI().getUI().addWindow(window);
        });
    }
    /**show modal form for adding new Doctor*/
    private void addWindow(){
        final Window window = new Window();
        window.setContent(new AddDoctorController());
        window.setModal(true);
        window.addCloseListener(closeEvent -> setDataToGrid());
        this.getUI().getUI().addWindow(window);
    }
    private void setDataToGrid(){
        doctors = doctorDAO.findAll();
        getGrid().setItems(doctors);
        getGrid().setColumnOrder("name","surname", "patronymic", "specialization" );
    }

}