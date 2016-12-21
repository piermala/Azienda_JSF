package main;

import java.util.List;

import it.alfasoft.pierangelo.model.bean.BustaPaga;
import it.alfasoft.pierangelo.model.bean.Dipendente;
import it.alfasoft.pierangelo.servizi.ServiziBustaPaga;
import it.alfasoft.pierangelo.servizi.ServiziDipendente;
import it.alfasoft.pierangelo.servizi.ServiziUtente;

public class Prova {

	private static List<BustaPaga> listaBustePaga;
	private static ServiziBustaPaga servBustaPaga;
	private static ServiziDipendente servDipendente;
	private static ServiziUtente servUtente;
	private static BustaPaga bustaPaga;
	private static Dipendente dipendente;
	private static long sceltaId;
	
	
	public static void main(String[] args) {
		
		
		dipendente = (Dipendente) servUtente.cercaUtenteConId(67);
		
		System.out.println(dipendente.getNome() + " " + dipendente.getCognome());
		
		
		
	}

}
