package com.atguigu.demo.excal;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.util.ArrayList;
import java.util.List;

public class TestExcel {
    public static void main(String[] args) {
        //写法一
//        String fileName = "/Users/admin/Desktop/write.xlsx";
//        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(data());

        //写法二
        // 写法2，方法二需要手动关闭流
        String fileName = "/Users/admin/Desktop/write2.xlsx";
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("写入方法二").build();
        excelWriter.write(data(), writeSheet);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }
}
