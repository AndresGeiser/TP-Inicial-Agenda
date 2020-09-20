package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.DomicilioDTO;
import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;

import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	
	private JCheckBox chckDomicilio;
	private JComboBox<String> cbxPais;
	private JComboBox<String> cbxProvincia;
	private JComboBox<String> cbxLocalidad;
	private JTextField txtCalle;
	private JTextField txtAltura;
	private JTextField txtPiso;
	private JTextField txtDpto;
	
	private JButton btnAgregarPersona;
	private JButton btnActualizarPersona;
	
	
	public static VentanaPersona getInstance()
	{
		if(INSTANCE == null)
			INSTANCE = new VentanaPersona(); 	
		return INSTANCE;
	}

	private VentanaPersona() 
	{
		super();
		setResizable(false);
		setTitle("Agregar Contacto");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 500);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 364, 400);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(295, 750));
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		JLabel lblNombreYApellido = new JLabel("Nombre y apellido *");
		lblNombreYApellido.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNombreYApellido.setBounds(10, 11, 325, 30);
		panel.add(lblNombreYApellido);
		
		JLabel lblTelfono = new JLabel("Telefono *");
		lblTelfono.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblTelfono.setBounds(10, 93, 325, 30);
		panel.add(lblTelfono);
		
		txtNombre = new JTextField();
		txtNombre.setMargin(new Insets(2, 5, 2, 5));
		txtNombre.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtNombre.setColumns(10);
		txtNombre.setBounds(10, 52, 325, 30);
		panel.add(txtNombre);
		
		txtTelefono = new JTextField();
		txtTelefono.setMargin(new Insets(2, 5, 2, 5));
		txtTelefono.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(10, 134, 325, 30);
		txtTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				 char car = evt.getKeyChar();
				 if(!Character.isDigit(car)){
					 evt.consume();
					 getToolkit().beep();
				 }
				 if(txtTelefono.getText().length() > 11) {
					 evt.consume();
					 getToolkit().beep();
				 }
			}
		});
		panel.add(txtTelefono);
		
		JLabel lblCorreo = new JLabel("Correo electronico");
		lblCorreo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblCorreo.setBounds(10, 175, 325, 30);
		panel.add(lblCorreo);
		
		txtCorreo = new JTextField();
		txtCorreo.setMargin(new Insets(2, 5, 2, 5));
		txtCorreo.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(10, 216, 325, 30);
		panel.add(txtCorreo);
		
		JLabel lblTipo = new JLabel("Tipo de contacto");
		lblTipo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblTipo.setBounds(10, 257, 325, 30);
		panel.add(lblTipo);
		
		cbxTipo = new JComboBox<String>();
		cbxTipo.setFont(new Font("SansSerif", Font.PLAIN, 12));
		cbxTipo.setName("");
		cbxTipo.setBounds(10, 298, 162, 30);
		cbxTipo.addItem("Trabajo");
		cbxTipo.addItem("Familia");
		cbxTipo.addItem("Amigos");
		cbxTipo.addItem("Universidad");
		panel.add(cbxTipo);
		
		JLabel lblFecha = new JLabel("Fecha de Cumplea\u00F1os");
		lblFecha.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblFecha.setBounds(10, 339, 325, 30);
		panel.add(lblFecha);
		
		txtFecha = new JTextField();
		txtFecha.setMargin(new Insets(2, 5, 2, 5));
		txtFecha.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtFecha.setColumns(10);
		txtFecha.setBounds(10, 380, 325, 30);
		panel.add(txtFecha);
		
		
		JLabel lblPais = new JLabel("Pais:");
		lblPais.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPais.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblPais.setBounds(10, 462, 80, 30);
		panel.add(lblPais);
		
		cbxPais = new JComboBox<String>();
		cbxPais.setEnabled(false);
		cbxPais.setBounds(100, 462, 188, 30);
		panel.add(cbxPais);
		
		
		JLabel lblProvincia = new JLabel("Provincia:");
		lblProvincia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProvincia.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblProvincia.setBounds(10, 503, 80, 30);
		panel.add(lblProvincia);
		
		cbxProvincia = new JComboBox<String>();
		cbxProvincia.setEnabled(false);
		cbxProvincia.setBounds(100, 503, 188, 30);
		panel.add(cbxProvincia);
		
		
		JLabel lblLocalidad = new JLabel("Localidad: ");
		lblLocalidad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLocalidad.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblLocalidad.setBounds(10, 544, 80, 30);
		panel.add(lblLocalidad);
		
		cbxLocalidad = new JComboBox<String>();
		cbxLocalidad.setEnabled(false);
		cbxLocalidad.setBounds(100, 544, 188, 30);
		panel.add(cbxLocalidad);
		
		JLabel lblCalle = new JLabel("Calle:");
		lblCalle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCalle.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblCalle.setBounds(10, 585, 80, 30);
		panel.add(lblCalle);
		
		txtCalle = new JTextField();
		txtCalle.setEnabled(false);
		txtCalle.setMargin(new Insets(2, 5, 2, 5));
		txtCalle.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtCalle.setColumns(10);
		txtCalle.setBounds(100, 585, 188, 30);
		txtCalle.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				 char car = evt.getKeyChar();
				 if(Character.isDigit(car)){
					 evt.consume();
					 getToolkit().beep();
				 }
			}
		});
		panel.add(txtCalle);
		
		JLabel lblAltura = new JLabel("Altura:");
		lblAltura.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAltura.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblAltura.setBounds(10, 626, 80, 30);
		panel.add(lblAltura);
		
		txtAltura = new JTextField();
		txtAltura.setEnabled(false);
		txtAltura.setMargin(new Insets(2, 5, 2, 5));
		txtAltura.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtAltura.setColumns(10);
		txtAltura.setBounds(100, 626, 94, 30);
		txtAltura.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				 char car = evt.getKeyChar();
				 if(!Character.isDigit(car)){
					 evt.consume();
					 getToolkit().beep();
				 }
				 if(txtAltura.getText().length() > 6) {
					 evt.consume();
					 getToolkit().beep();
				 }
			}
		});
		panel.add(txtAltura);
		
		JLabel lblPiso = new JLabel("Piso:");
		lblPiso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPiso.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblPiso.setBounds(10, 667, 80, 30);
		panel.add(lblPiso);
		
		txtPiso = new JTextField();
		txtPiso.setEnabled(false);
		txtPiso.setMargin(new Insets(2, 5, 2, 5));
		txtPiso.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtPiso.setColumns(10);
		txtPiso.setBounds(100, 667, 94, 30);
		txtPiso.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				 char car = evt.getKeyChar();
				 if(!Character.isDigit(car)){
					 evt.consume();
					 getToolkit().beep();
				 }
				 if(txtPiso.getText().length() > 3) {
					 evt.consume();
					 getToolkit().beep();
				 }
			}
		});
		panel.add(txtPiso);
		
		JLabel lblDto = new JLabel("Dto:");
		lblDto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDto.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblDto.setBounds(10, 708, 80, 30);
		panel.add(lblDto);
		
		txtDpto = new JTextField();
		txtDpto.setEnabled(false);
		txtDpto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				 char car = evt.getKeyChar();
				 if(!Character.isDigit(car)){
					 evt.consume();
					 getToolkit().beep();
				 }
				 if(txtDpto.getText().length() > 3) {
					 evt.consume();
					 getToolkit().beep();
				 }
			}
		});
		txtDpto.setMargin(new Insets(2, 5, 2, 5));
		txtDpto.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtDpto.setColumns(10);
		txtDpto.setBounds(100, 708, 94, 30);
		panel.add(txtDpto);
		
		chckDomicilio = new JCheckBox("Agregar Domicilio");
		chckDomicilio.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chckDomicilio.setBounds(10, 417, 325, 30);
		panel.add(chckDomicilio);
		
		btnAgregarPersona = new JButton("Agregar");
		btnAgregarPersona.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAgregarPersona.setBorder(null);
		btnAgregarPersona.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnAgregarPersona.setForeground(Color.WHITE);
		btnAgregarPersona.setBackground(new Color( 255, 153, 51 ));
		btnAgregarPersona.setBounds(264, 422, 110, 30);
		btnAgregarPersona.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnAgregarPersona.setBackground(new Color(250, 145, 39));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnAgregarPersona.setBackground(new Color(255, 153, 51));
			}
		});
		contentPane.add(btnAgregarPersona);
		
		btnActualizarPersona = new JButton("Actualizar");
		btnActualizarPersona.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnActualizarPersona.setBorder(null);
		btnActualizarPersona.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnActualizarPersona.setForeground(Color.WHITE);
		btnActualizarPersona.setBackground(new Color( 255, 153, 51 ));
		btnActualizarPersona.setBounds(264, 422, 110, 30);
		btnActualizarPersona.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnActualizarPersona.setBackground(new Color(250, 145, 39));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnActualizarPersona.setBackground(new Color(255, 153, 51));
			}
		});
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
		txtCalle.setText("");
		txtAltura.setText("");
		txtPiso.setText("");
		txtDpto.setText("");
		txtFecha.setText("");
		cbxTipo.setSelectedIndex(0);
		chckDomicilio.setSelected(false);
		cbxPais.setSelectedIndex(0);
		habilitarCamposDomicilio(false);
		
		this.setVisible(true);
	}
	
	public void mostrarVentanaConDatos(PersonaDTO p)
	{
		setTitle("Actualizar Contacto");
		btnAgregarPersona.setVisible(false);
		btnActualizarPersona.setVisible(true);

		txtNombre.setText(p.getNombre());
		txtTelefono.setText(p.getTelefono());
		txtCorreo.setText(p.getCorreo());
		txtFecha.setText(p.getFecha_cumple());
		
		seleccionar(cbxTipo, p.getTipo_contacto());
		
		DomicilioDTO domicilio = p.getDomicilio();
		if(!domicilio.getPais().equals("")) 
		{
			chckDomicilio.setSelected(true);
			
			seleccionar(cbxPais, domicilio.getPais());
			seleccionar(cbxProvincia, domicilio.getProvincia());
			seleccionar(cbxLocalidad, domicilio.getLocalidad());
			txtCalle.setText(domicilio.getCalle());
			txtAltura.setText(domicilio.getAltura());
			txtPiso.setText(domicilio.getPiso());
			txtDpto.setText(domicilio.getDpto());
			
			habilitarCamposDomicilio(true);
		}
		else 
		{
			chckDomicilio.setSelected(false);
			
			cbxPais.setSelectedIndex(0);
			txtCalle.setText("");
			txtAltura.setText("");
			txtPiso.setText("");
			txtDpto.setText("");
			
			habilitarCamposDomicilio(false);
		}
		
		this.setVisible(true);
	}
	
	public void cargarPaises(List<PaisDTO> paises) {
		for (PaisDTO paisDTO : paises) 
			cbxPais.addItem(paisDTO.getNombre());
	}
	
	public void cargarProvincias(List<ProvinciaDTO> provincias) {
		cbxProvincia.removeAllItems();
		
		for (ProvinciaDTO paisDTO : provincias) 
			cbxProvincia.addItem(paisDTO.getNombre());
	}
	
	public void cargarLocalidades(List<LocalidadDTO> localidades) {
		cbxLocalidad.removeAllItems();
		
		for (LocalidadDTO localidadDTO : localidades)
			cbxLocalidad.addItem(localidadDTO.getNombre());
	}
	
	public void habilitarCamposDomicilio(boolean habilitar) 
	{
		cbxPais.setEnabled(habilitar);
		cbxProvincia.setEnabled(habilitar);
		cbxLocalidad.setEnabled(habilitar);
		txtCalle.setEnabled(habilitar);
		txtAltura.setEnabled(habilitar);
		txtPiso.setEnabled(habilitar);
		txtDpto.setEnabled(habilitar);
	}
	
	private void seleccionar(JComboBox<String> combo, String cadena) 
	{
		for (int i = 0; i < combo.getItemCount(); i++) {
			if(combo.getItemAt(i).equalsIgnoreCase(cadena)) {
				combo.setSelectedIndex(i);
				break;
			}
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
	
	public JCheckBox getChckDomicilio() 
	{
		return chckDomicilio;
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
	
	public JTextField getTxtCalle() 
	{
		return txtCalle;
	}
	
	public JTextField getTxtAltura() 
	{
		return txtAltura;
	}
	
	public JTextField getTxtPiso() 
	{
		return txtPiso;
	}
	
	public JTextField getTxtDpto() 
	{
		return txtDpto;
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

