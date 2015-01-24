public class Outcast {
    private WordNet net;

    public Outcast(WordNet wordnet) {
        net = wordnet;
    }

    public String outcast(String[] nouns) {
        int maxDistance = 0, currentDistance;
        String outcast = "";

        for (int i = 0; i < nouns.length; i++) {
            currentDistance = 0;
            for (int j = 0; j < nouns.length; j++) {
                currentDistance += net.distance(nouns[i], nouns[j]);
            }
            if (currentDistance > maxDistance) {
                maxDistance = currentDistance;
                outcast = nouns[i];
            }
        }

        return outcast;
    }

    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        Outcast out = new Outcast(wordNet);
        String[] nouns = new String[]{"water", "soda", "bed", "orange_juice", "milk", "apple_juice", "tea", "coffee"};
        System.out.println(out.outcast(nouns));
    }
}
