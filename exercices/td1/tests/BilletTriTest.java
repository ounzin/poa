package fr.poa.td1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class BilletTriTest {

  private final List<Billet> billets = new ArrayList<>(List.of(
      new Billet("Nuit Électro", "Chloé", 45),
      new Billet("Jazz au parc", "Bruno", 30),
      new Billet("Rock en scène", "David", 60),
      new Billet("Nuit Électro", "Alice", 45),
      new Billet("Jazz au parc", "Alice", 25)));

  @Test
  void trier_par_concert_puis_par_spectateur() {
    billets.sort(Billet.PAR_CONCERT_PUIS_SPECTATEUR);
    assertEquals(List.of(
        new Billet("Jazz au parc", "Alice", 25),
        new Billet("Jazz au parc", "Bruno", 30),
        new Billet("Nuit Électro", "Alice", 45),
        new Billet("Nuit Électro", "Chloé", 45),
        new Billet("Rock en scène", "David", 60)), billets);
  }

  @Test
  void trier_du_plus_cher_au_moins_cher() {
    billets.sort(Billet.DU_PLUS_CHER_AU_MOINS_CHER);
    assertEquals(List.of(60, 45, 45, 30, 25), billets.stream().map(Billet::prix).toList());
  }

  @Test
  void a_prix_egal_les_spectateurs_sont_dans_l_ordre_alphabetique() {
    billets.sort(Billet.DU_PLUS_CHER_AU_MOINS_CHER);
    assertEquals(new Billet("Nuit Électro", "Alice", 45), billets.get(1));
    assertEquals(new Billet("Nuit Électro", "Chloé", 45), billets.get(2));
  }
}
