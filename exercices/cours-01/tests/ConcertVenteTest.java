package fr.poa.cours01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ConcertVenteTest {

  @Test
  void vendre_une_place_diminue_les_places_restantes() {
    Concert concert = new Concert("Nuit Électro", 500);
    concert.vendrePlace();
    concert.vendrePlace();
    assertEquals(498, concert.placesRestantes());
  }

  @Test
  void impossible_de_vendre_une_place_de_trop() {
    Concert concert = new Concert("Nuit Électro", 1);
    concert.vendrePlace();
    assertThrows(IllegalStateException.class, () -> concert.vendrePlace());
  }

  @Test
  void une_vente_refusee_ne_change_pas_les_places() {
    Concert concert = new Concert("Nuit Électro", 1);
    concert.vendrePlace();
    assertThrows(IllegalStateException.class, () -> concert.vendrePlace());
    assertEquals(0, concert.placesRestantes());
  }
}