package org.wt.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.wt.demo.backend.MyMatrix;
import org.wt.demo.backend.Simulation;

import java.util.Arrays;
import java.util.List;

public class HelloApplication extends Application
{
    private TextField verticesField;
    private TextField edgesField;

    @Override
    public void start(Stage primaryStage)
    {
        Pane pane = new Pane();
        VBox vbox = new VBox();
        vbox.setSpacing(10); // Set spacing between elements in the VBox
        vbox.setPadding(new Insets(10)); // Set padding around the VBox

        HBox hbox = new HBox();
        hbox.setSpacing(10); // Set spacing between elements in the HBox

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);

        TableView<List<String>> matrixTable = new TableView<>();
        matrixTable.setPrefSize(400, 400);
        BorderPane borderPane = new BorderPane();
        borderPane.setRight(matrixTable);

        Simulation simulation = new Simulation(pane);

        Button generateGraphButton = new Button("Generate Graph");
        generateGraphButton.setOnAction(e -> {
            simulation.run();
            updateButtons(simulation);
            updateMatrix(simulation, matrixTable);
        });

        Button selectRandomEdgeButton = new Button("Select Random Edge");
        selectRandomEdgeButton.setOnAction(e -> {
            simulation.getG().getRandomEdge();
            updateButtons(simulation);
        });

        verticesField = new TextField();
        verticesField.setEditable(false); // make the field read-only

        edgesField = new TextField();
        edgesField.setEditable(false); // make the field read-only

        hbox.getChildren().addAll(generateGraphButton, selectRandomEdgeButton, verticesField, edgesField);
        vbox.getChildren().addAll(hbox, borderPane, pane);

        primaryStage.show();
    }

    private void updateButtons(Simulation simulation) {
        verticesField.setText("Vertices: " + simulation.getG().getG().vertexSet().size());
        edgesField.setText("Edges: " + simulation.getG().getG().edgeSet().size());
    }

    private void updateMatrix(Simulation simulation, TableView<List<String>> matrixTable)
    {
        MyMatrix matrix = simulation.getG().getMatrix();

        System.out.println("Matrix: " + Arrays.deepToString(matrix.getMatrix()));

        matrix.displayMatrix(matrixTable);
    }

    public static void main(String[] args)
    {
        launch();
    }
}