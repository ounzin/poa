package fr.poa.td1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TraitementsGarderTest {

  @Test
  void garder_les_nombres_pairs() {
    List<Integer> pairs = Traitements.garder(List.of(1, 2, 3, 4, 5, 6), n -> n % 2 == 0);
    assertEquals(List.of(2, 4, 6), pairs);
  }

  @Test
  void garder_conserve_l_ordre_des_elements() {
    List<String> concerts = List.of("Nuit Électro", "Jazz au parc", "Nuit Blanche", "Rock en scène");
    assertEquals(List.of("Nuit Électro", "Nuit Blanche"), Traitements.garder(concerts, c -> c.startsWith("Nuit")));
  }

  @Test
  void aucun_element_ne_convient() {
    assertTrue(Traitements.garder(List.of(1, 3, 5), n -> n > 10).isEmpty());
  }

  @Test
  void garder_accepte_une_reference_de_methode() {
    assertEquals(List.of("", " "), Traitements.garder(List.of("", "Jazz", " ", "Rock"), String::isBlank));
  }

  @Test
  void la_liste_recue_n_est_pas_modifiee() {
    List<Integer> nombres = new ArrayList<>(List.of(1, 2, 3, 4));
    Traitements.garder(nombres, n -> n > 2);
    assertEquals(List.of(1, 2, 3, 4), nombres);
  }
}
