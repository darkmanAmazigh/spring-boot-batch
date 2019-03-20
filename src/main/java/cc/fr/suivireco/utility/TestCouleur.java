package com.exane.anafi.neuron.security;

public enum TestCouleur {
	MARRON("marron"),
	BLEU("bleu"),
	VERT("vert"),
	VERRON("verron"),
	INCONNU("non déterminé"),
	ROUGE("rouge mais j'avais piscine...");
	
	private String name = "";
	
	TestCouleur(String n){name = n;}
	public String toString() {return name;}
}
