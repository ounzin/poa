package fr.poa.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class EquipeTest {

    @Test
    void une_equipe_garde_son_nom() {
        assertEquals("Fennecs", new Equipe("Fennecs").nom());
    }

    @Test
    void un_nom_absent_ou_vide_est_refuse() {
        assertThrows(NullPointerException.class, () -> new Equipe(null));
        assertThrows(IllegalArgumentException.class, () -> new Equipe(""));
    }

    @Test
    void une_nouvelle_equipe_n_a_aucun_joueur() {
        Equipe equipe = new Equipe("Fennecs");
        assertTrue(equipe.joueurs().isEmpty());
        assertFalse(equipe.estComplete());
    }

    @Test
    void les_joueurs_sont_dans_l_ordre_alphabetique() {
        Equipe equipe = new Equipe("Fennecs");
        assertTrue(equipe.ajouterJoueur("Inès"));
        assertFalse(equipe.estComplete());
        assertTrue(equipe.ajouterJoueur("Hugo"));
        assertEquals(List.of("Hugo", "Inès"), equipe.joueurs());
        assertTrue(equipe.estComplete());
    }

    @Test
    void un_joueur_deja_present_n_est_pas_ajoute_une_seconde_fois() {
        Equipe equipe = new Equipe("Fennecs");
        equipe.ajouterJoueur("Inès");
        assertFalse(equipe.ajouterJoueur("Inès"));
        assertEquals(List.of("Inès"), equipe.joueurs());
    }

    @Test
    void une_equipe_complete_refuse_un_troisieme_joueur() {
        Equipe equipe = new Equipe("Fennecs");
        equipe.ajouterJoueur("Inès");
        equipe.ajouterJoueur("Hugo");
        assertThrows(IllegalStateException.class, () -> equipe.ajouterJoueur("Léa"));
        assertEquals(List.of("Hugo", "Inès"), equipe.joueurs());
    }

    @Test
    void la_liste_des_joueurs_n_est_pas_modifiable() {
        Equipe equipe = new Equipe("Fennecs");
        equipe.ajouterJoueur("Inès");
        assertThrows(UnsupportedOperationException.class, () -> equipe.joueurs().add("Léa"));
    }

    @Test
    void deux_equipes_de_meme_nom_sont_egales() {
        Equipe equipe = new Equipe("Fennecs");
        Set<Equipe> equipes = new HashSet<>();
        equipes.add(equipe);
        equipe.ajouterJoueur("Inès");
        assertEquals(new Equipe("Fennecs"), equipe);
        assertEquals(new Equipe("Fennecs").hashCode(), equipe.hashCode());
        assertTrue(equipes.contains(equipe), "equals et hashCode ne doivent pas dépendre des joueurs");
    }
}
