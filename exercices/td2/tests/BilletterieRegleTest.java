package fr.poa.td2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class BilletterieRegleTest {

  private static final Concert NUIT = new Concert("Nuit Électro", LocalDate.of(2026, 12, 30), 4500);
  private static final LocalDate LUNDI = LocalDate.of(2026, 11, 30);
  private final DepotDeVentes depot = new DepotEnMemoire();

  @Test
  void la_prevente_est_une_regle_de_prix() {
    RegleDePrix prevente = new Prevente();
    assertEquals(3600, prevente.prix(NUIT, LUNDI));
    assertEquals(4500, prevente.prix(NUIT, LUNDI.plusDays(1)));
  }

  @Test
  void une_regle_ecrite_en_lambda() {
    Billetterie billetterie = new Billetterie(new HorlogeFixe(LUNDI), depot, (concert, jour) -> 1000);
    assertEquals(1000, billetterie.prix(NUIT));
  }

  @Test
  void vendre_applique_la_regle_recue() {
    Billetterie billetterie = new Billetterie(new HorlogeFixe(LUNDI), depot, (concert, jour) -> concert.prix() / 2);
    assertEquals(2250, billetterie.vendre(NUIT, "Alice").prix());
    assertEquals(2250, billetterie.recette());
  }

  @Test
  void sans_regle_la_billetterie_applique_la_prevente() {
    assertEquals(3600, new Billetterie(new HorlogeFixe(LUNDI), depot).prix(NUIT));
  }

  @Test
  void une_regle_absente_est_refusee() {
    assertThrows(NullPointerException.class, () -> new Billetterie(new HorlogeFixe(LUNDI), depot, null));
  }
}
