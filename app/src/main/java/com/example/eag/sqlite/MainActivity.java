package com.example.eag.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText codigo, nombre, precio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codigo = (EditText) findViewById(R.id.codigo);
        nombre = (EditText)  findViewById(R.id.nombre);
        precio = (EditText)  findViewById(R.id.precio);

    }

    public void registrar(View view){
        //Creamos instancia de nuestra clase
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion",null, 1);

        //Damos permisos de escritura a nuestra BD
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //Obtenemos datos del usuario
        String cod = codigo.getText().toString();
        String nom = nombre.getText().toString();
        String prec = precio.getText().toString();

        //Validamos los datos

        if(!cod.isEmpty() && !nom.isEmpty() && !prec.isEmpty()){

            //Creamos contenedor de valores y guardamos datos del usuario en él
            ContentValues datos = new ContentValues();
            datos.put("codigo", cod);
            datos.put("nombre", nom);
            datos.put("precio", prec);

            //Insertamos los datos en la BD
            BaseDeDatos.insert("articulos", null, datos);

            //Cerramos conexión
            BaseDeDatos.close();

            //Notificamos al usuario
            Toast.makeText(this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();

            //Vaciamos campos
            codigo.setText("");
            nombre.setText("");
            precio.setText("");


        }else{
            Toast.makeText(this, "Introduce todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscar(View view){
        //Creamos instancia de nuestra clase
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion",null, 1);

        //Damos permisos de escritura a nuestra BD
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //Recuperamos el código de búsqueda
        String cod = codigo.getText().toString();

        //Validamos que el usuario ha introducido el código

        if(!cod.isEmpty()){

            //Creamos un Cursor para almacenar los datos que recupera nuestra consulta
            Cursor fila = BaseDeDatos.rawQuery("select nombre, precio from articulos where codigo="+cod, null);

            if(fila.moveToFirst()){
                //Mostramos los datos
                nombre.setText(fila.getString(0));
                precio.setText(fila.getString(1));
            }else{
                Toast.makeText(this, "El artículo que busca no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Introduce el código de búsqueda", Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizar (View view){
        //Creamos instancia de nuestra clase
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion",null, 1);

        //Damos permisos de escritura a nuestra BD
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //Recojo los datos
        String cod = codigo.getText().toString();
        String nom = nombre.getText().toString();
        String prec = precio.getText().toString();


        if(!cod.isEmpty() && !nom.isEmpty() && !prec.isEmpty()){
            //Guardamos los datos en un ContentValues
            ContentValues registro = new ContentValues();
            registro.put("codigo", cod);
            registro.put("nombre", nom);
            registro.put("precio", prec);

            //Actualizamos los datos en la BD

            int afectados = BaseDeDatos.update("articulos", registro, "codigo ="+cod, null);

            BaseDeDatos.close();

            if(afectados == 1){
                Toast.makeText(this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Producto no actualizado", Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(this, "Ningún campo puede esta vacío", Toast.LENGTH_SHORT).show();
        }
    }

    public void borrar(View view){
        //Creamos instancia de nuestra clase
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion",null, 1);

        //Damos permisos de escritura a nuestra BD
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //Recojo los datos
        String cod = codigo.getText().toString();

        if(!cod.isEmpty()){
            //Borramos los datos
            int borrados = BaseDeDatos.delete("articulos", "codigo = "+cod,  null);

            BaseDeDatos.close();

            if(borrados == 1){
                Toast.makeText(this, "Los datos se han borrado correctamente", Toast.LENGTH_SHORT).show();
                codigo.setText("");
                nombre.setText("");
                precio.setText("");

            }else{
                Toast.makeText(this, "No se han podido borrar los datos", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Introduce el código del producto a borrar", Toast.LENGTH_SHORT).show();
        }
    }

}
