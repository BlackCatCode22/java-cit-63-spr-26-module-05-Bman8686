package ZooKeepersChallenge;

public class genUniqueID {import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    // Class to represent individual animals
    class Animal {
        private String species;
        private String id;

        public Animal(String species, String id) {
            this.species = species;
            this.id = id;
        }

        public String getSpecies() {
            return species;
        }

        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Species: " + species + " | ID: " + id;
        }
    }

    // Class to handle the deterministic generation of sequential IDs
    class IdGenerator {
        private Map<String, Integer> speciesCounter;

        public IdGenerator() {
            speciesCounter = new HashMap<>();
        }

        public String generateId(String species, String prefix) {
            // Retrieve the current count for the species, increment by 1
            int count = speciesCounter.getOrDefault(species, 0) + 1;
            speciesCounter.put(species, count);

            // Format the string to include the prefix and a 2-digit padded number
            return String.format("%s%02d", prefix, count);
        }
    }

    public class AnimalIdSystem {
        public static void main(String[] args) {
            List<Animal> zoo = new ArrayList<>();
            IdGenerator idGen = new IdGenerator();

            // Create 4 Hyenas (Prefix: Hy)
            for (int i = 0; i < 4; i++) {
                zoo.add(new Animal("Hyena", idGen.generateId("Hyena", "Hy")));
            }

            // Create 4 Lions (Prefix: Li)
            for (int i = 0; i < 4; i++) {
                zoo.add(new Animal("Lion", idGen.generateId("Lion", "Li")));
            }

            // Create 4 Tigers (Prefix: Ti)
            for (int i = 0; i < 4; i++) {
                zoo.add(new Animal("Tiger", idGen.generateId("Tiger", "Ti")));
            }

            // Create 4 Bears (Prefix: Be)
            for (int i = 0; i < 4; i++) {
                zoo.add(new Animal("Bear", idGen.generateId("Bear", "Be")));
            }

            // Print the results to verify unique, properly formatted IDs
            System.out.println("--- Generated Animal Roster ---");
            for (Animal animal : zoo) {
                System.out.println(animal.toString());
            }
        }
    }
}
