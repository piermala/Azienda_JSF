package it.alfasoft.pierangelo.editview;


import it.alfasoft.pierangelo.controller.DipendenteController;
import it.alfasoft.pierangelo.model.bean.Dipendente;
import it.alfasoft.pierangelo.servizi.ServiziDipendente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@SessionScoped
@ManagedBean(name="dipEditView")
public class EditViewDipendente implements Serializable {


	private static final long serialVersionUID = 1L;
	
	
	private ServiziDipendente servDipendente;	
	private List<Dipendente> listaDipendenti;	
	
	@ManagedProperty("#{controllerDipendente}")
    private DipendenteController controller;
	
	
	
	/// GETTERS AND SETTERS
	public List<Dipendente> getListaDipendenti() {
		return listaDipendenti;
	}
	public void setListaDipendenti(List<Dipendente> listaClienti) {
		this.listaDipendenti = listaClienti;
	}
	public ServiziDipendente getServDipendente() {
		return servDipendente;
	}
	public void setServDipendente(ServiziDipendente servDipendente) {
		this.servDipendente = servDipendente;
	}	
	public DipendenteController getController() {
		return controller;
	}
	public void setController(DipendenteController controller) {
		this.controller = controller;
	}
	
	
	
	/// COSTRUTTORI
	
	public EditViewDipendente(){
		super();
	}
	
	
	@PostConstruct
	public void init(){
		
		servDipendente = new ServiziDipendente();
		listaDipendenti = servDipendente.getTuttiDipendenti();
		
	}
	
	
	
	
	public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Dipendente modificato", ((Dipendente) event.getObject()).getUsername());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        servDipendente.modificaDipendente((Dipendente) event.getObject());
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Modifica annullata", ((Dipendente) event.getObject()).getUsername());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Propriet� cambiata", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    
    public String delete(Dipendente dipendente){
    	controller.deleteDipendente(dipendente);
    	return "elencoDipendenti?faces-redirect=true";
    }
    
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public String getListValues(){
		
		String value = "";
		
		for (Dipendente d : listaDipendenti){
			value = d.getNome() + " " + d.getCognome();
		}
		
		return value;
	}
	
	
}
