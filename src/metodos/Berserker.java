package metodos;

public class Berserker extends Personaje{
	public Berserker(Tipos tipo, int salud, int danyoBase, int estamina, int velocidad, boolean muerto) {
		super(tipo, salud, danyoBase, estamina, velocidad, muerto);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void habilidad(Personaje defensor) {
	    defensor.salud -= 40;
	}

}