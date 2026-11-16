package fr.poa.tp3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

class CriteresTest {

  private static final LocalDate JOUR = LocalDate.of(2026, 12, 1);
  private final Trajet trajet = new Trajet("Alice", JOUR, "Gare", "Stade", 30);

  @Test
  void les_trajets_d_un_usager() {
    assertTrue(Criteres.deLUsager("Alice").test(trajet));
    assertFalse(Criteres.deLUsager("Bruno").test(trajet));
  }

  @Test
  void les_trajets_partant_d_une_station() {
    assertTrue(Criteres.partantDe("Gare").test(trajet));
    assertFalse(Criteres.partantDe("Stade").test(trajet));
  }

  @Test
  void les_trajets_arrivant_a_une_station() {
    assertTrue(Criteres.arrivantA("Stade").test(trajet));
    assertFalse(Criteres.arrivantA("Gare").test(trajet));
  }

  @Test
  void la_duree_minimale_est_comprise() {
    assertTrue(Criteres.dureeAuMoins(30).test(trajet));
    assertFalse(Criteres.dureeAuMoins(31).test(trajet));
  }

  @Test
  void une_periode_comprend_ses_deux_bornes() {
    assertTrue(Criteres.entre(JOUR, JOUR.plusDays(6)).test(trajet));
    assertTrue(Criteres.entre(JOUR.minusDays(6), JOUR).test(trajet));
    assertFalse(Criteres.entre(JOUR.plusDays(1), JOUR.plusDays(6)).test(trajet));
  }

  @Test
  void une_periode_a_l_envers_est_refusee() {
    assertThrows(IllegalArgumentException.class, () -> Criteres.entre(JOUR, JOUR.minusDays(1)));
  }

  @Test
  void les_criteres_se_combinent() {
    Predicate<Trajet> longsDAlice = Criteres.deLUsager("Alice").and(Criteres.dureeAuMoins(20));
    assertTrue(longsDAlice.test(trajet));
    assertFalse(longsDAlice.test(new Trajet("Alice", JOUR, "Gare", "Stade", 10)));
  }

  @Test
  void un_critere_s_inverse() {
    assertFalse(Criteres.partantDe("Gare").negate().test(trajet));
  }
}
