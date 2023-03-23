package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import metodos.Personaje;
import metodos.Tipos;

public class VentanaCombate extends JFrame{
	private String imgFondo = "";

	private Personaje berserkerAliado = new Personaje(Tipos.BERSERKER, 150, 20, 4, 5);
	private Personaje balisticaAliado = new Personaje(Tipos.BALISTICA, 100, 30, 5, 7);
	private Personaje tanqueAliado = new Personaje(Tipos.TANQUE, 200, 10, 6, 2);

	private Personaje berserkerEnemigo = new Personaje(Tipos.BERSERKER, 150, 20, 4, 5);
	private Personaje balisticaEnemigo = new Personaje(Tipos.BALISTICA, 100, 30, 5, 7);
	private Personaje tanqueEnemigo = new Personaje(Tipos.TANQUE, 200, 10, 6, 2);

	private JButton boton1 = new JButton();
	private JButton boton2 = new JButton();

	private int nuevaAltura = 256;
	private int nuevoAncho = 270;
	
	private Personaje[] listaPersonajesAliados;
	private Personaje[] listaPersonajesEnemigos;
	
	private JLabel fondo;
	
    private JProgressBar pbSaludAliado;
    private JProgressBar pbEstaminaAliado;
    
    private int saludMaximaBerserker = 150;
    private int saludMaximaBalistica = 100;
    private int saludMaximaTanque = 200;
    
    private int saludMaximaAliado = 0;
    private int saludAliado = 0;
    private int saludMaximaEnemigo = 0;
    private int saludEnemigo = 0;
    private int estaminaMaximaAliado = 0;
    private int estaminaAliado = 0;
    private int estaminaMaximaEnemigo = 0;
    private int estaminaEnemigo = 0;
	
	public VentanaCombate(String[] equipoSeleccionadoList, String[] equipoNoSeleccionadoList) {
		super("Juego de combate");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		imgFondo = emparejamiento(equipoSeleccionadoList, equipoNoSeleccionadoList);

		fondo = new JLabel(new ImageIcon(imgFondo));
		fondo.setSize(800, 500);
		add(fondo);

		JPanel panelAliado = new JPanel();

		JButton atacarAliado = new JButton("Atacar");
		JButton descansarAliado = new JButton("Descansar");
		JButton cambiarPersonajeAliado = new JButton("Cambiar Guerrero");

		atacarAliado.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				listaPersonajesAliados[0].calcularDanyo(listaPersonajesEnemigos[0]);
			}
		});
		descansarAliado.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				listaPersonajesAliados[0].descansar();
			}
		});
		cambiarPersonajeAliado.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
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
				listaPersonajesEnemigos[0].calcularDanyo(listaPersonajesAliados[0]);
			}
		});
		descansarEnemigo.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				listaPersonajesEnemigos[0].descansar();
			}
		});
		cambiarPersonajeEnemigo.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
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
		String tercerJugadorSeleccionado = equipoSeleccionadoList[2].toUpperCase();
		String tercerJugadorNoSeleccionado = equipoNoSeleccionadoList[2].toUpperCase();
		
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
		return imgFondo;
	}

}


/*		JPanel panelBarras = new JPanel( new BorderLayout() );
		JPanel panelBarrasAliado = new JPanel();
		JPanel panelBarrasEnemigo = new JPanel();
		
		pbSaludAliado = new JProgressBar(0, saludMaximaAliado);
		pbSaludAliado.setStringPainted(true);
		pbSaludAliado.setForeground(Color.GREEN);
        panelBarrasAliado.add(pbSaludAliado);
        
        pbEstaminaAliado = new JProgressBar(0, estaminaMaximaAliado);
        pbEstaminaAliado.setStringPainted(true);
        pbEstaminaAliado.setForeground(Color.BLUE);
        panelBarrasAliado.add(pbEstaminaAliado);
		
        pbSaludAliado.setValue(saludAliado);
        pbSaludAliado.setMaximum(saludMaximaAliado);
        pbEstaminaAliado.setValue(estaminaAliado);
        pbEstaminaAliado.setMaximum(estaminaMaximaAliado);
        
        
		panelBarras.add(panelBarrasAliado, BorderLayout.WEST);
		panelBarras.add(panelBarrasEnemigo, BorderLayout.EAST);
		getContentPane().add(panelBarras);*/