package fr.poa.cours01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

class ConcertCreationTest {

  @Test
  void un_nouveau_concert_a_toutes_ses_places_libres() {
    Concert concert = new Concert("Nuit Électro", 500);
    assertEquals(500, concert.placesRestantes());
  }

  @Test
  void une_capacite_nulle_est_refusee() {
    assertThrows(IllegalArgumentException.class, () -> new Concert("Nuit Électro", 0));
  }

  @Test
  void une_capacite_negative_est_refusee() {
    assertThrows(IllegalArgumentException.class, () -> new Concert("Nuit Électro", -10));
  }

  @Test
  void les_attributs_sont_prives() {
    for (Field attribut : Concert.class.getDeclaredFields()) {
      if (!attribut.isSynthetic()) {
        assertTrue(Modifier.isPrivate(attribut.getModifiers()),
            "L'attribut " + attribut.getName() + " doit être private");
      }
    }
  }

  @Test
  void aucun_setter() {
    for (Method methode : Concert.class.getMethods()) {
      assertFalse(methode.getName().startsWith("set"),
          methode.getName() + " permettrait de contourner les règles du concert");
    }
  }
}