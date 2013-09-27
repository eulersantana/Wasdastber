package br.ufba.roomsmanager.bean;

import br.ufba.roomsmanager.dao.Hibernate;
import br.ufba.roomsmanager.model.Setor;
import br.ufba.roomsmanager.model.Sala;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;

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



@ManagedBean
@ViewScoped
public class SetorBean implements Serializable {
	
	private Setor setor = new Setor();
	private static ArrayList<Setor> setores = new ArrayList<Setor>();
	private DataModel<Setor> sal;
	private String setor_id;
	
	@PostConstruct
	void iniciSetor(){
		SessionFactory sf = Hibernate.getSessionFactory();
	    Session session = sf.openSession();
	    setores = (ArrayList<Setor>) session.createQuery("FROM Setor").list();
	    sal = new ListDataModel(setores);	 
	    session.close();
	}	
	
	public void create(ActionEvent ae) throws ParseException
	{
	    SessionFactory sf = Hibernate.getSessionFactory();
	    Session session = sf.openSession();
	    Transaction tx = null;
	    String nomeSetor = "";
	    
            nomeSetor = setor.getNome();
	    try{
                if(verificaSetor(nomeSetor)){
                    tx = session.beginTransaction();
                    session.saveOrUpdate(setor); 
                    tx.commit();
                    setor = new Setor();
                    FacesMessage msg = new FacesMessage("Setor adiconado com sucesso.");  
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }else{
                    setor = new Setor();
                    FacesMessage msg = new FacesMessage("O setor "+ nomeSetor+" já existe.");  
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
    	}catch (HibernateException e) {
    		if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
    	}finally {
	    	session.close();
	    }
	    
    }
	
	public String update(RowEditEvent event) throws ParseException
	{
		SessionFactory sf = Hibernate.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;
		
		setor = (Setor) event.getObject();		
		
		try{
			tx = session.beginTransaction();
			session.update(setor);setores = (ArrayList<Setor>) session.createQuery("FROM Setor").list();
		        sal = new ListDataModel(setores);
			session.flush();
			tx.commit(); 
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
				e.printStackTrace(); 
		}finally {
		session.close(); 
		}
		
		return "list"; 
	}
	
	
	public void delete()
    {
		SessionFactory sf = Hibernate.getSessionFactory();
	    Session session = sf.openSession();
	    Transaction tx = null;
	
	    select();	    
	    
	    try{
	    	tx = session.beginTransaction();
	    	session.delete(setor); 
	    	tx.commit();
	    	session.close();
	    	
    	}catch (HibernateException e) {
    		if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    	JOptionPane.showMessageDialog(null, "Não foi possivel deletar\n"+e);
    	}finally {
    		session = sf.openSession();
    		setores = (ArrayList<Setor>) session.createQuery("FROM Setor").list();
		    sal = new ListDataModel(setores);
	    	session.close();
	    }
    }
	
	public void select()
	{
        this.setor = this.sal.getRowData();
    }
	
	 private String getRandomModel() {  
	        return UUID.randomUUID().toString().substring(0, 8);  
	 }
	
	 
	 public void onEdit(RowEditEvent event) {  
	        FacesMessage msg = new FacesMessage("Setor editada", ((Setor) event.getObject()).getNome());  
	        Sala sala = (Sala) event.getObject();
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	}  
	      
	 public void onCancel(RowEditEvent event) {  
	     FacesMessage msg = new FacesMessage("Edição Cancelada", ((Setor) event.getObject()).getNome());  
	     JOptionPane.showMessageDialog(null, "Edição cancelada");
	     FacesContext.getCurrentInstance().addMessage(null, msg);  
	 } 
	public Setor getSetor(){
		return setor;
	}
	
	public ArrayList<Setor> getSetores(){
		return setores;
	}

	public String getSetor_id() {
		return setor_id;
	}

	public void setSetor_id(String setor_id) {
		this.setor_id = setor_id;
	}
	
        public boolean verificaSetor(String nomeSetor) throws ParseException
	{
		SessionFactory sf = Hibernate.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;
                ArrayList<Setor> listaSetor = new ArrayList<Setor>();
		
				
		
		try{
			tx = session.beginTransaction();
			listaSetor = (ArrayList<Setor>) session.createQuery("FROM Setor where nome = '"+nomeSetor+"'").list();
                        for(Setor aux : listaSetor){
                            if(nomeSetor.equals(aux.getNome())){
                                return false;
                            }
                        }
		    sal = new ListDataModel(setores);
			session.flush();
			tx.commit(); 
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
				e.printStackTrace(); 
		}finally {
		session.close(); 
		}
		
		return true; 
	}
    

}
