import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class BaseballElimination {
    private int numberOfTeams;
    private Map<String, Integer> teams = new HashMap<String, Integer>();
    private int[] wins;
    private int[] loses;
    private int[] remaining;
    private int[][] games;

    public BaseballElimination(String filename) {
        In in = new In(filename);
        numberOfTeams = in.readInt();

        wins = new int[numberOfTeams];
        loses = new int[numberOfTeams];
        remaining = new int[numberOfTeams];
        games = new int[numberOfTeams][numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            teams.put(in.readString(), i);
            wins[i] = in.readInt();
            loses[i] = in.readInt();
            remaining[i] = in.readInt();
            for (int j = 0; j < numberOfTeams; j++) {
                games[i][j] = in.readInt();
            }
        }
    }

    public int numberOfTeams() {
        return this.numberOfTeams;
    }

    public Iterable<String> teams() {
        return teams.keySet();
    }

    public int wins(String team) {
        return wins[teams.get(team)];
    }

    public int losses(String team) {
        return loses[teams.get(team)];
    }

    public int remaining(String team) {
        return remaining[teams.get(team)];
    }

    public int against(String team1, String team2) {
        return games[teams.get(team1)][teams.get(team2)];
    }

    public boolean isEliminated(String team) {
        int teamIndex = teams.get(team);
        for (int i = 0; i < numberOfTeams; i++) {
            if (wins[teamIndex] + remaining[teamIndex] < wins[i]) {
                return true;
            }
        }

        FlowNetwork network = new FlowNetwork((numberOfTeams * numberOfTeams - numberOfTeams) / 2 + 2 + numberOfTeams);
        for (int i = 1; i <= (numberOfTeams * numberOfTeams - numberOfTeams) / 2; i++) {
            network.addEdge(new FlowEdge(0, i, 0.0));
        }
        return false;
    }

    private double gameCapacity(int n) {

    }

    public Iterable<String> certificateOfElimination(String team) {
        throw new NotImplementedException();
    }

    public static void main(String[] args) {
        BaseballElimination b = new BaseballElimination("teams4.txt");
        System.out.println(b.wins("New_York"));
        System.out.println(b.isEliminated("New_York"));
    }
}
