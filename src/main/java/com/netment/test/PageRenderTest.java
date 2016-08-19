package com.netment.test;

import com.netment.container.PageRender;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jeff on 16/5/27.
 */
public class PageRenderTest {

    private static final Logger logger = Logger.getLogger(PageRender.class);

    public static void main(String[] args) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put(null, null);
        logger.info(map.get("ddd"));

        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("fsdf", null);

        Map<String, String> table = new Hashtable<>();
        table.put("aaa", "ddd");

        ConcurrentMap<String, String> cMap = new ConcurrentHashMap<>();
        cMap.put("can not be null", "can not be null");
        cMap.put(null, null);
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(1);
        buffer.flip();
        StringBuilder sb = new StringBuilder();
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            sb.append(binarify(b));
        }
        logger.info("Big endian : " + sb.toString());
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.clear();
        buffer.putInt(0x80);
        buffer.flip();
        StringBuilder sb2 = new StringBuilder();
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            sb2.append(binarify(b));
        }
        logger.info("Little endian : " + sb2.toString());
        System.out.printf("%32s gggg", 2);
        int i = 0x80;
        logger.error(i);


    }

    public static String binarify(byte ByteToCheck) {
        String binaryCode = "";
        byte[] reference = new byte[]{(byte) 0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01};

        for (byte z = 0; z < 8; z++) {
            //if bit z of byte a is set, append a 1 to binaryCode. Otherwise, append a 0 to binaryCode
            if ((reference[z] & ByteToCheck) != 0) {
                binaryCode += "1";
            } else {
                binaryCode += "0";
            }
        }

        return binaryCode;
    }


    public static String toBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((n >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }
}
