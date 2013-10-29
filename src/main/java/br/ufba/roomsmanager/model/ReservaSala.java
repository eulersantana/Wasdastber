package br.ufba.roomsmanager.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;


public class ReservaSala implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private Sala sala;
	private Date dataInicio;
	private Date dataFim;
	private Date horarioInicio;
	private Date horarioTermino;
	private String responsavel;
	private String reservadoPara;
	private boolean eventoPrivado = false;
	private String email;
	private String telefone;
	private String observacao;
	private boolean aceito = false;
	private int status = 1;	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ReservaSala(){
		this.sala = new Sala();
	}

        public ReservaSala(int id, Sala sala, Date dataInicio, Date dataFim,
			Date horarioInicio, Date horarioTermino, String responsavel,
			String reservadoPara, boolean eventoPrivado, String email,
			String telefone, String observacao, int status) {
		super();
		this.id = id;
		this.sala = sala;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.horarioInicio = horarioInicio;
		this.horarioTermino = horarioTermino;
		this.responsavel = responsavel;
		this.reservadoPara = reservadoPara;
		this.eventoPrivado = eventoPrivado;
		this.email = email;
		this.telefone = telefone;
		this.observacao = observacao;
		this.status = status;
	}

	public Sala getSala(){
            return this.sala;
	}
	
	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(Date horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public void setHorarioTermino(Date horarioTermino) {
		this.horarioTermino = horarioTermino;
	}

	public Date getHorarioTermino() {
		return horarioTermino;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getReservadoPara() {
		return reservadoPara;
	}

	public void setReservadoPara(String reservadoPara) {
		this.reservadoPara = reservadoPara;
	}

	public boolean isEventoPrivado() {
		return eventoPrivado;
	}

	public void setEventoPrivado(boolean eventoPrivado) {
		this.eventoPrivado = eventoPrivado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public boolean isAceito() {
		return aceito;
	}

	public void setAceito(boolean aceito) {
		this.aceito = aceito;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 
	 * @param data
	 * @return a data no formato dd/mm/yyyy
	 * 
	 */
	private Date dataPtBR(String data){
		String dt = "";
		
		if(data != null && !data.isEmpty()){
			dt = data;
			String [] str = dt.split("-");
			
			if(str.length > 1){
				dt = str[2]+"/"+str[1]+"/"+str[0];
			}
		}
		
		return stringToDate(dt,"ptBR");
	}
	
	/**
	 * 
	 * @param data
	 * @return data no formato yyyy-mm-dd
	 * 
	 */
	private Date dataEn(String data){
		String dt = "";
		
		if(data != null && !data.isEmpty()){
			dt = data;
			String [] str = dt.split("/");
			
			if(str.length > 1){
				dt = str[2]+"-"+str[1]+"-"+str[0];
			}
		}
		
		return stringToDate(dt,"en");
	}

	private Date stringToDate(String text, String local){
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    Date data = null;
	    
	    if(local.equals("ptBR")){
	        df = new SimpleDateFormat("dd/MM/yyyy");  
		}
		
		try {
			 data = df.parse(text);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,"ERRO:"+e.getMessage());
			e.printStackTrace();
		}
		
        return data;
    }
        
    /**
     * 
     * @return o horario de inicio no formato hh:mm
     */
    public String getHorarioInicioHM(){
        DateFormat df = new SimpleDateFormat("HH:mm");
        return (this.horarioInicio != null) ? df.format(this.horarioInicio) : "";
    }
    
    /**
     * 
     * @return Horario de termino no formato hh:mm
     */
    public String getHorarioTerminoHM(){
        DateFormat df = new SimpleDateFormat("HH:mm");
        return (this.horarioTermino != null) ? df.format(this.horarioTermino) : "";
    }

    /**
     * 
     * @return A data de inicio no formato dia/mes/ano
     */
    public String getDataInicioPtBr(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return (this.dataInicio != null) ? df.format(this.dataInicio) : "";
    }
    
    /**
     * 
     * @return Data de termino no formato dia/mes/ano
     */
    public String getDataFimPtBr(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return (this.dataFim != null) ? df.format(this.dataFim) : "";
    }


    @Override
    public String toString(){
        return  "ID: "+id+"\n"+
                "SALA: " +sala.getNome()+"\n"+
                "DATA INICIO: "+dataInicio+"\n"+
                "DATA FIM: "+dataFim+"\n"+
                "HORARIO INICIO: "+horarioInicio+"\n"+
                "HORARIO TERMINO: "+horarioTermino+"\n"+
                "RESPONSAVEL: "+responsavel+"\n"+
                "RESERVA PARA: "+reservadoPara+"\n"+
                "EVENTO PRIVADO: "+eventoPrivado+"\n"+
                "EMAIL: "+email+"\n"+
                "TELEFONE: "+telefone+"\n"+
                "OBSERVACAO: "+observacao+"\n"+
                "ACEITO: "+aceito+"\n"+
                "STATUS: "+status;
    }
	
}
