/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;

import dao.GestionProduitDao;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.transform.Scale;
import org.kairos.core.Activity;

/**
 *
 * @author Wael
 */
public class AreaChartProd extends Activity {

    @FXML
    void SetAreaChart() {
//        print(area);
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        String[] months = {"Janvier", "Fevrier", "Mars", "Avril", "May", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "decembre"};

        xAxis.setLabel("Année");
        yAxis.setLabel("Nombre");

        GestionProduitDao pdao = new GestionProduitDao();

        area.setTitle("Produit inseré par Mois");
        XYChart.Series series = new XYChart.Series();
        series.setName("Nombre de produits par mois");
        for (int i = 0; i < 12; i++) {
            try {
                series.getData().add(new XYChart.Data(months[i], pdao.GetStatProd(i + 1)));
            } catch (SQLException ex) {
                Logger.getLogger(PieChartProd.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        area.getData().add(series);
    }

    @FXML
    AreaChart<Number, Number> area;

    @Override
    public void onCreate() {
        super.onCreate();
        setContentView(getClass().getResource("/fxml/AreaChartProd.fxml"));
        SetAreaChart();
    }

}
