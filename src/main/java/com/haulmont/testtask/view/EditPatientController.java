package com.haulmont.testtask.view;

import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.model.Patient;
import com.vaadin.ui.Window;

public class EditPatientController extends EditPatientView {

    private PatientDAO patientDAO = new PatientDAO();
    public EditPatientController(Patient patient){
        getNameField().setValue(patient.getName());
        getPatronymicField().setValue(patient.getPatronymic());
        getPhoneField().setValue(patient.getPhone());
        getSurnameField().setValue(patient.getSurname());
        getEditButton().addClickListener(clickEvent -> {
            if(checkEditFields(patient)){
                patient.setName(getNameField().getValue());
                patient.setSurname(getSurnameField().getValue());
                patient.setPatronymic(getPatronymicField().getValue());
                patient.setPhone(getPhoneField().getValue());
                patientDAO.update(patient);
                ((Window)getParent()).close();
            }
        });
        getCancelButton().addClickListener(clickEvent -> {
            ((Window)getParent()).close();
        });
    }
    /**
     * method checkEditFields check TextFields and set to TextField error caption,
     * if textfield is empty, also it return boolean value, which shows right or wrong, filled or not fields
     */
    private boolean checkEditFields(Patient patient){
        boolean result = true;
        Patient checkpatient = patientDAO.findByNameAndSurnameAndPatronymic(getNameField().getValue(),
                getSurnameField().getValue(),
                getPatronymicField().getValue());
        if(checkpatient != null){
            if(checkpatient.getId() !=patient.getId()){
                getEditLayout().setCaption("<span style='color: red;'>Пациент с таким ФИО уже существует</span>");
                result = false;
            }
        }
        if(getNameField().getValue().equals("")){
            getNameField().setCaption("<span style='color: red;'>Введите имя</span>");
            result = false;
        } else getNameField().setCaption("Введите имя");
        if(getSurnameField().getValue().equals("")){
            getSurnameField().setCaption("<span style='color: red;'>Введите фамилию</span>");
            result = false;
        } else getSurnameField().setCaption("Введите фамилию");
        if(getPatronymicField().getValue().equals("")){
            getPatronymicField().setCaption("<span style='color: red;'>Введите отчество</span>");
            result = false;
        } else getPatronymicField().setCaption("Введите отчество");
        if(getPhoneField().getValue().equals("")){
            getPhoneField().setCaption("<span style='color: red;'>Введите телефон</span>");
            result = false;
        } else getPhoneField().setCaption("Введите телефон");
        return result;
    }
}
