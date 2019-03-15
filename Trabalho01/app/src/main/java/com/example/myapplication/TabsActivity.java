package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class TabsActivity extends AppCompatActivity {

    private int RESULT_LOAD_IMAGE = 2000;
    ConstraintLayout cLayout;
    String value;
    TextView tvActivityTabs;
    ListView listView;
    ArrayList<String> cursos;
    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        tvActivityTabs = findViewById(R.id.tvActivityTabs);
        listView = findViewById(R.id.listView);
        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Primeira");
        spec.setContent(R.id.primeira);
        spec.setIndicator("Primeira");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Segunda");
        spec.setContent(R.id.segunda);
        spec.setIndicator("Segunda");
        tabHost.addTab(spec);

        cursos = new ArrayList<>();

        cursos.add("Engenharia de Computação");
        cursos.add("Sistemas de informação");
        cursos.add("Ciência da computação");
        cursos.add("Engenharia de Software");
        cursos.add("Redes de Computadores");
        cursos.add("Design Digital");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,cursos);
        listView.setAdapter(arrayAdapter);

        value = getIntent().getExtras().getString("nome");
        tvActivityTabs.setText(value);

        cLayout = findViewById(R.id.cLayoutTabs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.red:
                cLayout.setBackgroundColor(Color.RED);
                break;
            case R.id.blue:
                cLayout.setBackgroundColor(Color.BLUE);
                break;
            case R.id.green:
                cLayout.setBackgroundColor(Color.GREEN);
                break;
            case R.id.image_menu:
                Intent getPhotoIntent = new Intent(Intent.ACTION_PICK);
                getPhotoIntent.setType("image/*");
                startActivityForResult(getPhotoIntent, RESULT_LOAD_IMAGE);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                cLayout.setBackground(new BitmapDrawable(getResources(), selectedImage));
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
        }
    }
}
