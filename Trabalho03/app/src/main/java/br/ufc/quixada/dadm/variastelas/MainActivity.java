package br.ufc.quixada.dadm.variastelas;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.ufc.quixada.dadm.variastelas.transactions.Agenda;
import br.ufc.quixada.dadm.variastelas.transactions.Constants;
import br.ufc.quixada.dadm.variastelas.transactions.Contato;
import br.ufc.quixada.dadm.variastelas.transactions.MyAppDatabase;

public class MainActivity extends AppCompatActivity {

    int selected;
    List<Contato> listaContatos;
    ArrayAdapter adapter;
    ListView listViewContatos;
    MyAppDatabase myAppDatabase;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "agendadb").allowMainThreadQueries().build();

        selected = -1;

        listaContatos = myAppDatabase.myDao().getContatos();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaContatos );

        listViewContatos = ( ListView )findViewById( R.id.listViewContatos );
        listViewContatos.setAdapter( adapter );
        listViewContatos.setSelector( android.R.color.holo_blue_light );

        listViewContatos.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Toast.makeText(MainActivity.this, "" + listaContatos.get( position ).toString(), Toast.LENGTH_SHORT).show();
                selected = position;
            }
        } );

        progressBar = findViewById( R.id.progressBar );
        progressBar.setIndeterminate( true );
        progressBar.setVisibility( View.VISIBLE );

    }

    public void updateListaContatos( Agenda agenda ){
        progressBar.setVisibility( View.INVISIBLE );

        Contato[] lista = agenda.getListaTelefone();
        for( Contato c: lista ) listaContatos.add( c );

        adapter.notifyDataSetChanged();;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_main_activity, menu );
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected( MenuItem item ) {

        switch(item.getItemId())
        {
            case R.id.add:
                clicarAdicionar();
                break;
            case R.id.edit:
                clicarEditar();
                break;
            case R.id.delete:
                apagarItemLista();
                break;
            case R.id.settings:
                break;
            case R.id.about:
                break;
        }
        return true;
    }

    private void apagarItemLista(){

        if( listaContatos.size() > 0 ){
            myAppDatabase.myDao().deleteContato(listaContatos.get(selected));
            listaContatos.remove( selected );
            adapter.notifyDataSetChanged();
        } else {
            selected = -1;
        }

    }

    public void clicarAdicionar(){
        Intent intent = new Intent( this, ContactActivity.class );
        startActivityForResult( intent, Constants.REQUEST_ADD );
    }

    public void clicarEditar(){

        Intent intent = new Intent( this, ContactActivity.class );
        //Intent intent2 = new Intent( this, "br.ufc.quixada.dadm.variastelas.ContactActivity" );

        Contato contato = listaContatos.get( selected );

        intent.putExtra( "id", contato.getId() );
        intent.putExtra( "nome", contato.getNome() );
        intent.putExtra( "telefone", contato.getTelefone() );
        intent.putExtra( "endereco", contato.getEndereco() );

        startActivityForResult( intent, Constants.REQUEST_EDIT );
    }


    @Override
    protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);

      if( requestCode == Constants.REQUEST_ADD && resultCode == Constants.RESULT_ADD ){

          String nome = ( String )data.getExtras().get( "nome" );
          String telefone = ( String )data.getExtras().get( "telefone" );
          String endereco = ( String )data.getExtras().get( "endereco" );

          Contato contato = new Contato( nome, telefone, endereco );

          listaContatos.add( contato );

          myAppDatabase.myDao().addContato(contato);
          Log.i("Insert: ", "Contato adicionado com sucesso!!");

          adapter.notifyDataSetChanged();

      } else if( requestCode == Constants.REQUEST_EDIT && resultCode == Constants.RESULT_ADD ){

          String nome = ( String )data.getExtras().get( "nome" );
          String telefone = ( String )data.getExtras().get( "telefone" );
          String endereco = ( String )data.getExtras().get( "endereco" );
          int idEditar = (int)data.getExtras().get( "id" );

          for( Contato contato: listaContatos ){

              if( contato.getId() == idEditar ){
                  contato.setNome( nome );
                  contato.setEndereco( endereco );
                  contato.setTelefone( telefone );

                  myAppDatabase.myDao().updateContato(contato);
              }
          }

          adapter.notifyDataSetChanged();

      } //Retorno da tela de contatos com um conteudo para ser adicionado
        //Na segunda tela, o usuario clicou no botão ADD
      else if( resultCode == Constants.RESULT_CANCEL ){
            Toast.makeText( this,"Cancelado",
                    Toast.LENGTH_SHORT).show();
        }

    }








}
