package com.inthemoon.trycamerawrite;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dims on 04.02.2017.
 */

public class RootsUtil {

    private final static String seed = Environment.DIRECTORY_PICTURES;

    public final static File[] getRoots(Context context) {

        File[] paths = context.getExternalFilesDirs(seed);

        if( paths.length <= 1 ) {
            return new File[] { Environment.getExternalStorageDirectory() };
        }
        else {
            while(true) {
                int count = 1;

                for (int i = 1; i < paths.length; ++i) {
                    if (paths[0].getName().equals(paths[i].getName())) {
                        count++;
                    }
                }
                if( count==paths.length ) {
                    for (int i = 0; i < paths.length; ++i) {
                        paths[i] = paths[i].getParentFile();
                    }
                }
                else {
                    break;
                }
            }
            return paths;
        }
    }

    public final static File[] getRemovableRoots(Context context) {
        File external = Environment.getExternalStorageDirectory();
        File[] roots = getRoots(context);
        ArrayList<File> ans = new ArrayList<>();
        for(int i=0; i<roots.length; ++i) {
            if( !roots[i].equals(external) ) {
                ans.add(roots[i]);
            }
        }
        return ans.toArray(new File[]{});
    }
}
