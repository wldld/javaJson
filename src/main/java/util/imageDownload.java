package util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class imageDownload {
    public static void main(String[] args) {
        String url = "http://img.aibizhi.adesk.com/5a51b4d5e7bce736590a6d38?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280";
        downloadPicture(url);
    }
    //链接url下载图片
    private static void downloadPicture(String urlList) {
        URL url = null;
        int imageNumber = 0;

        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            String imageName =  "F:/temp/test.jpg";

            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context=output.toByteArray();
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
