package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

public class CoAutore {
	
	private  Author autore1;
	private Author autore2;
	private Paper pubbl;
	
	public CoAutore(Author autore1, Author autore2, Paper pubbl) {
		super();
		this.autore1 = autore1;
		this.autore2 = autore2;
		this.pubbl = pubbl;
	}
	public Author getAutore1() {
		return autore1;
	}
	public Author getAutore2() {
		return autore2;
	}
	public Paper getPubbl() {
		return pubbl;
	}
	public void setPubbl(Paper pubbl) {
		this.pubbl = pubbl;
	}
	
	
	

}
