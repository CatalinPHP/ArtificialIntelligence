package Helpers;

import Algoritmi.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Diagram extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
    }

    private void init(Stage primaryStage) {
        HBox root = new HBox();
        Scene scene = new Scene(root, 650, 500);

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Generation");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Best");

        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Diagram of best evolution");

        XYChart.Series<Number, Number> data = new XYChart.Series<>();
        AEFunctie fct = new AEFunctie();
        double[] bestofGenArray = fct.AlgoritmMinimizareFunctie(30, 20);

        for (int i = 0; i < bestofGenArray.length ; i++) {
            data.getData().add(new XYChart.Data<Number, Number>(i,bestofGenArray[i]));
        }

        lineChart.getData().add(data);
        root.getChildren().add(lineChart);

        primaryStage.setTitle("Line Chart on ...");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void main(String args){
        launch(args);
    }
}
