package fr.poa.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class TournoiInscriptionTest {

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

    @Test
    void un_nouveau_tournoi_n_a_aucune_equipe() {
        assertTrue(tournoi.equipes().isEmpty());
    }

    @Test
    void les_equipes_sont_dans_l_ordre_d_inscription() {
        tournoi.inscrire(pumas);
        tournoi.inscrire(fennecs);
        assertEquals(List.of(pumas, fennecs), tournoi.equipes());
    }

    @Test
    void une_equipe_incomplete_est_refusee() {
        Equipe ours = new Equipe("Ours");
        ours.ajouterJoueur("Yanis");
        assertThrows(IllegalStateException.class, () -> tournoi.inscrire(ours));
        assertTrue(tournoi.equipes().isEmpty());
    }

    @Test
    void une_equipe_ne_s_inscrit_qu_une_fois() {
        tournoi.inscrire(fennecs);
        assertThrows(IllegalStateException.class, () -> tournoi.inscrire(fennecs));
        assertThrows(IllegalStateException.class, () -> tournoi.inscrire(equipe("Fennecs", "Yanis", "Ana")));
        assertEquals(List.of(fennecs), tournoi.equipes());
    }

    @Test
    void un_joueur_ne_joue_que_dans_une_seule_equipe() {
        tournoi.inscrire(fennecs);
        assertThrows(IllegalStateException.class, () -> tournoi.inscrire(equipe("Ours", "Yanis", "Hugo")));
        assertEquals(List.of(fennecs), tournoi.equipes());
    }

    @Test
    void la_liste_des_equipes_n_est_pas_modifiable() {
        tournoi.inscrire(fennecs);
        assertThrows(UnsupportedOperationException.class, () -> tournoi.equipes().add(lynx));
    }
}
