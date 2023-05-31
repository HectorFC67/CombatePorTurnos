package metodos;

public class Partida {
	private String usuario;
    private int partidasJugadas;
    private int victorias;
    private int derrotas;

    public Partida(String usuario, int partidasJugadas, int victorias, int derrotas) {
        this.usuario = usuario;
        this.partidasJugadas = partidasJugadas;
        this.victorias = victorias;
        this.derrotas = derrotas;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public int getVictorias() {
        return victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }
}
