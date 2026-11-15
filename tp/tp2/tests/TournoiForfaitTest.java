package fr.poa.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TournoiForfaitTest {

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

    private void programmerQuatreMatchs() {
        tournoi.programmer(fennecs, lynx);
        tournoi.programmer(orques, pumas);
        tournoi.programmer(lynx, orques);
        tournoi.programmer(pumas, fennecs);
    }

    @Test
    void le_forfait_retire_tous_les_matchs_de_l_equipe() {
        programmerQuatreMatchs();
        assertEquals(2, tournoi.declarerForfait(fennecs));
        assertEquals(List.of(new Match(orques, pumas), new Match(lynx, orques)), tournoi.matchsAJouer());
    }

    @Test
    void les_autres_matchs_restent_dans_le_meme_ordre() {
        programmerQuatreMatchs();
        assertEquals(2, tournoi.declarerForfait(orques));
        assertEquals(List.of(new Match(fennecs, lynx), new Match(pumas, fennecs)), tournoi.matchsAJouer());
    }

    @Test
    void les_matchs_deja_joues_restent_acquis() {
        programmerQuatreMatchs();
        tournoi.jouerProchainMatch(2, 1);
        assertEquals(1, tournoi.declarerForfait(fennecs));
        assertEquals(1, tournoi.resultats().size());
        assertEquals(3, tournoi.points().get("Fennecs"));
        assertEquals(4, tournoi.classement().size());
    }

    @Test
    void une_equipe_sans_match_a_jouer() {
        assertEquals(0, tournoi.declarerForfait(fennecs));
    }

    @Test
    void une_equipe_non_inscrite_est_refusee() {
        assertThrows(IllegalArgumentException.class, () -> tournoi.declarerForfait(equipe("Ours", "Yanis", "Ana")));
    }
}
