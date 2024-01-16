package edu.qc.seclass.glm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.qc.seclass.glm.classes.DBHelper;
import edu.qc.seclass.glm.classes.GroceryLineItem;
import edu.qc.seclass.glm.classes.GroceryList;
import edu.qc.seclass.glm.classes.GroceryListManager;
import edu.qc.seclass.glm.classes.Item;

public class SearchItems extends AppCompatActivity {
    GroceryList gl;
    ListView listView;
    EditText editText;
    Button button;
    DBHelper dbHelper;
    ArrayAdapter<Item> adapter;
    ArrayList<Item> itemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_items);

        dbHelper = new DBHelper(getApplicationContext());

        gl = (GroceryList) getIntent().getSerializableExtra("list");
        gl.getGroceryLineItem().clear();
        gl.setDbHelper(dbHelper);

        listView = findViewById(R.id.searchListView);
        editText = findViewById(R.id.searchEditText);
        button = findViewById(R.id.btnSearch);


        itemArrayList = dbHelper.searchItems("");
        /*itemArrayList = new ArrayList<>();

        while (c.moveToNext()) {
            Item it = new Item(dbHelper, c.getString(1), c.getString(2));
            it.setId(c.getInt(0));
            itemArrayList.add(it);
        }*/

        adapter = new ArrayAdapter<>(SearchItems.this, R.layout.support_simple_spinner_dropdown_item, itemArrayList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setQuantityDialog(itemArrayList.get(i));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchKeyword = editText.getText().toString();
                itemArrayList.clear();
                for (Item it : dbHelper.searchItems(searchKeyword)) {
                    itemArrayList.add(it);
                }
                adapter.notifyDataSetChanged();

                if (itemArrayList.size() == 0) {
                    createNewItemDialog(searchKeyword);
                }

            }
        });

    }


    private void createNewItemDialog(final String searchKeyword) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SearchItems.this);
        final View v = View.inflate(SearchItems.this, R.layout.dialog_create_new_item, null);

        dialog.setTitle("Create New Item");
        dialog.setView(v);
        final EditText newItemName = v.findViewById(R.id.dialogItemNewName);
        final EditText newItemType = v.findViewById(R.id.dialogItemNewType);
        newItemName.setText(searchKeyword);

        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String itemName = newItemName.getText().toString();
                String itemType = newItemType.getText().toString();

                if (itemName.length() > 0 && itemType.length() > 0) {
                    Item it = new Item(itemName, itemType);
                    it = dbHelper.insertNewItem(it);
                    itemArrayList.clear();
                    itemArrayList.add(it);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(SearchItems.this, "Adding New Item ( " + itemName + " ) ...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SearchItems.this, "Either Item Name or Type are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void setQuantityDialog(final Item it) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(SearchItems.this);
        final View v = View.inflate(SearchItems.this, R.layout.dialog_set_quantity, null);

        dialog.setTitle("Enter Item Quantity");
        dialog.setView(v);

        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText et = v.findViewById(R.id.dialogSetQuantity);
                String answer = et.getText().toString();
                if (answer.length() > 0) {
                    int q = Integer.parseInt(answer);
                    if (q > 0) {
                        gl.createGroceryLineItem(it, q, false);
                        Toast.makeText(SearchItems.this, "Setting Quantity ...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SearchItems.this, "Item Quantity cannot be set to 0!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SearchItems.this, "Item Quantity cannot empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", gl);
        setResult(Activity.RESULT_OK, returnIntent);

        super.onBackPressed();
    }
}