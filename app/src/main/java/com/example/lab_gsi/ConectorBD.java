package com.example.lab_gsi;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ConectorBD {
    static final String NOMBRE_BD = "AgendaLocal";
    private RecomendacionesSQLiteHelper dbHelper;
    private SQLiteDatabase db;
    /*Constructor*/
    public ConectorBD (Context ctx)
    {
        dbHelper = new RecomendacionesSQLiteHelper(ctx, NOMBRE_BD, null, 1);
    }
    /*Abre la conexión con la base de datos*/
    public ConectorBD abrir() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    /*Cierra la conexión con la base de datos*/
    public void cerrar()
    {
        if (db != null) db.close();
    }

    /*inserta una recomendacion en la BD*/
    public void insertarRecomendacion(String nombre, String comentario, String latitud, String longitud, String imagenBit, String tipo)
    {
        String consultaSQL = "INSERT INTO Recomendaciones VALUES('"+nombre+"','"+comentario+"','"+latitud+"','"+longitud+"','"+imagenBit+"','"+tipo+"')";
        db.execSQL(consultaSQL);
    }

    /*devuelve todas las recomendaciones*/
    public Cursor listarRecomendaciones()
    {
        return db.rawQuery("SELECT * FROM Recomendaciones", null);
    }

    public void modificarRecomendacion(String nombre, String comentario, String latitud, String longitud, String imagenBit, String tipo)
    {

        db.execSQL("UPDATE Recomendaciones SET nombre='"+nombre+"', comentario='"+comentario+"', latitud='"+latitud+"', longitud='"+longitud+"', imagenBit='"+imagenBit+"', tipo='"+tipo+"' WHERE nombre='"+nombre+"'");
    }

    public void eliminarRecomendacion(String nombre)
    {
        db.execSQL("DELETE FROM Recomendaciones WHERE nombre='"+nombre+"'");
    }

}
