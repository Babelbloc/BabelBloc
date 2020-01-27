package net.lemarechal.corentin.babelbloc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SelectLangueActivity extends AppCompatActivity {

    static ArrayList<Langue> listLangue;
    static Langue langueSelectionne;
    public static Spinner langue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.langueselect);
            langue = findViewById(R.id.langueSelection);
            langue.setDropDownWidth(305);
            listLangue = new ArrayList<>();
            listLangue.add(new Langue("Français", MainActivity.sqLite.getDrapeau("fr")));
            listLangue.add(new Langue("Anglais", MainActivity.sqLite.getDrapeau("gb")));
            listLangue.add(new Langue("Espagnol", MainActivity.sqLite.getDrapeau("es")));
            listLangue.add(new Langue("Allemand", MainActivity.sqLite.getDrapeau("de")));
            listLangue.add(new Langue("Arabe", MainActivity.sqLite.getDrapeau("arabe")));
            listLangue.add(new Langue("Italien", MainActivity.sqLite.getDrapeau("it")));
            listLangue.add(new Langue("Portugais", MainActivity.sqLite.getDrapeau("pt")));
            listLangue.add(new Langue("Russe", MainActivity.sqLite.getDrapeau("ru")));
            listLangue.add(new Langue("Chinois", MainActivity.sqLite.getDrapeau("cn")));
            listLangue.add(new Langue("Japonnais", MainActivity.sqLite.getDrapeau("jp")));
            listLangue.add(new Langue("Indien", MainActivity.sqLite.getDrapeau("in")));
            LangueAdapter dataAdapter = new LangueAdapter(getApplicationContext(), listLangue);
            langue.setAdapter(dataAdapter);
            langue.setOnLongClickListener(new AdapterView.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    try {
                        Toast.makeText(getApplicationContext(), langueSelectionne.getNom(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create().show();
                    }
                    return false;
                }
            });
            langue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        langueSelectionne = listLangue.get(position);
                    } catch (Exception e) {
                        e.printStackTrace();
                        new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create().show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            Button button = findViewById(R.id.langueSelValid);
            button.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), CheckListActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create().show();
        }
    }

}
