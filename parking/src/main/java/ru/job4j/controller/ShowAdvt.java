package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import ru.job4j.service.Service;
import ru.job4j.service.entities.Advertisement;
import ru.job4j.service.entities.View;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class ShowAdvt extends HttpServlet {
    private final Service service = Service.getInstance();
    public static final Logger LOG = Logger.getLogger(ShowAdvt.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        List<Advertisement> ads = service.getAllAds();
        byte[] imageData;
        String base64Image;
        View view;

        List<View> imgs = new ArrayList<>();
        for (int i = 0; i < ads.size(); i++) {
            File image = new File(ads.get(i).getPicturePath());
            imageData = Files.readAllBytes(image.toPath());
            base64Image = Base64.getEncoder().encodeToString(imageData);
            view = new View();
            view.setImg(base64Image);
            view.setDesc(ads.get(i).getDescription());
            view.setSold(ads.get(i).isSold());
            imgs.add(view);
        }
        mapper.writeValue(pw, imgs);
        pw.flush();

//        pw.append(mapper.writeValueAsString(imgs));
//        pw.flush();

//        OutputStream out = resp.getOutputStream();
//
////            for (Advertisement ad : ads) {
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
////            }
//
////            out.write(String.format("{car:%s}", "Kia").getBytes());
//            out.flush();
//            out.close();

    }
}
