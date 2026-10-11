package fr.poa.cours02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ConcertEgaliteTest {

  @Test
  void un_concert_a_un_code_un_nom_et_une_capacite() {
    Concert concert = new Concert("NE-2026", "Nuit Électro", 500);
    assertEquals("NE-2026", concert.code());
    assertEquals("Nuit Électro", concert.nom());
    assertEquals(500, concert.placesRestantes());
  }

  @Test
  void une_capacite_nulle_est_refusee() {
    assertThrows(IllegalArgumentException.class, () -> new Concert("NE-2026", "Nuit Électro", 0));
  }

  @Test
  void le_code_est_obligatoire() {
    assertThrows(NullPointerException.class, () -> new Concert(null, "Nuit Électro", 500));
  }

  @Test
  void deux_concerts_de_meme_code_sont_egaux() {
    Concert a = new Concert("NE-2026", "Nuit Électro", 500);
    Concert b = new Concert("NE-2026", "Nuit Électro (nouvelle salle)", 800);
    assertEquals(a, b);
  }

  @Test
  void deux_concerts_de_codes_differents_sont_differents() {
    Concert a = new Concert("NE-2026", "Nuit Électro", 500);
    Concert b = new Concert("JZ-2026", "Nuit Électro", 500);
    assertNotEquals(a, b);
  }

  @Test
  void deux_concerts_egaux_ont_le_meme_hashcode() {
    Concert a = new Concert("NE-2026", "Nuit Électro", 500);
    Concert b = new Concert("NE-2026", "Nuit Électro (nouvelle salle)", 800);
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  void un_concert_n_est_egal_ni_a_null_ni_a_un_autre_type() {
    Concert concert = new Concert("NE-2026", "Nuit Électro", 500);
    assertFalse(concert.equals(null));
    assertFalse(concert.equals("NE-2026"));
  }

  @Test
  void un_ensemble_ne_garde_qu_un_concert_par_code() {
    Set<Concert> concerts = new HashSet<>();
    concerts.add(new Concert("NE-2026", "Nuit Électro", 500));
    concerts.add(new Concert("NE-2026", "Nuit Électro (nouvelle salle)", 800));
    assertEquals(1, concerts.size());
    assertTrue(concerts.contains(new Concert("NE-2026", "Nuit Électro", 500)));
  }
}