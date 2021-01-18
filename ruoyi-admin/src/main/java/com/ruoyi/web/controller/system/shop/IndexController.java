package com.ruoyi.web.controller.system.shop;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Email;
import com.ruoyi.common.core.domain.model.CouponBody;
import com.ruoyi.common.core.domain.model.EmailBody;
import com.ruoyi.common.core.domain.model.EmailVo;
import com.ruoyi.common.enums.shop.CouponStatusEnum;
import com.ruoyi.common.enums.shop.EmailStateEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.file.shop.ShopImgFileUtil;
import com.ruoyi.email.dto.EmailOperationExecution;
import com.ruoyi.email.service.EmailSendService;
import com.ruoyi.email.service.EmailService;
import com.ruoyi.system.domain.shop.Coupon;
import com.ruoyi.system.domain.shop.CouponPhoto;
import com.ruoyi.system.domain.shop.Shop;
import com.ruoyi.system.dto.CouponOperationExecution;
import com.ruoyi.system.dto.CouponPhotoOperationExecution;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.shop.CouponPhotoService;
import com.ruoyi.system.service.shop.CouponService;
import com.ruoyi.system.service.shop.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * index page controller
 *
 * @author zuoyangding
 */

@RestController
@RequestMapping("/index")
public class IndexController {
    public static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ShopService shopService;

    @Autowired
    ISysUserService userService;

    @Autowired
    CouponService couponService;

    @Autowired
    CouponPhotoService couponPhotoService;

    @Autowired
    EmailSendService emailSendService;

    @Autowired
    EmailService emailService;


