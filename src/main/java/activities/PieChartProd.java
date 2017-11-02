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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import org.kairos.core.Activity;

/**
 *
 * @author Wael
 */
public class PieChartProd extends Activity  {
    
 @FXML
 public void setChart()
 {
            
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                
                String[] months = {"Janvier","Fevrier","Mars","Avril","May","Juin","Juillet","Aout","Septembre","Octobre","Novembre","decembre"};
                GestionProduitDao pdao = new GestionProduitDao();

                    for(int i = 0;i<12;i++)
                    {
                    try {
                        //                            pieChartData.addAll(new PieChart.Data("2015",1),
//                                    new PieChart.Data("2014", 2),
//                                    new PieChart.Data("2012", 1),
//                                    new PieChart.Data("2013", 0)
//                                    );
                        pieChartData.add(new PieChart.Data(months[i], pdao.GetStatProd(i+1)));
                    } catch (SQLException ex) {
                        Logger.getLogger(PieChartProd.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    }
                 
                piee.setTitle("Produit inseré par Mois");
                

        piee.setData(pieChartData);
 } 
    
     @FXML
    PieChart piee;
     
    @Override
    public void onCreate() {
        super.onCreate();
        setContentView(getClass().getResource("/fxml/PieChartProd.fxml"));
        setChart();
    }


}
