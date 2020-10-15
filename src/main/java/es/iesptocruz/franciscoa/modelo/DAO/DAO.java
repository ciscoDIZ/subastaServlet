package es.iesptocruz.franciscoa.modelo.DAO;

public interface DAO<T> {
    void insert();
    void delete();
    T get();
}
