package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.ProvinciaDTO;
import modelo.Agenda;
import vista.VentanaUbicaciones;

public class ControladorUbicaciones implements ActionListener
{
	private static ControladorUbicaciones INSTANCE;
	
	private VentanaUbicaciones ventanaUbicaciones;
	private Agenda agenda;
	
	private List<PaisDTO> paises;
	
	private List<ProvinciaDTO> provincias; //Provincias del panel provincia
	
	private List<ProvinciaDTO> provinciasLocalidad; //Provincias del panel localidad
	private List<LocalidadDTO> localidades; 
	
	public static ControladorUbicaciones getInstance(VentanaUbicaciones vista, Agenda modelo)
	{
		if(INSTANCE == null)
			INSTANCE = new ControladorUbicaciones(vista, modelo);
		
		return INSTANCE;
	}
	
	private ControladorUbicaciones(VentanaUbicaciones vista, Agenda modelo) 
	{
		ventanaUbicaciones = vista;
		ventanaUbicaciones.getBtnPaises().addActionListener(ae -> mostrarSeccionPais());
		ventanaUbicaciones.getBtnProvincias().addActionListener(ae -> mostrarSeccionProvincia());
		ventanaUbicaciones.getBtnLocalidades().addActionListener(ae -> mostrarSeccionLocalidad());
		
		ventanaUbicaciones.getBtnAgregarPais().addActionListener(ae -> agregarPais());
		ventanaUbicaciones.getBtnEditarPais().addActionListener(ae -> editarPais());
		ventanaUbicaciones.getBtnBorrarPais().addActionListener(ae -> borrarPais());
		
		ventanaUbicaciones.getBtnAgregarProvincia().addActionListener(ae -> agregarProvincia());
		ventanaUbicaciones.getBtnEditarProvincia().addActionListener(ae -> editarProvincia());
		ventanaUbicaciones.getBtnBorrarProvincia().addActionListener(ae -> borrarProvincia());
		
		ventanaUbicaciones.getBtnAgregarLocalidad().addActionListener(ae -> agregarLocalidad());
		ventanaUbicaciones.getBtnEditarLocalidad().addActionListener(ae -> editarLocalidad());
		ventanaUbicaciones.getBtnBorrarLocalidad().addActionListener(ae -> borrarLocalidad());
		
		ventanaUbicaciones.getCbxPaisesProvincia().addActionListener(ae -> actualizarPanelProvincia());
		
		ventanaUbicaciones.getCbxPaisesLocalidad().addActionListener(ae -> actualizarPanelLocalidad(ae));
		ventanaUbicaciones.getCbxProvinciasLocalidad().addActionListener(ae -> actualizarPanelLocalidad(ae));
		
		agenda = modelo;
		paises = agenda.obtenerPaises();
		
		ventanaUbicaciones.cargarPaises(paises);
	}

	public void iniciar() 
	{
		ventanaUbicaciones.mostrar();
	}
	
	
	private void mostrarSeccionPais() 
	{
		ventanaUbicaciones.mostrarPanelPais();
	}
	
	private void mostrarSeccionProvincia() 
	{
		ventanaUbicaciones.mostrarPanelProvincia();
	}
	
	private void mostrarSeccionLocalidad() 
	{
		ventanaUbicaciones.mostrarPanelLocalidad();
	}

	private void agregarPais() 
	{
		String nombrePais = ventanaUbicaciones.getTxtNombrePais().getText().trim();
		
		if (!nombrePais.equals("")) 
		{
			nombrePais = nombrePais.substring(0, 1).toUpperCase() + nombrePais.substring(1); //Convertimos la primera letra en mayuscula
			
			boolean existe = false;
			for (PaisDTO pais : paises) 
				if (pais.getNombre().equals(nombrePais))
					existe = true;
			
			if (existe)
				JOptionPane.showMessageDialog(null, "Ya existe un pais con el nombre '" + nombrePais + "'", "Aviso", JOptionPane.WARNING_MESSAGE); 
			else 
			{
				agenda.agregarPais(new PaisDTO(0, nombrePais));
				refrescar();
				JOptionPane.showMessageDialog(null, "Nuevo pais guardado.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
			}
		}
		else
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE); 
	}

