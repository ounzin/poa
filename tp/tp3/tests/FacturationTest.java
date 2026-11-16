package fr.poa.tp3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class FacturationTest {

  private final Journal journal = new Journal(List.of(
      new Trajet("Alice", LocalDate.of(2026, 11, 30), "Gare", "Bibliothèque", 12),
      new Trajet("Bruno", LocalDate.of(2026, 11, 30), "Résidence", "Stade", 45),
      new Trajet("Alice", LocalDate.of(2026, 12, 1), "Bibliothèque", "Gare", 15),
      new Trajet("Chloé", LocalDate.of(2026, 12, 1), "Gare", "Stade", 70),
      new Trajet("Bruno", LocalDate.of(2026, 12, 2), "Stade", "Résidence", 45),
      new Trajet("David", LocalDate.of(2026, 12, 3), "Gare", "Résidence", 31)));

  @Test
  void avec_un_tarif_fixe_chaque_trajet_coute_le_meme_prix() {
    Facturation facturation = new Facturation(trajet -> 100);
    assertEquals(200, facturation.montantDu(journal, "Bruno"));
    assertEquals(100, facturation.montantDu(journal, "David"));
  }

  @Test
  void un_usager_sans_trajet_ne_doit_rien() {
    assertEquals(0, new Facturation(trajet -> 100).montantDu(journal, "Emma"));
  }

  @Test
  void la_facture_de_chaque_usager() {
    Facturation facturation = new Facturation(trajet -> trajet.minutes());
    assertEquals(Map.of("Alice", 27, "Bruno", 90, "Chloé", 70, "David", 31), facturation.factures(journal));
  }

  @Test
  void les_factures_sont_dans_l_ordre_alphabetique_des_usagers() {
    Facturation facturation = new Facturation(trajet -> 100);
    assertEquals(List.of("Alice", "Bruno", "Chloé", "David"), List.copyOf(facturation.factures(journal).keySet()));
  }

  @Test
  void avec_le_tarif_standard() {
    Facturation facturation = new Facturation(new TarifStandard());
    assertEquals(Map.of("Alice", 0, "Bruno", 200, "Chloé", 200, "David", 100), facturation.factures(journal));
  }

  @Test
  void un_tarif_absent_est_refuse() {
    assertThrows(NullPointerException.class, () -> new Facturation(null));
  }
}
