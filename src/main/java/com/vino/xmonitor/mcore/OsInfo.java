package com.vino.xmonitor.mcore;

/**
 * 操作系统类： 获取System.getProperty("os.name")对应的操作系统
 *
 * @author phantom
 */
public class OsInfo {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static OsInfo instance = new OsInfo();

    private EPlatform platform;

    private OsInfo() {
    }

    private static boolean isLinux() {
        return OS.contains("linux");
    }

    private static boolean isMacOS() {
        return OS.contains("mac") && OS.indexOf("os") > 0 && !OS.contains("x");
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
            instance.platform = EPlatform.AIX;
        } else if (isDigitalUnix()) {
            instance.platform = EPlatform.Digital_Unix;
        } else if (isFreeBSD()) {
            instance.platform = EPlatform.FreeBSD;
        } else if (isHPUX()) {
            instance.platform = EPlatform.HP_UX;
        } else if (isIrix()) {
            instance.platform = EPlatform.Irix;
        } else if (isLinux()) {
            instance.platform = EPlatform.Linux;
        } else if (isMacOS()) {
            instance.platform = EPlatform.Mac_OS;
        } else if (isMacOSX()) {
            instance.platform = EPlatform.Mac_OS_X;
        } else if (isMPEiX()) {
            instance.platform = EPlatform.MPEiX;
        } else if (isNetWare()) {
            instance.platform = EPlatform.NetWare_411;
        } else if (isOpenVMS()) {
            instance.platform = EPlatform.OpenVMS;
        } else if (isOS2()) {
            instance.platform = EPlatform.OS2;
        } else if (isOS390()) {
            instance.platform = EPlatform.OS390;
        } else if (isOSF1()) {
            instance.platform = EPlatform.OSF1;
        } else if (isSolaris()) {
            instance.platform = EPlatform.Solaris;
        } else if (isSunOS()) {
            instance.platform = EPlatform.SunOS;
        } else if (isWindows()) {
            instance.platform = EPlatform.Windows;
        } else {
            instance.platform = EPlatform.Others;
        }
        return instance.platform;
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
    @SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
    public enum EPlatform {

        Any("any"), Linux("Linux"), Mac_OS("Mac OS"), Mac_OS_X("Mac OS X"), Windows("Windows"), OS2("OS/2"), Solaris(
                "Solaris"), SunOS("SunOS"), MPEiX("MPE/iX"), HP_UX("HP-UX"), AIX("AIX"), OS390("OS/390"), FreeBSD(
                "FreeBSD"), Irix("Irix"), Digital_Unix("Digital Unix"), NetWare_411(
                "NetWare"), OSF1("OSF1"), OpenVMS("OpenVMS"), Others("Others");

        private EPlatform(String desc) {
            this.description = desc;
        }


        @Override
        public String toString() {
            return description;
        }

        private String description;
    }
}