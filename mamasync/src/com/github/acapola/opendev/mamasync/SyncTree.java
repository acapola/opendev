package com.github.acapola.opendev.mamasync;

import java.util.List;

/**
 * Created by seb on 6/14/14.
 */
public class SyncTree {
    final List<StorageDirectory> storage;
    final List<WorkDirectory> work;
    public SyncTree(){
        storage=null;//TODO
        work=null;//TODO
    }
    public List<StorageDirectory> getStorage(){
        return storage;
    }
    public List<WorkDirectory> getWork(){
        return work;
    }
    public void sync(){

    }
    public void addWorkDirectory(){

    }
    public void addStorageDirectory(){

    }
}