	private void editarPais() 
	{
		int seleccionado = ventanaUbicaciones.getCbxPaisesEditar().getSelectedIndex();
		String nombreNuevo = ventanaUbicaciones.getTxtNombreNuevoPais().getText().trim();
		
		if (!nombreNuevo.equals("")) 
		{
			nombreNuevo = nombreNuevo.substring(0, 1).toUpperCase() + nombreNuevo.substring(1); //Convertimos la primera letra en mayuscula
			
			boolean existe = false;
			for (PaisDTO pais : paises) 
				if (pais.getNombre().equals(nombreNuevo))
					existe = true;
			
			if (existe)
				JOptionPane.showMessageDialog(null, "Ya existe un pais con el nombre '" + nombreNuevo + "'", "Aviso", JOptionPane.WARNING_MESSAGE); 
			else 
			{
				PaisDTO pais = paises.get(seleccionado);
				String nombreViejo = pais.getNombre();
				
				int respuesta = JOptionPane.showConfirmDialog(null, "Los contactos que tengan este pais asignado a su domicilio seran afectados.\n¿Estas seguro de cambiar el tipo '" + nombreViejo + "' por '" + nombreNuevo + "' ?", "Confirmacion para editar", JOptionPane.YES_NO_OPTION);
				
				if (respuesta == 0) 
				{
					pais.setNombre(nombreNuevo);
					agenda.editarPais(pais);
					refrescar();
					JOptionPane.showMessageDialog(null, "Pais actualizado con exito.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
				}
			}
		}
		else
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE);
	}
	
