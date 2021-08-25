package com.todolist.cc916647.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;
    TableLayout tableLayout;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();

        context = MainActivity.this;

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button showAllButton = (Button) findViewById(R.id.showAllButton);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemText = editText.getText().toString();

                if(itemText.isEmpty())
                {
                    showToast("Please enter an item!");
                }
                else
                {
                    items.add(itemText);
                    showToast("Item successfully added!");
                    //show real time add
                    showAllItems();
                }
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemText = editText.getText().toString();

                if(itemText.isEmpty())
                {
                    showToast("Please enter an item to delete!");
                }
                else
                {

                    int itemIndex = items.indexOf(itemText);


                    if(itemIndex != -1)
                    {
                        items.remove(itemIndex);
                        showToast("Item successfully deleted!");
                        //show real time delete
                        showAllItems();
                    }
                    else
                    {
                        showToast("Item was not found");
                    }

                }
            }
        });

        showAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(items.size() == 0)
                {
                    showToast("There are no items to show!");
                }
                showAllItems();

            }
        });
    }

    private void showToast(String toastText)
    {
        Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_LONG).show();
    }


    private void showAllItems()
    {
        tableLayout.removeAllViews();

        for(String item:items)
        {
            TableRow row = new TableRow(context);
            TextView itemTextView = new TextView(context);
            itemTextView.setText(item);
            row.addView(itemTextView);
            tableLayout.addView(row);
        }
    }

}
