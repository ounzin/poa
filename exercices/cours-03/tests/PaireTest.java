package fr.poa.cours03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PaireTest {

  @Test
  void paire_est_un_record_avec_deux_types_parametres() {
    assertTrue(Paire.class.isRecord());
    assertEquals(2, Paire.class.getTypeParameters().length);
  }

  @Test
  void une_paire_garde_ses_deux_valeurs() {
    Paire<String, Integer> paire = new Paire<>("Nuit Électro", 500);
    String nom = paire.premier();
    Integer capacite = paire.second();
    assertEquals("Nuit Électro", nom);
    assertEquals(500, capacite);
  }

  @Test
  void inverser_echange_les_deux_valeurs() {
    Paire<String, Integer> paire = new Paire<>("Nuit Électro", 500);
    Paire<Integer, String> inversee = paire.inverser();
    assertEquals(new Paire<>(500, "Nuit Électro"), inversee);
  }

  @Test
  void inverser_ne_modifie_pas_la_paire() {
    Paire<String, Integer> paire = new Paire<>("Nuit Électro", 500);
    paire.inverser();
    assertEquals(new Paire<>("Nuit Électro", 500), paire);
  }
}