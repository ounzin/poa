package fr.poa.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TournoiPointsTest {

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

    private void jouerQuatreMatchs() {
        tournoi.programmer(fennecs, lynx);
        tournoi.programmer(orques, pumas);
        tournoi.programmer(lynx, orques);
        tournoi.programmer(pumas, fennecs);
        tournoi.jouerProchainMatch(2, 1);
        tournoi.jouerProchainMatch(0, 0);
        tournoi.jouerProchainMatch(3, 0);
        tournoi.jouerProchainMatch(1, 1);
    }

    @Test
    void un_tournoi_sans_equipe_n_a_aucun_point() {
        assertTrue(new Tournoi().points().isEmpty());
    }

    @Test
    void sans_match_toutes_les_equipes_ont_0_point() {
        assertEquals(Map.of("Fennecs", 0, "Lynx", 0, "Orques", 0, "Pumas", 0), tournoi.points());
    }

    @Test
    void les_points_apres_quatre_matchs() {
        jouerQuatreMatchs();
        assertEquals(Map.of("Fennecs", 4, "Lynx", 3, "Orques", 1, "Pumas", 2), tournoi.points());
    }

    @Test
    void les_equipes_sont_dans_l_ordre_alphabetique() {
        jouerQuatreMatchs();
        assertEquals(List.of("Fennecs", "Lynx", "Orques", "Pumas"), List.copyOf(tournoi.points().keySet()));
    }
}
