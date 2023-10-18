package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.myapplication.PrincipalView.CONTACT_KEY;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddContactView extends AppCompatActivity {

    private EditText cName, cMail, cPhone,cPrenom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        cName = (EditText) findViewById(R.id.cName);
        cMail = (EditText) findViewById(R.id.cMail);
        cPhone = (EditText) findViewById(R.id.cPhone);

    }

    public void validateClick (View view){
        boolean saisie = true;
        if ( verify_inputs(cName.getText().toString())== false) {
            Toast.makeText(AddContactView.this, "error nom", Toast.LENGTH_SHORT).show();
            saisie = false;
            cName.setText("");
        }
        /*if ( verify_inputs(cMail.getText().toString())== false) {
            Toast.makeText(AddContactView.this, "error mail", Toast.LENGTH_SHORT).show();
            saisie = false;
            cMail.setText("");
        }
        if ( verify_inputs(cPhone.getText().toString())== false) {
            Toast.makeText(AddContactView.this, "error phone", Toast.LENGTH_SHORT).show();
            saisie = false;
            cPhone.setText("");
        }*/
        if(saisie) {

            Intent backIntent = new Intent();
            Contact contact =new Contact(cName.getText().toString(),cMail.getText().toString(),cPhone.getText().toString());
            backIntent.putExtra(CONTACT_KEY,contact);
            setResult(RESULT_OK,backIntent);


            String ok_message = "\n" + cName.getText().toString() + "\n" +
                    "va être ajouté aux contacts";

            Toast.makeText(AddContactView.this, ok_message, Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(AddContactView.this);
            builder.setTitle("Contact");
            builder.setMessage(ok_message);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();


        }

    }


    public boolean verify_inputs(String text) {
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

}



