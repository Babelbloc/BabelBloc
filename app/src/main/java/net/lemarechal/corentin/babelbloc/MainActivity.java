package net.lemarechal.corentin.babelbloc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final private int MENU_PREFERENCES= Menu.FIRST;
    static final private int Code_Requetes= 1234;
    public static final ArrayList<Personnel> P_REPONSE =new ArrayList<Personnel>();
    public static final SQLite PARAM_SOURCE =null;
    AlertDialog.Builder alertB;
    private static final String TABLE_P_KEY = "123";
    SQLite sqLite ;
    boolean connexion = false;


    ArrayPersonnelAdapter arrayPersonnelAdapter;
    ArrayList<Personnel> arrayPersonnel = new ArrayList<Personnel>();
    ListView listeView;
    ListView lsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    try {
    sqLite =new SQLite(getApplicationContext(),null,null,1);
    alertB = new AlertDialog.Builder(this);

    final Button[] ajouter = new Button[1];
    listeView = findViewById(R.id.lsView);
    int layoutID = R.layout.item_perso;
    listeView.setAdapter(arrayPersonnelAdapter);

        if(savedInstanceState != null){
            arrayPersonnel = ((ArrayList<Personnel>) savedInstanceState.getSerializable(MainActivity.TABLE_P_KEY));
        }

    arrayPersonnelAdapter = new ArrayPersonnelAdapter(this,layoutID,arrayPersonnel);
    listeView.setAdapter(arrayPersonnelAdapter);

    final EditText etPass= findViewById(R.id.Et_Pass);
    final boolean[] conn = {false};

       AfficherListe();
//        File file = new File( getApplicationContext().getFilesDir(),"test");
//        FileInputStream execlfile = new FileInputStream(new File("/storage/self/primary/BabelBLOC/babelBLOCconfigFR.xlsx"));
//        Workbook workbook = new HSSFWorkbook(execlfile);
//        Sheet datasheet = workbook.getSheet("Type");
//        Cell cell = datasheet.getRow(0).getCell(1);
//        alertB.setMessage(cell.getStringCellValue());

        Excel excel = new Excel(getApplicationContext(),null,null,1);
        excel.LireExcel();
        listeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                try{
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle(getResources().getString(R.string.dialConnTitre));
                    alert.setMessage(getResources().getString(R.string.dialConnMessage));
                    final Personnel p = arrayPersonnel.get(position);
                    alert.setPositiveButton(getResources().getString(R.string.dialConnectOUI), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            etPass.setEnabled(true);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    conn[0] =sqLite.Connection(p.PersonnelIdentifiant,etPass.getText().toString());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            if(conn[0]){
                                                alertB.setTitle(getResources().getString(R.string.dialConnTitre));
                                                alertB.setMessage((R.string.dialidentenfiant_OK)).show();
                                                connexion=true;
                                                invalidateOptionsMenu();

                                            }
                                            else{
                                                alertB.setMessage((R.string.dialidentifiant_KO)).show();
                                            }
                                        }
                                    });
                                }
                            }).start();
                        }
                    });
                    alert.setNegativeButton(getResources().getString(R.string.dialConnectNON), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert.show();
                }
                catch (Exception e){
                    alertB.setTitle(R.string.TitreErreur);
                    alertB.setMessage(e.getMessage());
                }

            }
        });


}
catch (Exception e){
    alertB.setTitle(R.string.TitreErreur);
    alertB.setMessage(e.getMessage()).show();
}



    }
    public void AfficherListe(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    if(!arrayPersonnel.isEmpty()){
                        arrayPersonnel.clear();
                    }
                    ArrayList<Personnel> array=sqLite.AfficherListe();
                    int i=0;
                    do{
                        Personnel p=(array.get(i));
                        arrayPersonnel.add(p);
                        i++;
                        arrayPersonnelAdapter.notifyDataSetChanged();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,MENU_PREFERENCES,Menu.NONE,"Gestion des comptes");
        if(!connexion)
            menu.getItem(0).setEnabled(false);
        else
            menu.getItem(0).setEnabled(true);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case (Code_Requetes):
                switch (resultCode){
                    case (Activity.RESULT_OK):
                        try{
                            if(data != null){
                           ArrayList<Personnel> arrayList  =data.getParcelableArrayListExtra("list");
                           if(!arrayPersonnel.isEmpty()){
                                    arrayPersonnel.clear();
                                }
                                int i=0;
                                do{
                                    Personnel p=(arrayList.get(i));
                                    arrayPersonnel.add(p);
                                    i++;
                                    arrayPersonnelAdapter.notifyDataSetChanged();
                                }
                                while (arrayList.size() > i);

                            }
                        }
                        catch (Exception e){
                            alertB.setTitle(R.string.TitreErreur);
                            alertB.setMessage(e.getMessage()).show();
                        }

                }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
        try {
            switch (item.getItemId()) {
                case (MENU_PREFERENCES): {
                    Intent sendIntent = new Intent(MainActivity.this, Ajouter_Compte.class);
                    sendIntent.putExtra("list", "");
                    startActivityForResult(sendIntent, Code_Requetes);
                    return true;
                }
            }
        } catch (Exception ex) {
            alertB.setTitle(R.string.TitreErreur);
           alertB.setMessage(ex.getMessage()).show();
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MainActivity.TABLE_P_KEY,this.arrayPersonnel);
    }
}