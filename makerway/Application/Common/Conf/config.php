<?php
return array(
	//'配置项'=>'配置值'
	'DB_TYPE'               =>  'MySql',     // 数据库类型
    'DB_HOST'               =>  'localhost', // 服务器地址
    'DB_NAME'               =>  'test',          // 数据库名
    'DB_USER'               =>  'root',      // 用户名
    'DB_PWD'                =>  '35cdb02f64',          // 密码
    'DB_PORT'               =>  '3306',        // 端口
    'DB_PREFIX'             =>  'pw_',    // 数据库表前缀
    'COOK_PRIFIX'			=>	'passon_',

	//URL_MODEL
	'URL_MODEL'=>2,

    /* 模板相关配置 (路径)*/
    'TMPL_PARSE_STRING' => array(
        /*
         * 示例
         * '__MEEZAOWX__' => __ROOT__.'/Public/MeeZao/weixin', //静态蜜枣微信样式文件
         */
        '__Public__' => __ROOT__.'/Public', //Public文件夹路径
        '__Ext__' => __ROOT__.'/Ext' //Ext文件夹路径
    ),
	
);
