package ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import metodos.Partida;

public class VentanaHistorial extends JFrame {

    public VentanaHistorial(String usuario1, String usuario2) {
        super("Historial de Partidas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Puesto");
        model.addColumn("Usuario");
        model.addColumn("Partidas jugadas");
        model.addColumn("Victorias");
        model.addColumn("Derrotas");

        List<Partida> partidas = new ArrayList<>();
        int puesto = 1;

        try (BufferedReader br = new BufferedReader(new FileReader("historial.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\t");
                if (partes.length >= 5) {
                    String usuario = partes[0];
                    int partidasJugadas = Integer.parseInt(partes[2]);
                    int victorias = Integer.parseInt(partes[3]);
                    int derrotas = Integer.parseInt(partes[4]);
                    partidas.add(new Partida(usuario, partidasJugadas, victorias, derrotas));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(partidas, new Comparator<Partida>() {
            @Override
            public int compare(Partida p1, Partida p2) {
                int diferencia1 = p1.getVictorias() - p1.getDerrotas();
                int diferencia2 = p2.getVictorias() - p2.getDerrotas();
                if (diferencia1 == diferencia2) {
                    // Si las diferencias son iguales, se compara por victorias
                    return p2.getVictorias() - p1.getVictorias();
                } else {
                    // Se compara por la diferencia
                    return diferencia2 - diferencia1;
                }
            }
        });

        for (Partida partida : partidas) {
            model.addRow(new Object[]{puesto, partida.getUsuario(), partida.getPartidasJugadas(), partida.getVictorias(), partida.getDerrotas()});
            puesto++;
        }

        JTable table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);

        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(580, 350));
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton volverBtn = new JButton("Volver a la ventana principal");
        volverBtn.addActionListener(e -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(usuario1, usuario2);
            ventanaPrincipal.setVisible(true);
            dispose();
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(volverBtn);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }
}
