# Testing

Game tests live in `src/main/java/com/nanonaitor/forgedarmor/test/ForgedArmorGameTests.java` and run on a headless
server:

```bash
./gradlew runGameTestServer
```

They cover forging an attachment, swapping one in an occupied position, refusing the wrong armor piece, removing
every attachment, reading legacy Chest-slot Turtle Shell data, and checking that every item in the mod has a model,
the textures that model uses, and an English name. The server prints `All N required tests passed` and the Gradle
task fails if any test fails. The tests use the empty structure in `data/nanonaitor_forged_armor/structures/empty.nbt`.

ForgeGradle 6 needs Java 17 to run. If your default `java` is newer, point `JAVA_HOME` at a JDK 17 first.
