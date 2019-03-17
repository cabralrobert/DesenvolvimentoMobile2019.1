package com.example.myapplication;

public class Curso {

    private String cursoNome;
    private String imgName;
    private String descricao;

    public Curso(String cursoNome, String imgName, String descricao) {
        this.cursoNome = cursoNome;
        this.imgName = imgName;
        this.descricao = descricao;
    }

    public String getCursoNome() {
        return cursoNome;
    }

    public void setCursoNome(String cursoNome) {
        this.cursoNome = cursoNome;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return cursoNome + ": " + this.descricao;
    }

}
