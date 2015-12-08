package mfes_mastermind;

import org.overture.codegen.runtime.*;

import java.util.*;


@SuppressWarnings("all")
public class TestGame extends MyTestCase {
    private Game ct_g = new Game(null, null, "ctplayer");
    private Team ct_t = new Team("ctteam");
    private Championship ct_c = new Championship(null);

    public TestGame() {
    }

    public void testSettings() {
        GameUtils.Settings s = null;
        s = GameUtils.defaultSettings();
        s = new GameUtils.Settings(4L, 3L, 12L, true);
    }

    public void testGame() {
        Game g = null;
        VDMSeq cs = null;
        String p = "player1";

        for (Iterator iterator_24 = SetUtil.set(null,
                    new GameUtils.Settings(4L, 3L, 12L, true)).iterator();
                iterator_24.hasNext();) {
            Object s = (Object) iterator_24.next();

            for (Iterator iterator_25 = SetUtil.set(null,
                        new Game(((GameUtils.Settings) s), null, "p").randomSequence())
                                               .iterator();
                    iterator_25.hasNext();) {
                VDMSeq a = (VDMSeq) iterator_25.next();
                g = new Game(((GameUtils.Settings) s), Utils.copy(a), p);
                g.makeGuess(SeqUtil.seq(1L, 1L, 1L, 1L));

                Boolean whileCond_2 = true;

                while (whileCond_2) {
                    whileCond_2 = g.getGuesses().size() < (g.getSettings().numberTries.longValue() -
                        1L);

                    if (!(whileCond_2)) {
                        break;
                    }

                    cs = g.randomSequence();

                    if (!(Utils.equals(cs, g.getAnswer()))) {
                        g.makeGuess(Utils.copy(cs));
                        super.assertEqual(g.over(), false);
                        super.assertEqual(g.won(), false);
                    }
                }

                g.makeGuess(g.getAnswer());
                super.assertEqual(g.over(), true);
                super.assertEqual(g.won(), true);
                super.assertEqual(g.getNeededTries(),
                    g.getSettings().numberTries);
                super.assertEqual(g.getGuesses().size(),
                    g.getSettings().numberTries);
                super.assertEqual(g.getFeedbacks().size(),
                    g.getSettings().numberTries);
                g = new Game(((GameUtils.Settings) s), null, p);

                Boolean whileCond_3 = true;

                while (whileCond_3) {
                    whileCond_3 = g.getGuesses().size() < g.getSettings().numberTries.longValue();

                    if (!(whileCond_3)) {
                        break;
                    }

                    cs = g.randomSequence();

                    if (!(Utils.equals(cs, g.getAnswer()))) {
                        g.makeGuess(Utils.copy(cs));
                        super.assertEqual(g.won(), false);
                    }
                }

                super.assertEqual(g.over(), true);
                super.assertEqual(g.lost(), true);
                super.assertEqual(g.getNeededTries(),
                    g.getSettings().numberTries);
            }
        }
    }

    public void testFeedback() {
        GameUtils.Settings s = GameUtils.defaultSettings();
        s.allowDuplicates = true;
        super.assertEqual(GameUtils.feedbackForSequence(SeqUtil.seq(1L, 2L, 3L,
                    4L), SeqUtil.seq(1L, 2L, 3L, 4L), Utils.copy(s)),
            new GameUtils.Feedback(4L, 0L, 0L));
        super.assertEqual(GameUtils.feedbackForSequence(SeqUtil.seq(1L, 1L, 2L,
                    2L), SeqUtil.seq(1L, 2L, 3L, 4L), Utils.copy(s)),
            new GameUtils.Feedback(1L, 1L, 2L));
        super.assertEqual(GameUtils.feedbackForSequence(SeqUtil.seq(1L, 1L, 1L,
                    2L), SeqUtil.seq(1L, 1L, 2L, 3L), Utils.copy(s)),
            new GameUtils.Feedback(2L, 1L, 1L));
        super.assertEqual(GameUtils.feedbackForSequence(SeqUtil.seq(4L, 3L, 2L,
                    1L), SeqUtil.seq(1L, 2L, 3L, 4L), Utils.copy(s)),
            new GameUtils.Feedback(0L, 4L, 0L));
    }

