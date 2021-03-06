package net.lemarechal.corentin.babelbloc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class SQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BDD.db";
    public static final String TABLE_NAME = "Personnel";
    public static final String COLUMN_IDENTIFIANT = "Identifiant";
    public static final String COLUMN_NOM = "Nom";
    public static final String COLUMN_QUESTION= "Question";
    public static final String COLUMN_TYPE= "Type";
    public static final String COLUMN_TYPE_QUESTION= "Type_Question";
    public static final String COLUMN_TYPE_CHOIX= "Choix";
    public static final String COLUMN_TYPE_CHOIX2= "Choix2";
    public static final String COLUMN_TYPE_CHOIX3= "Choix3";
    public static final String COLUMN_TYPE_CHOIX4= "Choix4";
    public static final String COLUMN_PRENOM = "Prenom";
    public static final String COLUMN_MDP= "Mdp";

    public SQLite( Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table if not exists drapeaux(data blob not null, code varchar(8) not null)");
            String CREATE_TABLE_QUESTION = "CREATE TABLE Question (Question VARCHAR(20) NOT NULL, Type_Question VARCHAR(20) NOT NULL);";
            db.execSQL(CREATE_TABLE_QUESTION);
            String CREATE_TABLE_QCU = "CREATE TABLE QCU (Question VARCHAR(20) NOT NULL, Choix VARCHAR(30),Choix2 VARCHAR(30),Choix3 VARCHAR(30),Choix4 VARCHAR(30));";
            db.execSQL(CREATE_TABLE_QCU);
            String CREATE_TABLE_QCM = "CREATE TABLE QCM (Question VARCHAR(20) NOT NULL, Choix VARCHAR(30),Choix2 VARCHAR(30),Choix3 VARCHAR(30),Choix4 VARCHAR(30));";
            db.execSQL(CREATE_TABLE_QCM);
            String CREATE_TABLE = "CREATE TABLE Personnel (Nom VARCHAR(30) NOT NULL, Prenom VARCHAR(30) NOT NULL, Identifiant VARCHAR(30) NOT NULL, Mdp VARCHAR(45) NOT NULL);";
            db.execSQL(CREATE_TABLE);
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOM, "admin");
            values.put(COLUMN_PRENOM, "admin");
            values.put(COLUMN_MDP, "admin");
            values.put(COLUMN_IDENTIFIANT, "admin");
            long count = db.insert(TABLE_NAME, null, values);
            if (count != -1)
                db.setTransactionSuccessful();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public List<Drapeau> getDrapeaux() throws Exception {
        List<Drapeau> drapeaux = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from drapeaux", null);
            while (cursor.moveToNext()) {
                byte[] bitmapdata = cursor.getBlob(0);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                String code = cursor.getString(1);
                drapeaux.add(new Drapeau(bitmap, code));
            }
            cursor.close();
        } catch (Exception e){
            throw e;
        }
        return drapeaux;
    }

    public Drapeau getDrapeau(String code) throws Exception {
        Drapeau drapeau = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("select data from drapeaux where code = '"+code+"'", null);
            while (cursor.moveToNext()) {
                byte[] bitmapdata = cursor.getBlob(0);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                drapeau = new Drapeau(bitmap, code);
            }
            cursor.close();
        } catch (Exception e){
            throw e;
        }
        return drapeau;
    }


    public void reimportDrapeaux(File f) throws Exception {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            for (File lf : f.listFiles()){
                if (lf.getName().endsWith(".png")) {
                    Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath()+"/"+lf.getName());
                    String code = lf.getName().replaceAll(".png", "");
                    ByteArrayOutputStream blob = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, blob);
                    byte[] bitmapdata = blob.toByteArray();
                    ContentValues values = new ContentValues();
                    values.put("data", bitmapdata);
                    values.put("code", code);
                    db.insert("drapeaux", null, values);
                }
            }
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME+";");
        }
    }

    public int AjouterPersonnel (Personnel personnel ) {

        ArrayList<Personnel> array=AfficherListe();
        if(array.size() != 0){
            int i=0;
            do{
                Personnel p=(array.get(i));
                i++;
                if(personnel.PersonnelIdentifiant.equals(p.PersonnelIdentifiant)){
                    return -3;
                }
            }
            while (array.size() > i);
        }
            SQLiteDatabase Db = getWritableDatabase();

            Db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                // values.put(COLUMN_ID,personnel.getID());
                values.put(COLUMN_NOM, personnel.getPersonnelName());
                values.put(COLUMN_PRENOM, personnel.getPersonnelPrenom());
                values.put(COLUMN_MDP, personnel.getPersonnelMdp());
                values.put(COLUMN_IDENTIFIANT, personnel.getPersonnelIdentifiant());
                long count = Db.insert(TABLE_NAME, null, values);

                if (count != -1)
                    Db.setTransactionSuccessful();
                else
                    return -2;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Db.endTransaction();
            }
            return 1;
        }
     public ArrayList<Personnel> AfficherListe() {


         Cursor cursor= null;
         Personnel p = null;
         ArrayList<Personnel> personnels= new ArrayList<>();
         try {
             String query = "Select* FROM " + TABLE_NAME+";";
             SQLiteDatabase db = this.getWritableDatabase();
              cursor = db.rawQuery(query, null);
             while (cursor.moveToNext()){
                 p = new Personnel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                 personnels.add(p);
             }
             cursor.close();
             db.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
         return personnels ;
     }

    public int SupprimerPersonnel (String Identifiant){

        SQLiteDatabase Db=null;
        try {
                 Db = getWritableDatabase();
                Db.beginTransaction();
                long count=  Db.delete(TABLE_NAME,"Identifiant = '" +Identifiant +"'",null);
                if(count != -1)
                    Db.setTransactionSuccessful();
                else {
                    return -1;
                }


        }

        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(Db != null)
            Db.endTransaction();
        }
        return 1;
    }

    public boolean Connection (String Identifiant , String mdp){
        boolean conn = false;
        String result = "";
        try {
            String query = "Select Identifiant, Mdp FROM " + TABLE_NAME+" WHERE Identifiant = "+"'" +Identifiant +"'"+" AND Mdp = "+"'" +mdp +"'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.getCount() >0) {
                conn = true;
            }
                cursor.close();
                db.close();

        } catch (Exception e) {
        e.getMessage();
        }
        return conn;
    }

    public int AjouterQuestion (String question, String type_question) {

        SQLiteDatabase Db = getWritableDatabase();

        Db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_QUESTION, question );
            values.put(COLUMN_TYPE_QUESTION,type_question);
            long count = Db.insert("Question", null, values);
            if (count != -1)
                Db.setTransactionSuccessful();
            else
                return -2;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Db.endTransaction();
        }
        return 1;
    }
    public int AjouterQCU (String question, String choix,String choix2,String choix3,String choix4) {

        SQLiteDatabase Db = getWritableDatabase();

        Db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_QUESTION, question);
            values.put(COLUMN_TYPE_CHOIX,choix);
            values.put(COLUMN_TYPE_CHOIX,choix2);
            values.put(COLUMN_TYPE_CHOIX,choix3);
            values.put(COLUMN_TYPE_CHOIX,choix4);
            long count = Db.insert("QCU", null, values);
            if (count != -1)
                Db.setTransactionSuccessful();
            else
                return -2;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Db.endTransaction();
        }
        return 1;
    }

}

