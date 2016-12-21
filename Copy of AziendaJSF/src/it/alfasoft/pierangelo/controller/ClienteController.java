package it.alfasoft.pierangelo.controller;

import it.alfasoft.pierangelo.model.bean.Cliente;
import it.alfasoft.pierangelo.model.bean.Utente;
import it.alfasoft.pierangelo.servizi.ServiziCliente;
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

import utility.CodificaPassword;




@ManagedBean(name="controllerCliente",eager=true)
@SessionScoped
public class ClienteController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Cliente> listaClienti;
	private ServiziCliente servCliente;
	private ServiziRubrica servRubrica;
	private ServiziUtente servUtente;
	
	
	//// GETTERS AND SETTERS
	public List<Cliente> getListaClienti() {
		return listaClienti;
	}

	public void setListaClienti(List<Cliente> listaClienti) {
		this.listaClienti = listaClienti;
	}
	
	
	public ServiziRubrica getServRubrica() {
		return servRubrica;
	}

	public void setServRubrica(ServiziRubrica servRubrica) {
		this.servRubrica = servRubrica;
	}

	public ServiziCliente getServCliente() {
		return servCliente;
	}

	public void setServCliente(ServiziCliente servCliente) {
		this.servCliente = servCliente;
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
	public ClienteController(){
		
	}

	
	@PostConstruct
    public void init() {
		servCliente = new ServiziCliente();	
		servUtente = new ServiziUtente();
		servRubrica = new ServiziRubrica();
		listaClienti = servCliente.getTuttiClienti();
	}
	
	
	
	
	/// AGGIUNGI CLIENTE
	public String addCliente(Cliente cliente){		
		
		cliente.setPassword(CodificaPassword.codificaPsw(cliente.getPassword()));
			
		servCliente.createCliente(cliente.getNome(), cliente.getCognome(), cliente.getUsername(), cliente.getPassword(), cliente.getpartitaIVA(), cliente.getRagioneSociale());	/// aggiungi nel database
		servRubrica.aggiungiRubrica(cliente.getUsername());
		
		Utente u = servUtente.cercaConUsername(cliente.getUsername());
		
		if (u == null){
			servRubrica.aggiungiRubrica(cliente.getUsername());
		}		
		
		aggiornaClienti();
		return "elencoClienti?faces-redirect=true";
		
	}	
	

	///  MODIFICA CLIENTE
	public String editCliente(String username) {

		Cliente cliente = servCliente.trovaClienteConUsername(username);
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		Map<String,Object> requestMap= context.getRequestMap();
		requestMap.put("c", cliente);
		
		return "modificaCliente";
		
	}
	
	
	///  ELIMINA CLIENTE
	public String deleteCliente(Cliente cliente) {

		servCliente.eliminaCliente(cliente);
		aggiornaClienti();

		return "elencoClienti?faces-redirect=true";

	}
	
	
	
	
	/// AGGIORNA LISTA CLIENTI
	public void aggiornaClienti(){
		setListaClienti(servCliente.getTuttiClienti());
	}
	

	
}
