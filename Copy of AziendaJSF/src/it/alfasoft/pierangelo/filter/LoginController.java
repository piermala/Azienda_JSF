package it.alfasoft.pierangelo.filter;


import it.alfasoft.pierangelo.controller.VoceController;
import it.alfasoft.pierangelo.model.bean.Utente;
import it.alfasoft.pierangelo.model.rubrica.Rubrica;
import it.alfasoft.pierangelo.servizi.ServiziRubrica;
import it.alfasoft.pierangelo.servizi.ServiziUtente;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import utility.CodificaPassword;

@ManagedBean(name="loginController",eager=true)
@SessionScoped
public class LoginController {

	private String username;
	private String password;
	private boolean loggato;
	private ServiziUtente servUtente;
	private VoceController vC;
	private char ruolo;
	private Rubrica rubrica;
	private ServiziRubrica servRubrica;
	private long id;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLoggato() {
		return loggato;
	}
	public void setLoggato(boolean loggato) {
		this.loggato = loggato;
	}
	public ServiziUtente getServUtente() {
		return servUtente;
	}
	public void setServUtente(ServiziUtente servUtente) {
		this.servUtente = servUtente;
	}
	public char getRuolo() {
		return ruolo;
	}
	public void setRuolo(char ruolo) {
		this.ruolo = ruolo;
	}	
	public VoceController getvC() {
		return vC;
	}
	public void setvC(VoceController vC) {
		this.vC = vC;
	}	
	
	public Rubrica getRubrica() {
		return rubrica;
	}
	public void setRubrica(Rubrica rubrica) {
		this.rubrica = rubrica;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LoginController(){
	}
	
	@PostConstruct
	public void init(){
		vC = new VoceController();
		rubrica = new Rubrica();
		servRubrica = new ServiziRubrica();
	}
	
	
	
	/// DO LOGIN
	public String doLogin(){
		
		servUtente = new ServiziUtente();
		
		Utente u = servUtente.cercaConUsername(username);
		
		
		if (u != null){
			
			String passCodificata = CodificaPassword.codificaPsw(password);
			
			if (passCodificata.equals(u.getPassword())){
				
				ruolo = u.getRuolo();				

				vC.setUsername(username);
				
				switch(ruolo){
				
					case 'A':
						this.loggato = true;
						return "/Admin/HomepageAdmin.xhtml?faces-redirect=true";  
						
					case 'C':
						this.loggato = true;
						System.out.println("1- " + username);
						this.id = servRubrica.leggiID(username);
						//vC.setId(id);
						System.out.println("2- " + id);
						this.rubrica = servRubrica.cercaRubrica(this.id);
						return "/Cliente/HomepageCliente.xhtml?faces-redirect=true";
						
					case 'D':
						this.loggato = true;
						System.out.println("1- " + username);
						this.id = servRubrica.leggiID(username);
						//vC.setId(id);
						System.out.println("2- " + id);
						this.rubrica = servRubrica.cercaRubrica(this.id);
						//this.rubrica = servRubrica.cercaRubrica(id);
						return "/Dipendente/HomepageDipendente.xhtml?faces-redirect=true";	
						
				}
			}
			
		}
		
		this.loggato = false;
		
		return "login.xhtml";
		
	}
	
	
	
	/// DO LOGOUT
	public String logout(){
		this.username = "";
		this.password = "";
		this.loggato = false;
		this.ruolo = ' ';
		return "login?faces-redirect=true";
	}
	
	
	
	
}
