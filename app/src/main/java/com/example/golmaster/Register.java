package com.example.golmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {

    private EditText editTextNombre, editTextApellidos, editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button buttonRegistrar;
    private ImageButton buttonAtras;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);
        buttonAtras = findViewById(R.id.button_arrow_back);


        buttonRegistrar.setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString().trim();
            String apellidos = editTextApellidos.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("PasswordCheck", "Password: " + password);
            Log.d("PasswordCheck", "Confirm Password: " + confirmPassword);

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            db.collection("Usuario")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(task -> {
                       if(task.isSuccessful()){
                           QuerySnapshot querySnapshot = task.getResult();
                           if (querySnapshot.isEmpty()){
                               //usuario no existe
                               String hashpasword = "";
                               hashpasword = hashPassword(password);
                               regitrarUsuario(nombre,apellidos,email,hashpasword);
                           }else {
                               //usuario existe
                               Toast.makeText(this,"Error: usuario ya existe",Toast.LENGTH_SHORT).show();
                           }
                       }
                    });
        });

        buttonAtras.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Launcher.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void regitrarUsuario(String nombre, String apellidos, String email, String password){

        Map<String, Object> user = new HashMap<>();
        user.put("Nombre", nombre);
        user.put("Apellidos", apellidos);
        user.put("email", email);
        user.put("Contraseña", password);

        db.collection("Usuario").add(user)
                .addOnSuccessListener( documentReference ->
                        Toast.makeText(this, "Usuario registrado Correctamente", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this,"Error al regitrar: " + e.getMessage(), Toast.LENGTH_SHORT).show());
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