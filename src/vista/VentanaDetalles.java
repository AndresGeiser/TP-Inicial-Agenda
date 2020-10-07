package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;

import dto.DomicilioDTO;
import dto.PersonaDTO;
import dto.TipoDTO;

import java.awt.Toolkit;

public class VentanaDetalles extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private static VentanaDetalles INSTANCE;
	
	private JLabel lblNombre;
	private JLabel lblTipo;
	private JLabel lblTelefono;
	private JLabel lblCorreo;
	private JLabel lblCumpleanios;
	private JLabel lblDomicilio;
	private JLabel lblDomicilio_2;
	private JLabel lblPiso;
	private JLabel lblDpto;
	
	
	public static VentanaDetalles getInstance(VentanaAgenda ventanaAgenda, boolean bool)
	{
		if(INSTANCE == null)
			INSTANCE = new VentanaDetalles(ventanaAgenda, bool); 	
		
		return INSTANCE;
	}

	private VentanaDetalles(VentanaAgenda ventana,boolean bool) 
	{
		super(ventana, bool);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaDetalles.class.getResource("/iconos/contactox16.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 420, 410);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblIcon = new JLabel("");
		lblIcon.setBounds(175, 15, 64, 64);
		lblIcon.setIcon(new ImageIcon(VentanaDetalles.class.getResource("/iconos/contactox64.png")));
		panel.add(lblIcon);
		
		lblNombre = new JLabel("");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(10, 107, 394, 21);
		lblNombre.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel.add(lblNombre);
		
		lblTipo = new JLabel("Tipo: ");
		lblTipo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTipo.setBounds(10, 143, 394, 19);
		lblTipo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel.add(lblTipo);
		
		lblTelefono = new JLabel("Telefono: ");
		lblTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		lblTelefono.setBounds(10, 173, 394, 19);
		lblTelefono.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel.add(lblTelefono);
		
		lblCorreo = new JLabel("Correo: ");
		lblCorreo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCorreo.setBounds(10, 203, 394, 19);
		lblCorreo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel.add(lblCorreo);
		
		lblCumpleanios = new JLabel("Cumpleaños: ");
		lblCumpleanios.setHorizontalAlignment(SwingConstants.CENTER);
		lblCumpleanios.setBounds(10, 233, 394, 19);
		lblCumpleanios.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel.add(lblCumpleanios);
		
		lblDomicilio = new JLabel("Domicilio: ");
		lblDomicilio.setHorizontalAlignment(SwingConstants.CENTER);
		lblDomicilio.setBounds(10, 260, 394, 19);
		lblDomicilio.setMaximumSize(new Dimension(200, 14));
		lblDomicilio.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel.add(lblDomicilio);
		
		lblDomicilio_2 = new JLabel("");
		lblDomicilio_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDomicilio_2.setBounds(10, 279, 394, 19);
		lblDomicilio_2.setMaximumSize(new Dimension(200, 14));
		lblDomicilio_2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel.add(lblDomicilio_2);
		
		lblPiso = new JLabel("Piso: ");
		lblPiso.setHorizontalAlignment(SwingConstants.CENTER);
		lblPiso.setBounds(10, 306, 394, 19);
		lblPiso.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel.add(lblPiso);
		
		lblDpto = new JLabel("Dpto: ");
		lblDpto.setHorizontalAlignment(SwingConstants.CENTER);
		lblDpto.setBounds(10, 338, 394, 19);
		lblDpto.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel.add(lblDpto);
		
		this.setVisible(false);
	}
	
	public void cargar(PersonaDTO persona) 
	{
		this.setTitle("Detalles de: '" + persona.getNombre() + "'");
		
		lblNombre.setText(persona.getNombre());
		
		TipoDTO tipo = persona.getTipo_contacto();
		if(tipo == null)
			lblTipo.setText("Tipo: Sin informacion.");
		else
			lblTipo.setText("Tipo: " + persona.getTipo_contacto().getNombre());
			
		lblTelefono.setText("Telefono: " + persona.getTelefono());
		
		String correo = persona.getCorreo();
		if(correo.equals(""))
			correo = "Sin informacion.";
		lblCorreo.setText("Correo: " + correo);
		
		String cumpleanios = persona.getFecha_cumple();
		if(cumpleanios.equals(""))
			cumpleanios = "Sin informacion.";
		lblCumpleanios.setText("Cumpleaños: " + cumpleanios);

		DomicilioDTO domicilio = persona.getDomicilio();
		if(domicilio.getPais() == null) 
		{
			lblDomicilio.setText("Domicilio: Sin informacion.");
			lblDomicilio_2.setText("");
		}
		else 
		{
			lblDomicilio.setText("Domicilio: "  + domicilio.getCalle() + " " + domicilio.getAltura() + ",");
			lblDomicilio_2.setText(domicilio.getLocalidad().getNombre() + ", " + domicilio.getProvincia().getNombre() + ", " + domicilio.getPais().getNombre());
		}
		
		String piso = domicilio.getPiso();
		if(piso.equals(""))
			piso = "Sin informacion.";
		lblPiso.setText("Piso: " + piso);
		
		String dpto = domicilio.getDpto();
		if(dpto.equals(""))
			dpto = "Sin informacion.";
		lblDpto.setText("Dpto: " + dpto);
	}
	
	public void mostrar()
	{
		this.setVisible(true);
	}
	
}
