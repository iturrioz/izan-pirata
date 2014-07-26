package models.logic;

public class Jugador {
	private int idJugador = -1;
    private Barco[] barcos;

	public int getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}

    public Barco[] getBarcos() {
        return barcos;
    }

    public void setBarcos(Barco[] barcos) {
        this.barcos = barcos;
    }

    public Barco comprobarBarcoHundido(int coordX, int coordY) {
        for (Barco barco : barcos) {
            Barco barcoTocado = barco.pegarBarco(coordX, coordY);
            if (barcoTocado != null) {
                return barcoTocado;
            }
        }
        return null;
    }
}
