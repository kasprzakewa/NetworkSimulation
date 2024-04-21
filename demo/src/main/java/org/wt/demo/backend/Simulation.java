package org.wt.demo.backend;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.List;

public class Simulation
{
    public MyGraph getG()
    {
        return g;
    }

    private MyGraph g;
    private final Pane pane;

    public Simulation(Pane pane)
    {
        this.pane = pane;
    }

    public void run()
    {
        pane.getChildren().clear();
        g = new MyGraph(pane);
        g.generateGraph();
        g.drawGraph();
    }
}
