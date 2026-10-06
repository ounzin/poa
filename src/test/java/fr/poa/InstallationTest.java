package fr.poa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class InstallationTest {
  @Test
  void java_25_ou_plus() {
    int version = Runtime.version().feature();
    assertTrue(version >= 25,
        "Java " + version + " détecté, Java 25 attendu : vérifiez JAVA_HOME (voir INSTALLATION.md)");
  }
}