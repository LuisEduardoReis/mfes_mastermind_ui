package mfes_mastermind;

import org.overture.codegen.runtime.*;

import java.util.*;


@SuppressWarnings("all")
public class Team {
    private VDMSet players;
    private String name;
    private VDMSeq games;

    public Team(final String n) {
        cg_init_Team_1(n);
    }

    public Team() {
    }

    public void cg_init_Team_1(final String n) {
        name = n;
        players = SetUtil.set();
        games = SeqUtil.seq();

        return;
    }

    public void clear() {
        VDMSet atomicTmp_3 = SetUtil.set();
        VDMSeq atomicTmp_4 = SeqUtil.seq();
        players = Utils.copy(atomicTmp_3);
        games = Utils.copy(atomicTmp_4);
    }

    public void addPlayer(final String p) {
        players = SetUtil.union(Utils.copy(players), SetUtil.set(p));
    }

    public void removePlayer(final String p) {
        VDMSet atomicTmp_5 = SetUtil.diff(Utils.copy(players), SetUtil.set(p));
        VDMSeq seqCompResult_5 = SeqUtil.seq();
        VDMSet set_19 = SeqUtil.inds(games);

        for (Iterator iterator_20 = set_19.iterator(); iterator_20.hasNext();) {
            Number i = ((Number) iterator_20.next());

            if (!(Utils.equals(((Game) Utils.get(games, i)).getPlayer(), p))) {
                seqCompResult_5 = SeqUtil.conc(Utils.copy(seqCompResult_5),
                        SeqUtil.seq(((Game) Utils.get(games, i))));
            }
        }

        VDMSeq atomicTmp_6 = Utils.copy(seqCompResult_5);
        players = Utils.copy(atomicTmp_5);
        games = Utils.copy(atomicTmp_6);
    }

    public void addGame(final Game g) {
        games = SeqUtil.conc(Utils.copy(games), SeqUtil.seq(g));
    }

    public VDMSet getPlayers() {
        return Utils.copy(players);
    }

    public String getName() {
        return name;
    }

    public VDMSeq getGames() {
        return Utils.copy(games);
    }

    public VDMSeq getOverGames() {
        VDMSeq seqCompResult_6 = SeqUtil.seq();
        VDMSet set_20 = SeqUtil.inds(games);

        for (Iterator iterator_21 = set_20.iterator(); iterator_21.hasNext();) {
            Number i = ((Number) iterator_21.next());

            if (((Game) Utils.get(games, i)).over()) {
                seqCompResult_6 = SeqUtil.conc(Utils.copy(seqCompResult_6),
                        SeqUtil.seq(((Game) Utils.get(games, i))));
            }
        }

        return Utils.copy(seqCompResult_6);
    }

    public Number getAveragePoints() {
        VDMSeq over_games = getOverGames();

        if (Utils.empty(over_games)) {
            return 0L;
        } else {
            VDMSeq seqCompResult_7 = SeqUtil.seq();
            VDMSet set_21 = SeqUtil.inds(over_games);

            for (Iterator iterator_22 = set_21.iterator();
                    iterator_22.hasNext();) {
                Number i = ((Number) iterator_22.next());
                seqCompResult_7 = SeqUtil.conc(Utils.copy(seqCompResult_7),
                        SeqUtil.seq(
                            ((Game) Utils.get(over_games, i)).getNeededTries()));
            }

            Number totalPoints = GameUtils.sum(Utils.copy(seqCompResult_7));

            return Utils.divide((1.0 * totalPoints.longValue()), games.size());
        }
    }

    public String toString() {
        return "Team{" + "players := " + Utils.toString(players) +
        ", name := " + Utils.toString(name) + ", games := " +
        Utils.toString(games) + "}";
    }
}
