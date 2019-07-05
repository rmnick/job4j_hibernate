package ru.job4j.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class ListenerForCreatePathOnStartUp implements ServletContextListener {
    public static final String ROOT_LINUX_PATH = "/home/nick";
    public static final String ROOT_WIN_PATH = "C:";
    public static final String PATH_IMG_NAME = "pictures";

    /**
     * create root folder for persons images
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String str = String.format("%s%s%s", ROOT_WIN_PATH, File.separator, PATH_IMG_NAME);
        File folder = new File(str);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }
}
