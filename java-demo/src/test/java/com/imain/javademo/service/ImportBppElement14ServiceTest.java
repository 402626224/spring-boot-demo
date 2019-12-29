package com.imain.javademo.service;

import org.junit.Test;

import javax.xml.bind.Element;

import static org.junit.Assert.*;

public class ImportBppElement14ServiceTest {

    @Test
    public void importData() throws Exception {

        new ImportBppElement14Service().importData();
        Thread.sleep(20000);

    }
}