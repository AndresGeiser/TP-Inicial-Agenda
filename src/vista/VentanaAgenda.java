package vista;

import dto.PersonaDTO;
import persistencia.Conexion;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class VentanaAgenda
{
	private JFrame frmAgenda;
	private JPanel panelContactos;
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnBorrar;
	private JButton btnReporte;


	public VentanaAgenda() 
	{
		super();
		initialize();
	}


	private void initialize() 
	{
		frmAgenda = new JFrame();
		frmAgenda.setTitle("Agenda");
		frmAgenda.setResizable(false);
		frmAgenda.getContentPane().setBackground(Color.DARK_GRAY);
		frmAgenda.setBounds(100, 100, 605, 650);
		frmAgenda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAgenda.getContentPane().setLayout(null);
		frmAgenda.setLocationRelativeTo(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 11, 485, 590);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		frmAgenda.getContentPane().add(scrollPane);
		
		panelContactos = new JPanel();
		panelContactos.setBackground(new Color( 74, 72, 75 ));
		scrollPane.setViewportView(panelContactos);
		panelContactos.setPreferredSize(new Dimension(0, 0));
		panelContactos.setLayout(null);
		
		
		btnAgregar = new JButton("");
		btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAgregar.setToolTipText("Agregar contacto");
		btnAgregar.setFocusable(false);
		btnAgregar.setBackground(new Color( 74, 72, 75 ));
		btnAgregar.setBorderPainted(false);
		btnAgregar.setIcon(new ImageIcon(VentanaAgenda.class.getResource("/icons/agregar.png")));
		btnAgregar.setBounds(505, 11, 70, 70);
		btnAgregar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnAgregar.setBackground(new Color(107, 105, 108));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnAgregar.setBackground(new Color( 74, 72, 75 ));
			}
		});
		frmAgenda.getContentPane().add(btnAgregar);
		
		btnBorrar = new JButton("");
		btnBorrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBorrar.setIcon(new ImageIcon(VentanaAgenda.class.getResource("/icons/borrar.png")));
		btnBorrar.setToolTipText("Eliminar contacto");
		btnBorrar.setFocusable(false);
		btnBorrar.setBorderPainted(false);
		btnBorrar.setBackground(new Color( 223, 84, 84 ));
		btnBorrar.setBounds(505, 450, 70, 70);
		btnBorrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnBorrar.setBackground(new Color(236, 130, 130));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnBorrar.setBackground(new Color( 223, 84, 84 ));
			}
		});
		frmAgenda.getContentPane().add(btnBorrar);
		
		btnReporte = new JButton("");
		btnReporte.setIcon(new ImageIcon(VentanaAgenda.class.getResource("/icons/reporte.png")));
		btnReporte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReporte.setToolTipText("Generar reporte");
		btnReporte.setFocusable(false);
		btnReporte.setBorderPainted(false);
		btnReporte.setBackground(new Color(255, 153, 51));
		btnReporte.setBounds(505, 531, 70, 70);
		btnReporte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnReporte.setBackground(new Color(252, 183, 113));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnReporte.setBackground(new Color(255, 153, 51));
			}
		});
		frmAgenda.getContentPane().add(btnReporte);
		
		btnEditar = new JButton("");
		btnEditar.setIcon(new ImageIcon(VentanaAgenda.class.getResource("/icons/editar.png")));
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setToolTipText("Editar contacto");
		btnEditar.setFocusable(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBackground(new Color(74, 72, 75));
		btnEditar.setBounds(505, 92, 70, 70);
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnEditar.setBackground(new Color(107, 105, 108));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnEditar.setBackground(new Color( 74, 72, 75 ));
			}
		});
		frmAgenda.getContentPane().add(btnEditar);
									
	}
	
	public void show()
	{
		this.frmAgenda.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frmAgenda.addWindowListener(new WindowAdapter() 
		{
			@Override
		    public void windowClosing(WindowEvent e) {
		        int confirm = JOptionPane.showOptionDialog(
		             null, "Estas seguro que quieres salir de la Agenda?", 
		             "Confirmacion", JOptionPane.YES_NO_OPTION,
		             JOptionPane.QUESTION_MESSAGE, null, null, null);
		        if (confirm == 0) {
		        	Conexion.getConexion().cerrarConexion();
		           System.exit(0);
		        }
		    }
		});
		this.frmAgenda.setVisible(true);
	}
	
	public JButton getBtnAgregar() 
	{
		return btnAgregar;
	}
	
	public JButton getBtnEditar() 
	{
		return btnEditar;
	}

	public JButton getBtnBorrar() 
	{
		return btnBorrar;
	}
	
	public JButton getBtnReporte() 
	{
		return btnReporte;
	}
	
	public Contacto[] getContactos() 
	{
		int cantContactos = panelContactos.getComponents().length;
		Contacto[] contactos = new Contacto [cantContactos];
		for (int i = 0; i < cantContactos; i++) {
			contactos[i] = (Contacto) panelContactos.getComponents()[i];
		}
		return contactos;
	}


	public void llenarTabla(List<PersonaDTO> personasEnTabla) {
		panelContactos.removeAll();
		
		int ejeY = 11;
		for (PersonaDTO p : personasEnTabla)
		{
			Contacto panelContacto = new Contacto(p);
			panelContacto.setLocation(10, ejeY);
			
			panelContactos.add(panelContacto);
			
			ejeY += panelContacto.getHeight();
			
			panelContactos.setPreferredSize(new Dimension(panelContactos.getWidth(), ejeY));
		}
		
		panelContactos.revalidate();
		panelContactos.repaint();
	}


	
}
