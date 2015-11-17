import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.HashSet;
import java.util.Set;

public class SAP {
    private Digraph graph;

    public SAP(Digraph G) {
        if (G == null) throw new NullPointerException();

        this.graph = new Digraph(G);
    }

    public int length(int v, int w) {
        if (v < 0 || v > graph.V() - 1) throw new IndexOutOfBoundsException();
        if (w < 0 || w > graph.V() - 1) throw new IndexOutOfBoundsException();

        int min = -1;
        BreadthFirstDirectedPaths sw = new BreadthFirstDirectedPaths(graph, w);
        BreadthFirstDirectedPaths sv = new BreadthFirstDirectedPaths(graph, v);
        Queue<Integer> vertices = new Queue<>();
        Set<Integer> visited = new HashSet<>();

        vertices.enqueue(v);
        while (!vertices.isEmpty()) {
            int vertex = vertices.dequeue();
            visited.add(vertex);
            if (sw.hasPathTo(vertex)) {
                int dist = sw.distTo(vertex) + sv.distTo(vertex);
                if (min == -1) {
                    min = dist;
                } else if (dist < min) {
                    min = dist;
                }
            }
            for (int adj : graph.adj(vertex)) {
                if (!visited.contains(adj)) vertices.enqueue(adj);
            }
        }

        return min;
    }

    public int ancestor(int v, int w) {
        if (v < 0 || v > graph.V() - 1) throw new IndexOutOfBoundsException();
        if (w < 0 || w > graph.V() - 1) throw new IndexOutOfBoundsException();

        int min = -1;
        int ancestor = -1;
        BreadthFirstDirectedPaths sw = new BreadthFirstDirectedPaths(graph, w);
        BreadthFirstDirectedPaths sv = new BreadthFirstDirectedPaths(graph, v);
        Queue<Integer> vertices = new Queue<>();
        Set<Integer> visited = new HashSet<>();

        vertices.enqueue(v);
        while (!vertices.isEmpty()) {
            int vertex = vertices.dequeue();
            visited.add(vertex);
            if (sw.hasPathTo(vertex)) {
                int dist = sw.distTo(vertex) + sv.distTo(vertex);
                if (min == -1) {
                    min = dist;
                    ancestor = vertex;
                } else if (dist < min) {
                    min = dist;
                    ancestor = vertex;
                }
            }
            for (int adj : graph.adj(vertex)) {
                if (!visited.contains(adj)) vertices.enqueue(adj);
            }
        }

        return ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new NullPointerException();

        int min = -1;
        BreadthFirstDirectedPaths sw = new BreadthFirstDirectedPaths(graph, w);
        BreadthFirstDirectedPaths sv = new BreadthFirstDirectedPaths(graph, v);
        Queue<Integer> vertices = new Queue<>();
        Set<Integer> visited = new HashSet<>();

        for (int vertex : v) {
            vertices.enqueue(vertex);
            visited.add(vertex);
        }
        while (!vertices.isEmpty()) {
            int vertex = vertices.dequeue();
            if (sw.hasPathTo(vertex)) {
                int dist = sw.distTo(vertex) + sv.distTo(vertex);
                if (min == -1) {
                    min = dist;
                } else if (dist < min) {
                    min = dist;
                }
            }
            for (int adj : graph.adj(vertex)) {
                if (!visited.contains(adj)) {
                    vertices.enqueue(adj);
                    visited.add(adj);
                }
            }
        }

        return min;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new NullPointerException();

        int min = -1;
        int ancestor = -1;
        BreadthFirstDirectedPaths sw = new BreadthFirstDirectedPaths(graph, w);
        BreadthFirstDirectedPaths sv = new BreadthFirstDirectedPaths(graph, v);
        Queue<Integer> vertices = new Queue<>();
        Set<Integer> visited = new HashSet<>();

        for (int vertex : v) {
            vertices.enqueue(vertex);
            visited.add(vertex);
        }
        while (!vertices.isEmpty()) {
            int vertex = vertices.dequeue();
            if (sw.hasPathTo(vertex)) {
                int dist = sw.distTo(vertex) + sv.distTo(vertex);
                if (min == -1) {
                    min = dist;
                    ancestor = vertex;
                } else if (dist < min) {
                    min = dist;
                    ancestor = vertex;
                }
            }
            for (int adj : graph.adj(vertex)) {
                if (!visited.contains(adj)) {
                    vertices.enqueue(adj);
                    visited.add(adj);
                }
            }
        }

        return ancestor;
    }

    public static void main(String[] args) {
    }
}
