package br.com.ufba.roomsmanager.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import javax.faces.bean.*;
import javax.faces.event.*;
import javax.swing.JOptionPane;

import br.com.ufba.roomsmanager.dao.*;
import br.com.ufba.roomsmanager.model.*;
import org.apache.jasper.tagplugins.jstl.ForEach;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@ManagedBean
public class ReservaSalaBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ReservaSala reserva = new ReservaSala();
    private List<ReservaSala> reservas;
    private List<Sala> salas;
    private String sala_id;

    public String getSala_id() {
            return sala_id;
    }

    public void setSala_id(String sala_id) {
            this.sala_id = sala_id;
    }

    public List<ReservaSala> getReservas(){

        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        
        try{
            this.reservas = (List<ReservaSala>) session.createQuery("FROM ReservaSala").list();
        }catch(HibernateException e){
            JOptionPane.showMessageDialog(null,"GETRESERVAS ERRO: "+e.getMessage());
        }
        
        return this.reservas;
    }

    public List<Sala> getSalas(){
        SalaDAO dao = new SalaDAO();
        try {
            salas = dao.getSalas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"GETRESERVAS GETSALAS ERRO: "+e.getMessage());
            e.printStackTrace();
        }
        return salas;
    }

    public ReservaSala getReserva(){
        return reserva;
    }

}
