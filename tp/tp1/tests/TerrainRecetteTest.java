package fr.poa.tp1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TerrainRecetteTest {

  private final Abonne hugo = new Abonne("Hugo Petit", "AB-0042");
  private final Etudiant lea = new Etudiant("Léa Morel", "12345678");
  private final Invite sami = new Invite("Sami Diallo", hugo);

  private static Creneau creneau(int heureDebut, int minuteDebut, int heureFin, int minuteFin) {
    return new Creneau(new Horaire(heureDebut, minuteDebut), new Horaire(heureFin, minuteFin));
  }

  @Test
  void un_terrain_sans_reservation_n_a_aucune_recette() {
    assertEquals(Montant.ZERO, new Terrain("T1").recette());
  }

  @Test
  void la_recette_additionne_les_prix_des_reservations() {
    Terrain terrain = new Terrain("T1");
    terrain.reserver(lea, creneau(10, 0, 11, 0));
    terrain.reserver(sami, creneau(14, 0, 15, 0));
    terrain.reserver(hugo, creneau(16, 0, 17, 0));
    assertEquals(new Montant(2150), terrain.recette());
  }
}