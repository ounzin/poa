package fr.poa.cours03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class SalleStatistiquesTest {

  private final Salle salle = new Salle('Z', 20);

  @Test
  void une_salle_vide_n_a_aucun_rang() {
    assertTrue(salle.reservationsParRang().isEmpty());
  }

  @Test
  void les_reservations_sont_comptees_par_rang() {
    salle.reserver(new Place('Q', 1), "Alice");
    salle.reserver(new Place('B', 4), "Bruno");
    salle.reserver(new Place('Q', 7), "Chloé");
    salle.reserver(new Place('Q', 3), "David");
    assertEquals(Map.of('B', 1, 'Q', 3), salle.reservationsParRang());
  }

  @Test
  void les_rangs_sont_dans_l_ordre_alphabetique() {
    salle.reserver(new Place('Q', 1), "Alice");
    salle.reserver(new Place('B', 4), "Bruno");
    assertEquals(List.of('B', 'Q'), List.copyOf(salle.reservationsParRang().keySet()));
  }
}