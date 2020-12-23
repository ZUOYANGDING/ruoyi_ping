package com.ruoyi.common.utils.file.shop;

import ch.qos.logback.core.util.FileUtil;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * shop image file upload and delete util
 *
 * @author zuoyangding
 */

public class ShopImgFileUtil {
    private static final Logger log = LoggerFactory.getLogger(ShopImgFileUtil.class);

    public static String getShopBaseDir() {
        StringBuffer sb = new StringBuffer();
        sb.append(RuoYiConfig.getUploadPath());
        sb.append(File.separator);
        sb.append("shop");
        return sb.toString();
    }

    public static String getCouponBaseDir() {
        StringBuffer sb = new StringBuffer();
        sb.append(RuoYiConfig.getUploadPath());
        sb.append(File.separator);
        sb.append("coupon");
        return sb.toString();
    }

    public static String uploadImgFile(String baseDir, MultipartFile file) throws IOException {
        try {
            String imagePath = FileUploadUtils.upload(baseDir, file);
            return imagePath;
        } catch (IOException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    public static void deleteImgFile(List<String> fileList) {
        if (fileList != null || fileList.size()>0) {
            for (String filePath : fileList) {
                log.debug("file path for delete: {}", filePath);
                String[] filePathArray = filePath.split("upload");
                StringBuffer sb = new StringBuffer();
                sb.append(RuoYiConfig.getUploadPath());
                sb.append(filePathArray[1]);
                log.debug("file path for delete after modify: {}", sb.toString());
                FileUtils.deleteFile(sb.toString());
            }
        }
    }
}
