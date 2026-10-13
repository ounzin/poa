package fr.poa.tp1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HoraireTest {

  @Test
  void horaire_est_un_record() {
    assertTrue(Horaire.class.isRecord());
  }

  @Test
  void un_horaire_garde_ses_heures_et_ses_minutes() {
    Horaire horaire = new Horaire(8, 30);
    assertEquals(8, horaire.heures());
    assertEquals(30, horaire.minutes());
  }

  @Test
  void des_heures_hors_limites_sont_refusees() {
    assertThrows(IllegalArgumentException.class, () -> new Horaire(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> new Horaire(24, 0));
  }

  @Test
  void des_minutes_hors_limites_sont_refusees() {
    assertThrows(IllegalArgumentException.class, () -> new Horaire(10, -1));
    assertThrows(IllegalArgumentException.class, () -> new Horaire(10, 60));
  }

  @Test
  void en_minutes_compte_les_minutes_depuis_minuit() {
    assertEquals(0, new Horaire(0, 0).enMinutes());
    assertEquals(510, new Horaire(8, 30).enMinutes());
  }

  @Test
  void est_avant_est_une_comparaison_stricte() {
    assertTrue(new Horaire(8, 30).estAvant(new Horaire(9, 0)));
    assertFalse(new Horaire(9, 0).estAvant(new Horaire(8, 30)));
    assertFalse(new Horaire(9, 0).estAvant(new Horaire(9, 0)));
  }

  @Test
  void to_string_ecrit_les_heures_et_les_minutes_sur_deux_chiffres() {
    assertEquals("14:30", new Horaire(14, 30).toString());
    assertEquals("08:05", new Horaire(8, 5).toString());
  }

  @Test
  void deux_horaires_identiques_sont_egaux() {
    assertEquals(new Horaire(8, 30), new Horaire(8, 30));
  }
}