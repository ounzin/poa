package fr.poa.td2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class HorlogeTest {

  @Test
  void horloge_est_une_interface() {
    assertTrue(Horloge.class.isInterface());
  }

  @Test
  void l_horloge_fixe_donne_toujours_le_meme_jour() {
    Horloge horloge = new HorlogeFixe(LocalDate.of(2026, 11, 30));
    assertEquals(LocalDate.of(2026, 11, 30), horloge.aujourdhui());
    assertEquals(LocalDate.of(2026, 11, 30), horloge.aujourdhui());
  }

  @Test
  void l_horloge_systeme_donne_le_jour_courant() {
    LocalDate avant = LocalDate.now();
    LocalDate jour = new HorlogeSysteme().aujourdhui();
    LocalDate apres = LocalDate.now();
    assertTrue(!jour.isBefore(avant) && !jour.isAfter(apres));
  }

  @Test
  void une_lambda_peut_servir_d_horloge() {
    Horloge horloge = () -> LocalDate.of(2026, 1, 1);
    assertEquals(LocalDate.of(2026, 1, 1), horloge.aujourdhui());
  }

  @Test
  void une_horloge_fixe_refuse_un_jour_absent() {
    assertThrows(NullPointerException.class, () -> new HorlogeFixe(null));
  }
}
