package fr.poa.cours03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class SalleReservationTest {

  private final Salle salle = new Salle('C', 10);

  @Test
  void dimensions_invalides() {
    assertThrows(IllegalArgumentException.class, () -> new Salle('c', 10));
    assertThrows(IllegalArgumentException.class, () -> new Salle('C', 0));
  }

  @Test
  void les_places_qui_existent() {
    assertTrue(salle.existe(new Place('A', 1)));
    assertTrue(salle.existe(new Place('C', 10)));
    assertFalse(salle.existe(new Place('D', 1)));
    assertFalse(salle.existe(new Place('A', 11)));
  }

  @Test
  void une_salle_neuve_est_libre() {
    assertEquals(30, salle.nombreDePlacesLibres());
    assertTrue(salle.estLibre(new Place('B', 5)));
    assertTrue(salle.placesReservees().isEmpty());
  }

  @Test
  void reserver_une_place() {
    salle.reserver(new Place('B', 5), "Alice");
    assertFalse(salle.estLibre(new Place('B', 5)));
    assertEquals("Alice", salle.occupant(new Place('B', 5)));
    assertEquals(29, salle.nombreDePlacesLibres());
  }

  @Test
  void une_place_ne_se_reserve_qu_une_fois() {
    salle.reserver(new Place('B', 5), "Alice");
    assertThrows(IllegalStateException.class, () -> salle.reserver(new Place('B', 5), "Bruno"));
    assertEquals("Alice", salle.occupant(new Place('B', 5)));
  }

  @Test
  void une_place_qui_n_existe_pas_est_refusee() {
    assertThrows(IllegalArgumentException.class, () -> salle.reserver(new Place('D', 1), "Alice"));
    assertThrows(IllegalArgumentException.class, () -> salle.estLibre(new Place('A', 11)));
    assertThrows(IllegalArgumentException.class, () -> salle.occupant(new Place('A', 11)));
  }

  @Test
  void une_place_libre_n_a_pas_d_occupant() {
    assertThrows(IllegalStateException.class, () -> salle.occupant(new Place('B', 5)));
  }

  @Test
  void les_places_reservees_sont_triees() {
    salle.reserver(new Place('C', 2), "Alice");
    salle.reserver(new Place('A', 10), "Bruno");
    salle.reserver(new Place('A', 2), "Chloé");
    salle.reserver(new Place('B', 7), "David");
    assertEquals(List.of(new Place('A', 2), new Place('A', 10), new Place('B', 7), new Place('C', 2)),
        salle.placesReservees());
  }

  @Test
  void la_liste_des_places_reservees_n_est_pas_modifiable() {
    salle.reserver(new Place('B', 5), "Alice");
    assertThrows(UnsupportedOperationException.class, () -> salle.placesReservees().clear());
  }
}