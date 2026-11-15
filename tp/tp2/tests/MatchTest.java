package fr.poa.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MatchTest {

    private final Equipe fennecs = new Equipe("Fennecs");
    private final Equipe lynx = new Equipe("Lynx");
    private final Equipe pumas = new Equipe("Pumas");

    @Test
    void un_match_garde_ses_deux_equipes() {
        Match match = new Match(fennecs, lynx);
        assertSame(fennecs, match.domicile());
        assertSame(lynx, match.exterieur());
    }

    @Test
    void les_deux_equipes_sont_obligatoires() {
        assertThrows(NullPointerException.class, () -> new Match(null, lynx));
    }

    @Test
    void une_equipe_ne_joue_pas_contre_elle_meme() {
        assertThrows(IllegalArgumentException.class, () -> new Match(fennecs, fennecs));
        assertThrows(IllegalArgumentException.class, () -> new Match(fennecs, new Equipe("Fennecs")));
    }

    @Test
    void un_match_concerne_ses_deux_equipes() {
        Match match = new Match(fennecs, lynx);
        assertTrue(match.concerne(fennecs));
        assertTrue(match.concerne(new Equipe("Lynx")));
        assertFalse(match.concerne(pumas));
    }

    @Test
    void un_score_negatif_est_refuse() {
        Match match = new Match(fennecs, lynx);
        assertThrows(IllegalArgumentException.class, () -> new Resultat(match, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Resultat(match, 0, -1));
    }

    @Test
    void la_victoire_rapporte_3_points_et_la_defaite_aucun() {
        Resultat resultat = new Resultat(new Match(fennecs, lynx), 2, 1);
        assertEquals(3, resultat.pointsDe(fennecs));
        assertEquals(0, resultat.pointsDe(lynx));
    }

    @Test
    void le_match_nul_rapporte_1_point_a_chacun() {
        Resultat resultat = new Resultat(new Match(fennecs, lynx), 2, 2);
        assertEquals(1, resultat.pointsDe(fennecs));
        assertEquals(1, resultat.pointsDe(lynx));
    }

    @Test
    void la_difference_de_buts_de_l_equipe_qui_recoit() {
        Resultat resultat = new Resultat(new Match(fennecs, lynx), 5, 2);
        assertEquals(3, resultat.differenceDe(fennecs));
    }

    @Test
    void une_equipe_qui_ne_joue_pas_le_match_est_refusee() {
        Resultat resultat = new Resultat(new Match(fennecs, lynx), 2, 1);
        assertThrows(IllegalArgumentException.class, () -> resultat.pointsDe(pumas));
    }
}
