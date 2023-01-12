package cz.cvut.fit.tjv.music_store.domain;

/*
    This class is an abstract class (interface) and it is a common predeccesor for all
    Data Entities

    Classes that are implementing this DomainEntity interface are data object
    - they represent data that object will hold in the database
*/

public interface DomainEntity<T> {

    T getId();
    void setId(T id);
}
