package eus.willix.foss.battleship.IA;

import eus.willix.foss.battleship.logic.Partida;

public abstract class IA {
	
	protected int idJugador = -1;
	
	public IA(int idJugador){
		this.idJugador = idJugador;
	}
	
	public abstract void disparar(Partida partida);
}
