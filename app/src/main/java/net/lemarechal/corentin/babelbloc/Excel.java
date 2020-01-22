package net.lemarechal.corentin.babelbloc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.inputmethodservice.Keyboard;
import android.os.Environment;


import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xslf.usermodel.XSLFSlideShowFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import androidx.annotation.Nullable;

public class Excel extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Layout.db";
    public static final String TABLE_NAME = "Personnel";
    public static final String COLUMN_IDENTIFIANT = "Identifiant";
    public static final String COLUMN_NOM = "Nom";
    public static final String COLUMN_PRENOM = "Prenom";
    public static final String COLUMN_MDP= "Mdp";

    public static String Type;
    public static String Parametres;
    public static String Langue ;
    public static String Drapeau ;
    public static String Text ;
    public static String Edit_Text ;
    public static String NbrChoixQCU ;
    public static String TextChoixQCU;
    public static String NbrChoixQCM ;
    public static String TextQCM ;


    public Excel(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE_QUESTION = "CREATE TABLE Question (Type VARCHAR(20) NOT NULL, Parametre VARCHAR(30));";
            db.execSQL(CREATE_TABLE_QUESTION);
            String CREATE_TABLE_QCU = "CREATE TABLE QCM (Type VARCHAR(20) NOT NULL, Parametre VARCHAR(30),Parametre2 VARCHAR(30),Parametre3 VARCHAR(30),Parametre4 VARCHAR(30));";
            db.execSQL(CREATE_TABLE_QCU);
            String CREATE_TABLE_QCM = "CREATE TABLE QCM (Type VARCHAR(20) NOT NULL, Parametre VARCHAR(30),Parametre2 VARCHAR(30),Parametre3 VARCHAR(30),Parametre4 VARCHAR(30));";
            db.execSQL(CREATE_TABLE_QCM);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public List<Excel> LireExcel(){
        List<Excel> excels = null;
        try {
            String ficher= "/storage/D2C3-7B63/Check-list.xlsx";
             excels = new ArrayList<>();
            int index = 1;

            FileInputStream execlfile = new FileInputStream(new File("/storage/D2C3-7B63/Check-list.xlsx"));
            Workbook workbook = new XSSFWorkbook(execlfile);
            Sheet sheet = workbook.getSheet("Calcul");
            Row row = sheet.getRow(1);
            while (row != null){
                Excel excel = LireData(row);
               excels.add(excel);
                row = sheet.getRow(index++);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return excels;
    }

    private static Excel LireData(Row row){
        Excel excel = null;
        String type = row.getCell(0).getStringCellValue();
        String parametres = row.getCell(1).getStringCellValue();
        excel.Type = type;
        excel.Parametres = parametres;
        return excel;
    }
}
