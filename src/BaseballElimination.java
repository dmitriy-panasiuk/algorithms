import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseballElimination {
    private int numberOfTeams;
    private boolean isEliminated;
    FordFulkerson ff;
    private Map<String, Integer> teams = new HashMap<String, Integer>();
    private String[] teamNames;
    private int[] wins;
    private int[] loses;
    private int[] remaining;
    private int[][] games;

    public BaseballElimination(String filename) {
        In in = new In(filename);
        String name;
        numberOfTeams = in.readInt();

        teamNames = new String[numberOfTeams];
        wins = new int[numberOfTeams];
        loses = new int[numberOfTeams];
        remaining = new int[numberOfTeams];
        games = new int[numberOfTeams][numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            name = in.readString();
            teamNames[i] = name;
            teams.put(name, i);
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

        ff = new FordFulkerson(network, 0, vertices - 1);

        for (FlowEdge edge : network.adj(0)) {
            if (edge.flow() != edge.capacity()) {
                return true;
            }
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
        List<String> result = new ArrayList<String>();
        int teamIndex = teams.get(team);
        if (isEliminated(team)) {
            for (int i = 1; i < numberOfTeams; i++) {
                if (ff.inCut(i)) {
                    result.add(teamNames[getTeamIndexReverse(i, teamIndex)]);
                }
            }
        } else {
            return null;
        }

        return result;
    }

    public static void main(String[] args) {
        BaseballElimination b = new BaseballElimination("teams5.txt");
        //System.out.println(b.isEliminated("Detroit"));
        System.out.println(b.certificateOfElimination("Detroit"));
    }
}
