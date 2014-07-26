package models.logic;

public class Barco {

    final int tipoBarco;
    final int coorX;
    final int coorY;
    final boolean horizontal;

    private int contador;

    public Barco(int tipoBarco, int coorX, int coorY, boolean horizontal) {
        this.tipoBarco = tipoBarco;
        this.coorX = coorX;
        this.coorY = coorY;
        this.horizontal = horizontal;
        contador = tipoBarco;
    }

    public Barco pegarBarco(int tiroX, int tiroY) {
        for (int i=0; i<tipoBarco; i++) {
            int x = horizontal ? coorX + i : coorX;
            int y = horizontal ? coorY : coorY + i;

            if (x == tiroX && y == tiroY) {
                contador--;
                return this;
            }
        }
        return null;
    }

    public boolean hundido() {
        return contador == 0;
    }
}
