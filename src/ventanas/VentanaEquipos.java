package ventanas;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaEquipos extends JFrame {

	private final String[] tipos = {"Berserker", "Balistica", "Tanque"};
	private JComboBox<String>[] comboBoxes = new JComboBox[3];
	private JList<String> equiposList;
	private int contar = 0;
	private static final int MAX_EQUIPOS = 2; // Número máximo de equipos permitidos
	private List<String> equipos = new ArrayList<>();

	DefaultListModel<String> modelo = new DefaultListModel<>();

	public VentanaEquipos() {
		super("Seleccionar equipos");
		setSize(450, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel panelTipos = new JPanel();
		panelTipos.setLayout(new GridLayout(3, 2));

		JLabel label1 = new JLabel("Selecciona un tipo para la posición 1:");
		comboBoxes[0] = new JComboBox<>(tipos);

		JLabel label2 = new JLabel("Selecciona un tipo para la posición 2:");
		comboBoxes[1] = new JComboBox<>(tipos);

		JLabel label3 = new JLabel("Selecciona un tipo para la posición 3:");
		comboBoxes[2] = new JComboBox<>(tipos);

		panelTipos.add(label1);
		panelTipos.add(comboBoxes[0]);
		panelTipos.add(label2);
		panelTipos.add(comboBoxes[1]);
		panelTipos.add(label3);
		panelTipos.add(comboBoxes[2]);

		JButton botonGenerar = new JButton("Generar equipos");
		botonGenerar.addActionListener(e -> generarEquipos());

		JButton botonSeleccionar = new JButton("Seleccionar mi equipo");
		botonSeleccionar.addActionListener(e -> seleccionarEquipo());

		JPanel panelBoton = new JPanel();
		panelBoton.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBoton.add(botonGenerar);
		panelBoton.add(botonSeleccionar);

		equiposList = new JList<>( modelo );
		JScrollPane scrollPane = new JScrollPane(equiposList);

		panel.add(panelTipos, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(panelBoton, BorderLayout.SOUTH);

		add(panel);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void generarEquipos() {
		if(contar == MAX_EQUIPOS) { // Si se han generado el número máximo de equipos permitidos
			JOptionPane.showMessageDialog(this, "Se ha alcanzado el número máximo de equipos para esta partida.");
		} else {

			String tipo1 = (String) comboBoxes[0].getSelectedItem();
			String tipo2 = (String) comboBoxes[1].getSelectedItem();
			String tipo3 = (String) comboBoxes[2].getSelectedItem();

			if (!tipo1.equals(tipo2) && !tipo1.equals(tipo3) && !tipo2.equals(tipo3)) {

				String equipo = tipo1 + "-" + tipo2 + "-" + tipo3;
				equipos.add(equipo); 
				contar += 1; 

				modelo.addElement( equipo );
			} else {
				JOptionPane.showMessageDialog(this, "Los tipos deben ser distintos entre sí");

			}
			//equiposList.setListData(equipos.toArray(new String[0]));

		}

	}

	private void seleccionarEquipo() {
		if (equiposList.getSelectedValue() == null) {
			JOptionPane.showMessageDialog(this, "Debes seleccionar un equipo de la lista.");
		} else {
			if (equipos.size() < 2) {
				JOptionPane.showMessageDialog(this, "Debes crear al menos dos equipos para poder seleccionar uno.");
			} else {
				String equipoSeleccionado = equiposList.getSelectedValue();				
				modelo.removeElement(equipoSeleccionado);
				String equipoNoSeleccionado = modelo.getElementAt(0);
				dispose();
				JOptionPane.showMessageDialog(this, "Has seleccionado el equipo: " + equipoSeleccionado);
				VentanaCombatePrevio combatePrevio = new VentanaCombatePrevio(equipoSeleccionado, equipoNoSeleccionado);
			}
		}
	}



}
