package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import metodos.Personaje;
import metodos.Tipos;

public class VentanaCombate extends JFrame{
	private String imgFondo = "";

	private Personaje berserkerAliado = new Personaje(Tipos.BERSERKER, 150, 20, 4, 5, false);
	private Personaje balisticaAliado = new Personaje(Tipos.BALISTICA, 100, 30, 5, 7, false);
	private Personaje tanqueAliado = new Personaje(Tipos.TANQUE, 200, 10, 6, 2, false);

	private Personaje berserkerEnemigo = new Personaje(Tipos.BERSERKER, 150, 20, 4, 5, false);
	private Personaje balisticaEnemigo = new Personaje(Tipos.BALISTICA, 100, 30, 5, 7, false);
	private Personaje tanqueEnemigo = new Personaje(Tipos.TANQUE, 200, 10, 6, 2, false);

	private JButton boton1 = new JButton();
	private JButton boton2 = new JButton();

	private int nuevaAltura = 256;
	private int nuevoAncho = 270;
	
	private Personaje[] listaPersonajesAliados;
	private Personaje[] listaPersonajesEnemigos;
	
	private JLabel fondo;
	
    private JProgressBar pbSaludAliado;
    private JProgressBar pbEstaminaAliado;
    private JProgressBar pbSaludEnemigo;
    private JProgressBar pbEstaminaEnemigo;
    
    private static int saludMaximaBerserker = 150;
    private static int saludMaximaBalistica = 100;
    private static int saludMaximaTanque = 200;
    
    private static int estaminaMaximaBerserker = 4;
    private static int estaminaMaximaBalistica = 6;
    private static int estaminaMaximaTanque = 8;
    
    private int saludMaximaAliado = 0;
    private int saludAliado = 0;
    private int saludMaximaEnemigo = 0;
    private int saludEnemigo = 0;
    private int estaminaMaximaAliado = 0;
    private int estaminaAliado = 0;
    private int estaminaMaximaEnemigo = 0;
    private int estaminaEnemigo = 0;
    
    private boolean muerto = false;
    
    private boolean turnoAliado = true;
    private boolean turnoEnemigo = false;
    
	private static int contadorPartidas = 0;
	
	private boolean primeraVez = true;
	
