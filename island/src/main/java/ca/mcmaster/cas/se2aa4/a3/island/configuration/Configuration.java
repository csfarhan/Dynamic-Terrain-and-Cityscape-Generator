package ca.mcmaster.cas.se2aa4.a3.island.configuration;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    //List of option names
    public static final String INPUT = "i";
    public static final String OUTPUT = "o";
    public static final String SHAPE = "s";
    public static final String ELEVATION = "e";
    public static final String DEBUG = "d";
    public static final String HELP = "help";

    private CommandLine cli;

    //Constructor
    public Configuration(String[] args) {
        try {
            this.cli = parser().parse(options(), args);
            if (cli.hasOption(HELP)) {
                help();
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    //Help message to print if incorrect syntax used
    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar island.jar", options());
        System.exit(0);
    }

    //Export HashMap containing all chosen options
    public Map<String, String> export() {
        Map<String, String> result = new HashMap<>();
        for(Option o: cli.getOptions()){
            result.put(o.getOpt(), o.getValue(""));
        }
        return result;
    }

    //Get just the specified chosen option
    public String export(String key) {
        return cli.getOptionValue(key);
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }

    public String input() {
        return this.cli.getOptionValue(INPUT);
    }

    public String output() {
        return this.cli.getOptionValue(OUTPUT, "output.mesh");
    }

    public boolean debug() { return this.cli.hasOption(DEBUG); }

    private Options options() {
        Options options = new Options();
        options.addOption(new Option(INPUT, true, "Input file (MESH)"));
        options.addOption(new Option(OUTPUT, true, "Output file (MESH)"));
        options.addOption(new Option(SHAPE, true, "Shape of the island"));
        options.addOption(new Option(ELEVATION, true, "Elevation of the island"));
        options.addOption(new Option(DEBUG, false, "Debug mode"));
        options.addOption(new Option(HELP, false, "print help message"));
        return options;
    }
}
