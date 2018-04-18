package com.duytue.finalproject;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceInfoActivity extends AppCompatActivity {

    ImageView imgPlace;
    TextView tvName, tvAddress, tvPhoneNo, tvWebsite,tvOpenHour;
    Place place;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        initComponents();
        getData();
        updateLayout();
    }

    private void initComponents() {
        builder = new AlertDialog.Builder(PlaceInfoActivity.this);
        imgPlace = (ImageView) findViewById(R.id.imageView);
        tvName =(TextView) findViewById(R.id.nameView);
        tvPhoneNo = (TextView) findViewById(R.id.detail_phone);
        tvAddress = (TextView) findViewById(R.id.detail_address);
        tvOpenHour = (TextView) findViewById(R.id.detail_hour);
        tvWebsite = (TextView) findViewById(R.id.detail_web);
    }
    private void getData() {
        Intent intent = getIntent();

        /*byte[] byteArr = intent.getByteArrayExtra("placeImgURL");
        LatLng latlng = new LatLng(intent.getDoubleExtra("lat",0),intent.getDoubleExtra("lng",0));
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
        place = new Place(intent.getStringExtra("name"),intent.getStringExtra("address"),
                intent.getStringExtra("phoneNo"),latlng,intent.getStringExtra("websiteURL"),intent.getStringExtra("hours"),
                bmp);*/
        String name = intent.getStringExtra("name");
        for (int i = 0; i < MainActivity.placesList.size(); ++i) {
            if (MainActivity.placesList.get(i).name.equals(name)) {
                place = MainActivity.placesList.get(i);
                return;
            }
        }
    }
    public void onClickCall(View view) {
        if(!tvPhoneNo.getText().equals("N/A")) {
            builder.setMessage("Do you want to make a call to " + place.phoneNo + "?").setTitle(R.string.call_dialog_title);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    try {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel: " + place.phoneNo));

                        if (ActivityCompat.checkSelfPermission(PlaceInfoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(PlaceInfoActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

                        }
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
        }
         else
        {
            builder.setMessage("Phone number for this place is not available.").setTitle(R.string.web_dialog_title);
        }
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.create();
        builder.show();
    }

    private void updateLayout() {
        imgPlace.setImageBitmap(place.getImg());
        tvName.setText(place.getName());
        if(!place.getWebsiteURL().isEmpty()) {
            tvWebsite.setText(place.getWebsiteURL());
        }
        else
        {
            tvWebsite.setText("N/A");

        }
        tvAddress.setText(place.getAddress());
        tvOpenHour.setText(place.getHours());
        if(!place.getPhoneNo().isEmpty()) {
            tvPhoneNo.setText(place.getPhoneNo());
        }else
            tvPhoneNo.setText("N/A");
    }

    public void onClickWeb(View view) {
        if (!tvWebsite.getText().equals("N/A"))
        {
            builder.setMessage(R.string.web_dialog_message).setTitle(R.string.web_dialog_title);

            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button

                try {
                        Uri webpage = Uri.parse(place.websiteURL);
                        if (!place.websiteURL.startsWith("http://") || !place.websiteURL.startsWith("https://")) {
                            webpage = Uri.parse("http://" + place.websiteURL);
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(webpage);
                        if (ActivityCompat.checkSelfPermission(PlaceInfoActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(PlaceInfoActivity.this, new String[]{Manifest.permission.INTERNET}, 1);

                        }
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
    }
    else
        {
            builder.setMessage("Website for this place is not available.").setTitle(R.string.web_dialog_title);
        }
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.create();
        builder.show();
    }

    public void onClickMap(View view) {
        builder.setMessage(R.string.map_dialog_message).setTitle(R.string.map_dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(PlaceInfoActivity.this, MapsActivity.class);


                for (int i = 0; i < MainActivity.placesList.size(); ++i) {
                    if (MainActivity.placesList.get(i).name.equals(place.name)){
                        intent.putExtra("position", i);
                        break;
                    }
                }
//                intent.putExtra("name", place.name);
//
//                intent.putExtra("address", place.address);
//
//                intent.putExtra("lat",place.latLng.latitude);
//                intent.putExtra("lng",place.latLng.longitude);
//                // convert bitmap to byte[] in order to send through intent
//                Bitmap bmp = place.getImg();
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                intent.putExtra("placeImgURL", byteArray);
                startActivity(intent);  }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.create();
        builder.show();

    }
}
