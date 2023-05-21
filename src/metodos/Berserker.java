package metodos;

public class Berserker extends Personaje{
	
	int aumentoDanyo = 2;
	int aumentoEstamina = 2;
	
	public Berserker(Tipos tipo, int salud, int danyoBase, int estamina, int velocidad, boolean muerto) {
		super(tipo, salud, danyoBase, estamina, velocidad, muerto);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void habilidad() {
	    this.danyoBase *= aumentoDanyo;
	    
	    this.estamina += aumentoEstamina;
	    
	    System.out.println("¡El Berserker desata su furia! Aumenta su daño base y estamina.");
	}

}