package ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal(String usuario1, String usuario2) {
        super("ANCIENT COMBAT - Inicio");
        setSize(940, 725);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel panelFondo = new JPanel();
        panelFondo.setLayout(new FlowLayout(FlowLayout.CENTER));

        ImageIcon imagen = new ImageIcon("img/iniciar.png");
        JLabel labelImagen = new JLabel(imagen);
        panelFondo.add(labelImagen);

        JButton bIniciar = new JButton("Iniciar");
        JButton bCerrar = new JButton("Cerrar Juego");
        JButton bHistorial = new JButton("Historial");

        bIniciar.addActionListener(e -> {
            VentanaEquipos ventanaEquipos = new VentanaEquipos(usuario1, usuario2);
            ventanaEquipos.setVisible(true);
            this.dispose();
        });

        bCerrar.addActionListener(e -> System.exit(0));

        bHistorial.addActionListener(e -> {
            VentanaHistorial ventanaHistorial = new VentanaHistorial(usuario1, usuario2);
            ventanaHistorial.setVisible(true);
            dispose();
        });

        panelBotones.add(bIniciar);
        panelBotones.add(bCerrar);
        panelBotones.add(bHistorial);

        add(panelBotones, BorderLayout.SOUTH);
        add(panelFondo, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
