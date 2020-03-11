package com.cly.hbasedemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class HbasedemoApplicationTests {

    static Configuration conf;
    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "106.13.8.224");
    }
    public static void main(String[] args) throws Exception {
        HTable test = new HTable(conf, "test");
        Get get = new Get(new String("row1").getBytes());
        Result result = test.get(get);
        List<Cell> cells = result.listCells();
        for (Cell cell: cells) {
            String format = String.format("%s %s %s %s",
                    new String(CellUtil.cloneFamily(cell)),
                    new String(CellUtil.cloneQualifier(cell)),
                    cell.getTimestamp(),
                    new String(CellUtil.cloneValue(cell)));
            System.out.println(format);
        }
    }

    @Resource(name = "hbt")
    private HbaseTemplate hbaseTemplate;

    @Test
    void TestSelect() {
        List<String> values = hbaseTemplate.get("user", "813782218261011172", (result, rowNum) -> {
            String format = String.format("序号:%s ", rowNum);
            List<Cell> cells = result.listCells();
            ArrayList<String> lists = Lists.newArrayList();
            cells.stream().forEach(cell -> {
                lists.add(new String(CellUtil.cloneQualifier(cell))+ ":" + new String(CellUtil.cloneValue(cell)));
            });
            return lists;
        });

        values.stream().forEach(s -> {
            System.out.println(s);
        });
    }

    @Test
    void TestAddOrUpdate() {
         hbaseTemplate.put("user","2", "info", "age" , "11".getBytes());
         hbaseTemplate.put("user","2", "info", "name" , "cly".getBytes());
    }

    @Test
    void TestDelete() {
        //hbaseTemplate.delete("user", "2", "info");
        hbaseTemplate.delete("user", "2", "info", "age");
    }

}
