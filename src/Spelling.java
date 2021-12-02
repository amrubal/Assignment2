import java.util.*;

public class Spelling { 
    //prints everything out 

    public List<List<String>> suggest(String token, int count) {
        Assign2 a2 = new Assign2();

        String unigramF = "/Users/angelarubalcava/Desktop/school/CS245/unigram_freq.csv";
        List<String> words = new ArrayList<>();
        List<Long> freq = new ArrayList<>();
        a2.read(unigramF, words, freq);

        String misspelledF = "/Users/angelarubalcava/Desktop/school/CS245/misspelling.csv";
        List<String> wrongWord = new ArrayList<>();
        a2.read(misspelledF, wrongWord);

        Trie trie = new Trie(words, freq);

        for (int i = 0; i < wrongWord.size(); i++) {
            System.out.println("MISSPELLED: ");
            System.out.println(wrongWord.get(i));
            System.out.println("**********************");
            System.out.println(trie.suggest(wrongWord.get(i).toLowerCase(), 3)); //prints out both 3
            System.out.println(trie.suggest(wrongWord.get(i).toLowerCase(), 7)); //and 7
            System.out.println();
        }

        System.out.println(token);
        System.out.println("**********************");

        return trie.suggest(token, count);
    }

    public static void main(String[] args) {
        Spelling spell = new Spelling();
        System.out.println(spell.suggest("onomatopoeia", 5));
    }
}
