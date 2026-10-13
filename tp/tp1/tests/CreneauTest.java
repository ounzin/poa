package fr.poa.tp1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CreneauTest {

  private static Creneau creneau(int heureDebut, int minuteDebut, int heureFin, int minuteFin) {
    return new Creneau(new Horaire(heureDebut, minuteDebut), new Horaire(heureFin, minuteFin));
  }

  @Test
  void creneau_est_un_record() {
    assertTrue(Creneau.class.isRecord());
  }

  @Test
  void un_creneau_a_un_debut_et_une_fin() {
    Creneau creneau = creneau(10, 0, 11, 30);
    assertEquals(new Horaire(10, 0), creneau.debut());
    assertEquals(new Horaire(11, 30), creneau.fin());
  }

  @Test
  void la_duree_est_en_minutes() {
    assertEquals(90, creneau(10, 0, 11, 30).dureeEnMinutes());
  }

  @Test
  void le_debut_doit_preceder_la_fin() {
    assertThrows(IllegalArgumentException.class, () -> creneau(11, 0, 10, 0));
    assertThrows(IllegalArgumentException.class, () -> creneau(10, 0, 10, 0));
  }

  @Test
  void le_debut_et_la_fin_sont_obligatoires() {
    assertThrows(NullPointerException.class, () -> new Creneau(null, new Horaire(11, 0)));
    assertThrows(NullPointerException.class, () -> new Creneau(new Horaire(10, 0), null));
  }

  @Test
  void deux_creneaux_qui_se_croisent_se_chevauchent() {
    Creneau a = creneau(10, 0, 11, 0);
    Creneau b = creneau(10, 30, 11, 30);
    assertTrue(a.chevauche(b));
    assertTrue(b.chevauche(a));
  }

  @Test
  void deux_creneaux_eloignes_ne_se_chevauchent_pas() {
    Creneau a = creneau(10, 0, 11, 0);
    Creneau b = creneau(14, 0, 15, 0);
    assertFalse(a.chevauche(b));
    assertFalse(b.chevauche(a));
  }
}