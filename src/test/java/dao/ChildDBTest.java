package dao;

import model.Child;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ChildTestFactory;
import utils.DBUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Child Database Operations Tests")
class ChildDBTest {

    private ChildDB db;

    @BeforeEach
    void setUp() throws SQLException, IOException {
        new DBUtil().executeFile("init.sql");
        db = new ChildDB();
    }

    @AfterEach
    void tearDown() throws Exception {
        db.close();
    }

    @Test
    @DisplayName("Should add a child and return it with an ID")
    void addShouldAddChildAndReturnWithId() throws SQLException {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        LocalDate birthDate = LocalDate.of(2010, 1, 1);
        Child child = new Child(firstName, lastName, birthDate);

        // Act
        Child addedChild = db.addChild(child);
        System.out.println("[DEBUG_LOG] Added child ID: " + addedChild.id());

        // Assert
        assertNotNull(addedChild.id(), "Child ID should not be null");
        assertEquals(firstName, addedChild.firstName(), "First name should match");
        assertEquals(lastName, addedChild.lastName(), "Last name should match");
        assertEquals(birthDate, addedChild.birthDate(), "Birth date should match");
    }

    @Test
    @DisplayName("Should add a child with null birth date")
    void addShouldHandleNullBirthDate() throws SQLException {
        // Arrange
        Child child = ChildTestFactory.withoutBirthDate();

        // Act
        Child addedChild = db.addChild(child);

        // Assert
        Child fromDb = db.getChildById(addedChild.id());
        assertNotNull(fromDb.id());
        assertEquals(addedChild.firstName(), fromDb.firstName());
        assertEquals(addedChild.lastName(), fromDb.lastName());
        assertNull(fromDb.birthDate());
    }

    @Test
    @DisplayName("Should update an existing child")
    void updateShouldUpdateExistingChild() throws SQLException {
        // Arrange - Add a child first
        Child addedChild = db.addChild(ChildTestFactory.random());

        // Create updated child
        Child updatedChild = ChildTestFactory.withId(addedChild.id());

        // Act
        boolean updateResult = db.updateChild(updatedChild);

        // Assert
        assertTrue(updateResult, "Update operation should return true when addedChild is successfully updated");

        // Verify the update by querying the database
        Child fromDb = db.getChildById(addedChild.id());
        assertEquals(updatedChild.firstName(), fromDb.firstName());
        assertEquals(updatedChild.lastName(), fromDb.lastName());
        assertEquals(updatedChild.birthDate(), fromDb.birthDate());
    }

    @Test
    @DisplayName("Should delete an existing child")
    void deleteShouldDeleteExistingChild() throws SQLException {
        // Arrange - Add a child first
        Child addedChild = db.addChild(ChildTestFactory.random());

        // Act
        boolean deleteResult = db.deleteChild(addedChild.id());

        // Assert
        assertTrue(deleteResult, "Delete operation should return true when child is successfully deleted");

        // Verify the deletion by querying the database
        assertFalse(db.existsById(addedChild.id()));
    }


    @Test
    @DisplayName("Should return children with at least the specified age")
    void findChildrenWithMinimumAgeShouldReturnChildrenWithMinimumAge() throws SQLException {
        // Arrange - Add children with different ages
        // Child 1 - 10 years old
        Child addedChild10 = db.addChild(ChildTestFactory.ofAge(10));
        // Child 2 - 5 years old
        Child addedChild5 = db.addChild(ChildTestFactory.ofAge(5));
        // Child 3 - 15 years old
        Child addedChild15 = db.addChild(ChildTestFactory.ofAge(15));

        // Act - Get all children at least 10 years old
        List<Child> childrenWithMinimumAge = db.findChildrenWithMinimumAge(10);

        // Assert
        assertFalse(childrenWithMinimumAge.isEmpty(), "Result should not be empty");

        // Verify that the result contains children with correct ages
        assertTrue(childrenWithMinimumAge.stream().anyMatch(c -> c.id().equals(addedChild10.id())), "Should contain 10-year-old child");
        assertTrue(childrenWithMinimumAge.stream().anyMatch(c -> c.id().equals(addedChild15.id())), "Should contain 15-year-old child");
        assertFalse(childrenWithMinimumAge.stream().anyMatch(c -> c.id().equals(addedChild5.id())), "Should NOT contain 5-year-old child");
    }

    @Test
    @DisplayName("Should return children with null birth date")
    void findChildrenWithoutBirthDateShouldReturnChildrenWithNullBirthDate() throws SQLException {
        // Arrange - Add children with and without birth dates
        // Child with birth date
        Child addedChildWithBD = db.addChild(ChildTestFactory.random());
        // Child without birth date
        Child addedChildWithoutBD = db.addChild(ChildTestFactory.withoutBirthDate());

        // Act
        List<Child> result = db.findChildrenWithoutBirthDate();

        // Assert
        assertTrue(result.stream().anyMatch(c -> c.id().equals(addedChildWithoutBD.id())), "Should contain child without birth date");
        assertFalse(result.stream().anyMatch(c -> c.id().equals(addedChildWithBD.id())), "Should NOT contain child with birth date");

        // Verify that the result contains the child without birth date
        assertTrue(result.stream().allMatch(c -> c.birthDate() == null), "All children should have null birth date");
    }
}

