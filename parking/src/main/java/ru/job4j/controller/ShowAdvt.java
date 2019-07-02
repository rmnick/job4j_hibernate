package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import ru.job4j.service.Service;
import ru.job4j.service.entities.Advertisement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

public class ShowAdvt extends HttpServlet {
    private final Service service = Service.getInstance();
    public static final Logger LOG = Logger.getLogger(ShowAdvt.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURL().toString();
        String uri = req.getRequestURI();
        String path = req.getContextPath();
        String serv = req.getServerName();
        String scheme = req.getScheme() + "://";
        String serverPort = (req.getServerPort() == 80) ? "" : ":" + req.getServerPort();

        System.out.println("url " + url);
        System.out.println("uri " + uri);
        System.out.println("path " + path);
        System.out.println("serv " + serv);
        System.out.println("scheme " + scheme);
        System.out.println("port " + serverPort);
        System.out.println(scheme + serv + serverPort + path);
        System.out.println("real path " + getServletContext().getRealPath("/img/"));

        PrintWriter pw = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        List<Advertisement> ads = service.getAllAds();
        Advertisement ad = ads.get(0);
        System.out.println(ad.getPicturePath());
        ObjectNode objectNode = mapper.createObjectNode();

        File image = new File(ad.getPicturePath());
        byte[] imageData = Files.readAllBytes(image.toPath());
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        objectNode.put("image64", base64Image);
        pw.append(mapper.writeValueAsString(objectNode));
        pw.flush();

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
