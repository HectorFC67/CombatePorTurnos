package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import metodos.Personaje;
import metodos.Balistica;
import metodos.Berserker;
import metodos.Tanque;
import metodos.Tipos;

public class VentanaCombate extends JFrame{
	private String imgFondo = "";

	private Berserker berserkerAliado = new Berserker(Tipos.BERSERKER, 150, 20, 4, 5, false);
	private Balistica balisticaAliado = new Balistica(Tipos.BALISTICA, 100, 30, 6, 7, false);
	private Tanque tanqueAliado = new Tanque(Tipos.TANQUE, 200, 10, 8, 2, false);

	private Berserker berserkerEnemigo = new Berserker(Tipos.BERSERKER, 150, 20, 4, 5, false);
	private Balistica balisticaEnemigo = new Balistica(Tipos.BALISTICA, 100, 30, 6, 7, false);
	private Tanque tanqueEnemigo = new Tanque(Tipos.TANQUE, 200, 10, 8, 2, false);

	private JButton boton1 = new JButton();
	private JButton boton2 = new JButton();

	private int nuevaAltura = 256;
	private int nuevoAncho = 270;
	
	private Personaje[] listaPersonajesAliados;
	private Personaje[] listaPersonajesEnemigos;
	
	private JLabel fondo;
	
    private JProgressBar pbSaludAliado;
    private JProgressBar pbEstaminaAliado;
    private JProgressBar pbSaludEnemigo;
    private JProgressBar pbEstaminaEnemigo;
    
    private static int saludMaximaBerserker = 150;
    private static int saludMaximaBalistica = 100;
    private static int saludMaximaTanque = 200;
    
    private static int estaminaMaximaBerserker = 4;
    private static int estaminaMaximaBalistica = 6;
    private static int estaminaMaximaTanque = 8;
    
    private int saludMaximaAliado = 0;
    private int saludAliado = 0;
    private int saludMaximaEnemigo = 0;
    private int saludEnemigo = 0;
    private int estaminaMaximaAliado = 0;
    private int estaminaAliado = 0;
    private int estaminaMaximaEnemigo = 0;
    private int estaminaEnemigo = 0;
    
    private boolean turnoAliado = true;
    private boolean turnoEnemigo = false;
    
	private static int contadorPartidas = 0;
	
	private boolean primeraVez = true;
	
	private int contadorMuertesEnemigo = 0;
	private int contadorMuertesAliado = 0;
	
	int coolDownHabilidadAliado = 0;
	int coolDownHabilidadEnemigo = 0;
	
