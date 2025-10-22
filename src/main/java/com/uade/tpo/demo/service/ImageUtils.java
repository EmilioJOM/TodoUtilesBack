package com.uade.tpo.demo.service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;

public class ImageUtils {
    // Re-encode a JPG (opcional: con resize)
    public static byte[] toJpgBytes(BufferedImage src, Float quality /*0..1 o null*/) throws Exception {
        BufferedImage rgb = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = rgb.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(src, 0, 0, Color.WHITE, null);
        g.dispose();

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (MemoryCacheImageOutputStream out = new MemoryCacheImageOutputStream(baos)) {
            writer.setOutput(out);
            ImageWriteParam p = writer.getDefaultWriteParam();
            if (p.canWriteCompressed() && quality != null) {
                p.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                p.setCompressionQuality(quality);
            }
            writer.write(null, new IIOImage(rgb, null, null), p);
            writer.dispose();
            return baos.toByteArray();
        }
    }
}
