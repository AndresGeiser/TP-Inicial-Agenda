package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingConstants;

import persistencia.Conexion;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class VentanaConfigurar extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static VentanaConfigurar INSTANCE;

	
	private JTextField txtUsuario;
	private JTextField txtContraseña;
	private JButton btnConfigurar;
	private JTextField txtHost;
	private JTextField txtPuerto;
	
	public VentanaConfigurar() 
	{
		setBounds(100, 100, 500, 470);
		setTitle("Mi Agenda - Configuracion");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel labelIcon = new JLabel("");
		labelIcon.setIcon(new ImageIcon(VentanaConfigurar.class.getResource("/iconos/config.png")));
		labelIcon.setBounds(186, 24, 128, 128);
		panel.add(labelIcon);
		
		JLabel lblMensaje = new JLabel("Ingrese los datos para configurar el servidor.");
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setBounds(10, 151, 464, 20);
		panel.add(lblMensaje);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(-30, 182, 200, 20);
		panel.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtUsuario.setBounds(47, 213, 160, 30);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblContraseña = new JLabel("Contraseña");
		lblContraseña.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		lblContraseña.setBounds(-18, 254, 200, 20);
		panel.add(lblContraseña);
		
		txtContraseña = new JTextField();
		txtContraseña.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtContraseña.setColumns(10);
		txtContraseña.setBounds(47, 285, 160, 30);
		panel.add(txtContraseña);
		
		btnConfigurar = new JButton("Configurar");
		btnConfigurar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnConfigurar.setBounds(187, 370, 110, 30);
		panel.add(btnConfigurar);
		
		JLabel lblHost = new JLabel("Host");
		lblHost.setHorizontalAlignment(SwingConstants.CENTER);
		lblHost.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblHost.setBounds(189, 182, 200, 20);
		panel.add(lblHost);
		
		txtHost = new JTextField();
		txtHost.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtHost.setColumns(10);
		txtHost.setBounds(269, 213, 160, 30);
		panel.add(txtHost);
		
		txtPuerto = new JTextField();
		txtPuerto.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtPuerto.setColumns(10);
		txtPuerto.setBounds(269, 285, 160, 30);
		panel.add(txtPuerto);
		
		JLabel lblPuerto = new JLabel("Puerto");
		lblPuerto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPuerto.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPuerto.setBounds(189, 258, 200, 20);
		panel.add(lblPuerto);
	}
	
	public String getTxtHost() {
		return txtHost.getText();
	}

	public void setTxtHost(String txtHost) {
		this.txtHost.setText(txtHost);
	}

	public String getTxtPuerto() {
		return txtPuerto.getText();
	}

	public void setTxtPuerto(String txtPuerto) {
		this.txtPuerto.setText(txtPuerto);
	}

	public void setTxtUsuario(String txtUsuario) {
		this.txtUsuario.setText(txtUsuario);
	}

	public void setTxtContraseña(String txtContraseña) {
		this.txtContraseña.setText(txtContraseña);
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
		        	null, "Estas seguro que quieres salir?", 
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
	
	public void cerrar()
	{
		setVisible(false);
	}
	
	public String getTxtUsuario() 
	{
		return txtUsuario.getText();
	}
	
	public String getTxtContraseña() 
	{
		return txtContraseña.getText();
	}
	
	public JButton getBtnConfigurar() 
	{
		return btnConfigurar;
	}

	public static VentanaConfigurar getInstance() 
	{
		if(INSTANCE == null)
			INSTANCE = new VentanaConfigurar(); 	
		
		return INSTANCE;
	}
}
