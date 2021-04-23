package org.jzz.study.io;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 监控文件系统变化 */
public class FileListener extends FileAlterationListenerAdaptor{
	
	Logger logger = LoggerFactory.getLogger(FileListener.class);
	
	
    @Override
    public void onStart(final FileAlterationObserver observer) {
        super.onStart(observer);
//        logger.info("onStart...");
    }

    @Override
    public void onDirectoryCreate(final File directory) {
        super.onDirectoryCreate(directory);
        logger.info("onDirectoryCreate:" + directory.getAbsolutePath());
    }
    
    @Override
    public void onDirectoryChange(final File directory) {
    	super.onDirectoryChange(directory);
        logger.info("onDirectoryChange:" + directory.getAbsolutePath());
    }
   
    @Override
    public void onDirectoryDelete(final File directory) {
    	super.onDirectoryDelete(directory);
        logger.info("onDirectoryDelete:" + directory.getAbsolutePath());
    }
    
    @Override
    public void onFileCreate(final File file) {
    	super.onFileCreate(file);
        logger.info("onFileCreate:" + file.getAbsolutePath());
    }
   
    @Override
    public void onFileChange(final File file) {
    	super.onFileChange(file);
        logger.info("onFileChange:" + file.getAbsolutePath());
    }
  
    @Override
    public void onFileDelete(final File file) {
    	super.onFileDelete(file);
        logger.info("onFileDelete:" + file.getAbsolutePath());
    }

    @Override
    public void onStop(final FileAlterationObserver observer) {
        super.onStop(observer);
//        logger.info("onStop...");
    }
    
    public static void main(String[] args) {
		long interval = 1000;
		final String path = "/opt/EXIM132";
		FileAlterationObserver observer;
		
		try {
			observer = new FileAlterationObserver(path, null, null);
			observer.addListener(new FileListener());
			FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
			monitor.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
