package fr.poa.td1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class FestivalParConcertTest {

  private final Festival festival = new Festival(List.of(
      new Billet("Jazz au parc", "Bruno", 30),
      new Billet("Nuit Électro", "Chloé", 45),
      new Billet("Jazz au parc", "Alice", 25),
      new Billet("Nuit Électro", "Alice", 45),
      new Billet("Rock en scène", "David", 60),
      new Billet("Jazz au parc", "Bruno", 30)));

  private final Festival vide = new Festival(List.of());

  @Test
  void compter_les_billets_de_chaque_concert() {
    assertEquals(Map.of("Jazz au parc", 3L, "Nuit Électro", 2L, "Rock en scène", 1L), festival.billetsParConcert());
  }

  @Test
  void un_festival_vide_n_a_aucun_concert() {
    assertTrue(vide.billetsParConcert().isEmpty());
  }
}
