package vista;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import dto.TipoDTO;

import java.awt.Font;
import java.util.List;

import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;


public class VentanaTipoContacto extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private static VentanaTipoContacto INSTANCE;
	
	private JPanel panelAgregar;
	private JTextField txtNombreAgregar;
	
	private JPanel panelEditar;
	private JComboBox<String> cbxTiposEditar;
	private JTextField txtNombreEditar;
	
	private JPanel panelBorrar;
	private JPanel panelInteriorBorrar;
	
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnBorrar;
	private JButton btnCancelar;
	
	
	public static VentanaTipoContacto getInstance(VentanaPersona ventana, boolean bool)
	{
		if(INSTANCE == null)
			INSTANCE = new VentanaTipoContacto(ventana, bool);	
		
		return INSTANCE;
	}
	
	
	public VentanaTipoContacto(VentanaPersona ventana, boolean bool) 
	{
		super(ventana, bool);
		setBounds(100, 100, 400, 286);
		getContentPane().setLayout(null);
		
		JPanel panelInferior = new JPanel();
		panelInferior.setBounds(0, 208, 384, 40);
		panelInferior.setPreferredSize(new Dimension(10, 40));
		panelInferior.setLayout(null);
		getContentPane().add(panelInferior);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(213, 5, 121, 30);
		panelInferior.add(btnAgregar);
		
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(213, 5, 121, 30);
		panelInferior.add(btnEditar);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(213, 5, 121, 30);
		panelInferior.add(btnBorrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(40, 5, 121, 30);
		panelInferior.add(btnCancelar);
		
		
		//Panel agregar
		panelAgregar = new JPanel();
		panelAgregar.setBounds(0, 0, 384, 208);
		panelAgregar.setLayout(null);
		getContentPane().add(panelAgregar);
		
		JLabel lblNombre = new JLabel("Ingrese el nombre");
		lblNombre.setBounds(10, 54, 364, 30);
		lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		panelAgregar.add(lblNombre);
		
		txtNombreAgregar = new JTextField();
		txtNombreAgregar.setBounds(100, 95, 200, 30);
		txtNombreAgregar.setMaximumSize(new Dimension(100, 30));
		panelAgregar.add(txtNombreAgregar);
		txtNombreAgregar.setColumns(10);
		
		
		//Panel editar
		panelEditar = new JPanel();
		panelEditar.setBounds(0, 0, 384, 208);
		panelEditar.setLayout(null);
		panelEditar.setVisible(false);
		getContentPane().add(panelEditar);
		
		JLabel lblSeleccione = new JLabel("Seleccione el tipo que desea editar:");
		lblSeleccione.setBounds(10, 23, 364, 30);
		lblSeleccione.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSeleccione.setHorizontalAlignment(SwingConstants.CENTER);
		panelEditar.add(lblSeleccione);
		
		cbxTiposEditar = new JComboBox<String>();
		cbxTiposEditar.setBounds(100, 64, 200, 30);
		panelEditar.add(cbxTiposEditar);
		
		JLabel lblIngrese = new JLabel("Ingrese el nuevo nombre:");
		lblIngrese.setHorizontalAlignment(SwingConstants.CENTER);
		lblIngrese.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblIngrese.setBounds(10, 105, 364, 30);
		panelEditar.add(lblIngrese);
		
		txtNombreEditar = new JTextField();
		txtNombreEditar.setBounds(100, 146, 200, 30);
		txtNombreEditar.setMaximumSize(new Dimension(100, 30));
		panelEditar.add(txtNombreEditar);
		txtNombreEditar.setColumns(10);
		
		//Panel borrar
		panelBorrar = new JPanel();
		panelBorrar.setBounds(0, 0, 384, 208);
		panelBorrar.setLayout(null);
		panelBorrar.setVisible(false);
		getContentPane().add(panelBorrar);
		
		JLabel lblSeleccioneBorrar = new JLabel("Seleccione los que desea borrar:");
		lblSeleccioneBorrar.setBounds(10, 23, 364, 30);
		lblSeleccioneBorrar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSeleccioneBorrar.setHorizontalAlignment(SwingConstants.CENTER);
		panelBorrar.add(lblSeleccioneBorrar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(70, 64, 250, 133);
		panelBorrar.add(scrollPane);
		
		panelInteriorBorrar = new JPanel();
		panelInteriorBorrar.setLayout(new BoxLayout(panelInteriorBorrar, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(panelInteriorBorrar);
	
	}
	
	public void mostrarAgregar() 
	{
		setTitle("Agregar tipo de contacto");
		
		btnAgregar.setVisible(true);
		btnEditar.setVisible(false);
		btnBorrar.setVisible(false);
		
		panelAgregar.setVisible(true);
		panelEditar.setVisible(false);
		panelBorrar.setVisible(false);
		
		txtNombreAgregar.setText("");
		
		setVisible(true);
	}
	
	public void mostrarEditar(List<TipoDTO> tipos) 
	{
		setTitle("Editar tipo de contacto");
		
		btnEditar.setVisible(true);
		btnAgregar.setVisible(false);
		btnBorrar.setVisible(false);
		
		panelEditar.setVisible(true);
		panelAgregar.setVisible(false);
		panelBorrar.setVisible(false);
		
		cbxTiposEditar.removeAllItems();
		for (TipoDTO tipo : tipos) 
			cbxTiposEditar.addItem(tipo.getNombre());
		
		txtNombreEditar.setText("");
		
		setVisible(true);
	}
	
	public void mostrarBorrar(List<TipoDTO> tipos) 
	{
		setTitle("Borrar tipos de contacto");
		
		btnBorrar.setVisible(true);
		btnAgregar.setVisible(false);
		btnEditar.setVisible(false);
		
		panelBorrar.setVisible(true);
		panelAgregar.setVisible(false);
		panelEditar.setVisible(false);
		
		panelInteriorBorrar.removeAll();
		for (TipoDTO tipo : tipos) 
		{
			JCheckBox cb = new JCheckBox(tipo.getNombre());
			panelInteriorBorrar.add(cb);
		}
		
		setVisible(true);
	}
	
	public JTextField getTxtNombreAgregar() 
	{
		return txtNombreAgregar;
	}
	
	public JTextField getTxtNombreEditar() 
	{
		return txtNombreEditar;
	}
	
	public JComboBox<String> getCbxTiposEditar() 
	{
		return cbxTiposEditar;
	}
	
	public JCheckBox[] getChksPanelBorrar()
	{
		int cantTipos = panelInteriorBorrar.getComponents().length;
		JCheckBox[] chkArray = new JCheckBox[cantTipos];
		for (int i = 0; i < cantTipos; i++) 
			chkArray[i] = (JCheckBox) panelInteriorBorrar.getComponents()[i];
		
		return chkArray;
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
	
	public JButton getBtnCancelar() 
	{
		return btnCancelar;
	}
 	
	public void cerrar() 
	{
		this.dispose();
	}
}
