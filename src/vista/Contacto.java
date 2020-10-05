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
import dto.TipoDTO;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Contacto extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	private JButton btnVerMas;
	
	private Color inicial = new Color(74, 72, 75);
	private Color ratonEncima = new Color(81, 79, 82);
	private Color seleccionado = new Color(130, 129,113);
	
	public Contacto(PersonaDTO p) 
	{	
		setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.GRAY));
		setLocation(10, 10);
		setSize(446, 104);
		setBackground(inicial);
		setLayout(null);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) 
			{
				if(!estaSeleccionado()) 
					setBackground(ratonEncima);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) 
			{
				if(!estaSeleccionado()) 
					setBackground(inicial);
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				if(estaSeleccionado()) 
					setBackground(ratonEncima);
				else 
					setBackground(seleccionado);
			}
		});
		
		JLabel lblNombre = new JLabel(p.getNombre());
		lblNombre.setIconTextGap(8);
		lblNombre.setIcon(new ImageIcon(Contacto.class.getResource("/icons/contactox16.png")));
		lblNombre.setForeground(new Color(255, 153, 51));
		lblNombre.setFont(new Font("SansSerif", Font.BOLD, 17));
		lblNombre.setBounds(10, 12, 181, 20);
		add(lblNombre);
		
		JLabel lblTelefono = new JLabel(p.getTelefono());
		lblTelefono.setIconTextGap(8);
		lblTelefono.setIcon(new ImageIcon(Contacto.class.getResource("/icons/telefono.png")));
		lblTelefono.setHorizontalAlignment(SwingConstants.LEFT);
		lblTelefono.setForeground(new Color(255, 153, 51));
		lblTelefono.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTelefono.setBounds(10, 43, 234, 20);
		add(lblTelefono);
		
		String correo = p.getCorreo();
		if(!correo.equals("")) 
		{
			JLabel lblCorreo = new JLabel(correo);
			lblCorreo.setIconTextGap(8);
			lblCorreo.setIcon(new ImageIcon(Contacto.class.getResource("/icons/correo.png")));
			lblCorreo.setForeground(new Color(255, 153, 51));
			lblCorreo.setFont(new Font("SansSerif", Font.PLAIN, 15));
			lblCorreo.setBounds(10, 74, 336, 17);
			add(lblCorreo);
		}
		
		TipoDTO tipo = p.getTipo_contacto();
		if(tipo != null) 
		{
			String tipoNombre = tipo.getNombre().toUpperCase();
			JLabel lblTipo = new JLabel(tipoNombre);
			switch (tipoNombre) 
			{
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
			lblTipo.setFont(new Font("SansSerif", Font.BOLD, 14));
			lblTipo.setBounds(286, 12, 150, 32);
			add(lblTipo);
		}
		btnVerMas = new JButton("+");
		btnVerMas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnVerMas.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(  198, 112, 27 )));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnVerMas.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color( 198, 112, 27  )));
			}
		});
		btnVerMas.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color( 198, 112, 27  )));
		btnVerMas.setFocusPainted(false);
		btnVerMas.setForeground(new Color(74, 75, 71));
		btnVerMas.setBackground(new Color(255, 153, 51));
		btnVerMas.setToolTipText("Ver mas");
		btnVerMas.setBounds(416, 74, 20, 20);
		add(btnVerMas);
	}
	
	public boolean estaSeleccionado() 
	{
		return (getBackground() == seleccionado) ?  true :  false;
	}
	
	public JButton getBtnVerMas() 
	{
		return btnVerMas;
	}
}
