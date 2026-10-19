package fr.poa.cours03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class SalleAttenteTest {

  private final Salle salle = new Salle('C', 10);

  @Test
  void la_liste_d_attente_garde_l_ordre_d_arrivee() {
    salle.inscrireEnAttente("Emma");
    salle.inscrireEnAttente("Farid");
    salle.inscrireEnAttente("Gina");
    assertEquals(List.of("Emma", "Farid", "Gina"), salle.listeDAttente());
  }

  @Test
  void annuler_sans_liste_d_attente_libere_la_place() {
    salle.reserver(new Place('B', 5), "Alice");
    salle.annuler(new Place('B', 5));
    assertTrue(salle.estLibre(new Place('B', 5)));
    assertEquals(30, salle.nombreDePlacesLibres());
  }

  @Test
  void la_place_annulee_va_au_premier_de_la_liste() {
    salle.reserver(new Place('B', 5), "Alice");
    salle.inscrireEnAttente("Emma");
    salle.inscrireEnAttente("Farid");
    salle.annuler(new Place('B', 5));
    assertEquals("Emma", salle.occupant(new Place('B', 5)));
    assertEquals(List.of("Farid"), salle.listeDAttente());
  }

  @Test
  void deux_annulations_servent_la_liste_dans_l_ordre() {
    salle.reserver(new Place('A', 1), "Alice");
    salle.reserver(new Place('A', 2), "Bruno");
    salle.inscrireEnAttente("Emma");
    salle.inscrireEnAttente("Farid");
    salle.annuler(new Place('A', 2));
    salle.annuler(new Place('A', 1));
    assertEquals("Emma", salle.occupant(new Place('A', 2)));
    assertEquals("Farid", salle.occupant(new Place('A', 1)));
    assertTrue(salle.listeDAttente().isEmpty());
  }

  @Test
  void annuler_une_place_libre_est_refuse() {
    assertThrows(IllegalStateException.class, () -> salle.annuler(new Place('B', 5)));
    assertThrows(IllegalArgumentException.class, () -> salle.annuler(new Place('D', 1)));
  }

  @Test
  void la_liste_d_attente_renvoyee_n_est_pas_modifiable() {
    salle.inscrireEnAttente("Emma");
    assertThrows(UnsupportedOperationException.class, () -> salle.listeDAttente().clear());
    assertEquals(List.of("Emma"), salle.listeDAttente());
  }
}