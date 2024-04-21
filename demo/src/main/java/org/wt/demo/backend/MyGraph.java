package org.wt.demo.backend;

import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

public class MyGraph
{
    public Graph<Integer, DefaultEdge> getG()
    {
        return g;
    }

    private Graph<Integer, DefaultEdge> g;
    private final Pane pane;
    private final int V = 20;
    private final int E = 30;
    private List<Circle> points;
    private final Map<DefaultEdge, Line> edgeLines = new HashMap<>();

    public MyMatrix getMatrix()
    {
        return matrix;
    }

    public int getV()
    {
        return V;
    }

    private MyMatrix matrix;


    public MyGraph(Pane pane)
    {
        g = new SimpleGraph<>(DefaultEdge.class);
        this.pane = pane;
        this.matrix = new MyMatrix(this);
    }

    public void getRandomEdge()
    {
        Random rand = new Random();
        int edgeIndex = rand.nextInt(g.edgeSet().size());

        List<DefaultEdge> edgeList = new ArrayList<>(g.edgeSet());
        DefaultEdge randomEdge = edgeList.get(edgeIndex);

        Line line = edgeLines.get(randomEdge);
        Color originalColor = (Color) line.getStroke();
        line.setStroke(Color.RED);
        line.setStrokeWidth(4);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            line.setStroke(originalColor);
            line.setStrokeWidth(2);
        });
        pause.play();
    }

    public void generateGraph()
    {
        g = new SimpleGraph<>(DefaultEdge.class);

        addVertices();
        addEdges();

        matrix.generateMatrix();
    }

    private void addVertices()
    {
        for(int i = 0; i < V; i++)
        {
            g.addVertex(i);
        }

        assert(g.vertexSet().size() == V);
    }

    private void addEdges()
    {
        for (int i = 0; i < V; i++)
        {
            g.addEdge(i, (i + 1) % V);
        }

        int e = (int) (Math.random() * (E - V));

        for (int i = 0; i < e; i++)
        {
            int source = (int) (Math.random() * V);
            int target = (int) (Math.random() * V);

            if (source != target && !g.containsEdge(source, target))
            {
                g.addEdge(source, target);
            }
            else
            {
                i--;
            }
        }

        assert(g.edgeSet().size() < E);
    }

    public void drawGraph()
    {
        Circle circle = new Circle(400, 300, 200);
        circle.setOpacity(0.0);
        pane.getChildren().add(circle);

        drawVertices(circle);
        drawEdges();
    }

    private void drawVertices(Circle circle)
    {
        points = new ArrayList<>();
        int numPoints = g.vertexSet().size();
        double angleStep = 360.0 / numPoints;

        for (int i = 0; i < numPoints; i++)
        {
            double angle = Math.toRadians(i * angleStep);
            double x = circle.getCenterX() + circle.getRadius() * Math.cos(angle);
            double y = circle.getCenterY() + circle.getRadius() * Math.sin(angle);

            Circle point = new Circle(x, y, 20);
            point.setFill(Color.BLACK);
            Text text = new Text(x, y, String.valueOf(i));
            text.setFill(Color.WHITE);
            pane.getChildren().addAll(point, text);
            points.add(point);
        }
    }

    private void drawEdges()
    {
        for (DefaultEdge edge : g.edgeSet())
        {
            int source = g.getEdgeSource(edge);
            int target = g.getEdgeTarget(edge);

            Line line = new Line();
            line.setStartX(points.get(source).getCenterX());
            line.setStartY(points.get(source).getCenterY());
            line.setEndX(points.get(target).getCenterX());
            line.setEndY(points.get(target).getCenterY());

            //set line width

            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);

            pane.getChildren().add(line);
            edgeLines.put(edge, line);
        }
    }
}
