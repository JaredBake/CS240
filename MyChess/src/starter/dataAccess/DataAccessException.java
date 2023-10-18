package dataAccess;

/**
 * Indicates there was an error connecting to the database
 * 1) Create objects in the data store, 2) Read (or query) objects from the data store, 3) Update objects already in the data store, and 4) Delete objects from the data store
 */
public class DataAccessException extends Exception{
    public DataAccessException(String message) {
        super(message);
    }

}
