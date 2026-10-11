package fr.poa.cours02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ConcertReservationTest {

  @Test
  void reserver_diminue_les_places_restantes() {
    Concert concert = new Concert("NE-2026", "Nuit Électro", 500);
    concert.reserver(3);
    assertEquals(497, concert.placesRestantes());
  }

  @Test
  void on_peut_reserver_toutes_les_places() {
    Concert concert = new Concert("NE-2026", "Nuit Électro", 2);
    concert.reserver(2);
    assertEquals(0, concert.placesRestantes());
  }

  @Test
  void un_nombre_de_places_nul_ou_negatif_est_refuse() {
    Concert concert = new Concert("NE-2026", "Nuit Électro", 500);
    assertThrows(IllegalArgumentException.class, () -> concert.reserver(0));
    assertThrows(IllegalArgumentException.class, () -> concert.reserver(-1));
  }

  @Test
  void trop_de_places_demandees_leve_une_exception_detaillee() {
    Concert concert = new Concert("NE-2026", "Nuit Électro", 2);
    PlacesInsuffisantesException exception = assertThrows(PlacesInsuffisantesException.class,
        () -> concert.reserver(3));
    assertEquals(3, exception.demandees());
    assertEquals(2, exception.restantes());
    String message = exception.getMessage();
    assertTrue(message != null && message.contains("NE-2026"),
        "Le message de l'exception doit indiquer le code du concert");
  }

  @Test
  void une_reservation_refusee_ne_change_pas_les_places() {
    Concert concert = new Concert("NE-2026", "Nuit Électro", 2);
    assertThrows(PlacesInsuffisantesException.class, () -> concert.reserver(3));
    assertEquals(2, concert.placesRestantes());
  }

  @Test
  void l_exception_est_non_verifiee() {
    assertTrue(RuntimeException.class.isAssignableFrom(PlacesInsuffisantesException.class),
        "PlacesInsuffisantesException doit hériter de RuntimeException");
  }

  @Test
  void un_concert_reste_retrouvable_apres_une_reservation() {
    Concert concert = new Concert("NE-2026", "Nuit Électro", 500);
    Set<Concert> concerts = new HashSet<>();
    concerts.add(concert);
    concert.reserver(3);
    assertTrue(concerts.contains(concert),
        "equals et hashCode ne doivent pas dépendre des places vendues");
  }
}