package rs.ac.bg.fon.sanja.update;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.sanja.Valuta;
import rs.ac.bg.fon.sanja.util.JsonRatesAPIKomunikacija;

public class AzuriranjeKursneListe {

	private final String putanjaDoFajlaKursnaLista = "data/kursnaLista.json";
	LinkedList<Valuta> kursevi = new LinkedList<Valuta>();
	
	
	@SuppressWarnings("unchecked")
	public LinkedList<Valuta> ucitajValute() {
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(putanjaDoFajlaKursnaLista)));
			kursevi = (LinkedList<Valuta>)(in.readObject());

			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return kursevi;
	}
	
	public void upisiValute(LinkedList<Valuta> valute, GregorianCalendar datumUpisa) {
		
		try {
			
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(putanjaDoFajlaKursnaLista)));
			JsonObject dJson = new JsonObject();
			dJson.addProperty("datum", datumUpisa.toString());
			out.writeObject(dJson);
			
			JsonArray kursArray = new JsonArray();
			
			if (kursArray != null) {
				
				for (int i = 0; i < valute.size(); i++) {

					JsonObject valutaJson = new JsonObject();
					valutaJson.addProperty("naziv", valute.get(i).getNaziv());
					valutaJson.addProperty("kurs", valute.get(i).getKurs());

					kursArray.add(valutaJson);
				}
				out.writeObject(kursArray);
			}

			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
public void azurirajValute() {
		//kreira novu listu valuta, koja sadrzi sve koje za sada postoje
		LinkedList<Valuta> noviKursevi = ucitajValute();
		
		//pomocni niz koji kupi nazive valuta i prosledjuje metodi vratiIznosKurseva
		String[] naziviTrenutnihKurseva = new String[kursevi.size()];
		
		for (int i = 0; i < naziviTrenutnihKurseva.length; i++) {
			naziviTrenutnihKurseva[i] = kursevi.get(i).getNaziv();
		}
		
		Valuta[] noveValute = JsonRatesAPIKomunikacija.vratiIznosKurseva(naziviTrenutnihKurseva);
		
		for (int i = 0; i < noveValute.length; i++) {
			if(!noviKursevi.contains(noveValute)) {
				noviKursevi.add(noveValute[i]);
			}
		}
		
		upisiValute(noviKursevi, new GregorianCalendar());
	}
}
