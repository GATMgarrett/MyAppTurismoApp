package com.univalle.myappturismo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView t1, t2;
    Double l1, l2;
    Button btn, btnUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        t1 = (TextView) findViewById(R.id.NombreHolder);
        t2 = (TextView) findViewById(R.id.DescripcionHolder);
        btn = (Button) findViewById(R.id.btnAtras);
        btnUbicacion = (Button) findViewById(R.id.btnUbicacion);

        t1.setText(getIntent().getStringExtra("uNombre").toString());
        t2.setText(getIntent().getStringExtra("uDescripcion").toString());



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });


        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                finish();
            }
        });
    }
}