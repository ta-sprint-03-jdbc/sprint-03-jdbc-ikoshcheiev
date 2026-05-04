package dao;

import model.Child;

import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for Child entities.
 * Provides methods to perform CRUD operations on children.
 */
public interface ChildDAO {

    /**
     * Adds a new child to the database.
     *
     * @param child The child to add
     * @return The added child with its ID
     * @throws SQLException             If a database access error occurs
     * @throws IllegalArgumentException If a child is null or has invalid data
     */
    Child addChild(Child child) throws SQLException;

    /**
     * Updates an existing child in the database.
     *
     * @param child The child to update
     * @return true if the child was updated, false otherwise
     * @throws SQLException             If a database access error occurs
     * @throws IllegalArgumentException If a child is null or has invalid data
     */
    boolean updateChild(Child child) throws SQLException;

    /**
     * Deletes a child from the database.
     *
     * @param id The ID of the child to delete
     * @return true if the child was deleted, false otherwise
     * @throws SQLException             If a database access error occurs
     * @throws IllegalArgumentException If id is null
     */
    boolean deleteChild(Long id) throws SQLException;

    /**
     * Finds all children with at least the specified age.
     *
     * @param age The minimum age of children to find
     * @return A list of children with at least the specified age
     * @throws SQLException             If a database access error occurs
     * @throws IllegalArgumentException If age is negative
     */
    List<Child> findChildrenWithMinimumAge(int age) throws SQLException;

    /**
     * Finds all children without a birthdate.
     *
     * @return A list of children without a birthdate
     * @throws SQLException If a database access error occurs
     */
    List<Child> findChildrenWithoutBirthDate() throws SQLException;

    /**
     * Retrieves a child by its id.
     *
     * @param childId child identifier
     * @return matching child, or null if not found
     * @throws SQLException if a database access error occurs
     */
    Child getChildById(long childId) throws SQLException;

    /**
     * Checks whether a child with the specified id exists in the database.
     *
     * @param id child identifier
     * @return true if a child with the given id exists, false otherwise
     * @throws SQLException             if a database access error occurs
     * @throws IllegalArgumentException if id is invalid (e.g. negative, if validation is applied)
     */
    boolean existsById(long id) throws SQLException;
}