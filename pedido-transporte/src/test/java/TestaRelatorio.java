import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import br.com.unigranrio.transporte.util.GeradorRelatorio;
import net.sf.jasperreports.engine.JRException;

public class TestaRelatorio {
	
	public static void main(String[] args) throws SQLException, JRException, FileNotFoundException {
		
		System.out.println("abrindo conexão");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/transportedb", "root", "marciane");
		
/*		JasperCompileManager.compileReportToFile("pedido_transporte_subreport3.jrxml");
*/		
		new GeradorRelatorio("report/pedido_transporte.jasper", new HashMap<String, Object>()).geraPdfParaOutputStream(new FileOutputStream("pedido_transporte.pdf"), connection);
			
		connection.close();
		System.out.println("Rodou");
		
		
	}

}
