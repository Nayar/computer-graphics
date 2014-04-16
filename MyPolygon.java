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
import java.util.*;

public class MyPolygon
{
    protected Point[] listOfPoints;
    private int[] xPoints;
    private int[] yPoints;
    private int N = 7;
    private boolean isComplete = false;
    private int count = 0;
    
    public MyPolygon()
    {
        listOfPoints = new Point[0];
        xPoints = new int[0];
        yPoints = new int[0];
        count = 0;
    }
    public void addPoint(int x, int y)
    {
        if (isComplete == false)
            if (this.contains(x, y) == false)
            {
                Point[] tmpListOfPoints = new Point[count + 1];
                int[] tmpX = new int[count + 1];
                int[] tmpY = new int[count + 1];
               
                
                for (int i = 0; i < count; i++)
                {
                    tmpListOfPoints[i] = listOfPoints[i];
                    tmpX[i] = xPoints[i];
                    tmpY[i] = yPoints[i];
                }
                tmpListOfPoints[count] = new Point(x, y);
                tmpX[count] = x;
                tmpY[count] = y;
                listOfPoints = Arrays.copyOf(tmpListOfPoints, count + 1);//allow array to b viewed as list
                xPoints = Arrays.copyOf(tmpX, count + 1);//and Copies the specified array 
                yPoints = Arrays.copyOf(tmpY, count + 1);// so the copy has the specified length.
                count++;
            }
    }    
    public void drawPolygon(Graphics g, boolean showVertexes, Color fillColor, boolean isClipped)
    {
        if (!this.isComplete && !isClipped)
            for (int i = 0; i < count; i++)
            {
                g.setColor(Color.red);
                if (showVertexes)
                    g.fillArc(listOfPoints[i].x - N, listOfPoints[i].y - N, N * 2, N * 2, 0, 360);       
                g.setColor(Color.blue);                                                           
                if ((i + 1) < count)
                    g.drawLine(listOfPoints[i].x, listOfPoints[i].y, listOfPoints[i + 1].x, listOfPoints[i + 1].y);
            }
        else if (isComplete || isClipped)
        {
            drawPolygon(g, fillColor, Color.blue);
        }
        if (showVertexes)
            for (int i = 0; i < count; i++)
            {
                g.setColor(Color.red);
                g.fillArc(listOfPoints[i].x - N, listOfPoints[i].y - N, N * 2, N * 2, 0, 360);
            }
    }
    public boolean isPolygonComplete()
    {
        return isComplete;
    }
    public int[] getLastVertex()
    {
        int[] result = new int[2];
        result[0] = listOfPoints[count - 1].x;
        result[1] = listOfPoints[count - 1].y;
        return result;
    }
    protected int countVertexes()
    {
        return count;
    }
    protected void clear()
    {
        isComplete = false;
        listOfPoints = new Point[0];
        xPoints = new int[0];
        yPoints = new int[0];
        count = 0;
    }
    private boolean contains(int x, int y)
    {
        for (int i = 0; i < count; i++)
        {
            if (x <= listOfPoints[i].x + N 
                    && x >= listOfPoints[i].x - N
                    && y <= listOfPoints[i].y + N
                    && y >= listOfPoints[i].y - N)
            {
                if (i == 0 && count >= 3)
                    isComplete = true;
                return true;
            }
        }
        return false;
    }
    private void drawPolygon(Graphics g, Color fillColor, Color borderColor)
    {
        g.setColor(fillColor);
        g.fillPolygon(xPoints, yPoints, count);
        g.setColor(borderColor);
        g.drawPolygon(xPoints, yPoints, count);
    }
}
