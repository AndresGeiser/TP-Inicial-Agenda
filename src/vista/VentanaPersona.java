package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dto.DomicilioDTO;
import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;
import dto.TipoDTO;

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
import javax.swing.ImageIcon;

public class VentanaPersona extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private static VentanaPersona INSTANCE;
	
	private JScrollPane scrollPane;
	
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
	private JComboBox<String> cbxTipoDomicilio;
	private JTextField txtPiso;
	private JTextField txtDpto;
	
	private JButton btnConfigurarUbicaciones;
	private JButton btnConfigurarTipo;
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
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 364, 400);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		getContentPane().add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(295, 800));
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		JLabel lblNombreDeContacto = new JLabel("Nombre de contacto *");
		lblNombreDeContacto.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNombreDeContacto.setBounds(10, 11, 325, 30);
		panel.add(lblNombreDeContacto);
		
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
				 char c = evt.getKeyChar();
				 if(!Character.isDigit(c)){
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
		lblTipo.setBounds(10, 257, 121, 30);
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
		
		btnConfigurarTipo = new JButton("");
		btnConfigurarTipo.setIcon(new ImageIcon(VentanaPersona.class.getResource("/iconos/configurarTipos.png")));
		btnConfigurarTipo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConfigurarTipo.setFont(new Font("SansSerif", Font.PLAIN, 10));
		btnConfigurarTipo.setToolTipText("Configurar tipos de contacto");
		btnConfigurarTipo.setBounds(129, 257, 30, 30);
		panel.add(btnConfigurarTipo);
		
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
		
		chckDomicilio = new JCheckBox("Agregar Domicilio");
		chckDomicilio.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chckDomicilio.setBounds(10, 417, 137, 30);
		panel.add(chckDomicilio);
		
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
		
		
		JLabel lblTipoDomicilio = new JLabel("Tipo:");
		lblTipoDomicilio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoDomicilio.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblTipoDomicilio.setBounds(10, 666, 80, 30);
		panel.add(lblTipoDomicilio);
		
		cbxTipoDomicilio = new JComboBox<String>();
		cbxTipoDomicilio.setEnabled(false);
		cbxTipoDomicilio.setName("");
		cbxTipoDomicilio.setFont(new Font("SansSerif", Font.PLAIN, 12));
		cbxTipoDomicilio.setBounds(100, 666, 94, 30);
		cbxTipoDomicilio.addItem("Casa");
		cbxTipoDomicilio.addItem("Edificio");
		panel.add(cbxTipoDomicilio);
		
		JLabel lblPiso = new JLabel("Piso:");
		lblPiso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPiso.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblPiso.setBounds(10, 706, 80, 30);
		panel.add(lblPiso);
		
		txtPiso = new JTextField();
		txtPiso.setEnabled(false);
		txtPiso.setMargin(new Insets(2, 5, 2, 5));
		txtPiso.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtPiso.setColumns(10);
		txtPiso.setBounds(100, 706, 94, 30);
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
		
		JLabel lblDpto = new JLabel("Dpto:");
		lblDpto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDpto.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblDpto.setBounds(10, 746, 80, 30);
		panel.add(lblDpto);
		
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
		txtDpto.setBounds(100, 746, 94, 30);
		panel.add(txtDpto);
		
		btnConfigurarUbicaciones = new JButton("");
		btnConfigurarUbicaciones.setIcon(new ImageIcon(VentanaPersona.class.getResource("/iconos/configurarTipos.png")));
		btnConfigurarUbicaciones.setToolTipText("Configurar ubicaciones");
		btnConfigurarUbicaciones.setFont(new Font("SansSerif", Font.PLAIN, 10));
		btnConfigurarUbicaciones.setBounds(153, 417, 30, 30);
		panel.add(btnConfigurarUbicaciones);
		
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
		getContentPane().add(btnAgregarPersona);
		
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
		getContentPane().add(btnActualizarPersona);
		
		this.setVisible(false);
	}
	
	public void mostrarVentana()
	{
		setTitle("Agregar Contacto");
		scrollPane.getVerticalScrollBar().setValue(0);
		btnAgregarPersona.setVisible(true);
		btnActualizarPersona.setVisible(false);
		
		//Reseteo de los campos a su estado inicial
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
		cbxTipoDomicilio.setSelectedIndex(0);
		habilitarCamposDomicilio(false);
		
		this.setVisible(true);
	}
	
	public void mostrarVentanaConDatos(PersonaDTO p)
	{
		setTitle("Actualizar Contacto");
		scrollPane.getVerticalScrollBar().setValue(0);
		btnAgregarPersona.setVisible(false);
		btnActualizarPersona.setVisible(true);

		txtNombre.setText(p.getNombre());
		txtTelefono.setText(p.getTelefono());
		txtCorreo.setText(p.getCorreo());
		txtFecha.setText(p.getFecha_cumple());
		
		TipoDTO tipo = p.getTipo_contacto();
		if(tipo != null)
			seleccionar(cbxTipo, p.getTipo_contacto().getNombre());
		else
			cbxTipo.setSelectedIndex(0);
		
		DomicilioDTO domicilio = p.getDomicilio();
		if(domicilio.getPais() != null) 
		{
			chckDomicilio.setSelected(true);
			
			seleccionar(cbxPais, domicilio.getPais().getNombre());
			seleccionar(cbxProvincia, domicilio.getProvincia().getNombre());
			seleccionar(cbxLocalidad, domicilio.getLocalidad().getNombre());
			txtCalle.setText(domicilio.getCalle());
			txtAltura.setText(domicilio.getAltura());
			seleccionar(cbxTipoDomicilio, domicilio.getTipo());
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
			cbxTipoDomicilio.setSelectedIndex(0);
			txtPiso.setText("");
			txtDpto.setText("");
			
			habilitarCamposDomicilio(false);
		}
		
		this.setVisible(true);
	}

	
	public void cargarPaises(List<PaisDTO> paises) 
	{
		cbxPais.removeAllItems();
		for (PaisDTO paisDTO : paises) 
			cbxPais.addItem(paisDTO.getNombre());
	}
	
	public void cargarProvincias(List<ProvinciaDTO> provincias) 
	{
		cbxProvincia.removeAllItems();
		for (ProvinciaDTO paisDTO : provincias) 
			cbxProvincia.addItem(paisDTO.getNombre());
	}
	
	public void cargarLocalidades(List<LocalidadDTO> localidades) 
	{
		cbxLocalidad.removeAllItems();
		for (LocalidadDTO localidadDTO : localidades)
			cbxLocalidad.addItem(localidadDTO.getNombre());
	}
	
	public void limpiarProvincias() 
	{
		cbxProvincia.removeAllItems();
	}
	
	public void limpiarLocalidades() 
	{
		cbxLocalidad.removeAllItems();
	}
	
	public void cargarTiposDeContacto(List<TipoDTO> tipos) 
	{
		cbxTipo.removeAllItems();
		cbxTipo.addItem(null);
		for (TipoDTO tipo : tipos)
			cbxTipo.addItem(tipo.getNombre());
	}
	
	
	/**
	 * Activa o desactiva los campos del domicilio  
	 */
	public void habilitarCamposDomicilio(boolean habilitar) 
	{
		cbxPais.setEnabled(habilitar);
		cbxProvincia.setEnabled(habilitar);
		cbxLocalidad.setEnabled(habilitar);
		txtCalle.setEnabled(habilitar);
		txtAltura.setEnabled(habilitar);
		cbxTipoDomicilio.setEnabled(habilitar);
		txtPiso.setEnabled(habilitar);
		txtDpto.setEnabled(habilitar);
	}
	
	
	/**
	 * Selecciona el item del combo que coincida con el string 
	 */
	private void seleccionar(JComboBox<String> combo, String cadena) 
	{
		for (int i = 1; i < combo.getItemCount(); i++) 
		{
			if(combo.getItemAt(i).equalsIgnoreCase(cadena))
			{
				combo.setSelectedIndex(i);
				break;
			}
		}
	}
	
	
	public JTextField getTxtNombre() { return txtNombre; }

	public JTextField getTxtTelefono() { return txtTelefono; }
	
	public JTextField getTxtCorreo() { return txtCorreo; }
	
	public JTextField getTxtCumple() { return txtFecha; }
	
	public JComboBox<String> getTipo() { return cbxTipo; }
	
	public JCheckBox getChckDomicilio() { return chckDomicilio;}
	
	public JComboBox<String> getCbxPais() { return cbxPais; }
	
	public JComboBox<String> getCbxProvincia() { return cbxProvincia; }
	
	public JComboBox<String> getCbxLocalidad() { return cbxLocalidad; }
	
	public JTextField getTxtCalle() { return txtCalle; }
	
	public JTextField getTxtAltura() { return txtAltura; }
	
	public JComboBox<String> getCbxTipoDomicilio() { return cbxTipoDomicilio; }
	
	public JTextField getTxtPiso() { return txtPiso; }
	
	public JTextField getTxtDpto() { return txtDpto; }
	
	public JButton getBtnConfigurarUbicaciones() { return btnConfigurarUbicaciones; }
	
	public JButton getBtnConfigurarTipo() { return btnConfigurarTipo; }
	
	public JButton getBtnAgregarPersona() { return btnAgregarPersona; }
	
	public JButton getBtnActualizarPersona() { return btnActualizarPersona; }

	public void cerrar()
	{
		this.dispose();
	}
}

