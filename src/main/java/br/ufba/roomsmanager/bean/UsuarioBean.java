package br.ufba.roomsmanager.bean;

import br.ufba.roomsmanager.model.Tipo;
import br.ufba.roomsmanager.model.Usuario;
import java.io.Serializable;
import java.text.ParseException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.primefaces.event.RowEditEvent;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import br.ufba.roomsmanager.dao.Hibernate;
import br.ufba.roomsmanager.model.Login;
import br.ufba.roomsmanager.model.Sala;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Example;

@ManagedBean
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static String senhaValor = "********";
    private Usuario usuario = new Usuario();
    private DataModel<Usuario> usuarios;
    private ArrayList<Tipo> tipos = new ArrayList<Tipo>();
    private String tipo_id;

    public Usuario getUsuario() {
        return usuario;
    }

    public  String getSenhaValor() {
        return senhaValor;
    }

    public void setSenhaValor(String senhaValor) {
        UsuarioBean.senhaValor = senhaValor;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public DataModel<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setTipos(List tipos) {
        this.tipos = (ArrayList<Tipo>) tipos;
    }

    public ArrayList<Tipo> getTipos() {
        return tipos;
    }

    public void setTipo_id(String tipo_id) {
        this.tipo_id = tipo_id;
    }

    public String getTipo_id() {
        return tipo_id;
    }

    @PostConstruct
    public void UsuarioBean() {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        List<Usuario> l = (List<Usuario>) session.createQuery("FROM Usuario").list();
        usuarios = new ListDataModel(l);
        tipos = (ArrayList<Tipo>) session.createQuery("FROM Tipo").list();
        session.close();
    }

    public void create(ActionEvent ae) throws ParseException, NoSuchAlgorithmException {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        MessageDigest md = null;
        Transaction tx = null;
        String email = "";
        

        email = usuario.getEmail();
        System.out.println("E-mail : " + email);
        try {
           
            usuario.setSenha(md5(usuario.getSenha()));
            tx = session.beginTransaction();
            usuario.setTipo_id(Integer.valueOf(tipo_id));
            session.saveOrUpdate(usuario);
            tx.commit();
            usuario = new Usuario();
            FacesMessage msg = new FacesMessage("Usuario adicionado com sucesso.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            usuario = new Usuario();
            FacesMessage msg = new FacesMessage("E-mail ou Matricula já estão em uso! ");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } finally {
            session.close();
        }

        //return "list?faces-redirect=true";
    }

    public String update(RowEditEvent event) throws ParseException {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;

        select();

        try {
            tx = session.beginTransaction();
            usuario.setSenha(md5(senhaValor));
            usuario.setTipo_id(Integer.valueOf(tipo_id));
            session.update(usuario);
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

    public void delete() {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;

        select();

        try {
            tx = session.beginTransaction();
            session.delete(usuario);
            tx.commit();
            List<Usuario> l = (List<Usuario>) session.createQuery("FROM Usuario").list();
            usuarios = new ListDataModel(l);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public ArrayList buscaUsuario(Login login) {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        ArrayList<Login> listaUser = new ArrayList<Login>();

        try {
            login.setSenha(md5(login.getSenha()));
            tx = session.beginTransaction();
            listaUser = (ArrayList<Login>) session.createQuery("FROM Usuario where email = '" + login.getUsuario() + "' and senha = '" + login.getSenha() + "'").list();
            session.flush();
            tx.commit();            
            return listaUser;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public void select() {
        this.usuario = this.usuarios.getRowData();
    }

    public static String md5(String senha) {
        String sen = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
        sen = hash.toString(16);
        return sen;
    }
}