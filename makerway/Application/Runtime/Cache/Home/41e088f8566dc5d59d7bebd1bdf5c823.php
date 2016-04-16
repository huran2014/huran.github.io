<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
<head>
<title>PASSON统计图查看</title>
<!-- <script type="text/javascript" src="http://libs.useso.com/js/jquery/1.7.2/jquery.min.js"></script> -->

</head>
<style type="text/css">
	.visit{
		text-align: center;
	}
	.visit table,td,th{
		margin:10px auto;
		border: 1px solid black;
		border-collapse:collapse;
	}

</style>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	<div><a href="<?php echo U(share);?>">返回共享库</a></div>
    <div id="main" style="height:500px;"></div>
    <div>
	    <div class="visit">
	    	<table cellpadding="10" class="visitTable">
	    	<caption>用户浏览记录</caption>
	    		<tr>
	    			<th>用户</th>
	    			<th>网页</th>
	    			<th>日期</th>
	    			<th>时间</th>
	    			<th>IP</th>
	    		</tr>
	    		<?php foreach ($visitLog as $key => $value): ?>
	    			<tr>
	    				<td><?php echo ($value['username']); ?></td>
	    				<td><?php echo ($value['pagename']); ?></td>
	    				<td><?php echo ($value['date']); ?></td>
	    				<td><?php echo ($value['time']); ?></td>
	    				<td><?php echo ($value['ip']); ?></td>
		    		</tr>
	    		<?php endforeach ?>
	    	</table>
	    </div>
    </div>
    <!-- ECharts单文件引入 -->
<script type="text/javascript" src="/passon/Public/echarts/build/dist/echarts.js"></script>
<script type="text/javascript">
	var d='<?php echo ($date); ?>';
	var pages=<?php echo ($pages); ?>;
    var nums=<?php echo ($pagesnum); ?>;
    var publicurl='/passon/Public';
</script>
<script type="text/javascript" src="/passon/Public/js/stastic.todayPages.js"></script>
 
</body>
</html>