    @GetMapping("/all")
    public AjaxResult fetchAllCoupon(){
        Coupon coupon  = new Coupon();
        try {
            CouponOperationExecution coe = couponService.getCouponList(coupon);
            if (coe.getState() == CouponStatusEnum.NO_COUPON.getState()) {
                return AjaxResult.success(CouponStatusEnum.NO_COUPON.getInfo());
            } else if (coe.getState() == CouponStatusEnum.SUCCESS.getState()) {
                return AjaxResult.success(coe.getCouponList());
            } else {
                return AjaxResult.error(coe.getStateInfo());
            }
        } catch (CustomException e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @PostMapping("/filter")
    public AjaxResult fetchCouponByFilter(@RequestBody CouponBody couponBody) {
        Coupon coupon;
        if (couponBody == null) {
           coupon = new Coupon();
        } else {
            try {
                coupon = transferCouponBodyToCoupon(couponBody);
//                log.debug("couponBody {}", coupon);
            } catch (RuntimeException e) {
                return AjaxResult.error(500, e.getMessage());
            }
        }
        try {
            // filter coupon list by coupon info except the price intervals
            CouponOperationExecution coe = couponService.getCouponList(coupon);
            if (coe.getState() == CouponStatusEnum.INNER_ERROR.getState()) {
                return AjaxResult.error(coe.getStateInfo());
            } else if (coe.getState() == CouponStatusEnum.NO_COUPON.getState()) {
                return AjaxResult.success(coe.getStateInfo());
            } else {
                if (couponBody.getHigh() != null || couponBody.getLow() != null) {
                    Map<String, BigDecimal> priceInterval = new HashMap<>();
                    if (couponBody.getHigh() != null) {
                        BigDecimal highPrice = new BigDecimal(couponBody.getHigh());
                        priceInterval.put("high", highPrice);
                    }
                    if (couponBody.getLow() != null) {
                        BigDecimal lowPrice = new BigDecimal(couponBody.getLow());
                        priceInterval.put("low", lowPrice);
                    }

                    // filter coupon list by coupon price interval
                    CouponOperationExecution coe_by_price = couponService.getCouponListByPrice(priceInterval);
                    if (coe_by_price.getState() == CouponStatusEnum.INNER_ERROR.getState()) {
                        return AjaxResult.error(coe_by_price.getStateInfo());
                    } else if (coe_by_price.getState() == CouponStatusEnum.NO_COUPON.getState()) {
                        return AjaxResult.success(coe.getCouponList());
                    } else {
//                        log.debug("filter by coupon body{} ", coe.getCouponList());

                        // get the interception both upon filters
                        List<Coupon> result = coe.getCouponList().stream()
                                .filter(coe_coupon ->
                                        coe_by_price.getCouponList().stream().map(Coupon::getCouponId).anyMatch(
                                                couponId -> Objects.equals(coe_coupon.getCouponId(), couponId)
                                        )
                                ).collect(Collectors.toList());

//                        log.debug("filtered result {}", result);

                        if (result == null || result.size() < 1) {
                            return AjaxResult.success(CouponStatusEnum.NO_COUPON.getInfo());
                        } else {
                            return AjaxResult.success(result);
                        }
                    }
                } else {
                    return AjaxResult.success(coe.getCouponList());
                }
            }
        } catch (CustomException e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @PostMapping("/sendemail")
    public AjaxResult sendEmail(@RequestBody EmailBody emailBody) {
        if (checkTheEmailBody(emailBody)) {
            return AjaxResult.error(500, "Missing required Arguments");
        }
        EmailVo emailVo = setUpEmailTemplate(emailBody);
        try {
            EmailVo send_result = emailSendService.sendEmail(emailVo);
            Email email = parseEmailVoToEmail(send_result);
            if (send_result.getStatus().equals("ok")) {
                try {
                    EmailOperationExecution eoe = emailService.addEmail(email, true);
                    if (eoe.getState() == EmailStateEnum.SUCCESS.getState()) {
                        return AjaxResult.success();
                    } else {
                        return AjaxResult.error(eoe.getStateInfo());
                    }
                } catch (RuntimeException e) {
                    return AjaxResult.error(e.getMessage());
                }
            } else {
                return AjaxResult.error("send email failed");
            }
        } catch (RuntimeException e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * transfer http body to coupon entity
     * @param couponBody
     * @return
     * @throws RuntimeException
     */
    private Coupon transferCouponBodyToCoupon(CouponBody couponBody) throws RuntimeException{
        Coupon coupon = new Coupon();
        if (couponBody.getCouponId() != null) {
            coupon.setCouponId(couponBody.getCouponId());
        }
        if (couponBody.getShopId() != null) {
            Shop shop = new Shop();
            shop.setShopId(couponBody.getShopId());
            coupon.setShop(shop);
        }
        if (couponBody.getCouponCode() != null) {
            coupon.setCouponCode(couponBody.getCouponCode());
        }
        if (couponBody.getCouponDesc() != null) {
            coupon.setCouponDesc(couponBody.getCouponDesc());
        }
        if (couponBody.getCouponPrice() != null) {
            BigDecimal price = new BigDecimal(couponBody.getCouponPrice());
            BigDecimal div = new BigDecimal("100");
            price = price.divide(div, 2, RoundingMode.UNNECESSARY);
            coupon.setCouponPrice(price);
        }
        if (couponBody.getStartTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = null;
            try {
                startTime = sdf.parse(couponBody.getStartTime());
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
            coupon.setStartTime(startTime);
        }
        if (couponBody.getEndTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date endTime = null;
            try {
                endTime = sdf.parse(couponBody.getEndTime());
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
            coupon.setEndTime(endTime);
        }
        return coupon;
    }

    /**
     * check email params
     * @param emailBody
     * @return
     */
    private Boolean checkTheEmailBody(EmailBody emailBody) {
        if (emailBody == null) {
            return false;
        }
        if (emailBody.getCouponId() == null || emailBody.getCouponId() < 1) {
            return false;
        }
        if (emailBody.getUserEmail() == null || emailBody.getUserEmail().equals("")) {
            return false;
        }
        if (emailBody.getCouponPrice() == null || emailBody.getCouponPrice().equals("")) {
            return false;
        }
        if (emailBody.getCouponDesc() == null || emailBody.getCouponDesc().equals("")) {
            return false;
        }
        if (emailBody.getCouponCode() == null || emailBody.getCouponCode().equals("")) {
            return false;
        }
        if (emailBody.getStartTime() == null || emailBody.getStartTime().equals("")) {
            return false;
        }
        if (emailBody.getEndTime() == null || emailBody.getEndTime().equals("")) {
            return false;
        }
        return true;
    }

    /**
     * create Email template
     * @param emailBody
     * @return
     */
    private EmailVo setUpEmailTemplate(EmailBody emailBody) {
        EmailVo emailVo = new EmailVo();
        emailVo.setToEmail(emailBody.getUserEmail());
        Double couponPrice = Double.parseDouble(emailBody.getCouponPrice());
        Integer percentOff = (int)((1-couponPrice)*100);
        emailVo.setSubject("Coupon Code from PinPinFun");
        ArrayList<MultipartFile> attachment = new ArrayList<>();
        try {
            CouponPhotoOperationExecution cpoe =
                    couponPhotoService.selectCouponPhotoListByCouponId(emailBody.getCouponId());
            if (cpoe!=null && cpoe.getCouponPhotos()!=null && cpoe.getCouponPhotos().size()!=0) {
                for (CouponPhoto photo :cpoe.getCouponPhotos()) {
                    attachment.add(ShopImgFileUtil.getEmailAttachment(photo.getPhoto()));
                }
            }
            CouponOperationExecution coe = couponService.getCouponByCouponId(emailBody.getCouponId());
            if (coe!=null && coe.getCoupon()!=null
                    && coe.getCoupon().getStartTime()!=null && coe.getCoupon().getEndTime()!=null) {
                emailBody.setStartTime(coe.getCoupon().getStartTime().toString());
                emailBody.setEndTime(coe.getCoupon().getEndTime().toString());
            } else {
                emailBody.setStartTime((new Date()).toString());
                emailBody.setEndTime("No end time");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        if (attachment.size() != 0) {
            emailVo.setAttachment(attachment);
        }
        emailVo.setContent("Here is the coupon "
                + emailBody.getCouponDesc()
                + ". You can get "
                + percentOff + "%" + " off"
                + "\n"
                + "The coupon code is " + emailBody.getCouponCode() + "\n"
                + "The start time is " + emailBody.getStartTime() + "\n"
                + "The end time is " + emailBody.getEndTime());
        emailVo.setSendDate(new Date());
        return emailVo;
    }

    /**
     * transter emailVo to Email for db store
     * @param emailVo
     * @return
     */
    private Email parseEmailVoToEmail(EmailVo emailVo) {
        if (emailVo == null) {
            throw new RuntimeException(EmailStateEnum.INNER_ERROR.getStateInfo());
        }
        Email email = new Email();
        if (emailVo.getToEmail()!=null && !emailVo.getToEmail().equals("")) {
            email.setToEmail(emailVo.getToEmail());
        }
        if (emailVo.getSubject()!=null && !emailVo.getSubject().equals("")) {
            email.setSubject(emailVo.getSubject());
        }
        if (emailVo.getContent()!=null && !emailVo.getContent().equals("")) {
            email.setContent(emailVo.getContent());
        }
        if (emailVo.getAttachment()!=null) {
            StringBuilder tempAttachment = new StringBuilder();
            for (MultipartFile file : emailVo.getAttachment()) {
                tempAttachment.append(file.getOriginalFilename());
                tempAttachment.append(";");
            }
            email.setAttachment(tempAttachment.toString());
        }
        if (emailVo.getSendDate() != null) {
            email.setSendTime(emailVo.getSendDate());
            email.setPlanTime(emailVo.getSendDate());
        }
        email.setEmailType("0");
        email.setSendFlag("0");
        return email;
    }
}
