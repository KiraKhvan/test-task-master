package com.haulmont.testtask.DAO;

import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class PatientDAO implements PatientDaoInterface<Patient> {

    public PatientDAO() {
    }

    public void persist(Patient entity) {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        session2.save(entity);
        session2.getTransaction().commit();
        session2.close();
        sessionFactory2.close();
    }

    public void update(Patient entity) {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        session2.beginTransaction();
        session2.update(entity);
        session2.getTransaction().commit();
        session2.close();
        sessionFactory2.close();
    }
    public void delete(Patient entity) {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        session2.delete(entity);
        session2.getTransaction().commit();
        session2.close();
        sessionFactory2.close();
    }

    public List<Patient> findAll() {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        List<Patient> patients =  session2.createCriteria(Patient.class).list(); /*getCurrentSession().createQuery("from Doctor ").list();*/
        session2.close();
        sessionFactory2.close();
        return patients;
    }

    public void deleteAll() {
        List<Patient> entityList = findAll();
        for (Patient entity : entityList) {
            delete(entity);
        }
    }

    public Patient findById(Long id) {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory2.openSession();
        session.beginTransaction();
        Patient patient = (Patient)session.get(Patient.class, id);
        session.close();
        sessionFactory2.close();
        return patient;

    }
    public Patient findByNameAndSurnameAndPatronymic(String name, String surname, String patronymic) {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        Criteria cr = session2.createCriteria(Patient.class);
        cr.add(Restrictions.eq("name", name));
        cr.add(Restrictions.eq("surname", surname));
        cr.add(Restrictions.eq("patronymic", patronymic));
        Optional<List<Patient>> optionallist = Optional.of(cr.list());
        List<Patient> patients = optionallist.get();
        Patient patient;
        if (patients.size() !=0) {
            patient = patients.get(0);
        }else{
            patient = null;
        }
        session2.close();
        sessionFactory2.close();
        return patient;
    }

}
