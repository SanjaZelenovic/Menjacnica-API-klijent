package rs.ac.bg.fon.sanja.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.sanja.Valuta;

public class JsonRatesAPIKomunikacija {

	private static final String appKey = "jr-ba8999934fc5a7ab64a4872fb4ed9af7";
	private static final String jsonRatesURL = "http://jsonrates.com/get/";
	private static String url;
	

	//http://jsonrates.com/get/?from=EUR&to=RSD&apiKey=jr-ba8999934fc5a7ab64a4872fb4ed9af7
	
	private static String ispisiFromTo(String from, String to) {
		return url = jsonRatesURL + "?" +
				"from=" + from +
				"&to=" + to +
				"&apiKey=" + appKey;
	}
	
	public static Valuta[] vratiIznosKurseva( String [] valute) {
		
		Valuta[] kursevi = new Valuta[valute.length];
		
		for (int j = 0; j < valute.length; j++) {
			for (int k = 0; k < valute.length; k++) {
				if( j != k) {
					ispisiFromTo(valute[j], valute[k]);

				try {
					String result = sendGet(url);

					Gson gson = new GsonBuilder().create();
					JsonObject jsonResult = (JsonObject) gson.fromJson(result, JsonObject.class);

					Valuta val = new Valuta(valute[j], Double.parseDouble(jsonResult.get("rate").getAsString()));
					
					kursevi[j] = val;
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		}
		return kursevi;
		
	}
	
	private static String sendGet(String url) throws IOException {
		
		URL obj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		
		connection.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(connection.getInputStream()));
		
		boolean endReading = false;
		String response = "";
		
		while (!endReading) {
			String s = in.readLine();
			
			if (s != null) {
				response += s;
			} else {
				endReading = true;
			}
		}
		in.close();
 
		return response.toString();
	}
	
	
}
