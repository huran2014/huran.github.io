<?php
   set_time_limit(5400);
   $id=MySql_connect("localhost","root","12345");
   MySql_select_db("mystudy",$id);
   mysql_query("set names utf8");
   MySql_query("truncate table channel_info");
   $query="select * from program_new where 1";
   $result=MySql_query($query,$id);
   $ChannelNum=0;
   while($row=mysql_fetch_array($result))
   {
	   	if ($row[0] != $ChannelNum && $row[0] < 1000 && $row[0]!=246 && $row[0]!=737 && $row[0]!=16 )
	   	{
			$query="INSERT INTO `channel_info`(`ChannelNum`, `ChannelName`, `AreaName`) VALUES ('$row[0]','$row[1]','$row[8]')";
	        //$query="INSERT INTO program_infomation (`频道号`,`频道名称`,`节目时间`,`节目日期`,`星期`,`节目名称`,`节目预告`,`类型`,`国家地区`,`主持人`,`播出时间`,`重播时间`,`播出频道`,`首播频道`,`播出时长`,`单集片长`,`语言`,`集数`,`其他`) values ($row[0],'$row[1]','$row[2]','$row[3]','$row[4]','$row[5]','$row[6]','$row[7]','$row[8]','$row[9]','$row[10]','$row[11]','$row[12]','$row[13]','$row[14]','$row[15]','$row[16]','$row[17]','$row[18]')";
	        mysql_query($query);
	   		$ChannelNum=$row[0];
	   	}
   }
   print "complete\r\n";
   mysql_close();
?>