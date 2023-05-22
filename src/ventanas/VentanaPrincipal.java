package ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

	//mapas, hacer bien ventana registro ¡SOLO REGISTRO! (solo con usuario y verificar que este en el fichero).
    public VentanaPrincipal(String usuario1, String usuario2) {
    	// TODO Auto-generated constructor stub
        super("ANCIENT COMBAT - Inicio");
        setSize(940, 725);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel panelFondo = new JPanel();
        panelFondo.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Cargar la imagen de fond
        ImageIcon imagen = new ImageIcon("img/iniciar.png");
        JLabel labelImagen = new JLabel(imagen);

        panelFondo.add(labelImagen);

        JButton bIniciar = new JButton("Iniciar");
        JButton bCerrar = new JButton("Cerrar Juego");

        bIniciar.addActionListener(e -> {
            VentanaEquipos ventanaEquipos = new VentanaEquipos(usuario1, usuario2);
            ventanaEquipos.setVisible(true);
            this.dispose();
        });

        bCerrar.addActionListener(e -> System.exit(0));

        panelBotones.add(bIniciar);
        panelBotones.add(bCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        add(panelFondo, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}