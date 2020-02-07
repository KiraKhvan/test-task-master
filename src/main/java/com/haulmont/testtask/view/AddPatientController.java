package com.haulmont.testtask.view;

import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.model.Patient;
import com.vaadin.ui.Window;

public class AddPatientController extends AddPatientView {

    private PatientDAO patientDAO = new PatientDAO();
    Patient patient;

    public AddPatientController(){
        getSaveButton().addClickListener(clickEvent1 -> {
            //if the checkFields() method returns true a new patient is created and saved
            if(checkFields()){
                Patient newPatient = new Patient(getNameField().getValue(),
                        getSurnameField().getValue(),
                        getPatronymicField().getValue(),
                        getPhoneField().getValue());
                patientDAO.persist(newPatient);
                ((Window)getParent()).close();
            }
        });
    }
    /**
     * method checkFields() checks TextFields and set to TextField error caption, if textfield is empty.
     * method checkFields() checks if a patient exists with such name.
     * Method checkFields() return boolean value, which shows right or wrong, filled or not fields, or existing patient with such name
     */
    private boolean checkFields(){
        patient = patientDAO.findByNameAndSurnameAndPatronymic(getNameField().getValue(),
                getSurnameField().getValue(),
                getPatronymicField().getValue());

        boolean result = true;
        if(patient != null){
            getAddLayout().setCaption("<span style='color: red;'>В нашей базе данных такой пациент уже есть! </span>");
            return false;
        }

        if(getNameField().getValue().equals("")){
            getNameField().setCaption("<span style='color: red;'>Введите имя</span>");
            result = false;
        } else  getNameField().setCaption("Введите имя");

        if(getSurnameField().getValue().equals("")){
            getSurnameField().setCaption("<span style='color: red;'>Введите фамилию</span>");
            result = false;
        } else  getSurnameField().setCaption("Введите фамилию");

        if(getPatronymicField().getValue().equals("")){
            getPatronymicField().setCaption("<span style='color: red;'>Введите отчество</span>");
            result = false;
        } else  getPatronymicField().setCaption("Введите отчество");

        if(getPhoneField().getValue().equals("")){
            getPhoneField().setCaption("<span style='color: red;'>Введите телефон:</span>");
            result = false;
        } else  getPhoneField().setCaption("Введите телефон");

        return result;
    }
}