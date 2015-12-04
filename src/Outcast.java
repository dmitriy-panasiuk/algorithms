public class Outcast {
    private WordNet net;

    public Outcast(WordNet wordnet) {
        net = wordnet;
    }

    public String outcast(String[] nouns) {
        int maxDistance = 0, currentDistance;
        String outcast = "";

        for (String noun : nouns) {
            currentDistance = 0;
            for (String noun1 : nouns) {
                currentDistance += net.distance(noun, noun1);
            }
            if (currentDistance > maxDistance) {
                maxDistance = currentDistance;
                outcast = noun;
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
