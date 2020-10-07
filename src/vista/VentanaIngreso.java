package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import persistencia.Conexion;

public class VentanaIngreso extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	private JTextField txtUsuario;
	private JTextField txtContraseña;
	private JButton btnIngresar;
	
	public VentanaIngreso() 
	{	
		setBounds(100, 100, 400, 400);
		setTitle("Mi Agenda - Ingreso");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel labelIcon = new JLabel("");
		labelIcon.setIcon(new ImageIcon(VentanaIngreso.class.getResource("/icons/ingreso.png")));
		labelIcon.setBounds(136, 11, 128, 128);
		panel.add(labelIcon);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(100, 163, 200, 20);
		panel.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtUsuario.setBounds(100, 194, 200, 30);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblContraseña = new JLabel("Contraseña");
		lblContraseña.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		lblContraseña.setBounds(100, 235, 200, 20);
		panel.add(lblContraseña);
		
		txtContraseña = new JTextField();
		txtContraseña.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtContraseña.setColumns(10);
		txtContraseña.setBounds(100, 266, 200, 30);
		panel.add(txtContraseña);
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnIngresar.setBounds(136, 321, 110, 30);
		panel.add(btnIngresar);
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
	
	public JTextField getTxtUsuario() { return txtUsuario; }
	
	public JTextField getTxtContraseña() { return txtContraseña; }
	
	public JButton getBtnIngresar() { return btnIngresar; }
	
	
}
