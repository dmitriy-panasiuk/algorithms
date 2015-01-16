import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class WordNet {
    private List<String> synsetsList = new ArrayList<String>();
    private Map<String, Set<Integer>> nouns = new HashMap<String, Set<Integer>>();
    private Digraph graph;
    private SAP sap;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new NullPointerException();
        In inSyn = new In(synsets);
        In inHyper = new In(hypernyms);
        String line;
        String[] data;
        int vertixCount = 0;

        while (inSyn.hasNextLine()) {
            line = inSyn.readLine();
            data = line.split(",");
            synsetsList.add(data[1]);
            for (String noun : data[1].split(" ")) {
                if (nouns.containsKey(noun)) {
                    Set<Integer> tempSet = nouns.get(noun);
                    tempSet.add(Integer.parseInt(data[0]));
                } else {
                    Set<Integer> tempSet = new HashSet<Integer>();
                    tempSet.add(Integer.parseInt(data[0]));
                    nouns.put(noun, tempSet);
                }
            }
            vertixCount++;
        }

        graph = new Digraph(vertixCount);
        while (inHyper.hasNextLine()) {
            line = inHyper.readLine();
            data = line.split(",");
            for (int i = 1; i < data.length; i++) {
                graph.addEdge(Integer.parseInt(data[0]), Integer.parseInt(data[i]));
            }
        }

    }

    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null) throw new NullPointerException();
        return nouns.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new NullPointerException();
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new NullPointerException();
        return synsetsList.get(sap.ancestor(nouns.get(nounA), nouns.get(nounB)));
    }

    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println();
    }
}
