package com.example.golmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Launcher extends AppCompatActivity {

    private Button botonRegistrase, botonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        botonRegistrase = findViewById(R.id.Registrarse);
        botonLogin = findViewById(R.id.Iniciar_Sesion);

        botonRegistrase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Launcher.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        botonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Launcher.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}