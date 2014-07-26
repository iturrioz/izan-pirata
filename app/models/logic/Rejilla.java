package models.logic;

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
            for(int j=0;j<dimY;j++){
                valoresRejilla[i][j] = EstadoPosicionRejilla.AGUA;
            }
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

    public JSONArray getJsonArray() {
        final List<JSONArray> rejilla = new ArrayList<JSONArray>();
        for (EstadoPosicionRejilla[] fila : valoresRejilla) {
            final List<String> filaList = new ArrayList<String>();
            for (EstadoPosicionRejilla posicion : fila) {
                filaList.add(posicion.name());
            }
            final JSONArray jsonArray = new JSONArray(filaList);
            rejilla.add(jsonArray);
        }
        return new JSONArray(rejilla);
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
