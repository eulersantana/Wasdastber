package br.ufba.roomsmanager.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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

import br.ufba.roomsmanager.dao.Hibernate;
import br.ufba.roomsmanager.model.Tipo;
import java.util.ArrayList;

@ManagedBean
@ViewScoped
public class TipoBean implements Serializable {

    private Tipo tipo = new Tipo();
    private DataModel<Tipo> tipos;

    @PostConstruct
    void init() {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        List<Tipo> t = (List<Tipo>) session.createQuery("FROM Tipo").list();
        tipos = new ListDataModel(t);
        session.close();
    }

    public String create(ActionEvent ae) throws ParseException {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        String descricao = tipo.getDescricao();
        
        System.out.println("Descrição :"+descricao);
        try {
            if (verificaTipo(descricao)) {
                tx = session.beginTransaction();
                session.save(tipo);
                tx.commit();
                tipo = new Tipo();
                FacesMessage msg = new FacesMessage("Tipo adicionada com sucesso!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                tipo = new Tipo();
                FacesMessage msg = new FacesMessage("Tipo já existe!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return "list?faces-redirect=true";
    }

    public String update(RowEditEvent event) throws ParseException {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;

        tipo = (Tipo) event.getObject();

        try {
            tx = session.beginTransaction();
            session.update(tipo);
            session.flush();
            tx.commit();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo atualizado com sucesso!", "Sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, message);
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

    public void delete() {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;

        select();

        try {
            tx = session.beginTransaction();
            session.delete(tipo);
            tx.commit();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo removido com sucesso!", "Sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, message);
            List<Tipo> t = (List<Tipo>) session.createQuery("FROM Tipo").list();
            tipos = new ListDataModel(t);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possivel deletar " + tipo.getDescricao() + ".");
        } finally {
            session.close();
        }
    }

    public void select() {
        this.tipo = this.tipos.getRowData();
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edição cancelada!", "Falha!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public DataModel<Tipo> getTipos() {
        return tipos;
    }

    public boolean verificaTipo(String nomeTipo) throws ParseException {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        ArrayList<Tipo> listaTipo = new ArrayList<Tipo>();

        try {
            tx = session.beginTransaction();
            listaTipo = (ArrayList<Tipo>) session.createQuery("FROM Tipo where descricao = '" + nomeTipo + "'").list();
            for (Tipo aux : listaTipo) {
                if (nomeTipo.equals(aux.getDescricao())) {
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