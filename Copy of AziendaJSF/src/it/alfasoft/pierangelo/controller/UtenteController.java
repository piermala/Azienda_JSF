package it.alfasoft.pierangelo.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="uC",eager=true)
@SessionScoped
public class UtenteController {

	private String username;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
}