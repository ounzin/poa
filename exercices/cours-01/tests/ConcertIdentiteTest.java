package fr.poa.cours01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class ConcertIdentiteTest {

  @Test
  void deux_concerts_de_meme_etat_sont_deux_objets_distincts() {
    Concert a = new Concert("Nuit Électro", 500);
    Concert b = new Concert("Nuit Électro", 500);
    assertNotSame(a, b);
    assertNotEquals(a, b);
  }

  @Test
  void deux_variables_peuvent_designer_le_meme_objet() {
    Concert a = new Concert("Nuit Électro", 500);
    Concert c = a;
    c.vendrePlace();
    assertSame(a, c);
    assertEquals(499, a.placesRestantes());
  }
}