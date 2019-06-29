package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.Service;
import ru.job4j.service.entities.Advertisement;
import ru.job4j.service.entities.Brand;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class ShowAdvt extends HttpServlet {
    private final Service service = Service.getInstance();
    public static final Logger LOG = Logger.getLogger(ShowAdvt.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Advertisement> ads = service.getAllAds();
            String path = ads.get(0).getPicturePath();
            System.out.println(path);
//            String result = objectMapper.writeValueAsString(path);
//            resp.setCharacterEncoding("UTF-8");
//            resp.setContentType("text/json");
//            PrintWriter writer = resp.getWriter();
//            writer.print(result);
//            writer.flush();
            ServletContext cntx= req.getServletContext();
            // Get the absolute path of the image
            String filename = path;
            // retrieve mimeType dynamically
            String mime = cntx.getMimeType(filename);
            if (mime == null) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            resp.setContentType(mime);
            File file = new File(filename);
            resp.setContentLength((int)file.length());

            FileInputStream in = new FileInputStream(file);
            OutputStream out = resp.getOutputStream();

            // Copy the contents of the file to the output stream
            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
