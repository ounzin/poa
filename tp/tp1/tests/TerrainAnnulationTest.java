package fr.poa.tp1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class TerrainAnnulationTest {

  private final Etudiant lea = new Etudiant("Léa Morel", "12345678");
  private final Abonne hugo = new Abonne("Hugo Petit", "AB-0042");

  private static Creneau creneau(int heureDebut, int minuteDebut, int heureFin, int minuteFin) {
    return new Creneau(new Horaire(heureDebut, minuteDebut), new Horaire(heureFin, minuteFin));
  }

  @Test
  void annuler_retire_la_reservation() {
    Terrain terrain = new Terrain("T1");
    Reservation matin = terrain.reserver(lea, creneau(10, 0, 11, 0));
    Reservation apresMidi = terrain.reserver(hugo, creneau(14, 0, 15, 0));
    terrain.annuler(matin);
    assertEquals(List.of(apresMidi), terrain.reservations());
  }

  @Test
  void annuler_une_reservation_inconnue_est_refuse() {
    Terrain terrain = new Terrain("T1");
    terrain.reserver(lea, creneau(10, 0, 11, 0));
    Reservation inconnue = new Reservation(hugo, creneau(14, 0, 15, 0));
    assertThrows(IllegalArgumentException.class, () -> terrain.annuler(inconnue));
    assertEquals(1, terrain.reservations().size());
  }

  @Test
  void un_creneau_annule_peut_etre_reserve_a_nouveau() {
    Terrain terrain = new Terrain("T1");
    Reservation reservation = terrain.reserver(lea, creneau(10, 0, 11, 0));
    terrain.annuler(reservation);
    Reservation nouvelle = terrain.reserver(hugo, creneau(10, 0, 11, 0));
    assertTrue(terrain.reservations().contains(nouvelle));
  }
}