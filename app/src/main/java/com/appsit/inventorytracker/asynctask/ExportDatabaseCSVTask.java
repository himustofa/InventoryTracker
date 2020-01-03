package com.appsit.inventorytracker.asynctask;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExportDatabaseCSVTask extends AsyncTask<Void, Void, Void> {

    /*private Context mContext;
    private ArrayList<Sale> mArrayList;

    public ExportDatabaseCSVTask(Context mContext, ArrayList<Sale> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }*/

    protected Void doInBackground(Void... voids) {
        /*File exportDir = new File(Environment.getExternalStorageDirectory(), "/Backup/");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File file = new File(exportDir, "ExcelFile.csv");

        String[] columns = {"First Name", "Last Name", "Email", "Date Of Birth"};
        List<Contact> contacts = new ArrayList<>();

        try {
            file.createNewFile();

            contacts.add(new Contact("Mamun", "Islam", "mamun@gmail.com", "17/01/1980"));
            contacts.add(new Contact("Nasir", "Hossain", "nasir@gmail.com", "17/08/1989"));
            contacts.add(new Contact("Fuad", "Islam", "fuad@gmail.com", "17/07/1956"));
            contacts.add(new Contact("Abdur", "Rahman", "abrahman@gmail.com", "17/05/1988"));

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

            for (Contact contact : contacts) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(contact.firstName);
                row.createCell(1).setCellValue(contact.lastName);
                row.createCell(2).setCellValue(contact.email);
                row.createCell(3).setCellValue(contact.dateOfBirth);
            }

            // Resize all columns to fit the content size
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        /*try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

            //data
            ArrayList<String> listdata= new ArrayList<String>();
            listdata.add("Aniket");
            listdata.add("Shinde");
            listdata.add("pune");
            listdata.add("anything@anything");
            //Headers
            String arrStr1[] ={"First Name", "Last Name", "Address", "Email"};
            csvWrite.writeNext(arrStr1);

            String arrStr[] ={listdata.get(0), listdata.get(1), listdata.get(2), listdata.get(3)};
            csvWrite.writeNext(arrStr);

            csvWrite.close();
            return "";
        } catch (IOException e){
            Log.e("MainActivity", e.getMessage(), e);
            return "";
        }*/
        return null;
    }

    static class Contact {
        String firstName;
        String lastName;
        String email;
        String dateOfBirth;

        Contact(String firstName, String lastName, String email, String dateOfBirth) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.dateOfBirth = dateOfBirth;
        }
    }
}
