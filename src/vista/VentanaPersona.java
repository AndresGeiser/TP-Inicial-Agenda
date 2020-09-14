package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.awt.Color;

public class VentanaPersona extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JButton btnAgregarPersona;
	private static VentanaPersona INSTANCE;
	private JTextField txtCorreo;
	private JComboBox<String> cbxTipo;
	private JTextField txtFecha;
	
	
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
		setBounds(100, 100, 343, 419);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 307, 359);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNombreYApellido = new JLabel("Nombre y apellido *");
		lblNombreYApellido.setBounds(10, 11, 287, 14);
		panel.add(lblNombreYApellido);
		
		JLabel lblTelfono = new JLabel("Telefono *");
		lblTelfono.setBounds(10, 67, 287, 14);
		panel.add(lblTelfono);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(10, 36, 287, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(10, 92, 287, 20);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		btnAgregarPersona = new JButton("Agregar");
		btnAgregarPersona.setBounds(208, 325, 89, 23);
		panel.add(btnAgregarPersona);
		
		JLabel lblCorreo = new JLabel("Correo electronico");
		lblCorreo.setBounds(10, 123, 287, 14);
		panel.add(lblCorreo);
		
		txtCorreo = new JTextField();
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(10, 148, 287, 20);
		panel.add(txtCorreo);
		
		JLabel lblTipo = new JLabel("Tipo de contacto");
		lblTipo.setBounds(10, 179, 287, 14);
		panel.add(lblTipo);
		
		cbxTipo = new JComboBox<String>();
		cbxTipo.setName("");
		cbxTipo.setBounds(10, 204, 149, 20);
		cbxTipo.addItem("Trabajo");
		cbxTipo.addItem("Familia");
		cbxTipo.addItem("Amigos");
		cbxTipo.addItem("Universidad");
		panel.add(cbxTipo);
		
		JLabel lblFecha = new JLabel("Fecha de Cumplea\u00F1os");
		lblFecha.setBounds(10, 235, 205, 14);
		panel.add(lblFecha);
		
		JLabel lblEjFecha = new JLabel("Ej: 27/05/1998");
		lblEjFecha.setForeground(Color.GRAY);
		lblEjFecha.setBounds(208, 235, 89, 14);
		panel.add(lblEjFecha);

		txtFecha = new JTextField();
		txtFecha.setColumns(10);
		txtFecha.setBounds(10, 260, 287, 20);
		panel.add(txtFecha);
		
		this.setVisible(false);
	}
	
	public void mostrarVentana()
	{
		this.setVisible(true);
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
	
	public JButton getBtnAgregarPersona() 
	{
		return btnAgregarPersona;
	}

	public void cerrar()
	{
		this.txtNombre.setText(null);
		this.txtTelefono.setText(null);
		this.dispose();
	}
}

