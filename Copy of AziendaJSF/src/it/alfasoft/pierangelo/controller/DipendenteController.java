package it.alfasoft.pierangelo.controller;

import it.alfasoft.pierangelo.model.bean.Dipendente;
import it.alfasoft.pierangelo.model.bean.Utente;
import it.alfasoft.pierangelo.servizi.ServiziDipendente;
import it.alfasoft.pierangelo.servizi.ServiziRubrica;
import it.alfasoft.pierangelo.servizi.ServiziUtente;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.ws.Response;

import utility.CodificaPassword;


@ManagedBean(name="controllerDipendente",eager=true)
@SessionScoped
public class DipendenteController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Dipendente> listaDipendenti;
	private ServiziDipendente servDipendente;
	private ServiziRubrica servRubrica;
	private ServiziUtente servUtente;
	
	
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

	
	
	public ServiziUtente getServUtente() {
		return servUtente;
	}

	public void setServUtente(ServiziUtente servUtente) {
		this.servUtente = servUtente;
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
		servUtente = new ServiziUtente();
		servDipendente = new ServiziDipendente();	
		servRubrica = new ServiziRubrica();
		listaDipendenti = servDipendente.getTuttiDipendenti();
	}
	
	
	
	
	/// AGGIUNGI DIPENDENTE
	public String addDipendente(Dipendente dipendente){		
			
		// codifica password
		dipendente.setPassword(CodificaPassword.codificaPsw(dipendente.getPassword()));
				
		servDipendente.createDipendente(dipendente.getNome(), dipendente.getCognome(), dipendente.getUsername(), dipendente.getPassword(), dipendente.getPosizione(), dipendente.getStipendio());	/// aggiungi nel database
		servRubrica.aggiungiRubrica(dipendente.getUsername());
		
		
		Utente u = servUtente.cercaConUsername(dipendente.getUsername());
		
		if (u == null){
			servRubrica.aggiungiRubrica(dipendente.getUsername());
		}		
		
		aggiornaDipendenti();
		return "elencoDipendenti?faces-redirect=true";
		
	}	
	

	///  MODIFICA DIPENDENTE
	public String editDipendente(String username) {

		Dipendente cliente = servDipendente.trovaDipendenteConUsername(username);
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		Map<String,Object> requestMap= context.getRequestMap();
		requestMap.put("c", cliente);
		
		return "modificaDipendente";
		
	}
	
	
	///  ELIMINA DIPENDENTE
	public String deleteDipendente(Dipendente dipendente) {

		servDipendente.eliminaDipendente(dipendente);
		aggiornaDipendenti();

		return "elencoDipendenti?faces-redirect=true";

	}
	
	
	
	
	/// AGGIORNA LISTA DIPENDENTI
	public void aggiornaDipendenti(){
		setListaDipendenti(servDipendente.getTuttiDipendenti());
	}
	
	
	
	/// ANNULLA
	public String annulla(){
		
		return "HomepageAdmin?faces-redirect=true";
		
	}
	
	


	
	
	
	
	
}
