package de.fh.stud.p3;

import de.fh.kiServer.agents.Agent;
import de.fh.kiServer.util.Util;
import de.fh.pacman.*;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p2.Knoten;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyAgent_P3 extends PacmanAgent_2021 {

	/**
	 * Die als nächstes auszuführende Aktion
	 */
	private PacmanAction nextAction;
	
	/**
	 * Der gefundene Lösungknoten der Suche
	 */
	private Knoten loesungsKnoten;
	private List<Knoten> loesungsweg;
	
	public MyAgent_P3(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		MyAgent_P3 agent = new MyAgent_P3("MyAgent");
		Agent.start(agent, "127.0.0.1", 5000);
	}

	/**
	 * @param percept - Aktuelle Wahrnehmung des Agenten, bspw. Position der Geister und Zustand aller Felder der Welt.
	 * @param actionEffect - Aktuelle Rückmeldung des Server auf die letzte übermittelte Aktion.
	 */
	@Override
	public PacmanAction action(PacmanPercept percept, PacmanActionEffect actionEffect) {
		
		//Gebe den aktuellen Zustand der Welt auf der Konsole aus
		Util.printView(percept.getView());
		
		//Wenn noch keine Lösung gefunden wurde, dann starte die Suche
		if (loesungsKnoten == null) {
			/*
			 * TODO Praktikum 3 [3]: Übergebt hier der Suche die notwendigen Informationen, um
			 * von einem Startzustand zu einem Zielzustand zu gelangen.
			 */
			Knoten start = new Knoten(percept.getView(), percept.getPosX(), percept.getPosY());
			Suche suche = new Suche(start);
			/*
			 * TODO Praktikum 4 [2]: Entscheidet hier welches Suchverfahren ausgeführt werden soll.
			 */
			loesungsKnoten = suche.start(5);
			loesungsweg = new ArrayList<>();
		}
		
		//Wenn die Suche eine Lösung gefunden hat, dann ermittle die als nächstes auszuführende Aktion
		if (loesungsKnoten != null) {
			/*
			 * TODO Praktikum 3 [4]: Ermittelt hier die als naechstes auszufuehrende Aktion,
			 * basierend auf dem loesungsknoten und weist diese nextAction zu.
			 */
			if(loesungsweg.isEmpty()){
				while (loesungsKnoten.getParent() != null){
					loesungsweg.add(loesungsKnoten);
					loesungsKnoten = loesungsKnoten.getParent();
				}
			}

			if(loesungsweg != null){
				Knoten temp = loesungsweg.remove(loesungsweg.size()-1);
				if (percept.getPosY() == temp.getPosY() && percept.getPosX() == temp.getPosX()) nextAction = PacmanAction.WAIT;
				if (percept.getPosY() < temp.getPosY() && percept.getPosX() == temp.getPosX()) nextAction = PacmanAction.GO_SOUTH;
				if (percept.getPosY() > temp.getPosY() && percept.getPosX() == temp.getPosX()) nextAction = PacmanAction.GO_NORTH;
				if (percept.getPosY() == temp.getPosY() && percept.getPosX() < temp.getPosX()) nextAction = PacmanAction.GO_EAST;
				if (percept.getPosY() == temp.getPosY() && percept.getPosX() > temp.getPosX()) nextAction = PacmanAction.GO_WEST;
			}
		}
		
		return nextAction;
	}

	@Override
	protected void onGameStart(PacmanStartInfo startInfo) {
		
	}

	@Override
	protected void onGameover(PacmanGameResult gameResult) {
		
	}
	
}
