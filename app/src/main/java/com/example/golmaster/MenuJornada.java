package com.example.golmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class MenuJornada extends AppCompatActivity {

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_jornada);

        db = FirebaseFirestore.getInstance();

        Spinner spinnerOptions = findViewById(R.id.spinnerOptions);

        // Configura el adaptador usando el array de recursos
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Jornadas, android.R.layout.simple_spinner_item);

        // Especifica el layout a usar cuando la lista se despliega
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asocia el adaptador al Spinner
        spinnerOptions.setAdapter(adapter);

        // Configura el listener para detectar selecciones
        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                Toast.makeText(MenuJornada.this, "Seleccionado: " + selectedOption, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Si no se selecciona ninguna opción
            }
        });
    }
}