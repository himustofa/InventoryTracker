package com.appsit.inventorytracker.utils;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.views.activities.PurchaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.List;

public class Utility {

    //====================================================| DatePicker
    public static void getDate(Context context, EditText birth) {
        DatePicker datePicker = new DatePicker(context);
        int day = datePicker.getDayOfMonth();
        int mon = datePicker.getMonth();
        int year = datePicker.getYear();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                birth.setText(dayOfMonth +"/"+ (month+1) +"/"+ year);
            }
        }, year, mon, day).show();
    }

    //====================================================| Checkbox
    public static String getCheckboxValue(LinearLayout checkboxLayout) {
        StringBuilder value = new StringBuilder();
        for(int i=0; i<checkboxLayout.getChildCount(); i++) {
            CheckBox cb = (CheckBox) checkboxLayout.getChildAt(i);
            if (cb.isClickable()) {
                value.append(cb.getText().toString()).append(",");
            }
        }
        return value.toString();
    }

    //====================================================| For Image
    public static String bitmapToBase64(ImageView imageView) {
        String encode = null;
        if (imageView.getVisibility()== View.VISIBLE) {
            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            //String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"Title",null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                encode = Base64.getEncoder().encodeToString(stream.toByteArray());
            } else {
                encode = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT);
            }
        }
        return encode;
    }

    //https://github.com/elye/demo_android_base64_image/tree/master/app/src/main/java/com/elyeproj/base64imageload
    public static Bitmap base64ToBitmap(String encode) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            byte[] decodedBytes = Base64.getDecoder().decode(encode);
            bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } else {
            byte[] decodedString = android.util.Base64.decode(encode, android.util.Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return bitmap;
    }

    public static String saveToInternalStorage(Context context, Bitmap bitmapImage, String imageName){
        File directory = new File(context.getFilesDir() + "/UsersPhoto/");
        directory.mkdir(); //Create imageDir
        File file = new File(directory, imageName);
        try {
            OutputStream output = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, output); // Compress into png format image from 0% - 100%
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap loadFromInternalStorage(String imagePath, String imageName){
        Bitmap bitmap = null;
        try {
            File file = new File(imagePath, imageName);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //====================================================| Double Value Round
    public static double getRound(double value) {
        DecimalFormat df = new DecimalFormat("#.00000");
        return Double.parseDouble(df.format(value));
    }

    //====================================================| Remove first character zero from phone number
    public static String removeZero(String str) {
        //int n = Character.getNumericValue(str.charAt(0)
        if(Character.getNumericValue(str.charAt(0)) == 0) {
            return str.substring(1); //remove first character
        } else {
            return str;
        }
    }

    //====================================================| Get Time from Timestamp
    public static String getTimeFromTimestamp(String input) {
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(input);
        return String.valueOf(java.text.DateFormat.getTimeInstance().format(ts.getTime())); //java.text.DateFormat.getDateTimeInstance().format(ts.getTime())
    }

    public static String getDateFromTimestamp(String input) {
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(input);
        return String.valueOf(new java.text.SimpleDateFormat("dd-MM-yyyy").format(ts)); //java.text.DateFormat.getDateTimeInstance().format(ts.getTime())
    }

    //====================================================| Round a double to 2 decimal
    public static double roundTwoDecimal(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    //====================================================| Alert Dialog
    public static void alertDialog(final Context context, String msg) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.alert_title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public static void aboutMe(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.about_title)
                .setMessage(context.getString(R.string.apps_details))
                .setPositiveButton(R.string.alert_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public static AlertDialog.Builder deleteDialog(final Context context) {
        return new AlertDialog.Builder(context).setTitle("Are your sure?").setMessage("Do you want to delete it?").setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    public static void getSpinnerData(final AdapterPosition mPosition, Context context, Spinner spinner, List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPosition.onPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    public interface AdapterPosition {
        void onPosition(int position);
    }

    //===============================================| ProgressDialog
    public static ProgressDialog showProgressDialog(Context mActivity, final String message, boolean isCancelable) {
        ProgressDialog mProgress = new ProgressDialog(mActivity);
        mProgress.show();
        mProgress.setCancelable(isCancelable); //setCancelable(false); = invisible clicking the outside
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.setMessage(message);
        return mProgress;
    }

    public static void dismissProgressDialog(ProgressDialog mProgress) {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }

    //===============================================| Encryption and Decryption
    public static String encode(String input) throws UnsupportedEncodingException {
        byte[] data = input.getBytes("UTF-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(data);
        } else {
            return android.util.Base64.encodeToString(data, android.util.Base64.DEFAULT);
        }
    }

    public static String decode(String base64) throws UnsupportedEncodingException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            byte[] decodedBytes = Base64.getDecoder().decode(base64);
            return new String(decodedBytes, "UTF-8");
        } else {
            byte[] decodedBytes = android.util.Base64.decode(base64, android.util.Base64.DEFAULT);
            return new String(decodedBytes, "UTF-8");
        }
    }

    /*
    implementation 'commons-codec:commons-codec:1.13'
    private static final String key = "aesEncryptionKey";
    private static final String initVector = "encryptionIntVec";

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    */
}
