package com.leviathanstudio.craftstudio.common;

public enum RenderType {

	BLOCK("blocks/"), ENTITY("entity/");

	String folderName;

	private RenderType(String folderNameIn) {
		this.folderName = folderNameIn;
	}

	public String getFolderName() {
		return this.folderName;
	}
}
