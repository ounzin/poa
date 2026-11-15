package fr.poa.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ClassementsTest {

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

    @Test
    void les_trois_plus_petits_entiers() {
        assertEquals(List.of(1, 2, 3), Classements.premiers(List.of(5, 1, 4, 2, 3), 3));
    }

    @Test
    void les_premiers_textes_dans_l_ordre_alphabetique() {
        assertEquals(List.of("Fennecs", "Lynx"), Classements.premiers(List.of("Pumas", "Fennecs", "Orques", "Lynx"), 2));
    }

    @Test
    void demander_plus_d_elements_qu_il_n_y_en_a() {
        assertEquals(List.of(1, 3), Classements.premiers(List.of(3, 1), 5));
    }

    @Test
    void un_nombre_negatif_est_refuse() {
        assertThrows(IllegalArgumentException.class, () -> Classements.premiers(List.of(3, 1), -1));
    }

    @Test
    void le_podium_d_un_tournoi() {
        Tournoi tournoi = new Tournoi();
        tournoi.inscrire(pumas);
        tournoi.inscrire(lynx);
        tournoi.inscrire(fennecs);
        tournoi.inscrire(orques);
        tournoi.programmer(fennecs, lynx);
        tournoi.programmer(orques, pumas);
        tournoi.programmer(lynx, orques);
        tournoi.programmer(pumas, fennecs);
        tournoi.jouerProchainMatch(2, 1);
        tournoi.jouerProchainMatch(0, 0);
        tournoi.jouerProchainMatch(3, 0);
        tournoi.jouerProchainMatch(1, 1);
        List<String> podium = new ArrayList<>();
        for (LigneDeClassement ligne : Classements.premiers(tournoi.classement(), 3)) {
            podium.add(ligne.equipe());
        }
        assertEquals(List.of("Fennecs", "Lynx", "Pumas"), podium);
    }
}
