package ca.mcmaster.cas.se2aa4.a3.island.configuration;

import java.util.Random;

public class Seed {

    private long seed;

    public Seed(){
        //New random seed
        Random random = new Random();
        // Loop until the random value does not contain a zero
        do {
            this.seed = random.nextLong(90) + 10;
        } while (containsZeroOrOne(this.seed));
    }

    public Seed(long seed){
        this.seed = seed;
    }

    public Seed(String seedStr){
        this(Long.parseLong(seedStr));
    }

    public long getSeed(){
        return seed;
    }

    // Check if number contains a 0 or 1
    public boolean containsZeroOrOne(long seed){
        while (seed != 0) {
            long digit = seed % 10;
            if (digit == 0 || digit == 1) {
                return true;
            }
            seed /= 10;
        }
        return false;
    }

    public long validateSeed(long seed){
        int last = (int) seed % 10;
        int secondLast = (int) (seed / 10) % 10;
        if (last == 0){
            last+=2;
        }
        if (secondLast == 0){
            secondLast+=2;
        }
        if (last == 1){
            last+=1;
        }
        if (secondLast == 1){
            secondLast+=1;
        }
        String combined = secondLast+""+last;
        this.seed = Long.parseLong(combined);
        if (seed == 0){
            this.seed = 22;
        }
        return this.seed;
    }
}
