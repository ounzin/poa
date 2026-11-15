package fr.poa.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TournoiProgrammeTest {

    private final Equipe fennecs = equipe("Fennecs", "Inès", "Hugo");
    private final Equipe lynx = equipe("Lynx", "Léa", "Malik");
    private final Equipe orques = equipe("Orques", "Zoé", "Tom");
    private final Equipe pumas = equipe("Pumas", "Nora", "Sami");

    private static Equipe equipe(String nom, String joueur1, String joueur2) {
        Equipe equipe = new Equipe(nom);
        equipe.ajouterJoueur(joueur1);
        equipe.ajouterJoueur(joueur2);
        return equipe;
    }

    private final Tournoi tournoi = new Tournoi();

    @BeforeEach
    void inscrireQuatreEquipes() {
        tournoi.inscrire(pumas);
        tournoi.inscrire(lynx);
        tournoi.inscrire(fennecs);
        tournoi.inscrire(orques);
    }

    @Test
    void un_nouveau_tournoi_n_a_aucun_match() {
        assertTrue(tournoi.matchsAJouer().isEmpty());
        assertTrue(tournoi.resultats().isEmpty());
    }

    @Test
    void les_matchs_programmes_attendent_dans_l_ordre() {
        tournoi.programmer(fennecs, lynx);
        tournoi.programmer(orques, pumas);
        assertEquals(List.of(new Match(fennecs, lynx), new Match(orques, pumas)), tournoi.matchsAJouer());
    }

    @Test
    void le_premier_match_programme_est_joue_en_premier() {
        tournoi.programmer(fennecs, lynx);
        tournoi.programmer(orques, pumas);
        Resultat resultat = tournoi.jouerProchainMatch(2, 1);
        assertEquals(new Match(fennecs, lynx), resultat.match());
        assertEquals(List.of(new Match(orques, pumas)), tournoi.matchsAJouer());
    }

    @Test
    void jouer_un_match_enregistre_son_resultat() {
        tournoi.programmer(fennecs, lynx);
        Resultat resultat = tournoi.jouerProchainMatch(2, 1);
        assertEquals(new Resultat(new Match(fennecs, lynx), 2, 1), resultat);
        assertEquals(List.of(resultat), tournoi.resultats());
        assertTrue(tournoi.matchsAJouer().isEmpty());
    }

    @Test
    void les_resultats_sont_dans_l_ordre_ou_les_matchs_sont_joues() {
        tournoi.programmer(fennecs, lynx);
        tournoi.programmer(orques, pumas);
        Resultat premier = tournoi.jouerProchainMatch(2, 1);
        Resultat second = tournoi.jouerProchainMatch(0, 0);
        assertEquals(List.of(premier, second), tournoi.resultats());
    }

    @Test
    void sans_match_programme_on_ne_peut_pas_jouer() {
        assertThrows(IllegalStateException.class, () -> tournoi.jouerProchainMatch(1, 0));
    }

    @Test
    void une_equipe_non_inscrite_ne_peut_pas_etre_programmee() {
        Equipe ours = equipe("Ours", "Yanis", "Ana");
        assertThrows(IllegalArgumentException.class, () -> tournoi.programmer(fennecs, ours));
        assertThrows(IllegalArgumentException.class, () -> tournoi.programmer(ours, fennecs));
    }

    @Test
    void les_listes_renvoyees_ne_sont_pas_modifiables() {
        tournoi.programmer(fennecs, lynx);
        tournoi.programmer(orques, pumas);
        tournoi.jouerProchainMatch(2, 1);
        assertThrows(UnsupportedOperationException.class, () -> tournoi.matchsAJouer().clear());
        assertThrows(UnsupportedOperationException.class, () -> tournoi.resultats().clear());
    }
}
