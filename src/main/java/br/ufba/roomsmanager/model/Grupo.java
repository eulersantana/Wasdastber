/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.roomsmanager.model;

import java.io.Serializable;

/**
 *
 * @author eulersantana
 */
public class Grupo implements Serializable{
    private int id;
    private String nome;
    private String professorResponsavel;

    public Grupo(){
        
    }
    
    public Grupo(int id, String nome, String professorResponsavel) {
        this.id = id;
        this.nome = nome;
        this.professorResponsavel = professorResponsavel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfessorResponsavel() {
        return professorResponsavel;
    }

    public void setProfessorResponsavel(String professorResponsavel) {
        this.professorResponsavel = professorResponsavel;
    }
    
}
