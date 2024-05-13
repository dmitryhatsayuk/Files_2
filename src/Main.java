import java.io.*;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress progress1 = new GameProgress(13, 35, 12, 2.5);
        saveGame("C:\\Users\\dmitr\\Games\\savegames\\progress1.dat", progress1);
        GameProgress progress2 = new GameProgress(15, 12, 12, 23.7);
        saveGame("C:\\Users\\dmitr\\Games\\savegames\\progress2.dat", progress2);
        GameProgress progress3 = new GameProgress(22, 22, 22, 22.2);
        saveGame("C:\\Users\\dmitr\\Games\\savegames\\progress3.dat", progress3);

        LinkedList<String> progressList = new LinkedList<>();
        progressList.add("C:\\Users\\dmitr\\Games\\savegames\\progress1.dat");
        progressList.add("C:\\Users\\dmitr\\Games\\savegames\\progress2.dat");
        progressList.add("C:\\Users\\dmitr\\Games\\savegames\\progress3.dat");

        zipFiles("C:\\Users\\dmitr\\Games\\savegames\\progziped.zip", progressList);
    }

    //Методы сохранения и архивирования
    public static void saveGame(String path, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
            System.out.println(STR."Прогресс \{path} успешно сохранен");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void zipFiles(String zipPath, LinkedList<String> progress) {
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream(zipPath))) {
            for (int i = 0; i < progress.size(); i++) {
                try (FileInputStream fis = new FileInputStream(progress.get(i))) {
                    ZipEntry entry = new ZipEntry(STR."packed_progress\{i + 1}.dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    System.out.println(STR."\{progress.get(i)} успешно сохранен в архив");
                    zout.closeEntry();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                File filedeletter = new File(progress.get(i));
                if (filedeletter.delete()) {
                    System.out.println(STR."\{progress.get(i)} удален успешно");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


    }
}