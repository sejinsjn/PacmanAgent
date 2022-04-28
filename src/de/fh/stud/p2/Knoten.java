package de.fh.stud.p2;

import de.fh.pacman.enums.PacmanTileType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Knoten implements Comparable<Knoten>{

	private Knoten parent;
	private final List<Knoten> children;
	private final PacmanTileType[][] world;
	private final int posX;
	private final int posY;
	private int cost;

	public Knoten(PacmanTileType[][] world, int posX, int posY, int cost){
		parent = null;
		children = new LinkedList<>();
		this.world = copyWorld(world);
		this.posX = posX;
		this.posY = posY;
		this.cost = cost;
	}

	public Knoten(PacmanTileType[][] world, int posX, int posY){
		parent = null;
		children = new LinkedList<>();
		this.world = copyWorld(world);
		this.posX = posX;
		this.posY = posY;
		this.cost = 0;
	}

	public PacmanTileType[][] getWorld(){ return world; }

	public Knoten getParent() { return parent; }

	public int getPosX(){ return posX; }

	public int getPosY() { return posY; }

	public int getCost() { return cost; }

	public void setCost(int cost) { this.cost = cost; }

	public int hashCode() {
		return Arrays.deepHashCode(this.world);
	}

	/**
	 * Kopiert die alle Werte der übergebenden World einzeln in eine neue World um
	 * eine Referenzierung der übergebenden World zu verhindern.
	 *
	 * @param world PacmanTileType[][]
	 * @return copy of param world without reference
	 */
	private PacmanTileType[][] copyWorld(PacmanTileType[][] world){
		PacmanTileType[][] copy = null;
		if(world != null){
			int lenX = world.length, lenY = world[0].length;
			copy = new PacmanTileType[lenX][lenY];
			for (int x = 0; x < lenX; x++)
				for (int y = 0; y < lenY; y++)
					copy[x][y] = world[x][y];
		}
		return copy;
	}

	/**
	 * Simuliert in einer kopierten World den Weg zum Tile auf der Position x und y.
	 * Die aktuelle Position wird auf Empty gesetzt und die neue übergebende Position in
	 * der kopierten Welt wird auf Pacman gesetzt.
	 *
	 * @param x Position auf der X-Achse
	 * @param y Position auf der Y-Achse
	 * @return Knoten mit der kopierten Welt, neuen Positionen und den Kosten 1 für Dot und 2 für Empty
	 */
	private Knoten simulateGoingToXY(int x, int y){
		if(world != null){
			PacmanTileType nextTile = world[x][y];
			PacmanTileType[][] newWorld = copyWorld(world);
			if(nextTile.equals(PacmanTileType.DOT) || nextTile.equals(PacmanTileType.EMPTY))
				if(newWorld[x][y] != PacmanTileType.WALL) {
					newWorld[posX][posY] = PacmanTileType.EMPTY;
					newWorld[x][y] = PacmanTileType.PACMAN;
					return new Knoten(newWorld, x, y, 1);
				}
			return null;
		}
		return null;
	}

	/**
	 * Überprüft ob die Welt des aktuellen Knotens noch Dots besitzt
	 *
	 * @return Wenn Dots vorhanden wird false zurückgegeben ansonsten true
	 */
	public boolean worldCleared(){
		if(world != null){
			int lenX = world.length, lenY = world[0].length;
			for (PacmanTileType[] pacmanTileTypes : world) {
				int y = 0;
				while (y < lenY) {
					if (pacmanTileTypes[y] == PacmanTileType.DOT)
						return false;
					y++;
				}
			}
		}
		return true;
	}

	/**
	 * Heuristik für die Greedy-Search und A*. Es zählt die Dots die in der World vorhanden
	 * sind. Das Ergebnis schätzt den Weg zu allen Dots die in der Welt existieren.
	 *
	 * @return
	 */
	public int heuristik() {
		int a = 0;
		for (PacmanTileType[] pacmanTileTypes : world)
			for (PacmanTileType pacmanTileType : pacmanTileTypes)
				if (pacmanTileType == PacmanTileType.DOT)
					a++;
		return a;
	}

	/**
	 * Vergleicht die aktuelle World mit der World des übergebenden Knotens
	 *
	 * @param o Übergebender Knoten als allgemeines Objekt
	 * @return true - beide Worlds sind identisch, false - Worlds sind nicht identisch
	 */
	@Override
	public boolean equals(Object o) {
		Knoten compare = (Knoten) o;
		return Arrays.deepEquals(this.getWorld(), compare.getWorld());
	}

	/**
	 * Vergleicht die Kosten des aktuellen Knotens und des übergebenden Knotens. Wichtig für
	 * das Sortieren der OpenList durch die Collections.
	 *
	 * @param o Knoten mit dem verglichen werden soll
	 * @return Differenz der Kosten - bei >0 wird aufsteigend sortiert - bei <0 wird absteigend sortiert
	 */
	@Override
	public int compareTo(Knoten o) {
		return this.getCost() - o.getCost() ;
	}

	/**
	 * Gibt die Daten des Knoten als String aus
	 *
	 * @return String mit den ganzen Daten von Knoten
	 */
	@Override
	public String toString() {
		return "Xpos: " + getPosX() + " Ypos: " + getPosY() + " Kosten: " + getCost();
	}

	/**
	 * Expandiert den aktuellen Knoten. Mögliche Nachfolger sind Norden, Osten, Westen und Süden.
	 * Je nachdem ob es einen Nachfolger in diesen Richtungen gibt wird dieser in eine Liste gespeichert.
	 *
	 * @return Gibt eine Liste mit den Nachfolgern aus
	 */
	public List<Knoten> expand() {
		/*
		 * TODO Praktikum 2 [2]: Implementiert in dieser Methode das Expandieren des Knotens.
		 * Die Methode soll die neu erzeugten Knoten (die Kinder des Knoten) zurückgeben.
		 */
		if(posX-1 > -1 && posY-1 > -1 && posX+1 < world.length && posY+1 < world[0].length ) {
			Knoten goingNorth = this.simulateGoingToXY(posX, posY - 1);
			if (goingNorth != null)
				children.add(goingNorth);

			Knoten goingEast = this.simulateGoingToXY(posX + 1, posY);
			if (goingEast != null)
				children.add(goingEast);

			Knoten goingSouth = this.simulateGoingToXY(posX, posY + 1);
			if (goingSouth != null)
				children.add(goingSouth);

			Knoten goingWest = this.simulateGoingToXY(posX - 1, posY);
			if (goingWest != null)
				children.add(goingWest);

			for (Knoten k : children)
				k.parent = this;
		}
		return children;
	}
}