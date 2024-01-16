package edu.qc.seclass.glm.classes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GroceryListTest {
    GroceryListManager glm;
    DBHelper dbHelper;
    Item testItem1;

    @Before
    public void setUp() throws Exception {
        dbHelper = mock(DBHelper.class);
        glm = new GroceryListManager();
        glm.setDbHelper(dbHelper);
        glm.addGroceryList("Weekly List");
        glm.selectGroceryList(0);

        testItem1 = new Item();
        testItem1.setName("orange");
        testItem1.setType("fruits");
        testItem1.setId(1);
    }

    //TC - 05
    @Test
    public void testAddLineItem(){
        //maybe change update to addGLIToList?
        assertTrue(glm.getSelectedGroceryList().updateGroceryLineItem(testItem1,5,false,1));
    }

    @Test
    public void testAddLineItemWhereItemIsNull(){
        //maybe change update to addGLIToList?
        assertFalse(glm.getSelectedGroceryList().updateGroceryLineItem(null,5,false,1));
    }

    @Test
    public void testAddLineItemWhereItemContainsANullValue(){
        testItem1.setName(null);
        testItem1.setType(null);

        //maybe change update to addGLIToList?
        assertFalse(glm.getSelectedGroceryList().updateGroceryLineItem(testItem1,5,false,1));
    }

    //TC - 05b
    //The method tested does a very similar thing as the one in the previous test
    //but this one also adds the line item to the database
    @Test
    public void testCreateNewLineItem(){
        Item item = new Item();
        item.setName("Grapes");
        item.setType("Fruits");
        item.setId(1);
        assertTrue(glm.getSelectedGroceryList().createGroceryLineItem(item,1,false));
    }

    @After
    public void tearDown() throws Exception {
    }


}