package com.example.lab_gsi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab_gsi.Dominio.Recomendacion;
import java.util.ArrayList;
import static android.view.Gravity.CENTER;

public class lista extends AppCompatActivity implements AdaptadorLista.OnItemSelectedListener, AdaptadorListaModificar.OnItemSelectedListener {

    private ArrayList<Recomendacion> recomendaciones;
    private ArrayList<Recomendacion> recomFiltradas;
    private RecyclerView lstRecomendaciones;
    private AdaptadorLista adaptadorTipo;
    private AdaptadorListaModificar adaptadorTipoModificar;
    private Context context;
    private String tipo;
    private Button btnGps;

    private ConectorBD conectorBD;

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        tipo = (String) getIntent().getSerializableExtra("Tipo");

        conectorBD = new ConectorBD(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("Recomendaciones");
        //Obtener una referencia a la lista gráfica
        lstRecomendaciones = findViewById(R.id.lstRecomendaciones);
        //Crear la lista de contactos y añadir algunos datos de prueba
        //recomendaciones = new ArrayList<Recomendacion>();
        recomendaciones = (ArrayList<Recomendacion>) getIntent().getSerializableExtra("Recomendaciones");
        System.out.println("Numero recomendaciones: " +recomendaciones.size());
        recomFiltradas = new ArrayList<Recomendacion>();
        //Método que rellena el array con datos de prueba
        if(tipo.equals("restaurantes")) {
            System.out.println("tipo Entra restaurantes:" + recomendaciones.size());
            for(int i=0; i<recomendaciones.size(); i++){
                if(recomendaciones.get(i).getTipo().equals("restaurante")){
                    recomFiltradas.add((recomendaciones.get(i)));
                }
            }
            //listarRecomendacionesRestaurantes();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            lstRecomendaciones.setLayoutManager(mLayoutManager);
            adaptadorTipo = new AdaptadorLista(context, recomFiltradas);

            adaptadorTipo.setItemSelectedListener(this);

            lstRecomendaciones.setAdapter(adaptadorTipo);

        }
        else if(tipo.equals("museos")) {
            System.out.println("tipo Entra museos");
            for(int i=0; i<recomendaciones.size(); i++){
                if(recomendaciones.get(i).getTipo().equals("museo")){
                    recomFiltradas.add((recomendaciones.get(i)));
                }
            }
            //listarRecomendacionesMuseos();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            lstRecomendaciones.setLayoutManager(mLayoutManager);
            adaptadorTipo = new AdaptadorLista(context, recomFiltradas);

            adaptadorTipo.setItemSelectedListener(this);

            lstRecomendaciones.setAdapter(adaptadorTipo);
        }
        else{
            System.out.println("tipo Entra modificar");
            //listarRecomendacionesRestaurantes();
            //listarRecomendacionesMuseos();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            lstRecomendaciones.setLayoutManager(mLayoutManager);
            adaptadorTipoModificar = new AdaptadorListaModificar(context, recomendaciones);

            adaptadorTipoModificar.setItemSelectedListener(this);
            lstRecomendaciones.setAdapter(adaptadorTipoModificar);


        }



    }


    @Override
    public void Gps(Recomendacion r) {
        Intent intent = new Intent(lista.this, mapa.class);

        System.out.println("Latitud y longitud: "+ r.getLatitud() + r.getLongitud());
        intent.putExtra("Recomendacion", r);
        intent.putExtra("Recomendaciones", recomendaciones);
        intent.putExtra("Tipo", tipo);
        startActivity(intent);
        finish();
    }

    @Override
    public void Modificar(Recomendacion r) {
        Intent intent = new Intent(lista.this, CrearActivity.class);

        intent.putExtra("Recomendacion", r);
        intent.putExtra("Tipo", tipo);
        intent.putExtra("Recomendaciones", recomendaciones);
        intent.putExtra("Modificar", "modificar");
        startActivity(intent);
        finish();
    }

    @Override
    public void Borrar(int position, String nombre) {
        recomendaciones.remove(position);

        conectorBD.abrir();
        conectorBD.eliminarRecomendacion(nombre);
        showToast("Recomendación borrada");
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onMenuContextual(int posicion, MenuItem menu, Recomendacion recomendacion, String nombre){
        switch (menu.getItemId()){
            case R.id.Modificar:
                Intent intent = new Intent(lista.this, CrearActivity.class);

                intent.putExtra("Recomendacion", recomendacion);
                intent.putExtra("Tipo", tipo);
                intent.putExtra("Recomendaciones", recomendaciones);
                intent.putExtra("Modificar", "modificar");
                startActivity(intent);
                finish();
                break;

            case  R.id.Borrar:
                recomendaciones.remove(posicion);

                conectorBD.abrir();
                conectorBD.eliminarRecomendacion(nombre);
                showToast("Recomendación borrada");
                finish();
                startActivity(getIntent());
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(lista.this, MenuActivity.class);

            intent.putExtra("Tipo", tipo);
            intent.putExtra("Recomendaciones", recomendaciones);
            startActivity(intent);
            finish();
        } if (item.getItemId() == R.id.btnAutores) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Aplicación realizada por Antonio Felipe Rojo y Roque Rojo Bacete.");
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
        TextView view = new TextView(lista.this);
        view.setBackgroundColor(Color.DKGRAY);
        view.setTextColor(Color.WHITE);
        view.setText("\"" + r + "\"");
        view.setPadding(10, 10, 10, 10);
        toast.setView(view);
        toast.show();
    }
}
