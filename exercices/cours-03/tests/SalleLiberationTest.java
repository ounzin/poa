package fr.poa.cours03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class SalleLiberationTest {

  private final Salle salle = new Salle('C', 10);

  @Test
  void liberer_toutes_les_places_d_une_personne() {
    salle.reserver(new Place('A', 1), "Alice");
    salle.reserver(new Place('A', 2), "Bruno");
    salle.reserver(new Place('A', 3), "Alice");
    salle.reserver(new Place('B', 1), "Chloé");
    assertEquals(2, salle.libererPlacesDe("Alice"));
    assertEquals(List.of(new Place('A', 2), new Place('B', 1)), salle.placesReservees());
  }

  @Test
  void liberer_les_places_d_une_personne_sans_reservation() {
    salle.reserver(new Place('A', 1), "Alice");
    assertEquals(0, salle.libererPlacesDe("Bruno"));
    assertEquals(1, salle.placesReservees().size());
  }

  @Test
  void les_places_liberees_restent_libres() {
    salle.reserver(new Place('A', 1), "Alice");
    salle.inscrireEnAttente("Emma");
    salle.libererPlacesDe("Alice");
    assertEquals(30, salle.nombreDePlacesLibres());
    assertEquals(List.of("Emma"), salle.listeDAttente());
  }
}