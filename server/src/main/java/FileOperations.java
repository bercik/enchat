import java.io.*;
import java.util.Arrays;

/**
 * Created by tochur on 19.04.15.
 */
public class FileOperations {
    public static void main(String[] args) throws IOException {
        File file = new File("fileName.txt");
        try {
            boolean succeed = file.createNewFile();
            if (!succeed){
                file.delete();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        DataOutputStream out = new DataOutputStream(fileOutputStream);
        out.writeInt(7);
        byte[] byteArray = new byte[] {1, 2, 3, 5, 6, 7};
        out.writeInt(byteArray.length);
        out.write(byteArray);
        System.out.print("byte array length: " + byteArray.length);
        System.out.println("Byte array: " + Arrays.toString(byteArray));

        //Sending string
        String message = "My message *****:) ż";
        byte[] bAr = message.getBytes();
        System.out.println("Send string array: " + Arrays.toString(bAr));
        out.writeInt(bAr.length);
        out.write(bAr);
        out.close();

        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream in = new DataInputStream(fileInputStream);
        int value = in.readInt();
        int length = in.readInt();
        byte[] readArray = new byte[length];
        in.readFully(readArray);
        System.out.print("Read byte array length: " + byteArray.length);
        System.out.println("Read byte array: " + Arrays.toString(byteArray));

        int arLen = in.readInt();
        byte[] arB = new byte[arLen];
        in.readFully(arB);
        System.out.println("Read string array: " + Arrays.toString(arB));
        String napis = new String(arB);
        System.out.print(napis);

        in.close();

        System.out.print("Read value is: " + value);
    }
}
