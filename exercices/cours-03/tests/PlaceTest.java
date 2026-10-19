package fr.poa.cours03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class PlaceTest {

  @Test
  void une_place_a_un_rang_et_un_numero() {
    Place place = new Place('B', 12);
    assertEquals('B', place.rang());
    assertEquals(12, place.numero());
  }

  @Test
  void rang_et_numero_invalides() {
    assertThrows(IllegalArgumentException.class, () -> new Place('a', 1));
    assertThrows(IllegalArgumentException.class, () -> new Place('@', 1));
    assertThrows(IllegalArgumentException.class, () -> new Place('B', 0));
  }

  @Test
  void to_string_colle_le_rang_et_le_numero() {
    assertEquals("B12", new Place('B', 12).toString());
  }

  @Test
  void place_est_comparable() {
    assertTrue(Comparable.class.isAssignableFrom(Place.class));
  }

  @Test
  void les_places_se_trient_par_rang_puis_par_numero() {
    List<Place> places = new ArrayList<>(List.of(
        new Place('B', 1), new Place('A', 10), new Place('A', 2), new Place('A', 1)));
    Collections.sort(places);
    assertEquals(List.of(new Place('A', 1), new Place('A', 2), new Place('A', 10), new Place('B', 1)), places);
  }

  @Test
  void la_plus_grande_place() {
    assertEquals(new Place('C', 3),
        Outils.plusGrand(List.of(new Place('A', 1), new Place('C', 3), new Place('B', 20))));
  }

  @Test
  void deux_places_identiques_sont_egales() {
    Set<Place> places = new HashSet<>();
    places.add(new Place('B', 12));
    places.add(new Place('B', 12));
    assertEquals(1, places.size());
    assertEquals(0, new Place('B', 12).compareTo(new Place('B', 12)));
  }
}