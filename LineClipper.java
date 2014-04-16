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

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class LineClipper {
	static double x1,x2,y1,y2;
	static Rectangle2D clipArea;
	static int cohenBits1[],cohenBits2[];
	static float m;
	
	public static Line2D clipLine(Line2D l,Rectangle2D clipArea1){
		x1 = l.getX1();
		x2 = l.getX2();
		y1 = l.getY1();
		y2 = l.getY2();
		clipArea = clipArea1;
		cohenBits1 = calculateCohenBits(x1,y1);
		cohenBits2 = calculateCohenBits(x2,y2);
		
		// Checking if completely inside
		if(completelyInside()){
			return l;
		}
		
		if(completelyOuside()){
			return new Line2D.Double(0,0,0,0);
		}
		
		int[] zeroarray = {0,0,0,0};
		while(true){
				
			if (cohenBits1[0] == 1) {
				x1 = x1 + (x2 - x1) * (clipArea.getMaxY() - y1) / (y2 - y1);
				y1 = clipArea.getMaxY();
			} 
			if (cohenBits1[1] == 1) {
				x1 = x1 + (x2 - x1) * (clipArea.getMinY() - y1) / (y2 - y1);
				y1 = clipArea.getMinY();
			} 
			if (cohenBits1[2] == 1) {
				y1 = y1 + (y2 - y1) * (clipArea.getMaxX() - x1) / (x2 - x1);
				x1 = clipArea.getMaxX();
			} 
			if (cohenBits1[3] == 1) {
				y1 = y1 + (y2 - y1) * (clipArea.getMinX() - x1) / (x2 - x1);
				x1 = clipArea.getMinX();
			}
			
			if (cohenBits2[0] == 1) {
				x2 = x1 + (x2 - x1) * (clipArea.getMaxY() - y1) / (y2 - y1);
				y2 = clipArea.getMaxY();
			} 
			if (cohenBits2[1] == 1) {
				x2 = x1 + (x2 - x1) * (clipArea.getMinY() - y1) / (y2 - y1);
				y2 = clipArea.getMinY();
			} 
			if (cohenBits2[2] == 1) {
				y2 = y1 + (y2 - y1) * (clipArea.getMaxX() - x1) / (x2 - x1);
				x2 = clipArea.getMaxX();
			} 
			if (cohenBits2[3] == 1) {
				y2 = y1 + (y2 - y1) * (clipArea.getMinX() - x1) / (x2 - x1);
				x2 = clipArea.getMinX();
			}
			cohenBits1 = calculateCohenBits(x1,y1);
			cohenBits2 = calculateCohenBits(x2,y2);
			if(cohenIsZero(cohenBits1) && cohenIsZero(cohenBits2)){
				break;
			}
		}
		return new Line2D.Double(x1,y1,x2,y2);
	}

	private static int[] calculateCohenBits(double x, double y) {
		int[] cohenBits = {0,0,0,0};
		if((x - clipArea.getMinX()) < 0){
			cohenBits[3] = 1;
		}
		if((clipArea.getMaxX() - x) < 0){
			cohenBits[2] = 1;
		}
		if((y - clipArea.getMinY()) < 0){
			cohenBits[1] = 1;
		}
		if((clipArea.getMaxY() - y) < 0){
			cohenBits[0] = 1;
		}
		for(int i = 0; i < 4; i++){
			System.out.print(cohenBits[i]);
		}
		System.out.println();
		return cohenBits;
	}
	
	public static boolean completelyInside(){
		for(int i = 0;i<4;i++){
			if((cohenBits1[i] | cohenBits2[i]) != 0){
				return false;
			}
		}
		return true;
	}
	
	private static boolean completelyOuside() {
		for(int i = 0;i<4;i++){
			if((cohenBits1[i] & cohenBits2[i]) != 0){
				System.out.print("Line discarded\n");
				return true;
			}
		}
		return false;
	}
	
	public Point2D clipLine(){
		Double x,y;
		
		if (cohenBits1[0] == 1) {
			x = x1 + (x2 - x1) * (clipArea.getMaxY() - y1) / (y2 - y1);
			y = clipArea.getMaxY();
		} else if (cohenBits1[1] == 1) {
			x = x1 + (x2 - x1) * (clipArea.getMinY() - y1) / (y2 - y1);
			y = clipArea.getMinY();
		} 
		else if (cohenBits1[2] == 1) {
			y = y1 + (y2 - y1) * (clipArea.getMaxX() - x1) / (x2 - x1);
			x = clipArea.getMaxX();
		} 
		else if (cohenBits1[3] == 1) {
			y = y1 + (y2 - y1) * (clipArea.getMinX() - x1) / (x2 - x1);
			x = clipArea.getMinX();
		}
		return null;
	}
	
	public static boolean cohenIsZero(int cohenBits[]){
		for(int i = 0;i<4;i++){
			if (cohenBits[i] == 1){
				return false;
			}
		}
		return true;
	}
}
