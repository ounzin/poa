package fr.poa.tp3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class TarifStandardTest {

  private final Tarif tarif = new TarifStandard();

  private static Trajet trajetDe(int minutes) {
    return new Trajet("Alice", LocalDate.of(2026, 12, 1), "Gare", "Stade", minutes);
  }

  @Test
  void tarif_est_une_interface() {
    assertTrue(Tarif.class.isInterface());
  }

  @Test
  void les_trente_premieres_minutes_sont_gratuites() {
    assertEquals(0, tarif.prix(trajetDe(1)));
    assertEquals(0, tarif.prix(trajetDe(30)));
  }

  @Test
  void une_tranche_commencee_est_due() {
    assertEquals(100, tarif.prix(trajetDe(31)));
    assertEquals(100, tarif.prix(trajetDe(60)));
  }

  @Test
  void chaque_tranche_de_trente_minutes_coute_un_euro() {
    assertEquals(200, tarif.prix(trajetDe(61)));
    assertEquals(200, tarif.prix(trajetDe(90)));
    assertEquals(300, tarif.prix(trajetDe(91)));
  }
}
