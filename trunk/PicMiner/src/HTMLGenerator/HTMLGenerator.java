package HTMLGenerator;

import FilePicker.FilePicker;
import ImageFinder.ImageFinder;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;


public class HTMLGenerator {
    private final FilePicker filePicker;
    private String folderName;
    private ImageFinder imageFinder;
    private String fileName;
    private final StringBuilder outputHtml = new StringBuilder();

    public HTMLGenerator(FilePicker filePicker) {
        this.filePicker = filePicker;
    }

    public void start(String folderName) {
        this.folderName = folderName;

        File dir = new File(FileSystemView.getFileSystemView()
                .getDefaultDirectory().getPath() +
                "\\PicMiner\\" + getFolderName());
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File imgDir = new File(FileSystemView.getFileSystemView()
                .getDefaultDirectory().getPath() +
                "\\PicMiner\\" + getFolderName() + "\\images");
        if (!imgDir.exists()) {
            imgDir.mkdirs();
        }

        outputHtml.append(
                "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<meta charset='utf-8'/>\n" +
                        "<title>" + folderName + "</title>\n" +
                        "<script>\n" +
                        "function showRelevantImage(){" +
                        "}" +
                        "</script>\n" +
                        "<style>\n" +
                        ".header {\n" +
                        "    margin-left: 600px;\n" +
                        "    top: 0;\n" +
                        "    position: relative;\n" +
                        "}\n" +
                        "h1 { color: white; font-size: 90px;}\n" +
                        ".sidebar {\n" +
                        "    height: 100%;\n" +
                        "    position: fixed;\n" +
                        "    left: 0;\n" +
                        "    top: 0;\n" +
                        "    width: 270px;\n" +
                        "    z-index: 1;\n" +
                        "    padding-top: 10px;\n" +
                        "    background-color: grey;\n" +
                        "}\n" +
                        ".sidebar p {\n" +
                        "    margin-left: 30px;\n" +
                        "    font-size: 30px;\n" +
                        "    color: white;\n" +
                        "    font-family: \"Arial\";\n" +
                        "}\n" +
                        ".sidebar a {\n" +
                        "    margin-left: 30px;\n" +
                        "    font-size: 20px;\n" +
                        "    font-family: \"Arial\";\n" +
                        "    color: white;\n" +
                        "    text-decoration: none;\n" +
                        "    line-height: 150%;\n" +
                        "}\n" +
                        ".sidebar a:hover { color: aqua; }\n" +
                        ".content {\n" +
                        "    margin-left: 350px;\n" +
                        "    padding-top: 5px;\n" +
                        "}\n" +
                        ".content img { padding: 5px; width: 150px; height: 100px; border: 3px solid #ddd; border-radius: 6px; margin-left: 2px;}\n" +
                        ".content img:hover { box-shadow: 0 0 5px 5px rgba(0, 191, 255, 0.5)}\n" +
                        "</style>\n" +
                        "</head>\n" +
                        "<body style = 'background-color:black'>\n" +
                        "<h1 class='header'><font color=#5a9bd6>Pic</font><font color=white>Miner</font></h1>\n" +
                        "<div class='sidebar'>\n" +
                        "<p>Files: </p>\n"
        );
        addFileNameInSideBar(filePicker.getDocumentSet());
        outputHtml.append("<div class='content' id='imgContent'>");
        filePicker.startSending();
    }

    private void addFileNameInSideBar(Set<String> documentSet) {
        for (String file: documentSet) {
            Path path = Paths.get(file);
            Path fileName = path.getFileName();
            outputHtml.append("<a href='" + "file:///" + path + "'>" + fileName + "</a>\n<br>\n");
        }
        outputHtml.append("</div>\n");
    }

    public void nextFile(ImageFinder imageFinder, String fileName) {
        this.imageFinder = imageFinder;
        this.fileName = fileName;
        //addHeading("h2", fileName);
        imageFinder.fileSent();
    }

    public void addImage(String imagePath) {
        outputHtml.append(
                "<a href='" + imagePath + "'>\n"
                +"<img src='" + imagePath + "'>\n"
                +"</a>"
        );
        imageFinder.nextImage();
    }

    public void finishFile() {
        //addHeading("h3", "-------------------------");
        imageFinder.fileDone();
    }

    public void endDocument() throws IOException {
        File file = new File(FileSystemView.getFileSystemView()
                .getDefaultDirectory().getPath() +
                "\\PicMiner\\" + getFolderName() + "\\index.html");
        FileWriter writer = new FileWriter(file);
        BufferedWriter outFile = new BufferedWriter(writer);
        outputHtml.append("</div>\n</body>\n</html>");
        outFile.write(outputHtml.toString());
        outFile.close();
        filePicker.htmlGeneratorDone(file.getAbsolutePath());
    }

    private void addHeading(String headingType, String heading) {
        outputHtml.append("<" + headingType + ">" +
                heading + "</" + headingType + ">\n" );
    }

    private void addParagraph(String paragraph) {
        outputHtml.append("<p>" + paragraph + "</p>\n");
    }

    protected FilePicker getFileLocator() {
        return filePicker;
    }

    protected String getFolderName() {
        return folderName;
    }

    protected String getOutputHtml() {
        return outputHtml.toString();
    }

    protected ImageFinder getImageFinder() {
        return imageFinder;
    }

    protected String getFileName() {
        return fileName;
    }

    protected void setOutputHtml(String newText) {
        outputHtml.delete(0, outputHtml.length());
        outputHtml.append(newText);
    }
}