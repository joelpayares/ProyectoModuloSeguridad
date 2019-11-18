package com.proyectomoduloseguridad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity implements View.OnClickListener{

    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView nombre = findViewById(R.id.nombre);
        TextView correo = findViewById(R.id.correo);
        Button salir = findViewById(R.id.logout);

        Intent intent = getIntent();
        currentUser (FirebaseUser) = intent.getStringExtra("Usuario");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        nombre.setText(user.getDisplayName());

        correo.setText(user.getEmail());

        salir.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
    }
}