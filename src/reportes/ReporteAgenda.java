package reportes;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import dto.PersonaDTO;

public class ReporteAgenda
{
	private JasperViewer reporteViewer;
	private JasperPrint	reporteLleno;
	private Logger log = Logger.getLogger(ReporteAgenda.class);
	
	//Recibe la lista de personas para armar el reporte
    public ReporteAgenda(List<PersonaDTO> personas)
    {
    	//Hardcodeado
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		parametersMap.put("NumPersonas", personas.size()); //Se usara en el reporte para sacar el porcentaje de los grupos
		
    	try	
    	{
    		String rutaArchivo = "archivos/ReporteAgenda.jasper";
    		ClassLoader cl = getClass().getClassLoader();
        	InputStream is = cl.getResourceAsStream(rutaArchivo);
			this.reporteLleno = JasperFillManager.fillReport(is, parametersMap, new JRBeanCollectionDataSource(personas));
    		log.info("Se cargo correctamente el reporte");
		}
		catch( JRException ex ) 
		{
			log.error("Ocurrio un error mientras se cargaba el archivo ReporteAgenda.jasper", ex);
		}
    }       
    
    public void mostrar()
	{
		this.reporteViewer = new JasperViewer(this.reporteLleno,false);
		this.reporteViewer.setVisible(true);
	}
   
}	
