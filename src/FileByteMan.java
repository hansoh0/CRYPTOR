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
        try {
            String mode = null, directory = null, secretKey = null, salt = null, targetDirectory = null;

    		if (args.length == 4) {
    			List<String> modeDirKeySalt = Arrays.asList("mode", "directory", "secret", "salt");
    			for (int i = 0; i < args.length; i++) {
    				switch(modeDirKeySalt.get(i)) {
    					case "mode":
    						mode = args[i];
    					case "directory":
    						targetDirectory = args[i];
    					case "secret":
    						secretKey = args[i];
    					case "salt":
    						salt = args[i];
                        default:
                            break;
    				}
    			}
    		} else if (args.length > 0 && (args[0].equalsIgnoreCase("help") || args[0].equals("--help") || args[0].equals("-h") || args[0].equalsIgnoreCase("h"))) {
                System.out.println("Usage: {encrypt/decrypt} {directory} {password/key} {salt}");
                System.exit(1);
            }
    		String strInDirectory = targetDirectory;

    		// Defining out directory as the target directory
    		String strOutDirectory = strInDirectory;
    		Path inputDirectory = Paths.get(strInDirectory);
    		Path outputDirectory = Paths.get(strOutDirectory);

            if ("encrypt".equalsIgnoreCase(mode)) {
                encryptFiles(inputDirectory, outputDirectory, secretKey, salt);
            } else if ("decrypt".equalsIgnoreCase(mode)) {
                decryptFiles(inputDirectory, outputDirectory, secretKey, salt);
            } else {
                System.out.println("Invalid choice. Please enter 'encrypt' or 'decrypt'.");
            }
        } catch (Exception e) {
            System.out.println("An error occured in main().");
            System.exit(1);
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
    private static void encryptFiles(Path inputDirectory, Path outputDirectory, String secretKey, String salt) {
    	// Exit if files dont exist
        try {
            if (Files.notExists(outputDirectory)) {
            	System.out.println("Directory doesn't exist.");
                System.exit(1);
            }

            List<Path> files = getFilesInDirectory(inputDirectory);

            // For file in targeted directory
            for (Path inputFile : files) {
                byte[] fileBytes = Files.readAllBytes(inputFile);
                byte[] encryptedBytes = AES256.encrypt(fileBytes, secretKey, salt);
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
    private static void decryptFiles(Path inputDirectory, Path outputDirectory, String secretKey, String salt) {
    	// Exit if files dont exist
        try {
            if (Files.notExists(outputDirectory)) {
                System.out.println("Directory doesn't exist.");
                System.exit(1);
            }

            List<Path> files = getFilesInDirectory(inputDirectory);

            // For file in targeted directory
            for (Path inputFile : files) {
                byte[] fileBytes = Files.readAllBytes(inputFile);
                byte[] decryptedBytes = AES256.decrypt(fileBytes, secretKey, salt);
                Path outputFile = outputDirectory.resolve(inputFile.getFileName());
                Files.write(outputFile, decryptedBytes);
                System.out.println("File Decrypted: " + inputFile.getFileName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
