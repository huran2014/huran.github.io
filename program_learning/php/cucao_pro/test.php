<?php
	$id=MySql_connect("localhost","root","12345");
	$ok=MySql_select_db("mystudy",$id);
	mysql_query("set names gb2312");

	$channel='cctc';
	$channel=iconv('UTF-8','GB2312//IGNORE', $channel);
	$channel="'".$channel."'";
	// $channel=$channel;
	$base[6]='2015年9月14号';
	$base[7]='周五';
	$base[6]=iconv('UTF-8','GB2312//IGNORE', $base[6]);
	$base[7]=iconv('UTF-8','GB2312//IGNORE', $base[7]);
	$date="'".$base[6]."'";
	$day="'".$base[7]."'";
	// $date=$base[6];
	// $day=$base[7];
	$program['time']='01:00';
	$program['name']='愁死人了';
	$program['time']=iconv('UTF-8','GB2312//IGNORE', $program['time']);
	$program['name']=iconv('UTF-8','GB2312//IGNORE', $program['name']);
	if(isset($program['content']))	  
		$program['content']=iconv('UTF-8','GB2312//IGNORE', $program['content']);
	else
		$program['content']=iconv('UTF-8','GB2312//IGNORE','');
	if(isset($program['abs']))	
		$program['abs']=iconv('UTF-8','GB2312//IGNORE', $program['abs']);		   
	else
		$program['abs']=iconv('UTF-8','GB2312//IGNORE','');
	$program['duration']='3600';
	$program['host']='苦逼狗';
	$program['duration']=iconv('UTF-8','GB2312//IGNORE', $program['duration']);
	$program['host']=iconv('UTF-8','GB2312//IGNORE', $program['host']);
	if(isset($program['type']))	   
		$program['type']=iconv('UTF-8','GB2312//IGNORE', $program['type']);
	else
		$program['type']=iconv('UTF-8','GB2312//IGNORE','others');	  
	   

	$Gnum=60;
	$t="'".$program['time']."'";
	$n="'".$program['name']."'";
	$c="'".$program['content']."'";
	$duration="'".$program['duration']."'";
	$abs="'".$program['abs']."'";
	$host="'".$program['host']."'";
	$type="'".$program['type']."'";

	$Gnum1=iconv('UTF-8','GB2312//IGNORE','频道号');	
	$channel1=iconv('UTF-8','GB2312//IGNORE','频道名称');	
	$t1=iconv('UTF-8','GB2312//IGNORE','节目时间');	
	$date1=iconv('UTF-8','GB2312//IGNORE','节目日期');	
	$day1=iconv('UTF-8','GB2312//IGNORE','星期');	
	$n1=iconv('UTF-8','GB2312//IGNORE','节目名称');	
	$c1=iconv('UTF-8','GB2312//IGNORE','节目预告');	
	$type1=iconv('UTF-8','GB2312//IGNORE','类型');	
	$host1=iconv('UTF-8','GB2312//IGNORE','主持人');	
	$duration1=iconv('UTF-8','GB2312//IGNORE','播出时长');
	$abs1=iconv('UTF-8','GB2312//IGNORE','其他');

	//$query="INSERT INTO program_new('频道号','频道名称','节目时间','节目日期') VALUES (60,$channel,$t,$day)";
	$query="INSERT INTO `program_new`(`$Gnum1`,`$channel1`,`$t1`,`$date1`,`$day1`,`$n1`,`$c1`,`$type1`,`$host1`,`$duration1`,`$abs1`) VALUES ($Gnum,$channel,$t,$date,$day,$n,$c,$type,$host,$duration,$abs)";
	mysql_query($query);
	print mysql_error();
	print "database ok!\n";
	mysql_close();
?>
