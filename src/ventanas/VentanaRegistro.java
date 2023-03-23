package ventanas;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class VentanaRegistro extends Frame implements ActionListener {
    Label lb1, lb2, lb3;
    TextField tf1, tf2;
    Button btn1;

    public VentanaRegistro() {
        super("Registro");
        setSize(400, 200);
        setVisible(true);
        setLocationRelativeTo(null);
        
        lb1 = new Label("Nombre de usuario:");
        lb2 = new Label("Contraseña:");
        lb3 = new Label("");

        tf1 = new TextField();
        tf2 = new TextField();

        btn1 = new Button("Registrar");
        btn1.addActionListener(this);
        lb3.setAlignment(Label.CENTER);
        
        Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER));
        panel.add(btn1);
        
        setLayout(new GridLayout(3, 2));

        add(lb1);
        add(tf1);
        add(lb2);
        add(tf2);
        add(lb3);
        add(btn1);

        // Agregar manejador de eventos para cerrar la ventana de registro
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

    }

    public void actionPerformed(ActionEvent e) {
        String usuario = tf1.getText();
        String contrasena = tf2.getText();
        if (usuario.isEmpty() || contrasena.isEmpty()) {
        	
            lb3.setText("Rellene usuario y contraseña.");
            
        } else {
        	
        	try {
        		
        		File archivoCSV = new File("historial.csv");
        		BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoCSV, true));
        		escritor.write(usuario + "," + contrasena + "\n");
        		escritor.close();

        		new VentanaPrincipal();
        		dispose();

        	} catch (IOException ex) {
        		
        		ex.printStackTrace();
        		
        	}
        }
    }

    public static void main(String[] args) {
        new VentanaRegistro();
    }
}
