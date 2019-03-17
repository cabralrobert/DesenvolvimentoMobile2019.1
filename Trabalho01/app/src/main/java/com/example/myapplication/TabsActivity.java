package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TabsActivity extends AppCompatActivity {

    private int RESULT_LOAD_IMAGE = 2000;
    ConstraintLayout cLayout;
    String value;
    TextView tvActivityTabs;
    ListView listView;
    ArrayList<String> cursos;
    TabHost tabHost;
    MediaPlayer player;
    List<Curso> imgDetails = getListData();
    private static final int ENGENHARIA_COMPUTACAO = 0;
    private static final int SISTEMAS_INFORMACOES = 1;
    private static final int CIENCIA_COMPUTACAO = 2;
    private static final int ENGENHARIA_SOFTWARE = 3;
    private static final int REDES_COMPUTADORES = 4;
    private static final int DESIGN_DIGITAL = 5;
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

        listView.setAdapter(new ListAdapter(this, imgDetails));
        listView.setOnItemClickListener(listener);


        value = getIntent().getExtras().getString("nome");
        tvActivityTabs.setText(value);

        cLayout = findViewById(R.id.cLayoutTabs);

        player = MediaPlayer.create(this, R.raw.toque);
    }

    private List<Curso> getListData(){
        List<Curso> list = new ArrayList<Curso>();

        Curso ec = new Curso("Engenharia de Computação", "logo_ec", "Curso de 10 semestres com o objetivo de fornecer as bases " +
                "teórica e prática necessárias para capacitar o aluno ao desenvolvimento de sistemas embarcados, bem como à integração entre software e hardware.");

        Curso si = new Curso("Sistemas de Informações", "logo_si", "Curso de 8 semestres com o objetivo de Formar profissionais" +
                " em Computação e Informática para aplicar tecnologias de informação e comunicação nas organizações visando a tomada de decisão empresarial. " +
                "Considera uso, desenvolvimento e gestão da tecnologia.");

        Curso dd = new Curso("Design Digital", "logo_dd", "Curso de 8 semestres com o objetivo de formar profissionais aptos a criar, organizar, planejar e " +
                "produzir (inclusive programando) interfaces de interação com usuário para produtos de software.");

        Curso cc = new Curso("Ciência da Computação", "logo_cc", "Curso de 8 semestres com o objetivo de fornecer sólida formação nos fundamentos de computação, " +
                "e uma visão abrangente das principais áreas tecnológicas. Desta forma o egresso é capaz de buscar soluções para os " +
                "desafios tecnológicos e de se adaptar rapidamente às frequentes mudanças do mercado de TI.");

        Curso es = new Curso("Engenharia de Software", "logo_es", "Curso de 8 semestres com o objetivo de formar profissionais aptos a introduzirem melhorias e a " +
                "participarem efetivamente de empreendimentos de software voltado para os mercados local e global, oferecendo a base " +
                "teórica e prática suficiente para que os egressos possam manter-se constantemente atualizados.");

        Curso rc = new Curso("Redes de Computadores", "logo_rc", "Curso de 6 semestres com o objetivo de formar profissionais aptos a projetar, desenvolver, implantar" +
                " e manter soluções na área de Redes de Computadores que atendam às necessidade atuais do mercado.");

        list.add(ec);
        list.add(si);
        list.add(cc);
        list.add(es);
        list.add(rc);
        list.add(dd);

        return list;

    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        List<Curso> cursosDetails = getListData();

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case ENGENHARIA_COMPUTACAO:
                    Curso cursoEC = cursosDetails.get(ENGENHARIA_COMPUTACAO);
                    showAlertDialog(cursoEC.getCursoNome(), cursoEC.getDescricao(), getMipmapResIdByName(cursoEC.getImgName()));

                    break;
                case SISTEMAS_INFORMACOES:
                    Curso cursoSI = cursosDetails.get(SISTEMAS_INFORMACOES);
                    showAlertDialog(cursoSI.getCursoNome(), cursoSI.getDescricao(), getMipmapResIdByName(cursoSI.getImgName()));
                    break;
                case CIENCIA_COMPUTACAO:
                    Curso cursoCC = cursosDetails.get(CIENCIA_COMPUTACAO);
                    showAlertDialog(cursoCC.getCursoNome(), cursoCC.getDescricao(), getMipmapResIdByName(cursoCC.getImgName()));
                    break;
                case ENGENHARIA_SOFTWARE:
                    Curso cursoES = cursosDetails.get(ENGENHARIA_SOFTWARE);
                    showAlertDialog(cursoES.getCursoNome(), cursoES.getDescricao(), getMipmapResIdByName(cursoES.getImgName()));
                    break;
                case REDES_COMPUTADORES:
                    Curso cursoRC = cursosDetails.get(REDES_COMPUTADORES);
                    showAlertDialog(cursoRC.getCursoNome(), cursoRC.getDescricao(), getMipmapResIdByName(cursoRC.getImgName()));
                    break;
                case DESIGN_DIGITAL:
                    Curso cursoDD = cursosDetails.get(DESIGN_DIGITAL);
                    showAlertDialog(cursoDD.getCursoNome(), cursoDD.getDescricao(), getMipmapResIdByName(cursoDD.getImgName()));
                    break;
                default:
                    break;

            }
        }
    };

    public void showAlertDialog(String titulo, String descricao, int icon){
        AlertDialog alertDialog = new AlertDialog.Builder(TabsActivity.this).create();
        alertDialog.setTitle(titulo);
        alertDialog.setIcon(icon);
        alertDialog.setMessage(descricao);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public int getMipmapResIdByName(String resName)  {
        String pkgName = getApplicationContext().getPackageName();
        // Return 0 if not found.
        int resID = getApplicationContext().getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.release();
    }

    // som
    public void onClickButtonSong(View view){
        player.start();
    }

    // menu
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
