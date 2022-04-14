package de.fh.stud.p3;

import de.fh.stud.p2.Knoten;

import java.util.List;

public class Suche {
	
	/*
	 * TODO Praktikum 3 [1]: Erweitern Sie diese Klasse um die notwendigen
	 * Attribute und Methoden um eine Tiefensuche durchführen zu können.
	 * Die Erweiterung um weitere Suchstrategien folgt in Praktikum 4.
	 */
	List<Knoten> openlist;
	Knoten next;

	/*
	 * TODO Praktikum 4 [1]: Erweitern Sie diese Klasse um weitere Suchstrategien (siehe Aufgabenblatt)
	 * zu unterstützen.
	 */
	
	public Knoten start(Knoten start) {
		/*
		 * TODO Praktikum 3 [2]: Implementieren Sie hier den Algorithmus einer Tiefensuche.
		 */
		openlist = start.expand();
		while(true){
			if(openlist.isEmpty()) return next;
			next = openlist.get(0);
			openlist.remove(0);
			System.out.println(next.toString());
			if(next.worldEmpty()) return next;
				else if(openlist.contains(next)) for(Knoten k: next.expand()) openlist.add(k);
		}
	}
	
}
