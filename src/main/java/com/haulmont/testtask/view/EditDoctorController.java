package com.haulmont.testtask.view;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.model.Doctor;
import com.vaadin.ui.Window;

public class EditDoctorController extends EditDoctorView {

    private DoctorDAO doctorDAO = new DoctorDAO();
    public EditDoctorController(Doctor doctor){
        getNameField().setValue(doctor.getName());
        getPatronymicField().setValue(doctor.getPatronymic());
        getSpecialisationField().setValue(doctor.getSpecialization());
        getSurnameField().setValue(doctor.getSurname());
        getEditButton().addClickListener(clickEvent -> {
            if(checkEditFields(doctor)){
                doctor.setName(getNameField().getValue());
                doctor.setSurname(getSurnameField().getValue());
                doctor.setPatronymic(getPatronymicField().getValue());
                doctor.setSpecialization(getSpecialisationField().getValue());
                doctorDAO.update(doctor);
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
    private boolean checkEditFields(Doctor doctor){
        boolean result = true;
         Doctor checkdoctor = doctorDAO.findByNameAndSurnameAndPatronymic(getNameField().getValue(),
                getSurnameField().getValue(),
                getPatronymicField().getValue());
        if(checkdoctor != null){
            if(checkdoctor.getId() !=doctor.getId()){
                getEditLayout().setCaption("<span style='color: red;'>Врач с таким ФИО уже существует</span>");
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
        if(getSpecialisationField().getValue().equals("")){
            getSpecialisationField().setCaption("<span style='color: red;'>Введите специализацию</span>");
            result = false;
        } else getSpecialisationField().setCaption("Введите специализацию");
        return result;
    }
}
