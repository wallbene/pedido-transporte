package br.com.unigranrio.transporte.util;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

import com.lowagie.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

public class GeradorRelatorio {
	
	private String nomeArquivo;
	private Map<String, Object> parametros;
	
	public GeradorRelatorio(String nomeArquivo, Map<String, Object> parametros) {
		this.nomeArquivo = nomeArquivo;
		this.parametros = parametros;
	}
	
	public void geraPdfParaOutputStream(OutputStream outputStream, Connection connection) throws SQLException{
		
		/*uma forma de pegar a connexão do EntityManager*/
		/*Connection connection = manager.unwrap(SessionImpl.class).connection();*/
		
		
		if(connection.isClosed()){
			System.out.println("conexão fechada");
		}
		else
			System.out.println("conexão aberta");
		
		try {
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(nomeArquivo, this.parametros, connection);
            
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			configuration.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			
			System.out.println("export");
			
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}

}