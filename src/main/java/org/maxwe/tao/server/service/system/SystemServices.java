package org.maxwe.tao.server.service.system;

import org.maxwe.tao.server.common.utils.DateTime;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Formatter;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by Pengwei Ding on 2016-11-13 16:19.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SystemServices implements ISystemServices {
    @Override
    public SystemEntity retrieveStatus() {
        SystemEntity systemEntity = new SystemEntity();
        Properties properties = System.getProperties();
        systemEntity.setOsName(properties.getProperty("os.name"));
        systemEntity.setOsVersion(properties.getProperty("os.version"));
        systemEntity.setOsArch(properties.getProperty("os.arch"));
        try {
            systemEntity.setOsIP(this.getIP());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            systemEntity.setOsMAC(this.getMac());
        } catch (Exception e) {
            e.printStackTrace();
        }
        systemEntity.setOsDate(DateTime.getCurrentTimestamp());
        systemEntity.setJavaVersion(properties.getProperty("java.version"));
        systemEntity.setServerName("");
        systemEntity.setServerVersion("");
        systemEntity.setDbName("MySQL");
        systemEntity.setDbVersion("6.0");
        systemEntity.setCPUS(Runtime.getRuntime().availableProcessors());
        systemEntity.setCPURatio(0.8f);
        systemEntity.setMemoryTotal(Runtime.getRuntime().totalMemory());
        systemEntity.setMemoryUsed(Runtime.getRuntime().maxMemory());
        systemEntity.setMemoryFree(Runtime.getRuntime().freeMemory());
        long[] diskSpace = getDiskSpace();
        systemEntity.setDiskTotal(diskSpace[0]);
        systemEntity.setDiskFree(diskSpace[2]);
        systemEntity.setDiskUsable(diskSpace[1]);
        return systemEntity;
    }

    private String getIP() throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        NetworkInterface ni = NetworkInterface.getByInetAddress(address);
        ni.getInetAddresses().nextElement().getAddress();
        return address.getHostAddress();
    }

    private String getMac() throws Exception {
        InetAddress localHost = InetAddress.getLocalHost();
        NetworkInterface byINetAddress = NetworkInterface.getByInetAddress(localHost);
        byINetAddress.getInetAddresses().nextElement().getAddress();
        byte[] hardwareAddress = byINetAddress.getHardwareAddress();
        if (hardwareAddress == null) {
            return null;
        }
        String mac = "";
        Formatter formatter = new Formatter();
        for (int index = 0; index < hardwareAddress.length; index++) {
            mac = formatter.format(Locale.getDefault(), "%02X%s", hardwareAddress[index],
                    (index < hardwareAddress.length - 1) ? "-" : "").toString();

        }
        return mac;
    }

    private long[] getDiskSpace() {
        long[] longs = new long[3];
        File[] files = File.listRoots();
        for (File file : files) {
            long freeSpace = file.getFreeSpace();// 空闲空间
            long usableSpace = file.getUsableSpace();// 可用空间
            long totalSpace = file.getTotalSpace();// 总空间
            longs[0] = longs[0] + totalSpace;
            longs[1] = longs[1] + usableSpace;
            longs[2] = longs[2] + freeSpace;
        }
        return longs;
    }
}
