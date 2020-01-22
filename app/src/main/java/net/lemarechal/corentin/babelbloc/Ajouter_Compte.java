package net.lemarechal.corentin.babelbloc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Ajouter_Compte extends AppCompatActivity {

    private static final String TABLE_P_KEY = "123";
    AlertDialog.Builder alertB;
    EditText et_Nom;
    EditText et_Prenom;
    EditText et_Identifiant;
    EditText et_Mdp;
    int retourAjouter ;
    String retour;
    int i =0;
    int resultatDelete;
    Button ajouter;
    GestureDetector gestureDetector;
    ArrayPersonnelAdapter arrayPersonnelAdapter;
    ArrayList<Personnel> arrayPersonnel = new ArrayList<Personnel>();
    ListView listeView;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_compte);
        alertB = new AlertDialog.Builder(this);
        ajouter = findViewById(R.id.Btn_Ajouter_Compte_Child);
        listeView = findViewById(R.id.listeView);
        int layoutID = R.layout.item_perso;
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        listeView.setAdapter(arrayPersonnelAdapter);

//        if(savedInstanceState != null){
//            arrayPersonnel = (ArrayList<Personnel> savedInstanceState.getSerializable(MainActivity.Tab))
//        }

        arrayPersonnelAdapter = new ArrayPersonnelAdapter(this,layoutID,arrayPersonnel);
        listeView.setAdapter(arrayPersonnelAdapter);
        et_Nom= findViewById(R.id.Et_Nom);
        et_Prenom = findViewById(R.id.Et_Prenom);
        et_Identifiant = findViewById(R.id.Et_Identifiant_Child);
        et_Mdp = findViewById(R.id.Et_Mdp);
        final SQLite sqLite = new SQLite(getApplicationContext(),null,null,1);

        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                try {
                    if (e2.getX() > e1.getX() && Math.abs(e2.getX() - e1.getX()) > 200) {
                        Intent Iretour = new Intent();
                        Iretour.putParcelableArrayListExtra("list",arrayPersonnel);
                        setResult(MainActivity.RESULT_OK, Iretour);
                        finish();
                    }

                } catch (Exception ex) {
                    alertB.setTitle(R.string.TitreErreur);
                    alertB.setMessage(ex.getMessage());
                }
                return true;

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    if(!arrayPersonnel.isEmpty()){
                        arrayPersonnel.clear();
                    }
                    SQLite sqLite = new SQLite(getApplicationContext(),null,null,1);
                    ArrayList<Personnel> array=sqLite.AfficherListe();
                    int i=0;
                     do{
                         Personnel p=(array.get(i));
                         arrayPersonnel.add(p);
                        i++;
                     }
        while (array.size() > i);
                }
                catch (final Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertB.setTitle(R.string.TitreErreur);
                            alertB.setMessage(e.getMessage());
                        }
                    });
                }
            }
        }).start();

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SQLite sqLite = new SQLite(getApplicationContext(), null, null, 1);
                            final Personnel personnel = new Personnel(et_Nom.getText().toString(), et_Prenom.getText().toString(), et_Identifiant.getText().toString(), et_Mdp.getText().toString());
                            retourAjouter = sqLite.AjouterPersonnel(personnel);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(retourAjouter == 1){
                                        arrayPersonnel.add(personnel);
                                        arrayPersonnelAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        } catch (final Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertB.setTitle(R.string.TitreErreur);
                                }
                            });
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    alertB.setTitle("Compte");
                                    switch (retourAjouter) {
                                        case -3:
                                            alertB.setMessage("Identifiant déjà saisi").show();
                                            break;

                                        case -2:
                                            alertB.setMessage("Problème lors de la création du compte").show();
                                            break;
                                        case -1:
                                            alertB.setMessage("Compte déjà crée").show();
                                            break;
                                        case 1:
                                            alertB.setMessage("Creation du compte réussi").show();
                                    }

                                } catch (Exception e) {
                                    alertB.setTitle(R.string.TitreErreur);
                                    alertB.setMessage(e.getMessage());
                                }

                            }
                        });
                    }
                }).start();
            }
        });

        listeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                try {
                    if (arrayPersonnel != null) {
                        final Personnel p = arrayPersonnel.get(position);
                        final AlertDialog.Builder alert = new AlertDialog.Builder(Ajouter_Compte.this);
                        alert.setTitle(getResources().getString(R.string.dialSupTitre));
                        alert.setMessage(getResources().getString(R.string.dialSupMessage));
                        alert.setPositiveButton(getResources().getString(R.string.dialSupBtnOUI), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            resultatDelete = sqLite.SupprimerPersonnel(p.PersonnelIdentifiant);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    if (resultatDelete == 1) {

                                                        alertB.setMessage("Vous avez supprimé le compte: ").show();
                                                        arrayPersonnel.remove(position);
                                                        arrayPersonnelAdapter.notifyDataSetChanged();
                                                    } else {
                                                        alertB.setMessage("Problème lors de la supression").show();
                                                    }
                                                }
                                            });


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();

                            }
                        });
                        alert.setNegativeButton(getResources().getString(R.string.dialSupBtnNON), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alert.show();
                    }


                } catch (Exception ex) {
                    alertB.setTitle(R.string.TitreErreur);
                    alertB.setMessage(ex.getMessage()).show();
                }




            }
        });

    }


}





