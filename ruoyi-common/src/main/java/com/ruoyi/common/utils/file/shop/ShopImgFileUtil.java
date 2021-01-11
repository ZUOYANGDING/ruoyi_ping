package com.ruoyi.common.utils.file.shop;

import ch.qos.logback.core.util.FileUtil;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    public static MultipartFile getEmailAttachment(String fileDir) throws IOException {
        try {
            String baseDir = RuoYiConfig.getProfile();
            String filePath = baseDir + File.separator + fileDir;
            File file = new File(filePath);
            FileItemFactory factory = new DiskFileItemFactory(16, null);
            FileItem item=factory.createItem(file.getName(),"text/plain",true,file.getName());
            int byteRead = 0;
            byte[] byteBuffer = new byte[10240];
            FileInputStream in = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((byteRead = in.read(byteBuffer, 0, 10240))!=-1) {
                os.write(byteBuffer, 0, byteRead);
            }
            os.close();
            in.close();
            MultipartFile multipartFile = new CommonsMultipartFile(item);
            log.debug("multipartFile name {}", multipartFile.getOriginalFilename());
            return multipartFile;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
