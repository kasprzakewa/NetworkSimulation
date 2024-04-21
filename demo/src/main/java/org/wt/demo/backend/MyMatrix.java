package org.wt.demo.backend;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyMatrix
{
    private final MyGraph graph;
    private int[][] matrix;
    private final int vertices;

    public MyMatrix(MyGraph graph)
    {
        this.graph = graph;
        this.vertices = graph.getV();
        this.matrix = new int[vertices][vertices];
    }

    public void generateMatrix()
    {
        for(int i = 0; i < vertices; i++)
        {
            for(int j = 0; j < vertices; j++)
            {
                if (graph.getG().getEdge(i, j) != null)
                {
                    matrix[i][j] = (int)(Math.random() * 4500) + 500;
                }
                else
                {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    public int[][] getMatrix()
    {
        return matrix;
    }

    public void displayMatrix(TableView<List<String>> matrixTable)
    {
        matrixTable.getItems().clear();
        matrixTable.getColumns().clear();

        // Add a column for the row numbers
        TableColumn<List<String>, String> rowNumberColumn = new TableColumn<>("#");
        rowNumberColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(matrixTable.getItems().indexOf(cellData.getValue()))));
        matrixTable.getColumns().add(rowNumberColumn);

        for(int i = 0; i < vertices; i++)
        {
            final int columnIndex = i;
            TableColumn<List<String>, String> column = new TableColumn<>(String.valueOf(i));
            column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(columnIndex)));
            matrixTable.getColumns().add(column);
        }

        ObservableList<List<String>> data = FXCollections.observableArrayList();
        for (int[] row : matrix)
        {
            data.add(Arrays.stream(row).mapToObj(String::valueOf).collect(Collectors.toList()));
        }

        matrixTable.setItems(data);
    }
}
