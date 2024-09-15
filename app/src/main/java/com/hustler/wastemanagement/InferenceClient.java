package com.hustler.wastemanagement;

import android.util.Log;

import okhttp3.*;
import java.io.File;
import java.io.IOException;

public class InferenceClient {

    private static final String API_URL = "https://detect.roboflow.com/waste-classifcation/1";
    private static final String API_KEY = "q3YW5rcc2j27BBpiaSet";
    private static final OkHttpClient client = new OkHttpClient();

    public static void inferOnImage(File imageFile) throws IOException {
        // Create the multipart request body
        RequestBody fileBody = RequestBody.create(imageFile, MediaType.parse("image/png"));

        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "img.png", fileBody)
                .build();

        // Build the request
        Request request = new Request.Builder()
                .url(API_URL + "?api_key=" + API_KEY) // Add the API key to the URL
                .post(requestBody)
                .build();

        // Execute the request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                // Get the response body
                String responseData = response.body().string();
                System.out.println(responseData);
                Log.e("Result", responseData);
                // Here, you would parse the JSON and extract the 'top' result
            }
        });
    }
}
