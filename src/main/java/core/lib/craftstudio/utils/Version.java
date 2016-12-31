package lib.craftstudio.utils;

import java.net.URL;
import java.util.Scanner;

import lib.craftstudio.CraftStudioApi;

public class Version implements Comparable<Version>
{
    private static Version actualVersion;
    private static Version newestVersion;

    enum Status {
        OUTDATED, UP_TO_DATE, AHEAD;
    }

    private String version;

    public final String get() {
        return this.version;
    }

    public Version() {}

    public Version(String version) {
        if (version == null)
            throw new IllegalArgumentException("Version can not be null");
        if (!version.matches("[0-9]+(\\.[0-9]+)*"))
            throw new IllegalArgumentException("Invalid version format");
        this.version = version;
    }

    @Override
    public int compareTo(Version that) {
        if (that == null)
            return 1;
        String[] thisParts = this.get().split("\\.");
        String[] thatParts = that.get().split("\\.");
        int length = Math.max(thisParts.length, thatParts.length);
        for (int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
            if (thisPart < thatPart)
                return -1;
            if (thisPart > thatPart)
                return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (that == null)
            return false;
        if (this.getClass() != that.getClass())
            return false;
        return this.compareTo((Version) that) == 0;
    }

    public static Version getActualVersion() {
        return Version.actualVersion;
    }

    private void setActualVersion(Version actualVersion) {
        Version.actualVersion = actualVersion;
    }

    public static Version getNewestVersion() {
        return Version.newestVersion;
    }

    private void setNewestVersion(Version newestVersion) {
        Version.newestVersion = newestVersion;
    }

    private static String status(Version actualVersion, Version newestVersion, boolean showing) {
        switch (actualVersion.compareTo(newestVersion)) {
            case -1:
                return String.format("Actual API Version (%s) is %s: %s", actualVersion.get(), Version.Status.OUTDATED, newestVersion.get());
            case 0:
                if (showing)
                    return String.format("Actual API Version (%s) is %s: %s", actualVersion.get(), Version.Status.UP_TO_DATE, newestVersion.get());
                break;
            case 1:
                return String.format("Actual API Version (%s) is %s: %s", actualVersion.get(), Version.Status.AHEAD, newestVersion.get());
            default:
                return null;
        }
        return null;
    }

    public void preInit() throws Exception {
        URL url = new URL("https://dl.dropboxusercontent.com/u/51292197/CraftStudio%20Converter/version.txt");
        Scanner s = new Scanner(url.openStream());
        this.setActualVersion(new Version("0.9"));
        this.setNewestVersion(new Version(s.next()));
        s.close();
        CraftStudioApi.getLogger().info(Version.status(Version.getActualVersion(), Version.getNewestVersion(), true));
    }

}