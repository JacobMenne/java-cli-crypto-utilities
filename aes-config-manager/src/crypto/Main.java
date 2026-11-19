package crypto;

import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        SecretKey key = Utils.getKey();

        try (Scanner scan = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n1. Read settings \n2. Modify settings \n3. Exit");
                String choice = scan.nextLine();

                if (choice.equals("1")) {
                    try {
                        byte[] enc = Files.readAllBytes(Paths.get("settings.txt"));
                        String dec = Utils.decrypt(enc, key);
                        System.out.println("Settings: " + dec);
                    } catch (Exception e) {
                        System.out.println("File not found or empty.");
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Enter string: ");
                    String s = scan.nextLine();
                    System.out.print("Enter integer: ");
                    String i = scan.nextLine();
                    System.out.print("Enter float: ");
                    String f = scan.nextLine();

                    String data = s + ", " + i + ", " + f;
                    byte[] encryptedData = Utils.encrypt(data, key);
                    Files.write(Paths.get("settings.txt"), encryptedData);
                    System.out.println("Saved.");
                } else if (choice.equals("3")) {
                    break;
                }
            }
        }
    }
}