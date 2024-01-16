package edu.qc.seclass.glm.classes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
public class GroceryListManagerTest {
    GroceryListManager glm;
    DBHelper dbHelper;

    @Before
    public void setUp() throws Exception {
        dbHelper = mock(DBHelper.class);
        glm = new GroceryListManager();
        glm.setDbHelper(dbHelper);
    }

    //TC - 01
    @Test
    public void testAddGroceryList(){
        //DBHelper db = new DBHelper();
        glm.addGroceryList("my list");
        assertEquals(1,glm.getGroceryLists().size());
    }

    @Test
    public void testAddGroceryListNameIsNull(){
        //DBHelper db = new DBHelper();

        assertFalse(glm.addGroceryList(null));
    }

    //TC - 02
    @Test
    public void testSelectList(){
        glm.addGroceryList("my list");

        assertEquals(true,glm.selectGroceryList(0));
    }

    @Test
    public void testSelectListIndexGreaterThanSize(){
        glm.addGroceryList("my list");

        assertFalse(glm.selectGroceryList(2));
    }

    @Test
    public void testSelectListIndexLessThanZero(){
        glm.addGroceryList("my list");

        assertFalse(glm.selectGroceryList(-1));
    }


    //TC - 03
    @Test
    public void testRenameSelectedList(){
        glm.addGroceryList("first");
        glm.selectGroceryList(0);
        assertTrue(glm.renameGroceryList("First List"));
    }

    @Test
    public void testRenameSelectedListWhereSelectedIsNull(){
        glm.addGroceryList("first");
        glm.selectGroceryList(-1);
        assertFalse(glm.renameGroceryList("First List"));
    }

    //TC - 04
    @Test
    public void testRemoveList(){
        glm.addGroceryList("first list");
        glm.addGroceryList("second list");

        assertTrue(glm.removeGroceryList(1));
    }

    @Test
    public void testRemoveListIndexGreaterThanSize(){
        glm.addGroceryList("my list");

        assertFalse(glm.removeGroceryList(2));
    }

    @Test
    public void testRemoveListIndexLessThanZero(){
        glm.addGroceryList("my list");

        assertFalse(glm.removeGroceryList(-1));
    }






    @After
    public void tearDown() throws Exception {
    }
}