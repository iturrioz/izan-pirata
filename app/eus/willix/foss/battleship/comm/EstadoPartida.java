package eus.willix.foss.battleship.comm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import eus.willix.foss.battleship.logic.Partida;
import eus.willix.foss.battleship.logic.Rejilla;

public class EstadoPartida implements Serializable{
	private int idPartida = -1;
	private List<JugadorRejilla> rejillas = null;
	private String ultimoEvento = null;
	private int siguienteJugador = -1;

	public EstadoPartida(Partida partida){
		fill(partida);
	}
	
	private void fill(Partida partida){
		
		List<JugadorRejilla> rejillas = null;
		JugadorRejilla jugadorRejilla = null;
		
		setIdPartida(partida.getIdPartida());
		
		rejillas = new ArrayList<JugadorRejilla>();
		
		for(Entry<Integer,Rejilla> rejilla : partida.getRejillas().entrySet()){
			jugadorRejilla = new JugadorRejilla();
			jugadorRejilla.setIdJugador(rejilla.getKey());
			jugadorRejilla.setRejilla(rejilla.getValue());
		
			rejillas.add(jugadorRejilla);
		}
				
		setUltimoEvento(partida.getUltimoEvento());
	
		this.setSiguienteJugador(partida.getJugadores().get(partida.getIndexTurno()).getIdJugador());
	}
		
	public int getIdPartida() {
		return idPartida;
	}
	
	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}

	public List<JugadorRejilla> getRejillas() {
		return rejillas;
	}

	public void setRejillas(List<JugadorRejilla> rejillas) {
		this.rejillas = rejillas;
	}

	public String getUltimoEvento() {
		return ultimoEvento;
	}

	public void setUltimoEvento(String ultimoEvento) {
		this.ultimoEvento = ultimoEvento;
	}

	public EstadoPartida(int idPartida, List<JugadorRejilla> rejillas,
			String ultimoEvento) {
		super();
		this.idPartida = idPartida;
		this.rejillas = rejillas;
		this.ultimoEvento = ultimoEvento;
	}
	
	

	public int getSiguienteJugador() {
		return siguienteJugador;
	}

	public void setSiguienteJugador(int siguienteJugador) {
		this.siguienteJugador = siguienteJugador;
	}



	public class JugadorRejilla{
		private int idJugador = -1;
		private Rejilla rejilla = null;
		
		public JugadorRejilla(){
			
		}

		public JugadorRejilla(int idJugador, Rejilla rejilla) {
			super();
			this.idJugador = idJugador;
			this.rejilla = rejilla;
		}

		public int getIdJugador() {
			return idJugador;
		}

		public void setIdJugador(int idJugador) {
			this.idJugador = idJugador;
		}

		public Rejilla getRejilla() {
			return rejilla;
		}

		public void setRejilla(Rejilla rejilla) {
			this.rejilla = rejilla;
		}

		@Override
		public String toString() {
			return "JugadorRejilla [idJugador=" + idJugador + ", rejilla="
					+ rejilla + "]";
		}
	}
}
