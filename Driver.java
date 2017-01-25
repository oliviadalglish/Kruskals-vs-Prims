/*
Created by Olivia Dalglish
November 28, 2016
*/

import java.util.*;
import static java.lang.System.out;

public class Driver {
	
	public static void main(String args[]) {
		Graph myGraph = new Graph();
		
		Vertex v0 = new Vertex(0);
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		Vertex v5 = new Vertex(5);

		v0.addEdge(v1, 6);
		v0.addEdge(v2, 7);
		v0.addEdge(v3, 8);
		v0.addEdge(v4, 4);
		v0.addEdge(v5, 12);
		v1.addEdge(v2, 6);	

		v1.addEdge(v3, 9);
		v1.addEdge(v4, 2);
		v3.addEdge(v4, 6);
		v3.addEdge(v5, 7);
		v4.addEdge(v5, 8);
		v1.addEdge(v2, 4);
		
		myGraph.addVertex(v0);
		myGraph.addVertex(v1);
		myGraph.addVertex(v2);
		myGraph.addVertex(v3);
		myGraph.addVertex(v4);
		myGraph.addVertex(v5);

		myGraph.printGraph();
		
		
	
		System.out.println("Kruskal's Minimum Spanning Tree:\n");
		final long startKruskals = System.currentTimeMillis();
		Kruskals KMST = new Kruskals(myGraph);
		KMST.KruskalsMST();
		final long endKruskals = System.currentTimeMillis();
		KMST.printMST();

		System.out.println("\nPrim's Minimum Spanning Tree:\n");
		final long startPrims = System.currentTimeMillis();
		Prims PMST = new Prims(myGraph);
		PMST.PrimsMST();
		final long endPrims = System.currentTimeMillis();
		PMST.printMST();

		System.out.printf("Running time for Kruskal's: %d\n", endKruskals-startKruskals);
		System.out.printf("Running time for Prim's: %d\n", endPrims-startPrims);
	}
	
};
	
class Kruskals {
	public Graph g;
	ArrayList<Edge> MST;
	TreeSet<Edge> edges;


	class UF {
		public int[] parent;
		public int[] rank;

		public UF(Graph g) {
			int size = g.vertices.size();
			parent = new int[size];
			rank = new int[size];
			for (int i = 0; i<size ; i++) {
				parent[i] = i;
				rank[i] = 0;
			}
		}

		public int find(int i) {
			if (i != parent[i]) {
				parent[i] = find(parent[i]);
			}
			return parent[i];
		}

		public void unions(int x, int y) {
			int xRoot = find(x);
			int yRoot = find(y);
			if (xRoot == yRoot) return;
			if (rank[xRoot] > rank[yRoot]) {
				parent[yRoot] = xRoot;
			}
			else {
				parent[xRoot] = yRoot;
				if (rank[xRoot] == rank[yRoot]) {
					rank[yRoot]++;
				}
			}
		}
	}

	public Kruskals(Graph graph) {
		g = graph;
		MST = new ArrayList<Edge>();
		edges = new TreeSet<Edge>();
		for (Vertex v : g.vertices) {
			for (Edge e : v.edgeList) {	
				edges.add(e);
			}
		}
	}	

	public void KruskalsMST() {
		UF uf = new UF(g);
		for (Edge e : edges) {
			if (uf.find(e.v1.label) != uf.find(e.v2.label)) {
				MST.add(e);
				uf.unions(e.v1.label,e.v2.label);
			}
		}
	}		


	public void printMST() {
		for (Edge e : MST) {
			e.printEdge();
		}
	}
};


class Prims {
	public Graph g;
	int size;
	ArrayList<Edge> MST;
	int[] dist;
	int[] parent;
	boolean[] visited;
	
	public Prims(Graph graph) {
		g = graph;
		size = g.vertices.size();

		dist = new int[size];
		visited = new boolean[size];
		parent = new int[size];
		for (int i=0; i <size; i++) { visited[i] = false; }
		for (int i=0; i <size; i++) { dist[i] = Integer.MAX_VALUE; }
		dist[0] = 0;
		MST = new ArrayList<Edge>();
	}
	
	public void PrimsMST() {
		for (int k=0; k<size; k++) {
			int next =  minVertex(dist, visited);
			if (next != -1 ){ 
				visited[next] = true;
				for (Edge e : g.vertices.get(next).edgeList) {
					if (dist[e.v2.label] > e.weight) {
						dist[e.v2.label] = e.weight;
						parent[e.v2.label] = next;
					}
				}
                                for (Edge e : g.vertices.get(parent[next]).edgeList) {
                                	if (next == e.v2.label) { MST.add(e); }
                                }

			}
		}		
	}
				
	private static int minVertex (int [] dist, boolean [] v) {
		int x = 99999999;
		int y = -1;   // graph not connected, or no unvisited vertices
		for (int i=0; i<dist.length; i++) {
			if (!v[i] && dist[i]<x) {y=i; x=dist[i];}
		}
		return y;
	}
	
	public void printMST() {
		for (Edge e : MST) {
			e.printEdge();
		}
	}
}
