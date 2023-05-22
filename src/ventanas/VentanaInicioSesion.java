package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VentanaInicioSesion extends JFrame {
    
	private JTextField usuarioField;
    private JPasswordField contrasenaField;
    private static int contador = 0;
    
    public String usuario1 = "";
    public String usuario2 = "";
    
    public VentanaInicioSesion() {
        setTitle("Inicio de sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 140);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioField = new JTextField(20);
        JLabel contrasenaLabel = new JLabel("Contraseña:");
        contrasenaField = new JPasswordField(20);

        panel.add(usuarioLabel);
        panel.add(usuarioField);
        panel.add(contrasenaLabel);
        panel.add(contrasenaField);

        JButton botonInicioSesion = new JButton("Iniciar sesión");
        botonInicioSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioField.getText();
                String contrasena = new String(contrasenaField.getPassword());
                if (verificarCredenciales(usuario, contrasena)) {
                    JOptionPane.showMessageDialog(VentanaInicioSesion.this, "Inicio de sesión exitoso.");
                    if(contador == 2) {
                    	VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(usuario1, usuario2);
                    	ventanaPrincipal.setVisible(true);
                    	dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaInicioSesion.this, "Credenciales incorrectas.");
                }
            }
        });

        JButton botonRegistrar = new JButton("Registrar");
        botonRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaRegistro ventanaRegistro = new VentanaRegistro();
                ventanaRegistro.setVisible(true);
                dispose();
            }
        });

        panel.add(botonInicioSesion);
        panel.add(botonRegistrar);

        add(panel);
        setVisible(true);
    }

    private boolean verificarCredenciales(String usuario, String contrasena) {
        try (BufferedReader br = new BufferedReader(new FileReader("historial.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\t");
                if (partes.length >= 2 && partes[0].equals(usuario) && partes[1].equals(contrasena)) {
                    if(contador == 0) {
                    	usuario1 = partes[0];
                    	contador++;
                    }else if(contador == 1) {
                    	if(partes[0].equals(usuario1)) {
                    			JOptionPane.showMessageDialog(null, "Error: El usuario 1 "
                    					+ "y el usuario 2 no pueden ser el mismo.", 
                    					"Error de usuario", JOptionPane.ERROR_MESSAGE);
                    			return false;
                    	}else {
                    		usuario2 = partes[0];
                    		contador++;
                    	}
                    }
                	return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VentanaInicioSesion();
            }
        });
    }
}