package ai.ml.service;

import ai.ml.util.Consts;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

@Service
public class FileController {

    private static final String fileSystemDelimiter = "\\";

    private static final String neiroFile = "E:\\AI\\neiro.ser";

    private static final String way = "E:\\AI\\DataSet\\";


    public void saveNeiro(Neiro neiro) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(neiroFile));) {
            oos.writeObject(neiro);
        } catch (IOException e) {
           System.out.println(e.getMessage());
        }
    }

    public Neiro loadNeiro() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(neiroFile));) {
            return (Neiro) ois.readObject();
        } catch (Exception e) {
            System.out.println("neiro -> " + e.getMessage());
        }
        return new Neiro();
    }


    public int filesCount(String fileWay) throws Exception {
        try {
            return Objects.requireNonNull(new File(way + fileWay).list()).length;
        } catch (Exception e) {
            if (new File(way + fileWay).mkdirs()) {
                return 0;
            }
        }
        throw new Exception("Can't create directory");
    }

//    public void loadImages(String symbol) {
//        BufferedImage
//    }

    public void saveImage(Image image, String ... fileWaySlices) {
        try {
            String fileWay = toOneWay(fileWaySlices);
            File file = new File(way + fileWay + filesCount(fileWay) + ".png");
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            bufferedImage = trim(bufferedImage);
            ImageIO.write(bufferedImage, "png", file);
            BufferedImage readBufferedImage = ImageIO.read(file);
            Image readeImage = toPerceptronResolution(SwingFXUtils.toFXImage(readBufferedImage, null));
            readBufferedImage = SwingFXUtils.fromFXImage(readeImage, null);
            ImageIO.write(readBufferedImage, "png", file);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private String toOneWay(String ... fileWay) {
        StringBuilder oneWay = new StringBuilder();
        for(String slice : fileWay){
            oneWay.append(slice).append(fileSystemDelimiter);
        }
        return oneWay.toString();
    }

    private Image toPerceptronResolution(Image image) {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(Consts.xSize);
        imageView.setFitWidth(Consts.ySize);
        imageView.setPreserveRatio(false);
        imageView.setSmooth(true);
        return convertToBinary(imageView.snapshot(null, null));
    }

    private Image convertToBinary(Image image) {
        WritableImage writableImage = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        PixelReader pixelReader = writableImage.getPixelReader();
        for (int i = 0; i < writableImage.getHeight(); i++) {
            for (int j = 0; j < writableImage.getWidth(); j++) {
                Color c = pixelReader.getColor(j, i);
                if (c.getRed() > 0 || c.getGreen() > 0 || c.getBlue() > 0) {
                    pixelWriter.setColor(j, i, Color.WHITE);
                } else {
                    pixelWriter.setColor(j, i, Color.BLACK);
                }
            }
        }
        return new ImageView(writableImage).snapshot(null, null);
    }

    private BufferedImage trim(BufferedImage img) {
        int top = getTopInset(img);
        System.out.println("top = " + top);
        int bottom = getBottomInset(img);
        System.out.println("bottom = " + bottom);
        int left = getLeftInset(img);
        System.out.println("left = " + left);
        int right = getRightInset(img);
        System.out.println("right = " + right);
        return img.getSubimage(left, top, right-left, bottom-top);
    }

    private int getLeftInset(BufferedImage img) {
        for(int x = 0; x < img.getHeight(); x++) {
            for(int y = 0; y < img.getWidth(); y++) {
                if (isNotWhite(img, x, y)) {
                    return x;
                }
            }
        }
        return 0;
    }

    private int getRightInset(BufferedImage img) {
        for(int x = img.getHeight()-1; x >= 0 ; x--) {
            for(int y = 0; y < img.getWidth(); y++) {
                if (isNotWhite(img, x, y)) {
                    return x;
                }
            }
        }
        return img.getHeight()-1;
    }

    private int getTopInset(BufferedImage img) {
        for(int y = 0; y < img.getWidth(); y++) {
            for(int x = 0; x < img.getHeight(); x++) {
                if (isNotWhite(img, x, y)) {
                    return y;
                }
            }
        }
        return 0;
    }

    private int getBottomInset(BufferedImage img) {
        for(int y = img.getWidth()-1; y >= 0; y--) {
            for(int x = 0; x < img.getHeight(); x++) {
                if (isNotWhite(img, x, y)) {
                    return y;
                }
            }
        }
        return 0;
    }

    private boolean isNotWhite(BufferedImage img, int x, int y) {
        return !new java.awt.Color(img.getRGB(x, y), true).equals(java.awt.Color.WHITE);
    }
}
