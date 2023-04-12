package ca.mcmaster.cas.se2aa4.a3.island.specification.city;

import java.util.*;

public class NameGenerator {
    private Map<String, List<Character>> markovModel;
    private Random random;


    public NameGenerator(List<String> wordList) {
        markovModel = new HashMap<>();
        random = new Random();

        // Build the Markov model from the list of words
        for (String word : wordList) {
            for (int i = 0; i < word.length() - 1; i++) {
                // Extract the current prefix and suffix
                String currentPrefix = word.substring(i, i + 1);
                char currentSuffix = word.charAt(i + 1);

                // Add the suffix to the list of suffixes for this prefix
                List<Character> suffixList = markovModel.getOrDefault(currentPrefix, new ArrayList<>());
                suffixList.add(currentSuffix);
                markovModel.put(currentPrefix, suffixList);
            }
        }
    }


    public String generateName() {
        String name = "";

        // Choose a random starting prefix
        String currentPrefix = getRandomPrefix();
        name += currentPrefix;

        // Generate characters until we reach the end of a word or hit the maximum length
        while (name.length() < 10) {
            // Get the list of possible suffixes for the current prefix
            List<Character> suffixList = markovModel.get(currentPrefix);
            if (suffixList == null) {
                break;
            }

            // Choose a random suffix from the list
            char currentSuffix = suffixList.get(random.nextInt(suffixList.size()));
            name += currentSuffix;

            // Move the prefix over by one character
            currentPrefix = currentPrefix.substring(1) + currentSuffix;
        }

        return name;
    }


    private String getRandomPrefix() {
        List<String> prefixList = new ArrayList<>(markovModel.keySet());
        return prefixList.get(random.nextInt(prefixList.size()));
    }
}
