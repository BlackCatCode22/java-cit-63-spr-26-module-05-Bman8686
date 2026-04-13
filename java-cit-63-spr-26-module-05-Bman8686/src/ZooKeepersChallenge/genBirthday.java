package ZooKeepersChallenge;

public class genBirthday
{import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

     {

        // Define the Animal class to store the parsed data
         public class ZooKeepersChallenge
         {static class Animal {
            String id;
            String species;
            String name;
            int age;
            LocalDate birthDate;
            String color;
            String sex;
            int weight;
            String sound;
            String origin;
            LocalDate arrivalDate;

            @Override
            public String toString() {
                return String.format("ID: %-5s | Species: %-6s | Name: %-8s | Age: %-2d | Birth Date: %s | Arrived: %s",
                        id, species, name, age, birthDate, arrivalDate);
            }
        }

        public static void main(String[] args) {
            // The raw data provided
            String rawData = """
                Hyena Habitat:
                Hy01; 4 years old; Zig; birthDate: 2020-03-21; tan color; female; 70 pounds; laugh: haha; from: from Friguia Park, Tunisia; arrived: 2024-04-07
                Hy02; 11 years old; Bud; birthDate: 2012-09-21; brown color; male; 150 pounds; laugh: hehe; from: from Friguia Park, Tunisia; arrived: 2024-04-07
                Hy03; 4 years old; Lou; birthDate: 2020-03-21; black color; male; 120 pounds; laugh: xaxa; from: from Friguia Park, Tunisia; arrived: 2024-04-07
                Hy04; 8 years old; Kamari; birthDate: 2016-01-01; black and tan striped color; female; 105 pounds; laugh: chacha; from: from Friguia Park, Tunisia; arrived: 2024-04-07
                
                Lion Habitat:
                Li01; 6 years old; Kiara; birthDate: 2018-03-21; tan color; female; 300 pounds; roar: Roarrr; from: from Zanzibar, Tanzania; arrived: 2024-04-07
                Li02; 11 years old; King; birthDate: 2012-12-21; dark tan color; female; 375 pounds; roar: RoooarRoooar; from: from KopeLion, Tanzania; arrived: 2024-04-07
                Li03; 21 years old; Drooper; birthDate: 2002-09-21; golden color; male; 450 pounds; roar: Roaaar!; from: from Zanzibar, Tanzania; arrived: 2024-04-07
                Li04; 4 years old; Kimba; birthDate: 2020-03-21; tan and brown color; female; 275 pounds; roar: Rrrrroarrrr; from: from KopeLion, Tanzania; arrived: 2024-04-07
                
                Tiger Habitat:
                Ti01; 2 years old; Cosimia; birthDate: 2022-03-21; gold and tan stripes color; male; 270 pounds; roar: Mew; from: from Dhaka, Bangladesh; arrived: 2024-04-07
                Ti02; 4 years old; Cuddles; birthDate: 2020-03-21; black stripes color; female; 400 pounds; roar: Meowww; from: from Dhaka, Bangladesh; arrived: 2024-04-07
                Ti03; 17 years old; Dave; birthDate: 2006-09-21; gold and tan color; male; 300 pounds; roar: Mrrrrew!; from: from Bardia, Nepal; arrived: 2024-04-07
                Ti04; 3 years old; Jiba; birthDate: 2021-03-21; black stripes color; female; 285 pounds; roar: Mew! Mew!; from: from Bardia, Nepal; arrived: 2024-04-07
                
                Bear Habitat:
                Be01; 7 years old; Lippy; birthDate: 2017-03-21; brown color; male; 320 pounds; roar: Grrrrr; from: from Alaska Zoo, Alaska; arrived: 2024-04-07
                Be02; 25 years old; Bungle; birthDate: 1999-03-21; black color; female; 425 pounds; roar: RrrrRrrr; from: from Woodland park Zoo, Washington ; arrived: 2024-04-07
                Be03; 3 years old; Baloo; birthDate: 2020-09-21; black color; female; 355 pounds; roar: Gruff!; from: from Woodland park Zoo, Washington ; arrived: 2024-04-07
                Be04; 4 years old; Rupert; birthDate: 2020-03-21; brown color; male; 405 pounds; roar: Rrruff; from: from Alaska Zoo, Alaska; arrived: 2024-04-07
                """;

            List<Animal> animals = parseAnimalData(rawData);

            // Display the parsed animals
            System.out.println("--- Zoo Animal Roster ---");
            for (Animal a : animals) {
                System.out.println(a.toString());
            }
        }

        private static List<Animal> parseAnimalData(String data) {
            List<Animal> animalList = new ArrayList<>();
            String[] lines = data.split("\\r?\\n");
            String currentSpecies = "Unknown";

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Track the current habitat to assign the species
                if (line.endsWith("Habitat:")) {
                    currentSpecies = line.replace(" Habitat:", "").trim();
                    continue;
                }

                // Parse individual animal records
                String[] parts = line.split(";\\s*");
                if (parts.length < 10) continue; // Skip malformed lines

                Animal animal = new Animal();
                animal.species = currentSpecies;
                animal.id = parts[0];

                // Extract Age (e.g., "4 years old" -> 4)
                animal.age = Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));
                animal.name = parts[2];

                // Extract Arrival Date (Needed as a fallback for deriving birthdate)
                String arrivalStr = parts[9].replace("arrived: ", "").trim();
                animal.arrivalDate = LocalDate.parse(arrivalStr, DateTimeFormatter.ISO_LOCAL_DATE);

                // Extract or Derive Birth Date
                String birthDateStr = parts[3];
                animal.birthDate = deriveBirthDate(birthDateStr, animal.age, animal.arrivalDate);

                // Extract remaining attributes
                animal.color = parts[4].replace(" color", "");
                animal.sex = parts[5];
                animal.weight = Integer.parseInt(parts[6].replaceAll("[^0-9]", ""));
                animal.sound = parts[7];
                animal.origin = parts[8].replace("from: ", "");

                animalList.add(animal);
            }

            return animalList;
        }

        /**
         * Derives the ISO 8601 birth date.
         * Parses the date if explicitly provided. If missing or only a season/age is known,
         * it estimates the birth year and defaults to January 1st.
         */
        private static LocalDate deriveBirthDate(String birthDateStr, int age, LocalDate arrivalDate) {
            try {
                if (birthDateStr.startsWith("birthDate: ")) {
                    String datePart = birthDateStr.replace("birthDate: ", "").trim();
                    return LocalDate.parse(datePart, DateTimeFormatter.ISO_LOCAL_DATE);
                }
            } catch (DateTimeParseException e) {
                System.err.println("Could not parse explicit date. Falling back to derivation.");
            }

            // Fallback Logic: If exact date/season is missing, derive from age and arrival year.
            // We default to January 1st (01-01) of the calculated birth year to maintain ISO 8601 formatting.
            int birthYear = arrivalDate.getYear() - age;
            return LocalDate.of(birthYear, 1, 1);
        }