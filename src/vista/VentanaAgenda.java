package vista;

import dto.PersonaDTO;
import persistencia.Conexion;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.border.MatteBorder;


public class VentanaAgenda extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static VentanaAgenda INSTANCE;
	
	private JPanel panelContactos;
	private List<Contacto> contactos;
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnBorrar;
	private JButton btnReporte;

	
	public static VentanaAgenda getInstance()
	{
		if(INSTANCE == null)
			INSTANCE = new VentanaAgenda(); 	
		
		return INSTANCE;
	}
	
	private VentanaAgenda() 
	{
		super();
		inicializar();
	}

	private void inicializar() 
	{
		setTitle("Agenda");
		getContentPane().setBackground(Color.DARK_GRAY);
		setBounds(100, 100, 605, 650);
		setMinimumSize(new Dimension(0, 350));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		
		JPanel panelDerecho = new JPanel();
		panelDerecho.setBorder(new MatteBorder(5, 0, 5, 0, (Color) new Color(64, 64, 64)));
		panelDerecho.setBackground(Color.DARK_GRAY);
		panelDerecho.setLayout(new BorderLayout(0, 0));
		getContentPane().add(panelDerecho, BorderLayout.EAST);
		
		JPanel panelDerechoSuperior = new JPanel();
		panelDerechoSuperior.setBackground(Color.DARK_GRAY);
		panelDerechoSuperior.setPreferredSize(new Dimension(80, 140));
		panelDerechoSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelDerecho.add(panelDerechoSuperior, BorderLayout.NORTH);
		
		btnAgregar = new JButton("");
		btnAgregar.setPreferredSize(new Dimension(60, 60));
		btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAgregar.setToolTipText("Agregar contacto");
		btnAgregar.setFocusable(false);
		btnAgregar.setBackground(new Color(74, 72, 75));
		btnAgregar.setBorderPainted(false);
		btnAgregar.setIcon(new ImageIcon(VentanaAgenda.class.getResource("/icons/agregar.png")));
		btnAgregar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnAgregar.setBackground(new Color(82, 79, 83));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnAgregar.setBackground(new Color(74, 72, 75));
			}
		});
		panelDerechoSuperior.add(btnAgregar);
		
		btnEditar = new JButton("");
		btnEditar.setPreferredSize(new Dimension(60, 60));
		btnEditar.setIcon(new ImageIcon(VentanaAgenda.class.getResource("/icons/editar.png")));
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setToolTipText("Editar contacto");
		btnEditar.setFocusable(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBackground(new Color(74, 72, 75));
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnEditar.setBackground(new Color(82, 79, 83));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnEditar.setBackground(new Color(74, 72, 75));
			}
		});
		panelDerechoSuperior.add(btnEditar);
		
		
		JPanel panelDerechoInferior = new JPanel();
		panelDerechoInferior.setBackground(Color.DARK_GRAY);
		panelDerechoInferior.setPreferredSize(new Dimension(80, 140));
		panelDerechoInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelDerecho.add(panelDerechoInferior, BorderLayout.SOUTH);
		
		btnBorrar = new JButton("");
		btnBorrar.setPreferredSize(new Dimension(60, 60));
		btnBorrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBorrar.setIcon(new ImageIcon(VentanaAgenda.class.getResource("/icons/borrar.png")));
		btnBorrar.setToolTipText("Eliminar contacto");
		btnBorrar.setFocusable(false);
		btnBorrar.setBorderPainted(false);
		btnBorrar.setBackground(new Color(223, 84, 84));
		btnBorrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnBorrar.setBackground(new Color(227, 101, 101));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnBorrar.setBackground(new Color(223, 84, 84));
			}
		});
		panelDerechoInferior.add(btnBorrar);
		
		btnReporte = new JButton("");
		btnReporte.setPreferredSize(new Dimension(60, 60));
		btnReporte.setIcon(new ImageIcon(VentanaAgenda.class.getResource("/icons/reporte.png")));
		btnReporte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReporte.setToolTipText("Generar reporte");
		btnReporte.setFocusable(false);
		btnReporte.setBorderPainted(false);
		btnReporte.setBackground(new Color(255, 153, 51));
		btnReporte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnReporte.setBackground(new Color(251, 163, 76));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnReporte.setBackground(new Color(255, 153, 51));
			}
		});
		panelDerechoInferior.add(btnReporte);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		panelContactos = new JPanel();
		panelContactos.setBorder(new MatteBorder(10, 20, 10, 15, (Color) new Color(64, 64, 64)));
		panelContactos.setBackground(new Color( 74, 72, 75 ));
		panelContactos.setPreferredSize(new Dimension(0, 0));
		scrollPane.setViewportView(panelContactos);
		panelContactos.setLayout(new BoxLayout(panelContactos, BoxLayout.Y_AXIS));
									
		contactos = new ArrayList<Contacto>();
	}
	
	public void mostrar()
	{
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() 
		{
			@Override
		    public void windowClosing(WindowEvent e) 
			{
		        int confirm = JOptionPane.showOptionDialog(
		            null, "Estas seguro que quieres salir de la Agenda?", 
		            "Confirmacion", JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE, null, null, null);
		        if (confirm == 0) 
		        {
		        	Conexion.getConexion().cerrarConexion();
		        	System.exit(0);
		        }
		    }
		});
		setVisible(true);
	}


	public void cargarContactos(List<PersonaDTO> personasEnTabla) 
	{
		contactos.clear();
		panelContactos.removeAll();
		
		int altoTotal = 0;
		for (PersonaDTO p : personasEnTabla)
		{
			Contacto panelContacto = new Contacto(p);
			
			contactos.add(panelContacto);
			panelContactos.add(panelContacto);
			
			altoTotal += panelContacto.getPreferredSize().getHeight();
			
			panelContactos.setPreferredSize(new Dimension(0, altoTotal));
		}
		panelContactos.revalidate();
		panelContactos.repaint();
	}
	
	public List<Contacto> getContactos() { return contactos; }
	
	public JButton getBtnAgregar() { return btnAgregar; }
	
	public JButton getBtnEditar() { return btnEditar; }

	public JButton getBtnBorrar() { return btnBorrar; }
	
	public JButton getBtnReporte() { return btnReporte; }
	
}
