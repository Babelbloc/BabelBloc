package net.lemarechal.corentin.babelbloc;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

public void Excel(){

}

    public List<String> LireExcel(){
        List<String> excels = null;
        try {
            int i =0 ;
             excels = new ArrayList<>();
            FileInputStream execlfile = new FileInputStream("/storage/D2C3-7B63/Check-list.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(execlfile);
            XSSFSheet sheet = workbook.getSheet("Calcul");
            XSSFRow row;
            Iterator rows = sheet.rowIterator();
            XSSFCell cell;
            while (rows.hasNext()){
                row =(XSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                 while (cells.hasNext()){
                    cell = (XSSFCell) cells.next();
                     if(( cell.getCellType()== XSSFCell.CELL_TYPE_STRING )){
                         excels.add(cell.getStringCellValue());
                     }
                     else
                    excels.add(String.valueOf(cell.getNumericCellValue()));
                 }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return excels;
    }

}
