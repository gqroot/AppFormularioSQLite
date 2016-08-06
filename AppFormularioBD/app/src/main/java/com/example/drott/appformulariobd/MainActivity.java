package com.example.drott.appformulariobd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Cursor fila;
    public EditText etn,eta,etc,ett,ete;
    public RadioButton rb, rb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etn=(EditText)findViewById(R.id.ETNombre);
        eta=(EditText)findViewById(R.id.ETApellido);
        etc=(EditText)findViewById(R.id.ETCedula);
        ett=(EditText)findViewById(R.id.ETTelefono);
        ete=(EditText)findViewById(R.id.ETEmail);
        rb =(RadioButton)findViewById(R.id.RBMasculino);
        rb2 =(RadioButton)findViewById(R.id.RBFemenino);
    }
    public void Grabar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cedula = etc.getText().toString();
        String nombre = etn.getText().toString();
        String apellido = eta.getText().toString();
        String telefono = ett.getText().toString();
        String email = ete.getText().toString();



        ContentValues registro = new ContentValues();  //es una clase para guardar datos
        registro.put("cedula", cedula);
        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("telefono", telefono);
        registro.put("correo", email);
        if(rb.isChecked()){
            registro.put("sexo", "Masculino");
        }else{
            registro.put("sexo", "Femenino");
        }

        long a= bd.insert("persona", null, registro);
        bd.close();
        etn.setText("");
        eta.setText("");
        etc.setText("");
        ett.setText("");
        ete.setText("");
        if(a==-1){
            Toast.makeText(this, "NO se puedo grabar",
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Se cargaron los datos de la persona",
                    Toast.LENGTH_SHORT).show();}
    }
    public void Consulta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase(); //Create and/or open a database that will be used for reading and writing.
        String ced = etc.getText().toString();
        Cursor fila = bd.rawQuery(  //devuelve 0 o 1 fila //es una consulta
                "select nombre,apellido,telefono,correo,sexo from persona where cedula=" + ced, null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            etn.setText(fila.getString(0));
            eta.setText(fila.getString(1));
            ett.setText(fila.getString(2));
            ete.setText(fila.getString(3));
            if(fila.getString(4).equals("Masculino")){
                rb.setChecked(true);
                rb2.setChecked(false);
            }else{
                rb2.setChecked(true);
                rb.setChecked(false);
            }


        } else
            Toast.makeText(this, "No existe una persona con dicha cedula" ,
                    Toast.LENGTH_SHORT).show();
        bd.close();

    }
}
