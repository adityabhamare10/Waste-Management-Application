package com.hustler.wastemanagement;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScanActivity3 extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private ImageView imageView;
    private TextView resultTextView;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scan3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button takePictureButton = findViewById(R.id.button);
        Button pickImageButton = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);
        resultTextView = findViewById(R.id.result);

        takePictureButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
            } else {
                takePicture();
            }
        });

        pickImageButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_IMAGE_PICK);
            } else {
                pickImage();
            }
        });





    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickImage() {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImageIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
                uploadImage(getImageUriFromBitmap(imageBitmap)); // Upload captured image
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
                uploadImage(imageUri); // Upload picked image
            }
        }
    }

    private Uri getImageUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage(Uri imageUri) {
        OkHttpClient client = new OkHttpClient();
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            byte[] imageBytes = getBytes(inputStream);

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "image.jpg",
                            RequestBody.create(MediaType.parse("image/jpeg"), imageBytes))
                    .build();

            Request request = new Request.Builder()
                    .url("http://192.168.0.195:3000/predict")
                    .post(requestBody)
                    .build();

            new Thread(() -> {
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {

                        String jsonResponse = response.body().string();
                        Log.e("Result: ",jsonResponse);
                        try {
                            JSONObject jsonObject = new JSONObject(jsonResponse);
                            String topClass = jsonObject.getString("top");
                            // Update resultTextView with the top class
                            runOnUiThread(() -> resultTextView.setText(topClass));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            runOnUiThread(() -> Toast.makeText(ScanActivity3.this, "Error parsing JSON", Toast.LENGTH_SHORT).show());
                        }  // Update resultTextView with the response
                    } else {
                        runOnUiThread(() -> Toast.makeText(ScanActivity3.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(ScanActivity3.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}