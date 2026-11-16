package fr.poa.td1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class FestivalPlusCherTest {

  private final Festival festival = new Festival(List.of(
      new Billet("Jazz au parc", "Bruno", 30),
      new Billet("Nuit Électro", "Chloé", 45),
      new Billet("Jazz au parc", "Alice", 25),
      new Billet("Nuit Électro", "Alice", 45),
      new Billet("Rock en scène", "David", 60),
      new Billet("Jazz au parc", "Bruno", 30)));

  private final Festival vide = new Festival(List.of());

  @Test
  void le_billet_le_plus_cher() {
    assertEquals(Optional.of(new Billet("Rock en scène", "David", 60)), festival.billetLePlusCher());
  }

  @Test
  void un_festival_vide_n_a_pas_de_billet_le_plus_cher() {
    assertEquals(Optional.empty(), vide.billetLePlusCher());
  }

  @Test
  void le_prix_le_plus_eleve() {
    assertEquals(60, festival.prixLePlusEleve());
  }

  @Test
  void le_prix_le_plus_eleve_d_un_festival_vide_vaut_zero() {
    assertEquals(0, vide.prixLePlusEleve());
  }
}
