package com.haulmont.testtask.DAO;

import com.haulmont.testtask.model.Doctor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorDAO implements DoctorDaoInterface<Doctor> {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public DoctorDAO() {
    }

    public void persist(Doctor entity) {
       SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        session2.save(entity);
        session2.getTransaction().commit();
        session2.close();
        sessionFactory2.close();
    }

    public void update(Doctor entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

    }

    public Doctor findById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Doctor doctor = (Doctor)session.get(Doctor.class, id);
        session.close();
        sessionFactory.close();
        return doctor;
    }

    public void delete(Doctor entity) {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        session2.delete(entity);
        session2.getTransaction().commit();
        session2.close();
        sessionFactory2.close();
    }

    @SuppressWarnings("unchecked")
    public List<Doctor> findAll() {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        List<Doctor> doctors =  session2.createCriteria(Doctor.class).list(); /*getCurrentSession().createQuery("from Doctor ").list();*/

        session2.close();
        sessionFactory2.close();
        return doctors;
    }

    public void deleteAll() {
        List<Doctor> entityList = findAll();
        for (Doctor entity : entityList) {
            delete(entity);
        }
    }
    public Doctor findByNameAndSurnameAndPatronymic(String name, String surname, String patronymic) {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        Criteria cr = session2.createCriteria(Doctor.class);
        cr.add(Restrictions.eq("name", name));
        cr.add(Restrictions.eq("surname", surname));
        cr.add(Restrictions.eq("patronymic", patronymic));
        Optional<List<Doctor>> optionallist = Optional.of(cr.list());
        List<Doctor> doctors = optionallist.get();
        Doctor doctor;
        if (doctors.size() !=0) {
            doctor = doctors.get(0);
        }else{
            doctor = null;
        }
        session2.close();
        sessionFactory2.close();
        return doctor;
    }
}
