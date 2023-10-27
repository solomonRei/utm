package org.utm.lab2.utils.files;

import org.utm.lab2.utils.files.abstracts.FileDetails;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageFileDetails extends FileDetails {

    public ImageFileDetails(String folderPath) {
        super(folderPath);
    }

    @Override
    public void displaySpecificInfo(File file) {
        System.out.println("Image Size: " + getImageDimensions(file));
    }


    private String getImageDimensions(File file) {
        try {
            var image = ImageIO.read(file);
            return image.getWidth() + "x" + image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
}
