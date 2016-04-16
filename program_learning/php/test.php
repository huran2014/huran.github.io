<?php
	$course_id=$_GET["courseid"];
	$id=MySql_connect("cclab.see.bupt.cn","testpoint","tp20150929");
    MySql_select_db("lab",$id);
    mysql_query("set names latin1");
    $query="SELECT* from experiment where course=".$course_id;
    $query=iconv("UTF-8","latin1//ignore",$query);
    $result=MySql_query($query,$id);
    $datanum=MySql_num_rows($result);
    //print "\ndatanum=".$datanum."\n";
    print "[";
    for ($i=0; $i<$datanum-1; $i++)
    {
      $row = mysql_fetch_array($result,MYSQL_ASSOC);
      if(isset($row['content']))
      	$row['content']=iconv('gbk','UTF-8',$row['content']);	
      else
      	$row['content']="";
      $row['experiment']=iconv('gbk','UTF-8',$row['experiment']);	
      $row['experimentid']=iconv('gbk','UTF-8',$row['experimentid']);	
      print("{");
      print_r("\"content\":\"".$row['content']."\",");
      print_r("\"experiment\":\"".$row['experiment']."\",");
      print_r("\"experimentid\":".$row['experimentid']);
      if($i<$datanum-2) print("},");
      else print("}");
      //print mb_detect_encoding($row['content'], array("ASCII","UTF-8","GB2312","GBK","latin1")); 
  	}
  	print "]";
?>
