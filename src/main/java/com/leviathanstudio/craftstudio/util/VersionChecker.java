package com.leviathanstudio.craftstudio.util;

import java.net.URL;
import java.util.Scanner;

import com.leviathanstudio.craftstudio.CraftStudioApi;

public class VersionChecker implements Comparable<VersionChecker> {
	private static VersionChecker actualVersion;
	private static VersionChecker newestVersion;

	enum Status {
		OUTDATED, UP_TO_DATE, AHEAD;
	}

	private String version;

	public final String get() {
		return this.version;
	}

	public VersionChecker() {
	}

	public VersionChecker(String version) {
		if (version == null)
			throw new IllegalArgumentException("Version can not be null");
		if (!version.matches("[0-9]+(\\.[0-9]+)*"))
			throw new IllegalArgumentException("Invalid version format");
		this.version = version;
	}

	@Override
	public int compareTo(VersionChecker that) {
		if (that == null)
			return 1;
		final String[] thisParts = this.get().split("\\.");
		final String[] thatParts = that.get().split("\\.");
		final int length = Math.max(thisParts.length, thatParts.length);
		for (int i = 0; i < length; i++) {
			final int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
			final int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
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
		return this.compareTo((VersionChecker) that) == 0;
	}

	public static VersionChecker getActualVersion() {
		return VersionChecker.actualVersion;
	}

	private void setActualVersion(VersionChecker actualVersion) {
		VersionChecker.actualVersion = actualVersion;
	}

	public static VersionChecker getNewestVersion() {
		return VersionChecker.newestVersion;
	}

	private void setNewestVersion(VersionChecker newestVersion) {
		VersionChecker.newestVersion = newestVersion;
	}

	private static String status(VersionChecker actualVersion, VersionChecker newestVersion, boolean showing) {
		switch (actualVersion.compareTo(newestVersion)) {
		case -1:
			return String.format("Actual API Version (%s) is %s: %s", actualVersion.get(),
					VersionChecker.Status.OUTDATED, newestVersion.get());
		case 0:
			if (showing)
				return String.format("Actual API Version (%s) is %s: %s", actualVersion.get(),
						VersionChecker.Status.UP_TO_DATE, newestVersion.get());
			break;
		case 1:
			return String.format("Actual API Version (%s) is %s: %s", actualVersion.get(), VersionChecker.Status.AHEAD,
					newestVersion.get());
		default:
			return null;
		}
		return null;
	}

	public void preInit() {
		try {
			final URL url = new URL("https://leviathan-studio.com/amateis/craftstudio-converter/version.txt");
			final Scanner s = new Scanner(url.openStream());
			this.setActualVersion(new VersionChecker(CraftStudioApi.ACTUAL_VERSION));
			this.setNewestVersion(new VersionChecker(s.next()));
			s.close();
			CraftStudioApi.getLogger().info(
					VersionChecker.status(VersionChecker.getActualVersion(), VersionChecker.getNewestVersion(), true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}