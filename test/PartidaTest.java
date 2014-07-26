import models.logic.Partida;
import models.logic.Torpedo;
import org.junit.*;

import static org.fest.assertions.Assertions.assertThat;

public class PartidaTest {
    @Test
    public void simpleCheck() {
        Partida partida = new Partida(0);
        partida.disparar(partida.getJugadores().get(0).getIdJugador(),partida.getJugadores().get(1).getIdJugador(), 0,0, Torpedo.NORMAL);
        partida.disparar(partida.getJugadores().get(1).getIdJugador(),partida.getJugadores().get(0).getIdJugador(), 0,1, Torpedo.NORMAL);
        partida.disparar(partida.getJugadores().get(0).getIdJugador(),partida.getJugadores().get(1).getIdJugador(), 2,0, Torpedo.NORMAL);
        String json = partida.getJsonString();
        System.out.println(json);
        Partida recovered = new Partida(json);
        assertThat(partida.getIdPartida()).isEqualTo(recovered.getIdPartida());
        assertThat(partida.getJugadores().size()).isEqualTo(recovered.getJugadores().size());
        assertThat(partida.getRejillas().values().size()).isEqualTo(recovered.getRejillas().values().size());
    }
}
