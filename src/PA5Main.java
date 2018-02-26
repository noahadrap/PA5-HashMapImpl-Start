import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeSet;
/*
 * PA5Main
 * @author
 * Noah Drap (noahdrap@email.arizona.edu)
 * 
 * This program uses a custom made HashMap to sort keys and values based on
 * the arguments placed in.
 */
public class PA5Main {

    /*
     * public static void main(String[] args)
     * 
     * This method reads in the file that has been placed in args and runs other
     * methods based on
     * what the action is in args[1]
     */
    public static void main(String[] args) {
        // initializes the file input
        Scanner input = null;
        try {
            // reads in the file
            input = new Scanner(new File(args[0]));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // saves the action in which the program is supposed to perform
        String command = args[1];

        // prints out all airports that appear more than the number in the
        // argument
        if (command.equals("LIMIT")) {
            MyHashMap<String, Integer> flights = countDepartures(input);
            ArrayList<String> airportsSorted = new ArrayList<String>(flights.keySet());
            Collections.sort(airportsSorted);
            for (String key : airportsSorted) {
                int numFlights = flights.get(key);
                int limit = Integer.valueOf(args[2]);
                if (numFlights > limit) {
                    System.out.println(key + " - " + numFlights);
                }
            }

        }
        if (command.equals("MAX")) {
            int max = 0;
            TreeSet<String> maxAirline = new TreeSet<String>();
            String airline = null;

            MyHashMap<String, Integer> flights = countDepartures(input);
            for (MyHashMap.Node<String, Integer> entry : flights.entrySet()) {
                int val = entry.getValue();
                if (val > max) {
                    max = val;
                    maxAirline.add(entry.getKey());
                }
            }
            System.out.print("MAX FLIGHTS " + max + " : ");
            for (String s : maxAirline) {
                System.out.print(s + " ");
            }
        }
        if (command.equals("DEPARTURES")) {
            flightDestination(input);

        }
        if (command.equals("DEBUG")) {
            MyHashMap<String, Integer> flights = countDepartures(input);
            flights.printBucket();
            }

        }


    // prints the airport that has the most amount of flights


    public static MyHashMap<String, Integer> countDepartures(Scanner input) {
        // creates a HashMap of all airports and makes the value the number of
        // how many times
        // the airport appears in the file
        MyHashMap<String, Integer> airportToNumFlights = new MyHashMap<String, Integer>();
        boolean first = true;

        while (input.hasNextLine()) {
            String[] line = input.nextLine().split(",");

            if (first == true) {
                first = false;
                continue;
            }
            if (airportToNumFlights.get(line[2]) == null) {
                airportToNumFlights.put(line[2], 1);


            } else {
                int flights = airportToNumFlights.get(line[2]);

                flights++;

                airportToNumFlights.put(line[2], flights);

            }

            if (airportToNumFlights.get(line[4]) == null) {
                airportToNumFlights.put(line[4], 1);
            } else {
                int flights = airportToNumFlights.get(line[4]);
                flights++;
                airportToNumFlights.put(line[4], flights);






            }
        }
        airportToNumFlights.entrySet();
        return airportToNumFlights;
    }

    public static void flightDestination(Scanner input) {
        // makes a map of every airport and the value every airport that flights
        // fly to
        MyHashMap<String, TreeSet> Destinations = new MyHashMap<String, TreeSet>();
        TreeSet<String> airports = new TreeSet<String>();
        // skips the first line
        boolean first = true;
        while (input.hasNextLine()) {
            String[] line = input.nextLine().split(",");
            if (first == true) {
                first = false;
                continue;
            }
            // Makes sure that the destination is not repeated
            if (Destinations.get(line[2]) == null) {
                airports = new TreeSet<String>();
                airports.add(line[4]);
                Destinations.put(line[2], airports);
            } else {
                airports = Destinations.get(line[2]);
                if (airports.contains(line[4])) {
                    continue;
                } else {
                    airports.add(line[4]);
                    Destinations.put(line[2], airports);
                }
            }

        }
        // Sorts the map in alphabetical order
        // prints every airport flights fly from and to
        TreeSet<String> keyset = Destinations.keySet();
        for (String key : keyset) {
            TreeSet<String> treeSet = Destinations.get(key);
            System.out.println(key + " flies to " + String.join(" ", treeSet));

            }

        return;
    }

}


