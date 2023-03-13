package ventanas;

import java.awt.BorderLayout;
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
				
			}
		});
		descansarAliado.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				
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
				
			}
		});
		descansarEnemigo.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {

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
		String primerJugadorSeleccionado = equipoSeleccionadoList[0].toLowerCase();
		String primerJugadorNoSeleccionado = equipoNoSeleccionadoList[0].toLowerCase();
		if (primerJugadorSeleccionado.equals("berserker") && primerJugadorNoSeleccionado.equals("berserker")) {

			imgFondo = "img/combate-berserker-berserker.png";

		} else if (primerJugadorSeleccionado.equals("balistica") && primerJugadorNoSeleccionado.equals("balistica")) {

			imgFondo = "img/combate-balistica-balistica.png";

		} else if (primerJugadorSeleccionado.equals("tanque") && primerJugadorNoSeleccionado.equals("tanque")) {

			imgFondo = ("img/combate-tanque-tanque.png");

		} else if (primerJugadorSeleccionado.equals("berserker") && primerJugadorNoSeleccionado.equals("balistica")) {

			imgFondo = ("img/combate-berserker-balistica.png");

		} else if (primerJugadorSeleccionado.equals("berserker") && primerJugadorNoSeleccionado.equals("tanque")) {

			imgFondo = "img/combate-berserker-tanque.png";

		} else if (primerJugadorSeleccionado.equals("balistica") && primerJugadorNoSeleccionado.equals("tanque")) {

			imgFondo = "img/combate-balistica-tanque.png";

		} else if ( primerJugadorSeleccionado.equals("balistica") && primerJugadorNoSeleccionado.equals("berserker")) {

			imgFondo = "img/combate-balistica-berserker.png";

		} else if (primerJugadorSeleccionado.equals("tanque") && primerJugadorNoSeleccionado.equals("berserker")) {

			imgFondo = "img/combate-tanque-berserker.png";

		} else if (primerJugadorSeleccionado.equals("tanque") && primerJugadorNoSeleccionado.equals("balistica")) {

			imgFondo = "img/combate-tanque-balistica.png";
		} else {

			System.out.println("No se encontro la imagen de fondo");
		}
		return imgFondo;
	}

}
