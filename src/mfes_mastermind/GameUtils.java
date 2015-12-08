package mfes_mastermind;

import org.overture.codegen.runtime.*;

import java.util.*;


@SuppressWarnings("all")
public class GameUtils {
    public GameUtils() {
    }

    public static Settings defaultSettings() {
        return new Settings(4L, 8L, 12L, false);
    }

    public static Boolean validGuessForSettings(final VDMSeq sequence,
        final Settings settings) {
        Boolean andResult_23 = false;

        if (Utils.equals(sequence.size(), settings.sequenceLength)) {
            Boolean forAllExpResult_1 = true;
            VDMSet set_14 = SeqUtil.elems(Utils.copy(sequence));

            for (Iterator iterator_14 = set_14.iterator();
                    iterator_14.hasNext() && forAllExpResult_1;) {
                Number c = ((Number) iterator_14.next());
                forAllExpResult_1 = c.longValue() <= settings.numberColors.longValue();
            }

            if (forAllExpResult_1) {
                andResult_23 = true;
            }
        }

        return andResult_23;
    }

    public static Boolean validSequenceForSettings(final VDMSeq sequence,
        final Settings settings) {
        Boolean andResult_24 = false;

        if (validGuessForSettings(Utils.copy(sequence), Utils.copy(settings))) {
            Boolean orResult_3 = false;

            if (settings.allowDuplicates) {
                orResult_3 = true;
            } else {
                orResult_3 = !(hasDuplicates(Utils.copy(sequence)));
            }

            if (orResult_3) {
                andResult_24 = true;
            }
        }

        return andResult_24;
    }

    public static Boolean validFeedbackForSettings(final Feedback feedback,
        final Settings settings) {
        return Utils.equals(feedback.correct.longValue() +
            feedback.misplaced.longValue() + feedback.wrong.longValue(),
            settings.sequenceLength);
    }

    public static Feedback feedbackForSequence(final VDMSeq guess,
        final VDMSeq answer, final Settings settings) {
        VDMSeq seqCompResult_3 = SeqUtil.seq();
        VDMSet set_15 = SetUtil.range(1L, guess.size());

        for (Iterator iterator_15 = set_15.iterator(); iterator_15.hasNext();) {
            Number i = ((Number) iterator_15.next());

            if (Utils.equals(((Number) Utils.get(guess, i)),
                        ((Number) Utils.get(answer, i)))) {
                seqCompResult_3 = SeqUtil.conc(Utils.copy(seqCompResult_3),
                        SeqUtil.seq(((Number) Utils.get(answer, i))));
            }
        }

        VDMSeq correct_seq = Utils.copy(seqCompResult_3);
        Number correct_num = correct_seq.size();
        VDMSeq seqCompResult_4 = SeqUtil.seq();
        VDMSet set_16 = SeqUtil.elems(Utils.copy(answer));

        for (Iterator iterator_16 = set_16.iterator(); iterator_16.hasNext();) {
            Number c = ((Number) iterator_16.next());
            seqCompResult_4 = SeqUtil.conc(Utils.copy(seqCompResult_4),
                    SeqUtil.seq(min(count(Utils.copy(answer), c),
                            count(Utils.copy(guess), c)).longValue() -
                        count(Utils.copy(correct_seq), c).longValue()));
        }

        Number misplaced_num = sum(Utils.copy(seqCompResult_4));

        return new Feedback(correct_num, misplaced_num,
            guess.size() - correct_num.longValue() - misplaced_num.longValue());
    }

    public static Boolean hasDuplicates(final VDMSeq sequence) {
        return !(Utils.equals(sequence.size(),
            SeqUtil.elems(Utils.copy(sequence)).size()));
    }

    public static Number sum(final VDMSeq s) {
        if (Utils.empty(s)) {
            return 0L;
        } else {
            return ((Number) s.get(0)).longValue() +
            sum(SeqUtil.tail(Utils.copy(s))).longValue();
        }
    }

    private static Number sum_m(final VDMSeq s) {
        return s.size();
    }

    public static Number count(final VDMSeq s, final Object e) {
        if (Utils.empty(s)) {
            return 0L;
        } else {
            if (Utils.equals(((Object) s.get(0)), e)) {
                return 1L +
                count(SeqUtil.tail(Utils.copy(s)), ((Object) e)).longValue();
            } else {
                return count(SeqUtil.tail(Utils.copy(s)), ((Object) e));
            }
        }
    }

    private static Number count_m(final VDMSeq s, final Object e) {
        return s.size();
    }

    public static Number min(final Number a, final Number b) {
        if (a.longValue() < b.longValue()) {
            return a;
        } else {
            return b;
        }
    }

