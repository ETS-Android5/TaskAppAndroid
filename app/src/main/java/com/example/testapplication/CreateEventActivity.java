package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

public class CreateEventActivity extends AppCompatActivity {

    private EditText etName;
    private String selectedId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Intent i = getIntent();

        EditText nameField = findViewById(R.id.nameInput);
        EditText placeField = findViewById(R.id.placeInput);
        RadioGroup typeField = findViewById(R.id.readioGroup);
        int selectId =  typeField.getCheckedRadioButtonId();
        RadioButton typeFieldBtn = findViewById(selectId);
        EditText dateAndTimeField = findViewById(R.id.dateAndTime);
        EditText capacityField = findViewById(R.id.capacityInput);
        EditText budgetField= findViewById(R.id.budgetInput);
        EditText emailField = findViewById(R.id.emailInput);
        EditText phoneField = findViewById(R.id.phoneInput);
        EditText descriptionField = findViewById(R.id.descriptionInput);


        try {

        if(!i.getStringExtra("id").isEmpty()){
            selectedId = i.getStringExtra("id");
            String title = i.getStringExtra("title");
            String place = i.getStringExtra("place");
            String type = i.getStringExtra("type");
            String dateTime = i.getStringExtra("dateAndTime");
            int capacity =i.getIntExtra("capacity",-1);
            double budget = i.getDoubleExtra("budget",-1);
            String email = i.getStringExtra("email");
            String phone = i.getStringExtra("phone");
            String description = i.getStringExtra("description");




            nameField.setText(title);
            placeField.setText(place);
            if(type != null) {
                switch (type) {
                    case "Indoor":
                        int radid = 2131231060;
                        RadioButton rb1 = findViewById(radid);
                        rb1.setChecked(true);
                        break;
                    case "Outdoor":
                        radid = 2131231062;
                        rb1 = findViewById(radid);
                        rb1.setChecked(true);
                        break;
                    case "Online":
                        radid = 2131231061;
                        rb1 = findViewById(radid);
                        rb1.setChecked(true);
                        break;
                    default:

                        System.out.println("no match");
                }

            }
            dateAndTimeField.setText(dateTime);
            capacityField.setText(Integer.toString(capacity));
            budgetField.setText(Double.toString(budget));
            emailField.setText(email);
            phoneField.setText(phone);
            descriptionField.setText(description);
        }
        }
        catch(Exception e) {
            System.out.println(e);
        }


        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){

                try {
                    RadioGroup typeField = findViewById(R.id.readioGroup);
                    int selectId =  typeField.getCheckedRadioButtonId();
                    RadioButton typeFieldBtn = findViewById(selectId);



                if(selectedId.isEmpty()){
                    selectedId=nameField.getText().toString()+"-"+System.currentTimeMillis();
                }



                String eventValue=nameField.getText().toString()+"-----"+placeField.getText().toString()+
                        "-----"+typeFieldBtn.getText().toString()+ "-----" +dateAndTimeField.getText().toString()+
                        "-----"+capacityField.getText().toString()+"-----"+budgetField.getText().toString()
                        +"-----"+emailField.getText().toString()+"-----"+phoneField.getText().toString()+
                        "-----"+descriptionField.getText().toString();

                Util.getInstance().setKeyValue(CreateEventActivity.this,selectedId,eventValue);












                Toast.makeText(getApplicationContext(),"Items has been updated",Toast.LENGTH_SHORT).show();
                finish();
                }
                catch(Exception e) {
                    TextView errorsTV = findViewById(R.id.errorTextMessage);
                    errorsTV.setText("Please insert correct values!");


                }

            }

        });

    }


}