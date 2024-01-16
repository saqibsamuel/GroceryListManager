package edu.qc.seclass.glm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.qc.seclass.glm.classes.DBHelper;
import edu.qc.seclass.glm.classes.GroceryLineItem;
import edu.qc.seclass.glm.classes.GroceryList;
import edu.qc.seclass.glm.classes.GroceryListManager;
import edu.qc.seclass.glm.classes.Item;

public class SelectedGroceryList extends AppCompatActivity {
    LinearLayout layout;

    ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
    DBHelper db;
    GroceryListManager glm;

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        checkBoxArrayList.clear();
        layout.removeAllViews();
        addViewToLayout();

    }

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_grocery_list);
        layout = findViewById(R.id.SelectedView);
        FloatingActionButton fab = findViewById(R.id.AddNewItems);
        int index = getIntent().getIntExtra("index", -1);

        db = new DBHelper(getApplicationContext());
        glm = (GroceryListManager) getIntent().getSerializableExtra("glms");
        glm.getSelectedGroceryList().setDbHelper(db);
        glm.setDbHelper(db);
        tv = findViewById(R.id.selectedTextView);
        tv.setText(glm.getSelectedGroceryList().getName());

        Toast.makeText(SelectedGroceryList.this, "Loading " + glm.getSelectedGroceryList().getName() + " ...", Toast.LENGTH_SHORT).show();
        if (index != -1 && glm.getSelectedGroceryList().getGroceryLineItem().size() == 0) {
            load();
        }

        addViewToLayout();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedGroceryList.this, SearchItems.class);
                intent.putExtra("list", glm.getSelectedGroceryList());
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            for (GroceryLineItem gli : ((GroceryList) data.getSerializableExtra("result")).getGroceryLineItem()) {
                glm.getSelectedGroceryList().updateGroceryLineItem(gli.getItem(), gli.getQuantity(), false, gli.getId());
            }
            refresh();
        }
    }

    private void load() {
        GroceryList gl = glm.getSelectedGroceryList();
        Cursor c = db.getLineItemsForList(gl.getId());
        while (c.moveToNext()) {
            GroceryLineItem gli = new GroceryLineItem();
            gl.updateGroceryLineItem(db.getItem(c.getInt(1)), c.getInt(2), (c.getInt(3) == 1), c.getInt(0));


        }

    }

    private void addViewToLayout() {

        for (final GroceryLineItem gli : glm.getSelectedGroceryList().getGroceryLineItem()) {
            View v = View.inflate(SelectedGroceryList.this, R.layout.line_item_layout, null);
            TextView tv = v.findViewById(R.id.lineItemTextView);
            CheckBox cb = v.findViewById(R.id.lineItemCheckbox);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gli.setDbHelper(db);
                    gli.setChecked(!gli.isChecked());
                }
            });
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setQuantityDialog(gli);
                }
            });
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    createDeleteGLIDialog(gli);
                    return true;
                }
            });

            checkBoxArrayList.add(cb);
            tv.setText(gli.toString());
            cb.setChecked(gli.isChecked());
            layout.addView(v);
        }

    }

    private void setQuantityDialog(final GroceryLineItem gli) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(SelectedGroceryList.this);
        final View v = View.inflate(SelectedGroceryList.this, R.layout.dialog_set_quantity, null);

        dialog.setTitle("Change Item Quantity");
        dialog.setView(v);

        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText et = v.findViewById(R.id.dialogSetQuantity);
                String answer = et.getText().toString();
                if (answer.length() > 0) {
                    int q = Integer.parseInt(answer);
                    if (q > 0) {
                        gli.setDbHelper(db);
                        gli.setQuantity(q);
                        Toast.makeText(SelectedGroceryList.this, "Setting Quantity ...", Toast.LENGTH_SHORT).show();
                        refresh();
                    } else
                        Toast.makeText(SelectedGroceryList.this, "Can't have a Quantity of 0...", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(SelectedGroceryList.this, "Item Quantity cannot be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();

    }

    private void createDeleteGLIDialog(final GroceryLineItem gli) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SelectedGroceryList.this);

        dialog.setTitle("Delete Item");
        dialog.setMessage("Do you want to delete a selected item?");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SelectedGroceryList.this, "Deleting Item ...", Toast.LENGTH_SHORT).show();
                glm.getSelectedGroceryList().removeGroceryLineItem(gli);
                refresh();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SelectedGroceryList.this, "Canceling " + glm.getSelectedGroceryList().getName() + " ...", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_selected_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getOrder();
        switch (i) {
            case 0:
                checkAllBoxes(true);
                break;
            case 1:
                checkAllBoxes(false);
                break;
            case 2:
                createRenameDialog();
                break;
            case 3:
                glm.removeGroceryList(getIntent().getIntExtra("index", -1));
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkAllBoxes(boolean b) {
        for (CheckBox cb : checkBoxArrayList) {
            cb.setChecked(b);
        }
        glm.getSelectedGroceryList().checkAllLineItems(b);
    }

    private void createRenameDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SelectedGroceryList.this);
        View v = View.inflate(SelectedGroceryList.this, R.layout.dialog_rename_list, null);
        final EditText et = v.findViewById(R.id.dialogSetListName);
        dialog.setTitle("Enter a new name for the list");
        dialog.setView(v);

        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SelectedGroceryList.this, "Renaming List ...", Toast.LENGTH_SHORT).show();
                glm.renameGroceryList(et.getText().toString());
                tv.setText(glm.getSelectedGroceryList().getName());
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", glm);


        setResult(Activity.RESULT_OK, returnIntent);

        super.onBackPressed();

    }
}