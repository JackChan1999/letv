package com.letv.mobile.core.time;

import android.support.v4.media.TransportMediator;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Deprecated
public class SntpClient {
    private static final int NTP_MODE_CLIENT = 3;
    private static final int NTP_PACKET_SIZE = 48;
    private static final int NTP_PORT = 123;
    private static final int NTP_VERSION = 3;
    private static final long OFFSET_1900_TO_1970 = 2208988800L;
    private static final int ORIGINATE_TIME_OFFSET = 24;
    private static final int RECEIVE_TIME_OFFSET = 32;
    private static final int TRANSMIT_TIME_OFFSET = 40;
    private long mNtpTime = -1;
    private long mNtpTimeReference = -1;
    private long mRoundTripTime = -1;

    public boolean requestTime(String host, int timeout) {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(timeout);
            byte[] buffer = new byte[NTP_PACKET_SIZE];
            DatagramPacket request = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(host), NTP_PORT);
            buffer[0] = (byte) 27;
            long requestTime = System.currentTimeMillis();
            long requestTicks = System.nanoTime() / 1000;
            writeTimeStamp(buffer, 40, requestTime);
            socket.send(request);
            socket.receive(new DatagramPacket(buffer, buffer.length));
            long responseTicks = System.nanoTime() / 1000;
            long responseTime = requestTime + (responseTicks - requestTicks);
            socket.close();
            long originateTime = readTimeStamp(buffer, 24);
            long receiveTime = readTimeStamp(buffer, 32);
            long transmitTime = readTimeStamp(buffer, 40);
            long roundTripTime = (responseTicks - requestTicks) - (transmitTime - receiveTime);
            this.mNtpTime = responseTime + (((receiveTime - originateTime) + (transmitTime - responseTime)) / 2);
            this.mNtpTimeReference = responseTicks;
            this.mRoundTripTime = roundTripTime;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long getNtpTime() {
        return this.mNtpTime;
    }

    public long getNtpTimeReference() {
        return this.mNtpTimeReference / 1000;
    }

    public long getRoundTripTime() {
        return this.mRoundTripTime;
    }

    public boolean isFetchedTime() {
        return getNtpTime() != -1;
    }

    public long getCurrentTime() {
        if (isFetchedTime()) {
            return getNtpTime();
        }
        return -1;
    }

    private long read32(byte[] buffer, int offset) {
        int i0;
        int i1;
        int i2;
        int i3;
        byte b0 = buffer[offset];
        byte b1 = buffer[offset + 1];
        byte b2 = buffer[offset + 2];
        byte b3 = buffer[offset + 3];
        if ((b0 & 128) == 128) {
            i0 = (b0 & TransportMediator.KEYCODE_MEDIA_PAUSE) + 128;
        } else {
            byte i02 = b0;
        }
        if ((b1 & 128) == 128) {
            i1 = (b1 & TransportMediator.KEYCODE_MEDIA_PAUSE) + 128;
        } else {
            byte i12 = b1;
        }
        if ((b2 & 128) == 128) {
            i2 = (b2 & TransportMediator.KEYCODE_MEDIA_PAUSE) + 128;
        } else {
            byte i22 = b2;
        }
        if ((b3 & 128) == 128) {
            i3 = (b3 & TransportMediator.KEYCODE_MEDIA_PAUSE) + 128;
        } else {
            byte i32 = b3;
        }
        return (((((long) i0) << 24) + (((long) i1) << 16)) + (((long) i2) << 8)) + ((long) i3);
    }

    private long readTimeStamp(byte[] buffer, int offset) {
        return ((read32(buffer, offset) - OFFSET_1900_TO_1970) * 1000) + ((1000 * read32(buffer, offset + 4)) / 4294967296L);
    }

    private void writeTimeStamp(byte[] buffer, int offset, long time) {
        long seconds = time / 1000;
        long milliseconds = time - (1000 * seconds);
        seconds += OFFSET_1900_TO_1970;
        int i = offset + 1;
        buffer[offset] = (byte) ((int) (seconds >> 24));
        offset = i + 1;
        buffer[i] = (byte) ((int) (seconds >> 16));
        i = offset + 1;
        buffer[offset] = (byte) ((int) (seconds >> 8));
        offset = i + 1;
        buffer[i] = (byte) ((int) seconds);
        long fraction = (4294967296L * milliseconds) / 1000;
        i = offset + 1;
        buffer[offset] = (byte) ((int) (fraction >> 24));
        offset = i + 1;
        buffer[i] = (byte) ((int) (fraction >> 16));
        i = offset + 1;
        buffer[offset] = (byte) ((int) (fraction >> 8));
        offset = i + 1;
        buffer[i] = (byte) ((int) (Math.random() * 255.0d));
    }
}
