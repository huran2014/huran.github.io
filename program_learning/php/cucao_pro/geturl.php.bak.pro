<?php
   	include_once "simple_html_dom.php";

/* ÏÔÊ¾µ±Ç°µÄÄÚ²¿×Ö·û±àÂë*/
   //echo mb_internal_encoding();
	function dealTime($time)
	{
		$minite=0;
		$minite=$minite*10+($time[0]-'0');
		$minite=$minite*10+($time[1]-'0');
		$second=0;
		$second=$second*10+($time[3]-'0');
		$second=$second*10+($time[4]-'0');
		return $minite*60+$second;
	}
   
   	function getDurl($url)
   	{
        $html0 = file_get_html($url);
		$cnt=0;
		$allcnt=0;
	    while ($html0 == false)
	    {
	      	if ($cnt == 5)
		  	{
		     	$allcnt++;
			 	if ($allcnt == 2)
			    	return false;
		     	sleep(1);
			 	$cnt=0;
		  	}
		  	$html0 = file_get_html($url);
		  	$cnt++;
	    }
		
	    $urlqueue=array();
	    foreach($html0->find('div[class=listmenu]') as $e)
	    {
	        $length=count($urlqueue);
	        $urlqueue[$length][0][0]='http://epg.tvsou.com'.$e->find('a', 0)->href;
		$urlqueue[$length][0][1]=$e->find('a', 0)->plaintext;
		#$true_name = $html0->find('div[class=listmenu2]');
		#$urlqueue[$length][0][1]=$true_name->find('font')->plaintext;
	    }
		$Emptyurl=0;
	    for ($i=0; $i < count($urlqueue); $i++)
	    {
	       	$html01 = file_get_html($urlqueue[$i][0][0]);
		   	if ($html01 == false)
		   	{
		      	$Emptyurl++;
		      	continue;
		   	}
		   	foreach($html01->find('div[class=listmenu2]') as $e1)
	       	{
		       	foreach($e1->find('a') as $d1)
			   	{
		           	$h='http://epg.tvsou.com'.$d1->href;
			       	$con=$d1->plaintext;
				   	$length=count($urlqueue[$i]);
				   	$urlqueue[$i-$Emptyurl][$length][0]=$h;
				   	$urlqueue[$i-$Emptyurl][$length][1]=$con;
			   	}
	       	}
	    }
		return $urlqueue;
   	}
   
   	function getSurl($url)
   	{
       
       	$html0 = file_get_html($url);
	   	$cnt=0;
	   	$allcnt=0;
	    while ($html0 == false)
	    {
	      	if ($cnt == 5)
		  	{
		     	$allcnt++;
			 	if ($allcnt == 2)
			    	return false;
		     	sleep(1);
			 	$cnt=0;
		  	}
		  	$html0 = file_get_html($url);
		  	$cnt++;
	    }
#	print "html0=";
#	print $html0;
#	print "\n";
#	print "ok!\n";

       	$sheng=array();
       	foreach($html0->find('div[class="clear pd10 mb10 bd_gray bg_deep_gray"]') as $e)
	   	{
			print "ok!\n";
		   	$i=0;
#		   	$neirong=$e->find('div[class="lt ml10 mr20 lh20"]',2);
#		   	foreach($neirong->find('a') as $d)
		   	foreach($e->find('a') as $d)
		   	{
				// print "i="+$i+"\n";
		        $i++;
		        if ($i <= 34)
			    {
		           $sheng[$i-1][0]=$d->href;
				if ($i == 32)
				{
				    $sheng[$i-1][0]="http://www.tvsou.com/program/TV_33/Channel_55/W2.htm";
				}
			       $sheng[$i-1][1]=$d->plaintext;
				   //echo $sheng[$i-1][0];
				   //echo "<br>";
			    }
				else
				   break;
				   
		    }   
			break;
	    }
#      	print "sheng=";
#       	print_r($sheng);
#       	print "\n";   
        return $sheng;
    }
   
