package ventanas;

import javax.swing.*;
import java.awt.*;

public class VentanaVictoria extends JFrame {

    public VentanaVictoria(int equipoGanador) {
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JLabel mensaje = new JLabel();
        mensaje.setFont(mensaje.getFont().deriveFont(24.0f));
        mensaje.setHorizontalAlignment(JLabel.CENTER);
        mensaje.setVerticalAlignment(JLabel.CENTER);

        if (equipoGanador == 1) {
            mensaje.setText("Ha ganado el equipo 1, ¡felicidades!");
        } else if (equipoGanador == 2) {
            mensaje.setText("Ha ganado el equipo 2, ¡felicidades!");
        } else {
            mensaje.setText("Valor inválido");
        }

        panel.add(mensaje, BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel(new GridLayout(1, 2));
        panel.add(botonesPanel, BorderLayout.SOUTH);

        JButton volverBtn = new JButton("Volver al inicio");
        volverBtn.addActionListener(e -> {
            dispose();
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
            ventanaPrincipal.setVisible(true);
        });
        botonesPanel.add(volverBtn);

        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.addActionListener(e -> {
            dispose();
            System.exit(0);
        });
        botonesPanel.add(cerrarBtn);

        setVisible(true);
    }
}
