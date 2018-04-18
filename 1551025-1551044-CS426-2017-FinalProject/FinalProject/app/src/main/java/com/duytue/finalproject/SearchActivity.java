package com.duytue.finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.duytue.finalproject.Information.InformationActivity;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.WebDetection;
import com.google.protobuf.ByteString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayAdapter<String> adapter;

    ArrayList<String> searchList;
    static Bitmap imgBitmap;

    private static final int CAMERA_REQUEST = 289;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = (SearchView)findViewById(R.id.searchView);
        listView = (ListView)findViewById(R.id.listSearchView);
        searchList = new ArrayList<String>();


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                int newPosition = 0;
                for (int index = 0 ; index < MainActivity.recipesList.size(); ++index) {
                    if (searchList.get(i).equals(MainActivity.recipesList.get(index).name)) {
                        newPosition = index;
                    }
                }
                intent.putExtra("position", newPosition);
                startActivity(intent);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchList = searchByName(query.split(" "));
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, searchList);
                listView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchList = searchByName(query.split(" "));
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, searchList);
                listView.setAdapter(adapter);
                return false;
            }
        });
    }


    public ArrayList<String> searchByName(String[] query) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < MainActivity.recipesList.size(); ++i) {
            Recipe temp = MainActivity.recipesList.get(i);
            for (int j = 0; j < query.length; ++j) {
                if (isFound(query[j], temp.name)) {
                    if (j == (query.length - 1)) {
                        result.add(temp.name);
                    }
                }
            }
        }
        return result;
    }

    public boolean isFound(String name, String sample) {
        return Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(sample).find();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static void detectWebDetections(String filePath, PrintStream out) throws Exception,
            IOException {
        Log.i("FUnction", "RUN");
        List<AnnotateImageRequest> requests = new ArrayList<>();

        //ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        ByteString imgBytes = ByteString.copyFrom(stream.toByteArray());

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.WEB_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {

            Log.i("client", "created");
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    out.printf("ErrorHERE: %s\n", res.getError().getMessage());
                    return;
                }

                // Search the web for usages of the image. You could use these signals later
                // for user input moderation or linking external references.
                // For a full list of available annotations, see http://g.co/cloud/vision/docs
                WebDetection annotation = res.getWebDetection();
                out.println("Entity:Id:Score");
                out.println("===============");
                for (WebDetection.WebEntity entity : annotation.getWebEntitiesList()) {
                    out.println(entity.getDescription() + " : " + entity.getEntityId() + " : "
                            + entity.getScore());
                }
                out.println("\nPages with matching images: Score\n==");
                for (WebDetection.WebPage page : annotation.getPagesWithMatchingImagesList()) {
                    out.println(page.getUrl() + " : " + page.getScore());
                }
                out.println("\nPages with partially matching images: Score\n==");
                for (WebDetection.WebImage image : annotation.getPartialMatchingImagesList()) {
                    out.println(image.getUrl() + " : " + image.getScore());
                }
                out.println("\nPages with fully matching images: Score\n==");
                for (WebDetection.WebImage image : annotation.getFullMatchingImagesList()) {
                    out.println(image.getUrl() + " : " + image.getScore());
                }
            }
        }
    }

    public void startCamera(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        } else
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgBitmap = photo;
            Log.i("Camera", "Returned");
            if (imgBitmap == null)
                Log.i("Image", "Is null");
            try {
                detectWebDetections("", System.out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
