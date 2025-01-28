package sn.zomethdev.school_management_jpa.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "classe")
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "effectif")
    private int effectif;

    /*public Classe(String nom, int id, int effectif) {
        this.nom = nom;
        this.id = id;
        this.effectif = effectif;
    }*/
    public Classe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEffectif() {
        return effectif;
    }

    public void setEffectif(int effectif) {
        this.effectif = effectif;
    }

    @Override
    public String toString() {
        return getNom();
    }
}
