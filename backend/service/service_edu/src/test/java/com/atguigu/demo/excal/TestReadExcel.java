package com.atguigu.demo.excal;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.atguigu.demo.listen.ExcelListener;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestReadExcel {
    public static void main(String[] args) throws FileNotFoundException {
        // 写法1：
        String fileName = "/Users/admin/Desktop/write.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
        // 写法2：
//        InputStream in = new BufferedInputStream(new FileInputStream("/Users/admin/Desktop/write.xlsx"));
//        ExcelReader excelReader = EasyExcel.read(in, DemoData.class, new ExcelListener()).build();
//        ReadSheet readSheet = EasyExcel.readSheet(0).build();
//        excelReader.read(readSheet);
//        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
//        excelReader.finish();
    }
}
