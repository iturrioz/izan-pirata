package models.logic;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Partida {
	private Map<Integer,Rejilla> rejillas = null;
	private int idPartida = -1;
	
	private int dimX = -1, dimY = -1;
	private List<Jugador> jugadores = null;
	private int indexTurno = 0;
	
	private String ultimoEvento = null;

    private EstadoPartida estadoPartida = EstadoPartida.EN_CURSO;
	
	public Partida(int idPartida){
		this.idPartida = idPartida;

		dimX = 3;
		dimY = 3;

		jugadores = new ArrayList<Jugador>();
		Jugador jug = null;
		jug = new Jugador();
		jug.setIdJugador(1);
		jugadores.add(jug);
		jug = new Jugador();
		jug.setIdJugador(2);
		jugadores.add(jug);
		
		initPartida();
	}

    public Partida(String json) {
        getFromJson(json);
    }
	
	private void initPartida(){
		rejillas = new HashMap<Integer,Rejilla>();
		for(int i=0;i<jugadores.size();i++){
			rejillas.put(jugadores.get(i).getIdJugador(), new Rejilla(dimX,dimY));
		}
	}
	
	public void disparar(int quien, int aQuien, int coordX, int coordY, Torpedo torpedo){
		
		Rejilla rejillaDestino = null;
		
		rejillaDestino = rejillas.get(aQuien);
		
		switch(rejillaDestino.getValoresRejilla()[coordX][coordY]){
			case AGUA	:	rejillaDestino.getValoresRejilla()[coordX][coordY] = EstadoPosicionRejilla.AGUA_TOCADA;
							this.setUltimoEvento(String.format("El disparo de %d ha caido en AGUA",quien));
							break;
			case BARCO	:	rejillaDestino.getValoresRejilla()[coordX][coordY] = EstadoPosicionRejilla.BARCO_TOCADO;
							this.setUltimoEvento(String.format("El disparo de %d ha TOCADO el barco de %d",aQuien));
							break;
			
			default		:	break;
		}
		
		indexTurno = (indexTurno+1) % jugadores.size();
	}
	
	public List<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public int getIndexTurno() {
		return indexTurno;
	}

	public void setIndexTurno(int indexTurno) {
		this.indexTurno = indexTurno;
	}

	public void colocarBarco(int quien, int tipoBarco, int coorX, int coorY, boolean horizontal){
		Rejilla rejillaDestino = null;
		
		rejillaDestino = rejillas.get(quien);
		
		if(horizontal){
			for(int x=coorX-tipoBarco;x<coorX+tipoBarco;x++){
				rejillaDestino.getValoresRejilla()[x][coorY] = EstadoPosicionRejilla.BARCO;
			}
		}
		else{
			for(int y=coorY-tipoBarco;y<coorY+tipoBarco;y++){
				rejillaDestino.getValoresRejilla()[coorX][y] = EstadoPosicionRejilla.BARCO;
			}
		}
	}

	public int getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}

	public Map<Integer, Rejilla> getRejillas() {
		return rejillas;
	}

	public void setRejillas(Map<Integer, Rejilla> rejillas) {
		this.rejillas = rejillas;
	}

	public String getUltimoEvento() {
		return ultimoEvento;
	}

	public void setUltimoEvento(String ultimoEvento) {
		this.ultimoEvento = ultimoEvento;
	}

    public String getJsonString() {
        final JSONObject retVal = new JSONObject();
        try {
            retVal.put("idPartida", idPartida);
            retVal.put("dimX", dimX);
            retVal.put("dimY", dimY);
            retVal.put("indexTurno", indexTurno);
            retVal.put("ultimoEvento", ultimoEvento);
            retVal.put("estadoPartida", estadoPartida.name());
            final List<JSONObject> jugadoresJson = new ArrayList<JSONObject>();
            for (Jugador jugador : jugadores) {
                final JSONObject jugadorObject = new JSONObject();
                int idJugador = jugador.getIdJugador();
                jugadorObject.put("id", idJugador);
                jugadorObject.put("rejilla", rejillas.get(idJugador).getJsonArray());
                jugadoresJson.add(jugadorObject);
            }
            final JSONArray jsonArray = new JSONArray(jugadoresJson);
            retVal.put("jugadores", jsonArray);
        } catch (JSONException e) { e.printStackTrace();}
        return retVal.toString();
    }

    private void getFromJson(String json) {
        try {
            final JSONObject obj = new JSONObject(json);
            idPartida = obj.getInt("idPartida");
            dimX = obj.getInt("dimX");
            dimY = obj.getInt("dimY");
            indexTurno = obj.getInt("indexTurno");
            ultimoEvento = obj.getString("ultimoEvento");
            estadoPartida = EstadoPartida.valueOf(obj.getString("estadoPartida"));
            final JSONArray jsonArray = obj.getJSONArray("jugadores");
            final Jugador[] jugadorArray = new Jugador[jsonArray.length()];
            rejillas = new HashMap<Integer,Rejilla>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jugadorArray[i] = new Jugador();
                jugadorArray[i].setIdJugador(jsonArray.getJSONObject(i).getInt("id"));
                String jsonRejilla = new JSONObject(jsonArray.getString(i)).getString("rejilla");
                rejillas.put(jugadorArray[i].getIdJugador(), new Rejilla(jsonRejilla));
            }
            jugadores = Arrays.asList(jugadorArray);
        } catch (JSONException e) { e.printStackTrace(); }
    }
}
