package fr.poa.td1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TraitementsTransformerTest {

  @Test
  void multiplier_chaque_nombre() {
    assertEquals(List.of(10, 20, 30), Traitements.transformer(List.of(1, 2, 3), n -> n * 10));
  }

  @Test
  void transformer_peut_changer_le_type() {
    List<Integer> longueurs = Traitements.transformer(List.of("Jazz", "Rock", "Électro"), String::length);
    assertEquals(List.of(4, 4, 7), longueurs);
  }

  @Test
  void transformer_une_liste_vide() {
    assertTrue(Traitements.transformer(List.<String>of(), String::length).isEmpty());
  }

  @Test
  void transformer_garde_les_doublons_et_l_ordre() {
    assertEquals(List.of("JAZZ", "ROCK", "JAZZ"), Traitements.transformer(List.of("jazz", "rock", "jazz"), String::toUpperCase));
  }

  @Test
  void la_liste_recue_n_est_pas_modifiee() {
    List<String> styles = new ArrayList<>(List.of("jazz", "rock"));
    Traitements.transformer(styles, String::toUpperCase);
    assertEquals(List.of("jazz", "rock"), styles);
  }
}
