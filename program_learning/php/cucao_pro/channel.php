<?php
  include_once "geturl.php";

  header("content-Type:text/html;charset=utf-8");  
  set_time_limit(43200);
  $result_start=date('Y-m-d H:i:s',time());
   //getUrl($url0);
  $id=MySql_connect("localhost","root","12345");
	$ok=MySql_select_db("mystudy",$id);		  
//	mysql_query("set names gb2312");
//	$query="DELETE from `program_pro` where 1";
//  echo $query;
	mysql_query("truncate table program_new");
  mysql_close(); 
  /////////////////////////////////////////////////////////////////////////////////////////////////////
  $url="http://www.tvsou.com/";
  $S=getSurl($url);
  if ($S == false)
  {
    printf("cannot link...\n");
    exit();
  }
  for ($i=0; $i < count($S); $i++)
  {
    $urlqueue=getDurl($S[$i][0]);
    if ($urlqueue == false)
    {
      printf("cannot link...\n");
      exit();
    }
    for ($j=0; $j < count($urlqueue); $j++)/*count($urlqueue)*/
    {
      for ($k=0; $k < count($urlqueue[$j]);$k++)/*count($urlqueue[$j])*/
      {
        preg_match('/Channel_(\d)*/',$urlqueue[$j][$k][0],$Cnum);
        $Gnum=preg_replace("/[a-zA-Z]|_/","",$Cnum[0]);
        //printf("channelNum is %d\n",$Gnum);
        //printf($urlqueue[$j][$k][0]);
        //printf($urlqueue[$j][$k][1]);
        //echo '</br>';
        print("该频道所在的地区是:".$S[$i][1]."\n");
        getContent($urlqueue[$j][$k][0],$urlqueue[$j][$k][1],$Gnum,$S[$i][1]);
      }
    }
       //print_r($urlqueue);  
  }
  $result_info=date('Y-m-d H:i:s',time());
  $filename=date('Y_m_d',time());
  $handle=fopen("logfile/result".$filename.".log","w");
  if (!fwrite($handle,"Start Time:".$result_start."End Time:".$result_info))
  {
    print "cannot write into log!\n";
    exit;
  }
  fclose($handle);
  printf("complete!\n");
  /////////////////////////////////////////////////////////////////////////////////////////////////////
  //print_r($urlqueue);
  /*

  */
?>
