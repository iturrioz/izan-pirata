package models.logic;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import com.typesafe.plugin.RedisPlugin;
import play.libs.Json;

public class Partida {

    private Map<Integer,Rejilla> rejillas = null;
    private int idPartida = -1;
    private int dimX = -1, dimY = -1;
    private int indexTurno = 0;
    private List<Jugador> jugadores = null;
    private String ultimoEvento = null;
    private EstadoPartida estadoPartida = EstadoPartida.EN_CURSO;

    public static Partida load(int gameId) {
        Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
        try {
            //All messages are pushed through the pub/sub channel
            String strPartida = j.get(String.valueOf(gameId));
            return new Partida(strPartida);
        } catch (NullPointerException e) {
            System.out.println(e);
        } finally {
            play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
        }

        return null;
    }

    public Partida(int idPartida){
        this.idPartida = idPartida;

        jugadores = new ArrayList<Jugador>();
        Jugador jug = null;
        jug = new Jugador();
        jug.setIdJugador(0);
        jugadores.add(jug);
        jug = new Jugador();
        jug.setIdJugador(1);
        jugadores.add(jug);

        initPartida();
    }    
    
    
    public void store() {
        Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
        try {
            //All messages are pushed through the pub/sub channel
            j.set(""+this.idPartida, this.getJsonString());
            System.out.println("Stored");
        } finally {
            play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
        }
    }
    
    public void load() {
        Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
        try {
            //All messages are pushed through the pub/sub channel
            String strPartida = j.get(""+this.idPartida);
            this.getFromJson(strPartida);
            System.out.println("loaded");
        } catch (NullPointerException e) {
            System.out.println(e);
        } finally {
            play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
        }
    }

    public Partida(String json) {
        getFromJson(json);
    }
	
	private void initPartida(){
		rejillas = new HashMap<Integer,Rejilla>();
		for(int i=0;i<jugadores.size();i++){
			rejillas.put(jugadores.get(i).getIdJugador(), new Rejilla(dimX, dimY));
		}
	}
	
	public void disparar(int quien, int aQuien, int coordX, int coordY, Torpedo torpedo){
		
		Rejilla rejillaDestino = rejillas.get(aQuien);
		
		switch(rejillaDestino.getValoresRejilla()[coordX][coordY]){
			case AGUA	:	rejillaDestino.getValoresRejilla()[coordX][coordY] = EstadoPosicionRejilla.AGUA_TOCADA;
							setUltimoEvento(String.format("El disparo de %d ha caido en AGUA",quien));
							break;
			case BARCO	:	rejillaDestino.getValoresRejilla()[coordX][coordY] = EstadoPosicionRejilla.BARCO_TOCADO;

                            Barco barco = comprobarBarcoHundido(aQuien, coordX, coordY);
                            if (barco != null && barco.hundido()) {
                                for (int i=0; i<barco.tipoBarco; i++) {
                                    int x = barco.horizontal ? barco.coorX + i : barco.coorX;
                                    int y = barco.horizontal ? barco.coorY : barco.coorY + i;
                                    rejillaDestino.getValoresRejilla()[x][y] = EstadoPosicionRejilla.BARCO_HUNDIDO;
                                }
                                Barco[] barcos = jugadores.get(aQuien).getBarcos();
                                boolean finalizado = true;
                                for (Barco barco1 : barcos) {
                                    finalizado = barco1.hundido() && finalizado;
                                }
                                if (finalizado) {
                                    estadoPartida = EstadoPartida.FINALIZADO;
                                    setUltimoEvento(String.format("El disparo de %d ha HUNDIDO el barco de %d. %d gana!",aQuien, quien));
                                } else {
                                    setUltimoEvento(String.format("El disparo de %d ha HUNDIDO el barco de %d",aQuien));
                                }
                            } else {
                                setUltimoEvento(String.format("El disparo de %d ha TOCADO el barco de %d",aQuien));
                            }
							break;
			
			default		:	break;
		}
		
		indexTurno = (indexTurno+1) % jugadores.size();
	}

    private Barco comprobarBarcoHundido(int aQuien, int coordX, int coordY) {
        Jugador jugador = jugadores.get(aQuien);
        return jugador.comprobarBarcoHundido(coordX, coordY);
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

	public void colocarBarco(int quien, Barco barco){
		Rejilla rejillaDestino = null;
		
		rejillaDestino = rejillas.get(quien);
		
		if(barco.horizontal){
			for(int x=barco.coorX-barco.tipoBarco;x<barco.coorX+barco.tipoBarco;x++){
				rejillaDestino.getValoresRejilla()[x][barco.coorY] = EstadoPosicionRejilla.BARCO;
			}
		}
		else{
			for(int y=barco.coorY-barco.tipoBarco;y<barco.coorY+barco.tipoBarco;y++){
				rejillaDestino.getValoresRejilla()[barco.coorX][y] = EstadoPosicionRejilla.BARCO;
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
        System.out.println(json);
        try {
            final JSONObject obj = new JSONObject(json);
            idPartida = obj.getInt("idPartida");
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
