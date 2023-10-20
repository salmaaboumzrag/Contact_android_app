package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.myapplication.PrincipalView.CONTACT_KEY;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddContactView extends AppCompatActivity {

    private EditText cName, cMail, cPhone,cPrenom,cDate,cCodePostal,cAdresse;
    private RadioGroup radioGroupGender;
    private String selectedGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        cName = (EditText) findViewById(R.id.cName);
        cMail = (EditText) findViewById(R.id.cMail);
        cPhone = (EditText) findViewById(R.id.cPhone);
        cPrenom = (EditText) findViewById(R.id.cPrenom);
        cDate = (EditText) findViewById(R.id.cDate);
        cCodePostal = (EditText) findViewById(R.id.cCodePostal);
        cAdresse = (EditText) findViewById(R.id.cAdresse);
        radioGroupGender = findViewById(R.id.radioGroupSex);

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                selectedGender = radioButton.getText().toString();
            }
        });

    }

    public void validateClick (View view){
        boolean saisie = true;

        if (!verifyNameOrSurname(cName.getText().toString())) {
            Toast.makeText(AddContactView.this, "Invalid Name !", Toast.LENGTH_SHORT).show();
            saisie = false;
            cName.setText("");
        }

        if (!cPrenom.getText().toString().isEmpty() &&!verifyNameOrSurname(cPrenom.getText().toString())) {
            Toast.makeText(AddContactView.this, "Invalid firstName !", Toast.LENGTH_SHORT).show();
            saisie = false;
            cPrenom.setText("");
        }

        if (!cMail.getText().toString().isEmpty() &&!verifyEmail(cMail.getText().toString())) {
            Toast.makeText(AddContactView.this, "Invalid mail !", Toast.LENGTH_SHORT).show();
            saisie = false;
            cMail.setText("");
        }

        if (!verifyPhone(cPhone.getText().toString())) {
            Toast.makeText(AddContactView.this, "Invalid phone !", Toast.LENGTH_SHORT).show();
            saisie = false;
            cPhone.setText("");
        }

        if (!cCodePostal.getText().toString().isEmpty() &&!verifyPostalCode(cCodePostal.getText().toString())) {
            Toast.makeText(AddContactView.this, "Invalid CP !", Toast.LENGTH_SHORT).show();
            saisie = false;
            cPhone.setText("");
        }
        if(saisie) {

            Intent backIntent = new Intent();
            Contact contact =new Contact(selectedGender
                    ,cName.getText().toString(),cPrenom.getText().toString()
                    ,cDate.getText().toString(),cPhone.getText().toString()
                    ,cMail.getText().toString(),cAdresse.getText().toString()
                    ,cCodePostal.getText().toString());

            backIntent.putExtra(CONTACT_KEY,contact);
            setResult(RESULT_OK,backIntent);


            String ajout_message = "Le contact " + cName.getText().toString() + " est ajouté !";

            Toast.makeText(AddContactView.this, ajout_message, Toast.LENGTH_SHORT).show();

            finish(); // Close the current activity
        }

    }


    public boolean verifyNameOrSurname(String text) {
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() && !text.isEmpty();
    }

    public boolean verifyEmail(String email) {
        String regx = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find() && !email.isEmpty();
    }

    public boolean verifyPhone(String phone) {
        return phone.matches("^\\+?[0-9]+$");
    }
    public boolean verifyPostalCode(String postalCode) {
        return postalCode.matches("^[0-9]{5}$");
    }



}



