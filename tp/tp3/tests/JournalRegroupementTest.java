package fr.poa.tp3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class JournalRegroupementTest {

  private final Journal journal = new Journal(List.of(
      new Trajet("Alice", LocalDate.of(2026, 11, 30), "Gare", "Bibliothèque", 12),
      new Trajet("Bruno", LocalDate.of(2026, 11, 30), "Résidence", "Stade", 45),
      new Trajet("Alice", LocalDate.of(2026, 12, 1), "Bibliothèque", "Gare", 15),
      new Trajet("Chloé", LocalDate.of(2026, 12, 1), "Gare", "Stade", 70),
      new Trajet("Bruno", LocalDate.of(2026, 12, 2), "Stade", "Résidence", 45),
      new Trajet("David", LocalDate.of(2026, 12, 3), "Gare", "Résidence", 31)));

  @Test
  void compter_les_departs_de_chaque_station() {
    assertEquals(Map.of("Gare", 3L, "Résidence", 1L, "Bibliothèque", 1L, "Stade", 1L), journal.departsParStation());
  }

  @Test
  void les_minutes_de_chaque_usager() {
    assertEquals(Map.of("Alice", 27, "Bruno", 90, "Chloé", 70, "David", 31), journal.minutesParUsager());
  }

  @Test
  void les_usagers_sont_dans_l_ordre_alphabetique() {
    assertEquals(List.of("Alice", "Bruno", "Chloé", "David"), List.copyOf(journal.minutesParUsager().keySet()));
  }

  @Test
  void la_station_la_plus_frequentee() {
    assertEquals(Optional.of("Gare"), journal.stationLaPlusFrequentee());
  }

  @Test
  void a_egalite_la_premiere_station_dans_l_ordre_alphabetique() {
    LocalDate jour = LocalDate.of(2026, 12, 1);
    Journal egalite = new Journal(List.of(
        new Trajet("Alice", jour, "Stade", "Gare", 10),
        new Trajet("Bruno", jour, "Bibliothèque", "Gare", 10),
        new Trajet("Chloé", jour, "Stade", "Gare", 10),
        new Trajet("David", jour, "Gare", "Stade", 10),
        new Trajet("Emma", jour, "Bibliothèque", "Stade", 10)));
    assertEquals(Optional.of("Bibliothèque"), egalite.stationLaPlusFrequentee());
  }

  @Test
  void un_journal_vide_n_a_pas_de_station_la_plus_frequentee() {
    assertEquals(Optional.empty(), new Journal(List.of()).stationLaPlusFrequentee());
  }
}
