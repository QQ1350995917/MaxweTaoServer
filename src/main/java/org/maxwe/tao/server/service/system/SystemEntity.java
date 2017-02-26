package org.maxwe.tao.server.service.system;

/**
 * Created by Pengwei Ding on 2017-02-26 08:49.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SystemEntity {
    /**系统名*/
    private String osName;
    /**系统架构*/
    private String osArch ;
    /**系统版本号*/
    private String osVersion ;
    /**文件分隔符  在 unix 系统中是＂／＂*/
    private String fileSeparator;
    /**系统IP*/
    private String ip ;
    /**系统MAC地址*/
    private String mac;
    /**系统时间*/
    private long date;
    /**系统CPU个数*/
    private Integer cpus ;
    /** 可使用内存. */
    private long totalMemory;
    /** 剩余内存. */
    private long freeMemory;
    /** 最大可使用内存. */
    private long maxMemory;
    /** 总的物理内存. */
    private long totalMemorySize;
    /** 剩余的物理内存. */
    private long freePhysicalMemorySize;
    /** 已使用的物理内存. */
    private long usedMemory;

    /**系统用户名*/
    private String os_user_name;
    /**用户的当前工作目录*/
    private String os_user_dir;
    /**用户的主目录*/
    private String os_user_home;

    /**Java的运行环境版本*/
    private String java_version ;
    /**java默认的临时文件路径*/
    private String java_io_tmpdir;

    /**java 平台*/
    private String sun_desktop ;



    /**服务context**/
    private String server_context ;
    /**服务器名*/
    private String server_name;
    /**服务器端口*/
    private Integer server_port;
    /**服务器地址*/
    private String server_addr;
    /**获得客户端电脑的名字，若失败，则返回客户端电脑的ip地址*/
    private String server_host;
    /**服务协议*/
    private String server_protocol;








}
