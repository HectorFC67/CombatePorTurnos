package metodos;

public class Tanque extends Personaje{
	public int recuperacionSalud = 20;
	public Tanque(Tipos tipo, int salud, int danyoBase, int estamina, int velocidad, boolean muerto) {
		super(tipo, salud, danyoBase, estamina, velocidad, muerto);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void habilidad(Personaje defensor) {
		// TODO Auto-generated method stub
		defensor.salud -= 15;
		defensor.estamina -= 2;
		this.estamina--;
	}
}