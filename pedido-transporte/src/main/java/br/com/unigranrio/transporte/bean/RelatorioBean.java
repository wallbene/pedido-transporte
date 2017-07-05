package br.com.unigranrio.transporte.bean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.unigranrio.transporte.dao.PedidoDao;
import br.com.unigranrio.transporte.modelo.StatusEnum;
import br.com.unigranrio.transporte.util.GeradorRelatorio;

@Controller
public class RelatorioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	

	@Inject
	PedidoDao pedidoDao;
	
	@PersistenceContext
	private EntityManager manager;

	private BarChartModel pedidoModel;
	
	public BarChartModel getPedidoModel() {
		return pedidoModel;
	}
	
	@PostConstruct
	public void init(){
		criaChartPedido();
	}

	private void criaChartPedido() {
		pedidoModel = initCharPedido();
		
		
		
		pedidoModel.setTitle("Gráfico dos pedidos");
		pedidoModel.setLegendPosition("ne");
         
		Axis xAxis = pedidoModel.getAxis(AxisType.X);
        xAxis.setLabel("Ano");
         
        Axis yAxis = pedidoModel.getAxis(AxisType.Y);
        yAxis.setLabel("Pedido");
        yAxis.setMin(0);
        yAxis.setMax(10);
    
	}

/*Método que pega os pedidos e gera o gráfico*/ 
public BarChartModel initCharPedido() {

		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		

		//Pedidos Atendidos
		ChartSeries pedidosAtendidosSerie = setaPedidosPorAnoStatus(pedidoDao.anoDosPedidoPorStatus(StatusEnum.ATENDIDO),
																		StatusEnum.ATENDIDO);
		pedidosAtendidosSerie.setLabel("pedidos Atendidos");
		model.addSeries(pedidosAtendidosSerie);
		
		
		//Pedidos Negados
		
		ChartSeries pedidosIndeferidosSerie = setaPedidosPorAnoStatus(pedidoDao.anoDosPedidoPorStatus(StatusEnum.NEGADO),
																		StatusEnum.NEGADO);
		
		pedidosIndeferidosSerie.setLabel("Pedidos Negados");
		model.addSeries(pedidosIndeferidosSerie);
		
		
		
		//Pedidos Cancelados
		
		ChartSeries pedidosCanceladosSerie = setaPedidosPorAnoStatus(pedidoDao.anoDosPedidoPorStatus(StatusEnum.CANCELADO),
																	StatusEnum.CANCELADO);
		pedidosCanceladosSerie.setLabel("Pedidos Cancelados");
		model.addSeries(pedidosCanceladosSerie);
		

		return model;
	}

private ChartSeries setaPedidosPorAnoStatus(List<Integer> years, StatusEnum status) {
	ChartSeries pedidosSerie = new ChartSeries();
	Collections.reverse(years);
	
	for (Integer year : years) {
		pedidosSerie.set(year.toString(), pedidoDao.listaPorDataAno(year.toString(),status).size());
		
	}
	return pedidosSerie;
}

@Transactional
 public void gerarRelatorio() throws FileNotFoundException, SQLException{
	
	Session session = (Session) manager.getDelegate();
	Connection connection = session.doReturningWork(new ReturningWork<Connection>() {
		@Override
		public Connection execute(Connection connection) throws SQLException {
	
			return connection;
		}
	});
	 
	 new GeradorRelatorio("report/pedido_transporte.jasper", new HashMap<String, Object>()).
	 							geraPdfParaOutputStream(new FileOutputStream("pedido_transporte.pdf"),connection);
 }


}