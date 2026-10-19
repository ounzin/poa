package fr.poa.cours03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class OutilsTest {

  @Test
  void le_plus_grand_de_plusieurs_entiers() {
    Integer plusGrand = Outils.plusGrand(List.of(3, 17, 5));
    assertEquals(17, plusGrand);
  }

  @Test
  void le_plus_grand_peut_etre_en_premier_ou_en_dernier() {
    assertEquals(17, Outils.plusGrand(List.of(17, 3, 5)));
    assertEquals(17, Outils.plusGrand(List.of(3, 5, 17)));
  }

  @Test
  void le_plus_grand_de_plusieurs_textes() {
    String plusGrand = Outils.plusGrand(List.of("Nuit", "Concert", "Zénith"));
    assertEquals("Zénith", plusGrand);
  }

  @Test
  void une_liste_d_un_seul_element() {
    assertEquals(42, Outils.plusGrand(List.of(42)));
  }

  @Test
  void une_liste_vide_est_refusee() {
    assertThrows(IllegalArgumentException.class, () -> Outils.plusGrand(List.<Integer>of()));
  }
}