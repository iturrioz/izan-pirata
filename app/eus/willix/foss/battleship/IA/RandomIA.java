package eus.willix.foss.battleship.IA;

import java.util.List;
import java.util.Random;

import eus.willix.foss.battleship.logic.EstadoPosicionRejilla;
import eus.willix.foss.battleship.logic.Jugador;
import eus.willix.foss.battleship.logic.Partida;
import eus.willix.foss.battleship.logic.Rejilla;
import eus.willix.foss.battleship.logic.Torpedo;

public class RandomIA extends IA{

	Random rnd = null;
	
	public RandomIA(int idJugador) {
		super(idJugador);
		rnd = new Random();
	}

	@Override
	public void disparar(Partida partida) {
		List<Jugador> jugadores = null;
		Rejilla rejillaEnemiga = null;
		int maxX, maxY;
		int rndX, rndY;
		
		jugadores = partida.getJugadores();
		
		
		for(Jugador jugador : jugadores){
			if(jugador.getIdJugador() != idJugador){
				
				rejillaEnemiga = partida.getRejillas().get(jugador.getIdJugador());
				maxX = rejillaEnemiga.getValoresRejilla().length;
				maxY = rejillaEnemiga.getValoresRejilla()[0].length;
				
				do{
					rndX = rnd.nextInt(maxX);
					rndY = rnd.nextInt(maxY);
				}while(rejillaEnemiga.getValoresRejilla()[rndX][rndY] != EstadoPosicionRejilla.AGUA);
				
				partida.disparar(this.idJugador, jugador.getIdJugador(), rndX, rndY, Torpedo.NORMAL);
				
				break;
			}
		}
	}

}
