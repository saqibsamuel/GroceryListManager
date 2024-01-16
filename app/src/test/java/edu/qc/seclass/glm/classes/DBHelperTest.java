package edu.qc.seclass.glm.classes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import edu.qc.seclass.glm.SearchItems;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DBHelperTest {

    DBHelper dbHelper;



    @Before
    public void setUp() throws Exception {

        dbHelper = mock(DBHelper.class);

    }

    //TC - 06
    @Test
    public void testSearch(){
        ArrayList<Item> list = new ArrayList<>();
        list.add(new Item("apple","fruits"));
        list.add(new Item("plum","fruits"));
        list.add(new Item("grapes","fruits"));
        when(dbHelper.searchItems("p")).thenReturn(list);

        assertEquals(3,dbHelper.searchItems("p").size());
    }

    @Test
    public void testSearchWithEmptyString(){
        ArrayList<Item> list = new ArrayList<>();
        list.add(new Item("apple","fruits"));
        list.add(new Item("plum","fruits"));
        list.add(new Item("grapes","fruits"));
        list.add(new Item("oranges","fruits"));
        list.add(new Item("banana","fruits"));
        when(dbHelper.searchItems("")).thenReturn(list);

        assertEquals(5,dbHelper.searchItems("").size());
    }

    //TC - 07
    @Test
    public void testCreateItem(){
        Item it = new Item();
        it.setName("Plum");
        it.setType("Fruits");
        when(dbHelper.insertNewItem(it)).thenReturn(new Item("plum","fruits"));

        Item newItem = dbHelper.insertNewItem(it);
        assertEquals("plum",newItem.getName());
    }

    @After
    public void tearDown() throws Exception {
    }

}