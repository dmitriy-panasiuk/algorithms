import java.util.*;

public class SAP {
    private Digraph graph;
    public SAP(Digraph G) {
        if (G == null) throw new NullPointerException();
        this.graph = G;
    }

    public int length(int v, int w) {
        if (v < 0 || v > graph.V() - 1) throw new NullPointerException();
        if (w < 0 || w > graph.V() - 1) throw new NullPointerException();
        int min = -1;
        BreadthFirstDirectedPaths sw = new BreadthFirstDirectedPaths(graph, w);
        BreadthFirstDirectedPaths sv = new BreadthFirstDirectedPaths(graph, v);
        Queue<Integer> vertices = new Queue<Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        vertices.enqueue(v);
        while (!vertices.isEmpty()) {
            Integer vertex = vertices.dequeue();
            visited.add(vertex);
            if (sw.hasPathTo(vertex)) {
                if (min == -1) min = sw.distTo(vertex) + sv.distTo(vertex);
                else if(sw.distTo(vertex) + sv.distTo(vertex) < min) {
                    min = sw.distTo(vertex) + sv.distTo(vertex);
                }
            }
            for(Integer adj : graph.adj(vertex)) {
                if (!visited.contains(adj)) vertices.enqueue(adj);
            }
        }

        return min;
    }

    public int ancestor(int v, int w) {
        if (v < 0 || v > graph.V() - 1) throw new NullPointerException();
        if (w < 0 || w > graph.V() - 1) throw new NullPointerException();
        int min = -1;
        int ancestor = -1;
        BreadthFirstDirectedPaths sw = new BreadthFirstDirectedPaths(graph, w);
        BreadthFirstDirectedPaths sv = new BreadthFirstDirectedPaths(graph, v);
        Queue<Integer> vertices = new Queue<Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        vertices.enqueue(v);
        while (!vertices.isEmpty()) {
            Integer vertex = vertices.dequeue();
            visited.add(vertex);
            if (sw.hasPathTo(vertex)) {
                if (min == -1) {
                    min = sw.distTo(vertex) + sv.distTo(vertex);
                    ancestor = vertex;
                }
                else if(sw.distTo(vertex) + sv.distTo(vertex) < min) {
                    min = sw.distTo(vertex) + sv.distTo(vertex);
                    ancestor = vertex;
                }
            }
            for(Integer adj : graph.adj(vertex)) {
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
        Queue<Integer> vertices = new Queue<Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        for (Integer vertex : v) {
            vertices.enqueue(vertex);
            visited.add(vertex);
        }
        while (!vertices.isEmpty()) {
            Integer vertex = vertices.dequeue();
            if (sw.hasPathTo(vertex)) {
                if (min == -1) min = sw.distTo(vertex) + sv.distTo(vertex);
                else if(sw.distTo(vertex) + sv.distTo(vertex) < min) {
                    min = sw.distTo(vertex) + sv.distTo(vertex);
                }
            }
            for(Integer adj : graph.adj(vertex)) {
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
        Queue<Integer> vertices = new Queue<Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        for (Integer vertex : v) {
            vertices.enqueue(vertex);
            visited.add(vertex);
        }
        while (!vertices.isEmpty()) {
            Integer vertex = vertices.dequeue();
            if (sw.hasPathTo(vertex)) {
                if (min == -1) {
                    min = sw.distTo(vertex) + sv.distTo(vertex);
                    ancestor = vertex;
                }
                else if(sw.distTo(vertex) + sv.distTo(vertex) < min) {
                    min = sw.distTo(vertex) + sv.distTo(vertex);
                    ancestor = vertex;
                }
            }
            for(Integer adj : graph.adj(vertex)) {
                if (!visited.contains(adj)) {
                    vertices.enqueue(adj);
                    visited.add(adj);
                }
            }
        }

        return ancestor;
    }

    public static void main(String[] args) {
        In in = new In("digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Integer[] a = new Integer[] {7, 2};
        Integer[] b = new Integer[] {4, 5};
        System.out.println(sap.length(Arrays.asList(a), Arrays.asList(b)));
        System.out.println(sap.ancestor(1, 6));
    }
}