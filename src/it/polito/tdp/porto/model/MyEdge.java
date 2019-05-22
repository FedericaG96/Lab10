package it.polito.tdp.porto.model;

import org.jgrapht.graph.DefaultEdge;

public class MyEdge extends DefaultEdge{
	
	private Paper pubbl;
	
	

	public MyEdge(Author a1, Author a2, Paper pubbl) {
		super();
		this.pubbl = pubbl;
	}

	public Paper getPubbl() {
		return pubbl;
	}

	public void setPubbl(Paper pubbl) {
		this.pubbl = pubbl;
	}
	
	

}
