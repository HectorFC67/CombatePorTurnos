package ventanas;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import metodos.Personaje;
import metodos.Tipos;

public class VentanaCombatePrevio extends JFrame {

	JLabel personaje1EquipoSeleccionado;
	JLabel personaje2EquipoSeleccionado;
	JLabel personaje3EquipoSeleccionado;

	JLabel personaje1EquipoNoSeleccionado;
	JLabel personaje2EquipoNoSeleccionado;
	JLabel personaje3EquipoNoSeleccionado;


	int nuevaAltura = 256;
	int nuevoAncho = 270;
	String rutaImagen = "";

	public VentanaCombatePrevio(String equipoSeleccionado, String equipoNoSeleccionado, String usuario1, String usuario2) {
		super("Preparación del combate");
		setSize(1000, 625);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String[] equipoSeleccionadoList = equipoSeleccionado.split("-");
		String[] equipoNoSeleccionadoList = equipoNoSeleccionado.split("-");

		// Mostrar cada jugador en una ventana
		JPanel panel = new JPanel(new BorderLayout());

		// Equipo seleccionado
		JPanel panelEquipoSeleccionado = new JPanel(new BorderLayout());
		JLabel labelEquipoSeleccionado = new JLabel(usuario1 + ": " + equipoSeleccionadoList[0] + ", " + equipoSeleccionadoList[1] + " y " + equipoSeleccionadoList[2]);
		panelEquipoSeleccionado.add(labelEquipoSeleccionado, BorderLayout.NORTH);

		JPanel panelJugadoresEquipoSeleccionado = new JPanel();

		// Determinar la ruta de la imagen basándose en el equipo seleccionado para el jugador 1
		if (equipoSeleccionadoList[0].toLowerCase().equals("berserker")) {
			rutaImagen = "img/berserker(local).png";
		} else if (equipoSeleccionadoList[0].toLowerCase().equals("balistica")) {
			rutaImagen = "img/balistica(local).png";
		} else if (equipoSeleccionadoList[0].toLowerCase().equals("tanque")) {
			rutaImagen = "img/tanque(local).png";
		}

		// Crear la imagen redimensionada para el jugador 1
		ImageIcon imagenOriginal = new ImageIcon(rutaImagen);
		Image imagenEtiqueta = imagenOriginal.getImage();
		Image nuevaImagen = imagenEtiqueta.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon nuevaImagenIcono = new ImageIcon(nuevaImagen);
		JLabel personaje1EquipoSeleccionado = new JLabel(nuevaImagenIcono);

		// Repetir el proceso para los otros jugadores personaje2EquipoSeleccionado y personaje3EquipoSeleccionado


		panelJugadoresEquipoSeleccionado.add(personaje1EquipoSeleccionado);

		if (equipoSeleccionadoList[1].toLowerCase().equals("berserker")) {
			rutaImagen = "img/berserker(local).png";
		} else if (equipoSeleccionadoList[1].toLowerCase().equals("balistica")) {
			rutaImagen = "img/balistica(local).png";
		} else if (equipoSeleccionadoList[1].toLowerCase().equals("tanque")) {
			rutaImagen = "img/tanque(local).png";
		}

		// Crear la imagen redimensionada para el jugador 2
		imagenOriginal = new ImageIcon(rutaImagen);
		imagenEtiqueta = imagenOriginal.getImage();
		nuevaImagen = imagenEtiqueta.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		nuevaImagenIcono = new ImageIcon(nuevaImagen);
		personaje2EquipoSeleccionado = new JLabel(nuevaImagenIcono);

		panelJugadoresEquipoSeleccionado.add(personaje2EquipoSeleccionado);

		if (equipoSeleccionadoList[2].toLowerCase().equals("berserker")) {
			rutaImagen = "img/berserker(local).png";
		} else if (equipoSeleccionadoList[2].toLowerCase().equals("balistica")) {
			rutaImagen = "img/balistica(local).png";
		} else if (equipoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			rutaImagen = "img/tanque(local).png";
		}

		// Crear la imagen redimensionada para el jugador 3
		imagenOriginal = new ImageIcon(rutaImagen);
		imagenEtiqueta = imagenOriginal.getImage();
		nuevaImagen = imagenEtiqueta.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		nuevaImagenIcono = new ImageIcon(nuevaImagen);
		JLabel personaje3EquipoSeleccionado = new JLabel(nuevaImagenIcono);

		panelJugadoresEquipoSeleccionado.add(personaje3EquipoSeleccionado);

		panelEquipoSeleccionado.add(panelJugadoresEquipoSeleccionado, BorderLayout.CENTER);

		panel.add(panelEquipoSeleccionado, BorderLayout.NORTH);

		// Equipo no seleccionado
		JPanel panelEquipoNoSeleccionado = new JPanel(new BorderLayout());
		JLabel labelEquipoNoSeleccionado = new JLabel(usuario2 + ": " + equipoNoSeleccionadoList[0] + ", " + equipoNoSeleccionadoList[1] + " y " + equipoNoSeleccionadoList[2]);
		panelEquipoNoSeleccionado.add(labelEquipoNoSeleccionado, BorderLayout.NORTH);

		JPanel panelJugadoresEquipoNoSeleccionado = new JPanel();

		// Determinar la ruta de la imagen basándose en el equipo seleccionado para el jugador 1
		if (equipoNoSeleccionadoList[0].toLowerCase().equals("berserker")) {
			rutaImagen = "img/berserker(enemigo).png";
		} else if (equipoNoSeleccionadoList[0].toLowerCase().equals("balistica")) {
			rutaImagen = "img/balistica(enemigo).png";
		} else if (equipoNoSeleccionadoList[0].toLowerCase().equals("tanque")) {
			rutaImagen = "img/tanque(enemigo).png";
		}

		// Crear la imagen redimensionada para el jugador 1
		imagenOriginal = new ImageIcon(rutaImagen);
		imagenEtiqueta = imagenOriginal.getImage();
		nuevaImagen = imagenEtiqueta.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		nuevaImagenIcono = new ImageIcon(nuevaImagen);
		personaje1EquipoNoSeleccionado = new JLabel(nuevaImagenIcono);

		panelJugadoresEquipoNoSeleccionado.add(personaje1EquipoNoSeleccionado);

		if (equipoNoSeleccionadoList[1].toLowerCase().equals("berserker")) {
			rutaImagen = "img/berserker(enemigo).png";
		} else if (equipoNoSeleccionadoList[1].toLowerCase().equals("balistica")) {
			rutaImagen = "img/balistica(enemigo).png";
		} else if (equipoNoSeleccionadoList[1].toLowerCase().equals("tanque")) {
			rutaImagen = "img/tanque(enemigo).png";
		}

		// Crear la imagen redimensionada para el jugador 2
		imagenOriginal = new ImageIcon(rutaImagen);
		imagenEtiqueta = imagenOriginal.getImage();
		nuevaImagen = imagenEtiqueta.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		nuevaImagenIcono = new ImageIcon(nuevaImagen);
		personaje2EquipoNoSeleccionado = new JLabel(nuevaImagenIcono);

		panelJugadoresEquipoNoSeleccionado.add(personaje2EquipoNoSeleccionado);

		if (equipoNoSeleccionadoList[2].toLowerCase().equals("berserker")) {
			rutaImagen = "img/berserker(enemigo).png";
		} else if (equipoNoSeleccionadoList[2].toLowerCase().equals("balistica")) {
			rutaImagen = "img/balistica(enemigo).png";
		} else if (equipoNoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			rutaImagen = "img/tanque(enemigo).png";
		}

		// Crear la imagen redimensionada para el jugador 3
		imagenOriginal = new ImageIcon(rutaImagen);
		imagenEtiqueta = imagenOriginal.getImage();
		nuevaImagen = imagenEtiqueta.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		nuevaImagenIcono = new ImageIcon(nuevaImagen);
		JLabel personaje3EquipoNoSeleccionado = new JLabel(nuevaImagenIcono);

		panelJugadoresEquipoNoSeleccionado.add(personaje3EquipoNoSeleccionado);

		panelEquipoNoSeleccionado.add(panelJugadoresEquipoNoSeleccionado, BorderLayout.CENTER);
		panel.add(panelEquipoNoSeleccionado, BorderLayout.CENTER);

		// Botón "Iniciar Combate"
		JButton iniciarCombateButton = new JButton("Iniciar Combate");
		iniciarCombateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ocultar esta ventana y abrir VentanaCombate
				setVisible(false);
				VentanaCombate ventanaCombate = new VentanaCombate(equipoSeleccionadoList, equipoNoSeleccionadoList, usuario1, usuario2);
				ventanaCombate.setVisible(true);
			}
		});

		panel.add(iniciarCombateButton, BorderLayout.SOUTH);

		// anadir paneles
		add(panel);

		setLocationRelativeTo(null);

		setVisible(true);
	}
}
