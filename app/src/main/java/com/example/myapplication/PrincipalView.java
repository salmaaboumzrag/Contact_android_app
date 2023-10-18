package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;


import java.util.ArrayList;

public class PrincipalView extends AppCompatActivity {

    public static final String CONTACT_KEY ="key_contact";
    private ArrayList<Contact> contactList = new ArrayList<>();
    ContactAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = findViewById(R.id.my_lv);
        adapter = new ContactAdapter(PrincipalView.this, contactList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText((Context) PrincipalView.this,contactList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        Button addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact(v);
            }
        });

    }

    public void addContact(View view) {
        Intent intent = new Intent(PrincipalView.this, AddContactView.class);
        startActivityForResult(intent,  100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==RESULT_OK && requestCode==100){
            Contact contact = (Contact) data.getSerializableExtra(CONTACT_KEY);
            contactList.add(contact);
            adapter.notifyDataSetChanged();

        }
    }

}