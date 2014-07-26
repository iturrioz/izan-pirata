package eus.willix.foss.battleship.logic;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Rejilla {
	private EstadoPosicionRejilla[][] valoresRejilla = null;
	
	public Rejilla(int dimX, int dimY){
		valoresRejilla = new EstadoPosicionRejilla[dimX][];
		for(int i=0;i<dimX;i++){
			valoresRejilla[i] = new EstadoPosicionRejilla[dimY];
		}	
	}

    public Rejilla(String json) {
        valoresRejilla = getFromJson(json);
    }

	public EstadoPosicionRejilla[][] getValoresRejilla() {
		return valoresRejilla;
	}

	public void setValoresRejilla(EstadoPosicionRejilla[][] valoresRejilla) {
		this.valoresRejilla = valoresRejilla;
	}

    public String getJsonString() {
        final List<String> rejilla = new ArrayList<String>();
        for (EstadoPosicionRejilla[] fila : valoresRejilla) {
            final List<String> filaList = new ArrayList<String>();
            for (EstadoPosicionRejilla posicion : fila) {
                filaList.add(posicion.name());
            }
            final JSONArray jsonArray = new JSONArray(filaList);
            rejilla.add(jsonArray.toString());
        }
        return new JSONArray(rejilla).toString();
    }

    private EstadoPosicionRejilla[][] getFromJson(String json) {
        EstadoPosicionRejilla[][] rejillas = null;
        try {
            final JSONArray jsonArray = new JSONArray(json);
            rejillas = new EstadoPosicionRejilla[jsonArray.length()][];
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONArray jsonArrayFila = new JSONArray(jsonArray.getString(i));
                rejillas[i] = new EstadoPosicionRejilla[jsonArrayFila.length()];
                for (int j = 0; j < jsonArrayFila.length(); j++) {
                    rejillas[i][j] = EstadoPosicionRejilla.valueOf(jsonArrayFila.getString(i));
                }
            }
        } catch (JSONException e) { e.printStackTrace(); }
        return rejillas;
    }
}
