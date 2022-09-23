package ai.ml.service;

import ai.ml.util.Consts;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

@Service
public class FileController {

    Logger logger = LoggerFactory.getLogger(FileController.class);

    private static final String fileSystemDelimiter = "\\";

    private static final String basicWay = "E:\\AI\\";

    private static final String neiroFile = basicWay + "neiro.ser";

    private static final String way = basicWay + "DataSet\\";
    private static final String cache = basicWay + "Cache\\";


    public void saveNeiro(Neiro neiro) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(neiroFile));) {
            oos.writeObject(neiro);
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
    }

    public Neiro loadNeiro() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(neiroFile));
        return (Neiro) ois.readObject();
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

    public Image save(File file, Image image) throws Exception {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            bufferedImage = trim(bufferedImage);
            ImageIO.write(bufferedImage, "png", file);
            BufferedImage readBufferedImage = ImageIO.read(file);
            Image readeImage = toPerceptronResolution(SwingFXUtils.toFXImage(readBufferedImage, null));
            readBufferedImage = SwingFXUtils.fromFXImage(readeImage, null);
            ImageIO.write(readBufferedImage, "png", file);
            return readeImage;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    public Image prepareImageFoNeiro(Image image) throws Exception {
        File file = new File(cache + "cache.png");
        return save(file, image);
    }

    public Image saveImage(Image image, String ... fileWaySlices) throws Exception {
        String fileWay = toOneWay(fileWaySlices);
        File file = new File(way + fileWay + filesCount(fileWay) + ".png");
        return save(file, image);
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
        int bottom = getBottomInset(img);
        int left = getLeftInset(img);
        int right = getRightInset(img);
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
