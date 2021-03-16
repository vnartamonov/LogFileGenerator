import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FileGenerator {
    private final static int SIZE_BUFFER = 1024; // Размер буффера для копирования файлов.
    private String textFormat; // Формат текстовых файлов. Если не указан, то смотрим все файлы.
    private File root; // Путь от которого формируем выходной файл.
    private File outputFile; // Выходной файл на основе списка textFiles.
    private final List<File> textFiles = new ArrayList<File>(); // Список текстовых файлов в от каталога root.

    public FileGenerator(String root) throws NullPointerException {
        this.root = new File(root);
        this.textFormat = "";
    }

    public FileGenerator(String root, String fileFormat) throws NullPointerException {
        this.root = new File(root);
        this.textFormat = fileFormat;
    }

    public void makeFile(String outputFileName) throws IOException {
        outputFile = new File(outputFileName);
        textFiles.clear();

        recursiveDirectoryTraversal(root); // Формируем список текстовых файлов.
        textFiles.sort(new FileOrderComparator()); // Сортируем список по имени файлов.
        fileGeneration(); // Собираем выходной файл.
    }

    private void recursiveDirectoryTraversal(File root){
        File listFiles[] = root.listFiles();

        if(listFiles == null || listFiles.length == 0) return;

        for(File currentFile : listFiles){
            if(currentFile.isFile() && currentFile.canRead()){
                String fileFormat = currentFile.getName().substring(currentFile.getName().length() - textFormat.length());
                if(textFormat.isEmpty() || fileFormat.equals(textFormat))
                    textFiles.add(currentFile);
            }
            else if(currentFile.isDirectory() && currentFile.canRead()){
                recursiveDirectoryTraversal(currentFile);
            }
        }
    }

    private void fileGeneration() throws IOException {
        byte buffer[] = new byte[SIZE_BUFFER];

        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

        for(File currentFile : textFiles){
            FileInputStream fileInputStream = new FileInputStream(currentFile);
            while (fileInputStream.available() > 0) {
                int currentSize = fileInputStream.readNBytes(buffer, 0, SIZE_BUFFER);
                fileOutputStream.write(buffer, 0, currentSize);
            }
            fileInputStream.close();
        }

        fileOutputStream.close();
    }

    public String getRoot() {
        return root.getPath();
    }

    public void setRoot(String root) throws NullPointerException{
        this.root = new File(root);
    }

    public String getTextFormat() {
        return textFormat;
    }

    public void setTextFormat(String textFormat){
        this.textFormat = textFormat;
    }

    private static class FileOrderComparator implements Comparator<File> {
        @Override
        public int compare(File f1, File f2) {
            return f1.getName().compareTo(f2.getName());
        }
    }
}
