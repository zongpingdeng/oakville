import org.apache.log4j.Logger;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * Created by jeff on 16/5/24.
 */
public class UnsafeDirectMem {

    private static final Logger logger = Logger.getLogger(UnsafeDirectMem.class);

    private static final int MB = 1024*1024;

    public static void main(String args[]) throws Exception{
        Field  unsafeF = Unsafe.class.getDeclaredFields()[0];
        unsafeF.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeF.get(null);
        String str1 = new StringBuffer("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuffer("Ja").append("va").toString();
        System.out.println(str2.intern() == str2);

        Enumeration<NetworkInterface> all = NetworkInterface.getNetworkInterfaces();
        while (all.hasMoreElements()){
            NetworkInterface face = all.nextElement();
            logger.error(face.getDisplayName());
            Enumeration<InetAddress> addrs = face.getInetAddresses();
            while (addrs.hasMoreElements()){
                InetAddress addr = addrs.nextElement();
                String ip = addr.getHostAddress();
                String regex1 = "(192[.]168[.]\\d{1,3}[.]\\d{1,3})";
                String regex2 = "(10[.]0[.]\\d{1,3}[.]\\d{1,3})";
                if (Pattern.matches(regex1, ip) || Pattern.matches(regex2 , ip)) {
                    logger.error(addr.getHostAddress());
                }
                //logger.error(addr.getCanonicalHostName());
            }
        }
        //unsafe.monitorEnter();
//        while (false){
//            unsafe.allocateMemory(MB);
//            ByteBuffer.allocateDirect(MB);
//        }
    }
}
