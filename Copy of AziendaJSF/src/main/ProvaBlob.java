package main;

import hibernateUtil.HibernateUtil;
import it.alfasoft.pierangelo.model.bean.BustaPaga;
import it.alfasoft.pierangelo.model.bean.Dipendente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.Session;

public class ProvaBlob {

	
	public static void main(String[] args) {
	
		Dipendente dip = new Dipendente("aaa", "bbb", "abc", "123", 'D', "Segretario", 2010);
		Date d = new Date();
		BustaPaga bp = new BustaPaga();
		bp.setData(d);
		bp.setImporto(2000);
		bp.setDipendente(dip);
		
		
		
		File file = new File("C:\\Users\\piera_000\\Documents\\SISTEMA INFORMATIVO LAVORO.pdf");
		
		
		/// convertire il file pdf im blob
		Session session = HibernateUtil.openSession();
		try {
			FileInputStream fileInput = new FileInputStream(file);
			
			Blob blob = Hibernate.getLobCreator(session)
					  .createBlob(fileInput, file.length());
			
			bp.setBlob(blob);
			
			session.close();
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
}
