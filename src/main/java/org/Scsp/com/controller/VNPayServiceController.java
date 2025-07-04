package org.Scsp.com.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.Scsp.com.service.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment/vnpay")
@RequiredArgsConstructor
public class VNPayServiceController {

    @Autowired
    private final VNPayService vnPayService;

    @PostMapping("/create-payment")
    public Map<String, String> createPayment(@RequestBody Map<String, Object> payload,
                                             HttpServletRequest request) {
        long amount = Long.parseLong(payload.get("amount").toString());
        String email = payload.get("email").toString();
        String ipAddr = request.getRemoteAddr();
        if ("0:0:0:0:0:0:0:1".equals(ipAddr) || "::1".equals(ipAddr)) {
            ipAddr = "127.0.0.1";
        }

        long userId = Long.parseLong(payload.get("memberId").toString());
        long planId = Long.parseLong(payload.get("planId").toString());

        System.out.println(userId);
        System.out.println(planId);

        String paymentUrl = vnPayService.createPaymentUrl(amount, email, userId, planId, ipAddr);

        return Map.of("paymentUrl", paymentUrl);
    }
}
