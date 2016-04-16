require.config({
            paths: {
                echarts: publicurl+'/echarts/build/dist'
            }
        });
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/line',
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main'));
                var option = {
				    title : {
				        text: 'PASSON访问量:'+d
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['PASSON访问量:'+d]
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    xAxis : [
				        {
				            type : 'category',
				            data : pages,
				            "axisLabel":{  
				            interval: 0  
					        } 
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : [
				        {
				            name:'PASSON访问量:'+d,
				            type:'bar',
				            data:nums,				            
				        }
				    ]
				};
              
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );