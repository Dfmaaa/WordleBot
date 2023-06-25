import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.util.Scanner;

public class ImprovedWordleBot {
    // Using this is okay because every word in the dictionary is unique.
    static int getIndex(String x, ArrayList<String> s) {
        for (int i = 0; i < s.size(); i++) {
            if (s.get(i).equals(x)) {
                return i;
            }
        }
        return -1;
    }

    // To be used in the correct letter hint check part.
    static int condition_meet(String candidate, String hint) {
        // hint is in the form of 0's and normal characters.
        char[] hint_array = hint.toCharArray(); // for the loop
        char[] candidate_array = candidate.toCharArray(); // for the loop
        for (int x = 0; x < hint.length(); x++) {
            if (hint_array[x] != '0') {
                if (candidate_array[x] != hint_array[x]) {
                    return -1;
                }
            }
        }
        return 0;
    }

    // To be used to update hint.
    static String change_index(String x, char y, int i) {
        char[] x_array = x.toCharArray();
        x_array[i] = y;
        return String.valueOf(x_array);
    }

    static ArrayList<String> read(String location) throws IOException {
        File f = new File(location);
        ArrayList<String> a = new ArrayList<String>();
        try (FileReader fileStream = new FileReader(f);
                BufferedReader bufferedReader = new BufferedReader(fileStream)) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                a.add(line.toLowerCase());
            }
        }
        return a;
    }

    public static void main(String[] args) throws IOException {

        /* Game constraints:
         * 1. 5 letter word. Super relevant
         * 2. 6 tries. Not relevant
         */
        if (args.length != 1) {
            System.out.println("Usage: java WordleBot [dictionary filename]");
            return;
        }

        ArrayList<Character> banned = new ArrayList<Character>();
        ArrayList<Character> present = new ArrayList<Character>();
        HashMap<Integer, Character> present_i=new HashMap<Integer, Character>();
        ArrayList<String> possible_words = read(args[0]);;
        String firstTry = "adieu"; // Has a lot of vowels.
        String complete_word = "00000";
        Scanner input = new Scanner(System.in);
        possible_words.remove(getIndex(firstTry, possible_words)); // removing the possibility, will be pushed to position 0.
        possible_words.add(0, firstTry); // Now we can use a general procedure to compute results for all words, including the first try.
        banned.add('`');
        banned.add('\'');

        // Removing every word except for 5-letter words.
        for (int i = 0; i < possible_words.size(); i++) {
            if (possible_words.get(i).length() != 5) {
                possible_words.remove(i);
                i--; // Decrement i after removing an element
            }
        }

        System.out.println("Starting. Possible words: " + possible_words.size());

        MainLoop:
        for (int i = 0; i < possible_words.size(); i++) {
            String candidate = possible_words.get(i);
            for (int j = 0; j < banned.size(); j++) {
                if (candidate.indexOf(banned.get(j)) != -1) {
                    possible_words.remove(i); // removing the word
                    i--;
                    continue MainLoop;
                }
            }

            for(int j = 0; j < present.size(); j++){
                if(candidate.indexOf(present.get(j)) == -1){
                    possible_words.remove(i); // removing the word
                    i--;
                    continue MainLoop;
                }
            }
            
            
            
            // If present, but not correct character is in the same position.
            for(int g=0;g<candidate.length();g++){
                if(present_i.get(g)!=null){
                    if(candidate.charAt(g)==present_i.get(g)){
                        possible_words.remove(i);
                        i--;
                        continue MainLoop;
                    }
                }
            }
            if(condition_meet(candidate, complete_word) == -1){
                possible_words.remove(i);
                i--;
                continue MainLoop;
            }

            if(condition_meet(candidate, complete_word) == -1){
                possible_words.remove(i);
                i--;
                continue MainLoop;
            }
            
            System.out.println("Possible words: "+possible_words.size()+ " words.");
            // We just removed the impossible words by using the hints given to us (banned, present, fixed).
            System.out.println("TRY: " + candidate); // The candidate variable now satisfies all conditions.
            char[] candidate_array = candidate.toCharArray();

            // getting more hints
            outerLoop:
            for (int l = 0; l < candidate_array.length; l++) {
                System.out.println("Is '" + candidate_array[l] + "' present(P), correct(C), not present(N)");
                String choice = input.next().toUpperCase();
                switch (choice.charAt(0)) {
                    case 'P':
                        present.add(candidate_array[l]);
                        present_i.put(l,candidate_array[l]); // Now we know where the character ISN'T.
                        break;
                    case 'C':
                        complete_word = change_index(complete_word, candidate_array[l], l);
                        break;
                    case 'N':
                        banned.add(candidate_array[l]);
                        break;
                    default:
                       possible_words.remove(i);
                        break outerLoop; // Break out of the outer loop
                }
            }
            i--;
        }
        System.out.println("Something is seriously wrong. The program should not be able to execute this.");
        input.close();
    }
}
