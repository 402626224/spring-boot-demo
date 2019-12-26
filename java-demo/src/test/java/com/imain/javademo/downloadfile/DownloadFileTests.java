package com.imain.javademo.downloadfile;

import org.junit.Test;

/**
 * author Songrui.Liu
 * date 2019/12/26 13:42
 */
public class DownloadFileTests {

    @Test
    public void downloadFile() throws Exception {
        new DownLoadManager().doDownload();
    }


}
