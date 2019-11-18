package com.proyectomoduloseguridad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    private Button iniciar;
    private Button registro;
    private EditText correo;
    private EditText clave;

    private String nomusu;
    private String clausu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar = findViewById(R.id.btnIniciar);
        registro = findViewById(R.id.btnRegistrar);
        correo = findViewById(R.id.txtEmail);
        clave = findViewById(R.id.txtPass);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        iniciar.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        Intent saltoHome = new Intent(MainActivity.this, Registro.class);
        saltoHome.putExtra("Usuario", currentUser);
        startActivity(saltoHome);
    }

    @Override
    public void onClick(View view) {
        switch (String.valueOf(view.getId())){
            case "btnIniciar":
                nomusu = correo.getText().toString();
                clausu = clave.getText().toString();

                inicioSecion(nomusu, clausu);
                break;
            case "btnRegistrar":
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(view.getId()));
        }
    }

    private void inicioSecion(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            progressDialog.setMessage("Iniciando Secion");
                            progressDialog.show();
                        } else {
                            Toast.makeText(MainActivity.this, "No estas Registrado!!",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);

                            progressDialog.dismiss();
                        }
                    }
                });
    }
}
