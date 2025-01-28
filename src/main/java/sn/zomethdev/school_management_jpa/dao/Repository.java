package sn.zomethdev.school_management_jpa.dao;

import javafx.collections.ObservableList;

public interface Repository<T> {
    void add(T entity);

    void update(T entity);

    void delete(T entity);

    ObservableList<T> list();

    T get(int id);
}
