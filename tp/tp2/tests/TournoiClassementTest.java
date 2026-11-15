package fr.poa.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TournoiClassementTest {

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
    void le_classement_apres_quatre_matchs() {
        jouerQuatreMatchs();
        assertEquals(List.of(
                new LigneDeClassement("Fennecs", 4, 1),
                new LigneDeClassement("Lynx", 3, 2),
                new LigneDeClassement("Pumas", 2, 0),
                new LigneDeClassement("Orques", 1, -3)), tournoi.classement());
    }

    @Test
    void sans_match_les_equipes_sont_dans_l_ordre_alphabetique() {
        assertEquals(List.of(
                new LigneDeClassement("Fennecs", 0, 0),
                new LigneDeClassement("Lynx", 0, 0),
                new LigneDeClassement("Orques", 0, 0),
                new LigneDeClassement("Pumas", 0, 0)), tournoi.classement());
    }

    @Test
    void le_classement_n_est_pas_modifiable() {
        jouerQuatreMatchs();
        assertThrows(UnsupportedOperationException.class, () -> tournoi.classement().clear());
    }
}
