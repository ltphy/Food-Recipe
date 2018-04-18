package com.duytue.finalproject.Information;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.duytue.finalproject.Cook;
import com.duytue.finalproject.MapsActivity;
import com.duytue.finalproject.PlaceActivity;
import com.duytue.finalproject.R;
import com.duytue.finalproject.Recipe;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;

import java.io.InputStream;
import java.util.ArrayList;

import static com.duytue.finalproject.MainActivity.cookList;
import static com.duytue.finalproject.MainActivity.recipesList;

public class InformationActivity extends AppCompatActivity {

    private int TAKE_PHOTO = 111;
    private int PICK_PHOTO = 222;

    ArrayList<String> instructions, ingredients;
    RowAdapter rowAdapter1, rowAdapter2;

    ImageView imageView;
    TextView nameView, descView;// , instructionView, ingredientView;
    String name, description;// , instruction = "", ingredient = "";


    Bitmap image;

    int position;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        overridePendingTransition(R.anim.animation_right_slide_left, R.anim.animation_left_slide_left);


        createToolbar();
        prepareInformation();

        startFacebook();
    }

    private void startFacebook() {
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.i("Status", "Success");
            }

            @Override
            public void onCancel() {
                Log.i("Status", "Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("Status", "Error");
            }
        });
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformationActivity.super.onBackPressed();
                overridePendingTransition(R.anim.animation_left_slide_right, R.anim.animation_right_slide_right);
            }
        });

    }

    private void prepareInformation() {
        Intent i = getIntent();

        position = i.getIntExtra("position", 0);
        Recipe temp;
        temp = recipesList.get(position);
        bindInformation(temp);
    }

    private void bindInformation(Recipe temp) {
        name = temp.getName();
        description = temp.getDesc();
        image = temp.getImg();

        int cookid = temp.getCookID();
        Cook cooktemp = new Cook();

        for (int i = 0; i < cookList.size(); ++i) {
            if (cookList.get(i).getId() == cookid) {
                cooktemp = cookList.get(i);
               break;
            }
        }


        // TODO: change this view into a simple list view
        /*for (int i = 0 ; i < cooktemp.ingredients.size(); ++i) {
            if (i != cooktemp.ingredients.size() - 1)
                ingredient += "•    " + cooktemp.ingredients.get(i) + '\n';
            else
                ingredient += "•    " + cooktemp.ingredients.get(i);
        }

        for (int i = 0 ; i < cooktemp.instruction.size(); ++i) {
            if (i != cooktemp.instruction.size() - 1)
                instruction += "•   " + cooktemp.instruction.get(i) + '\n' + '\n';
            else
                instruction += "•   " + cooktemp.instruction.get(i);
        }*/


        instructions = cooktemp.getInstruction();
        System.out.println(instructions.size());
        ingredients = cooktemp.getIngredients();

        rowAdapter1 = new RowAdapter(InformationActivity.this, ingredients);
        rowAdapter2 = new RowAdapter(InformationActivity.this, instructions);

        initiateRecyclerView();




        bindView();
    }

    private void bindView() {
        nameView = (TextView)findViewById(R.id.nameInfoView);
        descView = (TextView)findViewById(R.id.descInfoView);
        imageView = (ImageView)findViewById(R.id.imageInfoView);
        //instructionView = (TextView)findViewById(R.id.instructionInfoView);
        //ingredientView = (TextView)findViewById(R.id.ingredientInfoView);

        nameView.setText(name);
        imageView.setImageBitmap(image);
        descView.setText("  " + description);
        //instructionView.setText(instruction);
        //ingredientView.setText(ingredient);
    }

    public void initiateRecyclerView() {
        RecyclerView ingreRV = (RecyclerView) findViewById(R.id.ingredientInfoRecyclerView);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        ingreRV.setLayoutManager(layoutManager);
        ingreRV.setItemAnimator(new DefaultItemAnimator());
        ingreRV.setAdapter(rowAdapter1);

        RecyclerView instrRV = (RecyclerView) findViewById(R.id.instructionInfoRecyclerView);
        instrRV.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager2;
        layoutManager2 = new LinearLayoutManager(this);
        instrRV.setLayoutManager(layoutManager2);
        instrRV.setItemAnimator(new DefaultItemAnimator());
        instrRV.setAdapter(rowAdapter2);
    }

    public void shareButton(View view) {
        new AlertDialog.Builder(this).setMessage("Press back button after taking photo to choose your image").setTitle("Tips").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        }).setNegativeButton("Close", null).show();

    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_PHOTO);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            } else {
                try {

                    InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    Log.i("inputStream", "Success");

                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(image)
                            .build();
                    ShareContent content = new ShareMediaContent.Builder()
                            .addMedium(photo)
                            .build();

                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        shareDialog.show(content);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == TAKE_PHOTO) {
            pickImage();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.animation_left_slide_right, R.anim.animation_right_slide_right);
    }

    public void startMapActivity(View view) {
        Intent i = new Intent(this, PlaceActivity.class);
        startActivity(i);
    }
}
