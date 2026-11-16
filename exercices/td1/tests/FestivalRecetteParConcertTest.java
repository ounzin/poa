package fr.poa.td1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class FestivalRecetteParConcertTest {

  private final Festival festival = new Festival(List.of(
      new Billet("Jazz au parc", "Bruno", 30),
      new Billet("Nuit Électro", "Chloé", 45),
      new Billet("Jazz au parc", "Alice", 25),
      new Billet("Nuit Électro", "Alice", 45),
      new Billet("Rock en scène", "David", 60),
      new Billet("Jazz au parc", "Bruno", 30)));

  private final Festival vide = new Festival(List.of());

  @Test
  void la_recette_de_chaque_concert() {
    assertEquals(Map.of("Jazz au parc", 85, "Nuit Électro", 90, "Rock en scène", 60), festival.recetteParConcert());
  }

  @Test
  void les_concerts_sont_dans_l_ordre_alphabetique() {
    assertEquals(List.of("Jazz au parc", "Nuit Électro", "Rock en scène"), List.copyOf(festival.recetteParConcert().keySet()));
  }

  @Test
  void un_festival_vide_n_a_aucune_recette() {
    assertTrue(vide.recetteParConcert().isEmpty());
  }
}
