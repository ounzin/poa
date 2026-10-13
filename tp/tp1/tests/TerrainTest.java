package fr.poa.tp1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class TerrainTest {

  @Test
  void un_terrain_a_un_code() {
    assertEquals("T1", new Terrain("T1").code());
  }

  @Test
  void le_code_est_obligatoire() {
    assertThrows(NullPointerException.class, () -> new Terrain(null));
  }

  @Test
  void deux_terrains_de_meme_code_sont_egaux() {
    assertEquals(new Terrain("T1"), new Terrain("T1"));
    assertEquals(new Terrain("T1").hashCode(), new Terrain("T1").hashCode());
  }

  @Test
  void deux_terrains_de_codes_differents_sont_differents() {
    assertNotEquals(new Terrain("T1"), new Terrain("T2"));
  }

  @Test
  void un_terrain_n_est_egal_ni_a_null_ni_a_un_autre_type() {
    Terrain terrain = new Terrain("T1");
    assertFalse(terrain.equals(null));
    assertFalse(terrain.equals("T1"));
  }

  @Test
  void un_ensemble_ne_garde_qu_un_terrain_par_code() {
    Set<Terrain> terrains = new HashSet<>();
    terrains.add(new Terrain("T1"));
    terrains.add(new Terrain("T1"));
    assertEquals(1, terrains.size());
  }

  @Test
  void les_attributs_sont_prives() {
    for (Field attribut : Terrain.class.getDeclaredFields()) {
      if (!attribut.isSynthetic()) {
        assertTrue(Modifier.isPrivate(attribut.getModifiers()),
            "L'attribut " + attribut.getName() + " doit être private");
      }
    }
  }

  @Test
  void aucun_setter() {
    for (Method methode : Terrain.class.getMethods()) {
      assertFalse(methode.getName().startsWith("set"),
          methode.getName() + " permettrait de contourner les règles du terrain");
    }
  }
}