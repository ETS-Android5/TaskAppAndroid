package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static int globalCounter = 0;
    private ListView eventListView;
    ArrayList<Event> items = new ArrayList<>();
    private EventListAdoptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(MainActivity.globalCounter + " @MainActivity-onCreate()");

        eventListView = findViewById(R.id.listEventView);


        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event selectedEvent = items.get(position);

                Intent i = new Intent(MainActivity.this, CreateEventActivity.class);
                i.putExtra("id",selectedEvent.id);
                i.putExtra("title", selectedEvent.title);
                i.putExtra("place", selectedEvent.place);
                i.putExtra("type", selectedEvent.type);
                i.putExtra("dateAndTime", selectedEvent.dateTime);
                i.putExtra("capacity", selectedEvent.capacity);
                i.putExtra("budget", selectedEvent.budget);
                i.putExtra("email", selectedEvent.email);
                i.putExtra("phone", selectedEvent.phone);
                i.putExtra("description", selectedEvent.description);

                startActivity(i);

            }
        });

        eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Event selectedEvent = items.get(position);
                System.out.println(selectedEvent.id);

                showDialog("Do you want to delete"+selectedEvent.title,"Info",selectedEvent);
                return true;
            }
        });


        Button btnCreateNew = findViewById(R.id.createNewButton);

        btnCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(MainActivity.globalCounter + "Mainactivity-Create btn was pressed");
                MainActivity.globalCounter++;

                Intent i = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivity(i);


            }
        });

//        //TestPageOpener
//        Button TestActivity = findViewById(R.id.histioryButton);
//        TestActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this,TestActivity.class);
//                startActivity(i);
//
//            }
//        });


//        Button HistoryBtn = findViewById(R.id.histioryButton);
//        HistoryBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                System.out.println(MainActivity.globalCounter+"Mainactivity-Create btn was pressed");
////                MainActivity.globalCounter++;
//                String[] values = value.split("-----");
//
//                System.out.println("Name : "+values[0]);
//                System.out.println("Place : "+values[1]);
//                System.out.println("Type :"+values[2]);
//                System.out.println("Date & Time :"+values[3]);
//                System.out.println("Capacity  :"+values[4]);
//                System.out.println("Budget :"+values[5]);
//                System.out.println("Email :"+values[6]);
//                System.out.println("Phone :"+values[7]);
//                System.out.println("Description :"+values[8]);
//
//
//            }
//        });

        Button BtnExit = findViewById(R.id.exitButton);

        BtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(MainActivity.globalCounter + "Mainactivity-Create btn was pressed");
                MainActivity.globalCounter++;
                finish();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println(MainActivity.globalCounter + " @MainActivity-onStart()");
        MainActivity.globalCounter++;

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println(MainActivity.globalCounter + " @MainActivity-onResume()");
        MainActivity.globalCounter++;
         items.clear();

        KeyValueDB db = new KeyValueDB(this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        while (rows.moveToNext()) {
            String key = rows.getString(0);
            String value = rows.getString(1);
            System.out.println(value);
            String[] subString = value.split("-----");
            if (subString.length != 9) {
                continue;
            }
            String title = subString[0];
            String place = subString[1];
            String type = subString[2];
            String dateTime = subString[3];
            int capacity = Integer.parseInt(subString[4]);
            double budget = Double.parseDouble(subString[5]);
            String email = subString[6];
            String phone = subString[7];
            String description = subString[8];

            items.add(new Event(key,title, place, type, dateTime, capacity, budget, email, phone, description));

        }

        adapter = new EventListAdoptor(this, R.layout.layout_row_event, items);
        eventListView.setAdapter(adapter);

    }


    private void showDialog(String message, String title, Event event) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
        Util.getInstance().deleteByKey(MainActivity.this, event.id);
                        dialog.cancel();
//                        initializeCustomEventList();
                        items.remove(event);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

//Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        System.out.println(MainActivity.globalCounter + " @MainActivity-onPause()");
        MainActivity.globalCounter++;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println(MainActivity.globalCounter + " @MainActivity-onRestart()");
        MainActivity.globalCounter++;
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println(MainActivity.globalCounter + " @MainActivity-onStop()");
        MainActivity.globalCounter++;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println(MainActivity.globalCounter + " @MainActivity-onDestroy()");
        MainActivity.globalCounter++;
    }
}