//package FileLocator;
//
//import HTML.HTMLGenerator;
//import ImageExtractor.ExtractImages;
//import GUI.GUI;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.*;
////import java.lang.reflect.Array;
////import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;
//
//
//public class filesearchcopy {
//
//        protected static Set<String> documentArray = new HashSet<>();
//        protected boolean startSendingReceived = false;
//        protected  boolean htmlDocDoneReceived = false;
//        protected String htmlDocLocation = null;
//        public guI;
//        public JFrame frame;
//        public JPanel panel;
//        public JButton button;
//        public File outFile;
//        public BufferedWriter bw;
//        public JLabel label;
//        public void start(){
//
//            frame = new JFrame("File Picker");
//            frame.setLocation(new Point(500,250));
//            frame.setPreferredSize(new Dimension(600, 400));
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setVisible(true);
//            frame.setResizable(false);
//            panel = new JPanel();
//            button = new JButton("Choose Directory");
//            label = new JLabel("Select Directory");
//
//
//            panel.add(label);
//            panel.add(button);
//            button.addActionListener(this);
//
//            frame.add(panel);
//            frame.pack();
//            try{
//                outFile = new File("out.html");
//                bw = new BufferedWriter(new FileWriter(outFile));
//            }catch (Exception e){
//
//            }
//
//        }
//
//
//        public void actionPerformed(ActionEvent event){
//            if (event.getSource() == button){
//                //Prompt the user to choose directories only
//                JFileChooser chooser = new JFileChooser();
//                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//                int returnVal = chooser.showOpenDialog(frame); // user to be given two option "cancel" or "choose " when the dialog box open
//                File path;
//                if(returnVal == JFileChooser.APPROVE_OPTION) { // when clicked "choose" print the output in html
//                    try {
//                        bw.write("<html>");
//                        bw.write("<body>");
//                        bw.write("<h1>File List</h1>");
//                        bw.write("<h3><p>");
//                    }catch (Exception e){
//
//                    }
//                    path = chooser.getSelectedFile(); // returns a list of selected files
//                    label.setText(path.getAbsolutePath());
//                    try {
//                        FindFiles(path); // calls the recursive function which read files in directory
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    label.setText("Finish Search");
//
//                    try{
//                        bw.write("</p><h3>");
//                        bw.write("</body>");
//                        bw.write("</html>");
//                        bw.close();
//                    }catch (Exception e){
//
//                    }
//                }
//            }
//
//        }
//
//        public static Set<String> FindFiles(File directory) throws FileNotFoundException {
//
////        try {
//            // gets all the files from directory
//            File[]files = directory.listFiles();
//            if (files == null) return documentArray;
//            for (File file : files) {
//                // if its a file lets read it
//                if (!file.isDirectory()){
//                    String path = file.getAbsolutePath();
//
//                    // checking the specific file extensions
//
//                    if (path.endsWith(".doc") || path.endsWith(".docx") || path.endsWith(".pdf") || path.endsWith(".ppt") || path.endsWith(".pptx")){
//
////                        bw.write(file.getName() + "<br>");
//
////                        System.out.print(file.getName() + "\n");
//
//                        documentArray.add(file.getAbsolutePath());
//                    }
//                }
//                else{
//                    // recursive search for sub directories
//                    FindFiles(file);
//                }
//
//            }
//
////        }catch (Exception e){
////            e.printStackTrace();
////        }
//            if(documentArray.isEmpty()){
//                throw new FileNotFoundException("No Valid Files");
//            }
//
////        for(String documentpath: documentArray){
////            System.out.println(documentpath);
////        }
//
//
//            return documentArray;
//        }
//
//        public void startSending(){
//
//
//        }
//        public void htmlGeneratorDone() {
//        }
//
//        public void folderProcessed(){
//            ExtractImages extractImages = new ExtractImages();
//        }
//
//        public void fileSent(){
//
//        }
//
//        public void nextFile(){
//
//        }
//
//
//
//        public static void main(String[] args)
//        {
//            FileSearch filesearch = new FileSearch();
//            filesearch.start();
//        }
//
//
//
//
//}
