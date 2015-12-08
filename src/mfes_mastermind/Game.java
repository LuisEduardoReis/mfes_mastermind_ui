package mfes_mastermind;

import org.overture.codegen.runtime.*;

import java.util.*;


@SuppressWarnings("all")
public class Game {
    private GameUtils.Settings settings;
    private VDMSeq answer;
    private VDMSeq guesses;
    private VDMSeq feedbacks;
    private String player;

    public Game(final GameUtils.Settings s, final VDMSeq a, final String p) {
        cg_init_Game_1(Utils.copy(s), Utils.copy(a), p);
    }

    public Game() {
    }

    public void cg_init_Game_1(final GameUtils.Settings s, final VDMSeq a,
        final String p) {
        if (!(Utils.equals(s, null))) {
            settings = Utils.copy(s);
        } else {
            settings = GameUtils.defaultSettings();
        }

        if (!(Utils.equals(a, null))) {
            answer = Utils.copy(a);
        } else {
            answer = randomSequence();
        }

        guesses = SeqUtil.seq();
        feedbacks = SeqUtil.seq();
        player = p;

        return;
    }

    public VDMSeq randomSequence() {
        if (settings.allowDuplicates) {
            VDMSeq seqCompResult_1 = SeqUtil.seq();
            VDMSet set_12 = SetUtil.range(1L, (long) settings.sequenceLength);

            for (Iterator iterator_12 = set_12.iterator();
                    iterator_12.hasNext();) {
                Number i = ((Number) iterator_12.next());

                if (i.longValue() > 0L) {
                    seqCompResult_1 = SeqUtil.conc(Utils.copy(seqCompResult_1),
                            SeqUtil.seq(MATH.rand(settings.numberColors)
                                            .longValue() + 1L));
                }
            }

            return Utils.copy(seqCompResult_1);
        } else {
            VDMSeq res = SeqUtil.seq();
            VDMSet colorset = SetUtil.range(1L, (long) settings.numberColors);
            VDMSeq seqCompResult_2 = SeqUtil.seq();
            VDMSet set_13 = Utils.copy(colorset);

            for (Iterator iterator_13 = set_13.iterator();
                    iterator_13.hasNext();) {
                Number c = ((Number) iterator_13.next());
                seqCompResult_2 = SeqUtil.conc(Utils.copy(seqCompResult_2),
                        SeqUtil.seq(c));
            }

            VDMSeq colors = Utils.copy(seqCompResult_2);
            Boolean whileCond_1 = true;

            while (whileCond_1) {
                Boolean andResult_15 = false;

                if (res.size() < settings.sequenceLength.longValue()) {
                    if (!(Utils.empty(colors))) {
                        andResult_15 = true;
                    }
                }

                whileCond_1 = andResult_15;

                if (!(whileCond_1)) {
                    break;
                }

                {
                    Number ri = MATH.rand(colors.size()).longValue() + 1L;
                    res = SeqUtil.conc(Utils.copy(res),
                            SeqUtil.seq(((Number) Utils.get(colors, ri))));

                    colors = SeqUtil.conc(SeqUtil.subSeq(Utils.copy(colors),
                                1L, ri.longValue() - 1L),
                            SeqUtil.subSeq(Utils.copy(colors),
                                ri.longValue() + 1L, colors.size()));
                }
            }

            return Utils.copy(res);
        }
    }

    public void makeGuess(final VDMSeq guess) {
        GameUtils.Feedback feedback = GameUtils.feedbackForSequence(Utils.copy(
                    guess), Utils.copy(answer), Utils.copy(settings));
        VDMSeq atomicTmp_1 = SeqUtil.conc(Utils.copy(guesses),
                SeqUtil.seq(Utils.copy(guess)));

        VDMSeq atomicTmp_2 = SeqUtil.conc(Utils.copy(feedbacks),
                SeqUtil.seq(Utils.copy(feedback)));
        guesses = Utils.copy(atomicTmp_1);
        feedbacks = Utils.copy(atomicTmp_2);
    }

    public Boolean won() {
        Boolean andResult_20 = false;

        if (feedbacks.size() > 0L) {
            GameUtils.Feedback lf = getLastFeedback();

            Boolean andResult_21 = false;

            if (Utils.equals(lf.misplaced, 0L)) {
                if (Utils.equals(lf.wrong, 0L)) {
                    andResult_21 = true;
                }
            }

            if (andResult_21) {
                andResult_20 = true;
            }
        }

        return andResult_20;
    }

    public Boolean over() {
        Boolean orResult_2 = false;

        if (Utils.equals(guesses.size(), settings.numberTries)) {
            orResult_2 = true;
        } else {
            orResult_2 = won();
        }

        return orResult_2;
    }

    public Boolean lost() {
        Boolean andResult_22 = false;

        if (over()) {
            if (!(won())) {
                andResult_22 = true;
            }
        }

        return andResult_22;
    }

    public GameUtils.Settings getSettings() {
        return Utils.copy(settings);
    }

    public VDMSeq getAnswer() {
        return Utils.copy(answer);
    }

    public VDMSeq getGuesses() {
        return Utils.copy(guesses);
    }

    public VDMSeq getFeedbacks() {
        return Utils.copy(feedbacks);
    }

    public VDMSeq getLastGuess() {
        return Utils.copy(((VDMSeq) Utils.get(guesses, guesses.size())));
    }

    public GameUtils.Feedback getLastFeedback() {
        return Utils.copy(((GameUtils.Feedback) Utils.get(feedbacks,
                feedbacks.size())));
    }

    public Number getNeededTries() {
        return guesses.size();
    }

    public String getPlayer() {
        return player;
    }

    public String toString() {
        return "Game{" + "settings := " + Utils.toString(settings) +
        ", answer := " + Utils.toString(answer) + ", guesses := " +
        Utils.toString(guesses) + ", feedbacks := " +
        Utils.toString(feedbacks) + ", player := " + Utils.toString(player) +
        "}";
    }
}
