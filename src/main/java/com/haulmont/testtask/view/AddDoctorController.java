package com.haulmont.testtask.view;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.model.Doctor;
import com.vaadin.ui.Window;

public class AddDoctorController extends AddDoctorView {

    private DoctorDAO doctorDAO = new DoctorDAO();
    private Doctor doctor;


    public AddDoctorController(){
        getSaveButton().addClickListener(clickEvent1 -> {

            //if the checkFields() method returns true a new doctor is created and saved
            if(checkFields()) {
                Doctor newDoctor = new Doctor(getNameField().getValue(),
                        getSurnameField().getValue(),
                        getPatronymicField().getValue(),
                        getSpecializationField().getValue());
                doctorDAO.persist(newDoctor);
                ((Window) getParent()).close();
            }
        });
    }
    /**
     * method checkFields() checks TextFields and set to TextField error caption, if textfield is empty.
     * method checkFields() checks if a doctor exists with such name.
     * Method checkFields() return boolean value, which shows right or wrong, filled or not fields, or existing doctor with such name
     */
    private boolean checkFields(){
        doctor = doctorDAO.findByNameAndSurnameAndPatronymic(getNameField().getValue(),
                getSurnameField().getValue(),
                getPatronymicField().getValue());

        boolean result = true;
        if(doctor != null){
            getAddLayout().setCaption("<span style='color: red;'>В нашей базе данных такой пациент уже есть! </span>");
            return false;
        }
        if(getNameField().getValue().equals("")){
            getNameField().addStyleName("error");
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
        if(getSpecializationField().getValue().equals("")){
            getSpecializationField().setCaption("<span style='color: red;'>Введите специализацию:</span>");
            result = false;
        } else  getSpecializationField().setCaption("Введите специализацию");
        return result;
    }
}
