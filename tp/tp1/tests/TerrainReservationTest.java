package fr.poa.tp1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class TerrainReservationTest {

  private final Etudiant lea = new Etudiant("Léa Morel", "12345678");
  private final Abonne hugo = new Abonne("Hugo Petit", "AB-0042");

  private static Creneau creneau(int heureDebut, int minuteDebut, int heureFin, int minuteFin) {
    return new Creneau(new Horaire(heureDebut, minuteDebut), new Horaire(heureFin, minuteFin));
  }

  @Test
  void un_nouveau_terrain_n_a_aucune_reservation() {
    assertTrue(new Terrain("T1").reservations().isEmpty());
  }

  @Test
  void reserver_enregistre_et_renvoie_la_reservation() {
    Terrain terrain = new Terrain("T1");
    Reservation reservation = terrain.reserver(lea, creneau(10, 0, 11, 0));
    assertSame(lea, reservation.joueur());
    assertEquals(creneau(10, 0, 11, 0), reservation.creneau());
    assertEquals(List.of(reservation), terrain.reservations());
  }

  @Test
  void les_reservations_sont_dans_l_ordre_de_creation() {
    Terrain terrain = new Terrain("T1");
    Reservation premiere = terrain.reserver(lea, creneau(14, 0, 15, 0));
    Reservation seconde = terrain.reserver(hugo, creneau(10, 0, 11, 0));
    assertEquals(List.of(premiere, seconde), terrain.reservations());
  }

  @Test
  void un_creneau_qui_chevauche_une_reservation_est_refuse() {
    Terrain terrain = new Terrain("T1");
    Reservation existante = terrain.reserver(lea, creneau(10, 0, 11, 0));
    CreneauIndisponibleException exception = assertThrows(CreneauIndisponibleException.class,
        () -> terrain.reserver(hugo, creneau(10, 30, 11, 30)));
    assertEquals(existante, exception.conflit());
    assertEquals(List.of(existante), terrain.reservations());
  }

  @Test
  void l_exception_est_non_verifiee() {
    assertTrue(RuntimeException.class.isAssignableFrom(CreneauIndisponibleException.class),
        "CreneauIndisponibleException doit hériter de RuntimeException");
  }

  @Test
  void la_liste_des_reservations_n_est_pas_modifiable() {
    Terrain terrain = new Terrain("T1");
    terrain.reserver(lea, creneau(10, 0, 11, 0));
    assertThrows(UnsupportedOperationException.class,
        () -> terrain.reservations().add(new Reservation(hugo, creneau(14, 0, 15, 0))));
  }

  @Test
  void un_terrain_reste_retrouvable_apres_une_reservation() {
    Terrain terrain = new Terrain("T1");
    Set<Terrain> terrains = new HashSet<>();
    terrains.add(terrain);
    terrain.reserver(lea, creneau(10, 0, 11, 0));
    assertTrue(terrains.contains(terrain),
        "equals et hashCode ne doivent pas dépendre des réservations");
  }
}