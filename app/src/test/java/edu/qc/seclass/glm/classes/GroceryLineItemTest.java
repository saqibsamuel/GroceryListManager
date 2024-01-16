package edu.qc.seclass.glm.classes;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import edu.qc.seclass.glm.SearchItems;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GroceryLineItemTest {
    GroceryListManager glm;
    DBHelper dbHelper;
    Item testItem1;
    GroceryLineItem lineItem;
    SearchItems searchItems;

    @Before
    public void setUp() throws Exception {
        searchItems = mock(SearchItems.class);
        dbHelper = mock(DBHelper.class);
        glm = new GroceryListManager();
        glm.setDbHelper(dbHelper);
        glm.addGroceryList("Weekly List");
        glm.selectGroceryList(0);

        testItem1 = new Item();
        testItem1.setName("orange");
        testItem1.setType("fruits");
        testItem1.setId(1);
        glm.getSelectedGroceryList().createGroceryLineItem(testItem1,2,false);

        lineItem = glm.getSelectedGroceryList().getGroceryLineItem().get(0);
        lineItem.setDbHelper(dbHelper);
    }


    //TC - 08
    @Test
    public void testCheckLineItemIsChecked(){
        lineItem.setChecked(true);
        assertTrue(lineItem.isChecked());
    }

    @Test
    public void testCheckLineItemIsNotChecked(){
        lineItem.setChecked(false);
        assertFalse(lineItem.isChecked());
    }

    //TC - 09
    @Test
    public void testSetItemQuantity(){
        int newQty = 5;
        lineItem.setQuantity(newQty);

        assertEquals(newQty, lineItem.getQuantity());
    }

    @Test
    public void testSetItemQuantityWhereNewQuantityIsLessThanOne(){
        int newQty = 0;
        lineItem.setQuantity(newQty);

        assertEquals(1, lineItem.getQuantity());
    }

    //TC - 10
    @Test
    public void testDeleteLineItem(){
        assertTrue(glm.getSelectedGroceryList().removeGroceryLineItem(lineItem));
    }

    @Test
    public void testDeleteLineItemWhereLineItemNull(){
        assertFalse(glm.getSelectedGroceryList().removeGroceryLineItem(null));
    }

    @Test
    public void testDeleteLineItemWhereLineItemIsNotInList(){
        GroceryLineItem lineItem2 = new GroceryLineItem(testItem1,10,true);

        assertFalse(glm.getSelectedGroceryList().removeGroceryLineItem(lineItem2));
    }

    @After
    public void tearDown() throws Exception {
    }


}