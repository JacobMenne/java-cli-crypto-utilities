package edu.umsl;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import static org.fusesource.jansi.Ansi.*;
import org.fusesource.jansi.AnsiConsole;

@Command(name = "base64util", description = "Base64 encoding and decoding utility", version = "1.0")
public class Base64Utility implements Runnable {

    @Option(names = { "-e", "--encode" }, description = "Encode the provided string")
    private String encodeString;

    @Option(names = { "-d", "--decode" }, description = "Decode the provided Base64 string")
    private String decodeString;

    @Option(names = { "-a", "--analyze" }, description = "Analyze string properties")
    private boolean analyze;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "Display help information")
    private boolean helpRequested;

    public static String encodeWithCommons(String input) {
        byte[] bytes = input.getBytes();
        return Base64.encodeBase64String(bytes);
    }

    public static String decodeWithCommons(String encoded) {
        byte[] decoded = Base64.decodeBase64(encoded);
        return new String(decoded);
    }

    public static void analyzeStringProperties(String input) {
        System.out.println("\nString Analysis:");
        System.out.println("  Length: " + StringUtils.length(input));
        System.out.println("  Is Alpha: " + StringUtils.isAlpha(input));
        System.out.println("  Is Alphanumeric: " + StringUtils.isAlphanumeric(input));
        System.out.println("  Contains Whitespace: " + StringUtils.containsWhitespace(input));
        System.out.println("  Word Count: " + (StringUtils.countMatches(input, ' ') + 1));
        System.out.println("  Reversed: " + StringUtils.reverse(input));
    }

    public static boolean isValidBase64(String input) {
        return Base64.isBase64(input);
    }

    public static int calculateSizeChange(String original, String encoded) {
        if (original == null || original.isEmpty()) {
            return 0;
        }
        double change = ((double) encoded.length() - original.length()) / original.length() * 100;
        return (int) Math.round(change);
    }

    public static void displayColoredResults(String operation, String input, String output) {
        AnsiConsole.systemInstall();

        System.out.println(ansi().fgBrightGreen().bold().a("✓ " + operation + " COMPLETE").reset());
        System.out.println(ansi().fgBlue().a("Input:  " + input).reset());
        System.out.println(ansi().fgCyan().a("Output: " + output).reset());

        int sizeChange = calculateSizeChange(input, output);
        String changeText;

        if (sizeChange >= 0) {
            changeText = "+" + sizeChange + "%";
        } else {
            changeText = sizeChange + "%";
        }

        System.out.println(ansi().fgYellow().a("Size Change: " + changeText).reset());

        AnsiConsole.systemUninstall();
    }

    public static void printColoredHeader() {
        AnsiConsole.systemInstall();
        System.out.println(ansi().fgBrightMagenta().bold().a("=== Base64 Utility ===").reset());
        System.out.println(ansi().fgGreen().a("• Use --encode to encode strings").reset());
        System.out.println(ansi().fgGreen().a("• Use --decode to decode Base64").reset());
        System.out.println(ansi().fgGreen().a("• Use --analyze for string analysis").reset());
        AnsiConsole.systemUninstall();
    }

    public static void printErrorMessage(String error) {
        AnsiConsole.systemInstall();
        System.out.println(ansi().fgRed().bold().a("Error: " + error).reset());
        AnsiConsole.systemUninstall();
    }

    public void run() {
        if (encodeString != null) {
            processEncoding();
        } else if (decodeString != null) {
            processDecoding();
        } else {
            printColoredHeader();
            CommandLine.usage(this, System.out);
        }
    }

    public void processEncoding() {
        String output = encodeWithCommons(encodeString);
        displayColoredResults("ENCODE", encodeString, output);
        if (analyze) {
            processAnalysis();
        }
    }

    public void processDecoding() {
        if (!isValidBase64(decodeString)) {
            printErrorMessage("Invalid Base64 string provided.");
            return;
        }
        String output = decodeWithCommons(decodeString);
        displayColoredResults("DECODE", decodeString, output);

        if (analyze) {
            processAnalysis();
        }
    }

    public void processAnalysis() {
        if (encodeString != null) {
            analyzeStringProperties(encodeString);
        } else if (decodeString != null) {
            analyzeStringProperties(decodeWithCommons(decodeString));
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Base64Utility()).execute(args);
        System.exit(exitCode);
    }
}