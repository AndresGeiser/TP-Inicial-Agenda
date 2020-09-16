package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class VentanaIngreso extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField txtUsuario;
	private JTextField txtContrase�a;
	private JButton btnIngresar;
	
	public VentanaIngreso() {
		
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
		
		JLabel lblContrase�a = new JLabel("Contrase�a");
		lblContrase�a.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblContrase�a.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrase�a.setBounds(100, 235, 200, 20);
		panel.add(lblContrase�a);
		
		txtContrase�a = new JTextField();
		txtContrase�a.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtContrase�a.setColumns(10);
		txtContrase�a.setBounds(100, 266, 200, 30);
		panel.add(txtContrase�a);
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnIngresar.setBounds(136, 321, 110, 30);
		panel.add(btnIngresar);
	}
	
	public void mostrar() {
		setVisible(true);
	}
	
	public JTextField getTxtUsuario() {
		return txtUsuario;
	}
	
	public JTextField getTxtContrase�a() {
		return txtContrase�a;
	}
	
	public JButton getBtnIngresar() {
		return btnIngresar;
	}
}
