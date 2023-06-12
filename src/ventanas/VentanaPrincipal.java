package ventanas;

import java.awt.BorderLayout;
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
        JButton bInstrucciones = new JButton("Instrucciones");

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
        
        bInstrucciones.addActionListener(e -> {
            VentanaInstrucciones ventanaInstrucciones = new VentanaInstrucciones(usuario1, usuario2);
            ventanaInstrucciones.setContexto("- Se necesitan 2 jugadores para poder disfrutar de la experiencia de juego al maximo."
						            		+ "\n - Hay diferentes tipos de personajes:\n"
						            		+ "\t- Berserker: personaje mas equilibrado. Destaca en su ofensiva pero tiene una estamina reducida. Su habilidad realiza 40 de daño y no pierde estamina\n"
						            		+ "\t- Balistica: personaje muy veloz con un gran poder ofensivo pero salud reducida. Su habilidad drena al enemigo quitandole 30 de de vida y recuperando el 15.\n"
						            		+ "\t- Tanque: personaje con mucha salud mucha estamina pero ataque reducido y muy lento. Su habilidad hace un golpe aturdidor al enemigo el cual le resta 15 de daño y le reduce la estamina a su oponente\n");
            ventanaInstrucciones.setFuncionamiento("- Atacar: este boton realiza el ataque basico del jugador que este activo.\n"
            								+ "- Descansar: recupera uno de estamina a cambio a cambio de un turno sin realizar una accion ofensiva.\n"
            								+ "- Cambiar Guerrero: si lo desea el jugador puede cambiar de guerrero.\n"
            								+ "- Habilidad: realiza la habilidad unica de cada guerrero.");
            ventanaInstrucciones.setVisible(true);
            dispose();
        });

        panelBotones.add(bIniciar);
        panelBotones.add(bCerrar);
        panelBotones.add(bHistorial);
        panelBotones.add(bInstrucciones);

        add(panelBotones, BorderLayout.SOUTH);
        add(panelFondo, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
