package com.example.todoapp;

public class Task {
    private boolean completed;
    private String dateCreated;
    private String descricao;
    private String titulo;

    public Task(String titulo, String descricao, boolean completed, String dateCreated) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.completed = completed;
        this.dateCreated = dateCreated;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTitulo() {
        return titulo;
    }
}
