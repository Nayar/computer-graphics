/*
    Copyright 2013 by Laura Dubois

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, version 3 of the License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.awt.*;

enum OptionForClipping//Enum serves as a type of fixed number of constants
                          //and can be used at least for two things
{
    InsideInside,
    InsideOutside,
    OutsideOutside,
    OutsideInside
}

enum Side
{
    left,
    right,
    top,
    bottom
}

public class Clipping extends MyPolygon
{
    private int maxX;
    private int minX;
    public int maxY;
    private int minY;
    private boolean isClipped = false;
    MyPolygon polygon;
    
    public Clipping(int fromX, int fromY, int toX, int toY) 
    {
        minX = fromX;
        minY = fromY;
        maxX = toX;
        maxY = toY;
        polygon = new MyPolygon();
    }
    public void clearAll()
    {
        clear();
        isClipped = false;
    }
    public void drawClippingWindow(Graphics g)
    {
        g.setColor(Color.yellow);
        g.fillRect(minX, minY, maxX - minX, maxY - minY);
        g.setColor(Color.yellow);
        g.drawRect(minX, minY, maxX - minX, maxY - minY);
        g.setColor(Color.lightGray);
        g.drawString("Clipping window", minX, maxY + 14);
    }
    public void runBySideClipping(Graphics g, boolean showVertexes, int width, int height, Side side)
    {
        Point x, y;        
        switch (side)
        {
            case left:
                x = new Point(minX, 0);
                y = new Point(minX, height);
                for (int i = 0; i < countVertexes(); i++)
                {
                    if ((i + 1) < countVertexes())
                        getOutput(i, i + 1, x, y, Side.left);
                    else if ((i + 1) == countVertexes())
                        getOutput(i, 0, x, y, Side.left);
                }
                rebuiltPolygon();
                break;
            case right:
                x = new Point(maxX, 0);
                y = new Point(maxX, height);
                for (int i = 0; i < countVertexes(); i++)
                {
                    if ((i + 1) < countVertexes())
                        getOutput(i, i + 1, x, y, Side.right);
                    else if ((i + 1) == countVertexes())
                        getOutput(i, 0, x, y, Side.right);
                }
                rebuiltPolygon();
                break;
            case top:
                x = new Point(0, minY);
                y = new Point(width, minY);
                for (int i = 0; i < countVertexes(); i++)
                {
                    if ((i + 1) < countVertexes())
                        getOutput(i, i + 1, x, y, Side.top);
                    else if ((i + 1) == countVertexes())
                        getOutput(i, 0, x, y, Side.top);
                }
                rebuiltPolygon();
                break;
            case bottom:
                x = new Point(0, maxY);
                y = new Point(width, maxY);
                for (int i = 0; i < countVertexes(); i++)
                {
                    if ((i + 1) < countVertexes())
                        getOutput(i, i + 1, x, y, Side.bottom);
                    else if ((i + 1) == countVertexes())
                        getOutput(i, 0, x, y, Side.bottom);
                }
                rebuiltPolygon();
                break;
        }
        isClipped = true;
    }
    public void runClipping(Graphics g, boolean showVertexes, int width, int height)
    {
        // left side
        runBySideClipping(g, showVertexes, width, height, Side.left);
        // right side
        runBySideClipping(g, showVertexes, width, height, Side.right);
        // top side
        runBySideClipping(g, showVertexes, width, height, Side.top);
        // bottom side
        runBySideClipping(g, showVertexes, width, height, Side.bottom);
    }
    public boolean IsClipped()
    {
        return isClipped;
    }
    private void getOutput(int vertex1, int vertex2, Point point1, Point point2, Side side)
    {
    	switch (side)
        {
            case left:
                if (listOfPoints[vertex1].x >= minX && listOfPoints[vertex2].x >= minX)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.InsideInside);
                if (listOfPoints[vertex1].x >= minX && listOfPoints[vertex2].x < minX)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.InsideOutside);
                if (listOfPoints[vertex1].x < minX && listOfPoints[vertex2].x < minX)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.OutsideOutside);
                if (listOfPoints[vertex1].x < minX && listOfPoints[vertex2].x >= minX)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.OutsideInside);
                break;
            case right:
                if (listOfPoints[vertex1].x <= maxX && listOfPoints[vertex2].x <= maxX)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.InsideInside);
                if (listOfPoints[vertex1].x <= maxX && listOfPoints[vertex2].x > maxX)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.InsideOutside);
                if (listOfPoints[vertex1].x > maxX && listOfPoints[vertex2].x > maxX)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.OutsideOutside);
                if (listOfPoints[vertex1].x > maxX && listOfPoints[vertex2].x <= maxX)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.OutsideInside);
                break;
            case top:
                if (listOfPoints[vertex1].y >= minY && listOfPoints[vertex2].y >= minY)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.InsideInside);
                if (listOfPoints[vertex1].y >= minY && listOfPoints[vertex2].y < minY)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.InsideOutside);
                if (listOfPoints[vertex1].y < minY && listOfPoints[vertex2].y < minY)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.OutsideOutside);
                if (listOfPoints[vertex1].y < minY && listOfPoints[vertex2].y >= minY)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.OutsideInside);
                break;
            case bottom:
                if (listOfPoints[vertex1].y <= maxY && listOfPoints[vertex2].y <= maxY)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.InsideInside);
                if (listOfPoints[vertex1].y <= maxY && listOfPoints[vertex2].y > maxY)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.InsideOutside);
                if (listOfPoints[vertex1].y > maxY && listOfPoints[vertex2].y > maxY)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.OutsideOutside);
                if (listOfPoints[vertex1].y > maxY && listOfPoints[vertex2].y <= maxY)
                    clipping(listOfPoints[vertex1], listOfPoints[vertex2], point1, point2, OptionForClipping.OutsideInside);
                break;
        
        }
    }
    private void clipping(Point v1, Point v2, Point vc1, Point vc2, OptionForClipping option)
    {
        Point tmpPoint;
        switch (option)
        {
            case InsideInside: 
                polygon.addPoint(v2.x, v2.y);
                break; // v2
            case InsideOutside: 
                tmpPoint = intersect(v1, v2, vc1, vc2);
                polygon.addPoint(tmpPoint.x, tmpPoint.y);
                break; // output intersect
            case OutsideOutside: break; // no output
            case OutsideInside: 
                tmpPoint = intersect(v1, v2, vc1, vc2);
                polygon.addPoint(tmpPoint.x, tmpPoint.y);
                polygon.addPoint(v2.x, v2.y);
                break; // output v2, intersect
        }
    }
    private Point intersect(Point v1, Point v2, Point vc1, Point vc2)
    {
        double  x1 = v1.x, y1 = v1.y,
                x2 = v2.x, y2 = v2.y,
                x3 = vc1.x, y3 = vc1.y,
                x4 = vc2.x, y4 = vc2.y;
        double x = det(det(x1, y1, x2, y2), x1 - x2, det(x3, y3, x4, y4), x3 - x4)/
                     det(x1 - x2, y1 - y2, x3 - x4, y3 - y4);
        double y = det(det(x1, y1, x2, y2), y1 - y2, det(x3, y3, x4, y4), y3 - y4)/
                     det(x1 - x2, y1 - y2, x3 - x4, y3 - y4);
        return new Point((int)x, (int)y);
    }
    private static double det(double a, double b, double c, double d)
    {
        return a * d - b * c;
    }
    private void rebuiltPolygon()
    {
        clear();
        for (int i = 0; i < polygon.countVertexes(); i++)
        {
            addPoint(polygon.listOfPoints[i].x, polygon.listOfPoints[i].y);
        }
        //listOfPoints = Arrays.copyOf(polygon.listOfPoints, polygon.countVertexes());
        polygon.clear();
    }
    public void sleep()
    {
        try
        { Thread.sleep(200); }
        catch (InterruptedException e) {}
    }
}

