package com.haulmont.testtask.DAO;

import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Recipe;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class RecipeDAO  implements RecipeDaoInterface<Recipe> {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public RecipeDAO() {
    }

    public void persist(Recipe entity) {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        session2.save(entity);
        session2.getTransaction().commit();
        session2.close();
        sessionFactory2.close();
    }

    public void update(Recipe entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

    }
    public void delete(Recipe entity) {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        session2.delete(entity);
        session2.getTransaction().commit();
        session2.close();
        sessionFactory2.close();
    }

    public List<Recipe> findAll() {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        List<Recipe> recipes =  session2.createCriteria(Recipe.class).list(); /*getCurrentSession().createQuery("from Doctor ").list();*/
        session2.close();
        sessionFactory2.close();
        return recipes;
    }

    public void deleteAll() {
        List<Recipe> entityList = findAll();
        for (Recipe entity : entityList) {
            delete(entity);
        }
    }

    public Recipe findById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Recipe recipe = (Recipe)session.get(Recipe.class, id);
        session.close();
        sessionFactory.close();
        return recipe;

    }
    public List<Recipe> findByPatientAndPriotity(Patient patient, String priorityR){
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        Optional<List<Recipe>> optionallist = Optional.of(session2.createQuery("from Recipe where patient="
                +patient.getId()+  " and priorityR=" + priorityR).list());
        List<Recipe> recipes = optionallist.get();
        session2.close();
        sessionFactory2.close();
        return recipes;
    }
    public List<Recipe>  findByPatient(Patient patient){
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        Optional<List<Recipe>> optionallist = Optional.of(session2.createQuery("from Recipe where patient=" +patient.getId()).list());
        List<Recipe> recipes = optionallist.get();
        session2.close();
        sessionFactory2.close();
        return recipes;
    }
    public List<Recipe> findByPatientAndDescription(Patient patient, String description){
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        Query query = session2.createQuery("from Recipe where patient=" +patient.getId()+ " and description like CONCAT('%',?,'%')");
        query.setParameter(0, description);
        Optional<List<Recipe>> optionallist = Optional.of(query.list());
        List<Recipe> recipes = optionallist.get();
        session2.close();
        sessionFactory2.close();
        return recipes;
    }
    public List<Recipe>  findByDescription(String description){
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        Query query = session2.createQuery("from Recipe where description like CONCAT('%',?,'%')");
        query.setParameter(0, description);
        Optional<List<Recipe>> optionallist = Optional.of(query.list());
        List<Recipe> recipes = optionallist.get();
        session2.close();
        sessionFactory2.close();
        return recipes;
    }
    public List<Recipe> findByDescriptionAndPriotity(String description,String priorityR){
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        Query query = session2.createQuery("from Recipe where description like CONCAT('%',?,'%') and priorityR=?");
        query.setParameter(0, description);
        query.setParameter(1, priorityR);
        Optional<List<Recipe>> optionallist = Optional.of(query.list());
        List<Recipe> recipes = optionallist.get();
        session2.close();
        sessionFactory2.close();
        return recipes;
    }
    public List<Recipe> findByPatientAndDescriptionAndPriotity(Patient patient, String description,String priorityR){
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        Query query = session2.createQuery("from Recipe where patient="  +patient.getId()+ " and description like CONCAT('%',?,'%') and priorityR=?");
        query.setParameter(0, description);
        query.setParameter(1, priorityR);
        Optional<List<Recipe>> optionallist = Optional.of(query.list());
        List<Recipe> recipes = optionallist.get();
        session2.close();
        sessionFactory2.close();
        return recipes;
    }
    public List<Recipe> findByPriority(String priorityR){
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        //session2.createQuery("from Recipe where priority=" +priority ).list();
        Query query = session2.createQuery("from Recipe where priorityR=?");
        query.setParameter(0, priorityR);
        Optional<List<Recipe>> optionallist = Optional.of(query.list());
       // Optional<List<Recipe>> optionallist = Optional.of(session2.createQuery("from Recipe where priorityR=" + priorityR).list());
        List<Recipe> recipes = optionallist.get();

        session2.close();
        sessionFactory2.close();
        return recipes;
    }
    public List<Recipe> findByDoctor(Doctor doctor) {
        SessionFactory sessionFactory2 = new Configuration().configure().buildSessionFactory();
        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        Criteria cr = session2.createCriteria(Recipe.class);
        session2.createQuery("from Recipe where doctor=" +doctor.getId() ).list();
       // cr.add(Restrictions.eq("doctor_id", doctor.getId()));
        Optional<List<Recipe>> optionallist = Optional.of(session2.createQuery("from Recipe where doctor=" +doctor.getId() ).list());
        List<Recipe> recipes = optionallist.get();
        session2.close();
        sessionFactory2.close();
        return recipes;
    }

}

