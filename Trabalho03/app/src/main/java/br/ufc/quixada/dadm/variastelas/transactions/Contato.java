package br.ufc.quixada.dadm.variastelas.transactions;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "contatos")
public class Contato {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "telefone")
    private String telefone;

    @ColumnInfo(name = "endereco")
    private String endereco;

    public Contato(){

    }

    public Contato(String nome, String telefone, String endereco) {

        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return nome;
    }
}
