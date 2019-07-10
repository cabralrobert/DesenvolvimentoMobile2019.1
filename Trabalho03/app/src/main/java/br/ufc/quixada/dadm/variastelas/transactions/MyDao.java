package br.ufc.quixada.dadm.variastelas.transactions;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void addContato(Contato contato);

    @Query("select * from contatos")
    public List<Contato> getContatos();

    @Delete
    public void deleteContato(Contato contato);

    @Update
    public void updateContato(Contato contato);

}
