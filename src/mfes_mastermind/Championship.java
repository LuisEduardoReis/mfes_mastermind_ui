package mfes_mastermind;

import org.overture.codegen.runtime.*;

import java.util.*;


@SuppressWarnings("all")
public class Championship {
    private GameUtils.Settings settings;
    private VDMSet teams;

    public Championship(final GameUtils.Settings s) {
        cg_init_Championship_1(Utils.copy(s));
    }

    public Championship() {
    }

    public void cg_init_Championship_1(final GameUtils.Settings s) {
        if (!(Utils.equals(s, null))) {
            settings = Utils.copy(s);
        } else {
            settings = GameUtils.defaultSettings();
        }

        teams = SetUtil.set();

        return;
    }

    public void createTeam(final String name) {
        teams = SetUtil.union(Utils.copy(teams), SetUtil.set(new Team(name)));
    }

    public void removeTeam(final String name) {
        teams = SetUtil.diff(Utils.copy(teams), SetUtil.set(findTeam(name)));
    }

    public void createPlayer(final String team_name, final String player) {
        findTeam(team_name).addPlayer(player);
    }

    public void removePlayer(final String team_name, final String player) {
        findTeam(team_name).removePlayer(player);
    }

    public Game newGame(final String team_name, final String player) {
        Team team = findTeam(team_name);
        Game game = new Game(Utils.copy(settings), null, player);
        team.addGame(game);

        return game;
    }

    public VDMSet getTeams() {
        return Utils.copy(teams);
    }

    public GameUtils.Settings getSettings() {
        return Utils.copy(settings);
    }

    public Team findTeam(final String team) {
        {
            Team letBeStExp_1 = null;
            Team t = null;
            Boolean success_1 = false;
            VDMSet set_10 = Utils.copy(teams);

            for (Iterator iterator_10 = set_10.iterator();
                    iterator_10.hasNext() && !(success_1);) {
                t = ((Team) iterator_10.next());
                success_1 = Utils.equals(t.getName(), team);
            }

            if (!(success_1)) {
                throw new RuntimeException(
                    "Let Be St found no applicable bindings");
            }

            letBeStExp_1 = t;

            return letBeStExp_1;
        }
    }

    public VDMSeq getResults() {
        VDMSeq result = SeqUtil.seq();

        for (Iterator iterator_23 = teams.iterator(); iterator_23.hasNext();) {
            Team t = (Team) iterator_23.next();
            VDMSeq over_games = t.getOverGames();

            VDMMap mapCompResult_1 = MapUtil.map();
            VDMSet set_11 = SeqUtil.inds(over_games);

            for (Iterator iterator_11 = set_11.iterator();
                    iterator_11.hasNext();) {
                Number i = ((Number) iterator_11.next());
                mapCompResult_1 = MapUtil.munion(Utils.copy(mapCompResult_1),
                        MapUtil.map(
                            new Maplet(i,
                                ((Game) Utils.get(over_games, i)).getNeededTries())));
            }

            result = SeqUtil.conc(Utils.copy(result),
                    SeqUtil.seq(
                        new ChampionshipEntry(t.getName(),
                            t.getAveragePoints(), mapCompResult_1,
                            over_games.size())));
        }

        return GameUtils.mergesort_ce(Utils.copy(result));
    }

    public String toString() {
        return "Championship{" + "settings := " + Utils.toString(settings) +
        ", teams := " + Utils.toString(teams) + "}";
    }

    public static class ChampionshipEntry implements Record {
        public String team;
        public Number average_team_points;
        public VDMMap games;
        public Number num_games;

        public ChampionshipEntry(final String _team,
            final Number _average_team_points, final VDMMap _games,
            final Number _num_games) {
            team = (_team != null) ? _team : null;
            average_team_points = _average_team_points;
            games = (_games != null) ? Utils.copy(_games) : null;
            num_games = _num_games;
        }

        public boolean equals(final Object obj) {
            if (!(obj instanceof ChampionshipEntry)) {
                return false;
            }

            ChampionshipEntry other = ((ChampionshipEntry) obj);

            return (Utils.equals(team, other.team)) &&
            (Utils.equals(average_team_points, other.average_team_points)) &&
            (Utils.equals(games, other.games)) &&
            (Utils.equals(num_games, other.num_games));
        }

        public int hashCode() {
            return Utils.hashCode(team, average_team_points, games, num_games);
        }

        public ChampionshipEntry copy() {
            return new ChampionshipEntry(team, average_team_points, games,
                num_games);
        }

        public String toString() {
            return "mk_Championship`ChampionshipEntry" +
            Utils.formatFields(team, average_team_points, games, num_games);
        }
    }
}
