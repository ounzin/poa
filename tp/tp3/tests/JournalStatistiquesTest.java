package fr.poa.tp3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class JournalStatistiquesTest {

  private final Journal journal = new Journal(List.of(
      new Trajet("Alice", LocalDate.of(2026, 11, 30), "Gare", "Bibliothèque", 12),
      new Trajet("Bruno", LocalDate.of(2026, 11, 30), "Résidence", "Stade", 45),
      new Trajet("Alice", LocalDate.of(2026, 12, 1), "Bibliothèque", "Gare", 15),
      new Trajet("Chloé", LocalDate.of(2026, 12, 1), "Gare", "Stade", 70),
      new Trajet("Bruno", LocalDate.of(2026, 12, 2), "Stade", "Résidence", 45),
      new Trajet("David", LocalDate.of(2026, 12, 3), "Gare", "Résidence", 31)));

  private final Journal vide = new Journal(List.of());

  @Test
  void le_trajet_le_plus_long() {
    assertEquals(Optional.of(new Trajet("Chloé", LocalDate.of(2026, 12, 1), "Gare", "Stade", 70)), journal.plusLong());
  }

  @Test
  void un_journal_vide_n_a_pas_de_trajet_le_plus_long() {
    assertEquals(Optional.empty(), vide.plusLong());
  }

  @Test
  void la_duree_moyenne() {
    assertEquals(218.0 / 6, journal.dureeMoyenne(), 0.001);
  }

  @Test
  void la_duree_moyenne_d_un_journal_vide_vaut_zero() {
    assertEquals(0.0, vide.dureeMoyenne(), 0.001);
  }

  @Test
  void les_plus_longs_du_plus_long_au_plus_court() {
    assertEquals(List.of(70, 45, 45, 31), journal.lesPlusLongs(4).stream().map(Trajet::minutes).toList());
  }

  @Test
  void a_duree_egale_les_trajets_gardent_l_ordre_du_journal() {
    List<Trajet> plusLongs = journal.lesPlusLongs(3);
    assertEquals(LocalDate.of(2026, 11, 30), plusLongs.get(1).jour());
    assertEquals(LocalDate.of(2026, 12, 2), plusLongs.get(2).jour());
  }

  @Test
  void demander_plus_de_trajets_que_le_journal_n_en_a() {
    assertEquals(6, journal.lesPlusLongs(10).size());
    assertTrue(vide.lesPlusLongs(3).isEmpty());
  }

  @Test
  void un_nombre_negatif_est_refuse() {
    assertThrows(IllegalArgumentException.class, () -> journal.lesPlusLongs(-1));
  }
}
