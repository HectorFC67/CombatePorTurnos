package ventanas;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class VentanaVictoria extends JFrame {

    public VentanaVictoria(int equipoGanador, String usuario1, String usuario2) {
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
            mensaje.setText("Ha ganado " + usuario1 + ", ¡felicidades!");
            actualizarHistorial(usuario1, usuario2, true);
        } else if (equipoGanador == 2) {
            mensaje.setText("Ha ganado el "+ usuario2 + ", ¡felicidades!");
            actualizarHistorial(usuario1, usuario2, false);
        } else {
            mensaje.setText("Valor inválido");
        }

        panel.add(mensaje, BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel(new GridLayout(1, 2));
        panel.add(botonesPanel, BorderLayout.SOUTH);

        JButton volverBtn = new JButton("Volver al inicio");
        volverBtn.addActionListener(e -> {
            dispose();
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(usuario1, usuario2);
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

    private void actualizarHistorial(String usuario1, String usuario2, boolean equipo1Ganador) {
        try {
            File archivo = new File("historial.txt");
            File archivoTemporal = new File("historial_temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemporal));

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\t");

                // Actualizar las victorias y derrotas de usuario1 y usuario2
                if (partes.length >= 5 && partes[0].equals(usuario1)) {
                    int partidas = Integer.parseInt(partes[2]);
                    int victorias = Integer.parseInt(partes[3]);
                    int derrotas = Integer.parseInt(partes[4]);

                    partidas++;
                    if (equipo1Ganador) {
                        victorias++;
                    } else {
                        derrotas++;
                    }

                    linea = usuario1 + "\t" + partes[1] + "\t" + partidas + "\t" + victorias + "\t" + derrotas;
                } else if (partes.length >= 5 && partes[0].equals(usuario2)) {
                    int partidas = Integer.parseInt(partes[2]);
                    int victorias = Integer.parseInt(partes[3]);
                    int derrotas = Integer.parseInt(partes[4]);

                    partidas++;
                    if (!equipo1Ganador) {
                        victorias++;
                    } else {
                        derrotas++;
                    }

                    linea = usuario2 + "\t" + partes[1] + "\t" + partidas + "\t" + victorias + "\t" + derrotas;
                }

                bw.write(linea);
                bw.newLine();
            }

            br.close();
            bw.close();

            // Reemplazar el archivo original con el archivo temporal
            archivo.delete();
            archivoTemporal.renameTo(archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
