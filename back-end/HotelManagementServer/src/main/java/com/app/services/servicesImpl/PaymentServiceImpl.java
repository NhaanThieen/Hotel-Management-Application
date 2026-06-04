package com.app.services.servicesImpl;

import com.app.dto.request.PaymentRequestDTO;
import com.app.services.PaymentService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("classpath:payment.properties") 
public class PaymentServiceImpl implements PaymentService {

    @Value("${payment.vnp.payUrl}") private String vnp_PayUrl;
    @Value("${payment.vnp.tmnCode}") private String vnp_TmnCode;
    @Value("${payment.vnp.hashSecret}") private String vnp_HashSecret;
    @Value("${payment.vnp.version}") private String vnp_Version;
    @Value("${payment.vnp.command}") private String vnp_Command;
    @Value("${payment.vnp.currCode}") private String vnp_CurrCode;
    @Value("${payment.vnp.locale}") private String vnp_Locale;
    @Value("${payment.vnp.orderType}") private String vnp_OrderType;
    @Value("${payment.vnp.returnUrl}") private String vnp_ReturnUrl;

    @Value("${payment.zalo.endpoint}") private String zalo_Endpoint;
    @Value("${payment.zalo.appId}") private String zalo_AppId;
    @Value("${payment.zalo.key1}") private String zalo_Key1;


    @Override
    public String createVnPayUrl(PaymentRequestDTO request, String ipAddress) {
        String vnp_TxnRef = String.valueOf(request.getBookingId()) + "_" + System.currentTimeMillis();
        long amount = request.getTotalAmount().longValue() * 100; 

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", vnp_CurrCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don dat phong: " + request.getBookingId());
        vnp_Params.put("vnp_OrderType", vnp_OrderType);
        vnp_Params.put("vnp_Locale", vnp_Locale);
        
        String returnUrl = vnp_ReturnUrl.replace("{bookingId}", String.valueOf(request.getBookingId()));
        vnp_Params.put("vnp_ReturnUrl", returnUrl);
        vnp_Params.put("vnp_IpAddr", ipAddress);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        try {
            Iterator<String> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
                         .append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

            return vnp_PayUrl + "?" + queryUrl;
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi khi tạo URL VNPay: " + ex.getMessage());
        }
    }


    @Override
    public String createZaloPayUrl(PaymentRequestDTO request) {
        String app_trans_id = new SimpleDateFormat("yyMMdd").format(new Date()) + "_" + request.getBookingId() + "_" + System.currentTimeMillis();
        long amount = request.getTotalAmount().longValue();
        long app_time = System.currentTimeMillis();
        
        String embed_data = "{\"redirecturl\": \"http://localhost:3000/receipt/" + request.getBookingId() + "\"}";
        String item = "[]";

        Map<String, Object> order = new HashMap<>();
        order.put("app_id", Integer.parseInt(zalo_AppId)); 
        order.put("app_trans_id", app_trans_id);
        order.put("app_time", app_time);
        order.put("app_user", "OU_Hotel_Customer");
        order.put("amount", amount);
        order.put("description", "Thanh toán đơn đặt phòng #" + request.getBookingId());
        order.put("item", item);
        order.put("embed_data", embed_data);

        String data = zalo_AppId + "|" + app_trans_id + "|OU_Hotel_Customer|" + amount + "|" + app_time + "|" + embed_data + "|" + item;
        order.put("mac", hmacSHA256(zalo_Key1, data));

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            
            org.springframework.http.HttpEntity<Map<String, Object>> entity = new org.springframework.http.HttpEntity<>(order, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(zalo_Endpoint, entity, Map.class);
            
            if (response.getBody() != null && response.getBody().get("order_url") != null) {
                return (String) response.getBody().get("order_url");
            }
            throw new RuntimeException("API ZaloPay từ chối cấp URL: " + response.getBody());
            
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi kết nối API ZaloPay: " + ex.getMessage());
        }
    }


    private String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) throw new NullPointerException();
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    private String hmacSHA256(String key, String data) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return "";
        }
    }
    
   
//    @Override
//    public boolean verifyVnPayPayment(Map<String, String> queryParams) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public boolean verifyZaloPayPayment(Map<String, String> queryParams) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}