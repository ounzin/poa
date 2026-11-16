package fr.poa.td1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class FestivalRecetteTest {

  private final Festival festival = new Festival(List.of(
      new Billet("Jazz au parc", "Bruno", 30),
      new Billet("Nuit Électro", "Chloé", 45),
      new Billet("Jazz au parc", "Alice", 25),
      new Billet("Nuit Électro", "Alice", 45),
      new Billet("Rock en scène", "David", 60),
      new Billet("Jazz au parc", "Bruno", 30)));

  private final Festival vide = new Festival(List.of());

  @Test
  void la_recette_additionne_le_prix_de_tous_les_billets() {
    assertEquals(235, festival.recette());
  }

  @Test
  void un_festival_vide_ne_rapporte_rien() {
    assertEquals(0, vide.recette());
  }

  @Test
  void savoir_si_un_spectateur_a_un_billet() {
    assertTrue(festival.aUnBillet("Alice"));
    assertTrue(festival.aUnBillet("David"));
    assertFalse(festival.aUnBillet("Emma"));
  }

  @Test
  void tous_les_billets_coutent_au_moins_un_prix() {
    assertTrue(festival.tousLesBilletsCoutentAuMoins(25));
    assertFalse(festival.tousLesBilletsCoutentAuMoins(30));
  }

  @Test
  void dans_un_festival_vide_aucun_billet_ne_contredit_le_minimum() {
    assertTrue(vide.tousLesBilletsCoutentAuMoins(1000));
  }
}
