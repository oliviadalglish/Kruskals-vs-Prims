/*
 Created by Olivia Dalglish
 November 28, 2015
*/

import java.util.*;
import static java.lang.System.out;

/* This graph is implemented using an ArrayList of Vertex objects. Each Vertex has a HashMap member to represent to adjacency ist */

public class Graph {
	ArrayList<Vertex> vertices;

	public Graph() {
		vertices = new ArrayList<Vertex>();
	}
	
	public void addVertex(Vertex v) {
		vertices.add(v);
	}

	public void printGraph() {
		for (Vertex v : vertices) {
			v.printVertex();
		}
	}
	public ArrayList<Edge> concatenateEdges() {
		ArrayList<Edge> allEdges = new ArrayList<Edge>();
		for (Vertex v : vertices) {
			for (Edge e : v.edgeList) {
				allEdges.add(e);
			}
		}
		return allEdges;
	}
};

class Vertex {
	int label;

	HashMap<Vertex, Integer> adjList;
	ArrayList<Edge> edgeList;

	public Vertex(int i) {
		label = i;
		adjList = new HashMap<Vertex, Integer>();
		edgeList = new ArrayList<Edge>();
	}
	public void addEdge(Vertex v, int w) {
		adjList.put(v, w);
		//(v.adjList).put(this, w);
		Edge e = new Edge(this, v, w);
		edgeList.add(e);
		
	}
	public void printVertex() {
		System.out.printf("%s has the following adjacent vertices: ", 
				this.label);
		Iterator it = adjList.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			Object o = pair.getKey();
			Vertex v = (Vertex) o;
			int i = v.label;
			System.out.printf("%d, weight of %d; ", i, pair.getValue());
			
		}
		System.out.println();	

	}

	public int getLabel(Vertex v) { return v.label; } 
			 
	@Override
	public int hashCode() {
		return label;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != getClass()) { return false; }
		else {
			Vertex v = (Vertex) obj;
			return label == v.label;
		}
	}
};

class Edge implements Comparable<Edge> {
	Vertex v1, v2;
	int weight;
	
	Edge(Vertex one, Vertex two, int w) {
		v1 = one;
		v2 = two;
		weight = w;
	}
	
	public void printEdge() {
		System.out.printf("(%d, %d) %d;\n", v1.label, v2.label, weight);
	}
	
	@Override
	public int hashCode() { 
		int result = 17;
		result = 31 * result + v1.hashCode();
		result = 31 * result + v2.hashCode();
		result = 31 * result + weight;
		return result;
	}

	@Override 
	public boolean equals(Object obj) {
		if (obj.getClass() != getClass()) { return false; }
		else {
			Edge e = (Edge) obj;
			if (weight != e.weight) { return false; }
			else {
				if (v1.equals(e.v1)) {
					if (!v2.equals(e.v2)) { return false; }
				}
				else if (v1.equals(e.v2)) {
					if (!v2.equals(e.v1)) { return false; }
				}
				else { return true; }
			}
		}
		return false;
	} 
	
	@Override
	public int compareTo(Edge e) {
		if (this.equals(e)) { return 0; }
		else if (weight < e.weight) { return -1; }
		else { return 1; } 
		
	}

}
