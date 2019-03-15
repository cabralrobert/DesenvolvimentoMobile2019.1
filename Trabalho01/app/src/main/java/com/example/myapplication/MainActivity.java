package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    EditText edNome;
    Button botao, buttonPopup, buttonLongPress;
    AutoCompleteTextView autoCompleteTextView;
    Spinner spinner;
    RadioGroup radioGroup;
    ToggleButton toggle;
    ConstraintLayout cLayout;
    String complete[] = {"UFC", "Universidade Federal do Ceará", "EC", "Engenharia de Computação", "SI", "Sistemas de informação", "CC", "Ciência da computação"};
    private int RESULT_LOAD_IMAGE = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> arrayAdapterAutoComplete = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, complete);
        ArrayAdapter<String> arrayAdapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, complete);

        botao = findViewById(R.id.button3);
        buttonPopup = findViewById(R.id.buttonPopup);
        buttonLongPress = findViewById(R.id.buttonLongPress);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        edNome = findViewById(R.id.editText4);
        toggle = findViewById(R.id.toggleButton);
        spinner = findViewById(R.id.spinner);
        radioGroup = findViewById(R.id.radioGroup);
        cLayout = findViewById(R.id.layoutMain);
        radioGroup.clearCheck();

        edNome.addTextChangedListener(nomeTextWatcher);

        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(arrayAdapterAutoComplete);
        spinner.setAdapter(arrayAdapterSpinner);
        spinner.setOnItemSelectedListener(spinnerOptions);

        radioGroup.setOnCheckedChangeListener(onCheckedChangeListenerRadioGroup);

        toggle.setOnCheckedChangeListener(onCheckedChangeListenerToggleButton);

        buttonLongPress.setOnLongClickListener(longClickRotine);

    }

    View.OnLongClickListener longClickRotine = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(getApplicationContext(), "Precionou e segurou", Toast.LENGTH_SHORT).show();
            return false;
        }
    };

    public void onClickButtonLongPress(View view){
        Toast.makeText(getApplicationContext(), "Precionou simples", Toast.LENGTH_SHORT).show();
    }

    private  CompoundButton.OnCheckedChangeListener onCheckedChangeListenerToggleButton = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                Toast.makeText(getApplicationContext(), "Ativado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Desativado", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListenerRadioGroup = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton radioButton = group.findViewById(checkedId);
            if(radioButton != null)
                Toast.makeText(getApplicationContext(), "Curso selecionado: " + radioButton.getText(), Toast.LENGTH_LONG).show();
        }
    };

    private AdapterView.OnItemSelectedListener spinnerOptions = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), "Você selecionou: " + complete[position], Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };

    private TextWatcher nomeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nome = edNome.getText().toString().trim();
            botao.setEnabled(!nome.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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

    public void onClickBotao(View v) {
        Intent i = new Intent(this, TabsActivity.class);
        i.putExtra("nome", edNome.getText().toString());
        startActivity(i);
    }

    public void onClickButtonPopup(View view){
        PopupMenu menu = new PopupMenu(MainActivity.this, buttonPopup);
        int i = 0;
        while (i < (complete.length -1)) {
            menu.getMenu().add(complete[i]);
            i++;
        }

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), "Selecionado: " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        menu.show();

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

    public void onClickNewButton(View view){

    }

    void showToast(String txt){
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
    }
}
