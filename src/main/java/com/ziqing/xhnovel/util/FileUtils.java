package com.ziqing.xhnovel.util;

import com.ziqing.xhnovel.bean.ImageEntity;
import com.ziqing.xhnovel.exception.XHNException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class FileUtils {

    private final static  String localDir = "D:/image";


    public static ImageEntity upload(MultipartFile file) throws XHNException {

        //获取图片名称
        String filename = file.getOriginalFilename();
        //验证是否为图片
        if (!filename.matches("^.+\\.(jpg|png|gif)$")){
            return null;
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (width + height < 2){
                return null;
            }

            String dateDir = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
            String localDirPath = localDir + dateDir;
            File dirFile = new File(localDirPath);
            if (!dirFile.exists()){
                dirFile.mkdirs();
            }

            String uuid = UUID.randomUUID().toString().replace("-", "");

            int index = filename.lastIndexOf(".");

            String fileType = filename.substring(index);

            String newFilename = uuid + fileType;

            String readFilePath = localDirPath + newFilename;

            file.transferTo(new File(readFilePath));

            String localPath = dateDir + newFilename;
            String urlPath = "http://localhost:8887" + localPath;

            return new ImageEntity(newFilename, readFilePath, urlPath);
        }catch (IOException e){
            log.error("********* 图片文件转存本地失败", e);
            throw new XHNException("图片文件转存本地失败", e.getCause());
        }
    }


    public static void deleteFile(String filePath){
        String path = localDir + filePath;
        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
    }

}
