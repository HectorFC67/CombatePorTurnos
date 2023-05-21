package ventanas;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.JPasswordField;

public class VentanaRegistro extends Frame implements ActionListener {
    private Label lb1, lb2, lb3;
    private TextField tf1;
    private JPasswordField  tf2;
    private Button btn1;

    public VentanaRegistro() {
        super("Registro");
        setSize(350, 140);
        setLocationRelativeTo(null);

        lb1 = new Label("Nombre de usuario:");
        lb2 = new Label("Contraseña:");
        lb3 = new Label("");

        tf1 = new TextField();
        tf2 = new JPasswordField();

        btn1 = new Button("Registrar");
        btn1.addActionListener(this);

        Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER));
        panel.add(btn1);

        // Usamos BorderLayout para organizar los componentes
        setLayout(new BorderLayout());

        // Creamos un panel para organizar los componentes en el centro usando GridBagLayout
        Panel panelCentral = new Panel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        panelCentral.add(lb1, c);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        panelCentral.add(tf1, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        panelCentral.add(lb2, c);

        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        panelCentral.add(tf2, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panelCentral.add(lb3, c);

        add(panelCentral, BorderLayout.CENTER);

        add(panel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String usuario = tf1.getText();
        String contrasena = tf2.getText();
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            lb3.setText("Rellene usuario y contraseña.");
        } else {
            try {
                File archivo = new File("historial.txt");
                BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo, true));
                escritor.write(usuario + "\t" + contrasena + "\t0\t0\t0\n");
                escritor.close();

                new VentanaInicioSesion();
                dispose();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
