package com.bmaxtech.dbscan.logic;

import java.util.List;
import java.util.Vector;

public class Dbscan {
	public static Vector<Point> hset = new Vector<Point>();
	public static int tdistance = 0;
	public static int minpt = 0;

	@SuppressWarnings("rawtypes")
	public static Vector<List> resultList = new Vector<List>();
	public static Vector<Point> pointList = Utility.getList();
	public static Vector<Point> Neighbours;

	@SuppressWarnings("rawtypes")
	public static Vector<List> applyDbscan() {
		resultList.clear();
		pointList.clear();
		Utility.VisitList.clear();
		pointList = Utility.getList();

		int index2 = 0;

		while (pointList.size() > index2) {
			Point p = pointList.get(index2);
			if (!Utility.isVisited(p)) {
				Utility.Visited(p);
				Neighbours = Utility.getNeighbours(p);
				if (Neighbours.size() >= minpt) {
					int ind = 0;
					while (Neighbours.size() > ind) {
						Point r = Neighbours.get(ind);
						if (!Utility.isVisited(r)) {
							Utility.Visited(r);
							Vector<Point> Neighbours2 = Utility.getNeighbours(r);
							if (Neighbours2.size() >= minpt) {
								Neighbours = Utility.Merge(Neighbours, Neighbours2);
							}
						}
						ind++;
					}

					resultList.add(Neighbours);
				}
			}
			index2++;
		}
		return resultList;
	}

	public static void setTDistance(int dis) {
		tdistance = dis;
	}

	public static void setMinPoints(int min) {
		minpt = min;
	}

	/**
	 * Add new points
	 */
	public static void addPoint(int x, int y) {
		hset.addElement(new Point(x, y));
	}

	/**
	 * Get points
	 * 
	 * @return
	 */
	public static Vector<Point> getPoints() {
		// TODO Auto-generated method stub
		return hset;
	}
}
