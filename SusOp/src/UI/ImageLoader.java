package UI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Optional;

public class ImageLoader
{
    //Load images from resource folder
    public static Optional<ImageIcon> loadImage(String path)
    {
        try
        {
            URL url = Window.class.getClassLoader().getResource(path);
            ImageIcon image = new ImageIcon(url);

            return Optional.of(image);
        }
        catch (Exception e)
        {
            System.out.println("Couldnt read icon " + path + " from resources. Caused error: " + e);

            return Optional.empty();
        }
    }

    public static ImageIcon resizeIcon(ImageIcon icon, int width, int height)
    {
        return new ImageIcon(icon.getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
    }
}
