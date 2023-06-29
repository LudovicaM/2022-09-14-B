package it.polito.tdp.itunes.model;

public class Coppia {
	
	private Album a1;
	private Album a2;
	
	
	public Coppia(Album a1, Album a2) {
		super();
		this.a1 = a1;
		this.a2 = a2;
	}


	public Album getA1() {
		return a1;
	}


	public void setA1(Album a1) {
		this.a1 = a1;
	}


	public Album getA2() {
		return a2;
	}


	public void setA2(Album a2) {
		this.a2 = a2;
	}
	
	

}
