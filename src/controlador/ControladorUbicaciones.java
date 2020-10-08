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

	
	//ABM PAIS
	private void agregarPais() 
	{
		String nombrePais = ventanaUbicaciones.getTxtNombrePais().getText().trim();
		
		if (nombrePais.equals(""))
		{
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		boolean existe = false;
		for (PaisDTO pais : paises) 
			if (pais.getNombre().equalsIgnoreCase(nombrePais))
				existe = true;
		
		if (existe)
		{
			JOptionPane.showMessageDialog(null, "Ya existe un pais con el nombre '" + nombrePais + "'", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
			
		agenda.agregarPais(new PaisDTO(0, nombrePais));
		refrescar();
		JOptionPane.showMessageDialog(null, "Nuevo pais guardado.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
	}

	private void editarPais() 
	{
		int seleccionado = ventanaUbicaciones.getCbxPaisesEditar().getSelectedIndex();
		PaisDTO pais = paises.get(seleccionado);
		
		String nombreNuevo = ventanaUbicaciones.getTxtNombreNuevoPais().getText().trim();
		String nombreViejo = pais.getNombre();
		
		if (nombreNuevo.equals(""))
		{
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		boolean existe = false;
		for (PaisDTO p : paises) 
			if (p.getNombre().equalsIgnoreCase(nombreNuevo) && p.getId() != pais.getId())
				existe = true;
		
		if (existe)
		{
			JOptionPane.showMessageDialog(null, "Ya existe un pais con el nombre '" + nombreNuevo + "'", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
			
		int respuesta = JOptionPane.showConfirmDialog(null, "Los contactos que tengan este pais asignado a su domicilio seran afectados.\n¿Estas seguro de cambiar el nombre del pais '" + nombreViejo + "' a '" + nombreNuevo + "' ?", "Confirmacion para editar", JOptionPane.YES_NO_OPTION);
		
		if (respuesta == 0) 
		{
			pais.setNombre(nombreNuevo);
			agenda.editarPais(pais);
			refrescar();
			JOptionPane.showMessageDialog(null, "Pais actualizado con exito.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
		}
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
	
	
	//ABM PROVINCIA
	private void agregarProvincia() 
	{
		int i = ventanaUbicaciones.getCbxPaisesProvincia().getSelectedIndex();
		PaisDTO pais = paises.get(i);

		String nombreProvincia = ventanaUbicaciones.getTxtNombreProvincia().getText().trim();
		
		if (nombreProvincia.equals("")) 
		{
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}	
			
		boolean existe = false;
		for (ProvinciaDTO provincia : provincias) 
			if (provincia.getNombre().equalsIgnoreCase(nombreProvincia))
				existe = true;
		
		if (existe)
		{
			JOptionPane.showMessageDialog(null, "Ya existe una provincia con el nombre '" + nombreProvincia + "' en el pais '" + pais.getNombre() + "'.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return ;
		}
		
		int idPais = pais.getId();
		agenda.agregarProvincia(new ProvinciaDTO(0, nombreProvincia, idPais));
		refrescar();
		JOptionPane.showMessageDialog(null, "Nueva provincia agregada al pais '" + pais.getNombre() + "'.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
	}
	
	private void editarProvincia() 
	{
		if(provincias.size() == 0) 
		{
			JOptionPane.showMessageDialog(null, "Este pais no tiene provincias generadas para editar.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
		
		int indexPais = ventanaUbicaciones.getCbxPaisesProvincia().getSelectedIndex();
		PaisDTO pais = paises.get(indexPais);
		
		int indexProv = ventanaUbicaciones.getCbxProvinciasEditar().getSelectedIndex();
		ProvinciaDTO provincia = provincias.get(indexProv);
		
		String nombreViejo = provincia.getNombre();
		String nombreNuevo = ventanaUbicaciones.getTxtNombreNuevoProvincia().getText().trim();
		
		if (nombreNuevo.equals("")) 
		{
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}	
			
		boolean existe = false;
		for (ProvinciaDTO p : provincias) 
			if (p.getNombre().equalsIgnoreCase(nombreNuevo) && p.getId() != provincia.getId())
				existe = true;
		
		if (existe)
		{
			JOptionPane.showMessageDialog(null, "Ya existe una provincia con el nombre '" + nombreNuevo + "' en el pais '" + pais.getNombre() + "'.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return ;
		}
		
		int respuesta = JOptionPane.showConfirmDialog(null, "Los contactos que tengan esta provincia asignada a su domicilio seran afectados.\n¿Estas seguro de cambiar el nombre de '" + nombreViejo + "' a '" + nombreNuevo + "' ?", "Confirmacion para editar", JOptionPane.YES_NO_OPTION);
		if (respuesta == 0) 
		{
			provincia.setNombre(nombreNuevo);
			agenda.editarProvincia(provincia);
			refrescar();
			JOptionPane.showMessageDialog(null, "Provincia actualizada con exito.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
		}
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
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna provincia.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
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
	
	
	//ABM LOCALIDAD
	private void agregarLocalidad() 
	{
		if(provinciasLocalidad.size() == 0) 
		{
			JOptionPane.showMessageDialog(null, "Este pais no tiene provincias generadas para poder crear una localidad nueva.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
		
		int indexProv = ventanaUbicaciones.getCbxProvinciasLocalidad().getSelectedIndex();
		ProvinciaDTO provincia = provinciasLocalidad.get(indexProv);

		String nombreLocalidad = ventanaUbicaciones.getTxtNombreLocalidad().getText().trim();
		
		if (nombreLocalidad.equals("")) 
		{
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
		
		boolean existe = false;
		for (LocalidadDTO localidad : localidades) 
			if (localidad.getNombre().equalsIgnoreCase(nombreLocalidad))
				existe = true;
		
		if (existe)
		{
			JOptionPane.showMessageDialog(null, "Ya existe una localidad con el nombre '" + nombreLocalidad + "' en la provincia '" + provincia.getNombre() + "'.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
		
		int idProvincia = provincia.getId();
		agenda.agregarLocalidad(new LocalidadDTO(0, nombreLocalidad, idProvincia));
		refrescar();
		JOptionPane.showMessageDialog(null, "Nueva localidad agregada a la provincia '" + provincia.getNombre() + "'.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
	}

	private void editarLocalidad() 
	{
		if(provinciasLocalidad.size() == 0) 
		{
			JOptionPane.showMessageDialog(null, "Este pais no tiene provincias generadas para poder editar una localidad.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
		
		int indexProv = ventanaUbicaciones.getCbxProvinciasLocalidad().getSelectedIndex();
		ProvinciaDTO provincia = provinciasLocalidad.get(indexProv);
		
		int indexLoc = ventanaUbicaciones.getCbxLocalidadesEditar().getSelectedIndex();
		LocalidadDTO localidad = localidades.get(indexLoc);
		
		String nombreViejo = localidad.getNombre();
		String nombreNuevo = ventanaUbicaciones.getTxtNombreNuevoLocalidad().getText().trim();
		
		if (nombreNuevo.equals("")) 
		{
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return;
		}
		
		boolean existe = false;
		for (LocalidadDTO l : localidades) 
			if (l.getNombre().equalsIgnoreCase(nombreNuevo) && l.getId() != localidad.getId())
				existe = true;
		
		if (existe)
		{
			JOptionPane.showMessageDialog(null, "Ya existe una localidad con el nombre '" + nombreNuevo + "' en la provincia '" + provincia.getNombre() + "'.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return ;
		}
		
		int respuesta = JOptionPane.showConfirmDialog(null, "Los contactos que tengan esta localidad asignada a su domicilio seran afectados.\n¿Estas seguro de cambiar el nombre de '" + nombreViejo + "' a '" + nombreNuevo + "' ?", "Confirmacion para editar", JOptionPane.YES_NO_OPTION);
		if (respuesta == 0) 
		{
			localidad.setNombre(nombreNuevo);
			agenda.editarLocalidad(localidad);
			refrescar();
			JOptionPane.showMessageDialog(null, "Localidad actualizada con exito.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
		}
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
		
		//Actualizamos la vista de la ventana de ubicaciones
		ventanaUbicaciones.cargarPaises(paises);
		ventanaUbicaciones.limpiarCampos();
		
		//LLamamos al otro controlador para que actualice los datos de las otras ventanas
		ControladorAgenda ctrlAgenda = ControladorAgenda.getInstance(null, null);
		ctrlAgenda.refrescarLista();
		ctrlAgenda.actualizarPaises();
	}
	
	
	public void actionPerformed(ActionEvent ae) { }
}
