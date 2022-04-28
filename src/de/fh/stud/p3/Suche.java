package de.fh.stud.p3;

import de.fh.stud.p2.Knoten;

import java.util.*;

public class Suche {
	
	/*
	 * TODO Praktikum 3 [1]: Erweitern Sie diese Klasse um die notwendigen
	 * Attribute und Methoden um eine Tiefensuche durchführen zu können.
	 * Die Erweiterung um weitere Suchstrategien folgt in Praktikum 4.
	 */
	private Knoten start;
	private int strategy, cost;
	private Hashtable<Integer, Knoten> closedList;
	private List<Knoten> openList;

	public Suche(Knoten start){
		this.start = start;
		closedList = new Hashtable<>();
		openList = new LinkedList<>();
	}

	/**
	 * Hier wird entschieden welche Suche man benutzt und je nachdem was man für eine Suche benutzen möchte,
	 * wird die openList dementsprechend sortiert.
	 *
	 * @param child expandierter Knoten
	 * @return sortierte openList
	 */
	public List<Knoten> insert(Knoten child){
		List<Knoten> newList = new LinkedList<>();
		switch (strategy) {
			case 1 -> {
				newList.add(child);
				newList.addAll(openList);
				return newList;
			}
			case 2 -> {
				openList.add(child);
				return openList;
			}
			case 3, 4, 5 -> {
				closedList.put(child.hashCode(), child);
				openList.add(child);
				Collections.sort(openList);
				return openList;
			}
		}
		return null;
	}

	/**
	 * Hier wird der Suchalgorithmus ausgeführt. Je nachdem welche Suche man benutzen möchte
	 * kann man diese durch die im param angegebenen Zahlen auswählen.
	 *
	 * @param strategy
	 * 			1 - Tiefensuche,
	 * 			2 - Breitensuche,
	 * 			3 - UCS,
	 * 			4 - Greedy-Search,
	 * 			5 - A-Stern
	 * @return Lösungsknoten
	 */
	public Knoten start(int strategy) {
		/*
		 * TODO Praktikum 3 [2]: Implementieren Sie hier den Algorithmus einer Tiefensuche.
		 */
		Knoten current = start;
		this.strategy = strategy;

		openList.add(current);
		while(!openList.isEmpty()){
			current = openList.remove(0);
			switch (strategy) {
				case 3:
					current.setCost(current.getCost() + cost);
					cost = current.getCost();
				case 4:
					current.setCost(current.heuristik());
					cost = current.getCost();
				case 5:
					cost = current.getCost();
					current.setCost(current.heuristik() + cost);
			}
			closedList.put(current.hashCode(), current);
			if(current.worldCleared()){
				System.out.println("openList Size: " + openList.size());
				System.out.println("closedList Size: " + closedList.size());
				return current;
			}
			for(Knoten child : current.expand())
				if(!closedList.containsKey(child.hashCode()))
					openList = insert(child);
		}
		System.out.println("openList Size: " + openList.size());
		System.out.println("closedList Size: " + closedList.size());
		System.out.println("Keine Loesung");
		return current;
	}
}
