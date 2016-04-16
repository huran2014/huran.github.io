package com.hbase.test01;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HBaseTest
{
  @SuppressWarnings("null")
public static void main(String[] args)
    throws ZooKeeperConnectionException, IOException
  {
    String tablename = args[0];
    String[] columnfamily = null;
    for (int i = 1; i < args.length; ++i)
      columnfamily[(i - 1)] = args[i];
    createtable(tablename, columnfamily);
  }

  public static void createtable(String tablename, String[] columnfamily)
    throws MasterNotRunningException, ZooKeeperConnectionException, IOException
  {
    @SuppressWarnings({ "deprecation", "resource" })
	HBaseAdmin hb = new HBaseAdmin(getConfiguration());
    if (hb.tableExists(tablename)) {
      System.out.println("table exists!");
    }
    else
    {
      String[] arrayOfString;
      @SuppressWarnings("deprecation")
	HTableDescriptor tableDesc = new HTableDescriptor(tablename);
      int j = (arrayOfString = columnfamily).length; for (int i = 0; i < j; ++i) { String family = arrayOfString[i];
        tableDesc.addFamily(new HColumnDescriptor(family)); }
      hb.createTable(tableDesc);
      System.out.println("create table succeed");
    }
  }

  private static Configuration getConfiguration()
  {
    Configuration conf = HBaseConfiguration.create();
    conf.set("hbase.rootdir", "hdfs://123.57.223.22:9000/hbase");
    conf.set("hbase.zookeeper.quorum", "123.57.223.22");
    return conf;
  }
}