package com.vino.xmonitor.utils;

/**
 * 操作系统类： 获取System.getProperty("os.name")对应的操作系统
 *
 * @author
 */
public class OsInfo {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static OsInfo _instance = new OsInfo();

    private EPlatform platform;

    private OsInfo() {
    }

    private static boolean isLinux() {
        return OS.contains("linux");
    }

    private static boolean isMacOS() {
        return OS.contains("mac") && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
    }

    private static boolean isMacOSX() {
        return OS.contains("mac") && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
    }

    public static boolean isWindows() {
        return OS.contains("windows");
    }

    private static boolean isOS2() {
        return OS.contains("os/2");
    }

    private static boolean isSolaris() {
        return OS.contains("solaris");
    }

    private static boolean isSunOS() {
        return OS.contains("sunos");
    }

    private static boolean isMPEiX() {
        return OS.contains("mpe/ix");
    }

    private static boolean isHPUX() {
        return OS.contains("hp-ux");
    }

    private static boolean isAix() {
        return OS.contains("aix");
    }

    private static boolean isOS390() { return OS.contains("os/390"); }

    private static boolean isFreeBSD() {
        return OS.contains("freebsd");
    }

    private static boolean isIrix() {
        return OS.contains("irix");
    }

    private static boolean isDigitalUnix() {
        return OS.contains("digital") && OS.indexOf("unix") > 0;
    }

    private static boolean isNetWare() {
        return OS.contains("netware");
    }

    private static boolean isOSF1() {
        return OS.contains("osf1");
    }

    private static boolean isOpenVMS() {
        return OS.contains("openvms");
    }

    /**
     * 获取操作系统名字
     *
     * @return 操作系统名
     */
    private static EPlatform getOSname() {
        if (isAix()) {
            _instance.platform = EPlatform.AIX;
        } else if (isDigitalUnix()) {
            _instance.platform = EPlatform.Digital_Unix;
        } else if (isFreeBSD()) {
            _instance.platform = EPlatform.FreeBSD;
        } else if (isHPUX()) {
            _instance.platform = EPlatform.HP_UX;
        } else if (isIrix()) {
            _instance.platform = EPlatform.Irix;
        } else if (isLinux()) {
            _instance.platform = EPlatform.Linux;
        } else if (isMacOS()) {
            _instance.platform = EPlatform.Mac_OS;
        } else if (isMacOSX()) {
            _instance.platform = EPlatform.Mac_OS_X;
        } else if (isMPEiX()) {
            _instance.platform = EPlatform.MPEiX;
        } else if (isNetWare()) {
            _instance.platform = EPlatform.NetWare_411;
        } else if (isOpenVMS()) {
            _instance.platform = EPlatform.OpenVMS;
        } else if (isOS2()) {
            _instance.platform = EPlatform.OS2;
        } else if (isOS390()) {
            _instance.platform = EPlatform.OS390;
        } else if (isOSF1()) {
            _instance.platform = EPlatform.OSF1;
        } else if (isSolaris()) {
            _instance.platform = EPlatform.Solaris;
        } else if (isSunOS()) {
            _instance.platform = EPlatform.SunOS;
        } else if (isWindows()) {
            _instance.platform = EPlatform.Windows;
        } else {
            _instance.platform = EPlatform.Others;
        }
        return _instance.platform;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(OsInfo.getOSname());
    }

    /**
     * 平台
     *
     * @author isea533
     */
    public enum EPlatform {
        Any("any"), Linux("Linux"), Mac_OS("Mac OS"), Mac_OS_X("Mac OS X"), Windows("Windows"), OS2("OS/2"), Solaris(
                "Solaris"), SunOS("SunOS"), MPEiX("MPE/iX"), HP_UX("HP-UX"), AIX("AIX"), OS390("OS/390"), FreeBSD(
                "FreeBSD"), Irix("Irix"), Digital_Unix("Digital Unix"), NetWare_411(
                "NetWare"), OSF1("OSF1"), OpenVMS("OpenVMS"), Others("Others");

        private EPlatform(String desc) {
            this.description = desc;
        }

        public String toString() {
            return description;
        }

        private String description;
    }
}