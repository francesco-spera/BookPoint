package bean;

import java.util.ArrayList;

public class Carrello {
	private ArrayList<Libro> libri;
	private double totale;
	
	public Carrello() {
		
	}
	
	public Carrello(ArrayList<Libro> libri, double totale) {
		this.libri= libri;
		this.totale= totale;
	}

	public ArrayList<Libro> getLibri() {
		return libri;
	}

	public void setLibri(ArrayList<Libro> libri) {
		this.libri = libri;
	}
	
	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}

	public void aggiungiAlCarrello(Libro lib, int quantità) {
		lib.setQuantitàSelezionata(quantità);
		libri.add(lib);
		totale+= lib.getPrezzo()* quantità;
	}
	
	public void aumentaQuantità(String isbn) {
		
	}
	
	public void diminuisciQuantità(String isbn) {
		
	}
	
	public void rimuoviDalCarrello(String isbn) {
		
	}

}
