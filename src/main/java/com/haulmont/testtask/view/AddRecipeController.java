package com.haulmont.testtask.view;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.DAO.RecipeDAO;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.PriorityR;
import com.haulmont.testtask.model.Recipe;
import com.vaadin.ui.Window;

import java.util.*;


public class AddRecipeController extends AddRecipeView {

    private RecipeDAO recipeDAO = new RecipeDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();
    private PatientDAO patientDAO = new PatientDAO();
    Patient patient;
    Doctor doctor;
    public AddRecipeController(){
        getPriorityCombobox().setItems(PriorityR.getAsArrayList());
        getAddRecipeButton().addClickListener(clickEvent1 -> {
            //if the checkValuesRecipe() method returns true a new recipe is created and saved
            if(checkValuesRecipe()){
                Date dateOfcreation = new Date();
                Recipe newRecipe = new Recipe(doctor, patient,
                        getDescriptionTextArea().getValue(),
                        dateOfcreation,
                        getDeadlinesField().getValue(),
                        getPriorityCombobox().getValue().toString());
                recipeDAO.persist(newRecipe);
                ((Window)getParent()).close();}
        });
    }
    /**
     * method checkRecipe check TextFields and value of combobox and set to TextField and ComboBox error caption,
     * if textfield is empty. Also it return boolean value, which shows does the form pass validation
     */
    private boolean checkValuesRecipe(){
        boolean result = true;
        if(getDeadlinesField().getValue().equals("")){
            getDeadlinesField().setCaptionAsHtml(true);
            getDeadlinesField().setCaption("<span style='color: red;'>Cрок действия</span>");
            result = false;
        } else getDeadlinesField().setCaption("Cрок действия");
        if(getPriorityCombobox().getValue() == null){
            getPriorityCombobox().setCaptionAsHtml(true);
            getPriorityCombobox().setCaption("<span style='color: red;'>Приоритет</span>");
            result = false;
        } else getPriorityCombobox().setCaption("Приоритет");
        if(getDescriptionTextArea().getValue().equals("")){
            getDescriptionTextArea().setCaptionAsHtml(true);
            getDescriptionTextArea().setCaption("<span style='color: red;'>Рецепт</span>");
            result = false;
        } else getDescriptionTextArea().setCaption("Рецепт");
        checkDoctor();
        checkPatient();
       return (checkDoctor()&&checkPatient()&&result);
    }
    /**
     * method checkPatient() checks TextFields and set to TextField error caption, if textfield is empty.
     * method checkPatient() checks if a patient  with such name does not exist.
     * Method checkPatient() return boolean value, which shows right or wrong, filled or not fields, or existing patient with such name
     */
    private boolean checkPatient(){
        patient = patientDAO.findByNameAndSurnameAndPatronymic(getPatientNameField().getValue(),
                getPatientSurnameField().getValue(),
                getPatientPatronymicField().getValue());
        boolean result = true;
        if(patient == null){
            getPatientLayout().setCaptionAsHtml(true);
            getPatientLayout().setCaption("<span style='color: red;'>В нашей базе данных нет такого пациента!</span>");
            result =  false;
        } else  getPatientLayout().setCaption("");
        if(getPatientNameField().getValue().equals("")){
            getPatientNameField().setCaptionAsHtml(true);
            getPatientNameField().setCaption("<span style='color: red;'>Введите имя</span>");
            result = false;
        } else  getPatientNameField().setCaption("Имя");
        if(getPatientSurnameField().getValue().equals("")){
            getPatientSurnameField().setCaptionAsHtml(true);
            getPatientSurnameField().setCaption("<span style='color: red;'>Введите фамилию</span>");
            result = false;
        }else  getPatientSurnameField().setCaption("Фамилия");
        if(getPatientPatronymicField().getValue().equals("")){
            getPatientPatronymicField().setCaptionAsHtml(true);
            getPatientPatronymicField().setCaption("<span style='color: red;'>Введите отчество</span>");
            result = false;
        } else  getPatientPatronymicField().setCaption("Отчество");
        return result;
    }
    /**
     * method checkDoctor() checks TextFields and set to TextField error caption, if textfield is empty.
     * method checkDoctor() checks if a doctor  with such name does not exist.
     * Method checkDoctor() return boolean value, which shows right or wrong, filled or not fields, or existing doctor with such name
     */
    private boolean checkDoctor(){
        doctor = doctorDAO.findByNameAndSurnameAndPatronymic(getDoctorNameField().getValue(),
                getDoctorSurnameField().getValue(),
                getDoctorPatronymicField().getValue());
        boolean result = true;
        if(doctor == null){
            getDoctorLayout().setCaptionAsHtml(true);
            getDoctorLayout().setCaption("<span style='color: red;'>В нашей поликлинике такого врача не существует!</span>");
            return false;
        }else  getDoctorLayout().setCaption("");
        if(getDoctorNameField().getValue().equals("")){
            getDoctorNameField().setCaptionAsHtml(true);
            getDoctorNameField().setCaption("<span style='color: red;'>Введите имя</span>");
            result = false;
        } else  getDoctorNameField().setCaption("Имя");
        if(getDoctorSurnameField().getValue().equals("")){
            getDoctorSurnameField().setCaptionAsHtml(true);
            getDoctorSurnameField().setCaption("<span style='color: red;'>Введите фамилию</span>");
            result = false;
        } else  getDoctorSurnameField().setCaption("Фамилия");
        if(getDoctorPatronymicField().getValue().equals("")){
            getDoctorPatronymicField().setCaptionAsHtml(true);
            getDoctorPatronymicField().setCaption("<span style='color: red;'>Введите отчество</span>");
            result = false;
        } else  getDoctorPatronymicField().setCaption("Отчество");
        return result;
    }
}