package ZooKeepersChallenge;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


    public class theZoo { public static void main(String[] args) throws FileNotFoundException {
        ArrayList<ZooKeepersChallenge.Animal> animalList = new ArrayList<>();
        HashMap<String, Integer> speciesCount = new HashMap<>();

        // File paths
        String arrivingFileFile = "arrivingAnimals.txt";
        String namesFile = "animalNames.txt";
        String reportFile = "newAnimals.txt";

        // Step 1: Process Animal Data (Simplified Logic)
        String arrivingFile = null;
        try (Scanner scanner = new Scanner(new File(arrivingFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Assume format: "4 year old female hyena..."
                String[] parts = line.split(" ");
                int age = Integer.parseInt(parts[0]);
                String species = parts[4].toLowerCase();

                // Polymorphism: Instantiate specific subclasses based on species
                Animal animal;
                String name = "PlaceholderName"; // In a full app, pull from animalNames.txt

                if (species.contains("hyena")) animal = new Hyena(name, age);
                else if (species.contains("lion")) animal = new Lion(name, age);
                else if (species.contains("tiger")) animal = new Tiger(name, age);
                else if (species.contains("bear")) animal = new Bear(name, age);
                else animal = new Animal(name, age, species);

                animalList.add(animal);

                // Update HashMap count
                speciesCount.put(animal.getSpecies(), speciesCount.getOrDefault(animal.getSpecies(), 0) + 1);
            }
        }  {
            System.out.println("Error: Input file not found.");
        }

        // Step 2: Generate Report
        try (PrintWriter writer = new PrintWriter(new FileWriter(reportFile))) {
            writer.println("Zoo Population Report");
            writer.println("======================");

            // Group by species for the report
            String[] speciesOrder = {"Hyena", "Lion", "Tiger", "Bear"};
            for (String s : speciesOrder) {
                writer.println(s + " Habitat:");
                for (Animal a : animalList) {
                    if (a.getSpecies().equalsIgnoreCase(s)) {
                        writer.println(" - " + a.getName() + ", " + a.getAge() + " years old");
                    }
                }
                writer.println("Total " + s + "s: " + speciesCount.getOrDefault(s, 0));
                writer.println();
            }
            System.out.println("Report generated successfully in " + reportFile);
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }
    }




