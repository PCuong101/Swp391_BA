package org.Scsp.com.service;

public interface VNPayService {

    String createPaymentUrl(long amount, String email, String ipAddr);
}
