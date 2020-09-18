package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import dto.PersonaDTO;
import javax.swing.ImageIcon;

public class Contacto extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Color inicial = new Color(74, 72, 75);
	private Color ratonEncima = new Color(81, 79, 82);
	private Color seleccionado = new Color(130, 129,113);
	
	public Contacto(PersonaDTO p) {
		
		setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.GRAY));
		setLocation(10, 10);
		setSize(446, 145);
		setBackground(inicial);
		setLayout(null);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!estaSeleccionado()) {
					setBackground(ratonEncima);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				if(!estaSeleccionado()) {
					setBackground(inicial);
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(estaSeleccionado()) {
					setBackground(ratonEncima);
				}
				else {
					setBackground(seleccionado);
				}
			}
		});
		
		JLabel lblNombre = new JLabel(p.getNombre());
		lblNombre.setForeground(new Color(255, 153, 51));
		lblNombre.setFont(new Font("SansSerif", Font.BOLD, 17));
		lblNombre.setBounds(95, 11, 181, 20);
		add(lblNombre);
		
		JLabel lblTelefono = new JLabel(p.getTelefono());
		lblTelefono.setHorizontalAlignment(SwingConstants.LEFT);
		lblTelefono.setForeground(new Color(255, 153, 51));
		lblTelefono.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblTelefono.setBounds(95, 42, 234, 20);
		add(lblTelefono);
		
		JLabel lblCorreo = new JLabel("Correo: " + p.getCorreo());
		lblCorreo.setForeground(new Color(255, 153, 51));
		lblCorreo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblCorreo.setBounds(95, 72, 341, 17);
		add(lblCorreo);
		
		JLabel lblCumpleanios = new JLabel("Cumpleaños: " + p.getFecha_cumple());
		lblCumpleanios.setForeground(new Color(255, 153, 51));
		lblCumpleanios.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblCumpleanios.setBounds(10, 117, 426, 14);
		add(lblCumpleanios);
		
		
		String tipo = p.getTipo_contacto().toUpperCase();
		JLabel lblTipo = new JLabel(tipo);
		switch (tipo) {
			case "TRABAJO":
				lblTipo.setIcon(new ImageIcon(Contacto.class.getResource("/icons/trabajo.png")));
				break;
			case "UNIVERSIDAD":
				lblTipo.setIcon(new ImageIcon(Contacto.class.getResource("/icons/universidad.png")));
				break;
			case "AMIGOS":
				lblTipo.setIcon(new ImageIcon(Contacto.class.getResource("/icons/amigos.png")));
				break;
			case "FAMILIA":
				lblTipo.setIcon(new ImageIcon(Contacto.class.getResource("/icons/familia.png")));
				break;
			default:
				break;
		}
		lblTipo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipo.setForeground(Color.ORANGE);
		lblTipo.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTipo.setBounds(286, 12, 150, 32);
		add(lblTipo);
		
		JLabel lblDomicilio = new JLabel("Domicilio:" + p.getDomicilio().getCalle());
		lblDomicilio.setForeground(new Color(255, 153, 51));
		lblDomicilio.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblDomicilio.setBounds(10, 92, 426, 14);
		add(lblDomicilio);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(Contacto.class.getResource("/icons/contacto.png")));
		lblNewLabel.setBounds(10, 11, 75, 75);
		add(lblNewLabel);
		
	}
	
	
	public boolean estaSeleccionado() {
		return (getBackground() == seleccionado) ?  true :  false;
	}
}
