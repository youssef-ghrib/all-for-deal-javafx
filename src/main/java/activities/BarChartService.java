/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;

import dao.GestionProduitDao;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import org.kairos.core.Activity;

/**
 *
 * @author Wael
 */
public class BarChartService extends Activity {
    
    public StackedBarChart bar;
    
        @FXML
    public void setBarChart()
    {
     
        String[] months = {"Janvier","Fevrier","Mars","Avril","May","Juin","Juillet","Aout","Septembre","Octobre","Novembre","decembre"};
    final CategoryAxis xAxis = new CategoryAxis();  
    final NumberAxis yAxis = new NumberAxis();  
              XYChart.Series seriesS = new XYChart.Series();
              
              GestionProduitDao pdao = new GestionProduitDao();
              for(int i = 0;i<12;i++)
                    {
                    try {
                 seriesS.getData().add(new XYChart.Data(months[i], pdao.GetStatProd(i+1)));  
                         } catch (SQLException ex) {
                        Logger.getLogger(PieChartProd.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
        bar.getData().addAll(seriesS);
        bar.setTitle("Produit inséré par Mois");
        }
    
        @Override
    public void onCreate() {
        super.onCreate();
        setContentView(getClass().getResource("/fxml/BarChartService.fxml"));
        setBarChart();
    }
    


        
    }
    

