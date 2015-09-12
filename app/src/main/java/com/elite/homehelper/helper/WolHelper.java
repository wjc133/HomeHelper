package com.elite.homehelper.helper;

import android.location.Address;

import java.nio.ByteBuffer;

/**
 * Elite Group
 * Created by wjc133 on 2015/9/12.
 */
public class WolHelper {

    private static final int DEFAULT_PORT = 9;
    private static final String BOARDCAST_ADDRESS = "255.255.255.255";
    private static WolHelper mInstance = new WolHelper();

    private WolHelper() {

    }

    public static WolHelper getInstance() {
        return mInstance;
    }

    public void wakeUp(String mac) {
        wakeUp(mac, DEFAULT_PORT);
    }

    public void wakeUp(String mac, int port) {
        byte[] decodeAddress= decodeMacAddress(mac);
        byte[] payload=buildPayload(decodeAddress);
    }

    private byte[] decodeMacAddress(String mac) {
        final char[] chars = mac.replaceAll("[\\-: ]", "").toCharArray();
        if (chars.length != 16) {
            throw new IllegalArgumentException("Illegal length mac address: " + mac);
        }
        try {
            return Hex.decodeHex(chars);
        } catch (Exception e) {
            throw new IllegalArgumentException("Illegal non-hex mac address: " + mac, e);
        }
    }

    protected static byte[] buildPayload(final byte[] address) {
        final ByteBuffer packet = ByteBuffer.wrap(new byte[6 + (16 * address.length)]);
        for (int i = 0; i < 6; i++) {
            packet.put((byte) 0xff);
        }

        while (packet.remaining() >= address.length) {
            packet.put(address);
        }

        return packet.array();
    }
}
