package com.example.golmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton buttonAtras;
        Button buttonLogin;

        db = FirebaseFirestore.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonAtras = findViewById(R.id.button_arrow_back);
        buttonLogin = findViewById(R.id.Iniciar_Sesion);

        buttonAtras.setOnClickListener((View v) -> {
            Intent intent = new Intent(Login.this, Launcher.class);
            startActivity(intent);
            finish();
        });

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            validarUsuario(email,password);

        });
    }

    private void validarUsuario(String email, String password) {
        // Consulta Firestore para verificar si el usuario existe
        db.collection("Usuario")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            // El usuario existe, ahora verifica la contraseña
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                String storedPasswordHash = document.getString("Contraseña");

                                // Hashea la contraseña ingresada para compararla
                                String inputPasswordHash = hashPassword(password);
                                if (inputPasswordHash.equals(storedPasswordHash)) {
                                    // Contraseña válida, inicio de sesión exitoso
                                    Toast.makeText(Login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, MenuJornada.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Contraseña incorrecta
                                    Toast.makeText(Login.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            // Usuario no encontrado
                            Toast.makeText(Login.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error en la consulta
                        Toast.makeText(Login.this, "Error al consultar el usuario", Toast.LENGTH_SHORT).show();
                        Log.e("FirestoreError", "Error en la consulta", task.getException());
                    }
                });
    }

    private String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash  = md.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for(byte b : hash){
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
}