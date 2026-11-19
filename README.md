# Java CLI & Cryptographic Utilities

A collection of two lightweight Java command-line utilities focused on encoding, string analysis, and symmetric file encryption.

## 1. Base64 CLI Utility (`base64-cli/`)

A Maven-packaged command-line tool for Base64 encoding, decoding, and string property analysis with ANSI-colored terminal output.

**Tech Stack:** Java 21, Maven, Picocli, Apache Commons Codec, Apache Commons Lang3, JANSI

### Build & Run

```bash
cd base64-cli
mvn clean package

# Encode a string with property analysis
java -jar target/base64mavenutilityproject-1.0.0-SNAPSHOT.jar --encode "Hello Maven!" --analyze

# Decode a Base64 string
java -jar target/base64mavenutilityproject-1.0.0-SNAPSHOT.jar --decode "SGVsbG8gTWF2ZW4h"

# Display CLI usage options
java -jar target/base64mavenutilityproject-1.0.0-SNAPSHOT.jar --help
```