	public VentanaCombate(String[] equipoSeleccionadoList, String[] equipoNoSeleccionadoList, String usuario1, String usuario2) {
		super("Juego de combate");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelBarrasAliado = new JPanel();
		pbSaludAliado = new JProgressBar();
		pbSaludAliado.setForeground(new Color(0, 255, 0));
		pbEstaminaAliado = new JProgressBar();
		pbEstaminaAliado.setForeground(new Color(75, 0, 130));
		panelBarrasAliado.add(pbSaludAliado);
		panelBarrasAliado.add(pbEstaminaAliado);
		
		JPanel panelBarrasEnemigo = new JPanel();
		pbSaludEnemigo = new JProgressBar();
		pbSaludEnemigo.setForeground(new Color(0, 255, 0));
		pbEstaminaEnemigo = new JProgressBar();
		pbEstaminaEnemigo.setForeground(new Color(75, 0, 130));
		panelBarrasEnemigo.add(pbSaludEnemigo);
		panelBarrasEnemigo.add(pbEstaminaEnemigo);
		
		JPanel panelBarras = new JPanel();
		panelBarras.add(panelBarrasAliado, BorderLayout.WEST);
		panelBarras.add(panelBarrasEnemigo, BorderLayout.EAST);
		
		add(panelBarras, BorderLayout.NORTH);
		
		imgFondo = emparejamiento(equipoSeleccionadoList, equipoNoSeleccionadoList);
		
		if(primeraVez == true) {
			inicializarTurnos();
			primeraVez = false;
		}
		
		fondo = new JLabel(new ImageIcon(imgFondo));
		fondo.setSize(800, 500);
		add(fondo);
		
		JPanel panelAliado = new JPanel();

		JButton atacarAliado = new JButton("Atacar");
		JButton descansarAliado = new JButton("Descansar");
		JButton cambiarPersonajeAliado = new JButton("Cambiar Guerrero");
		JButton habilidadAliado = new JButton("Habilidad");
		
		atacarAliado.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				if(turnoAliado == true) {
					if(listaPersonajesAliados[0].getEstamina() <= 0) {
						JOptionPane.showMessageDialog(null, usuario1 + ", no tienes estamina, te sugiero descansar o cambiar de guerrero.");
					}else {
						listaPersonajesAliados[0].calcularDanyo(listaPersonajesEnemigos[0]);
						pbSaludEnemigo.setValue(listaPersonajesEnemigos[0].getSalud());
						pbEstaminaAliado.setValue(listaPersonajesAliados[0].getEstamina());
						turnoAliado = false;
						turnoEnemigo = true;
						if(coolDownHabilidadAliado>0) {
							coolDownHabilidadAliado--;
						}
						if(listaPersonajesEnemigos[0].isMuerto() == true) {
							contadorMuertesEnemigo++;
							if(contadorMuertesEnemigo == 3) {
								dispose();
								VentanaVictoria ventanaVictoria = new VentanaVictoria(1, usuario1, usuario2);
				                ventanaVictoria.setVisible(true);
							}else {
								ventanaCambiarPersonajeEnemigo(equipoSeleccionadoList, equipoNoSeleccionadoList);
							}					
						}
					}	
				}else if(turnoAliado == false) {
					JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno de " + usuario2);
				}
				
			}
		});
		
		descansarAliado.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				if(turnoAliado == true) {
					if(listaPersonajesAliados[0].getEstamina() == estaminaMaximaAliado) {
						JOptionPane.showMessageDialog(null, usuario1 + ", tienes la estamina al maximo, te sugiero atacar o cambiar de guerrero");
					}else {
						listaPersonajesAliados[0].descansar();
						pbEstaminaAliado.setValue(listaPersonajesAliados[0].getEstamina());
						turnoAliado = false;
						turnoEnemigo = true;
						if(coolDownHabilidadAliado>0) {
							coolDownHabilidadAliado--;
						}
					}
				} else if(turnoAliado == false) {
					JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno de " + usuario2);
				}
			}
		});
		
		cambiarPersonajeAliado.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (turnoAliado) {
		            if (contadorMuertesAliado == 2) {
		                cambiarPersonajeAliado.setEnabled(false);
		                JOptionPane.showMessageDialog(null, "No puedes cambiar de personaje. Has alcanzado el límite de muertes aliadas.");
		            } else {
		                ventanaCambiarPersonajeAliado(equipoSeleccionadoList, equipoNoSeleccionadoList);
		                turnoAliado = false;
		                turnoEnemigo = true;
		                coolDownHabilidadAliado = 0;
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno de " + usuario2);
		        }
		    }
		});
		
		habilidadAliado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(turnoAliado == true) {
					if(coolDownHabilidadAliado <= 0) {
						listaPersonajesAliados[0].habilidad(listaPersonajesEnemigos[0]);
						pbSaludAliado.setValue(listaPersonajesAliados[0].getSalud());
						pbSaludEnemigo.setValue(listaPersonajesEnemigos[0].getSalud());
						pbEstaminaEnemigo.setValue(listaPersonajesEnemigos[0].getEstamina());
						pbEstaminaAliado.setValue(listaPersonajesAliados[0].getEstamina());
						turnoAliado = false;
						turnoEnemigo = true;
						coolDownHabilidadAliado = 3;
						if(listaPersonajesEnemigos[0].isMuerto() == true) {
							contadorMuertesEnemigo++;
							if(contadorMuertesEnemigo == 3) {
								dispose();
								VentanaVictoria ventanaVictoria = new VentanaVictoria(1, usuario1, usuario2);
				                ventanaVictoria.setVisible(true);
							}else {
								ventanaCambiarPersonajeEnemigo(equipoSeleccionadoList, equipoNoSeleccionadoList);
							}					
						}
					}else {
						JOptionPane.showMessageDialog(null, "No puedes realizar tu habilidad aun espera " + coolDownHabilidadAliado + " turno/s");
					}
					
				} else if(turnoAliado == false) {
					JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno de " + usuario2);
				}
			}
		});
			
		panelAliado.add(atacarAliado);
		panelAliado.add(descansarAliado);
		panelAliado.add(habilidadAliado);
		panelAliado.add(cambiarPersonajeAliado);

		JPanel panelEnemigo = new JPanel();

		JButton atacarEnemigo = new JButton("Atacar");
		JButton descansarEnemigo = new JButton("Descansar");
		JButton cambiarPersonajeEnemigo = new JButton("Cambiar Guerrero");
		JButton habilidadEnemigo = new JButton("Habilidad");

		atacarEnemigo.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				if(turnoEnemigo == true) {
					if(listaPersonajesEnemigos[0].getEstamina() <= 0) {
						JOptionPane.showMessageDialog(null, usuario2 + ", no tienes estamina, te sugiero descansar o cambiar de guerrero.");
					}else {
						listaPersonajesEnemigos[0].calcularDanyo(listaPersonajesAliados[0]);
						pbSaludAliado.setValue(listaPersonajesAliados[0].getSalud());
						pbEstaminaEnemigo.setValue(listaPersonajesEnemigos[0].getEstamina());
						turnoEnemigo = false;
						turnoAliado = true;
						if(coolDownHabilidadEnemigo>0) {
							coolDownHabilidadEnemigo--;
						}
						if(listaPersonajesAliados[0].isMuerto() == true) {
							contadorMuertesAliado++;
							if(contadorMuertesAliado == 3) {
				                dispose();
								VentanaVictoria ventanaVictoria = new VentanaVictoria(2, usuario1, usuario2);
				                ventanaVictoria.setVisible(true);
							}else {
								ventanaCambiarPersonajeAliado(equipoSeleccionadoList, equipoNoSeleccionadoList);
							}						
						}
					}
									
				}else if(turnoEnemigo == false) {
					JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno de "+usuario1);
				}
			}
		});
		
		descansarEnemigo.addActionListener(new ActionListener() {        
			public void actionPerformed(ActionEvent e) {
				if(turnoEnemigo == true) {
					if(listaPersonajesEnemigos[0].getEstamina() == estaminaMaximaEnemigo) {
						JOptionPane.showMessageDialog(null, usuario2 + ", tienes la estamina al maximo, te sugiero atacar o cambiar de guerrero");
					}else {
						listaPersonajesEnemigos[0].descansar();
						pbEstaminaEnemigo.setValue(listaPersonajesEnemigos[0].getEstamina());
						turnoEnemigo = false;
						turnoAliado = true;
						if(coolDownHabilidadEnemigo>0) {
							coolDownHabilidadEnemigo--;
						}
					}
					
				}else if(turnoEnemigo == false) {
					JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno de " + usuario1);
				}
			}
		});
		
		cambiarPersonajeEnemigo.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (turnoEnemigo) {
		            if (contadorMuertesEnemigo == 2) {
		                cambiarPersonajeEnemigo.setEnabled(false);
		                JOptionPane.showMessageDialog(null, "No puedes cambiar de personaje. Has alcanzado el límite de muertes enemigas.");
		            } else {
		                ventanaCambiarPersonajeEnemigo(equipoSeleccionadoList, equipoNoSeleccionadoList);
		                turnoAliado = true;
		                turnoEnemigo = false;
		                coolDownHabilidadEnemigo = 0;
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno de " + usuario1);
		        }
		    }
		});


		habilidadEnemigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(turnoEnemigo == true) {
					if(coolDownHabilidadEnemigo <= 0) {
						listaPersonajesEnemigos[0].habilidad(listaPersonajesAliados[0]);
						pbSaludAliado.setValue(listaPersonajesAliados[0].getSalud());
						pbSaludEnemigo.setValue(listaPersonajesEnemigos[0].getSalud());
						pbEstaminaEnemigo.setValue(listaPersonajesEnemigos[0].getEstamina());
						pbEstaminaAliado.setValue(listaPersonajesAliados[0].getEstamina());
						turnoAliado = true;
						turnoEnemigo = false;
						coolDownHabilidadEnemigo = 3;
						if(listaPersonajesAliados[0].isMuerto() == true) {
							contadorMuertesAliado++;
							if(contadorMuertesAliado == 3) {
				                dispose();
								VentanaVictoria ventanaVictoria = new VentanaVictoria(2, usuario1, usuario2);
				                ventanaVictoria.setVisible(true);
							}else {
								ventanaCambiarPersonajeAliado(equipoSeleccionadoList, equipoNoSeleccionadoList);
							}						
						}
					}else {
						JOptionPane.showMessageDialog(null, "No puedes realizar tu habilidad aun espera " + coolDownHabilidadEnemigo + " turno/s");
					}
					
				} else if(turnoAliado == false) {
					JOptionPane.showMessageDialog(null, "No puedes realizar ninguna acción porque es el turno de " + usuario1);
				}
			}
		});
		
		panelEnemigo.add(atacarEnemigo);
		panelEnemigo.add(descansarEnemigo);
		panelEnemigo.add(habilidadEnemigo);
		panelEnemigo.add(cambiarPersonajeEnemigo); 

		JPanel panelBotones = new JPanel( new BorderLayout() );

		panelBotones.add(panelAliado, BorderLayout.WEST);
		panelBotones.add(panelEnemigo, BorderLayout.EAST);

		add(panelBotones, BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private String emparejamiento(String[] equipoSeleccionadoList, String[] equipoNoSeleccionadoList) 
	{
		listaPersonajesAliados = new Personaje[3];
		listaPersonajesEnemigos = new Personaje[3];
		
		String primerJugadorSeleccionado = equipoSeleccionadoList[0].toLowerCase();
		String primerJugadorNoSeleccionado = equipoNoSeleccionadoList[0].toLowerCase();
		String segundoJugadorSeleccionado = equipoSeleccionadoList[1].toUpperCase();
		String segundoJugadorNoSeleccionado = equipoNoSeleccionadoList[1].toUpperCase();
		//String tercerJugadorSeleccionado = equipoSeleccionadoList[2].toUpperCase();
		//String tercerJugadorNoSeleccionado = equipoNoSeleccionadoList[2].toUpperCase();
		
		if (primerJugadorSeleccionado.equals("berserker") && primerJugadorNoSeleccionado.equals("berserker")) {

			imgFondo = "img/combate-berserker-berserker.png";
			listaPersonajesAliados[0] = berserkerAliado;
			listaPersonajesEnemigos[0] = berserkerEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaBerserker;
			saludMaximaEnemigo = saludMaximaBerserker;
			estaminaMaximaAliado = estaminaMaximaBerserker;
			estaminaMaximaEnemigo = estaminaMaximaBerserker;
			
			if(segundoJugadorSeleccionado.equals(balisticaAliado.getTipo().name())) {
				listaPersonajesAliados[1] = balisticaAliado;
				listaPersonajesAliados[2] = tanqueAliado;
			}else if(segundoJugadorNoSeleccionado.equals(balisticaEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = balisticaEnemigo;
				listaPersonajesEnemigos[2] = tanqueEnemigo;
			}else if(segundoJugadorSeleccionado.equals(tanqueAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = balisticaAliado;
			}else if(segundoJugadorNoSeleccionado.equals(tanqueEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = tanqueEnemigo;
				listaPersonajesEnemigos[2] = balisticaEnemigo;
			}

		} else if (primerJugadorSeleccionado.equals("balistica") && primerJugadorNoSeleccionado.equals("balistica")) {

			imgFondo = "img/combate-balistica-balistica.png";
			listaPersonajesAliados[0] = balisticaAliado;
			listaPersonajesEnemigos[0] = balisticaEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaBalistica;
			saludMaximaEnemigo = saludMaximaBalistica;
			estaminaMaximaAliado = estaminaMaximaBalistica;
			estaminaMaximaEnemigo = estaminaMaximaBalistica;
			
			if(segundoJugadorSeleccionado.equals(berserkerAliado.getTipo().name())) {
				listaPersonajesAliados[1] = berserkerAliado;
				listaPersonajesAliados[2] = tanqueAliado;
			}else if(segundoJugadorNoSeleccionado.equals(berserkerEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = berserkerEnemigo;
				listaPersonajesEnemigos[2] = tanqueEnemigo;
			}else if(segundoJugadorSeleccionado.equals(tanqueAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = berserkerAliado;
			}else if(segundoJugadorNoSeleccionado.equals(tanqueEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = tanqueEnemigo;
				listaPersonajesEnemigos[2] = berserkerEnemigo;
			}

		} else if (primerJugadorSeleccionado.equals("tanque") && primerJugadorNoSeleccionado.equals("tanque")) {

			imgFondo = ("img/combate-tanque-tanque.png");
			listaPersonajesAliados[0] = tanqueAliado;
			listaPersonajesEnemigos[0] = tanqueEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaTanque;
			saludMaximaEnemigo = saludMaximaTanque;
			estaminaMaximaAliado = estaminaMaximaTanque;
			estaminaMaximaEnemigo = estaminaMaximaTanque;
			
			if(segundoJugadorSeleccionado.equals(berserkerAliado.getTipo().name())) {
				listaPersonajesAliados[1] = berserkerAliado;
				listaPersonajesAliados[2] = balisticaAliado;
			}else if(segundoJugadorNoSeleccionado.equals(berserkerEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = berserkerEnemigo;
				listaPersonajesEnemigos[2] = balisticaEnemigo;
			}else if(segundoJugadorSeleccionado.equals(balisticaAliado.getTipo().name())) {
				listaPersonajesAliados[1] = balisticaAliado;
				listaPersonajesAliados[2] = berserkerAliado;
			}else if(segundoJugadorNoSeleccionado.equals(balisticaEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = balisticaEnemigo;
				listaPersonajesEnemigos[2] = berserkerEnemigo;
			}

		} else if (primerJugadorSeleccionado.equals("berserker") && primerJugadorNoSeleccionado.equals("balistica")) {

			imgFondo = ("img/combate-berserker-balistica.png");
			listaPersonajesAliados[0] = berserkerAliado;
			listaPersonajesEnemigos[0] = balisticaEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaBerserker;
			saludMaximaEnemigo = saludMaximaBalistica;
			estaminaMaximaAliado = estaminaMaximaBerserker;
			estaminaMaximaEnemigo = estaminaMaximaBalistica;
			
			if(segundoJugadorSeleccionado.equals(balisticaAliado.getTipo().name())) {
				listaPersonajesAliados[1] = balisticaAliado;
				listaPersonajesAliados[2] = tanqueAliado;
			}else if(segundoJugadorNoSeleccionado.equals(berserkerEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = berserkerEnemigo;
				listaPersonajesEnemigos[2] = tanqueEnemigo;
			}else if(segundoJugadorSeleccionado.equals(tanqueAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = balisticaAliado;
			}else if(segundoJugadorNoSeleccionado.equals(tanqueEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = tanqueEnemigo;
				listaPersonajesEnemigos[2] = berserkerEnemigo;
			}
			
		} else if (primerJugadorSeleccionado.equals("berserker") && primerJugadorNoSeleccionado.equals("tanque")) {

			imgFondo = "img/combate-berserker-tanque.png";
			listaPersonajesAliados[0] = berserkerAliado;
			listaPersonajesEnemigos[0] = tanqueEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaBerserker;
			saludMaximaEnemigo = saludMaximaTanque;
			estaminaMaximaAliado = estaminaMaximaBerserker;
			estaminaMaximaEnemigo = estaminaMaximaTanque;
			
			if(segundoJugadorSeleccionado.equals(balisticaAliado.getTipo().name())) {
				listaPersonajesAliados[1] = balisticaAliado;
				listaPersonajesAliados[2] = tanqueAliado;
			}else if(segundoJugadorNoSeleccionado.equals(berserkerEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = berserkerEnemigo;
				listaPersonajesEnemigos[2] = balisticaEnemigo;
			}else if(segundoJugadorSeleccionado.equals(tanqueAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = balisticaAliado;
			}else if(segundoJugadorNoSeleccionado.equals(balisticaEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = balisticaEnemigo;
				listaPersonajesEnemigos[2] = berserkerEnemigo;
			}

		} else if (primerJugadorSeleccionado.equals("balistica") && primerJugadorNoSeleccionado.equals("tanque")) {

			imgFondo = "img/combate-balistica-tanque.png";
			listaPersonajesAliados[0] = balisticaAliado;
			listaPersonajesEnemigos[0] = tanqueEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaBalistica;
			saludMaximaEnemigo = saludMaximaTanque;
			estaminaMaximaAliado = estaminaMaximaBalistica;
			estaminaMaximaEnemigo = estaminaMaximaTanque;
			
			if(segundoJugadorSeleccionado.equals(berserkerAliado.getTipo().name())) {
				listaPersonajesAliados[1] = berserkerAliado;
				listaPersonajesAliados[2] = tanqueAliado;
			}else if(segundoJugadorNoSeleccionado.equals(balisticaEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = balisticaEnemigo;
				listaPersonajesEnemigos[2] = berserkerEnemigo;
			}else if(segundoJugadorSeleccionado.equals(tanqueAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = berserkerAliado;
			}else if(segundoJugadorNoSeleccionado.equals(berserkerEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = berserkerEnemigo;
				listaPersonajesEnemigos[2] = balisticaEnemigo;
			}

		} else if ( primerJugadorSeleccionado.equals("balistica") && primerJugadorNoSeleccionado.equals("berserker")) {

			imgFondo = "img/combate-balistica-berserker.png";
			listaPersonajesAliados[0] = balisticaAliado;
			listaPersonajesEnemigos[0] = berserkerEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaBalistica;
			saludMaximaEnemigo = saludMaximaBerserker;
			estaminaMaximaAliado = estaminaMaximaBalistica;
			estaminaMaximaEnemigo = estaminaMaximaBerserker;
			
			if(segundoJugadorSeleccionado.equals(berserkerAliado.getTipo().name())) {
				listaPersonajesAliados[1] = berserkerAliado;
				listaPersonajesAliados[2] = tanqueAliado;
			}else if(segundoJugadorNoSeleccionado.equals(balisticaEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = balisticaEnemigo;
				listaPersonajesEnemigos[2] = tanqueEnemigo;
			}else if(segundoJugadorSeleccionado.equals(tanqueAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = berserkerAliado;
			}else if(segundoJugadorNoSeleccionado.equals(tanqueEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = tanqueEnemigo;
				listaPersonajesEnemigos[2] = balisticaEnemigo;
			}

		} else if (primerJugadorSeleccionado.equals("tanque") && primerJugadorNoSeleccionado.equals("berserker")) {

			imgFondo = "img/combate-tanque-berserker.png";
			listaPersonajesAliados[0] = tanqueAliado;
			listaPersonajesEnemigos[0] = berserkerEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaTanque;
			saludMaximaEnemigo = saludMaximaBerserker;
			estaminaMaximaAliado = estaminaMaximaTanque;
			estaminaMaximaEnemigo = estaminaMaximaBerserker;
			
			if(segundoJugadorSeleccionado.equals(balisticaAliado.getTipo().name())) {
				listaPersonajesAliados[1] = balisticaAliado;
				listaPersonajesAliados[2] = berserkerAliado;
			}else if(segundoJugadorNoSeleccionado.equals(balisticaEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = balisticaEnemigo;
				listaPersonajesEnemigos[2] = tanqueEnemigo;
			}else if(segundoJugadorSeleccionado.equals(berserkerAliado.getTipo().name())) {
				listaPersonajesAliados[1] = tanqueAliado;
				listaPersonajesAliados[2] = balisticaAliado;
			}else if(segundoJugadorNoSeleccionado.equals(tanqueEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = tanqueEnemigo;
				listaPersonajesEnemigos[2] = balisticaEnemigo;
			}

		} else if (primerJugadorSeleccionado.equals("tanque") && primerJugadorNoSeleccionado.equals("balistica")) {

			imgFondo = "img/combate-tanque-balistica.png";
			listaPersonajesAliados[0] = tanqueAliado;
			listaPersonajesEnemigos[0] = balisticaEnemigo;
			
			saludAliado = listaPersonajesAliados[0].getSalud();
			saludEnemigo = listaPersonajesEnemigos[0].getSalud();
			estaminaAliado = listaPersonajesAliados[0].getEstamina();
			estaminaEnemigo = listaPersonajesEnemigos[0].getEstamina();
			saludMaximaAliado = saludMaximaTanque;
			saludMaximaEnemigo = saludMaximaBalistica;
			estaminaMaximaAliado = estaminaMaximaTanque;
			estaminaMaximaEnemigo = estaminaMaximaBalistica;
			
			if(segundoJugadorSeleccionado.equals(balisticaAliado.getTipo().name())) {
				listaPersonajesAliados[1] = balisticaAliado;
				listaPersonajesAliados[2] = berserkerAliado;
			}else if(segundoJugadorNoSeleccionado.equals(berserkerEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = berserkerEnemigo;
				listaPersonajesEnemigos[2] = tanqueEnemigo;
			}else if(segundoJugadorSeleccionado.equals(berserkerAliado.getTipo().name())) {
				listaPersonajesAliados[1] = berserkerAliado;
				listaPersonajesAliados[2] = balisticaAliado;
			}else if(segundoJugadorNoSeleccionado.equals(tanqueEnemigo.getTipo().name())) {
				listaPersonajesEnemigos[1] = tanqueEnemigo;
				listaPersonajesEnemigos[2] = berserkerEnemigo;
			}
		} else {

			System.out.println("No se encontro la imagen de fondo");
		}
        establecerSalud(saludMaximaAliado, saludAliado, pbSaludAliado);
        establecerEstamina(estaminaMaximaAliado, estaminaAliado, pbEstaminaAliado);
		
        establecerSalud(saludMaximaEnemigo, saludEnemigo, pbSaludEnemigo);
        establecerEstamina(estaminaMaximaEnemigo, estaminaEnemigo, pbEstaminaEnemigo);
        
        return imgFondo;
	}

	public void establecerSalud(int saludMaxima, int saludActual, JProgressBar pbSalud) {
		pbSalud.setMaximum(saludMaxima);
		pbSalud.setValue(saludActual);
	}
	
	public void establecerEstamina(int estaminaMaxima, int estaminaActual, JProgressBar pbEstamina) {
		pbEstamina.setMaximum(estaminaMaxima);
		pbEstamina.setValue(estaminaActual);
	}
	
	public void inicializarTurnos(){
		if(listaPersonajesAliados[0].getVelocidad() >= listaPersonajesEnemigos[0].getVelocidad()) {
			turnoAliado = true;
			turnoEnemigo = false;
		}else if(listaPersonajesAliados[0].getVelocidad() < listaPersonajesEnemigos[0].getVelocidad()) {
			turnoAliado = false;
			turnoEnemigo = true;
		}
	}
	
	public void ventanaCambiarPersonajeAliado(String[] equipoSeleccionadoList, String[] equipoNoSeleccionadoList) {
		// Crear la nueva ventana
		JDialog seleccionaPersonaje = new JDialog();
		// Configurar la ventana
		seleccionaPersonaje.setTitle("Selecciona Personaje");
		seleccionaPersonaje.setSize(600, 600);

		// Crear el panel de botones
		JPanel panelBotones = new JPanel();
		// Crear los botones
		String rutaBerserker = "img/berserker(local).png";
		String rutaBalistica = "img/balistica(local).png";
		String rutaTanque = "img/tanque(local).png";
		
		String rutaBerserkerMuerto = "img/berserker(local)-muerto.png";
		String rutaBalisticaMuerto = "img/balistica(local)-muerto.png";
		String rutaTanqueMuerto = "img/tanque(local)-muerto.png";

		ImageIcon imagenOriginal = new ImageIcon(rutaBerserker);
		Image imagenEtiqueta = imagenOriginal.getImage();
		Image nuevaImagen = imagenEtiqueta.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon BerserkerImagen = new ImageIcon(nuevaImagen);

		ImageIcon imagenOriginal1 = new ImageIcon(rutaBalistica);
		Image imagenEtiqueta1 = imagenOriginal1.getImage();
		Image nuevaImagen1 = imagenEtiqueta1.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon BalisticaImagen = new ImageIcon(nuevaImagen1);

		ImageIcon imagenOriginal2 = new ImageIcon(rutaTanque);
		Image imagenEtiqueta2 = imagenOriginal2.getImage();
		Image nuevaImagen2 = imagenEtiqueta2.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon TanqueImagen = new ImageIcon(nuevaImagen2);
		
		ImageIcon imagenOriginal3 = new ImageIcon(rutaBerserkerMuerto);
		Image imagenEtiqueta3 = imagenOriginal3.getImage();
		Image nuevaImagen3 = imagenEtiqueta3.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon BerserkerImagenMuerto = new ImageIcon(nuevaImagen3);
		
		ImageIcon imagenOriginal4 = new ImageIcon(rutaBalisticaMuerto);
		Image imagenEtiqueta4 = imagenOriginal4.getImage();
		Image nuevaImagen4 = imagenEtiqueta4.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon BalisticaImagenMuerto = new ImageIcon(nuevaImagen4);
		
		ImageIcon imagenOriginal5 = new ImageIcon(rutaTanqueMuerto);
		Image imagenEtiqueta5 = imagenOriginal5.getImage();
		Image nuevaImagen5 = imagenEtiqueta5.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon TanqueImagenMuerto = new ImageIcon(nuevaImagen5);
		
		if (equipoSeleccionadoList[1].toLowerCase().equals("berserker") && equipoSeleccionadoList[2].toLowerCase().equals("balistica")) {
			if(berserkerAliado.isMuerto() == true) {
				boton1 = new JButton("Este personaje esta debilitado", BerserkerImagenMuerto);
			}else {
				boton1 = new JButton("Selecciona Personaje", BerserkerImagen);
			}
			
			if(balisticaAliado.isMuerto() == true) {
				boton2 = new JButton("Este personaje esta debilitado", BalisticaImagenMuerto);
			}else {
				boton2 = new JButton("Selecciona Personaje", BalisticaImagen);
			}
		} else if(equipoSeleccionadoList[1].toLowerCase().equals("berserker") && equipoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			if(berserkerAliado.isMuerto() == true) {
				boton1 = new JButton("Este personaje esta debilitado", BerserkerImagenMuerto);
			}else {
				boton1 = new JButton("Selecciona Personaje", BerserkerImagen);
			}
			
			if(tanqueAliado.isMuerto() == true) {
				boton2 = new JButton("Este personaje esta debilitado", TanqueImagenMuerto);
			}else {
				boton2 = new JButton("Selecciona Personaje", TanqueImagen);
			}
		} else if(equipoSeleccionadoList[1].toLowerCase().equals("balistica") && equipoSeleccionadoList[2].toLowerCase().equals("berserker")) {
			if(balisticaAliado.isMuerto() == true) {
				boton1 = new JButton("Este personaje esta debilitado", BalisticaImagenMuerto);
			}else {
				boton1 = new JButton("Selecciona Personaje", BalisticaImagen);
			}
			
			if(berserkerAliado.isMuerto() == true) {
				boton2 = new JButton("Este personaje esta debilitado", BerserkerImagenMuerto);
			}else {
				boton2 = new JButton("Selecciona Personaje", BerserkerImagen);
			}
		} else if(equipoSeleccionadoList[1].toLowerCase().equals("balistica") && equipoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			if(balisticaAliado.isMuerto() == true) {
				boton1 = new JButton("Este personaje esta debilitado", BalisticaImagenMuerto);
			}else {
				boton1 = new JButton("Selecciona Personaje", BalisticaImagen);
			}
			
			if(tanqueAliado.isMuerto() == true) {
				boton2 = new JButton("Este personaje esta debilitado", TanqueImagenMuerto);
			}else {
				boton2 = new JButton("Selecciona Personaje", TanqueImagen);
			}
		} else if(equipoSeleccionadoList[1].toLowerCase().equals("tanque") && equipoSeleccionadoList[2].toLowerCase().equals("berserker")) {
			if(tanqueAliado.isMuerto() == true) {
				boton1 = new JButton("Este personaje esta debilitado", TanqueImagenMuerto);
			}else {
				boton1 = new JButton("Selecciona Personaje", TanqueImagen);
			}
			
			if(berserkerAliado.isMuerto() == true) {
				boton2 = new JButton("Este personaje esta debilitado", BerserkerImagenMuerto);
			}else {
				boton2 = new JButton("Selecciona Personaje", BerserkerImagen);
			}
		} else if(equipoSeleccionadoList[1].toLowerCase().equals("tanque") && equipoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			if(tanqueAliado.isMuerto() == true) {
				boton1 = new JButton("Este personaje esta debilitado", TanqueImagenMuerto);
			}else {
				boton1 = new JButton("Selecciona Personaje", TanqueImagen);
			}
			
			if(balisticaAliado.isMuerto() == true) {
				boton2 = new JButton("Este personaje esta debilitado", BalisticaImagenMuerto);
			}else {
				boton2 = new JButton("Selecciona Personaje", BalisticaImagen);
			}
		}

		boton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = equipoSeleccionadoList[1];
				equipoSeleccionadoList[1] = equipoSeleccionadoList[0];
				equipoSeleccionadoList[0] = temp;
				imgFondo = emparejamiento(equipoSeleccionadoList, equipoNoSeleccionadoList);
				getContentPane().remove(fondo); // Eliminar fondo
				fondo = new JLabel(new ImageIcon(imgFondo));
	            fondo.setSize(800, 500);
	            add(fondo, BorderLayout.CENTER);
	                
	            establecerSalud(saludMaximaAliado, saludAliado, pbSaludAliado);
	            establecerEstamina(estaminaMaximaAliado, estaminaAliado, pbEstaminaAliado);
	            
	        	validate(); // Validar cambios
	            repaint(); // Repintar ventana
	            // Cerrar el JDialog actual
				Window window = SwingUtilities.getWindowAncestor(boton1);
				window.dispose();	
			}
		});
		
		boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = equipoSeleccionadoList[2];
				equipoSeleccionadoList[2] = equipoSeleccionadoList[0];
				equipoSeleccionadoList[0] = temp;
				imgFondo = emparejamiento(equipoSeleccionadoList, equipoNoSeleccionadoList);
				getContentPane().remove(fondo); // Eliminar fondo
				fondo = new JLabel(new ImageIcon(imgFondo));
	            fondo.setSize(800, 500);
	            add(fondo, BorderLayout.CENTER);
	               
	            establecerSalud(saludMaximaAliado, saludAliado, pbSaludAliado);
	            establecerEstamina(estaminaMaximaAliado, estaminaAliado, pbEstaminaAliado);
	                
	            validate(); // Validar cambios
	            repaint(); // Repintar ventana
				// Cerrar el JDialog actual
				Window window = SwingUtilities.getWindowAncestor(boton1);
				window.dispose();		
			}
		});

		// Agregar los botones al panel
		panelBotones.add(boton1);
		panelBotones.add(boton2);
		// Agregar el panel de botones a la ventana
		seleccionaPersonaje.add(panelBotones, BorderLayout.CENTER);
		
		if(boton1.getText() == "Este personaje esta debilitado") {
			boton1.setEnabled(false);
		}else {
			boton1.setEnabled(true);
		}
		
		if(boton2.getText() == "Este personaje esta debilitado") {
			boton2.setEnabled(false);
		}else {
			boton2.setEnabled(true);
		}
		
		seleccionaPersonaje.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		seleccionaPersonaje.setModal(true); // Bloquear la ventana principal mientras esta ventana está abierta
		seleccionaPersonaje.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Cerrar la ventana al presionar la "X"
		seleccionaPersonaje.setVisible(true);
	}
	
	public void ventanaCambiarPersonajeEnemigo(String[] equipoSeleccionadoList, String[] equipoNoSeleccionadoList) {
		// Crear la nueva ventana
		JDialog seleccionaPersonaje = new JDialog();
		// Configurar la ventana
		seleccionaPersonaje.setTitle("Selecciona Personaje");
		seleccionaPersonaje.setSize(600, 600);

		// Crear el panel de botones
		JPanel panelBotones = new JPanel();
		// Crear los botones
		String rutaBerserker = "img/berserker(enemigo).png";
		String rutaBalistica = "img/balistica(enemigo).png";
		String rutaTanque = "img/tanque(enemigo).png";
		
		String rutaBerserkerMuerto = "img/berserker(enemigo)-muerto.png";
		String rutaBalisticaMuerto = "img/balistica(enemigo)-muerto.png";
		String rutaTanqueMuerto = "img/tanque(enemigo)-muerto.png";
		
		ImageIcon imagenOriginal = new ImageIcon(rutaBerserker);
		Image imagenEtiqueta = imagenOriginal.getImage();
		Image nuevaImagen = imagenEtiqueta.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon BerserkerImagen = new ImageIcon(nuevaImagen);

		ImageIcon imagenOriginal1 = new ImageIcon(rutaBalistica);
		Image imagenEtiqueta1 = imagenOriginal1.getImage();
		Image nuevaImagen1 = imagenEtiqueta1.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon BalisticaImagen = new ImageIcon(nuevaImagen1);

		ImageIcon imagenOriginal2 = new ImageIcon(rutaTanque);
		Image imagenEtiqueta2 = imagenOriginal2.getImage();
		Image nuevaImagen2 = imagenEtiqueta2.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon TanqueImagen = new ImageIcon(nuevaImagen2);

		ImageIcon imagenOriginal3 = new ImageIcon(rutaBerserkerMuerto);
		Image imagenEtiqueta3 = imagenOriginal3.getImage();
		Image nuevaImagen3 = imagenEtiqueta3.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon BerserkerImagenMuerto = new ImageIcon(nuevaImagen3);
		
		ImageIcon imagenOriginal4 = new ImageIcon(rutaBalisticaMuerto);
		Image imagenEtiqueta4 = imagenOriginal4.getImage();
		Image nuevaImagen4 = imagenEtiqueta4.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon BalisticaImagenMuerto = new ImageIcon(nuevaImagen4);
		
		ImageIcon imagenOriginal5 = new ImageIcon(rutaTanqueMuerto);
		Image imagenEtiqueta5 = imagenOriginal5.getImage();
		Image nuevaImagen5 = imagenEtiqueta5.getScaledInstance(nuevoAncho, nuevaAltura, Image.SCALE_SMOOTH);
		ImageIcon TanqueImagenMuerto = new ImageIcon(nuevaImagen5);
		
		if (equipoNoSeleccionadoList[1].toLowerCase().equals("berserker") && equipoNoSeleccionadoList[2].toLowerCase().equals("balistica")) {
			if(berserkerEnemigo.isMuerto() == true) {
				boton1 = new JButton("Este personaje esta debilitado", BerserkerImagenMuerto);
			}else {
				boton1 = new JButton("Selecciona Personaje", BerserkerImagen);
			}
			
			if(balisticaEnemigo.isMuerto() == true) {
				boton2 = new JButton("Este personaje esta debilitado", BalisticaImagenMuerto);
			}else {
				boton2 = new JButton("Selecciona Personaje", BalisticaImagen);
			}
		} else if(equipoNoSeleccionadoList[1].toLowerCase().equals("berserker") && equipoNoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			if(berserkerEnemigo.isMuerto() == true) {
				boton1 = new JButton("Este personaje esta debilitado", BerserkerImagenMuerto);
			}else {
				boton1 = new JButton("Selecciona Personaje", BerserkerImagen);
			}
			
			if(tanqueEnemigo.isMuerto() == true) {
				boton2 = new JButton("Este personaje esta debilitado", TanqueImagenMuerto);
			}else {
				boton2 = new JButton("Selecciona Personaje", TanqueImagen);
			}
		} else if(equipoNoSeleccionadoList[1].toLowerCase().equals("balistica") && equipoNoSeleccionadoList[2].toLowerCase().equals("berserker")) {
			if(balisticaEnemigo.isMuerto() == true) {
				boton1 = new JButton("Este personaje esta debilitado", BalisticaImagenMuerto);
			}else {
				boton1 = new JButton("Selecciona Personaje", BalisticaImagen);
			}
			
			if(berserkerEnemigo.isMuerto() == true) {
				boton2 = new JButton("Este personaje esta debilitado", BerserkerImagenMuerto);
			}else {
				boton2 = new JButton("Selecciona Personaje", BerserkerImagen);
			}
		} else if(equipoNoSeleccionadoList[1].toLowerCase().equals("balistica") && equipoNoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			if(balisticaEnemigo.isMuerto() == true) {
				boton1 = new JButton("Este personaje esta debilitado", BalisticaImagenMuerto);
			}else {
				boton1 = new JButton("Selecciona Personaje", BalisticaImagen);
			}
			
			if(tanqueEnemigo.isMuerto() == true) {
				boton2 = new JButton("Este personaje esta debilitado", TanqueImagenMuerto);
			}else {
				boton2 = new JButton("Selecciona Personaje", TanqueImagen);
			}
		} else if(equipoNoSeleccionadoList[1].toLowerCase().equals("tanque") && equipoNoSeleccionadoList[2].toLowerCase().equals("berserker")) {
			if(tanqueEnemigo.isMuerto() == true) {
				boton1 = new JButton("Este personaje esta debilitado", TanqueImagenMuerto);
			}else {
				boton1 = new JButton("Selecciona Personaje", TanqueImagen);
			}
			
			if(berserkerEnemigo.isMuerto() == true) {
				boton2 = new JButton("Este personaje esta debilitado", BerserkerImagenMuerto);
			}else {
				boton2 = new JButton("Selecciona Personaje", BerserkerImagen);
			}
		} else if(equipoNoSeleccionadoList[1].toLowerCase().equals("tanque") && equipoNoSeleccionadoList[2].toLowerCase().equals("tanque")) {
			if(tanqueEnemigo.isMuerto() == true) {
				boton1 = new JButton("Este personaje esta debilitado", TanqueImagenMuerto);
			}else {
				boton1 = new JButton("Selecciona Personaje", TanqueImagen);
			}
			
			if(balisticaEnemigo.isMuerto() == true) {
				boton2 = new JButton("Este personaje esta debilitado", BalisticaImagenMuerto);
			}else {
				boton2 = new JButton("Selecciona Personaje", BalisticaImagen);
			}
		}
		
		boton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = equipoNoSeleccionadoList[1];
				equipoNoSeleccionadoList[1] = equipoNoSeleccionadoList[0];
				equipoNoSeleccionadoList[0] = temp;
				imgFondo = emparejamiento(equipoSeleccionadoList, equipoNoSeleccionadoList);
				getContentPane().remove(fondo); // Eliminar fondo
				fondo = new JLabel(new ImageIcon(imgFondo));
                fondo.setSize(800, 500);
                add(fondo, BorderLayout.CENTER);
                
                establecerSalud(saludMaximaEnemigo, saludEnemigo, pbSaludEnemigo);
                establecerEstamina(estaminaMaximaEnemigo, estaminaEnemigo, pbEstaminaEnemigo);
                
                validate(); // Validar cambios
                repaint(); // Repintar ventana
				// Cerrar el JDialog actual
				Window window = SwingUtilities.getWindowAncestor(boton1);
				window.dispose();
			}
		});
		
		boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = equipoNoSeleccionadoList[2];
				equipoNoSeleccionadoList[2] = equipoNoSeleccionadoList[0];
				equipoNoSeleccionadoList[0] = temp;
				imgFondo = emparejamiento(equipoSeleccionadoList, equipoNoSeleccionadoList);
				getContentPane().remove(fondo); // Eliminar fondo
				fondo = new JLabel(new ImageIcon(imgFondo));
                fondo.setSize(800, 500);
                add(fondo, BorderLayout.CENTER);
                
                establecerSalud(saludMaximaEnemigo, saludEnemigo, pbSaludEnemigo);
                establecerEstamina(estaminaMaximaEnemigo, estaminaEnemigo, pbEstaminaEnemigo);
                
                validate(); // Validar cambios
                repaint(); // Repintar ventana
				// Cerrar el JDialog actual
				Window window = SwingUtilities.getWindowAncestor(boton1);
				window.dispose();
			}
		});
		
		// Agregar los botones al panel
		panelBotones.add(boton1);
		panelBotones.add(boton2);
		// Agregar el panel de botones a la ventana
		seleccionaPersonaje.add(panelBotones, BorderLayout.CENTER);
		
		if(boton1.getText() == "Este personaje esta debilitado") {
			boton1.setEnabled(false);
		}else {
			boton1.setEnabled(true);
		}
		
		if(boton2.getText() == "Este personaje esta debilitado") {
			boton2.setEnabled(false);
		}else {
			boton2.setEnabled(true);
		}
		seleccionaPersonaje.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		seleccionaPersonaje.setModal(true); // Bloquear la ventana principal mientras esta ventana está abierta
		seleccionaPersonaje.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		seleccionaPersonaje.setVisible(true);
	}
}