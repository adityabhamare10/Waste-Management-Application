package com.hustler.wastemanagement;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;

public class ScanActivity2 extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private Button gallery, inferButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scan2);

        sharedPreferences = getSharedPreferences("WasteManagementPrefs", Context.MODE_PRIVATE);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Button to open the gallery and select an image
        gallery = findViewById(R.id.button2);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check for storage permission
                if (ContextCompat.checkSelfPermission(ScanActivity2.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ScanActivity2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                } else {
                    openGallery();
                }
            }
        });

        // Button to trigger the inference API call
        inferButton = findViewById(R.id.button);
        inferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the image path from SharedPreferences
                String imagePath = sharedPreferences.getString("selected_image_path", null);
                if (imagePath != null) {
                    File imageFile = new File(imagePath);
                    try {
                        // Call the method to run inference
                        InferenceClient.inferOnImage(imageFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ScanActivity2.this, "No image selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Open the gallery to pick an image
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    // Handle the result of the image picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String imagePath = getRealPathFromURI(selectedImage);

            if (imagePath != null) {
                // Store the selected image path in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selected_image_path", imagePath);
                editor.apply();

                Toast.makeText(this, "Image selected and saved", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to get the file path from the URI
    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }

    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
