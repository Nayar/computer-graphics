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

public class MeraPolygon {
	MeraPoint start = null;;
	MeraPolygon next;
	int noPts = 0;
	
	public void insertPt(int x,int y){
		noPts++;
		if(start == null){
			start = new MeraPoint(x, y);
			System.out.printf("Added %d %d ",x,y);
			return;
		}
		MeraPoint current = start;
		while(current.next != null){
			current = current.next;
		}
		current.next = new MeraPoint(x,y);
		System.out.printf("Added %d %d ",x,y);
	}
}
