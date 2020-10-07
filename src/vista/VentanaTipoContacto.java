package vista;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;

import dto.TipoDTO;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Insets;


public class VentanaTipoContacto extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private static VentanaTipoContacto INSTANCE;
	
	private JTextField txtNombreAgregar;
	
	private JComboBox<String> cbxTiposEditar;
	private JTextField txtNombreEditar;
	
	private List<JCheckBox> chksBorrar;
	private JPanel panelChksBorrar;
	
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnBorrar;
	
	
	public static VentanaTipoContacto getInstance(VentanaPersona ventana, boolean bool)
	{
		if(INSTANCE == null)
			INSTANCE = new VentanaTipoContacto(ventana, bool);	
		
		return INSTANCE;
	}
	
	
	public VentanaTipoContacto(VentanaPersona ventana, boolean bool) 
	{
		super(ventana, bool);
		setTitle("Configuracion tipos de contacto");
		setBounds(new Rectangle(200, 50, 500, 600));
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel contPanels = new JPanel();
		contPanels.setBorder(new LineBorder(Color.WHITE, 15, true));
		contPanels.setLayout(new BoxLayout(contPanels, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(contPanels);
		
		//Panel agregar
		JPanel panelAgregar = new JPanel();
		panelAgregar.setMinimumSize(new Dimension(300, 300));
		panelAgregar.setBackground(Color.WHITE);
		panelAgregar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Agregar Tipo", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
		panelAgregar.setLayout(new BorderLayout(0, 0));
		contPanels.add(panelAgregar);
		
		JPanel panelAgregarInferior = new JPanel();
		panelAgregarInferior.setBackground(Color.WHITE);
		panelAgregarInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelAgregar.add(panelAgregarInferior, BorderLayout.SOUTH);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(291, 68, 121, 30);
		panelAgregarInferior.add(btnAgregar);
		
		JPanel panelAgregarCentro = new JPanel();
		panelAgregarCentro.setBackground(Color.WHITE);
		panelAgregarCentro.setPreferredSize(new Dimension(10, 50));
		panelAgregarCentro.setLayout(null);
		panelAgregarCentro.setAutoscrolls(true);
		panelAgregar.add(panelAgregarCentro, BorderLayout.CENTER);
		
		JLabel lblNombre = new JLabel("Ingrese el nombre:");
		lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNombre.setBounds(10, 11, 200, 20);
		panelAgregarCentro.add(lblNombre);
		
		txtNombreAgregar = new JTextField();
		txtNombreAgregar.setMargin(new Insets(2, 5, 2, 5));
		txtNombreAgregar.setColumns(10);
		txtNombreAgregar.setBounds(209, 11, 203, 20);
		panelAgregarCentro.add(txtNombreAgregar);
		
		
		//Panel editar
		JPanel panelEditar = new JPanel();
		panelEditar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Editar Tipo", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
		panelEditar.setBackground(new Color(255, 255, 255));
		panelEditar.setLayout(new BorderLayout(0, 0));
		contPanels.add(panelEditar);
		
		JPanel panelEditarInferior = new JPanel();
		panelEditarInferior.setBackground(Color.WHITE);
		panelEditarInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelEditar.add(panelEditarInferior, BorderLayout.SOUTH);
		
		btnEditar = new JButton("Editar");
		panelEditarInferior.add(btnEditar);
		
		JPanel panelEditarCentro = new JPanel();
		panelEditarCentro.setBackground(Color.WHITE);
		panelEditarCentro.setPreferredSize(new Dimension(10, 80));
		panelEditarCentro.setLayout(null);
		panelEditarCentro.setAutoscrolls(true);
		panelEditar.add(panelEditarCentro, BorderLayout.CENTER);
		
		JLabel lblSeleccione = new JLabel("Seleccione el tipo que desea editar:");
		lblSeleccione.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSeleccione.setBounds(10, 11, 200, 20);
		panelEditarCentro.add(lblSeleccione);
		
		cbxTiposEditar = new JComboBox<String>();
		cbxTiposEditar.setBounds(220, 11, 192, 20);
		panelEditarCentro.add(cbxTiposEditar);
		
		JLabel lblIngrese = new JLabel("Ingrese el nuevo nombre:");
		lblIngrese.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblIngrese.setBounds(10, 42, 200, 20);
		panelEditarCentro.add(lblIngrese);
		
		txtNombreEditar = new JTextField();
		txtNombreEditar.setMargin(new Insets(2, 5, 2, 5));
		txtNombreEditar.setColumns(10);
		txtNombreEditar.setBounds(220, 42, 192, 20);
		panelEditarCentro.add(txtNombreEditar);
		
		//Panel borrar
		JPanel panelBorrar = new JPanel();
		panelBorrar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Borrar Tipo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		panelBorrar.setBackground(Color.WHITE);
		panelBorrar.setLayout(new BorderLayout(0, 0));
		contPanels.add(panelBorrar);
		
		JPanel panelBorrarInferior = new JPanel();
		panelBorrarInferior.setBackground(Color.WHITE);
		panelBorrarInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelBorrar.add(panelBorrarInferior, BorderLayout.SOUTH);
		
		btnBorrar = new JButton("Borrar");
		panelBorrarInferior.add(btnBorrar);
		
		JPanel panelBorrarCentro = new JPanel();
		panelBorrarCentro.setLayout(null);
		panelBorrarCentro.setPreferredSize(new Dimension(10, 200));
		panelBorrarCentro.setBackground(Color.WHITE);
		panelBorrarCentro.setAutoscrolls(true);
		panelBorrar.add(panelBorrarCentro, BorderLayout.CENTER);
		
		JLabel lblSeleccioneLosPaises = new JLabel("Seleccione los que desea borrar:");
		lblSeleccioneLosPaises.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSeleccioneLosPaises.setBounds(10, 11, 251, 20);
		panelBorrarCentro.add(lblSeleccioneLosPaises);
		
		JScrollPane scrollPaneBorrar = new JScrollPane();
		scrollPaneBorrar.setBounds(61, 39, 280, 150);
		panelBorrarCentro.add(scrollPaneBorrar);

		
		panelChksBorrar = new JPanel();
		panelChksBorrar.setLayout(new BoxLayout(panelChksBorrar, BoxLayout.Y_AXIS));
		scrollPaneBorrar.setViewportView(panelChksBorrar);
		
		chksBorrar = new ArrayList<JCheckBox>();
	}
	
	public void mostrar() 
	{
		setBounds(new Rectangle(200, 50, 500, 600));
		setLocationRelativeTo(null);
		
		txtNombreAgregar.setText("");
		
		cbxTiposEditar.setSelectedIndex(0);
		txtNombreEditar.setText("");
	
		setVisible(true);
	}
	
	public void cargarTipos(List<TipoDTO> tipos) 
	{
		//Panel Editar
		cbxTiposEditar.removeAllItems();
		for (TipoDTO tipo : tipos) 
			cbxTiposEditar.addItem(tipo.getNombre());
		
		//Panel Borrar
		chksBorrar.clear();
		panelChksBorrar.removeAll();
		for (TipoDTO tipo : tipos) 
		{
			JCheckBox chk = new JCheckBox(tipo.getNombre());
			panelChksBorrar.add(chk);
			chksBorrar.add(chk);
		}
		
		panelChksBorrar.revalidate();
		panelChksBorrar.repaint();
	}
	
	public void limpiarCampos() 
	{
		txtNombreAgregar.setText("");
		
		cbxTiposEditar.setSelectedIndex(0);
		txtNombreEditar.setText("");
	}
	
	public JTextField getTxtNombreAgregar() { return txtNombreAgregar; }
	
	public JTextField getTxtNombreEditar() { return txtNombreEditar; }
	
	public JComboBox<String> getCbxTiposEditar() { return cbxTiposEditar; }
	
	public List<JCheckBox> getChksPanelBorrar() { return chksBorrar; }
	
	public JButton getBtnAgregar() { return btnAgregar; }
	
	public JButton getBtnEditar() { return btnEditar; }
	
	public JButton getBtnBorrar() { return btnBorrar; }
 	
	public void cerrar() 
	{
		this.dispose();
	}
}
