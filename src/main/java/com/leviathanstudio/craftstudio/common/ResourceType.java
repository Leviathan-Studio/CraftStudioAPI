package com.leviathanstudio.craftstudio.common;

public enum ResourceType {

    MODEL("craftstudio/models/", ".csjsmodel"), ANIM("craftstudio/animations/", ".csjsmodelanim");

    String path, extension;

    private ResourceType(String pathIn, String extensionIn) {
        this.path = pathIn;
        this.extension = extensionIn;
    }

    public String getPath() {
        return this.path;
    }

    public String getExtension() {
        return this.extension;
    }
}
