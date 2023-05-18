package metodos;

public class Balistica extends Personaje{
	int aumentoDanyo = 10;
	public Balistica(Tipos tipo, int salud, int danyoBase, int estamina, int velocidad, boolean muerto) {
		super(tipo, salud, danyoBase, estamina, velocidad, muerto);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void habilidad() {
	    this.danyoBase += aumentoDanyo;
	    
	    System.out.println("¡La Balistica realiza un disparo preciso! Inflige daño adicional.");
	}

}