	private void borrarPais() 
	{
		List<JCheckBox> chksPaises = ventanaUbicaciones.getChksPaisesBorrar();
		List<Integer> indicesSelec = new ArrayList<Integer>();
		
		//Recorremos para obtener los indices de los seleccionados
		for (int i = 0; i < chksPaises.size(); i++) 
			if (chksPaises.get(i).isSelected()) 
				indicesSelec.add(i);
		
		if (indicesSelec.size() > 0) //Verificamos si selecciono alguno 
		{
			int respuesta = JOptionPane.showConfirmDialog(null, "Al confirmar esta accion tambien se eliminaran:\n"
															+ "1) Las provincias y localidades correspondientes a los paises seleccionados.\n"
															+ "2) La informacion del domicilio de los contactos que tengan asignado alguno de los paises."
															, "Confirmacion para eliminar", JOptionPane.YES_NO_OPTION);

			if (respuesta == 0) 
			{
				//Recorremos y eliminamos los tipos que corresponden a los indices seleccionados
				for (Integer indice : indicesSelec) 
					if(chksPaises.get(indice).isSelected())
						agenda.borrarPais(paises.get(indice));
				
				refrescar();
				JOptionPane.showMessageDialog(null, "Los paises seleccionados fueron eliminados correctamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
			}
		}
		else
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguno.", "Aviso", JOptionPane.WARNING_MESSAGE);
	}
	
	
	private void agregarProvincia() 
	{
		int i = ventanaUbicaciones.getCbxPaisesProvincia().getSelectedIndex();
		PaisDTO pais = paises.get(i);

		String nombreProvincia = ventanaUbicaciones.getTxtNombreProvincia().getText().trim();
		
		if (!nombreProvincia.equals("")) 
		{
			nombreProvincia = nombreProvincia.substring(0, 1).toUpperCase() + nombreProvincia.substring(1).toLowerCase(); //Convertimos la primera letra en mayuscula
			
			boolean existe = false;
			for (ProvinciaDTO provincia : provincias) 
				if (provincia.getNombre().equals(nombreProvincia))
					existe = true;
			
			if (existe)
				JOptionPane.showMessageDialog(null, "Ya existe una provincia con el nombre '" + nombreProvincia + "' en el pais '" + pais.getNombre() + "'.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			else 
			{
				int idPais = pais.getId();
				System.out.println(idPais);
				agenda.agregarProvincia(new ProvinciaDTO(0, nombreProvincia, idPais));
				refrescar();
				JOptionPane.showMessageDialog(null, "Nueva provincia agregada al pais '" + pais.getNombre() + "'.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
			}
		}
		else
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE); 
	}
	
	private void editarProvincia() 
	{
		if(provincias.size() == 0) 
		{
			JOptionPane.showMessageDialog(null, "Este pais no tiene provincias generadas para editar.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
		
		int seleccionado = ventanaUbicaciones.getCbxProvinciasEditar().getSelectedIndex();
		String nombreNuevo = ventanaUbicaciones.getTxtNombreNuevoProvincia().getText().trim();
		
		if (!nombreNuevo.equals("")) 
		{
			nombreNuevo = nombreNuevo.substring(0, 1).toUpperCase() + nombreNuevo.substring(1).toLowerCase(); //Convertimos la primera letra en mayuscula
			
			boolean existe = false;
			for (ProvinciaDTO provincia : provincias) 
				if (provincia.getNombre().equals(nombreNuevo))
					existe = true;
			
			if (existe)
				JOptionPane.showMessageDialog(null, "Ya existe una provincia con el nombre '" + nombreNuevo + "'.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			else 
			{
				ProvinciaDTO provincia = provincias.get(seleccionado);
				String nombreViejo = provincia.getNombre();
				
				int respuesta = JOptionPane.showConfirmDialog(null, "Los contactos que tengan esta provincia asignada a su domicilio seran afectados.\n¿Estas seguro de cambiar el nombre de '" + nombreViejo + "' a '" + nombreNuevo + "' ?", "Confirmacion para editar", JOptionPane.YES_NO_OPTION);
				
				if (respuesta == 0) 
				{
					provincia.setNombre(nombreNuevo);
					agenda.editarProvincia(provincia);
					refrescar();
					JOptionPane.showMessageDialog(null, "Provincia actualizada con exito.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
				}
			}
		}
		else
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE);
	}
	
	private void borrarProvincia() 
	{
		if(provincias.size() == 0) 
		{
			JOptionPane.showMessageDialog(null, "Este pais no tiene provincias generadas para borrar.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
		
		List<JCheckBox> chksProvincias = ventanaUbicaciones.getChksProvinciasBorrar();
		List<Integer> indicesSelec = new ArrayList<Integer>();
		
		//Recorremos para obtener los indices de los seleccionados
		for (int i = 0; i < chksProvincias.size(); i++) 
			if (chksProvincias.get(i).isSelected()) 
				indicesSelec.add(i);
		
		if (indicesSelec.size() > 0) //Verificamos si selecciono alguno 
		{
			int respuesta = JOptionPane.showConfirmDialog(null, "Al confirmar esta accion tambien se eliminaran:\n"
															+ "1) Las localidades correspondientes a las provincias seleccionadas.\n"
															+ "2) La informacion del domicilio de los contactos que tengan asignado alguno de las pprovincias."
															, "Confirmacion para eliminar", JOptionPane.YES_NO_OPTION);
			
			if (respuesta == 0) 
			{
				//Recorremos y eliminamos los tipos que corresponden a los indices seleccionados
				for (Integer indice : indicesSelec) 
					agenda.borrarProvincia(provincias.get(indice));
				
				refrescar();
				JOptionPane.showMessageDialog(null, "Las provincias seleccionadas fueron eliminados correctamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
			}
		}
		else
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna provincia.", "Aviso", JOptionPane.WARNING_MESSAGE);
	}
	
	
	private void agregarLocalidad() 
	{
		if(provinciasLocalidad.size() == 0) 
		{
			JOptionPane.showMessageDialog(null, "Este pais no tiene provincias generadas para poder crear una localidad nueva.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
		
		int i = ventanaUbicaciones.getCbxProvinciasLocalidad().getSelectedIndex();
		ProvinciaDTO provincia = provinciasLocalidad.get(i);

		String nombreLocalidad = ventanaUbicaciones.getTxtNombreLocalidad().getText().trim();
		
		if (!nombreLocalidad.equals("")) 
		{
			nombreLocalidad = nombreLocalidad.substring(0, 1).toUpperCase() + nombreLocalidad.substring(1).toLowerCase(); //Convertimos la primera letra en mayuscula
			
			boolean existe = false;
			for (LocalidadDTO localidad : localidades) 
				if (localidad.getNombre().equals(nombreLocalidad))
					existe = true;
			
			if (existe)
				JOptionPane.showMessageDialog(null, "Ya existe una localidad con el nombre '" + nombreLocalidad + "' en la provincia '" + provincia.getNombre() + "'.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			else 
			{
				int idProvincia = provincia.getId();
				agenda.agregarLocalidad(new LocalidadDTO(0, nombreLocalidad, idProvincia));
				refrescar();
				JOptionPane.showMessageDialog(null, "Nueva localidad agregada a la provincia '" + provincia.getNombre() + "'.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
			}
		}
		else
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE); 
		
	}

	private void editarLocalidad() 
	{
		if(provinciasLocalidad.size() == 0) 
		{
			JOptionPane.showMessageDialog(null, "Este pais no tiene provincias generadas para poder editar una localidad.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
		
		int seleccionado = ventanaUbicaciones.getCbxLocalidadesEditar().getSelectedIndex();
		String nombreNuevo = ventanaUbicaciones.getTxtNombreNuevoLocalidad().getText().trim();
		
		if (!nombreNuevo.equals("")) 
		{
			nombreNuevo = nombreNuevo.substring(0, 1).toUpperCase() + nombreNuevo.substring(1).toLowerCase(); //Convertimos la primera letra en mayuscula
			
			boolean existe = false;
			for (LocalidadDTO localidad : localidades) 
				if (localidad.getNombre().equals(nombreNuevo))
					existe = true;
			
			if (existe)
				JOptionPane.showMessageDialog(null, "Ya existe una localidad con el nombre '" + nombreNuevo + "'.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			else 
			{
				LocalidadDTO localidad = localidades.get(seleccionado);
				String nombreViejo = localidad.getNombre();
				
				int respuesta = JOptionPane.showConfirmDialog(null, "Los contactos que tengan esta localidad asignada a su domicilio seran afectados.\n¿Estas seguro de cambiar el nombre de '" + nombreViejo + "' a '" + nombreNuevo + "' ?", "Confirmacion para editar", JOptionPane.YES_NO_OPTION);
				
				if (respuesta == 0) 
				{
					localidad.setNombre(nombreNuevo);
					agenda.editarLocalidad(localidad);
					refrescar();
					JOptionPane.showMessageDialog(null, "Localidad actualizada con exito.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
				}
			}
		}
		else
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE);
	}

	private void borrarLocalidad() 
	{
		if(provinciasLocalidad.size() == 0) 
		{
			JOptionPane.showMessageDialog(null, "Este pais no tiene provincias generadas para borrar una localidad.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
		
		List<JCheckBox> chksLocalidades = ventanaUbicaciones.getChksLocalidadesBorrar();
		List<Integer> indicesSelec = new ArrayList<Integer>();
		
		//Recorremos para obtener los indices de los seleccionados
		for (int i = 0; i < chksLocalidades.size(); i++) 
			if (chksLocalidades.get(i).isSelected()) 
				indicesSelec.add(i);
		
		if (indicesSelec.size() > 0) //Verificamos si selecciono alguno 
		{
			int respuesta = JOptionPane.showConfirmDialog(null, "Al confirmar esta accion tambien se eliminaran:\n"
															+ "1) Las localidades correspondientes a las provincias seleccionadas.\n"
															+ "2) La informacion del domicilio de los contactos que tengan asignado alguno de las localidades."
															, "Confirmacion para eliminar", JOptionPane.YES_NO_OPTION);
			
			if (respuesta == 0) 
			{
				System.out.println(localidades);
				//Recorremos y eliminamos los tipos que corresponden a los indices seleccionados
				for (Integer indice : indicesSelec) 
					agenda.borrarLocalidad(localidades.get(indice));
				
				refrescar();
				JOptionPane.showMessageDialog(null, "Las localidades seleccionadas fueron eliminados correctamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
			}
		}
		else
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna localidad.", "Aviso", JOptionPane.WARNING_MESSAGE);
	}
	
	
	private void actualizarPanelProvincia() 
	{
		int i = ventanaUbicaciones.getCbxPaisesProvincia().getSelectedIndex();
		if(i != -1) 
		{
			PaisDTO pais = paises.get(i);
			provincias = agenda.obtenerProvincias(pais);
			ventanaUbicaciones.cargarPanelProvincia(provincias);
		}
	}
	
	private void actualizarPanelLocalidad(ActionEvent ae) 
	{
		if (ae.getSource() == ventanaUbicaciones.getCbxPaisesLocalidad())
		{
			int i = ventanaUbicaciones.getCbxPaisesLocalidad().getSelectedIndex();
			if(i != -1) 
			{
				PaisDTO pais = paises.get(i);
				provinciasLocalidad = agenda.obtenerProvincias(pais);
				ventanaUbicaciones.cargarProvinciasPanelLocalidad(provinciasLocalidad);
			}
			System.out.println(provinciasLocalidad.size());
		}
		
		else if (ae.getSource() == ventanaUbicaciones.getCbxProvinciasLocalidad())
		{
			int i = ventanaUbicaciones.getCbxProvinciasLocalidad().getSelectedIndex();
			if(i != -1)
			{
				ProvinciaDTO provincia = provinciasLocalidad.get(i);
				localidades = agenda.obtenerLocalidades(provincia);
				ventanaUbicaciones.cargarLocalidadesPanelLocalidad(localidades);
			}
			else
			{
				localidades.clear();
				ventanaUbicaciones.cargarLocalidadesPanelLocalidad(localidades);
			}
		}
	}
	
	private void refrescar()
	{
		paises = agenda.obtenerPaises();
		ventanaUbicaciones.cargarPaises(paises);
		ventanaUbicaciones.limpiarCampos();
		
		ControladorAgenda ctrlAgenda = ControladorAgenda.getInstance(null, null);
		ctrlAgenda.refrescarLista();
		ctrlAgenda.actualizarPaisesVentanaPersona();
	}
	
	
	public void actionPerformed(ActionEvent ae) { }
}
