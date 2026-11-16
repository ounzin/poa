package fr.poa.tp3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class JournalSelectionTest {

  private final Journal journal = new Journal(List.of(
      new Trajet("Alice", LocalDate.of(2026, 11, 30), "Gare", "Bibliothèque", 12),
      new Trajet("Bruno", LocalDate.of(2026, 11, 30), "Résidence", "Stade", 45),
      new Trajet("Alice", LocalDate.of(2026, 12, 1), "Bibliothèque", "Gare", 15),
      new Trajet("Chloé", LocalDate.of(2026, 12, 1), "Gare", "Stade", 70),
      new Trajet("Bruno", LocalDate.of(2026, 12, 2), "Stade", "Résidence", 45),
      new Trajet("David", LocalDate.of(2026, 12, 3), "Gare", "Résidence", 31)));

  @Test
  void les_trajets_restent_dans_l_ordre_du_journal() {
    assertEquals(6, journal.trajets().size());
    assertEquals("Chloé", journal.trajets().get(3).usager());
  }

  @Test
  void le_journal_garde_sa_propre_copie() {
    List<Trajet> trajets = new ArrayList<>(List.of(new Trajet("Emma", LocalDate.of(2026, 12, 1), "Gare", "Stade", 5)));
    Journal copie = new Journal(trajets);
    trajets.clear();
    assertEquals(1, copie.trajets().size());
  }

  @Test
  void la_liste_des_trajets_n_est_pas_modifiable() {
    assertThrows(UnsupportedOperationException.class, () -> journal.trajets().clear());
  }

  @Test
  void selectionner_garde_l_ordre_du_journal() {
    assertEquals(List.of(
        new Trajet("Alice", LocalDate.of(2026, 11, 30), "Gare", "Bibliothèque", 12),
        new Trajet("Chloé", LocalDate.of(2026, 12, 1), "Gare", "Stade", 70),
        new Trajet("David", LocalDate.of(2026, 12, 3), "Gare", "Résidence", 31)),
        journal.selectionner(Criteres.partantDe("Gare")));
  }

  @Test
  void selectionner_avec_des_criteres_combines() {
    List<Trajet> trajets = journal.selectionner(Criteres.partantDe("Gare").and(Criteres.dureeAuMoins(30)));
    assertEquals(List.of("Chloé", "David"), trajets.stream().map(Trajet::usager).toList());
  }

  @Test
  void selectionner_peut_ne_rien_trouver() {
    assertTrue(journal.selectionner(Criteres.deLUsager("Emma")).isEmpty());
  }

  @Test
  void la_duree_totale_des_trajets_d_un_usager() {
    assertEquals(90, journal.dureeTotale(Criteres.deLUsager("Bruno")));
  }

  @Test
  void la_duree_totale_de_tous_les_trajets() {
    assertEquals(218, journal.dureeTotale(trajet -> true));
  }

  @Test
  void les_usagers_sans_doublon_dans_l_ordre_alphabetique() {
    LocalDate jour = LocalDate.of(2026, 12, 1);
    Journal autre = new Journal(List.of(
        new Trajet("Chloé", jour, "Gare", "Stade", 10),
        new Trajet("Alice", jour, "Stade", "Gare", 10),
        new Trajet("Chloé", jour, "Stade", "Gare", 10),
        new Trajet("Bruno", jour, "Gare", "Stade", 10)));
    assertEquals(List.of("Alice", "Bruno", "Chloé"), autre.usagers());
  }
}
