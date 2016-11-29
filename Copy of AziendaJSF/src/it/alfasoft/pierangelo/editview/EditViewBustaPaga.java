package it.alfasoft.pierangelo.editview;


import it.alfasoft.pierangelo.controller.BustaPagaController;
import it.alfasoft.pierangelo.controller.DipendenteController;
import it.alfasoft.pierangelo.model.bean.BustaPaga;
import it.alfasoft.pierangelo.model.bean.BustaPaga;
import it.alfasoft.pierangelo.servizi.ServiziBustaPaga;
import it.alfasoft.pierangelo.servizi.ServiziDipendente;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@SessionScoped
@ManagedBean(name="bustaPagaEditView")
public class EditViewBustaPaga implements Serializable {


	private static final long serialVersionUID = 1L;
	
	
	private ServiziBustaPaga servBustaPaga;	
	private List<BustaPaga> listaBustePaga;	
	
	@ManagedProperty("#{controllerBustaPaga}")
    private BustaPagaController controller;
	
	
	
	/// GETTERS AND SETTERS


	public ServiziBustaPaga getServBustaPaga() {
		return servBustaPaga;
	}


	public void setServBustaPaga(ServiziBustaPaga servBustaPaga) {
		this.servBustaPaga = servBustaPaga;
	}


	public List<BustaPaga> getListaBustePaga() {
		return listaBustePaga;
	}


	public void setListaBustePaga(List<BustaPaga> listaBustePaga) {
		this.listaBustePaga = listaBustePaga;
	}


	public BustaPagaController getController() {
		return controller;
	}


	public void setController(BustaPagaController controller) {
		this.controller = controller;
	}	
	
	
	
	/// COSTRUTTORI
	public EditViewBustaPaga(){
		super();
	}
	
	
	@PostConstruct
	public void init(){
		
		servBustaPaga = new ServiziBustaPaga();
		listaBustePaga = servBustaPaga.leggiTutteBustePaga();
		
	}
	
	
	
	
	public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Busta paga modificata", ((BustaPaga) event.getObject()).getId_BustaPaga() + "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        servBustaPaga.modificaBustaPaga( (BustaPaga) event.getObject());
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Busta paga eliminata", ((BustaPaga) event.getObject()).getId_BustaPaga() + "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Proprietà cambiata", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    
    public String delete(BustaPaga bustaPaga){
    	controller.deleteBustaPaga(bustaPaga);
    	return "HomepageAdmin";
    }
    
    
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public String getListValues(){
		
		String value = "";
		
		for (BustaPaga d : listaBustePaga){
			value = d.getDipendente() + " " + d.getImporto() + " " + d.getData();
		}
		
		return value;
	}
	
	
	/// DISPLAY BUSTA PAGA EMESSA
	public void displayLocation() {
        FacesMessage msg;
        if(listaBustePaga != null)
            msg = new FacesMessage("Busta paga aggiunta!");
        else
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "City is not selected."); 
             
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
	
	
	
	
	
	
	
}
