package sn.zomethdev.school_management_jpa.dao;


import sn.zomethdev.school_management_jpa.entities.Etudiant;

import java.util.ArrayList;

public interface IEtudiant extends Repository<Etudiant> {
    ArrayList<Etudiant> getEtudiantByClassName(String classe);
}
