package com.github.acapola.opendev.mamasync;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * Created by seb on 6/15/14.
 */
public class FsDirectory /*implements Path*/ {
    protected Set<FsDirectory> directories;
    protected Set<FsFile> files;
    protected String name;

    public Set<FsDirectory> getDirectories() {
        return directories;
    }

    public void setDirectories(Set<FsDirectory> directories) {
        this.directories = directories;
    }

    public Set<FsFile> getFiles() {
        return files;
    }

    public void setFiles(Set<FsFile> files) {
        this.files = files;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
