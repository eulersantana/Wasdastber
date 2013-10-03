/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.roomsmanager.bean;

import br.ufba.roomsmanager.dao.Hibernate;
import br.ufba.roomsmanager.model.Grupo;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author eulersantana
 */
@ManagedBean
public class GrupoBean implements Serializable{
    private static final long serialVersionUID = -6735027036534961738L;
    private ArrayList<Grupo> listaGrupo = new ArrayList<Grupo>();
    private DataModel<Grupo> gru;
    private Grupo grupo = new Grupo();
    private Grupo grupoRow;

   

    @PostConstruct
    public void GrupoBean() {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        listaGrupo = (ArrayList<Grupo>) session.createQuery("FROM Grupo").list();
        gru = new ListDataModel(listaGrupo);
        session.close();
    }

    public void create(ActionEvent ae) throws ParseException {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        String nomeGrupo ="";
        try {
            nomeGrupo = grupo.getNome();
            if(verificaSala(nomeGrupo)){
                tx = session.beginTransaction();                
                session.saveOrUpdate(grupo);
                tx.commit();
                grupo = new Grupo();
                FacesMessage msg = new FacesMessage("Grupo adicionada com sucesso!");  
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }else{
                grupo = new Grupo();
                FacesMessage msg = new FacesMessage("O grupo "+ nomeGrupo+" já existe.");  
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
       
    }

    public String update(RowEditEvent event) throws ParseException {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;

        grupo = (Grupo) event.getObject();

        try {
            tx = session.beginTransaction();
            session.update(grupo);
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return "list";
    }

    public void delete(Grupo grupo) {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        String nomeGrupo = "";
        int escolha = 0;
        

        try {
            nomeGrupo = grupo.getNome();
           escolha = JOptionPane.showConfirmDialog(null, "Será deletado "+nomeGrupo, "Deletando Grupo", 1);
            if(escolha == JOptionPane.OK_OPTION){
            
                tx = session.beginTransaction();
                session.delete(grupo);
                tx.commit();
                listaGrupo = (ArrayList<Grupo>) session.createQuery("FROM Grupo").list();
                gru = new ListDataModel(listaGrupo);
            }else{
                nomeGrupo = "";
                grupo = new Grupo();
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possivel deletar " + nomeGrupo + ".\nExiste alguma dependência.");
        } finally {
            session.close();
        }
    }

    
//    public void select() {
//        this.sala = this.sal.getRowData();
////        JOptionPane.showMessageDialog(null, sala.getNome());
//    }

    private String getRandomModel() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void onEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Grupo editado", ((Grupo) event.getObject()).getNome());
        Grupo grupo = (Grupo) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edição Cancelada", ((Grupo) event.getObject()).getNome());
        JOptionPane.showMessageDialog(null, "Edição cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public ArrayList<Grupo> getListaGrupo() {
        return listaGrupo;
    }

    public DataModel<Grupo> getGru() {
        return gru;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Grupo getGrupoRow() {
        return grupoRow;
    }

    

    public boolean verificaSala(String nomeGrupo) throws ParseException {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        ArrayList<Grupo> listaGrupo = new ArrayList<Grupo>();

        try {
            tx = session.beginTransaction();
            listaGrupo = (ArrayList<Grupo>) session.createQuery("FROM Grupo where nome = '" + nomeGrupo + "'").list();
            for (Grupo aux : listaGrupo) {
                if (nomeGrupo.equals(aux.getNome())) {
                    return false;
                }
            }
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return true;
    }
    
}
