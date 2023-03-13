package metodos;

public class Personaje {

	private Tipos tipo;
	private int salud;
	private int danyoBase;
	private int estamina;
	private int velocidad;
	private boolean prioridad = false;
	private boolean prioridadAtaqueAnterior = false;
	private boolean ataque = true;

	public Personaje(Tipos tipo, int salud, int danyoBase, int estamina, int velocidad) {
		this.tipo = tipo;
		this.salud = salud;
		this.danyoBase = danyoBase;
		this.estamina = estamina;
		this.velocidad = velocidad;
	}

	public Tipos getTipo() {
		return tipo;
	}

	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}

	public int getSalud() {
		return salud;
	}

	public void setSalud(int salud) {
		this.salud = salud;
	}

	public int getdanyoBase() {
		return danyoBase;
	}

	public void setdanyoBase(int danyoBase) {
		this.danyoBase = danyoBase;
	}

	public int getEstamina() {
		return estamina;
	}

	public void setEstamina(int estamina) {
		this.estamina = estamina;
	}
	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public void calcularDanyo(Personaje defensor) {

		int danyo = this.danyoBase;
		Tipos tipoDefensor = defensor.getTipo();

		if (this.tipo == Tipos.BERSERKER && tipoDefensor == Tipos.TANQUE) 
		{
			danyo = danyo + danyo/2;
		} else if (this.tipo == Tipos.TANQUE && tipoDefensor == Tipos.BALISTICA) 
		{
			danyo = danyo + danyo/2;
		} else if (this.tipo == Tipos.BALISTICA && tipoDefensor == Tipos.BERSERKER) 
		{
			danyo = danyo + danyo/2;
		} else if (this.tipo == Tipos.BERSERKER && tipoDefensor == Tipos.BALISTICA) 
		{
			danyo = danyo - danyo/2;
		} else if (this.tipo == Tipos.TANQUE && tipoDefensor == Tipos.BERSERKER) 
		{
			danyo = danyo - danyo/2;
		} else if (this.tipo == Tipos.BALISTICA && tipoDefensor == Tipos.TANQUE) 
		{
			danyo = danyo - danyo/2;
		}
		defensor.salud -= danyo;
	}
	public void descansar() {
		this.estamina ++;
	}
	public boolean puedeAtacar() {
		if(this.estamina > 0) {
			ataque = true;
		}else {
			ataque = false;
		}
		
		return ataque;
	}

	public boolean prioridadAtaque(Personaje defensor) {
		int velocidadMia = this.velocidad;
		int velocidadRival = defensor.getVelocidad();

		if(velocidadMia > velocidadRival) {
			prioridad = true;
			prioridadAtaqueAnterior = true;
		}else if(velocidadMia < velocidadRival) {
			prioridad = false;
			prioridadAtaqueAnterior = false;
		}else if(velocidadMia == velocidadRival){
			int estaminaMia = this.estamina;
			int estaminaRival = defensor.getEstamina();
			if(estaminaMia > estaminaRival) {
				prioridad = true;
				prioridadAtaqueAnterior = true;
			}else if(estaminaMia < estaminaRival) {
				prioridad = false;
				prioridadAtaqueAnterior = false;
			}else if (estaminaMia == estaminaRival) {
				if(prioridadAtaqueAnterior == false) {
					prioridad = true;
					prioridadAtaqueAnterior = true;
				}else if(prioridadAtaqueAnterior == true) {
					prioridad = false;
					prioridadAtaqueAnterior = false;
				}
			}
		}
		return prioridad;
	}
}