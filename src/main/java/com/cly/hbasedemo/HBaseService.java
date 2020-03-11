package com.cly.hbasedemo;

import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HBaseService {

    @Resource(name = "hbt")
    private HbaseTemplate hbaseTemplate;

    public void selectByTableName(String tableName){

    }
}
