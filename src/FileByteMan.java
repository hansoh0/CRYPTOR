import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Grabs files in a specified directory, encrypt/decrypt the files bytes and overewrite the file with the new bytes
 * @HanSoho 
 */

public class FileByteMan {

	/**
   * Main function for encryption/decryption program
   * @param args not used
   */
	public static void main(String[] args) {
		// Get directory to operate on
		Scanner scanObj = new Scanner(System.in);
		System.out.println("Directory: ");
		String strInDirectory = scanObj.nextLine();

		// Defining out directory as the target directory
		String strOutDirectory = strInDirectory;
		Path inputDirectory = Paths.get(strInDirectory);
		Path outputDirectory = Paths.get(strOutDirectory);

		System.out.println("Mode: (encrypt/decrypt): ");
        String choice = scanObj.nextLine().trim();

        if (choice.equalsIgnoreCase("encrypt")) {
            encryptFiles(inputDirectory, outputDirectory, scanObj);
        } else if (choice.equalsIgnoreCase("decrypt")) {
            decryptFiles(inputDirectory, outputDirectory, scanObj);
        } else {
            System.out.println("Invalid choice. Please enter 'encrypt' or 'decrypt'.");
        }
	}

	/**
   * Encrypts a byte string with a secretKey and defined Salt in AES256
   * @param directory is the path of the target directory/folder that going to be iterated and
   * enrypted/decrypted.
   */
	private static List<Path> getFilesInDirectory(Path directory) throws IOException {
		List<Path> fileList = new ArrayList<>();
		Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (Files.isRegularFile(file)) {
            fileList.add(file);
        }
        return FileVisitResult.CONTINUE;
			}
		});
		return fileList;
	}

	/**
   * Encrypts a byte string with a secretKey and defined Salt in AES256
   * @param inputdirectory is a path object where the files to-be-encrypted lie
   * @param outputDirectory is the path object where the encrypted files will be written 
   */
    private static void encryptFiles(Path inputDirectory, Path outputDirectory, Scanner scanObj) {
    	// Exit if files dont exist
        try {
            if (Files.notExists(outputDirectory)) {
                System.out.println("Directory doesn't exist.");
                System.exit(1);
            }

            List<Path> files = getFilesInDirectory(inputDirectory);

            String[] auth = engageUser(scanObj);

            // For file in targeted directory
            for (Path inputFile : files) {
                byte[] fileBytes = Files.readAllBytes(inputFile);
                byte[] encryptedBytes = AES256.encrypt(fileBytes, auth[0], auth[1]);
                Path outputFile = outputDirectory.resolve(inputFile.getFileName());
                Files.write(outputFile, encryptedBytes);
                System.out.println("File Encrypted: " + inputFile.getFileName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
   * Decrypts a byte string with a secretKey and defined Salt in AES256
   * @param inputdirectory is a path object where the files to-be-decrypted lie
   * @param outputDirectory is the path object where the decrypted files will be written 
   */
    private static void decryptFiles(Path inputDirectory, Path outputDirectory, Scanner scanObj) {
    	// Exit if files dont exist
        try {
            if (Files.notExists(outputDirectory)) {
                System.out.println("Directory doesn't exist.");
                System.exit(1);
            }

            List<Path> files = getFilesInDirectory(inputDirectory);

            String[] auth = engageUser(scanObj);

            // For file in targeted directory
            for (Path inputFile : files) {
                byte[] fileBytes = Files.readAllBytes(inputFile);
                byte[] decryptedBytes = AES256.decrypt(fileBytes, auth[0], auth[1]);
                Path outputFile = outputDirectory.resolve(inputFile.getFileName());
                Files.write(outputFile, decryptedBytes);
                System.out.println("File Decrypted: " + inputFile.getFileName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] engageUser(Scanner scanObj) {
        String[] auth = new String[2];
		    System.out.println("Secret Key: ");
	      auth[0] = scanObj.nextLine();
		    System.out.println("Salt: ");
		    auth[1] = scanObj.nextLine();
		    return auth;
    }

}
