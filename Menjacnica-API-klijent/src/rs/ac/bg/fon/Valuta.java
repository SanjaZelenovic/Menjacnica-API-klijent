package rs.ac.bg.fon;

public class Valuta {

	private String naziv;
	private double kurs;
	
	public Valuta(String naziv, double kurs) {
		this.naziv = naziv;
		this.kurs = kurs;
	}
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public double getKurs() {
		return kurs;
	}
	public void setKurs(double kurs) {
		this.kurs = kurs;
	}
	
	public String toString() {
		return "Valuta [naziv=" + naziv + ", kurs=" + kurs + "]";
	}
	
	public boolean equals(Object obj) {
		
		if ( obj instanceof Valuta) {
			Valuta v = (Valuta) obj;
			if(v.getNaziv().equals(naziv) && v.getKurs() == kurs)
			return true;
		}
		return false;
	}
	
}
