package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.ProvinciaDTO;

import javax.swing.border.LineBorder;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class VentanaUbicaciones extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private static VentanaUbicaciones INSTANCE;
	
	private JButton btnPaises;
	private JButton btnProvincias;
	private JButton btnLocalidades;
	
	JScrollPane scrollPanePrincipal;
	
	//Componentes Panel Pais
	private JPanel panelPais;
	private JTextField txtNombrePais;
	private JComboBox<String> cbxPaisesEditar;
	private JTextField txtNombreNuevoPais;
	private JPanel panelChksBorrarPais;
	private List<JCheckBox> chksPaisesBorrar;
	private JButton btnAgregarPais;
	private JButton btnEditarPais;
	private JButton btnBorrarPais;
	
	
	//Componentes Panel Provincia
	private JPanel panelProvincia;
	private JComboBox<String> cbxPaisesProvincia;
	private JTextField txtNombreProvincia;
	private JComboBox<String> cbxProvinciasEditar;
	private JTextField txtNombreNuevoProvincia;
	private JPanel panelChksBorrarProvincia;
	private List<JCheckBox> chksProvinciasBorrar;
	private JButton btnAgregarProvincia;
	private JButton btnEditarProvincia;
	private JButton btnBorrarProvincia;
	
	
	//Componentes Panel Localidad
	private JPanel panelLocalidad;
	private JComboBox<String> cbxPaisesLocalidad;
	private JComboBox<String> cbxProvinciasLocalidad;
	private JTextField txtNombreLocalidad;
	private JComboBox<String> cbxLocalidadesEditar;
	private JTextField txtNombreNuevoLocalidad;
	private JPanel panelChksBorrarLocalidad;
	private List<JCheckBox> chksLocalidadesBorrar;
	private JButton btnAgregarLocalidad;
	private JButton btnEditarLocalidad;
	private JButton btnBorrarLocalidad;


	public static VentanaUbicaciones getInstance(VentanaPersona ventanaPersona, boolean bool)
	{
		if(INSTANCE == null)
			INSTANCE = new VentanaUbicaciones(ventanaPersona, bool); 	
		
		return INSTANCE;
	}

	private VentanaUbicaciones(VentanaPersona ventanaPersona, boolean bool) 
	{
		super(ventanaPersona, bool);
		setTitle("Configuracion de ubicaciones");
		setBounds(new Rectangle(200, 50, 500, 600));
		
		JPanel panelSuperiorBotones = new JPanel();
		panelSuperiorBotones.setPreferredSize(new Dimension(10, 50));
		getContentPane().add(panelSuperiorBotones, BorderLayout.NORTH);
		panelSuperiorBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
		
		btnPaises = new JButton("Paises");
		panelSuperiorBotones.add(btnPaises);
		
		btnProvincias = new JButton("Provincias");
		panelSuperiorBotones.add(btnProvincias);
		
		btnLocalidades = new JButton("Localidades");
		panelSuperiorBotones.add(btnLocalidades);
		
		iniPanelPais();
		iniPanelProvincia();
		iniPanelLocalidad();
		
		scrollPanePrincipal = new JScrollPane();
		scrollPanePrincipal.setViewportView(panelPais);
		getContentPane().add(scrollPanePrincipal, BorderLayout.CENTER);

		chksPaisesBorrar = new ArrayList<JCheckBox>();
		chksProvinciasBorrar = new ArrayList<JCheckBox>();
		chksLocalidadesBorrar = new ArrayList<JCheckBox>();
	}
	
	private void iniPanelPais() 
	{
		//Panel Pais
		panelPais = new JPanel();
		panelPais.setBorder(new LineBorder(Color.WHITE, 15, true));
		//scrollPane.setViewportView(panelPais);
		panelPais.setLayout(new BoxLayout(panelPais, BoxLayout.Y_AXIS));
		
		JPanel panelPaisAgregar = new JPanel();
		panelPaisAgregar.setMinimumSize(new Dimension(300, 300));
		panelPaisAgregar.setBackground(Color.WHITE);
		panelPaisAgregar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Agregar Pais", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
		panelPais.add(panelPaisAgregar);
		panelPaisAgregar.setLayout(new BorderLayout(0, 0));
		
		JPanel panelAgregarPaisInferior = new JPanel();
		panelAgregarPaisInferior.setBackground(Color.WHITE);
		panelPaisAgregar.add(panelAgregarPaisInferior, BorderLayout.SOUTH);
		panelAgregarPaisInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnAgregarPais = new JButton("Agregar");
		panelAgregarPaisInferior.add(btnAgregarPais);
		
		JPanel panelAgregarPaisCentro = new JPanel();
		panelAgregarPaisCentro.setBackground(Color.WHITE);
		panelAgregarPaisCentro.setPreferredSize(new Dimension(10, 50));
		panelAgregarPaisCentro.setLayout(null);
		panelAgregarPaisCentro.setAutoscrolls(true);
		panelPaisAgregar.add(panelAgregarPaisCentro, BorderLayout.CENTER);
		
		JLabel lblPaisNuevo = new JLabel("Ingrese el nombre del nuevo pais:");
		lblPaisNuevo.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPaisNuevo.setBounds(10, 11, 200, 20);
		panelAgregarPaisCentro.add(lblPaisNuevo);
		
		txtNombrePais = new JTextField();
		txtNombrePais.setColumns(10);
		txtNombrePais.setBounds(209, 11, 203, 20);
		panelAgregarPaisCentro.add(txtNombrePais);
		
		JPanel panelPaisEditar = new JPanel();
		panelPaisEditar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Editar Pais", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
		panelPaisEditar.setBackground(new Color(255, 255, 255));
		panelPais.add(panelPaisEditar);
		panelPaisEditar.setLayout(new BorderLayout(0, 0));
		
		JPanel panelEditarPaisInferior = new JPanel();
		panelEditarPaisInferior.setBackground(Color.WHITE);
		panelPaisEditar.add(panelEditarPaisInferior, BorderLayout.SOUTH);
		panelEditarPaisInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnEditarPais = new JButton("Editar");
		panelEditarPaisInferior.add(btnEditarPais);
		
		JPanel panelEditarPaisCentro = new JPanel();
		panelEditarPaisCentro.setBackground(Color.WHITE);
		panelEditarPaisCentro.setPreferredSize(new Dimension(10, 80));
		panelEditarPaisCentro.setLayout(null);
		panelEditarPaisCentro.setAutoscrolls(true);
		panelPaisEditar.add(panelEditarPaisCentro, BorderLayout.CENTER);
		
		JLabel lblPaisSelectEditar = new JLabel("Seleccione el pais que desea editar:");
		lblPaisSelectEditar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPaisSelectEditar.setBounds(10, 11, 200, 20);
		panelEditarPaisCentro.add(lblPaisSelectEditar);
		
		cbxPaisesEditar = new JComboBox<String>();
		cbxPaisesEditar.setBounds(220, 11, 192, 20);
		panelEditarPaisCentro.add(cbxPaisesEditar);
		
		JLabel lblPaisNuevoNombre = new JLabel("Ingrese el nuevo nombre del pais:");
		lblPaisNuevoNombre.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPaisNuevoNombre.setBounds(10, 42, 200, 20);
		panelEditarPaisCentro.add(lblPaisNuevoNombre);
		
		txtNombreNuevoPais = new JTextField();
		txtNombreNuevoPais.setColumns(10);
		txtNombreNuevoPais.setBounds(220, 42, 192, 20);
		panelEditarPaisCentro.add(txtNombreNuevoPais);
		
		JPanel panelPaisBorrar = new JPanel();
		panelPaisBorrar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Borrar Pais", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		panelPaisBorrar.setBackground(Color.WHITE);
		panelPais.add(panelPaisBorrar);
		panelPaisBorrar.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBorrarPaisInferior = new JPanel();
		panelBorrarPaisInferior.setBackground(Color.WHITE);
		panelPaisBorrar.add(panelBorrarPaisInferior, BorderLayout.SOUTH);
		panelBorrarPaisInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnBorrarPais = new JButton("Borrar");
		panelBorrarPaisInferior.add(btnBorrarPais);
		
		JPanel panelBorrarPaisCentro = new JPanel();
		panelBorrarPaisCentro.setLayout(null);
		panelBorrarPaisCentro.setPreferredSize(new Dimension(10, 200));
		panelBorrarPaisCentro.setBackground(Color.WHITE);
		panelBorrarPaisCentro.setAutoscrolls(true);
		panelPaisBorrar.add(panelBorrarPaisCentro, BorderLayout.CENTER);
		
		JLabel lblSeleccioneLosPaises = new JLabel("Seleccione los paises que desea borrar:");
		lblSeleccioneLosPaises.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSeleccioneLosPaises.setBounds(10, 11, 251, 20);
		panelBorrarPaisCentro.add(lblSeleccioneLosPaises);
		
		JScrollPane scrollPanePaisBorrar = new JScrollPane();
		scrollPanePaisBorrar.setBounds(61, 39, 280, 150);
		panelBorrarPaisCentro.add(scrollPanePaisBorrar);
		
		panelChksBorrarPais = new JPanel();
		panelChksBorrarPais.setLayout(new BoxLayout(panelChksBorrarPais, BoxLayout.Y_AXIS));
		scrollPanePaisBorrar.setViewportView(panelChksBorrarPais);
	}
	
	private void iniPanelProvincia() 
	{
		//Panel Provincia
		panelProvincia = new JPanel();
		panelProvincia.setBorder(new LineBorder(Color.WHITE, 15, true));
		//scrollPane.setViewportView(panelProvincia);
		panelProvincia.setLayout(new BoxLayout(panelProvincia, BoxLayout.Y_AXIS));
		
		JPanel panelSelecPais = new JPanel();
		panelSelecPais.setBackground(Color.WHITE);
		panelSelecPais.setPreferredSize(new Dimension(10, 80));
		panelProvincia.add(panelSelecPais);
		panelSelecPais.setLayout(null);
		
		JLabel lblSelecPais = new JLabel("Seleccione el pais al cual agregar, editar o borrar una provincia:");
		lblSelecPais.setBounds(10, 11, 404, 20);
		panelSelecPais.add(lblSelecPais);
		lblSelecPais.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		cbxPaisesProvincia = new JComboBox<String>();
		cbxPaisesProvincia.setBounds(10, 42, 158, 20);
		panelSelecPais.add(cbxPaisesProvincia);
		
		JPanel panelProvinciaAgregar = new JPanel();
		panelProvinciaAgregar.setMinimumSize(new Dimension(300, 300));
		panelProvinciaAgregar.setBackground(Color.WHITE);
		panelProvinciaAgregar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Agregar Provincia", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		panelProvincia.add(panelProvinciaAgregar);
		panelProvinciaAgregar.setLayout(new BorderLayout(0, 0));
		
		JPanel panelAgregarProvinciaInferior = new JPanel();
		panelAgregarProvinciaInferior.setBackground(Color.WHITE);
		panelProvinciaAgregar.add(panelAgregarProvinciaInferior, BorderLayout.SOUTH);
		panelAgregarProvinciaInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnAgregarProvincia = new JButton("Agregar");
		panelAgregarProvinciaInferior.add(btnAgregarProvincia);
		
		JPanel panelAgregarProvinciaCentro = new JPanel();
		panelAgregarProvinciaCentro.setPreferredSize(new Dimension(10, 50));
		panelAgregarProvinciaCentro.setBackground(Color.WHITE);
		panelAgregarProvinciaCentro.setLayout(null);
		panelProvinciaAgregar.add(panelAgregarProvinciaCentro, BorderLayout.CENTER);
		
		JLabel lblProvinciaNuevo = new JLabel("Ingrese el nombre de la nueva provincia:");
		lblProvinciaNuevo.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblProvinciaNuevo.setBounds(10, 11, 232, 20);
		panelAgregarProvinciaCentro.add(lblProvinciaNuevo);
		
		txtNombreProvincia = new JTextField();
		txtNombreProvincia.setColumns(10);
		txtNombreProvincia.setBounds(241, 12, 174, 20);
		panelAgregarProvinciaCentro.add(txtNombreProvincia);
		
		JPanel panelProvinciaEditar = new JPanel();
		panelProvinciaEditar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Editar Provincia", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		panelProvinciaEditar.setBackground(new Color(255, 255, 255));
		panelProvincia.add(panelProvinciaEditar);
		panelProvinciaEditar.setLayout(new BorderLayout(0, 0));
		
		JPanel panelEditarProvinciaInferior = new JPanel();
		panelEditarProvinciaInferior.setBackground(Color.WHITE);
		panelProvinciaEditar.add(panelEditarProvinciaInferior, BorderLayout.SOUTH);
		panelEditarProvinciaInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnEditarProvincia = new JButton("Editar");
		panelEditarProvinciaInferior.add(btnEditarProvincia);
		
		JPanel panelEditarProvinciaCentro = new JPanel();
		panelEditarProvinciaCentro.setBackground(Color.WHITE);
		panelEditarProvinciaCentro.setPreferredSize(new Dimension(10, 80));
		panelEditarProvinciaCentro.setLayout(null);
		panelEditarProvinciaCentro.setAutoscrolls(true);
		panelProvinciaEditar.add(panelEditarProvinciaCentro, BorderLayout.CENTER);
		
		JLabel lblSelectEditarProvincia = new JLabel("Seleccione la provincia que desea editar:");
		lblSelectEditarProvincia.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSelectEditarProvincia.setBounds(10, 11, 225, 20);
		panelEditarProvinciaCentro.add(lblSelectEditarProvincia);
		
		cbxProvinciasEditar = new JComboBox<String>();
		cbxProvinciasEditar.setBounds(245, 11, 167, 20);
		panelEditarProvinciaCentro.add(cbxProvinciasEditar);
		
		JLabel lblNuevoNombreProvincia = new JLabel("Ingrese el nuevo nombre de la provincia:");
		lblNuevoNombreProvincia.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNuevoNombreProvincia.setBounds(10, 42, 225, 20);
		panelEditarProvinciaCentro.add(lblNuevoNombreProvincia);
		
		txtNombreNuevoProvincia = new JTextField();
		txtNombreNuevoProvincia.setColumns(10);
		txtNombreNuevoProvincia.setBounds(245, 42, 167, 20);
		panelEditarProvinciaCentro.add(txtNombreNuevoProvincia);
		
		JPanel panelProvinciaBorrar = new JPanel();
		panelProvinciaBorrar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Borrar Provincia", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		panelProvinciaBorrar.setBackground(Color.WHITE);
		panelProvincia.add(panelProvinciaBorrar);
		panelProvinciaBorrar.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBorrarProvinciaInferior = new JPanel();
		panelBorrarProvinciaInferior.setBackground(Color.WHITE);
		panelProvinciaBorrar.add(panelBorrarProvinciaInferior, BorderLayout.SOUTH);
		panelBorrarProvinciaInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnBorrarProvincia = new JButton("Borrar");
		panelBorrarProvinciaInferior.add(btnBorrarProvincia);
		
		JPanel panelBorrarProvinciaCentro = new JPanel();
		panelBorrarProvinciaCentro.setLayout(null);
		panelBorrarProvinciaCentro.setPreferredSize(new Dimension(10, 200));
		panelBorrarProvinciaCentro.setBackground(Color.WHITE);
		panelBorrarProvinciaCentro.setAutoscrolls(true);
		panelProvinciaBorrar.add(panelBorrarProvinciaCentro, BorderLayout.CENTER);
		
		JLabel lblSelecProvinciaBorrar = new JLabel("Seleccione las provincias que desea borrar:");
		lblSelecProvinciaBorrar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSelecProvinciaBorrar.setBounds(10, 11, 251, 20);
		panelBorrarProvinciaCentro.add(lblSelecProvinciaBorrar);
		
		JScrollPane scrollPaneProvinciaBorrar = new JScrollPane();
		scrollPaneProvinciaBorrar.setBounds(61, 39, 280, 150);
		panelBorrarProvinciaCentro.add(scrollPaneProvinciaBorrar);
		
		panelChksBorrarProvincia = new JPanel();
		panelChksBorrarProvincia.setLayout(new BoxLayout(panelChksBorrarProvincia, BoxLayout.Y_AXIS));
		scrollPaneProvinciaBorrar.setViewportView(panelChksBorrarProvincia);
	}

	private void iniPanelLocalidad() 
	{
		//Panel Localidad
		panelLocalidad = new JPanel();
		panelLocalidad.setBorder(new LineBorder(Color.WHITE, 15, true));
		//scrollPane.setViewportView(panelLocalidad);
		panelLocalidad.setLayout(new BoxLayout(panelLocalidad, BoxLayout.Y_AXIS));
		
		JPanel panelSelecProvincia = new JPanel();
		panelSelecProvincia.setBackground(Color.WHITE);
		panelSelecProvincia.setPreferredSize(new Dimension(10, 110));
		panelLocalidad.add(panelSelecProvincia);
		panelSelecProvincia.setLayout(null);
		
		JLabel lblSelecProvincia = new JLabel("Seleccione la provincia al cual agregar, editar o borrar una localidad:");
		lblSelecProvincia.setBounds(10, 11, 404, 20);
		panelSelecProvincia.add(lblSelecProvincia);
		lblSelecProvincia.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		cbxPaisesLocalidad = new JComboBox<String>();
		cbxPaisesLocalidad.setBounds(10, 42, 158, 20);
		panelSelecProvincia.add(cbxPaisesLocalidad);
		
		cbxProvinciasLocalidad = new JComboBox<String>();
		cbxProvinciasLocalidad.setBounds(10, 69, 158, 20);
		panelSelecProvincia.add(cbxProvinciasLocalidad);
		
		JPanel panelLocalidadAgregar = new JPanel();
		panelLocalidadAgregar.setMinimumSize(new Dimension(300, 300));
		panelLocalidadAgregar.setBackground(Color.WHITE);
		panelLocalidadAgregar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Agregar Localidad", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		panelLocalidad.add(panelLocalidadAgregar);
		panelLocalidadAgregar.setLayout(new BorderLayout(0, 0));
		
		JPanel panelAgregarLocalidadInferior = new JPanel();
		panelAgregarLocalidadInferior.setBackground(Color.WHITE);
		panelLocalidadAgregar.add(panelAgregarLocalidadInferior, BorderLayout.SOUTH);
		panelAgregarLocalidadInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnAgregarLocalidad = new JButton("Agregar");
		panelAgregarLocalidadInferior.add(btnAgregarLocalidad);
		
		JPanel panelAgregarLocalidadCentro = new JPanel();
		panelAgregarLocalidadCentro.setPreferredSize(new Dimension(10, 50));
		panelAgregarLocalidadCentro.setBackground(Color.WHITE);
		panelAgregarLocalidadCentro.setLayout(null);
		panelLocalidadAgregar.add(panelAgregarLocalidadCentro, BorderLayout.CENTER);
		
		JLabel lblLocalidadNuevo = new JLabel("Ingrese el nombre de la nueva localidad:");
		lblLocalidadNuevo.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblLocalidadNuevo.setBounds(10, 11, 232, 20);
		panelAgregarLocalidadCentro.add(lblLocalidadNuevo);
		
		txtNombreLocalidad = new JTextField();
		txtNombreLocalidad.setColumns(10);
		txtNombreLocalidad.setBounds(241, 12, 174, 20);
		panelAgregarLocalidadCentro.add(txtNombreLocalidad);
		
		JPanel panelLocalidadEditar = new JPanel();
		panelLocalidadEditar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Editar Localidad", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		panelLocalidadEditar.setBackground(new Color(255, 255, 255));
		panelLocalidad.add(panelLocalidadEditar);
		panelLocalidadEditar.setLayout(new BorderLayout(0, 0));
		
		JPanel panelEditarLocalidadInferior = new JPanel();
		panelEditarLocalidadInferior.setBackground(Color.WHITE);
		panelLocalidadEditar.add(panelEditarLocalidadInferior, BorderLayout.SOUTH);
		panelEditarLocalidadInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnEditarLocalidad = new JButton("Editar");
		panelEditarLocalidadInferior.add(btnEditarLocalidad);
		
		JPanel panelEditarLocalidadCentro = new JPanel();
		panelEditarLocalidadCentro.setBackground(Color.WHITE);
		panelEditarLocalidadCentro.setPreferredSize(new Dimension(10, 80));
		panelEditarLocalidadCentro.setLayout(null);
		panelEditarLocalidadCentro.setAutoscrolls(true);
		panelLocalidadEditar.add(panelEditarLocalidadCentro, BorderLayout.CENTER);
		
		JLabel lblSelectEditarLocalidad = new JLabel("Seleccione la localidad que desea editar:");
		lblSelectEditarLocalidad.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSelectEditarLocalidad.setBounds(10, 11, 232, 20);
		panelEditarLocalidadCentro.add(lblSelectEditarLocalidad);
		
		cbxLocalidadesEditar = new JComboBox<String>();
		cbxLocalidadesEditar.setBounds(252, 11, 160, 20);
		panelEditarLocalidadCentro.add(cbxLocalidadesEditar);
		
		JLabel lblNuevoNombreLocalidad = new JLabel("Ingrese el nuevo nombre de la localidad:");
		lblNuevoNombreLocalidad.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNuevoNombreLocalidad.setBounds(10, 42, 225, 20);
		panelEditarLocalidadCentro.add(lblNuevoNombreLocalidad);
		
		txtNombreNuevoLocalidad = new JTextField();
		txtNombreNuevoLocalidad.setColumns(10);
		txtNombreNuevoLocalidad.setBounds(252, 42, 160, 20);
		panelEditarLocalidadCentro.add(txtNombreNuevoLocalidad);
		
		JPanel panelLocalidadBorrar = new JPanel();
		panelLocalidadBorrar.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Borrar Localidad", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		panelLocalidadBorrar.setBackground(Color.WHITE);
		panelLocalidad.add(panelLocalidadBorrar);
		panelLocalidadBorrar.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBorrarLocalidadInferior = new JPanel();
		panelBorrarLocalidadInferior.setBackground(Color.WHITE);
		panelLocalidadBorrar.add(panelBorrarLocalidadInferior, BorderLayout.SOUTH);
		panelBorrarLocalidadInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnBorrarLocalidad = new JButton("Borrar");
		panelBorrarLocalidadInferior.add(btnBorrarLocalidad);
		
		JPanel panelBorrarLocalidadCentro = new JPanel();
		panelBorrarLocalidadCentro.setLayout(null);
		panelBorrarLocalidadCentro.setPreferredSize(new Dimension(10, 200));
		panelBorrarLocalidadCentro.setBackground(Color.WHITE);
		panelBorrarLocalidadCentro.setAutoscrolls(true);
		panelLocalidadBorrar.add(panelBorrarLocalidadCentro, BorderLayout.CENTER);
		
		JLabel lblSelecLocalidadBorrar = new JLabel("Seleccione las localidades que desea borrar:");
		lblSelecLocalidadBorrar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSelecLocalidadBorrar.setBounds(10, 11, 259, 20);
		panelBorrarLocalidadCentro.add(lblSelecLocalidadBorrar);
		
		JScrollPane scrollPaneLocalidadBorrar = new JScrollPane();
		scrollPaneLocalidadBorrar.setBounds(61, 39, 280, 150);
		panelBorrarLocalidadCentro.add(scrollPaneLocalidadBorrar);
		
		panelChksBorrarLocalidad = new JPanel();
		panelChksBorrarLocalidad.setLayout(new BoxLayout(panelChksBorrarLocalidad, BoxLayout.Y_AXIS));
		scrollPaneLocalidadBorrar.setViewportView(panelChksBorrarLocalidad);
	}

	
	public void mostrar() 
	{
		scrollPanePrincipal.setViewportView(panelPais);
		this.setVisible(true);
	}
	
	public void mostrarPanelPais() 
	{
		scrollPanePrincipal.setViewportView(panelPais);
	}
	
	public void mostrarPanelProvincia() 
	{
		scrollPanePrincipal.setViewportView(panelProvincia);
	}
	
	public void mostrarPanelLocalidad() 
	{
		scrollPanePrincipal.setViewportView(panelLocalidad);
	}
	
	public void cargarPaises(List<PaisDTO> paises) 
	{
		cbxPaisesEditar.removeAllItems();
		cbxPaisesProvincia.removeAllItems();
		cbxPaisesLocalidad.removeAllItems();
		panelChksBorrarPais.removeAll();
		chksPaisesBorrar.clear();
		for (PaisDTO pais : paises) 
		{
			cbxPaisesEditar.addItem(pais.getNombre());
			cbxPaisesProvincia.addItem(pais.getNombre());
			cbxPaisesLocalidad.addItem(pais.getNombre());
			
			JCheckBox chkPais = new JCheckBox(pais.getNombre());
			panelChksBorrarPais.add(chkPais);
			chksPaisesBorrar.add(chkPais);
		}
		
		panelChksBorrarPais.revalidate();
		panelChksBorrarPais.repaint();
	}
	
	public void cargarPanelProvincia(List<ProvinciaDTO> provincias) 
	{
		cbxProvinciasEditar.removeAllItems();
		panelChksBorrarProvincia.removeAll();
		chksProvinciasBorrar.clear();
		for (ProvinciaDTO provincia : provincias) 
		{
			cbxProvinciasEditar.addItem(provincia.getNombre());
			
			JCheckBox chkProvincia = new JCheckBox(provincia.getNombre());
			panelChksBorrarProvincia.add(chkProvincia);
			chksProvinciasBorrar.add(chkProvincia);
		}
		
		panelChksBorrarProvincia.revalidate();
		panelChksBorrarProvincia.repaint();
	}
	
	public void cargarProvinciasPanelLocalidad(List<ProvinciaDTO> provincias) 
	{
		cbxProvinciasLocalidad.removeAllItems();
		for (ProvinciaDTO provincia : provincias) 
			cbxProvinciasLocalidad.addItem(provincia.getNombre());
	}
	
	public void cargarLocalidadesPanelLocalidad(List<LocalidadDTO> localidades) 
	{
		cbxLocalidadesEditar.removeAllItems();
		panelChksBorrarLocalidad.removeAll();
		chksLocalidadesBorrar.clear();
		for (LocalidadDTO localidad : localidades) 
		{
			cbxLocalidadesEditar.addItem(localidad.getNombre());
			
			JCheckBox chkLocalidad = new JCheckBox(localidad.getNombre());
			panelChksBorrarLocalidad.add(chkLocalidad);
			chksLocalidadesBorrar.add(chkLocalidad);
		}
		
		panelChksBorrarLocalidad.revalidate();
		panelChksBorrarLocalidad.repaint();
	}

	public void limpiarCampos() 
	{
		txtNombrePais.setText("");
		txtNombreNuevoPais.setText("");
		
		txtNombreProvincia.setText("");
		txtNombreNuevoProvincia.setText("");
		
		txtNombreLocalidad.setText("");
		txtNombreNuevoLocalidad.setText("");
	}
	
	//Getters
	public JButton getBtnPaises() { return btnPaises; }
	
	public JButton getBtnProvincias() { return btnProvincias; }
	
	public JButton getBtnLocalidades() { return btnLocalidades; }
	
	//Getters componentes Panel Pais
	public JButton getBtnAgregarPais() { return btnAgregarPais; }
	
	public JButton getBtnEditarPais() { return btnEditarPais; }
	
	public JButton getBtnBorrarPais() { return btnBorrarPais; }
	
	public JTextField getTxtNombrePais() { return txtNombrePais; }

	public JComboBox<String> getCbxPaisesEditar() { return cbxPaisesEditar; }
	
	public JTextField getTxtNombreNuevoPais() { return txtNombreNuevoPais; }
	
	public List<JCheckBox> getChksPaisesBorrar() { return chksPaisesBorrar; } 
	
	
	//Getters componentes Panel Provincia
	public JButton getBtnAgregarProvincia() { return btnAgregarProvincia; }
	
	public JButton getBtnEditarProvincia() { return btnEditarProvincia; }
	
	public JButton getBtnBorrarProvincia() { return btnBorrarProvincia; }
	
	public JComboBox<String> getCbxPaisesProvincia() { return cbxPaisesProvincia; }
	
	public JTextField getTxtNombreProvincia() { return txtNombreProvincia; }
	
	public JComboBox<String> getCbxProvinciasEditar() { return cbxProvinciasEditar; }
	
	public JTextField getTxtNombreNuevoProvincia() { return txtNombreNuevoProvincia; }
	
	public List<JCheckBox> getChksProvinciasBorrar() { return chksProvinciasBorrar; } 
	
	//Getters componentes Panel Localidad
	public JButton getBtnAgregarLocalidad() { return btnAgregarLocalidad; }
	
	public JButton getBtnEditarLocalidad() { return btnEditarLocalidad; }
	
	public JButton getBtnBorrarLocalidad() { return btnBorrarLocalidad; }
	
	public JComboBox<String> getCbxPaisesLocalidad() { return cbxPaisesLocalidad; }
	
	public JComboBox<String> getCbxProvinciasLocalidad() { return cbxProvinciasLocalidad; }
	
	public JTextField getTxtNombreLocalidad() { return txtNombreLocalidad; }
	
	public JComboBox<String> getCbxLocalidadesEditar() { return cbxLocalidadesEditar; }
	
	public JTextField getTxtNombreNuevoLocalidad() { return txtNombreNuevoLocalidad; }
	
	public List<JCheckBox> getChksLocalidadesBorrar() { return chksLocalidadesBorrar; } 

}
