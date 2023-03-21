package ca.mcmaster.cas.se2aa4.a3.island.configuration;

import java.util.Random;

public class Seed {

    private final long seed;

    public Seed(){
        //New random seed
        Random random = new Random();
        this.seed = random.nextLong(100);
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
}
