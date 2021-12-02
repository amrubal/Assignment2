import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Trie {

    public static class Node {
        Map<Character, Node> nodes;
        char ch;
        boolean valid;
        Long frq;

        public Node(char ch) {
            this.ch = ch;
            nodes = new HashMap<>();
        }

        public Node() {
            nodes = new HashMap<>();
        }

        public void insert(String word, List<Long> count, int i) {
            
            if (word == null || word.isEmpty())
                return;
            char ch1 = word.charAt(0);
            Node child = nodes.get(ch1);
            if (child == null) {
                child = new Node(ch1);
                nodes .put(ch1, child);
            }

            if (word.length() > 1)
                child.insert(word.substring(1), count, i);
            else {
                child.valid = true;
                child.frq = count.get(i);
            }
        }
    }

    public Node root;
    
    public void helper(Node root, List<String> listC, StringBuffer temp, HashMap<String, Long> test, HashMap<String, Long> newTest) {
        if (root.valid) { //if it exists
            test.put(String.valueOf(temp), root.frq);
            newTest.put(String.valueOf(temp), root.frq);
        }

        if (root.nodes == null || root.nodes.isEmpty()) //if empty
            return;

        for (Node child : root.nodes.values()) { //run through each node and its values
            helper(child, listC, temp.append(child.ch), test, newTest);
            temp.setLength(temp.length() - 1);
        }
    }


    public Trie(List<String> wordList, List<Long> count) {
        int i = 0;
        root = new Node();
        for (String word : wordList)
            root.insert(word, count, i++);
    }

    public boolean find(String prefix, boolean match) { //check for prefix match
        Node finalN = root;
        for (char ch : prefix.toCharArray()) {
            finalN = finalN.nodes.get(ch);
            if (finalN == null)
                return false;
        }
        return !match || finalN.valid;
    }

    public boolean find(String prefix) { //retruns other find method
        return find(prefix, false);
    }

    
    public List<List<String>> suggest(String token, int count) {
        List<List<String>> list = new ArrayList<>();
        List<String> listC = new ArrayList<>(count);
        HashMap<String, Long> test = new HashMap<>();
        HashMap<String, Long> newTest = new HashMap<>();


        Node finalN = root;
        StringBuffer temp = new StringBuffer();
        for (char ch : token.toCharArray()) {
            finalN = finalN.nodes.get(ch);
            if (finalN == null || temp.toString().equals(token))
                return list;
            temp.append(ch);
            helper(finalN, listC, temp, test, newTest);


            Map<String, Long> sorted = test //create map
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

            Set<String> keySet = sorted.keySet();
            List tempList = new ArrayList(keySet);

            for (int i = 0; i < count; i++) {
                if(tempList.size() < count) {
                    for (int j = 0; j < tempList.size(); j++) {
                        listC.add((String) tempList.get(i++));
                    }
                    List<String> dlist = list.get(list.size()-1);

                    List<String> conn = new ArrayList<String>(dlist); //connect lists 
                    conn.addAll(listC);
                    List<String> tempHold = new ArrayList<String>(dlist);
                    tempHold.retainAll(listC);
                    conn.removeAll(tempHold);

                    int connI = 0;
                    while(listC.size() < count)
                        listC.add(conn.get(connI++));

                    break;
                }
                else
                    listC.add((String) tempList.get(i));
            }
            list.add(listC);

            listC = new ArrayList<>();
            test = new HashMap<>();
        }
        helper(finalN, listC, temp, test, newTest);
        return list;
    }
}