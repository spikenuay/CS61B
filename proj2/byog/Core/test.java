package byog.Core;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入字符串");
        String str = scanner.next();
        char firstchar = str.charAt(0);
        int length = str.length();
        System.out.println(firstchar + " " + "长度是：" + length);
        scanner.close();
    }
}
