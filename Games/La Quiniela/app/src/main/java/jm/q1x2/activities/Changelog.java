package jm.q1x2.activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import jm.q1x2.R;

public class Changelog extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_changelog);
        TextView t= (TextView) findViewById(R.id.contenido);
        
        String contenido= "<error en la carga del histórico de cambios>";

        StringBuilder sb = new StringBuilder();
        try
        {
            InputStream is= this.getBaseContext().getAssets().open("changelog.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null)
                sb.append(line).append("\n");
            is.close();
        } 
        catch(Exception ex)
        {
        	// no hago nada, para que se quede con el valor inicial
        }
        contenido = sb.toString();
        
        t.setText(contenido);
   }    

    public void boton_aceptar(View v)
    {
       	finish();
    }
}
