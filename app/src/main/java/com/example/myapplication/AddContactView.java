package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.example.myapplication.PrincipalView.CONTACT_KEY;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.Manifest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddContactView extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap contactBitmap;
    private String imagePath;
    private EditText cName, cMail, cPhone, cPrenom, cDate, cCodePostal, cAdresse;
    private RadioGroup radioGroupGender;
    private String selectedGender;
    private ImageView cameraIcon, avatar;

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
        avatar = findViewById(R.id.avatar);
        cameraIcon = findViewById(R.id.cameraIcon);


        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifier si la permission est déjà accordée
                if (ContextCompat.checkSelfPermission(AddContactView.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Si la permission n'est pas accordée, demandez-la
                    ActivityCompat.requestPermissions(AddContactView.this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                } else {
                    // Si la permission est accordée, lancez la caméra
                    launchCamera();
                }
            }
        });

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                selectedGender = radioButton.getText().toString();
            }
        });

        cDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créez une instance de Calendar pour obtenir l'année, le mois et le jour actuels.
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Créez une instance de DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddContactView.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                cDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            contactBitmap = (Bitmap) extras.get("data");
            avatar.setImageBitmap(contactBitmap); // Show thumbnail in ImageView

        }
    }

    public void validateClick(View view) {
        boolean saisie = true;

        if (cName.getText().toString().isEmpty()) {
            cName.setError("Empty field");
            saisie = false;
        } else if (!verifyNameOrSurname(cName.getText().toString())) {
            Toast.makeText(AddContactView.this, "Invalid Name !", Toast.LENGTH_SHORT).show();
            cName.setText("");
            saisie = false;
        }


        if (cPrenom.getText().toString().isEmpty()) {
            cPrenom.setError("Empty field");
            saisie = false;
        } else if (!verifyNameOrSurname(cPrenom.getText().toString())) {
            Toast.makeText(AddContactView.this, "Invalid firstName !", Toast.LENGTH_SHORT).show();
            saisie = false;
            cPrenom.setText("");
        }


        if (!cMail.getText().toString().isEmpty() && !verifyEmail(cMail.getText().toString())) {
            Toast.makeText(AddContactView.this, "Invalid mail !", Toast.LENGTH_SHORT).show();
            saisie = false;
            cMail.setText("");
        }

        if (cPhone.getText().toString().isEmpty()) {
            cPhone.setError("Empty field");
            saisie = false;
        } else if (!verifyPhone(cPhone.getText().toString())) {
            Toast.makeText(AddContactView.this, "Invalid phone !", Toast.LENGTH_SHORT).show();
            cPhone.setText("");
            saisie = false;
        }

        if (!cCodePostal.getText().toString().isEmpty() && !verifyPostalCode(cCodePostal.getText().toString())) {
            Toast.makeText(AddContactView.this, "Invalid CP !", Toast.LENGTH_SHORT).show();
            saisie = false;
            cPhone.setText("");
        }
        if (saisie) {

            // Save the contact image to a temporary file
            if (contactBitmap != null) {
                String uniqueImageName = "contact_image_" + System.currentTimeMillis() + ".png"; // Unique file name
                File imageFile = saveImageToInternalStorage(contactBitmap, uniqueImageName);
                if (imageFile != null) {
                    imagePath = imageFile.getAbsolutePath();
                }
            }

            Intent backIntent = new Intent();
            Contact contact = new Contact(selectedGender, cName.getText().toString(), cPrenom.getText().toString(), cDate.getText().toString(), cPhone.getText().toString(), cMail.getText().toString(), cAdresse.getText().toString(), cCodePostal.getText().toString(), imagePath);
            backIntent.putExtra(CONTACT_KEY, contact);
            setResult(RESULT_OK, backIntent);


            String ajout_message = "Le contact " + cName.getText().toString() + " est ajouté !";

            Toast.makeText(AddContactView.this, ajout_message, Toast.LENGTH_SHORT).show();

            finish(); // Close the current activity
        }

    }

    private File saveImageToInternalStorage(Bitmap bitmap, String fileName) {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        // Specify the directory where you want to save the image
        File directory = contextWrapper.getDir("images", Context.MODE_PRIVATE);

        // Create a unique file name for the image
        File imageFile = new File(directory, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Error occurred while saving the image
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imageFile;
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

    private void launchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

}



