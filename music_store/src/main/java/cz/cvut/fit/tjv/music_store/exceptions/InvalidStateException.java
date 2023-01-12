package cz.cvut.fit.tjv.music_store.exceptions;


public class InvalidStateException extends RuntimeException {
    public InvalidStateException(){}

    public <E> InvalidStateException(E entity){
        super("Data in" + entity + "are invalid");
    }

    public InvalidStateException(String s){
        super(s);
    }
}

