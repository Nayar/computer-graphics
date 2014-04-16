/*
    Copyright 2013 by Nayar Joolfoo

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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Path2D.Double;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MeraFrame extends JFrame{
	MeraLines l;
	MeraPolygons p;
	MeraPolygon temp = new MeraPolygon();
	Shape meraClippingArea;
	int clipAreaX,clipAreaY,clipAreaW,clipAreaH;
	int x1,y1,x2,y2 = 0;
	
	Point s;
	Point s1;
	//int<ArrayList> pointstodraw = new <ArrayLi
			
	ArrayList<Point> xxx=new ArrayList();
	
	int count = 0;
	
	JRadioButton shapeType;
	JRadioButton dLine,dPolygon,dClipWindow;
	JButton doPoly,doClear;
	MeraPanel drawingArea;
	boolean clipActivated = false;
	int noOfClicks = 0;
	Graphics2D g2d;
	
	public MeraFrame(){
		l = new MeraLines();
		p = new MeraPolygons();
		//this.setLayout(new GridLayout(2,1));
		this.setLayout(null);
		JPanel buttonsPanel = new JPanel();
		drawingArea = new MeraPanel();
		ButtonGroup actionIntended = new ButtonGroup();
		dLine = new JRadioButton("Draw Line");
		dPolygon = new JRadioButton("Draw Polygon");
		dClipWindow = new JRadioButton("Draw Window");
		doPoly = new JButton("Draw Polygon");
		doPoly.setEnabled(false);
		doClear = new JButton("Clear");
		actionIntended.add(dLine);
		actionIntended.add(dPolygon);
		actionIntended.add(dClipWindow);
		buttonsPanel.add(dLine);
		buttonsPanel.add(dPolygon);
		buttonsPanel.add(dClipWindow);
		buttonsPanel.add(doPoly);
		buttonsPanel.add(doClear);
		//drawingArea.addMouseListener(lineListener);
		dLine.addActionListener(al);
		dPolygon.addActionListener(al);
		doPoly.addActionListener(al);
		doClear.addActionListener(al);
		dClipWindow.addActionListener(al);
		buttonsPanel.setBounds(0, 0, 1000, 50);
		drawingArea.setBounds(0, 50, 1000, 700);
		drawingArea.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(buttonsPanel);
		this.add(drawingArea);
		
		//
		//this.add(x);
	}
	
	ActionListener al = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//removeListeners();
			doPoly.setEnabled(false);
			drawingArea.removeMouseListener(null);
			if(temp.start != null)
				p.insert(temp);
			if(e.getSource() == dLine){
				removeListeners();
				drawingArea.addMouseListener(lineListener);
				System.out.print("Switched to line\n");
			}
			else if (e.getSource() == dPolygon){
				repaint();
				removeListeners();
				temp = new MeraPolygon();
				drawingArea.addMouseListener(polyListener);
				System.out.print("Switched to poly\n");
				
			}
			else if(e.getSource() == doClear){
				l = new MeraLines();
				p = new MeraPolygons();
				clipAreaX = clipAreaY = clipAreaW = clipAreaH = 0;
				clipActivated = false;
				repaint();
			}
			else if(e.getSource() == dClipWindow){
				removeListeners();
				drawingArea.addMouseListener(clipAreaListener);
				System.out.print("Switched to Clip\n");
				
			}
			else if(e.getSource() == doPoly){
				repaint();
				removeListeners();
				xxx=new ArrayList();
				temp = new MeraPolygon();
				drawingArea.addMouseListener(polyListener);
				//System.out.print("Switched to poly\n");
			}
		}
	};
	
	MouseListener lineListener = new MouseAdapter() {
		//int x1,y1,x2,y2 = 0;
		boolean started = false;
		public void mouseReleased(MouseEvent arg0) {
			//x2 = arg0.getX();
			//y2 = arg0.getY();
			//l.insert(x1, y1, x2, y2);
			//repaint();
		}
		
		public void mousePressed(MouseEvent arg0) {
			//x1 = arg0.getX();
			//y1 = arg0.getY();
		}
		
		public void mouseClicked(MouseEvent arg0){
			if(noOfClicks == 0){
				x1 = arg0.getX();
				y1 = arg0.getY();
				s=new Point(x1,y1);
				g2d.draw(new Ellipse2D.Double(x1, y1,5,5));
				repaint();
				noOfClicks++;
				
			}
			else{
				x2 = arg0.getX();
				y2 = arg0.getY();
				s1=new Point(x2,y2);
				g2d.draw(new Ellipse2D.Double(x2, y2,5,5));
				repaint();
				l.insert(x1, y1, x2, y2);
				repaint();
				noOfClicks = 0;
			}
			
		}
	};
	
	MouseListener polyListener = new MouseAdapter() {

		public void mouseClicked(MouseEvent arg0) {
			temp.insertPt(arg0.getX(), arg0.getY());
			doPoly.setEnabled(true);
			xxx.add(new Point(arg0.getX(), arg0.getY()));
			System.out.print("Clicked Poly Listener\n");
			repaint();
			
		}
	};
	
	MouseListener clipAreaListener = new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.print("Released\n");
			// TODO Auto-generated method stub
			clipAreaW = e.getX()-clipAreaX;
			clipAreaH = e.getY()-clipAreaY;
			clipActivated = true;
			repaint();
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.print("Pressed\n");
			clipAreaX = e.getX();
			clipAreaY = e.getY();
		}
	};
	
	public class MeraPanel extends JPanel{
		public void paintComponent(Graphics g){
			if(l == null){
				return;
			}
			g2d = (Graphics2D)g;
			
			meraClippingArea = new Rectangle2D.Double(clipAreaX,clipAreaY,clipAreaW,clipAreaH);
			g2d.draw(meraClippingArea);
								
			MeraPolygon mp = p.start;
			while(mp != null){
				System.out.println("Num of pts: " + mp.noPts);
				int[] x = new int[mp.noPts];
				int[] y = new int[mp.noPts];
				MeraPoint current = mp.start;
				int count  = 0;
				// Added from Laura
				Clipping clipping = new Clipping(clipAreaX,clipAreaY,clipAreaX+clipAreaW,clipAreaY+clipAreaH);
				while(current != null){
					x[count] = current.x;
					y[count++] = current.y;
					// Added from Laura
					clipping.addPoint(current.x, current.y);
					current = current.next;
				}
				
				g2d.setColor(Color.GRAY);
				Shape poly = new Polygon(x,y,mp.noPts);
				g2d.fill(poly);
				g2d.draw(poly);
				if(clipActivated){
					// Added from Laura
					clipping.runClipping(g, true, 1000,700);
					clipping.drawPolygon(g, false, Color.CYAN, clipping.IsClipped());
				}
				mp = mp.next;
			}
			
			MeraLine ml = l.root;
			while(ml != null){
				g2d.setColor(Color.BLACK);
				g2d.draw(ml.l);
				g2d.draw(new Ellipse2D.Double(ml.l.getX1(), ml.l.getY1(),5,5));
				g2d.draw(new Ellipse2D.Double(ml.l.getX2(), ml.l.getY2(),5,5));
				if(clipActivated){
					g2d.setColor(Color.RED);
					g2d.draw(LineClipper.clipLine(ml.l, (Rectangle2D) meraClippingArea));
				}
				ml = ml.next;
			}
			
			g2d.setColor(Color.BLACK);
			for(int i = 0;i<xxx.size();i++){
				g2d.drawLine(xxx.get(i).x, xxx.get(i).y, xxx.get(i).x, xxx.get(i).y);
				g2d.draw(new Ellipse2D.Double(xxx.get(i).x, xxx.get(i).y,5,5));
			}
			
			if(noOfClicks == 1){
				g2d.draw(new Ellipse2D.Double(x1, y1,5,5));
			}
		}
	}
	
	public void removeListeners(){
		drawingArea.removeMouseListener(lineListener);
		drawingArea.removeMouseListener(polyListener);
		drawingArea.removeMouseListener(clipAreaListener);
	}
}
