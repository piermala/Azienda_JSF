package it.alfasoft.pierangelo.model.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@ManagedBean(name="dipendente",eager=true)
@SessionScoped
@Entity
public class Dipendente extends Utente {

	private static final long serialVersionUID = 1L;
	
	private String posizione;
	private double stipendio;
	
	@OneToMany(mappedBy="dipendente",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@NotFound(action=NotFoundAction.IGNORE)
	private List<BustaPaga> listaBustePaga = new ArrayList<BustaPaga>();
	
	
	
	public double getStipendio() {
		return stipendio;
	}
	public void setStipendio(double stipendio) {
		this.stipendio = stipendio;
	}
	public String getPosizione() {
		return posizione;
	}
	public void setPosizione(String posizione) {
		this.posizione = posizione;
	}
	public List<BustaPaga> getListaBustePaga() {
		return listaBustePaga;
	}
	public void addBustaPaga(BustaPaga bustaPaga) {
		this.listaBustePaga.add(bustaPaga);
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setListaBustePaga(List<BustaPaga> listaBustePaga) {
		this.listaBustePaga = listaBustePaga;
	}
	public Dipendente(){
		this.ruolo = 'D';
	}
	
	
	public Dipendente(String nome, String cognome, String username,
			String password, char ruolo, String posizione, double stipendio) {
		super(nome, cognome, username, password, ruolo);
		this.posizione = posizione;
		this.stipendio = stipendio;
	}
	
	@Override
	public String toString() {
		return nome + " " + cognome;
	}
	
	
	
	
	
}
