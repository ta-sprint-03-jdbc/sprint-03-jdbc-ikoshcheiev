package dao;

import model.Category;
import model.Child;

import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for Category entities.
 * Provides methods to perform CRUD operations on categories.
 */
public interface CategoryDAO {
    
    /**
     * Adds a new category to the database if it doesn't exist.
     * If a category with the same title already exists, returns the existing category.
     *
     * @param category The category to add
     * @return The added or existing category with its ID
     * @throws SQLException If a database access error occurs
     * @throws IllegalArgumentException If a category is null or has invalid data
     */
    Category addCategory(Category category) throws SQLException;
    
    /**
     * Updates an existing category in the database.
     *
     * @param category The category to update
     * @return true if the category was updated, false otherwise
     * @throws SQLException If a database access error occurs
     * @throws IllegalArgumentException If a category is null or has invalid data
     */
    boolean updateCategory(Category category) throws SQLException;
    
    /**
     * Deletes a category from the database.
     *
     * @param id The ID of the category to delete
     * @return true if the category was deleted, false otherwise
     * @throws SQLException If a database access error occurs
     * @throws IllegalArgumentException If id is null
     */
    boolean deleteCategory(Long id) throws SQLException;
    
    /**
     * Finds categories whose titles contain the given string (case-insensitive).
     *
     * @param titlePart The string to search for in category titles
     * @return A list of matching categories
     * @throws SQLException If a database access error occurs
     * @throws IllegalArgumentException If titlePart is null
     */
    List<Category> findCategoriesByTitlePart(String titlePart) throws SQLException;
    
    /**
     * Finds all children associated with a category.
     *
     * @param categoryId The ID of the category
     * @return A list of children associated with the category
     * @throws SQLException If a database access error occurs
     * @throws IllegalArgumentException If categoryId is null
     */
    List<Child> findAllChildrenInCategory(Long categoryId) throws SQLException;

    /**
     * Retrieves a category by its id.
     *
     * @param categoryId category identifier
     * @return matching category, or null if not found
     * @throws SQLException if a database access error occurs
     */
    Category getCategoryById(long categoryId) throws SQLException;

    /**
     * Retrieves a category by its exact title.
     *
     * @param title category title
     * @return matching category, or null if not found
     * @throws SQLException if a database access error occurs
     * @throws IllegalArgumentException if title is null or empty
     */
    Category getCategoryByTitle(String title) throws SQLException;

}