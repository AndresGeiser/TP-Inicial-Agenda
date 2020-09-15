package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.ProvinciaDTO;

import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaPersona extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private static VentanaPersona INSTANCE;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtCorreo;
	private JComboBox<String> cbxTipo;
	private JTextField txtFecha;
	
	private JComboBox<String> cbxPais;
	private JComboBox<String> cbxProvincia;
	private JComboBox<String> cbxLocalidad;
	
	private JButton btnAgregarPersona;
	private JButton btnActualizarPersona;
	
	
	public static VentanaPersona getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new VentanaPersona(); 	
			return new VentanaPersona();
		}
		else
			return INSTANCE;
	}

	private VentanaPersona() 
	{
		super();
		setTitle("Agregar Contacto");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 314, 403);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(295, 600));
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		JLabel lblNombreYApellido = new JLabel("Nombre y apellido *");
		lblNombreYApellido.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNombreYApellido.setBounds(10, 11, 275, 30);
		panel.add(lblNombreYApellido);
		
		JLabel lblTelfono = new JLabel("Telefono *");
		lblTelfono.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblTelfono.setBounds(10, 93, 275, 30);
		panel.add(lblTelfono);
		
		txtNombre = new JTextField();
		txtNombre.setMargin(new Insets(2, 5, 2, 5));
		txtNombre.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtNombre.setBounds(10, 52, 275, 30);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtTelefono = new JTextField();
		txtTelefono.setMargin(new Insets(2, 5, 2, 5));
		txtTelefono.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtTelefono.setBounds(10, 134, 275, 30);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblCorreo = new JLabel("Correo electronico");
		lblCorreo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblCorreo.setBounds(10, 175, 275, 30);
		panel.add(lblCorreo);
		
		txtCorreo = new JTextField();
		txtCorreo.setMargin(new Insets(2, 5, 2, 5));
		txtCorreo.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(10, 216, 275, 30);
		panel.add(txtCorreo);
		
		JLabel lblTipo = new JLabel("Tipo de contacto");
		lblTipo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblTipo.setBounds(10, 257, 275, 30);
		panel.add(lblTipo);
		
		cbxTipo = new JComboBox<String>();
		cbxTipo.setFont(new Font("SansSerif", Font.PLAIN, 12));
		cbxTipo.setName("");
		cbxTipo.setBounds(10, 298, 149, 30);
		cbxTipo.addItem("Trabajo");
		cbxTipo.addItem("Familia");
		cbxTipo.addItem("Amigos");
		cbxTipo.addItem("Universidad");
		panel.add(cbxTipo);
		
		JLabel lblFecha = new JLabel("Fecha de Cumplea\u00F1os");
		lblFecha.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblFecha.setBounds(10, 339, 275, 30);
		panel.add(lblFecha);
		
		txtFecha = new JTextField();
		txtFecha.setMargin(new Insets(2, 5, 2, 5));
		txtFecha.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtFecha.setColumns(10);
		txtFecha.setBounds(10, 380, 275, 30);
		panel.add(txtFecha);
		
		JLabel lblDomicilio = new JLabel("Domicilio");
		lblDomicilio.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblDomicilio.setBounds(10, 421, 292, 30);
		panel.add(lblDomicilio);
		
		
		JLabel lblPais = new JLabel("Pais:");
		lblPais.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPais.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblPais.setBounds(10, 462, 80, 30);
		panel.add(lblPais);
		
		cbxPais = new JComboBox<String>();
		cbxPais.setBounds(100, 462, 149, 30);
		panel.add(cbxPais);
		
		
		JLabel lblProvincia = new JLabel("Provincia:");
		lblProvincia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProvincia.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblProvincia.setBounds(10, 503, 80, 30);
		panel.add(lblProvincia);
		
		cbxProvincia = new JComboBox<String>();
		cbxProvincia.setBounds(100, 503, 149, 30);
		panel.add(cbxProvincia);
		
		
		JLabel lblLocalidad = new JLabel("Localidad: ");
		lblLocalidad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLocalidad.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblLocalidad.setBounds(10, 544, 80, 30);
		panel.add(lblLocalidad);
		
		cbxLocalidad = new JComboBox<String>();
		cbxLocalidad.setBounds(100, 544, 149, 30);
		panel.add(cbxLocalidad);
		
		btnAgregarPersona = new JButton("Agregar");
		btnAgregarPersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAgregarPersona.setBounds(214, 425, 110, 30);
		contentPane.add(btnAgregarPersona);
		
		btnActualizarPersona = new JButton("Actualizar");
		btnActualizarPersona.setBounds(214, 425, 110, 30);
		contentPane.add(btnActualizarPersona);
		
		this.setVisible(false);
	}
	
	public void mostrarVentana()
	{
		setTitle("Agregar Contacto");
		btnAgregarPersona.setVisible(true);
		btnActualizarPersona.setVisible(false);
		
		//Se limpia la vista de los campos
		txtNombre.setText("");
		txtTelefono.setText("");
		txtCorreo.setText("");
		txtFecha.setText("");
		cbxTipo.setSelectedIndex(0);
		this.setVisible(true);
	}
	
	public void mostrarVentanaConDatos(String nombre, String telefono, String correo, String tipo, String cumple)
	{
		setTitle("Actualizar Contacto");
		btnAgregarPersona.setVisible(false);
		btnActualizarPersona.setVisible(true);
		
		//Recorremos los items del combo para ver cual coincide con el del parametro
		for (int i = 0; i < cbxTipo.getItemCount(); i++) {
			if(cbxTipo.getItemAt(i).equalsIgnoreCase(tipo)) {
				cbxTipo.setSelectedIndex(i);
				break;
			}
		}

		txtNombre.setText(nombre);
		txtTelefono.setText(telefono);
		txtCorreo.setText(correo);
		txtFecha.setText(cumple);
		
		this.setVisible(true);
	}
	
	public void cargarPaises(List<PaisDTO> paises) {
		for (PaisDTO paisDTO : paises) {
			cbxPais.addItem(paisDTO.getNombre());
		}
	}
	
	public void cargarProvincias(List<ProvinciaDTO> provincias) {
		for (ProvinciaDTO paisDTO : provincias) {
			cbxProvincia.addItem(paisDTO.getNombre());
		}
	}
	
	public void cargarLocalidades(List<LocalidadDTO> localidades) {
		for (LocalidadDTO localidadDTO : localidades) {
			cbxLocalidad.addItem(localidadDTO.getNombre());
		}
	}
	
	
	public JTextField getTxtNombre() 
	{
		return txtNombre;
	}

	public JTextField getTxtTelefono() 
	{
		return txtTelefono;
	}
	
	public JTextField getTxtCorreo() 
	{
		return txtCorreo;
	}
	
	public JTextField getTxtCumple() 
	{
		return txtFecha;
	}
	
	public JComboBox<String> getTipo() 
	{
		return cbxTipo;
	}
	
	public JComboBox<String> getCbxPais() 
	{
		return cbxPais;
	}
	
	public JComboBox<String> getCbxProvincia() 
	{
		return cbxProvincia;
	}
	
	public JComboBox<String> getCbxLocalidad() 
	{
		return cbxLocalidad;
	}

	public JButton getBtnAgregarPersona() 
	{
		return btnAgregarPersona;
	}
	
	public JButton getBtnActualizarPersona() 
	{
		return btnActualizarPersona;
	}

	public void cerrar()
	{
		this.dispose();
	}
}