    public static VDMSeq mergesort_ce(final VDMSeq s) {
        if (Utils.empty(s)) {
            return SeqUtil.seq();
        } else {
            if (Utils.equals(s.size(), 1L)) {
                return Utils.copy(s);
            } else {
                VDMSeq s1 = SeqUtil.seq(Utils.copy(
                            ((Championship.ChampionshipEntry) s.get(0))));
                VDMSeq s2 = SeqUtil.tail(Utils.copy(s));

                return merge_aux_ce(mergesort_ce(Utils.copy(s1)),
                    mergesort_ce(Utils.copy(s2)));
            }
        }
    }

    private static Number mergesort_ce_m(final VDMSeq s) {
        return s.size();
    }

    private static VDMSeq merge_aux_ce(final VDMSeq s1, final VDMSeq s2) {
        if (Utils.empty(s1)) {
            return Utils.copy(s2);
        } else {
            if (Utils.empty(s2)) {
                return Utils.copy(s1);
            } else {
                if (((Championship.ChampionshipEntry) s1.get(0)).average_team_points.doubleValue() < ((Championship.ChampionshipEntry) s2.get(
                            0)).average_team_points.doubleValue()) {
                    return SeqUtil.conc(SeqUtil.seq(Utils.copy(
                                ((Championship.ChampionshipEntry) s1.get(0)))),
                        merge_aux_ce(SeqUtil.tail(Utils.copy(s1)),
                            Utils.copy(s2)));
                } else {
                    return SeqUtil.conc(SeqUtil.seq(Utils.copy(
                                ((Championship.ChampionshipEntry) s2.get(0)))),
                        merge_aux_ce(Utils.copy(s1),
                            SeqUtil.tail(Utils.copy(s2))));
                }
            }
        }
    }

    private static Number merge_aux_ce_m(final VDMSeq s1, final VDMSeq s2) {
        return s1.size() + s2.size();
    }

    public String toString() {
        return "GameUtils{}";
    }

    public static Boolean inv_Settings(final Settings s) {
        Boolean andResult_26 = false;

        Boolean orResult_5 = false;

        if (s.sequenceLength.longValue() <= s.numberColors.longValue()) {
            orResult_5 = true;
        } else {
            orResult_5 = s.allowDuplicates;
        }

        if (orResult_5) {
            if (s.numberColors.longValue() > 1L) {
                andResult_26 = true;
            }
        }

        return andResult_26;
    }

    public static class Settings implements Record {
        public Number sequenceLength;
        public Number numberColors;
        public Number numberTries;
        public Boolean allowDuplicates;

        public Settings(final Number _sequenceLength,
            final Number _numberColors, final Number _numberTries,
            final Boolean _allowDuplicates) {
            sequenceLength = _sequenceLength;
            numberColors = _numberColors;
            numberTries = _numberTries;
            allowDuplicates = _allowDuplicates;
        }

        public boolean equals(final Object obj) {
            if (!(obj instanceof Settings)) {
                return false;
            }

            Settings other = ((Settings) obj);

            return (Utils.equals(sequenceLength, other.sequenceLength)) &&
            (Utils.equals(numberColors, other.numberColors)) &&
            (Utils.equals(numberTries, other.numberTries)) &&
            (Utils.equals(allowDuplicates, other.allowDuplicates));
        }

        public int hashCode() {
            return Utils.hashCode(sequenceLength, numberColors, numberTries,
                allowDuplicates);
        }

        public Settings copy() {
            return new Settings(sequenceLength, numberColors, numberTries,
                allowDuplicates);
        }

        public String toString() {
            return "mk_GameUtils`Settings" +
            Utils.formatFields(sequenceLength, numberColors, numberTries,
                allowDuplicates);
        }
    }

    public static class Feedback implements Record {
        public Number correct;
        public Number misplaced;
        public Number wrong;

        public Feedback(final Number _correct, final Number _misplaced,
            final Number _wrong) {
            correct = _correct;
            misplaced = _misplaced;
            wrong = _wrong;
        }

        public boolean equals(final Object obj) {
            if (!(obj instanceof Feedback)) {
                return false;
            }

            Feedback other = ((Feedback) obj);

            return (Utils.equals(correct, other.correct)) &&
            (Utils.equals(misplaced, other.misplaced)) &&
            (Utils.equals(wrong, other.wrong));
        }

        public int hashCode() {
            return Utils.hashCode(correct, misplaced, wrong);
        }

        public Feedback copy() {
            return new Feedback(correct, misplaced, wrong);
        }

        public String toString() {
            return "mk_GameUtils`Feedback" +
            Utils.formatFields(correct, misplaced, wrong);
        }
    }
}
