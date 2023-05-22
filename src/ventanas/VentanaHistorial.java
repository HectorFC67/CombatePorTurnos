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
import java.util.Comparator;

public class VentanaHistorial extends JFrame {

    public VentanaHistorial(String usuario1, String usuario2) {
        super("Historial de Partidas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Usuario");
        model.addColumn("Partidas jugadas");
        model.addColumn("Victorias");
        model.addColumn("Derrotas");

        try (BufferedReader br = new BufferedReader(new FileReader("historial.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\t");
                if (partes.length >= 5) {
                    String usuario = partes[0];
                    int partidas = Integer.parseInt(partes[2]);
                    int victorias = Integer.parseInt(partes[3]);
                    int derrotas = Integer.parseInt(partes[4]);
                    model.addRow(new Object[]{usuario, partidas, victorias, derrotas});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
      //------------------------------------------------------------------------------------------------------------
        JTable table = new JTable(model);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        Comparator<Object> victoriasDerrotasComparator = (obj1, obj2) -> {
        	System.out.println( "comparator " + obj1 + "," + obj2 );
            int victorias1 = (int) obj1;
            int derrotas1 = (int) table.getValueAt(table.convertRowIndexToModel(table.getRowSorter().convertRowIndexToView(table.getSelectedRow())), 3);
            int victorias2 = (int) obj2;
            int derrotas2 = (int) table.getValueAt(table.convertRowIndexToModel(table.getRowSorter().convertRowIndexToView(table.getSelectedRow())), 3);
            int diferencia1 = victorias1 - derrotas1;
            int diferencia2 = victorias2 - derrotas2;
            if (diferencia1 == diferencia2) {
                // Si las diferencias son iguales, se compara por victorias
                return victorias2 - victorias1;
            } else {
                // Se compara por la diferencia
                return diferencia2 - diferencia1;
            }
        };
        sorter.setComparator(2, victoriasDerrotasComparator);
      //------------------------------------------------------------------------------------------------------------
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
