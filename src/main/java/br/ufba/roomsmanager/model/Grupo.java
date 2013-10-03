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
    private String professorResponvel;

    public Grupo(){
        
    }
    
    public Grupo(int id, String nome, String professorResponvel) {
        this.id = id;
        this.nome = nome;
        this.professorResponvel = professorResponvel;
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

    public String getProfessorResponvel() {
        return professorResponvel;
    }

    public void setProfessorResponvel(String professorResponvel) {
        this.professorResponvel = professorResponvel;
    }
    
        
    
    
    
}
