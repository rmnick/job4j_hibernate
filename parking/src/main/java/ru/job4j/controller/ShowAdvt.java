package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.Service;
import ru.job4j.service.entities.Advertisement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.List;

public class ShowAdvt extends HttpServlet {
    private final Service service = Service.getInstance();
    public static final Logger LOG = Logger.getLogger(ShowAdvt.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Advertisement> ads = service.getAllAds();
        Advertisement ad = ads.get(0);
//        for (Advertisement ad : ads) {
            String base64Image = "";
            File file = new File(ad.getPicturePath());
            try (FileInputStream imageInFile = new FileInputStream(file)) {
                // Reading a Image file from file system
                byte imageData[] = new byte[(int) file.length()];
                imageInFile.read(imageData);
                base64Image = Base64.getEncoder().encodeToString(imageData);
                System.out.println(base64Image);
                PrintWriter writer = resp.getWriter();
                writer.println(base64Image);
                writer.flush();

            } catch (FileNotFoundException e) {
                System.out.println("Image not found" + e);
            } catch (IOException ioe) {
                System.out.println("Exception while reading the Image " + ioe);
            }

//            for (Advertisement ad : ads) {
//                String path = ad.getPicturePath();
//
//                ServletContext cntx = req.getServletContext();
//                // Get the absolute path of the image
//                String filename = path;
//                // retrieve mimeType dynamically
//                String mime = cntx.getMimeType(filename);
//                if (mime == null) {
//                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                    return;
//                }
//                resp.setContentType(mime);
//                File file = new File(filename);
//                resp.setContentLength((int) file.length());
//
//                FileInputStream in = new FileInputStream(file);
//
//                // Copy the contents of the file to the output stream
//                byte[] buf = new byte[1024];
//                int count = 0;
//                while ((count = in.read(buf)) >= 0) {
//                    out.write(buf, 0, count);
//                }
//                in.close();
//            }
//
////            out.write(String.format("{car:%s}", "Kia").getBytes());
//            out.flush();
//            out.close();

//        }
    }
}