////////////////////////////////////////////////////////////////////////////////////////////////////
    function getContent($url,$channel,$Gnum)
	{
	    //$url='http://epg.tvsou.com/programys/TV_1/Channel_1/W2.htm';
   		// //检查url是否有效
   		// if (!fopen($url, "r")) continue;
	    $html = file_get_html($url);
	    $cnt=0;
	    $allcnt=0;
        while ($html == false)
        {
        	if ($cnt == 5)
	    	{
	        $allcnt++;
		    if ($allcnt == 2)
		    	return false;
	        sleep(1);
		    $cnt=0;
	    }
	    $html = file_get_html($url);
	    $cnt++;
        }
	   
	 
	    #$str=$html->find('div[id=epg_m3]',0)->plaintext;
	    $str=$html->find('dd[class="rt"]',0)->plaintext;
	    $base=explode(" ",$str);
	    $base[6]=preg_replace("/(\s|\&nbsp\;|¡¡|\xc2\xa0)/","",$base[6]);
	    print_r($base[6]);
	    print "\n";
	    print_r($base[7]);
	    print "\n";
       	   #echo "<br><br>";
	   
	   
	    $p_num=0;
	    $program=array();
	    $aas=array();
	    $last_time=null;
	    #foreach($html->find('div[id=PMT1], div[id=PMT2]') as $e)
	    foreach($html->find('div[class="tvgenre clear"]') as $e)//¿¿¿¿¿¿
	    {
		  	foreach($e->find('li') as $c)//节目列表
            {
			  	$program[$p_num]['duration']=null;
			  	$program[$p_num]['time']=null;
			  	$program[$p_num]['name']=null;
			  	$program[$p_num]['content']=null;
			  	$program[$p_num]['url']=null;
			  	$program[$p_num]['infourl']=null;
			  	$program[$p_num]['type']=null;
			  	$program[$p_num]['host']=null;
			  	$program[$p_num]['actor']=null;
			  	$program[$p_num]['abs']=null;
			  	$program[$p_num]['flag']=0;
		  		//¿¿¿¿¿¿¿¿¿¿¿¿
		      	if($c->find('span',0) != null)              //节目时间
                {
			  		$c_time = $c->find('span',0);
                    $program[$p_num]['time']=$c_time->plaintext;
                    if ($p_num > 0)
			      		$program[$p_num-1]['duration']=dealTime($program[$p_num]['time'])-dealTime($program[$p_num-1]['time']);
                    #$program[$p_num-1]['duration']=dealTime($program[$p_num]['time'])-dealTime($last_time);
                    $last_time=$program[$p_num]['time'];
                    //print "节目持续时间：";
                    //print $program[$p_num-1]['duration'];
			  		//print "\n";
                }
                #$program[$p_num]['time']=$c->find('span')->plaintext;//¿¿¿¿¿¿		
                if ($c->find('a', 0) != null)//节目具体内容
              	{
                  	if(isset($c->find('a', 0)->href))//¿¿¿¿¿¿url¿¿
                  	{
                      	$program[$p_num]['infourl']=$c->find('a', 0)->href;//节目链接
      					$program[$p_num]['name']=$c->find('a', 0)->plaintext;//节目名称
						// if ($c->find('a[class=“red”]',0))
						// {
						// 	if ($program[$count]['infourl'] != null)
						// 	{
						// 		$temp=iconv("gbk", "utf-8", "类别：剧情");
						// 		$program[$count]['type']=$temp;
						// 	}
						// 	//$program[$count]['infourl']=$c->find('a[class=font12-orange]',0)->href;
						// 	$program[$count]['flag']=1;
						// }
					  	//print "p_num=";
						//print $p_num;
      					//print $program[$p_num]['name'];

                  	}
              	}
              	//如果没有爬取到节目名称和链接，尝试看看是否本身这个是没有链接的节目
              	if ($program[$p_num]['infourl'] == null && $program[$p_num]['name'] == null )
              	{
              		if(isset($c->plaintext) && isset($c->find('span',0)->plaintext))
              		{
      					$li_text=$c->plaintext;//节目名称
      					$p_name=explode(" ",$li_text);
      					$program[$p_num]['name'] = $p_name[1];
					  	//print "p_num=";
						//print $p_num;
      					// print $program[$p_num]['name'];
              		}
              	}
				if ($c->find('div[class=border1px]',0) != null)//爬取节目详细内容
				{
					$program[$p_num]['content']=$c->find('div[class=border1px]',0)->plaintext;
					$program[$p_num]['abs']='program';
				}
              	//如果节目并没有剧情概述，就把节目链接赋给节目url   		
			  	for ($i=0; $i < count($program); $i++)
			  	{
				  	if ($program[$i]['content'] == null)
					 	$program[$i]['url']=$program[$i]['infourl'];
			  	}	
			  	//检查是否爬取到节目名称和链接，没有的话说明爬取出错，跳过这个节目
			  	if ($program[$p_num]['name'] != null )
			  		$p_num++;
			  	#print "\nok!\n";
			}
	    }
        ///////////////////////////////////////循环计算节目时长//////////////////////////////////////////////
        //因为这里是只爬取了一天的电视节目，所以不用循环计算时间
		#$program[$p_num-1]['duration']=dealTime($program[0]['time'])+24*60-dealTime($last_time);

		//深入挖掘电视节目信息		   
	   	for ($i =0; $i < count($program); $i++)
	   	{
	   		// print $program[$i]['name'];
	   		// print "\n";
	   		print "$i: ";
	   		//检查url是否有效
	   		$headers=@get_headers($program[$i]['url']);//抓取HTTP Header information 
			if(strpos($headers[0],'404')===true)//检查看看有没有404啊 
	   			continue;
	   		//如果节目没有剧情概要并且有节目链接的话，就进入页面爬取进一步的信息
		  	if ($program[$i]['content'] == null && isset($program[$i]['url']))
		  	{
		   		print $program[$i]['name'];
		   		// print "\n";
				print $program[$i]['url'];
				print "\n";
			 	$html2 = file_get_html($program[$i]['url']);//爬取该节目的网页
			 	if ($html2 == null) 
			 	{
			 		continue;
			 	}
				if(isset($html2->find('p[class="mt20 clear mb15 lh20"]',0)->plaintext))//爬取节目内容信息
				{
					$b= $html2->find('p[class="mt20 clear mb15 lh20"]',0);
					$program[$i]['content']= $b->plaintext;//$b->plaintext;
					// print $program[$i]['content'];
					// print "\n";
					//$program[$i]['content']= preg_replace("/(\s|\&nbsp\;|¡¡|\xc2\xa0)/","",$b->plaintext);//$b->plaintext;
				}
				foreach($html2->find('div[class="ml10 dsj_right rt mb15"]') as $c)//爬取栏目信息
				{
					preg_match_all("/(?:\()(.*)(?:\))/i",$c->find('span',0)->plaintext, $p_type); //爬取节目类型信息
					// print "节目信息：";
					// print_r($p_type[1]);
					// print "\n";
					$program[$i]['type']=$p_type[1][0];
					print $program[$i]['type'];
					print "\n";
					//利用正则表达式判断这是社会频道还是影视频道（根据是否有主持人来判断）
					if(preg_match("/主持人：(.*)/",$c->find('p',0)->plaintext))
						$program[$i]['flag'] = 0;//这是社会频道
					else
						$program[$i]['flag'] = 1;//这是影视频道

					// print $program[$i]['flag'];
					// print "\n";
			 		////////////////////////////////获取正常链接下的信息//////////////////////////////////////////////////////////
					if($program[$i]['flag'] == 0)
					{
						//利用正则表达式匹配获取主持人信息
						$p_info = $c->find('p',0)->plaintext;
						preg_match_all("/主持人：(.*)/",$p_info, $p_host);
						$program[$i]['host']=$p_host[1][0];//爬取主持人信息
						preg_match_all("/类别：(.*)/",$p_info, $p_abs);
						if(!preg_match("/类别：(.*)/",$c->find('p',0)->plaintext))
						{
							print "没有爬到类别名称\n";
							$p_abs1 = "类别:".$program[$i]['type'];
							//$temp=iconv("gbk", "utf-8", $p_abs1);
							$program[$i]['abs']=$p_abs1;
						}
						else
							$program[$i]['abs']=$p_abs[0][0];
						// print_r($p_abs); 
						print $program[$i]['host'];
						print "\n";
						print $program[$i]['abs'];
						print "\n";
					}
					////////////////////////////////获取剧情链接下的信息//////////////////////////////////////////////////////////
					else
					{
					    $p_info = $c->find('p',0)->plaintext;
						preg_match_all("/导演：(.*)/",$p_info, $p_host);
						$program[$i]['host']=$p_host[1][0];//爬取导演信息
						// print $program[$i]['host'];
						// print "\n";
						preg_match_all("/主演：(.*)/",$p_info, $p_actor);
						$program[$i]['actor']=$p_actor[1][0];//爬取演员信息
						preg_match_all("/类别：(.*)/",$p_info, $p_abs);//节目类别信息，如：类别：剧情
						if(!preg_match("/类别：(.*)/",$c->find('p',0)->plaintext))
						{
							print "没有爬到类别名称\n";
							$p_abs1 = "类别:".$program[$i]['type'];
							//$temp=iconv("gbk", "utf-8", $p_abs1);
							$program[$i]['abs']=$p_abs1;
						}
						else
							$program[$i]['abs']=$p_abs[0][0];
						print $program[$i]['host'];
						print "\n";
						print $program[$i]['abs'];
						print "\n";
					}
				}
			}
	   		//如果节目有剧情概要并且有节目链接的话，就进入页面爬取进一步的信息
			elseif (isset($program[$i]['infourl']))
			// else
			{
		   		print $program[$i]['name'];
		   		// print "\n";
				print $program[$i]['infourl'];
		   		print "\n";
				$html2 = file_get_html($program[$i]['infourl']);//爬取该节目的网页
				foreach($html2->find('div[class="ml10 dsj_right rt mb15"]') as $c)//爬取栏目信息
				{
					preg_match_all("/(?:\()(.*)(?:\))/i",$c->find('span',0)->plaintext, $p_type); //爬取节目类型信息
					$program[$i]['type']=$p_type[1][0];
					print $program[$i]['type'];
					print "\n";

					////////////////////////////////获取剧情链接下的信息//////////////////////////////////////////////////////////
				    $p_info = $c->find('p',0)->plaintext;
					preg_match_all("/导演：(.*)/",$p_info, $p_host);
					$program[$i]['host']=$p_host[1][0];//爬取导演信息
					// print $program[$i]['host'];
					// print "\n";
					preg_match_all("/主演：(.*)/",$p_info, $p_actor);
					$program[$i]['actor']=$p_actor[1][0];//爬取演员信息
					preg_match_all("/类别：(.*)/",$p_info, $p_abs);//节目类别信息，如：类别：剧情
					if(!preg_match("/类别：(.*)/",$c->find('p',0)->plaintext))
					{
						//print "没有爬到类别名称\n";
						$p_abs1 = "类别:".$program[$i]['type'];
						//$temp=iconv("gbk", "utf-8", $p_abs1);
						$program[$i]['abs']=$p_abs1;
					}
					else
						$program[$i]['abs']=$p_abs[0][0];
					print $program[$i]['host'];
					print "\n";
					print $program[$i]['abs'];
					print "\n";
				}
			}
		}
       	   
	   	// for ($i =0; $i < count($program); $i++)
	   	// {
		   	// if ($program[$i]['abs'] == 'program')
		   	// {
			  	// $temp=iconv("gbk", "utf-8", "类别:剧情");
			  	// $program[$i]['abs']=$temp;
			  	// $program[$i]['type']=$temp;
		   	// }
		  //  	if ($program[$i]['abs'] == null)
		  //  	{
				// print $program[$i]['type'];
				// print "\n";
		  //  		$p_abs = "类别:"+$program[$i]['type'];
			 //  	$temp=iconv("gbk", "utf-8", $p_abs);
			 //  	$program[$i]['abs']=$temp;
			 //  	$program[$i]['type']=$temp;
		  //  	}
	   	// }
		
	 //    echo "<table border=\"1\">";
		// echo "<tr><th>Cnum</th><th>date</th><th>day</th><th>time</th><th>duration</th><th>name</th><th>content</th>
		//       <th>type</th><th>host</th><th>actor</th><th>abstract</th><tr>";
	   	// for ($i =0; $i < count($program); $i++)
	   	// {
	   		// print $program[$i]['name'];
	   		// print $program[$i]['host'];
	   		// print $program[$i]['type'];
	   		// print $program[$i]['abs'];
	   		// print $program[$i]['actor'];
		//  	$date=$base[6];
		// 	$day=$base[7];
		// 	$t=$program[$i]['time'];
		// 	$n=$program[$i]['name'];
		// 	$c=$program[$i]['content'];
		// 	$duration=$program[$i]['duration'];
		// 	$abs=$program[$i]['abs'];
		// 	$p_host=$program[$i]['host'];
		// 	$type=$program[$i]['type'];
		// 	$actor=$program[$i]['actor'];
		// 	echo "<tr>";
		// 	echo "<td>$Gnum</td>";
		// 	echo "<td>$date</td>";
		// 	echo "<td>$day</td>";
		// 	echo "<td>$t</td>";
		// 	echo "<td>$duration</td>";
		// 	echo "<td>$n</td>";
		// 	echo "<td>$c</td>";
		// 	echo "<td>$type</td>";
		// 	echo "<td>$p_host</td>";
		// 	echo "<td>$actor</td>";
		// 	echo "<td>$abs</td>";
		// 	echo  "</tr>";
		// }
		// echo "</table>";
		
		   //
		   
//////////////////////////////////////////////////////////////////////////////////////////////////////////		   
		$id=MySql_connect("localhost","root","12345");
		$ok=MySql_select_db("mystudy",$id);		  
		mysql_query("set names gb2312");

		$channel=iconv('UTF-8','GB2312//IGNORE', $channel);
		$channel="'".$channel."'";
		$base[6]=iconv('UTF-8','GB2312//IGNORE', $base[6]);
		$base[7]=iconv('UTF-8','GB2312//IGNORE', $base[7]);
		$date="'".$base[6]."'";
		$day="'".$base[7]."'";
		for ($i=0; $i < count($program); $i++)
		{
			$program[$i]['time']=iconv('UTF-8','GB2312//IGNORE', $program[$i]['time']);
			$program[$i]['name']=iconv('UTF-8','GB2312//IGNORE', $program[$i]['name']);
			if(isset($program[$i]['content']))	  
				$program[$i]['content']=iconv('UTF-8','GB2312//IGNORE', $program[$i]['content']);
			else
				$program[$i]['content']=iconv('UTF-8','GB2312//IGNORE','');
			if(isset($program[$i]['abs']))	
				$program[$i]['abs']=iconv('UTF-8','GB2312//IGNORE', $program[$i]['abs']);		   
			else
				$program[$i]['abs']=iconv('UTF-8','GB2312//IGNORE','');
			$program[$i]['duration']=iconv('UTF-8','GB2312//IGNORE', $program[$i]['duration']);
			$program[$i]['host']=iconv('UTF-8','GB2312//IGNORE', $program[$i]['host']);
			if(isset($program[$i]['type']))	   
				$program[$i]['type']=iconv('UTF-8','GB2312//IGNORE', $program[$i]['type']);
			else
				$program[$i]['type']=iconv('UTF-8','GB2312//IGNORE','others');	  
			$program[$i]['actor']=iconv('UTF-8','GB2312//IGNORE', $program[$i]['actor']);
			   
			   
		   
		       
			$t="'".$program[$i]['time']."'";
			$n="'".$program[$i]['name']."'";
			$c="'".$program[$i]['content']."'";
			$duration=$program[$i]['duration'];
			$abs="'".$program[$i]['abs']."'";
			$host="'".$program[$i]['host']."'";
			$type="'".$program[$i]['type']."'";
			$actor="'".$program[$i]['actor']."'";

			$query="INSERT INTO `program_pro`(`num`,`Date`,`Day`,`duration`,`channel`, `time`, `name`, 
			       `content`, `type`,`host`,`actor`,`others`) VALUES
				  ($Gnum,$date,$day,$duration,$channel,$t,$n,$c,$type,$host,$actor,$abs)";
			mysql_query($query);
		}
		print "database ok!\n";
		mysql_close(); 	   
	   
	}

////////////////////////////////////////////////////////////////////////////////////////////////////

  
?>
