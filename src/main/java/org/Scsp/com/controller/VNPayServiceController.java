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

        String paymentUrl = vnPayService.createPaymentUrl(amount, email, ipAddr);

        return Map.of("paymentUrl", paymentUrl);
    }
}
