package com.ruoyi.common.utils.file.shop;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class ShopImgFileUtil {
    public static String getShopBaseDir() {
        StringBuffer sb = new StringBuffer();
        sb.append(RuoYiConfig.getUploadPath());
        sb.append(File.separator);
        sb.append("shop");
        return sb.toString();
    }

    public static String uploadShopImgFile(String baseDir, MultipartFile file) throws IOException {
        try {
            String imagePath = FileUploadUtils.upload(baseDir, file);
            return imagePath;
        } catch (IOException e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}
