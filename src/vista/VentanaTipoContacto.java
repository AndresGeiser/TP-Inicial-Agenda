package vista;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import dto.TipoDTO;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;


public class VentanaTipoContacto extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private static VentanaTipoContacto INSTANCE;
	
	private JTextField txtNombreAgregar;
	
	private JComboBox<String> cbxTiposEditar;
	private JTextField txtNombreEditar;
	
	private List<JCheckBox> chksBorrar;
	private JPanel panelInteriorBorrar;
	
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
		setBounds(100, 100, 460, 553);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel contPanels = new JPanel();
		contPanels.setPreferredSize(new Dimension(420, 510));
		scrollPane.setViewportView(contPanels);
		contPanels.setLayout(null);
				
		//Panel agregar
		JPanel panelAgregar = new JPanel();
		panelAgregar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Agregar nuevo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		panelAgregar.setBounds(10, 11, 422, 109);
		panelAgregar.setLayout(null);
		contPanels.add(panelAgregar);
		
		JLabel lblNombre = new JLabel("Ingrese el nombre:");
		lblNombre.setBounds(10, 26, 122, 30);
		panelAgregar.add(lblNombre);
		lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 12));

		txtNombreAgregar = new JTextField();
		txtNombreAgregar.setBounds(131, 27, 281, 30);
		txtNombreAgregar.setMaximumSize(new Dimension(100, 30));
		panelAgregar.add(txtNombreAgregar);
		txtNombreAgregar.setColumns(10);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(291, 68, 121, 30);
		panelAgregar.add(btnAgregar);
		
		
		//Panel editar
		JPanel panelEditar = new JPanel();
		panelEditar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Editar ", TitledBorder.LEADING, TitledBorder.TOP, null, Color.DARK_GRAY));
		panelEditar.setBounds(10, 131, 422, 145);
		panelEditar.setLayout(null);
		contPanels.add(panelEditar);
		
		JLabel lblSeleccione = new JLabel("Seleccione el tipo que desea editar:");
		lblSeleccione.setHorizontalTextPosition(SwingConstants.LEADING);
		lblSeleccione.setBounds(10, 23, 206, 30);
		lblSeleccione.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panelEditar.add(lblSeleccione);
		
		cbxTiposEditar = new JComboBox<String>();
		cbxTiposEditar.setBounds(226, 24, 186, 30);
		panelEditar.add(cbxTiposEditar);
		
		JLabel lblIngrese = new JLabel("Ingrese el nuevo nombre:");
		lblIngrese.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblIngrese.setBounds(10, 64, 154, 30);
		panelEditar.add(lblIngrese);
		
		txtNombreEditar = new JTextField();
		txtNombreEditar.setBounds(174, 65, 238, 30);
		txtNombreEditar.setMaximumSize(new Dimension(100, 30));
		panelEditar.add(txtNombreEditar);
		txtNombreEditar.setColumns(10);
		
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(291, 106, 121, 30);
		panelEditar.add(btnEditar);
		
		//Panel borrar
		JPanel panelBorrar = new JPanel();
		panelBorrar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Borrar", TitledBorder.LEADING, TitledBorder.TOP, null, Color.DARK_GRAY));
		panelBorrar.setBounds(10, 287, 422, 218);
		panelBorrar.setLayout(null);
		contPanels.add(panelBorrar);
		
		JLabel lblSeleccioneBorrar = new JLabel("Seleccione los que desea borrar:");
		lblSeleccioneBorrar.setBounds(10, 23, 193, 30);
		lblSeleccioneBorrar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panelBorrar.add(lblSeleccioneBorrar);
		
		JScrollPane scrollPaneBorrar = new JScrollPane();
		scrollPaneBorrar.setBounds(10, 64, 216, 133);
		panelBorrar.add(scrollPaneBorrar);
		
		panelInteriorBorrar = new JPanel();
		scrollPaneBorrar.setViewportView(panelInteriorBorrar);
		panelInteriorBorrar.setLayout(new BoxLayout(panelInteriorBorrar, BoxLayout.Y_AXIS));
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(291, 167, 121, 30);
		panelBorrar.add(btnBorrar);
		
		chksBorrar = new ArrayList<JCheckBox>();
	
	}
	
	public void mostrar() 
	{
		setBounds(100, 100, 460, 553);
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
		panelInteriorBorrar.removeAll();
		for (TipoDTO tipo : tipos) 
		{
			JCheckBox chk = new JCheckBox(tipo.getNombre());
			panelInteriorBorrar.add(chk);
			chksBorrar.add(chk);
		}
	}
	
	public void limpiarCampos() 
	{
		txtNombreAgregar.setText("");
		
		cbxTiposEditar.setSelectedIndex(0);
		txtNombreEditar.setText("");
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
	
	public List<JCheckBox> getChksPanelBorrar()
	{
		return chksBorrar;
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
 	
	public void cerrar() 
	{
		this.dispose();
	}
}
