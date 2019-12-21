package com.appsit.inventorytracker.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appsit.inventorytracker.BuildConfig;
import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.utils.Utility;
import com.appsit.inventorytracker.viewmodels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private static final int REQUEST_CODE_CAMERA = 91;
    private static final int REQUEST_CODE_GALLERY = 92;
    private static final int REQUEST_CODE_TAKE_PHOTO = 101;
    private static final int REQUEST_CODE_PICK_GALLERY = 102;

    private ProgressDialog mProgress = null;
    private UserViewModel mUserViewModel;

    private LinearLayout layout1;
    private LinearLayout layout2;
    private EditText userName, userPass, confirmPass, userFullName, userDesignation, userEmail, phoneNumber;
    private ImageView userImage;

    private String imageName;
    private Bitmap bitmap;
    private String currentPhotoPath;
    private User mUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        layout1 = (LinearLayout) findViewById(R.id.first_portion);
        layout2 = (LinearLayout) findViewById(R.id.second_portion);

        userName = (EditText) findViewById(R.id.user_name);
        userPass = (EditText) findViewById(R.id.user_pass);
        confirmPass = (EditText) findViewById(R.id.user_confirm_pass);

        userImage = (ImageView) findViewById(R.id.user_image);
        userFullName = (EditText) findViewById(R.id.user_full_name);
        userDesignation = (EditText) findViewById(R.id.user_designation);
        userEmail = (EditText) findViewById(R.id.user_email);
        phoneNumber = (EditText) findViewById(R.id.user_phone_number);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        ((ImageView) findViewById(R.id.user_image)).setOnClickListener(new ActionEvent());
        ((Button) findViewById(R.id.user_next_button)).setOnClickListener(new ActionEvent());
        ((Button) findViewById(R.id.sign_up_button)).setOnClickListener(new ActionEvent());
    }

    private class ActionEvent implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.user_next_button) {
                String name = userName.getText().toString();
                String pass = userPass.getText().toString();
                String cPass = confirmPass.getText().toString();
                if (pass.equals(cPass)) {
                    checkUserName(name, pass);
                } else {
                    Utility.alertDialog(SignUpActivity.this, "Check your confirm password.");
                }
            }
            if (v.getId() == R.id.user_image) {
                cameraGalleryAlertDialog();
            }
            if (v.getId() == R.id.sign_up_button) {
                if (bitmap != null) {
                    mProgress = Utility.showProgressDialog(SignUpActivity.this, getResources().getString( R.string.progress), false);
                    String id = UUID.randomUUID().toString();
                    String name = userFullName.getText().toString();
                    String desig = userDesignation.getText().toString();
                    String email = userEmail.getText().toString();
                    String phone = phoneNumber.getText().toString();
                    String imagePath = Utility.saveToInternalStorage(SignUpActivity.this, bitmap, imageName);
                    mUser.setUserId(id);
                    mUser.setFullName(name);
                    mUser.setDesignation(desig);
                    mUser.setEmail(email);
                    mUser.setPhoneNumber(phone);
                    mUser.setPhotoName(imageName);
                    mUser.setPhotoPath(imagePath);
                    if (mUser != null) {
                        saveData(mUser);
                        Utility.dismissProgressDialog(mProgress);
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please upload your photo", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        }
    }

    //====================================================| Camera and Gallery AlertDialog
    public void cameraGalleryAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_photo_option, null, false);
        builder.setView(view);
        builder.setCancelable(true);
        builder.create();

        final AlertDialog dialog = builder.show();

        ((ImageButton) view.findViewById(R.id.camera_id)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Runtime permissions
                if (ActivityCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
                } else {
                    getCamera();
                }
                dialog.dismiss();
            }
        });
        ((ImageButton) view.findViewById(R.id.gallery_id)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Runtime permissions
                if (ActivityCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                } else {
                    getImage();
                }
                dialog.dismiss();
            }
        });
    }

    //Runtime permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_GALLERY && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getImage();
        }
        if (requestCode == REQUEST_CODE_CAMERA) {
            getCamera();
        }
    }

    //----------------------------------------------| Take a photo with a camera app
    private void getCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                File photoFile = createImageFile();
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, photoFile);
                    Log.d(TAG, "Image Uri: "+photoURI);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,/* prefix */".jpg",/* suffix */storageDir/* directory */);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void getImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_GALLERY);
    }

    //===============================================| Image gallery with CropImage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //----------------------------------------------| Pick a photo from gallery
        if (requestCode == REQUEST_CODE_PICK_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    //.setOutputCompressQuality(10)
                    .start(this);
        }
        //----------------------------------------------| Take a photo from camera
        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
            Uri uri = Uri.fromFile(new File(currentPhotoPath));
            userImage.setImageURI(uri);
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    //.setOutputCompressQuality(10)
                    .start(this);
        }
        //----------------------------------------------| Get image after cropping
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                userImage.setImageURI(imageUri);
                imageName = "img_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";
                bitmap = ((BitmapDrawable) userImage.getDrawable()).getBitmap();
            }
        }
    }

    //====================================================| Check username
    private void checkUserName(String userName, String password) {
        mUserViewModel.getUserByUserName(userName).observe(SignUpActivity.this, new Observer<User>() {
            @Override
            public void onChanged(User model) {
                if (model == null) {
                    mUser.setUsername(userName);
                    mUser.setPassword(Utility.encode(password));
                    if (layout2.getVisibility() == View.GONE) {
                        layout2.setVisibility(View.VISIBLE);
                        layout1.setVisibility(View.GONE);
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "This user already exists.", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });
    }

    //====================================================| Save Button
    private void saveData(User user) {
        long result = mUserViewModel.saveData(user);
        if (result > 0) {
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Error....", Snackbar.LENGTH_INDEFINITE).show();
        }
    }
}
