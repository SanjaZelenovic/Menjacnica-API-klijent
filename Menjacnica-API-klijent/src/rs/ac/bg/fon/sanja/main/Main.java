package rs.ac.bg.fon.sanja.main;

import java.io.IOException;

import rs.ac.bg.fon.sanja.update.AzuriranjeKursneListe;

public class Main {

	public static void main(String[] args)  throws IOException {
		
		AzuriranjeKursneListe azur = new AzuriranjeKursneListe();

		try {
			azur.azurirajValute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	

	}

}
