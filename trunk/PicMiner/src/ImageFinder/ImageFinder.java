package ImageFinder;

import FilePicker.FilePicker;
import HTMLGenerator.HTMLGenerator;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class ImageFinder {
    private final FilePicker filePicker;
    private final HTMLGenerator htmlGenerator;
    private final File inFile;
    private boolean nextImage = true;
    private int count = 0;
    private final String fileProcessed = "File Processed";


    public ImageFinder(FilePicker filePicker, HTMLGenerator htmlGenerator, File inFile){
        this.filePicker = filePicker;
        this.htmlGenerator = htmlGenerator;
        this.inFile = inFile;
        htmlGenerator.nextFile(this, inFile.getName());
    }

    public void fileSent(){
        filePicker.fileSent();
        //CheckIndex.fileIndexCheck();
        callExtractionMethod();
        htmlGenerator.finishFile();
    }

    private void callExtractionMethod(){
        String extension = inFile.getAbsolutePath();
        if(extension.endsWith(".docx")){
            extractFromDocx();
        }
        if(extension.endsWith(".pdf")){
            extractFromPDF();
        }
        if(extension.endsWith(".pptx")){
            extractFromPPTX();
        }
    }

    public void extractFromPDF() {
        count = 0;
        try (final PDDocument document = PDDocument.load(inFile)) {
            PDPageTree list = document.getPages();
            for (PDPage page : list) {
                PDResources pdResources = page.getResources();
                readPDFObjects(pdResources);

            }
        }
        catch (IOException e) {System.err.println("Extract from PDF error: " + e);}
        System.out.println(fileProcessed);
    }

    private void readPDFObjects(PDResources pdResources){
        try {
            for (COSName name : pdResources.getXObjectNames()) {
                PDXObject object = pdResources.getXObject(name);
                if (object instanceof PDImageXObject) {
                    PDImageXObject image = (PDImageXObject) object;
//                    if(isImageValid(image.getImage())){
//                        throw new Exception("Image was a plain color or too small");
//                    }
                    File imgName = new File(FileSystemView.getFileSystemView()
                            .getDefaultDirectory().getPath() +
                            "\\PicMiner\\" + getFolderName() + "\\images\\" + inFile.getName() + "-image" + count + "." + image.getSuffix());                    ImageIO.write(image.getImage(), image.getSuffix(), imgName);
                    ImageIO.write(image.getImage(), image.getSuffix(), imgName);
                    htmlGenerator.addImage(imgName.getAbsolutePath());
                    count++;
                    nextImage = false;
                }
            }
        }
        catch(Exception e) {
            System.out.println("Failed to process image: " + e);
        }
    }
    public void extractFromDocx() {
        count = 0;
        try(XWPFDocument docx = new XWPFDocument(new FileInputStream(inFile))){;
            List<XWPFPictureData> pictureList = docx.getAllPictures();
            readDocxObjects(pictureList);
        }
        catch (Exception e) {System.err.println("Extract from docx error: " + e);}
        System.out.println(fileProcessed);
    }

    public void readDocxObjects(List<XWPFPictureData> pictureList){
        try {
            for (XWPFPictureData picture : pictureList) {
                byte[] pictureBytes = picture.getData();
                File imgName = new File(FileSystemView.getFileSystemView()
                        .getDefaultDirectory().getPath() +
                        "\\PicMiner\\" + getFolderName() + "\\images\\" + inFile.getName() + "-image" + count);
                if (!picture.suggestFileExtension().equals("png")){
                    imgName = new File(imgName+".jpeg");
                    emfToJpg(picture.getData(),""+imgName.getAbsoluteFile());
                }
                else{
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(pictureBytes));
                    imgName = new File(FileSystemView.getFileSystemView()
                            .getDefaultDirectory().getPath() +
                            "\\PicMiner\\" + getFolderName() + "\\images\\" + inFile.getName() + "-image" + count +"."+picture.suggestFileExtension());
                    ImageIO.write(image, picture.suggestFileExtension(), imgName);
                }
                htmlGenerator.addImage(imgName.getAbsolutePath());
                count++;
                nextImage = false;
            }
        }
        catch(Exception e) {
            System.out.println("Failed to process image: " + e);
        }
    }
    private void emfToJpg(byte[] content, String fileName) throws TranscoderException, IOException {
        System.out.println("Path is:"+fileName);

        // create SVG properties
        ByteArrayOutputStream fOut = new ByteArrayOutputStream();
        // prepare Graphics2D

        JPEGTranscoder converter = new JPEGTranscoder();
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        ostream.write(content, 0, content.length);

        TranscoderOutput transcoderOutput = new TranscoderOutput(ostream);
        TranscoderInput transcoderInput = new TranscoderInput(new ByteArrayInputStream(fOut.toByteArray()));
        converter.transcode(transcoderInput,transcoderOutput);
        try (FileOutputStream pngfos = new FileOutputStream(new File(fileName))) {
            pngfos.write(ostream.toByteArray());
            ostream.close();
            transcoderInput.getInputStream().close();
        } catch (Exception e) {

        }
        transcoderInput.getInputStream().close();
    }
    public void extractFromPPTX() {
        count = 0;
        try (XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(inFile))){
            readPPTXObjects(ppt);
        }
        catch(Exception e){System.err.println("Extract from pptx error: " + e);}
        System.out.println(fileProcessed);
    }


    private void readPPTXObjects(XMLSlideShow ppt){
        try{
            for (XSLFPictureData picture : ppt.getPictureData()) {
                byte[] pictureBytes = picture.getData();
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(pictureBytes));
//                if(isImageValid(image)){
//                    throw new Exception("Image was a plain color or too small");
//                }
                File imgName = new File(FileSystemView.getFileSystemView()
                        .getDefaultDirectory().getPath() +
                        "\\PicMiner\\" + getFolderName() + "\\images\\" + inFile.getName() + "-image" + count +
                        "." + picture.suggestFileExtension());
                ImageIO.write(image, picture.suggestFileExtension(), imgName);
                htmlGenerator.addImage(imgName.getAbsolutePath());
                count++;
                nextImage = false;
            }
        }
        catch(Exception e){
            System.out.println("Failed to process image: " + e);
        }
    }

    private boolean isImageValid(BufferedImage img){
        if(img.getHeight()<20 && img.getWidth()<20){
            return false;
        }
        // Checks if there is any pixel with a color different to the first one
        Color firstPixelColor = new Color(img.getRGB(1,1));
        for(int y = 0; y <=img.getHeight(); y++){
            for(int x = 0; x <=img.getWidth(); x++) {
                if(!(firstPixelColor.equals(new Color(img.getRGB(x,y))))) return true;
            }
        }
        return false;
    }

    public void fileDone(){
        filePicker.nextFile();
    }

    public void nextImage(){
        nextImage = true;
    }

    public String getFolderName(){
        return inFile.getParentFile().getName();
    }
}