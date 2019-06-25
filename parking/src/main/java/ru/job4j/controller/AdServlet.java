package ru.job4j.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdServlet extends HttpServlet {
    private final Map<String, String> attributes = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // creates the directory if it does not exist
            //for win & lin
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            Date resultdate = new Date(System.currentTimeMillis());
            String dateStr = resultdate.toString().replaceAll("\\s", "");

            String uploadDirUserName = String.format("%s/%s", req.getServletContext().getInitParameter("file-upload"),
                    req.getSession(false).getAttribute("login").toString());
            File uploadDir = new File(uploadDirUserName);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String advtPath = String.format("%s/%s", uploadDirUserName, dateStr);
            new File(advtPath).mkdir();

            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    attributes.put(item.getFieldName(), item.getString());
                } else if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String filePath = advtPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                    // saves the file on disk
                    item.write(storeFile);
                }
            }
            attributes.values().stream().forEach(System.out::println);
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        } catch (Exception e) {
            req.setAttribute("message",
                    "There was an error: " + e.getMessage());
        }
    }
}
