package UI;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static java.util.Comparator.*;

//https://stackoverflow.com/questions/8693342/drawing-a-simple-line-graph-in-java
public class LineGraph extends JPanel
{
    private LinkedHashMap<Integer, Integer> data = new LinkedHashMap<Integer, Integer>();
    int padding = 8;

    public LineGraph()
    {
        setBackground(Style.midgroundColor);
    }

    public void setData(LinkedHashMap data)
    {
        this.data = data;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        int min = data.values().stream().min(Comparator.<Integer>naturalOrder()).get();
        int max = data.values().stream().max(Comparator.<Integer>naturalOrder()).get();

        double xMul = (double) (getWidth()-padding*2) / (double) data.keySet().stream().max(Comparator.<Integer>naturalOrder()).get();
        double yMul = (double) (getHeight()-padding*2) / (double) Math.abs(min - max);

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setStroke(new BasicStroke(2f));

        for(int i = 0; i < (max-min); i += 5)
        {
            int y = getHeight () - (int) (i * yMul) - padding;

            g2D.setColor(Style.backgroundColor);
            g2D.drawLine(padding, y, getWidth()-padding, y);

            g2D.setColor(Style.textColor);
            g2D.drawString("" + i, 8+padding, y);
        }

        for(int i = 0; i < data.keySet().stream().max(Comparator.<Integer>naturalOrder()).get(); i+= 30)
        {
            int x = (int) (i * xMul) + padding;

            g2D.setColor(Style.backgroundColor);
            g2D.drawLine(x, padding, x, getHeight()-padding);

            //g2D.setColor(Style.textColor);
            //g2D.drawString("" + i, 8, y + 2);

        }


        List<Point> points = new ArrayList<Point>();

        for (int key : data.keySet())
        {
            int x = (int) (key * xMul) + padding;
            int y = getHeight() - (int) ((double)data.get(key) * yMul) - padding;

            points.add(new Point(x, y));
        }

        g2D.setColor(Style.highLightColor);
        g2D.setStroke(new BasicStroke(4f));
        for(int i = 0; i < points.size()-1; i++)
        {
            Point a = points.get(i);
            Point b = points.get(i+1);
            g2D.drawLine(a.x, a.y, b.x, b.y);
        }
    }
}
