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
	
	private JTextField txtUsuario;
	private JTextField txtContraseña;
	private JButton btnConfigurar;
	
	public VentanaConfigurar() 
	{
		setBounds(100, 100, 500, 470);
		setTitle("Mi Agenda - Configuracion");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel labelIcon = new JLabel("");
		labelIcon.setIcon(new ImageIcon(VentanaConfigurar.class.getResource("/icons/config.png")));
		labelIcon.setBounds(186, 24, 128, 128);
		panel.add(labelIcon);
		
		JLabel lblMensaje = new JLabel("Ingrese los datos para configurar el servidor.");
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setBounds(10, 151, 464, 20);
		panel.add(lblMensaje);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(150, 182, 200, 20);
		panel.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtUsuario.setBounds(170, 213, 160, 30);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblContraseña = new JLabel("Contraseña");
		lblContraseña.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		lblContraseña.setBounds(150, 254, 200, 20);
		panel.add(lblContraseña);
		
		txtContraseña = new JTextField();
		txtContraseña.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtContraseña.setColumns(10);
		txtContraseña.setBounds(170, 285, 160, 30);
		panel.add(txtContraseña);
		
		btnConfigurar = new JButton("Configurar");
		btnConfigurar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnConfigurar.setBounds(187, 370, 110, 30);
		panel.add(btnConfigurar);
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
	
	public JTextField getTxtUsuario() 
	{
		return txtUsuario;
	}
	
	public JTextField getTxtContraseña() 
	{
		return txtContraseña;
	}
	
	public JButton getBtnConfigurar() 
	{
		return btnConfigurar;
	}
	
}
