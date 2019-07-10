package br.ufc.quixada.dadm.variastelas.transactions;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = Contato.class, version = 1)
public abstract class MyAppDatabase extends RoomDatabase {

    public abstract MyDao myDao();

}
