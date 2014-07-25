package eus.willix.foss.battleship.logic;

public class Rejilla {
	private EstadoPosicionRejilla[][] valoresRejilla = null;
	
	public Rejilla(int dimX, int dimY){
		valoresRejilla = new EstadoPosicionRejilla[dimX][];
		for(int i=0;i<dimX;i++){
			valoresRejilla[i] = new EstadoPosicionRejilla[dimY];
		}	
	}

	public EstadoPosicionRejilla[][] getValoresRejilla() {
		return valoresRejilla;
	}

	public void setValoresRejilla(EstadoPosicionRejilla[][] valoresRejilla) {
		this.valoresRejilla = valoresRejilla;
	}
	
	
}
