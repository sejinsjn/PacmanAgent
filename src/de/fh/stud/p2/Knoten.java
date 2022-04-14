package de.fh.stud.p2;

import de.fh.pacman.enums.PacmanTileType;

import java.util.LinkedList;
import java.util.List;

public class Knoten {
	
	/*
	 * TODO Praktikum 2 [1]: Erweitert diese Klasse um die notwendigen
	 * Attribute, Methoden und Konstruktoren um die möglichen verschiedenen Weltzustände darstellen und
	 * einen Suchbaum aufspannen zu können.
	 */
	private Knoten vorgaenger;
	private	PacmanTileType[][] world;
	private int posX;
	private int posY;
	private int cost;

	public Knoten(Knoten vorgaenger, PacmanTileType[][] world, int posX, int posY, int cost){
		this.vorgaenger = vorgaenger;
		this.world = world;
		this.posX = posX;
		this.posY = posY;
		this.cost = cost;
	}

	public Knoten(Knoten vorgaenger, PacmanTileType[][] world, int posX, int posY) {
		this.vorgaenger = vorgaenger;
		this.world = world;
		this.posX = posX;
		this.posY = posY;
	}

	public Knoten(Knoten vorgaenger, PacmanTileType[][] world) {
		this.vorgaenger = vorgaenger;
		this.world = world;
	}

	public Knoten() {
	}

	public Knoten getVorgaenger() {
		return vorgaenger;
	}

	public PacmanTileType[][] getWorld() {
		return world;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getCost() {
		return cost;
	}

	public PacmanTileType[][] deepClone(PacmanTileType[][] world) {
		PacmanTileType[][] clone = new PacmanTileType[world.length][world[0].length];

		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[i].length; j++) {
				clone[i][j] = world[i][j];
			}
		}

		return clone;
	}

	public boolean worldEmpty(){
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[i].length; j++) {
				if(world[i][j] == PacmanTileType.DOT) return false;
			}
		}
		return true;
	}

	private void checkNeighborTiles(int x, int y, List<Knoten> nachfolger) {
		/**
		 * Kopiere aktuelle Spielwelt in ein neues Array
		 */
		PacmanTileType[][] tiles = this.deepClone(this.world);

		/* Prüfe Tile von der aktuellen Position plus Wert der Parameter x und y*/
		PacmanTileType currentTile = tiles[getPosX()+x][getPosY()+y];

		tiles[getPosX()+x][getPosY()+y] = PacmanTileType.PACMAN;
		tiles[getPosX()][getPosY()] = PacmanTileType.EMPTY;

		if(currentTile == PacmanTileType.DOT)
			nachfolger.add(new Knoten(this, tiles, getPosX()+x, getPosY()+y,1));
		else if(currentTile == PacmanTileType.EMPTY)
			nachfolger.add(new Knoten(this, tiles, getPosX()+x, getPosY()+y,2));
	}

	public List<Knoten> expand() {
		/*
		 * TODO Praktikum 2 [2]: Implementiert in dieser Methode das Expandieren des Knotens.
		 * Die Methode soll die neu erzeugten Knoten (die Kinder des Knoten) zurückgeben.
		 */
		List<Knoten> nachfolger = new LinkedList<Knoten>();

		nachfolger.add(this);
		checkNeighborTiles(-1, 0, nachfolger);
		checkNeighborTiles(1, 0, nachfolger);
		checkNeighborTiles(0, -1, nachfolger);
		checkNeighborTiles(0, 1, nachfolger);

		return nachfolger;
	}

	@Override
	public String toString() {
		return "Pos X: " + getPosX() + " Pos Y: " + getPosY();
	}
}
