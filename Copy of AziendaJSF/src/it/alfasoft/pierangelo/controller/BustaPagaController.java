package it.alfasoft.pierangelo.controller;

import it.alfasoft.pierangelo.model.bean.BustaPaga;
import it.alfasoft.pierangelo.model.bean.Dipendente;
import it.alfasoft.pierangelo.servizi.ServiziBustaPaga;
import it.alfasoft.pierangelo.servizi.ServiziDipendente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;




@ManagedBean(name="controllerBustaPaga",eager=true)
@RequestScoped
public class BustaPagaController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<BustaPaga> listaBustePaga;
	private List<Dipendente> listaDipendenti;
	private ServiziBustaPaga servBustaPaga;
	private ServiziDipendente servDipendente;
	
	private BustaPaga bustaPaga;
	private Dipendente dipendente;
	private long sceltaId;
	private String sceltaUsername;
	
	private Map<String,Long> dipendenti;
	
	//// GETTERS AND SETTER	
	
	public List<BustaPaga> getListaBustePaga() {
		return listaBustePaga;
	}


	public void setListaBustePaga(List<BustaPaga> listaBustePaga) {
		this.listaBustePaga = listaBustePaga;
	}


	public ServiziBustaPaga getServBustaPaga() {
		return servBustaPaga;
	}


	public void setServBustaPaga(ServiziBustaPaga servBustaPaga) {
		this.servBustaPaga = servBustaPaga;
	}


	
	public long getSceltaId() {
		return sceltaId;
	}


	public void setSceltaId(long sceltaId) {
		this.sceltaId = sceltaId;
	}


	public BustaPaga getBustaPaga() {
		return bustaPaga;
	}


	public void setBustaPaga(BustaPaga bustaPaga) {
		this.bustaPaga = bustaPaga;
	}

	public Dipendente getDipendente() {
		return dipendente;
	}


	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}


	public List<Dipendente> getListaDipendenti() {
		return listaDipendenti;
	}


	public void setListaDipendenti(List<Dipendente> listaDipendenti) {
		this.listaDipendenti = listaDipendenti;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	public ServiziDipendente getServDipendente() {
		return servDipendente;
	}


	public void setServDipendente(ServiziDipendente servDipendente) {
		this.servDipendente = servDipendente;
	}


	public String getSceltaUsername() {
		return sceltaUsername;
	}


	public void setSceltaUsername(String sceltaUsername) {
		this.sceltaUsername = sceltaUsername;
	}


	public Map<String,Long> getDipendenti() {
		return dipendenti;
	}


	public void setDipendenti(Map<String,Long> dipendenti) {
		this.dipendenti = dipendenti;
	}


	//// COSTRUTTORI
	public BustaPagaController(){
		
	}

	
	@PostConstruct
    public void init() {
		dipendente = new Dipendente();
		listaBustePaga = new ArrayList<BustaPaga>();
		servBustaPaga = new ServiziBustaPaga();	
		servDipendente = new ServiziDipendente();
		
		dipendenti = new HashMap<String, Long>();
		for(Dipendente d : servDipendente.getTuttiDipendenti()){
			dipendenti.put(d.getCognome()+" "+d.getNome(), d.getIdUtente());
		}
	}
	
	
	
	
	/// AGGIUNGI BUSTA PAGA
	public String addBustaPaga(BustaPaga bustaPaga){
				
		Dipendente dipendente = servDipendente.getDipendenteConId(sceltaId);
		bustaPaga.setDipendente(dipendente);
		this.dipendente.addBustaPaga(bustaPaga);
			
		servBustaPaga.creaBustaPaga(bustaPaga);
		aggiornaBustePaga();
		return "HomepageAdmin?faces-redirect=true";

	}	
	

	///  MODIFICA BUSTA PAGA
	public String editBustaPaga(long id) {

		BustaPaga bustaPaga = servBustaPaga.leggiBustaPagaDaId(id);
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		Map<String,Object> requestMap= context.getRequestMap();
		requestMap.put("bp", bustaPaga);
		
		return "modificaBustaPaga";
		
	}
	
	
	///  ELIMINA BUSTA PAGA
	public String deleteBustaPaga(BustaPaga bustaPaga) {

		servBustaPaga.eliminaBustaPaga(bustaPaga);
		aggiornaBustePaga();

		return "HomepageAdmin?faces-redirect=true";

	}
	
	
	
	
	/// AGGIORNA LISTA BUSTE PAGA
	public void aggiornaBustePaga(){
		setListaBustePaga(servBustaPaga.leggiTutteBustePaga());
	}
	
	

}
