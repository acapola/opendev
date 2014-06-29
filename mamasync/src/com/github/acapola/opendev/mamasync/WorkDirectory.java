package com.github.acapola.opendev.mamasync;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by seb on 6/14/14.
 */
public class WorkDirectory {
    boolean monitorEnabled;
    final File root;
    final File mamasyncFile;
    final String storagePath;
    byte []key;
    FsDirectory cache;
    public WorkDirectory(String storagePath){
        this.storagePath = storagePath;
        mamasyncFile = null;
        root = null;
    }
    public WorkDirectory(String storagePath,File topDir, File newMamasyncFile){
        this.storagePath = storagePath;
        mamasyncFile = null;
        root = null;
    }
    public void setKey(byte []key){
        this.key = key;
    }
    public void forgetKey(){
        Arrays.fill(key, (byte)0);
    }

    /**
     * programatically add a file to the cache (this is normally used by the monitor)
     * @param newFile
     */
    protected void addFile(File newFile) throws IOException {
        if(!FileUtils.isChild(newFile,root)) throw new RuntimeException("Cannot add a file outside of work directory ("+root.getCanonicalPath()+")");

    }
}
