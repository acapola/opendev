package com.github.acapola.opendev.mamasync;

import java.io.File;
import java.io.IOException;

/**
 * Created by seb on 6/16/14.
 */
public class FileUtils {
    static public boolean isChild(File f,File potentialParentDir) throws IOException {
        String fc = f.getCanonicalPath();
        String pc = potentialParentDir.getCanonicalPath();
        return fc.startsWith(pc);
    }
}
