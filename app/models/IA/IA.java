package models.IA;

import models.logic.Partida;

public abstract class IA {
	
	protected int idJugador = -1;
	
	public IA(int idJugador){
		this.idJugador = idJugador;
	}
	
	public abstract void disparar(Partida partida);
}
