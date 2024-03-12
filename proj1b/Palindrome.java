import java.util.Locale;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> list = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            list.addLast(word.charAt(i));
        }
        return list;
    }
    public boolean isPalindrome(String word) {
        Deque<Character> list = wordToDeque(word);
        while (list.size() > 1) {
            if (list.removeFirst() != list.removeLast()) {
                return false;
            }
        }
        return true;
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> list = wordToDeque(word);
        while (list.size() > 1) {
            if (!cc.equalChars(list.removeFirst(), list.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
