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
        int pairs = (numberOfTeams * numberOfTeams - numberOfTeams) / 2 - numberOfTeams + 1;
        int vertices = pairs + 2 + numberOfTeams - 1;
        int i = 0, j, k = 1;
        for (int ii = 0; ii < numberOfTeams; ii++) {
            if (wins[teamIndex] + remaining[teamIndex] < wins[ii]) {
                return true;
            }
        }

        FlowNetwork network = new FlowNetwork(vertices);

        while (i < numberOfTeams) {
            if (i == teamIndex) {
                i++;
                continue;
            }
            j = 0;
            while (j < numberOfTeams) {
                if (j == teamIndex) {
                    j++;
                    continue;
                }
                if (i >= j) {
                    j++;
                    continue;
                }
                network.addEdge(new FlowEdge(0, k, games[i][j]));
                network.addEdge(new FlowEdge(k, getTeamIndex(teamIndex, i), Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(k, getTeamIndex(teamIndex, j), Double.POSITIVE_INFINITY));
                k++;
                j++;
            }
            i++;
        }

        for (int jj = pairs + 1; jj < vertices - 1; jj++) {
            network.addEdge(new FlowEdge(jj, vertices - 1, wins[teamIndex] + remaining[teamIndex] - wins[getTeamIndexReverse(jj, teamIndex)]));
        }

        return false;
    }

    private int getTeamIndex(int teamIndex, int index) {
        int pairs = (numberOfTeams * numberOfTeams - numberOfTeams) / 2 - numberOfTeams + 1;
        if (index < teamIndex) {
            return pairs + index + 1;
        }
        return pairs + index;
    }

    private int getTeamIndexReverse(int index, int teamIndex) {
        int pairs = (numberOfTeams * numberOfTeams - numberOfTeams) / 2 - numberOfTeams + 1;
        if (index - pairs - 1 >= teamIndex) {
            return index - pairs;
        }
        return index - pairs - 1;
    }

    public Iterable<String> certificateOfElimination(String team) {
        throw new NotImplementedException();
    }

    public static void main(String[] args) {
        BaseballElimination b = new BaseballElimination("teams5.txt");
        System.out.println(b.wins("New_York"));
        System.out.println(b.isEliminated("Detroit"));
    }
}