	public VentanaCombate(String[] equipoSeleccionadoList, String[] equipoNoSeleccionadoList) {
		super("Juego de combate");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelBarrasAliado = new JPanel();
		pbSaludAliado = new JProgressBar();
		pbSaludAliado.setForeground(new Color(0, 255, 0));
		pbEstaminaAliado = new JProgressBar();
		pbEstaminaAliado.setForeground(new Color(75, 0, 130));
		panelBarrasAliado.add(pbSaludAliado);
		panelBarrasAliado.add(pbEstaminaAliado);
		
		JPanel panelBarrasEnemigo = new JPanel();
		pbSaludEnemigo = new JProgressBar();
		pbSaludEnemigo.setForeground(new Color(255, 0, 0));
		pbEstaminaEnemigo = new JProgressBar();
		pbEstaminaEnemigo.setForeground(new Color(75, 0, 130));
		panelBarrasEnemigo.add(pbSaludEnemigo);
		panelBarrasEnemigo.add(pbEstaminaEnemigo);
		
		JPanel panelBarras = new JPanel();
		panelBarras.add(panelBarrasAliado, BorderLayout.WEST);
		panelBarras.add(panelBarrasEnemigo, BorderLayout.EAST);
		
		add(panelBarras, BorderLayout.NORTH);
		
		imgFondo = emparejamiento(equipoSeleccionadoList, equipoNoSeleccionadoList);

		fondo = new JLabel(new ImageIcon(imgFondo));
		fondo.setSize(800, 500);
		add(fondo);

		if(primeraVez == true) {
			inicializacionTurnos(turnoAliado, turnoEnemigo);
			primeraVez = false;
		}else {
			turnos(turnoAliado, turnoEnemigo);
		}
		listaPersonajesAliados[0].isMuerto(listaPersonajesAliados[0], muerto);
		
		
		JPanel panelAliado = new JPanel();

		JButton atacarAliado = new JButton("Atacar");
		JButton descansarAliado = new JButton("Descansar");
		JButton cambiarPersonajeAliado = new JButton("Cambiar Guerrero");
		
		atacarAliado.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				if(turnoAliado == true) {
					listaPersonajesAliados[0].calcularDanyo(listaPersonajesEnemigos[0]);
					pbSaludEnemigo.setValue(listaPersonajesEnemigos[0].getSalud());
					pbEstaminaAliado.setValue(listaPersonajesAliados[0].getEstamina());
				}else if(turnoAliado == false) {
					JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno del jugador 2.");
				}
				
			}
		});
		descansarAliado.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				if(turnoAliado == true) {
					listaPersonajesAliados[0].descansar();
					pbEstaminaAliado.setValue(listaPersonajesAliados[0].getEstamina());
				}
				
				else if(turnoAliado == false) {
					JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno del jugador 2.");
				}
			}
		});
		cambiarPersonajeAliado.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
					if(turnoAliado == true) {
						ventanaCambiarPersonajeAliado(equipoSeleccionadoList, equipoNoSeleccionadoList);
					}
					
					else if(turnoAliado == false) {
						JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno del jugador 2.");
					}
				
			}
		});

		panelAliado.add(atacarAliado);
		panelAliado.add(descansarAliado);
		panelAliado.add(cambiarPersonajeAliado);

		JPanel panelEnemigo = new JPanel();

		JButton atacarEnemigo = new JButton("Atacar");
		JButton descansarEnemigo = new JButton("Descansar");
		JButton cambiarPersonajeEnemigo = new JButton("Cambiar Guerrero");

		atacarEnemigo.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				if(turnoEnemigo == true) {
					listaPersonajesEnemigos[0].calcularDanyo(listaPersonajesAliados[0]);
					pbSaludAliado.setValue(listaPersonajesAliados[0].getSalud());
					pbEstaminaEnemigo.setValue(listaPersonajesEnemigos[0].getEstamina());					
				}else if(turnoEnemigo == false) {
					JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno del jugador 1.");
				}
			}
		});
		descansarEnemigo.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				if(turnoEnemigo == true) {
					listaPersonajesEnemigos[0].descansar();
					pbEstaminaEnemigo.setValue(listaPersonajesEnemigos[0].getEstamina());
				}else if(turnoEnemigo == false) {
					JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno del jugador 1.");
				}
			}
		});
		cambiarPersonajeEnemigo.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				if(turnoEnemigo == true) {
					ventanaCambiarPersonajeEnemigo(equipoSeleccionadoList, equipoNoSeleccionadoList);
				}
				
				else if(turnoEnemigo == false) {
					JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno del jugador 1.");
				}
				
			}
		});

		panelEnemigo.add(atacarEnemigo);
		panelEnemigo.add(descansarEnemigo);
		panelEnemigo.add(cambiarPersonajeEnemigo); 

		JPanel panelBotones = new JPanel( new BorderLayout() );

		panelBotones.add(panelAliado, BorderLayout.WEST);
		panelBotones.add(panelEnemigo, BorderLayout.EAST);

		add(panelBotones, BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}


	private String emparejamiento(String[] equipoSeleccionadoList, String[] equipoNoSeleccionadoList) 
	{
		listaPersonajesAliados = new Personaje[3];
		listaPersonajesEnemigos = new Personaje[3];
		
		String primerJugadorSeleccionado = equipoSeleccionadoList[0].toLowerCase();
		String primerJugadorNoSeleccionado = equipoNoSeleccionadoList[0].toLowerCase();
		String segundoJugadorSeleccionado = equipoSeleccionadoList[1].toUpperCase();
		String segundoJugadorNoSeleccionado = equipoNoSeleccionadoList[1].toUpperCase();
		//String tercerJugadorSeleccionado = equipoSeleccionadoList[2].toUpperCase();
		//String tercerJugadorNoSeleccionado = equipoNoSeleccionadoList[2].toUpperCase();
		
		if (primerJugadorSeleccionado.equals("berserker") && primerJugadorNoSeleccionado.equals("berserker")) {

			imgFondo = "img/combate-berserker-berserker.png";
			listaPersonajesAliados[0] = berserkerAliado;
			listaPersonajesEnemigos[0] = berserkerEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaBerserker;
			saludMaximaEnemigo = saludMaximaBerserker;
			estaminaMaximaAliado = estaminaMaximaBerserker;
			estaminaMaximaEnemigo = estaminaMaximaBerserker;
			
			
			if(segundoJugadorSeleccionado.equals(balisticaAliado.getTipo().name())) {
				listaPersonajesAliados[1] = balisticaAliado;
				listaPersonajesAliados[2] = tanqueAliado;
			}else if(segundoJugadorNoSeleccionado.equals(balisticaEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = balisticaEnemigo;
				listaPersonajesEnemigos[2] = tanqueEnemigo;
			}else if(segundoJugadorSeleccionado.equals(tanqueAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = balisticaAliado;
			}else if(segundoJugadorNoSeleccionado.equals(tanqueEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = tanqueEnemigo;
				listaPersonajesEnemigos[2] = balisticaEnemigo;
			}

		} else if (primerJugadorSeleccionado.equals("balistica") && primerJugadorNoSeleccionado.equals("balistica")) {

			imgFondo = "img/combate-balistica-balistica.png";
			listaPersonajesAliados[0] = balisticaAliado;
			listaPersonajesEnemigos[0] = balisticaEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaBalistica;
			saludMaximaEnemigo = saludMaximaBalistica;
			estaminaMaximaAliado = estaminaMaximaBalistica;
			estaminaMaximaEnemigo = estaminaMaximaBalistica;
			
			if(segundoJugadorSeleccionado.equals(berserkerAliado.getTipo().name())) {
				listaPersonajesAliados[1] = berserkerAliado;
				listaPersonajesAliados[2] = tanqueAliado;
			}else if(segundoJugadorNoSeleccionado.equals(berserkerEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = berserkerEnemigo;
				listaPersonajesEnemigos[2] = tanqueEnemigo;
			}else if(segundoJugadorSeleccionado.equals(tanqueAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = berserkerAliado;
			}else if(segundoJugadorNoSeleccionado.equals(tanqueEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = tanqueEnemigo;
				listaPersonajesEnemigos[2] = berserkerEnemigo;
			}

		} else if (primerJugadorSeleccionado.equals("tanque") && primerJugadorNoSeleccionado.equals("tanque")) {

			imgFondo = ("img/combate-tanque-tanque.png");
			listaPersonajesAliados[0] = tanqueAliado;
			listaPersonajesEnemigos[0] = tanqueEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaTanque;
			saludMaximaEnemigo = saludMaximaTanque;
			estaminaMaximaAliado = estaminaMaximaTanque;
			estaminaMaximaEnemigo = estaminaMaximaTanque;
			
			if(segundoJugadorSeleccionado.equals(berserkerAliado.getTipo().name())) {
				listaPersonajesAliados[1] = berserkerAliado;
				listaPersonajesAliados[2] = balisticaAliado;
			}else if(segundoJugadorNoSeleccionado.equals(berserkerEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = berserkerEnemigo;
				listaPersonajesEnemigos[2] = balisticaEnemigo;
			}else if(segundoJugadorSeleccionado.equals(balisticaAliado.getTipo().name())) {
				listaPersonajesAliados[1] = balisticaAliado;
				listaPersonajesAliados[2] = berserkerAliado;
			}else if(segundoJugadorNoSeleccionado.equals(balisticaEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = balisticaEnemigo;
				listaPersonajesEnemigos[2] = berserkerEnemigo;
			}

		} else if (primerJugadorSeleccionado.equals("berserker") && primerJugadorNoSeleccionado.equals("balistica")) {

			imgFondo = ("img/combate-berserker-balistica.png");
			listaPersonajesAliados[0] = berserkerAliado;
			listaPersonajesEnemigos[0] = balisticaEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaBerserker;
			saludMaximaEnemigo = saludMaximaBalistica;
			estaminaMaximaAliado = estaminaMaximaBerserker;
			estaminaMaximaEnemigo = estaminaMaximaBalistica;
			
			if(segundoJugadorSeleccionado.equals(balisticaAliado.getTipo().name())) {
				listaPersonajesAliados[1] = balisticaAliado;
				listaPersonajesAliados[2] = tanqueAliado;
			}else if(segundoJugadorNoSeleccionado.equals(berserkerEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = berserkerEnemigo;
				listaPersonajesEnemigos[2] = tanqueEnemigo;
			}else if(segundoJugadorSeleccionado.equals(tanqueAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = balisticaAliado;
			}else if(segundoJugadorNoSeleccionado.equals(tanqueEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = tanqueEnemigo;
				listaPersonajesEnemigos[2] = berserkerEnemigo;
			}
			
		} else if (primerJugadorSeleccionado.equals("berserker") && primerJugadorNoSeleccionado.equals("tanque")) {

			imgFondo = "img/combate-berserker-tanque.png";
			listaPersonajesAliados[0] = berserkerAliado;
			listaPersonajesEnemigos[0] = tanqueEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaBerserker;
			saludMaximaEnemigo = saludMaximaTanque;
			estaminaMaximaAliado = estaminaMaximaBerserker;
			estaminaMaximaEnemigo = estaminaMaximaTanque;
			
			if(segundoJugadorSeleccionado.equals(balisticaAliado.getTipo().name())) {
				listaPersonajesAliados[1] = balisticaAliado;
				listaPersonajesAliados[2] = tanqueAliado;
			}else if(segundoJugadorNoSeleccionado.equals(berserkerEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = berserkerEnemigo;
				listaPersonajesEnemigos[2] = balisticaEnemigo;
			}else if(segundoJugadorSeleccionado.equals(tanqueAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = balisticaAliado;
			}else if(segundoJugadorNoSeleccionado.equals(balisticaEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = balisticaEnemigo;
				listaPersonajesEnemigos[2] = berserkerEnemigo;
			}

		} else if (primerJugadorSeleccionado.equals("balistica") && primerJugadorNoSeleccionado.equals("tanque")) {

			imgFondo = "img/combate-balistica-tanque.png";
			listaPersonajesAliados[0] = balisticaAliado;
			listaPersonajesEnemigos[0] = tanqueEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaBalistica;
			saludMaximaEnemigo = saludMaximaTanque;
			estaminaMaximaAliado = estaminaMaximaBalistica;
			estaminaMaximaEnemigo = estaminaMaximaTanque;
			
			if(segundoJugadorSeleccionado.equals(berserkerAliado.getTipo().name())) {
				listaPersonajesAliados[1] = berserkerAliado;
				listaPersonajesAliados[2] = tanqueAliado;
			}else if(segundoJugadorNoSeleccionado.equals(balisticaEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = balisticaEnemigo;
				listaPersonajesEnemigos[2] = berserkerEnemigo;
			}else if(segundoJugadorSeleccionado.equals(tanqueAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = berserkerAliado;
			}else if(segundoJugadorNoSeleccionado.equals(berserkerEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = berserkerEnemigo;
				listaPersonajesEnemigos[2] = balisticaEnemigo;
			}

		} else if ( primerJugadorSeleccionado.equals("balistica") && primerJugadorNoSeleccionado.equals("berserker")) {

			imgFondo = "img/combate-balistica-berserker.png";
			listaPersonajesAliados[0] = balisticaAliado;
			listaPersonajesEnemigos[0] = berserkerEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaBalistica;
			saludMaximaEnemigo = saludMaximaBerserker;
			estaminaMaximaAliado = estaminaMaximaBalistica;
			estaminaMaximaEnemigo = estaminaMaximaBerserker;
			
			if(segundoJugadorSeleccionado.equals(berserkerAliado.getTipo().name())) {
				listaPersonajesAliados[1] = berserkerAliado;
				listaPersonajesAliados[2] = tanqueAliado;
			}else if(segundoJugadorNoSeleccionado.equals(balisticaEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = balisticaEnemigo;
				listaPersonajesEnemigos[2] = tanqueEnemigo;
			}else if(segundoJugadorSeleccionado.equals(tanqueAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = berserkerAliado;
			}else if(segundoJugadorNoSeleccionado.equals(tanqueEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = tanqueEnemigo;
				listaPersonajesEnemigos[2] = balisticaEnemigo;
			}

		} else if (primerJugadorSeleccionado.equals("tanque") && primerJugadorNoSeleccionado.equals("berserker")) {

			imgFondo = "img/combate-tanque-berserker.png";
			listaPersonajesAliados[0] = tanqueAliado;
			listaPersonajesEnemigos[0] = berserkerEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaTanque;
			saludMaximaEnemigo = saludMaximaBerserker;
			estaminaMaximaAliado = estaminaMaximaTanque;
			estaminaMaximaEnemigo = estaminaMaximaBerserker;
			
			if(segundoJugadorSeleccionado.equals(balisticaAliado.getTipo().name())) {
				listaPersonajesAliados[1] = balisticaAliado;
				listaPersonajesAliados[2] = berserkerAliado;
			}else if(segundoJugadorNoSeleccionado.equals(balisticaEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = balisticaEnemigo;
				listaPersonajesEnemigos[2] = tanqueEnemigo;
			}else if(segundoJugadorSeleccionado.equals(berserkerAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = balisticaAliado;
			}else if(segundoJugadorNoSeleccionado.equals(tanqueEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = tanqueEnemigo;
				listaPersonajesEnemigos[2] = balisticaEnemigo;
			}

		} else if (primerJugadorSeleccionado.equals("tanque") && primerJugadorNoSeleccionado.equals("balistica")) {

			imgFondo = "img/combate-tanque-balistica.png";
			listaPersonajesAliados[0] = tanqueAliado;
			listaPersonajesEnemigos[0] = balisticaEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaTanque;
			saludMaximaEnemigo = saludMaximaBalistica;
			estaminaMaximaAliado = estaminaMaximaTanque;
			estaminaMaximaEnemigo = estaminaMaximaBalistica;
			
			if(segundoJugadorSeleccionado.equals(balisticaAliado.getTipo().name())) {
				listaPersonajesAliados[1] = balisticaAliado;
				listaPersonajesAliados[2] = berserkerAliado;
			}else if(segundoJugadorNoSeleccionado.equals(berserkerEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = berserkerEnemigo;
				listaPersonajesEnemigos[2] = tanqueEnemigo;
			}else if(segundoJugadorSeleccionado.equals(berserkerAliado.getTipo().name())) {
				listaPersonajesAliados[1] = berserkerAliado;
				listaPersonajesAliados[2] = balisticaAliado;
			}else if(segundoJugadorNoSeleccionado.equals(tanqueEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = tanqueEnemigo;
				listaPersonajesEnemigos[2] = berserkerEnemigo;
			}
		} else {

			System.out.println("No se encontro la imagen de fondo");
		}
		establecerSalud(saludMaximaAliado, saludAliado, saludMaximaEnemigo, saludEnemigo);
		establecerEstamina(estaminaMaximaAliado, estaminaAliado, estaminaMaximaEnemigo, estaminaEnemigo);
		return imgFondo;
	}
	
	public void inicializacionTurnos(boolean turnoAliado, boolean turnoEnemigo) {
		if(listaPersonajesAliados[0].getVelocidad()>listaPersonajesEnemigos[0].getVelocidad() ) {
			turnoAliado = true;
			turnoEnemigo = false;
		}else if(listaPersonajesAliados[0].getVelocidad()<listaPersonajesEnemigos[0].getVelocidad() ) { 
			turnoAliado = false;
			turnoEnemigo = true;
		}else if (listaPersonajesAliados[0].getVelocidad() == listaPersonajesEnemigos[0].getVelocidad() ) {
			turnos(turnoAliado, turnoEnemigo);
		}
	}
	
	public void turnos(boolean turnoAliado, boolean turnoEnemigo) {
		if(turnoAliado == false) {
			turnoAliado = true;
			turnoEnemigo = false;
		}else {
			turnoAliado = false;
			turnoEnemigo = true;
		}
	}
	
	public void establecerSalud(int saludMaximaAliado, int saludAliado, int saludMaximaEnemigo, int saludEnemigo) {
		pbSaludAliado.setMaximum(saludMaximaAliado);
		pbSaludAliado.setValue(saludAliado);
		
		pbSaludEnemigo.setMaximum(saludMaximaEnemigo);
		pbSaludEnemigo.setValue(saludEnemigo);
	}
	
	public void establecerEstamina(int estaminaMaximaAliado, int estaminaAliado, int estaminaMaximaEnemigo, int estaminaEnemigo) {
		pbEstaminaAliado.setMaximum(estaminaMaximaAliado);
		pbEstaminaAliado.setValue(estaminaAliado);
		
		pbEstaminaEnemigo.setMaximum(estaminaMaximaEnemigo);
		pbEstaminaEnemigo.setValue(estaminaEnemigo);
	}
	
	public void ventanaCambiarPersonajeAliado(String[] equipoSeleccionadoList, String[] equipoNoSeleccionadoList) {
		// Crear la nueva ventana
		JDialog seleccionaPersonaje = new JDialog();
		// Configurar la ventana
		seleccionaPersonaje.setTitle("Selecciona Personaje");
		seleccionaPersonaje.setSize(600, 600);

		// Crear el panel de botones
		JPanel panelBotones = new JPanel();
		// Crear los botones
		String rutaBerserker = "img/berserker(local).png";
		String rutaBalistica = "img/balistica(local).png";
		String rutaTanque = "img/tanque(local).png";

		ImageIcon imagenOriginal = new ImageIcon(rutaBerserker);
		Image imagenEtiqueta = imagenOriginal.getImage();
		Image nuevaImagen = imagenEtiqueta.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon BerserkerImagen = new ImageIcon(nuevaImagen);

		ImageIcon imagenOriginal1 = new ImageIcon(rutaBalistica);
		Image imagenEtiqueta1 = imagenOriginal1.getImage();
		Image nuevaImagen1 = imagenEtiqueta1.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon BalisticaImagen = new ImageIcon(nuevaImagen1);

		ImageIcon imagenOriginal2 = new ImageIcon(rutaTanque);
		Image imagenEtiqueta2 = imagenOriginal2.getImage();
		Image nuevaImagen2 = imagenEtiqueta2.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon TanqueImagen = new ImageIcon(nuevaImagen2);

		if (equipoSeleccionadoList[1].toLowerCase().equals("berserker") && equipoSeleccionadoList[2].toLowerCase().equals("balistica")) {
			boton1 = new JButton("Selecciona Personaje 1", BerserkerImagen);
			boton2 = new JButton("Selecciona Personaje 2", BalisticaImagen);
		} else if(equipoSeleccionadoList[1].toLowerCase().equals("berserker") && equipoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			boton1 = new JButton("Selecciona Personaje 1", BerserkerImagen);
			boton2 = new JButton("Selecciona Personaje 2", TanqueImagen);
		} else if(equipoSeleccionadoList[1].toLowerCase().equals("balistica") && equipoSeleccionadoList[2].toLowerCase().equals("berserker")) {
			boton1 = new JButton("Selecciona Personaje 1", BalisticaImagen);
			boton2 = new JButton("Selecciona Personaje 2", BerserkerImagen);
		} else if(equipoSeleccionadoList[1].toLowerCase().equals("balistica") && equipoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			boton1 = new JButton("Selecciona Personaje 1", BalisticaImagen);
			boton2 = new JButton("Selecciona Personaje 2", TanqueImagen);
		} else if(equipoSeleccionadoList[1].toLowerCase().equals("tanque") && equipoSeleccionadoList[2].toLowerCase().equals("berserker")) {
			boton1 = new JButton("Selecciona Personaje 1", TanqueImagen);
			boton2 = new JButton("Selecciona Personaje 2", BerserkerImagen);
		} else if(equipoSeleccionadoList[1].toLowerCase().equals("tanque") && equipoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			boton1 = new JButton("Selecciona Personaje 1", TanqueImagen);
			boton2 = new JButton("Selecciona Personaje 2", BalisticaImagen);
		}

		boton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String temp = equipoSeleccionadoList[1];
				equipoSeleccionadoList[1] = equipoSeleccionadoList[0];
				equipoSeleccionadoList[0] = temp;
				imgFondo = emparejamiento(equipoSeleccionadoList, equipoNoSeleccionadoList);
				getContentPane().remove(fondo); // Eliminar fondo
				//getContentPane().remove(panelBarras);
				fondo = new JLabel(new ImageIcon(imgFondo));
                fondo.setSize(800, 500);
                add(fondo, BorderLayout.CENTER);
                validate(); // Validar cambios
                repaint(); // Repintar ventana
				// Cerrar el JDialog actual
				Window window = SwingUtilities.getWindowAncestor(boton1);
				window.dispose();
				
			}
		});
		
		boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = equipoSeleccionadoList[2];
				equipoSeleccionadoList[2] = equipoSeleccionadoList[0];
				equipoSeleccionadoList[0] = temp;
				imgFondo = emparejamiento(equipoSeleccionadoList, equipoNoSeleccionadoList);
				getContentPane().remove(fondo); // Eliminar fondo
				fondo = new JLabel(new ImageIcon(imgFondo));
                fondo.setSize(800, 500);
                add(fondo, BorderLayout.CENTER);
                validate(); // Validar cambios
                repaint(); // Repintar ventana
				// Cerrar el JDialog actual
				Window window = SwingUtilities.getWindowAncestor(boton1);
				window.dispose();
			}
		});

		// Agregar los botones al panel
		panelBotones.add(boton1);
		panelBotones.add(boton2);
		// Agregar el panel de botones a la ventana
		seleccionaPersonaje.add(panelBotones, BorderLayout.CENTER);

		seleccionaPersonaje.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		seleccionaPersonaje.setModal(true); // Bloquear la ventana principal mientras esta ventana está abierta
		seleccionaPersonaje.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cerrar la ventana al presionar la "X"
		seleccionaPersonaje.setVisible(true);
	}
	
	public void ventanaCambiarPersonajeEnemigo(String[] equipoSeleccionadoList, String[] equipoNoSeleccionadoList) {
		// Crear la nueva ventana
		JDialog seleccionaPersonaje = new JDialog();
		// Configurar la ventana
		seleccionaPersonaje.setTitle("Selecciona Personaje");
		seleccionaPersonaje.setSize(600, 600);

		// Crear el panel de botones
		JPanel panelBotones = new JPanel();
		// Crear los botones
		String rutaBerserker = "img/berserker(enemigo).png";
		String rutaBalistica = "img/balistica(enemigo).png";
		String rutaTanque = "img/tanque(enemigo).png";

		ImageIcon imagenOriginal = new ImageIcon(rutaBerserker);
		Image imagenEtiqueta = imagenOriginal.getImage();
		Image nuevaImagen = imagenEtiqueta.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon BerserkerImagen = new ImageIcon(nuevaImagen);

		ImageIcon imagenOriginal1 = new ImageIcon(rutaBalistica);
		Image imagenEtiqueta1 = imagenOriginal1.getImage();
		Image nuevaImagen1 = imagenEtiqueta1.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon BalisticaImagen = new ImageIcon(nuevaImagen1);

		ImageIcon imagenOriginal2 = new ImageIcon(rutaTanque);
		Image imagenEtiqueta2 = imagenOriginal2.getImage();
		Image nuevaImagen2 = imagenEtiqueta2.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon TanqueImagen = new ImageIcon(nuevaImagen2);

		if (equipoNoSeleccionadoList[1].toLowerCase().equals("berserker") && equipoNoSeleccionadoList[2].toLowerCase().equals("balistica")) {
			boton1 = new JButton("Selecciona Personaje 1", BerserkerImagen);
			boton2 = new JButton("Selecciona Personaje 2", BalisticaImagen);
		} else if(equipoNoSeleccionadoList[1].toLowerCase().equals("berserker") && equipoNoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			boton1 = new JButton("Selecciona Personaje 1", BerserkerImagen);
			boton2 = new JButton("Selecciona Personaje 2", TanqueImagen);
		} else if(equipoNoSeleccionadoList[1].toLowerCase().equals("balistica") && equipoNoSeleccionadoList[2].toLowerCase().equals("berserker")) {
			boton1 = new JButton("Selecciona Personaje 1", BalisticaImagen);
			boton2 = new JButton("Selecciona Personaje 2", BerserkerImagen);
		} else if(equipoNoSeleccionadoList[1].toLowerCase().equals("balistica") && equipoNoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			boton1 = new JButton("Selecciona Personaje 1", BalisticaImagen);
			boton2 = new JButton("Selecciona Personaje 2", TanqueImagen);
		} else if(equipoNoSeleccionadoList[1].toLowerCase().equals("tanque") && equipoNoSeleccionadoList[2].toLowerCase().equals("berserker")) {
			boton1 = new JButton("Selecciona Personaje 1", TanqueImagen);
			boton2 = new JButton("Selecciona Personaje 2", BerserkerImagen);
		} else if(equipoNoSeleccionadoList[1].toLowerCase().equals("tanque") && equipoNoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			boton1 = new JButton("Selecciona Personaje 1", TanqueImagen);
			boton2 = new JButton("Selecciona Personaje 2", BalisticaImagen);
		}
		
		boton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = equipoNoSeleccionadoList[1];
				equipoNoSeleccionadoList[1] = equipoNoSeleccionadoList[0];
				equipoNoSeleccionadoList[0] = temp;
				imgFondo = emparejamiento(equipoSeleccionadoList, equipoNoSeleccionadoList);
				getContentPane().remove(fondo); // Eliminar fondo
				fondo = new JLabel(new ImageIcon(imgFondo));
                fondo.setSize(800, 500);
                add(fondo, BorderLayout.CENTER);
                validate(); // Validar cambios
                repaint(); // Repintar ventana
				// Cerrar el JDialog actual
				Window window = SwingUtilities.getWindowAncestor(boton1);
				window.dispose();
			}
		});
		
		boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = equipoNoSeleccionadoList[2];
				equipoNoSeleccionadoList[2] = equipoNoSeleccionadoList[0];
				equipoNoSeleccionadoList[0] = temp;
				imgFondo = emparejamiento(equipoSeleccionadoList, equipoNoSeleccionadoList);
				getContentPane().remove(fondo); // Eliminar fondo
				fondo = new JLabel(new ImageIcon(imgFondo));
                fondo.setSize(800, 500);
                add(fondo, BorderLayout.CENTER);
                validate(); // Validar cambios
                repaint(); // Repintar ventana
				// Cerrar el JDialog actual
				Window window = SwingUtilities.getWindowAncestor(boton1);
				window.dispose();
			}
		});
		
		// Agregar los botones al panel
		panelBotones.add(boton1);
		panelBotones.add(boton2);
		// Agregar el panel de botones a la ventana
		seleccionaPersonaje.add(panelBotones, BorderLayout.CENTER);

		seleccionaPersonaje.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		seleccionaPersonaje.setModal(true); // Bloquear la ventana principal mientras esta ventana está abierta
		seleccionaPersonaje.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cerrar la ventana al presionar la "X"
		seleccionaPersonaje.setVisible(true);
	}
	
	private void contadorPartidasConfigProperties() {
		File configFile = new File("config.properties");
        Properties props = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
            props.load(inputStream);
            contadorPartidas = Integer.parseInt(props.getProperty("contadorPartidas", "1"));
        } catch (IOException e) {
            // Si el archivo de configuración no existe, se puede crear con un valor inicial de 1
            System.out.println("No se encontró el archivo de configuración, se creará uno nuevo.");
        }

        // Incrementar el contador de partidas y mostrar el número de partida actual
        contadorPartidas++;
        System.out.println("Partida " + contadorPartidas);
        // Escribir en log.txt el numero de partida en el que estamos
        escribirFicheroLog("Partida " + contadorPartidas);
        // Guardar el valor actual de contadorPartidas en el archivo de configuración
        props.setProperty("contadorPartidas", Integer.toString(contadorPartidas));
        try (OutputStream outputStream = new FileOutputStream(configFile)) {
            props.store(outputStream, "Configuración del juego");
        } catch (IOException e) {
            System.out.println("Error al guardar la configuración del juego.");
        }
	}
	
	private void escribirFicheroLog(String mensaje){
		try {
            FileWriter fileWriter = new FileWriter("log.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(mensaje);
            bufferedWriter.newLine(); // Agrega una nueva línea para separar el contenido

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}