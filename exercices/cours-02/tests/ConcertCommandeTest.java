package fr.poa.cours02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class ConcertCommandeTest {

  @Test
  void commander_reserve_une_place_par_billet() {
    Concert concert = new Concert("NE-2026", "Nuit Électro", 500);
    Commande commande = concert.commander(List.of(
        new BilletStandard(new Prix(5000)),
        new BilletVIP(new Prix(5000))));
    assertSame(concert, commande.concert());
    assertEquals(new Prix(12500), commande.total());
    assertEquals(498, concert.placesRestantes());
  }

  @Test
  void une_commande_trop_grande_ne_reserve_aucune_place() {
    Concert concert = new Concert("NE-2026", "Nuit Électro", 1);
    List<Billet> billets = List.of(
        new BilletStandard(new Prix(5000)),
        new BilletStandard(new Prix(5000)));
    assertThrows(PlacesInsuffisantesException.class, () -> concert.commander(billets));
    assertEquals(1, concert.placesRestantes());
  }

  @Test
  void une_commande_vide_est_refusee() {
    Concert concert = new Concert("NE-2026", "Nuit Électro", 500);
    assertThrows(IllegalArgumentException.class, () -> concert.commander(List.of()));
  }

  @Test
  void une_commande_invalide_ne_reserve_aucune_place() {
    Concert concert = new Concert("NE-2026", "Nuit Électro", 500);
    List<Billet> billets = Arrays.asList(new BilletStandard(new Prix(5000)), null);
    assertThrows(NullPointerException.class, () -> concert.commander(billets));
    assertEquals(500, concert.placesRestantes(),
        "Une commande refusée ne doit réserver aucune place : validez avant de modifier");
  }
}