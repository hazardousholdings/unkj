package com.hazardousholdings.unkj;

public class Version {

	private int major;
	private int minor;
	private int patch;
	private String snapshot;
	private String revision;

	public Version(int major, int minor) {
		this(major, minor, 0);
	}

	public Version(int major, int minor, int patch) {
		this(major, minor, patch, null, null);
	}

	public Version(int major, int minor, int patch, String revision) {
		this(major, minor, patch, null, revision);
	}

	public Version(int major, int minor, int patch, String snapshot, String revision) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
		this.snapshot = snapshot;
		this.revision = revision;
	}

	public int getMajor() {
		return this.major;
	}

	public int getMinor() {
		return this.minor;
	}

	public int getPatch() {
		return this.patch;
	}

	public String getSnapshot() {
		return this.snapshot;
	}

	public String getRevision() {
		return this.revision;
	}

	@Override
	public String toString() {
		String str = Integer.toString(this.major) + Integer.toString(this.minor) + Integer.toString(this.patch);
		if(this.snapshot != null) {
			str += "-" + this.snapshot;
		}
		if(this.revision != null) {
			str += "+" + this.revision;
		}
		return str;
	}
}
