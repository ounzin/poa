package fr.poa.tp3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class TrajetTest {

  private static final LocalDate JOUR = LocalDate.of(2026, 12, 1);

  @Test
  void trajet_est_un_record() {
    assertTrue(Trajet.class.isRecord());
  }

  @Test
  void un_trajet_garde_ses_valeurs() {
    Trajet trajet = new Trajet("Alice", JOUR, "Gare", "Bibliothèque", 12);
    assertEquals("Alice", trajet.usager());
    assertEquals(JOUR, trajet.jour());
    assertEquals("Gare", trajet.depart());
    assertEquals("Bibliothèque", trajet.arrivee());
    assertEquals(12, trajet.minutes());
  }

  @Test
  void deux_trajets_identiques_sont_egaux() {
    assertEquals(new Trajet("Alice", JOUR, "Gare", "Stade", 12), new Trajet("Alice", JOUR, "Gare", "Stade", 12));
  }

  @Test
  void une_valeur_absente_est_refusee() {
    assertThrows(NullPointerException.class, () -> new Trajet(null, JOUR, "Gare", "Stade", 12));
    assertThrows(NullPointerException.class, () -> new Trajet("Alice", null, "Gare", "Stade", 12));
  }

  @Test
  void un_usager_ou_une_station_vide_est_refuse() {
    assertThrows(IllegalArgumentException.class, () -> new Trajet("", JOUR, "Gare", "Stade", 12));
    assertThrows(IllegalArgumentException.class, () -> new Trajet("Alice", JOUR, "  ", "Stade", 12));
  }

  @Test
  void une_duree_de_moins_d_une_minute_est_refusee() {
    assertThrows(IllegalArgumentException.class, () -> new Trajet("Alice", JOUR, "Gare", "Stade", 0));
  }

  @Test
  void une_minute_suffit() {
    assertDoesNotThrow(() -> new Trajet("Alice", JOUR, "Gare", "Stade", 1));
  }
}
