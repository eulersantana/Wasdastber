package br.ufba.roomsmanager.bean;

import br.ufba.roomsmanager.dao.Hibernate;
import br.ufba.roomsmanager.model.Sala;
import br.ufba.roomsmanager.model.Setor;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.swing.JOptionPane;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.primefaces.event.RowEditEvent;


@ManagedBean
public class SalaBean implements Serializable {
    private static final long serialVersionUID = -6735027036534961738L;
    private static ArrayList<Sala> listaSalas = new ArrayList<Sala>();
    private ArrayList<Setor> listaSetor = new ArrayList<Setor>();
    private DataModel<Sala> sal;
    private Sala sala = new Sala();
    private String setor_id;
    private Sala salaRow;

    public String getSetor_id() {
        return setor_id;
    }

    public void setSetor_id(String setor_id) {
        this.setor_id = setor_id;
    }

    @PostConstruct
    public void SalaBean(){
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        listaSalas = (ArrayList<Sala>) session.createQuery("FROM Sala").list();
        sal = new ListDataModel(listaSalas);
        listaSetor = (ArrayList<Setor>) session.createQuery("FROM Setor").list();
        session.close();
    }

    public void create(ActionEvent ae) throws ParseException
    {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        String nomeSala ="";
        try 
        {
            nomeSala = sala.getNome();
            if(verificaSala(nomeSala))
            {
                tx = session.beginTransaction();
                sala.setSetor_id(Integer.valueOf(setor_id));
                session.saveOrUpdate(sala);
                tx.commit();
                sala = new Sala();
                FacesMessage msg = new FacesMessage("Sala adicionada com sucesso!");  
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }else{
                sala = new Sala();
                FacesMessage msg = new FacesMessage("A sala "+ nomeSala+" já existe.");  
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } 
        catch (HibernateException e)
        {
            if (tx != null)
            {
                tx.rollback();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }     
        
    }

    public String update(RowEditEvent event) throws ParseException
    {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;

        sala = (Sala) event.getObject();

        sala.setSetor_id(Integer.valueOf(setor_id));

        try {
            tx = session.beginTransaction();
            session.update(sala);
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

    public void delete(Sala sala) {
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        String nomeSala = "";
        int escolha = 0;
        

        try {
            nomeSala = sala.getNome();
           escolha = JOptionPane.showConfirmDialog(null, "Sera deletado "+nomeSala, "Deletando sala", 1);
            if(escolha == JOptionPane.OK_OPTION){
            
                tx = session.beginTransaction();
                session.delete(sala);
                tx.commit();
                listaSalas = (ArrayList<Sala>) session.createQuery("FROM Sala").list();
                sal = new ListDataModel(listaSalas);
            }else{
                nomeSala = "";
                sala = new Sala();
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possivel deletar " + sala.getNome() + ".\nExiste alguma dependência.");
        } finally {
            session.close();
        }
    }

    private String getRandomModel() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void onEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Sala editada", ((Sala) event.getObject()).getNome());
        Sala sala = (Sala) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Edição Cancelada", ((Sala) event.getObject()).getNome());
//        JOptionPane.showMessageDialog(null, "Edição cancelada");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Sala getSala(){
        return sala;
    }

    public ArrayList<Sala> getListaSalas(){
        for (Sala auxSala : listaSalas)
        {
            for (Setor auxSetor : listaSetor)
            {
                if (auxSala.getSetor_id() == auxSetor.getId())
                {
                    auxSala.setSetorNome(auxSetor.getNome());
                }
            }
        }
        return listaSalas;
    }

    public DataModel<Sala> getSal(){
        return sal;
    }
    
    public ArrayList<Setor> getListaSetor(){
        return listaSetor;
    }

    public boolean verificaSala(String nomeSala) throws ParseException{
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        ArrayList<Sala> listaSala = new ArrayList<Sala>();

        try {
            tx = session.beginTransaction();
            listaSala = (ArrayList<Sala>) session.createQuery("FROM Sala where nome = '" + nomeSala + "'").list();
            for (Sala aux : listaSala) {
                if (nomeSala.equals(aux.getNome())) {
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
