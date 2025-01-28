package sn.zomethdev.school_management_jpa.dao;


import sn.zomethdev.school_management_jpa.entities.Classe;

public interface IClasse extends Repository<Classe> {
    Classe getClasseByName(String name);
}
