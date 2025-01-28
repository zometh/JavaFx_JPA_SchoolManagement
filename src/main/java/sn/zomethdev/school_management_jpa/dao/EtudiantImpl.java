package sn.zomethdev.school_management_jpa.dao;


import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sn.zomethdev.school_management_jpa.entities.Classe;
import sn.zomethdev.school_management_jpa.entities.Etudiant;
import sn.zomethdev.school_management_jpa.util.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class EtudiantImpl implements IEtudiant {

    private final EntityManager entityManager;

    public EtudiantImpl() {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

    }

    @Override
    public ArrayList<Etudiant> getEtudiantByClassName(String classe) {
        return null;
    }

    @Override
    public void add(Etudiant entity) {
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
    public void update(Etudiant entity) {
        try {
            entityManager.getTransaction().begin();
            Etudiant managedEntity = entityManager.merge(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(Etudiant entity) {
        try {
            entityManager.getTransaction().begin();
            Etudiant mergedClasse = entityManager.merge(entity);
            entityManager.remove(mergedClasse);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }

    }

    @Override
    public ObservableList<Etudiant> list() {

        List<Etudiant> datas = entityManager
                .createQuery("SELECT c FROM Etudiant c", Etudiant.class)
                .getResultList();


        return FXCollections.observableArrayList(datas);

    }

    public ObservableList<Etudiant> etudiantsByClasse(Classe classe) {

        List<Etudiant> datas = entityManager
                .createQuery("SELECT e FROM Etudiant e WHERE e.classe = :classe", Etudiant.class)
                .setParameter("classe", classe)
                .getResultList();
        return FXCollections.observableArrayList(datas);
    }

    @Override
    public Etudiant get(int id) {

        return entityManager.find(Etudiant.class, id);
    }


}
