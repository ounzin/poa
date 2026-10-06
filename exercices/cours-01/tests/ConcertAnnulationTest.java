package fr.poa.cours01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ConcertAnnulationTest {

  @Test
  void annuler_une_place_la_rend_disponible() {
    Concert concert = new Concert("Nuit Électro", 500);
    concert.vendrePlace();
    concert.vendrePlace();
    concert.annulerPlace();
    assertEquals(499, concert.placesRestantes());
  }

  @Test
  void impossible_d_annuler_sans_vente() {
    Concert concert = new Concert("Nuit Électro", 500);
    assertThrows(IllegalStateException.class, () -> concert.annulerPlace());
  }

  @Test
  void une_place_annulee_peut_etre_revendue() {
    Concert concert = new Concert("Nuit Électro", 1);
    concert.vendrePlace();
    concert.annulerPlace();
    concert.vendrePlace();
    assertEquals(0, concert.placesRestantes());
  }
}