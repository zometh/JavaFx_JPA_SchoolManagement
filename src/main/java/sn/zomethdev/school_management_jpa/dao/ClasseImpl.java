package sn.zomethdev.school_management_jpa.dao;

import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sn.zomethdev.school_management_jpa.entities.Classe;
import sn.zomethdev.school_management_jpa.util.JPAUtil;

import java.util.List;

public class ClasseImpl implements IClasse {
    final private EntityManager entityManager;

    public ClasseImpl() {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public Classe getClasseByName(String name) {
        return null;
    }

    @Override
    public void add(Classe entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public void update(Classe entity) {
        try {
            entityManager.getTransaction().begin();
            Classe managedEntity = entityManager.merge(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(Classe entity) {

        try {
            entityManager.getTransaction().begin();
            Classe mergedClasse = entityManager.merge(entity);
            entityManager.remove(mergedClasse);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    public boolean isClassExist(String nom) {

        List<Classe> result = entityManager
                .createQuery("SELECT c FROM Classe c WHERE c.nom = :nom", Classe.class)
                .setParameter("nom", nom)
                .getResultList();
        return !result.isEmpty();
    }

    @Override
    public ObservableList<Classe> list() {
        EntityManager manager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            manager.getTransaction().begin();
            List<Classe> datas = manager
                    .createQuery("SELECT c FROM Classe c", Classe.class)
                    .getResultList();
            manager.getTransaction().commit();
            return FXCollections.observableArrayList(datas);

        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();

            }
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }

    @Override
    public Classe get(int id) {

        List<Classe> result = entityManager
                .createQuery("SELECT c FROM Classe c WHERE c.id = :id", Classe.class)
                .setParameter("id", id)
                .getResultList();
        return result.getFirst();
    }


}