    public void testTeam() {
        Team t = new Team("t1");
        String p1 = "p1";
        String p2 = "p2";
        Game g = new Game(GameUtils.defaultSettings(), null, p1);
        t.addPlayer(p1);
        super.assertEqual(SetUtil.set(p1), t.getPlayers());
        t.addGame(g);
        super.assertEqual(SeqUtil.seq(g), t.getGames());
        t.addPlayer(p2);
        super.assertEqual(SetUtil.set(p1, p2), t.getPlayers());
        t.removePlayer(p1);
        super.assertEqual(SetUtil.set(p2), t.getPlayers());
        super.assertEqual(SeqUtil.seq(), t.getGames());
        t.addPlayer(p1);
        t.addGame(g);
        t.clear();
        super.assertEqual(SetUtil.set(), t.getPlayers());
        super.assertEqual(SeqUtil.seq(), t.getGames());
    }

    public void testTeamAveragePoints() {
        Team t = new Team("team1");
        String p1 = "player1";
        Game g1 = new Game(null, null, p1);
        Game g2 = new Game(null, null, p1);
        VDMSeq cs = null;
        super.assertEqual(0L, t.getAveragePoints());
        t.addPlayer(p1);
        t.addGame(g1);
        super.assertEqual(SeqUtil.seq(g1), t.getGames());
        super.assertEqual(SeqUtil.seq(), t.getOverGames());
        g1.makeGuess(g1.getAnswer());
        super.assertTrue(g1.won());
        super.assertEqual(SeqUtil.seq(g1), t.getOverGames());
        super.assertEqual(1L, t.getAveragePoints());
        t.addGame(g2);
        super.assertEqual(SeqUtil.seq(g1, g2), t.getGames());
        super.assertEqual(SeqUtil.seq(g1), t.getOverGames());

        Boolean whileCond_4 = true;

        while (whileCond_4) {
            whileCond_4 = g2.getGuesses().size() < 1L;

            if (!(whileCond_4)) {
                break;
            }

            cs = g2.randomSequence();

            if (!(Utils.equals(cs, g2.getAnswer()))) {
                g2.makeGuess(Utils.copy(cs));
            }
        }

        g2.makeGuess(g2.getAnswer());
        super.assertEqual(1.5, t.getAveragePoints());
    }

