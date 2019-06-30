package ru.job4j.controller;

import org.apache.log4j.Logger;
import ru.job4j.service.Service;
import ru.job4j.service.entities.Advertisement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class ShowAdvt extends HttpServlet {
    private final Service service = Service.getInstance();
    public static final Logger LOG = Logger.getLogger(ShowAdvt.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Advertisement> ads = service.getAllAds();
            String path = ads.get(1).getPicturePath();

            ServletContext cntx = req.getServletContext();
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
            resp.setContentLength((int) file.length());

            FileInputStream in = new FileInputStream(file);
            OutputStream out = resp.getOutputStream();

            // Copy the contents of the file to the output stream
            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            out.write(String.format("{car:%s}", "Kia").getBytes());
            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
