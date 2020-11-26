package com.example.lab_gsi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab_gsi.Dominio.Recomendacion;

import java.util.ArrayList;

import static android.view.Gravity.CENTER;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Recomendacion> recomendaciones;
    private String usuario;
    private String contraseña;
    private EditText txtUsuario;
    private EditText txtContraseña;

    private ConectorBD conectorBD;


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio Sesión");
        setSupportActionBar(toolbar);
        txtUsuario = findViewById(R.id.editText);
        txtContraseña = findViewById(R.id.editText2);

        usuario = "prueba@gmail.com";
        contraseña = "1234";


        conectorBD = new ConectorBD(this);
        recomendaciones = new ArrayList<Recomendacion>();
        //crearRecomendaciones();
        listarRecomendaciones();



    }

    public void iniciarSesion(View view) {

        if(txtUsuario.getText().toString().equals(usuario) && txtContraseña.getText().toString().equals(contraseña)) {
            Intent i = new Intent(this, MenuActivity.class);
            i.putExtra("Recomendaciones", recomendaciones);
            startActivity(i);
        }
        else{
            showToast("Usuario y/o contraseña incorrecto/s");
        }
    }

    private void listarRecomendaciones(){

        /*conectorBD.abrir();
        conectorBD.insertarRecomendacion("Casa Liu", "Buen restaurante de comida china", "38.990428", "-3.928247", "android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic1,"restaurante");
        conectorBD.insertarRecomendacion("Restaurante La Caleta", "Especialidad en arroces", "38.987120", "-3.926632", "android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic2,"restaurante");
        conectorBD.insertarRecomendacion("Restaurante El Ventero", "Lugar bueno y agradable", "38.985461", "-3.928788","android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic3,"restaurante");
        conectorBD.insertarRecomendacion("Museo Provincial de Ciudad Real", "Museo", "38.986295", "-3.929263", "android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic4, "museo");
        conectorBD.insertarRecomendacion("Museo del Prado", "Museo más importante de España", "40.413806", "-3.692127", "android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic5, "museo");
        conectorBD.cerrar();*/

        conectorBD.abrir();
        Cursor c = conectorBD.listarRecomendaciones();
        if (c.moveToFirst())
        {
            do {
                Recomendacion recomendacion = new Recomendacion(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
                //contacto.setNombre(c.getString(0));
                //contacto.setTelefono(c.getString(1));
                recomendaciones.add(recomendacion);
            } while (c.moveToNext());
        }
        c.close();
        conectorBD.cerrar();


        //recomendaciones.add(new Recomendacion("Casa Liu", "Buen restaurante de comida china", "38.990428", "-3.928247", "android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic1,"restaurante"));
        //recomendaciones.add(new Recomendacion("Restaurante La Caleta", "Especialidad en arroces", "38.987120", "-3.926632", "android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic2,"restaurante"));
        //recomendaciones.add(new Recomendacion("Restaurante El Ventero", "Lugar bueno y agradable", "38.985461", "-3.928788","android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic3,"restaurante"));
        //recomendaciones.add(new Recomendacion("Museo Provincial de Ciudad Real", "Museo", "38.986295", "-3.929263", "android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic4, "museo"));
        //recomendaciones.add(new Recomendacion("Museo del Prado", "Museo más importante de España", "40.413806", "-3.692127", "android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic5, "museo"));
    }

    private void crearRecomendaciones(){

        conectorBD.abrir();
        conectorBD.insertarRecomendacion("Casa Liu", "Buen restaurante de comida china", "38.990428", "-3.928247", "android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic1,"restaurante");
        conectorBD.insertarRecomendacion("Restaurante La Caleta", "Especialidad en arroces", "38.987120", "-3.926632", "android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic2,"restaurante");
        conectorBD.insertarRecomendacion("Restaurante El Ventero", "Lugar bueno y agradable", "38.985461", "-3.928788","android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic3,"restaurante");
        conectorBD.insertarRecomendacion("Museo Provincial de Ciudad Real", "Museo", "38.986295", "-3.929263", "android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic4, "museo");
        conectorBD.insertarRecomendacion("Museo del Prado", "Museo más importante de España", "40.413806", "-3.692127", "android.resource://com.example.lab_gsi/drawable/" + R.drawable.pic5, "museo");
        conectorBD.cerrar();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
       if (item.getItemId() == R.id.btnAutores) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Aplicación creada por Antonio Felipe Rojo y Roque Rojo Bacete");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showToast(String r) {
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(CENTER, 0, 0);
        TextView view = new TextView(MainActivity.this);
        view.setBackgroundColor(Color.DKGRAY);
        view.setTextColor(Color.WHITE);
        view.setText("\"" + r + "\"");
        view.setPadding(10, 10, 10, 10);
        toast.setView(view);
        toast.show();
    }
}
