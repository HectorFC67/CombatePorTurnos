package ventanas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInstrucciones extends JFrame {
    private JTextArea contextoTextArea;
    private JTextArea funcionamientoTextArea;

    public VentanaInstrucciones(String usuario1, String usuario2) {
        super("Instrucciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);

        JTabbedPane tabbedPane = new JTabbedPane();

        contextoTextArea = new JTextArea();
        contextoTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contextoTextArea.setEditable(false);

        funcionamientoTextArea = new JTextArea();
        funcionamientoTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        funcionamientoTextArea.setEditable(false);

        JScrollPane contextoScrollPane = new JScrollPane(contextoTextArea);
        JScrollPane funcionamientoScrollPane = new JScrollPane(funcionamientoTextArea);

        JPanel contextoPanel = new JPanel(new BorderLayout());
        contextoPanel.add(contextoScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Contexto", contextoPanel);

        JPanel funcionamientoPanel = new JPanel(new BorderLayout());
        funcionamientoPanel.add(funcionamientoScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Funcionamiento", funcionamientoPanel);

        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(usuario1, usuario2);
                ventanaPrincipal.setVisible(true);
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(volverButton);

        add(tabbedPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    public void setContexto(String textoContexto) {
        contextoTextArea.setText(textoContexto);
    }

    public String getContexto() {
        return contextoTextArea.getText();
    }

    public void setFuncionamiento(String textoFuncionamiento) {
        funcionamientoTextArea.setText(textoFuncionamiento);
    }

    public String getFuncionamiento() {
        return funcionamientoTextArea.getText();
    }
}