    public void testChampionship() {
        for (Iterator iterator_26 = SetUtil.set(null,
                    GameUtils.defaultSettings()).iterator();
                iterator_26.hasNext();) {
            Object s = (Object) iterator_26.next();
            Championship c = new Championship(((GameUtils.Settings) s));
            Team t1 = null;
            Team t2 = null;
            Team t3 = null;
            Championship.ChampionshipEntry result_champ_entry1 = null;
            Championship.ChampionshipEntry result_champ_entry2 = null;
            Championship.ChampionshipEntry result_champ_entry3 = null;
            VDMSeq cs = null;
            Game g1 = null;
            Game g2 = null;
            Game g3 = null;
            Game g4 = null;
            c.createTeam("t1");
            super.assertEqual(1L, c.getTeams().size());
            c.createTeam("t2");
            super.assertEqual(2L, c.getTeams().size());
            c.createTeam("t3");
            super.assertEqual(3L, c.getTeams().size());
            t1 = c.findTeam("t1");
            t2 = c.findTeam("t2");
            t3 = c.findTeam("t3");
            c.createPlayer("t1", "p1");
            super.assertTrue(SetUtil.inSet("p1", t1.getPlayers()));
            g1 = c.newGame("t1", "p1");
            super.assertEqual(SeqUtil.seq(g1), t1.getGames());
            super.assertEqual("p1",
                ((Game) Utils.get(t1.getGames(), 1L)).getPlayer());

            Boolean whileCond_5 = true;

            while (whileCond_5) {
                whileCond_5 = g1.getGuesses().size() < 3L;

                if (!(whileCond_5)) {
                    break;
                }

                cs = g1.randomSequence();

                if (!(Utils.equals(cs, g1.getAnswer()))) {
                    g1.makeGuess(Utils.copy(cs));
                }
            }

            g1.makeGuess(g1.getAnswer());
            c.createPlayer("t2", "p2");
            super.assertTrue(SetUtil.inSet("p2", t2.getPlayers()));
            g2 = c.newGame("t2", "p2");
            super.assertEqual(SeqUtil.seq(g2), t2.getGames());
            super.assertEqual("p2",
                ((Game) Utils.get(t2.getGames(), 1L)).getPlayer());
            g2.makeGuess(g2.getAnswer());
            c.createPlayer("t3", "p3");
            super.assertTrue(SetUtil.inSet("p3", t3.getPlayers()));
            g3 = c.newGame("t3", "p3");
            super.assertEqual(SeqUtil.seq(g3), t3.getGames());
            super.assertEqual("p3",
                ((Game) Utils.get(t3.getGames(), 1L)).getPlayer());

            Boolean whileCond_6 = true;

            while (whileCond_6) {
                whileCond_6 = g3.getGuesses().size() < 2L;

                if (!(whileCond_6)) {
                    break;
                }

                cs = g3.randomSequence();

                if (!(Utils.equals(cs, g3.getAnswer()))) {
                    g3.makeGuess(Utils.copy(cs));
                }
            }

            g3.makeGuess(g3.getAnswer());
            result_champ_entry1 = new Championship.ChampionshipEntry(t1.getName(),
                    t1.getAveragePoints(), MapUtil.map(new Maplet(1L, 4L)), 1L);
            result_champ_entry2 = new Championship.ChampionshipEntry(t2.getName(),
                    t2.getAveragePoints(), MapUtil.map(new Maplet(1L, 1L)), 1L);
            result_champ_entry3 = new Championship.ChampionshipEntry(t3.getName(),
                    t3.getAveragePoints(), MapUtil.map(new Maplet(1L, 3L)), 1L);

            {
                VDMSeq r = c.getResults();
                VDMSeq e = SeqUtil.seq(Utils.copy(result_champ_entry2),
                        Utils.copy(result_champ_entry3),
                        Utils.copy(result_champ_entry1));

                for (Iterator iterator_27 = SeqUtil.inds(e).iterator();
                        iterator_27.hasNext();) {
                    Number i = (Number) iterator_27.next();
                    super.assertEqual(((Championship.ChampionshipEntry) Utils.get(
                            e, i)).team,
                        ((Championship.ChampionshipEntry) Utils.get(r, i)).team);
                    super.assertEqual(((Championship.ChampionshipEntry) Utils.get(
                            e, i)).average_team_points,
                        ((Championship.ChampionshipEntry) Utils.get(r, i)).average_team_points);
                    super.assertEqual(MapUtil.dom(Utils.copy(
                                ((Championship.ChampionshipEntry) Utils.get(e, i)).games))
                                             .size(),
                        MapUtil.dom(Utils.copy(
                                ((Championship.ChampionshipEntry) Utils.get(r, i)).games))
                               .size());

                    for (Iterator iterator_28 = MapUtil.dom(Utils.copy(
                                    ((Championship.ChampionshipEntry) Utils.get(
                                        e, i)).games)).iterator();
                            iterator_28.hasNext();) {
                        Number gi = (Number) iterator_28.next();
                        super.assertEqual(((Number) Utils.get(
                                ((Championship.ChampionshipEntry) Utils.get(e, i)).games,
                                gi)),
                            ((Number) Utils.get(
                                ((Championship.ChampionshipEntry) Utils.get(r, i)).games,
                                gi)));
                    }

                    super.assertEqual(((Championship.ChampionshipEntry) Utils.get(
                            e, i)).num_games,
                        ((Championship.ChampionshipEntry) Utils.get(r, i)).num_games);
                }
            }

            c.createPlayer("t2", "p4");
            g4 = c.newGame("t2", "p4");
            c.removePlayer("t2", "p2");
            super.assertTrue(!(SetUtil.inSet("p2", t2.getPlayers())));
            c.removeTeam("t2");
            super.assertTrue(!(SetUtil.inSet(t2, c.getTeams())));
        }
    }

    public void testAll() {
        testSettings();
        testGame();
        testFeedback();
        testTeam();
        testTeamAveragePoints();
        testChampionship();
    }

    private static Number real_ident(final Number r) {
        return r;
    }

    public String toString() {
        return "TestGame{" + "ct_g := " + Utils.toString(ct_g) + ", ct_t := " +
        Utils.toString(ct_t) + ", ct_c := " + Utils.toString(ct_c) + "}";
    }
}
