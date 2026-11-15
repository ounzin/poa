package fr.poa.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class LigneDeClassementTest {

    @Test
    void une_ligne_de_classement_est_comparable() {
        assertTrue(Comparable.class.isAssignableFrom(LigneDeClassement.class),
                "LigneDeClassement doit implémenter Comparable<LigneDeClassement>");
    }

    @Test
    void plus_de_points_passe_devant() {
        LigneDeClassement pumas = new LigneDeClassement("Pumas", 6, 0);
        LigneDeClassement fennecs = new LigneDeClassement("Fennecs", 3, 5);
        assertTrue(pumas.compareTo(fennecs) < 0);
        assertTrue(fennecs.compareTo(pumas) > 0);
    }

    @Test
    void a_points_egaux_la_meilleure_difference_passe_devant() {
        LigneDeClassement pumas = new LigneDeClassement("Pumas", 3, 4);
        LigneDeClassement fennecs = new LigneDeClassement("Fennecs", 3, 1);
        assertTrue(pumas.compareTo(fennecs) < 0);
        assertTrue(fennecs.compareTo(pumas) > 0);
    }

    @Test
    void a_egalite_parfaite_l_ordre_alphabetique() {
        LigneDeClassement pumas = new LigneDeClassement("Pumas", 3, 1);
        LigneDeClassement fennecs = new LigneDeClassement("Fennecs", 3, 1);
        assertTrue(fennecs.compareTo(pumas) < 0);
        assertTrue(pumas.compareTo(fennecs) > 0);
        assertEquals(0, fennecs.compareTo(new LigneDeClassement("Fennecs", 3, 1)));
    }

    @Test
    void trier_des_lignes_de_classement() {
        LigneDeClassement a = new LigneDeClassement("Orques", 1, -3);
        LigneDeClassement b = new LigneDeClassement("Lynx", 4, -1);
        LigneDeClassement c = new LigneDeClassement("Pumas", 4, 2);
        LigneDeClassement d = new LigneDeClassement("Fennecs", 1, -3);
        List<LigneDeClassement> lignes = new ArrayList<>(List.of(a, b, c, d));
        Collections.sort(lignes);
        assertEquals(List.of(c, b, d, a), lignes);
    }
}
