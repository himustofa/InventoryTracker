package com.appsit.inventorytracker.utils.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.appsit.inventorytracker.models.Sale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExportToExcelTask extends AsyncTask<List<Sale>, Void, Boolean> {

    private Context mContext;
    private ProgressDialog mProgress;
    private AsyncResponse asyncResponse;

    public interface AsyncResponse {
        void processFinish(boolean result);
    }

    public ExportToExcelTask(Context mContext, AsyncResponse response) {
        this.mContext = mContext;
        this.asyncResponse = response;
    }

    @Override
    protected void onPreExecute() {
        this.mProgress = new ProgressDialog(mContext);
        //mProgress.setTitle("Retrieving data");
        /*this.mProgress.setMessage(context.getString(R.string.progress));
        this.mProgress.setIndeterminate(true);
        this.mProgress.setCancelable(false);
        this.mProgress.show();*/
    }

    protected Boolean doInBackground(List<Sale>... params) {
        List<Sale> arrayList = params[0];

        String[] columns = {"Name", "Quantity", "Date", "Amount"};

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Contacts");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Create a Row
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Create Other rows and cells with contacts data
            int rowNum = 1;

            for (Sale sale : arrayList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(sale.getProductName());
                row.createCell(1).setCellValue(sale.getProductQuantity());
                row.createCell(2).setCellValue(sale.getSalesDate());
                row.createCell(3).setCellValue(sale.getSalesAmount());
            }

            // Resize all columns to fit the content size
            for (int i = 0; i < columns.length; i++) {
                sheet.setColumnWidth(i, (15 * 600));
                //sheet.autoSizeColumn(i);
            }

            // Create directory
            //File exportDir = new File(Environment.getExternalStorageDirectory(), "/Backup/");
            File directory = new File(mContext.getFilesDir() + "/UsersPhoto/");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory, "Book1.xlsx");
            //file.createNewFile();

            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if(this.mProgress != null) {
                this.mProgress.dismiss(); //close the dialog if error occurs
            }
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        this.asyncResponse.processFinish(result);
        if(this.mProgress != null) {
            this.mProgress.dismiss(); //close the dialog if error occurs
        }
    }
}
