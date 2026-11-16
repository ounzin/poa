package fr.poa.td1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class FestivalSpectateursTest {

  private final Festival festival = new Festival(List.of(
      new Billet("Jazz au parc", "Bruno", 30),
      new Billet("Nuit Électro", "Chloé", 45),
      new Billet("Jazz au parc", "Alice", 25),
      new Billet("Nuit Électro", "Alice", 45),
      new Billet("Rock en scène", "David", 60),
      new Billet("Jazz au parc", "Bruno", 30)));

  private final Festival vide = new Festival(List.of());

  @Test
  void les_spectateurs_d_un_concert_dans_l_ordre_alphabetique() {
    assertEquals(List.of("Alice", "Chloé"), festival.spectateursDe("Nuit Électro"));
  }

  @Test
  void un_spectateur_qui_a_deux_billets_n_apparait_qu_une_fois() {
    assertEquals(List.of("Alice", "Bruno"), festival.spectateursDe("Jazz au parc"));
  }

  @Test
  void un_concert_sans_billet_n_a_aucun_spectateur() {
    assertTrue(festival.spectateursDe("Opéra d'été").isEmpty());
  }

  @Test
  void la_liste_des_spectateurs_n_est_pas_modifiable() {
    assertThrows(UnsupportedOperationException.class, () -> festival.spectateursDe("Nuit Électro").add("Emma"));
  }

  @Test
  void compter_les_billets_d_un_concert() {
    assertEquals(3, festival.nombreDeBillets("Jazz au parc"));
    assertEquals(1, festival.nombreDeBillets("Rock en scène"));
    assertEquals(0, festival.nombreDeBillets("Opéra d'été"));
  }

  @Test
  void le_festival_garde_sa_propre_copie_des_billets() {
    List<Billet> billets = new ArrayList<>(List.of(new Billet("Jazz au parc", "Alice", 25)));
    Festival copie = new Festival(billets);
    billets.clear();
    assertEquals(1, copie.nombreDeBillets("Jazz au parc"));
  }
}
