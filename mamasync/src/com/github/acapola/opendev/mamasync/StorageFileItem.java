package com.github.acapola.opendev.mamasync;

/**
 * Created by seb on 6/15/14.
 */
public class StorageFileItem extends FsFile{

    protected long dataLength;
    protected byte []hash;
    protected boolean deleted;
    protected FileVersion version;
    protected int fileNameLength;//in bytes, including padding
    protected String encryptedFileName;
    protected int workDirectoryIdLength;//in bytes
    protected String workDirectoryId;

    public long getDataLength() {
        return dataLength;
    }

    public void setDataLength(long dataLength) {
        this.dataLength = dataLength;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public FileVersion getVersion() {
        return version;
    }

    public void setVersion(FileVersion version) {
        this.version = version;
    }

    public int getFileNameLength() {
        return fileNameLength;
    }

    public void setFileNameLength(int fileNameLength) {
        this.fileNameLength = fileNameLength;
    }

    public String getEncryptedFileName() {
        return encryptedFileName;
    }

    public void setEncryptedFileName(String encryptedFileName) {
        this.encryptedFileName = encryptedFileName;
    }

    public int getWorkDirectoryIdLength() {
        return workDirectoryIdLength;
    }

    public void setWorkDirectoryIdLength(int workDirectoryIdLength) {
        this.workDirectoryIdLength = workDirectoryIdLength;
    }

    public String getWorkDirectoryId() {
        return workDirectoryId;
    }

    public void setWorkDirectoryId(String workDirectoryId) {
        this.workDirectoryId = workDirectoryId;
    }

}
