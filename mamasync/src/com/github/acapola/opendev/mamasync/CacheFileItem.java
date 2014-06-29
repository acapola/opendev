package com.github.acapola.opendev.mamasync;

/**
 * Created by seb on 6/15/14.
 */
public class CacheFileItem extends FsFile{
    protected byte []hash;
    protected FileVersion version;
    protected String fileId;
    protected long timestamp;
    protected boolean toDelete;
}
