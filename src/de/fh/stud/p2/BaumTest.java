package de.fh.stud.p2;

import de.fh.pacman.enums.PacmanTileType;

import java.util.List;

public class BaumTest {

	public static void main(String[] args) {
		//Anfangszustand nach Aufgabe
		PacmanTileType[][] view = {
				{PacmanTileType.WALL,PacmanTileType.WALL,PacmanTileType.WALL,PacmanTileType.WALL},
				{PacmanTileType.WALL,PacmanTileType.EMPTY,PacmanTileType.DOT,PacmanTileType.WALL},
				{PacmanTileType.WALL,PacmanTileType.DOT,PacmanTileType.WALL,PacmanTileType.WALL},
				{PacmanTileType.WALL,PacmanTileType.WALL,PacmanTileType.WALL,PacmanTileType.WALL}
		};
		//Startposition des Pacman
		int posX = 1, posY = 1;
		/*
		 * TODO Praktikum 2 [3]: Baut hier basierend auf dem gegebenen 
		 * Anfangszustand (siehe view, posX und posY) den Suchbaum auf.
		 */
		Knoten start = new Knoten(view, posX, posY);
		List<Knoten> nachfolger = start.expand();
		int size = nachfolger.size();

		for(int i = 0; i < size; i++) {
			nachfolger.addAll(nachfolger.get(i).expand());
		}

		for(Knoten k : nachfolger) {
			System.out.println(k);
		}
	}
}
