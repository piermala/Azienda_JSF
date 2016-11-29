package it.alfasoft.pierangelo.controller;

import it.alfasoft.pierangelo.model.bean.Dipendente;
import it.alfasoft.pierangelo.servizi.ServiziDipendente;
import it.alfasoft.pierangelo.servizi.ServiziRubrica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


@ManagedBean(name="controllerDipendente",eager=true)
@SessionScoped
public class DipendenteController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Dipendente> listaDipendenti;
	private ServiziDipendente servDipendente;
	private ServiziRubrica servRubrica;
	
	
	//// GETTERS AND SETTERS
	public List<Dipendente> getListaDipendenti() {
		return listaDipendenti;
	}

	public void setListaDipendenti(List<Dipendente> listaDipendenti) {
		this.listaDipendenti = listaDipendenti;
	}
	
	
	public ServiziDipendente getServDipendente() {
		return servDipendente;
	}

	public void setServDipendente(ServiziDipendente servDipendente) {
		this.servDipendente = servDipendente;
	}

	public ServiziRubrica getServRubrica() {
		return servRubrica;
	}

	public void setServRubrica(ServiziRubrica servRubrica) {
		this.servRubrica = servRubrica;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	//// COSTRUTTORI
	public DipendenteController(){
		
	}

	public DipendenteController(List<Dipendente> listaDipendenti) {
		super();
		this.listaDipendenti = listaDipendenti;
	}
	
	@PostConstruct
    public void init() {
		servDipendente = new ServiziDipendente();	
		servRubrica = new ServiziRubrica();
		listaDipendenti = servDipendente.getTuttiDipendenti();
	}
	
	
	
	
	/// AGGIUNGI CLIENTE
	public String addDipendente(Dipendente dipendente){		
			
		servDipendente.createDipendente(dipendente.getNome(), dipendente.getCognome(), dipendente.getUsername(), dipendente.getPassword(), dipendente.getPosizione(), dipendente.getStipendio());	/// aggiungi nel database
		servRubrica.aggiungiRubrica(dipendente.getUsername());
		aggiornaDipendenti();
		return "elencoDipendenti?faces-redirect=true";
		
	}	
	

	///  MODIFICA CLIENTE
	public String editDipendente(String username) {

		Dipendente cliente = servDipendente.trovaDipendenteConUsername(username);
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		Map<String,Object> requestMap= context.getRequestMap();
		requestMap.put("c", cliente);
		
		return "modificaDipendente";
		
	}
	
	
	///  ELIMINA CLIENTE
	public String deleteDipendente(Dipendente dipendente) {

		servDipendente.eliminaDipendente(dipendente);
		aggiornaDipendenti();

		return "HomepageAdmin?faces-redirect=true";

	}
	
	
	
	
	/// AGGIORNA LISTA CLIENTI
	public void aggiornaDipendenti(){
		setListaDipendenti(servDipendente.getTuttiDipendenti());
	}
	
	
	
	/// ANNULLA
	public String annulla(){
		
		return "HomepageAdmin?faces-redirect=true";
		
	}


	
	
	
	
	
}
