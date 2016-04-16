<?php
/**
 * Author: helen
 * CreateTime: 2016/3/24 19:41
 * description:
 */
    namespace Home\Controller;
    use Think\Controller;
    header('Content-Type:text/html; charset=utf-8');
    class BookController extends Controller{
        /*
         * 需要迁移的东西：数据表、方法、页面
         * */
        //微信验证(配置页面)
        public function Index(){
            //获取微信发送确认的参数。
            $signature = $_GET['signature'];
            $timestamp = $_GET['timestamp'];
            $nonce = $_GET['nonce'];
            $echostr = $_GET['echostr'];
            $token = 'weixin';
            $array = array($token,$timestamp,$nonce);
            sort($array);
            $str = sha1( implode($array) );
            if( $str==$signature && $echostr ){
                echo $echostr;
                exit;
            }else{
                $this->getWeixinMsg();
            }
        }
        //微信回复消息
        public function getWeixinMsg(){
            //1,获取到微信推送过来post数据（xml格式）
            $postArr = $GLOBALS['HTTP_RAW_POST_DATA'];
            //2,处理消息类型，并设置回复类型和内容
            $postObj = simplexml_load_string($postArr);
            //3、事件判断(根据微信推送的MsgType和Event进行事件的判断)
            $MsgType = strtolower($postObj->MsgType);
            $Event = strtolower($postObj->Event);
            //4、事件处理（基础事件处理和微信扫码处理）
            //获取接口调用凭据
            $access_token = $this->getWeixinToken();
            //根据用户openid判断用户信息是否已经存入数据库中
            $openid = $postObj->FromUserName;
            //此处进行用户检测
            $user_info = $this->searchWeixinUserInfo($openid);
            if(!$user_info){  //未有用户信息，则直接进行存储,根据用户的信息，拉取用户信息存入数据库中。
                $user_info_data = $this->getUserInfo($access_token,$openid);
                $this->saveWeixinUserInfo($openid,$user_info_data);
                $user_info['nickname'] = $user_info_data->nickname;
            }
            //扫码推送事件 (扫码推事件且弹出“消息接收中”提示框的事件推送 )
            if($MsgType=='event'&&$Event=='scancode_waitmsg'){
                //条形码、二维码扫描事件
                if($postObj->ScanCodeInfo->ScanType=='barcode'){
                    $result = $postObj->ScanCodeInfo->ScanResult;
                    $pos = strpos($result,',');
                    $ISBN = substr($result,$pos+1);
                    $BookInfo = $this->searchBookInfo($ISBN);
                    $action = $postObj->EventKey;
                    //存储图书信息
                    $this->saveBookInfo($openid,$ISBN,$BookInfo);
                    //存储web端图书本书信息
                    $this->saveWebBookMsg($ISBN,$BookInfo);
                    //$this->saveBookList($ISBN,$action);
                    //存储用户信息
                    //$this->saveBookUser($ISBN,$openid,$action);
                    //根据推送事件的类型，进行进一步处理
                    if($action=='share'){
                        $Content = "请点击书本链接，进行分享！\n<a href='http://www.makerway.space/makerway/Home/Book/share.html?ISBN=$ISBN&openid=$openid'>《".$BookInfo['title']."》</a>"."\n欢迎您，".$user_info['nickname']."。感谢您使用makerway，分享智慧！";
                    }elseif($action=='borrow'){
                        $Content = "请点击书本链接，进行借阅！\n<a href='http://www.makerway.space/makerway/Home/Book/borrow.html?ISBN=$ISBN&openid=$openid'>《".$BookInfo['title']."》</a>"."\n欢迎您，".$user_info['nickname']."。感谢您使用makerway，分享智慧！";
                    }else{
                        $Content = "请点击书本链接，进行归还！\n<a href='http://www.makerway.space/makerway/Home/Book/back.html?ISBN=$ISBN&openid=$openid'>《".$BookInfo['title']."》</a>"."\n欢迎您，".$user_info['nickname']."。感谢您使用makerway，分享智慧！";
                    }
                    $this->responseText($postObj,$Content);
                }elseif($postObj->ScanCodeInfo->ScanType=='qrcode'){
                    $redirect_url = $postObj->ScanCodeInfo->ScanResult;
                    $Content = "<a href='$redirect_url'>欢迎您,".$user_info['nickname']." 请点击此链接，进行进一步操作</a>";
                    $this->responseText($postObj,$Content);
                }
            }
            //点击菜单拉取消息时的事件推送
            if($MsgType=='event'&&$Event=='click'){
                //智慧分享(后续此处为图文消息)
                $Content = "欢迎您，<a href='http://www.makerway.space/Home/Book/comment.html'>请点击链接，输入您要书写的内容</a>";
                $this->responseText($postObj,$Content);
            }
            //用户关注事件
            if( strtolower($postObj->MsgType)=='event' && strtolower($postObj->Event)=='subscribe' ){
                $Content = 'MakerWay，感谢您的关注!智能回复系统期待与您对话。';
                $this->responseText($postObj,$Content);
            }
            //普通消息回复
            //用户发送文本消息
            if( strtolower($postObj->MsgType)=='text' && strtolower($postObj->Content)=='历史消息' ){
                $array = array(
                    array(
                        'title'=>'hao123',
                        'description'=>"hao123 is very cool",
                        'picUrl'=>'https://www.baidu.com/img/bdlogo.png',
                        'url'=>'http://www.hao123.com',
                    ),
                    //多图文直接添加即可！
                );
                $this->responseNews($postObj,$array);
            }
            if( strtolower($postObj->MsgType)=='text' && strtolower($postObj->Content)!='历史消息' )
            {
                $Content = '您输入的是：'.$postObj->Content;
                $this->responseText($postObj,$Content);
            }
            //用户发送位置信息
            if( strtolower($postObj->MsgType)=='location' ){
                $Content = '您现在所处的位置为：'.$postObj->Label.'.经度为：'.$postObj->Location_Y.'.纬度为：'.$postObj->Location_X;
                $this->responseText($postObj,$Content);
            }
        }

        //makerway图书公众号展示页
        //图书分享
        public function share(){
            $ISBN = $_GET['ISBN'];
            $openid = $_GET['openid'];
            $this->assign('isbn',$ISBN);
            $this->assign('openid',$openid);
            //首先获取数据库中的书本信息以及人员信息;
            $BookInfo = $this->getBookInfo($ISBN);
            $UserInfo = $this->getWeixinUserInfo($openid);
            //根据用户信息中是否存在web_id字段进行判断
            $web_id = $UserInfo['web_id'];
            if(empty($web_id)){
                //web_id不存在，证明至少没进行过绑定
                $this->assign('tip',1); //将此传值到前端，利用js控制页面显示注册页
            }else{
                //已进行绑定
                $this->assign('tip',0); //将此传值到前端，利用js控制页面显示注册页
                //此时根据web_id去pw_book_user_info表查找信息,进行信息确认的展示
                $BookUserInfo = $this->getWebBookUserInfo($web_id);
                $this->assign('BookUserInfo',$BookUserInfo);
            }
            //相关用户信息
            $this->assign('BookInfo',$BookInfo);
            $this->assign('UserInfo',$UserInfo);
            //分享记录

            //可能感兴趣的书

            $this->assign('tip',1);
            $this->display();
        }
        //分享处理
        public function share_handle(){
            //接受ajax传过来的变量值
            $username = $_POST['username'];
            $phone = $_POST['phone'];
            $addr = $_POST['addr'];
            $isbn = $_POST['isbn'];
            $openid = $_POST['openid'];
            $password = $_POST['password'];
            $type = $_POST['type'];
            //web端用户表
            $webBookUserInfoTable = M('book_user_info');
            //微信端用户表
            $weixinUserTable = M('weixin_user_info');
            //根据type值判断所要处理的事件类型
            switch($type){
                case 'modify':  //修改用户信息
                    //首先根据用户信息表中的web_id信息判断
                    $modify_map['openid'] = $openid;
                    $searchWebBookUser = $weixinUserTable->where($modify_map)->find();
                    $id = $searchWebBookUser['web_id'];
                    $modify_msg['username'] = $username;
                    $modify_msg['phone'] = $phone;
                    $modify_msg['addr'] = $addr;
                    $res = $webBookUserInfoTable->where("book_user_info_id=$id")->save($modify_msg);
                    if($res){
                        $r_data['msg'] = '修改成功！';
                        $r_data['code'] = 1;
                    }else{
                        $r_data['msg'] = '修改失败！';
                        $r_data['code'] = 0;
                    }
                    break;
                case 'share':
                    //更新web端的book表
                    $BookInfo = $this->searchBookInfo($isbn);
                    $res = $this->saveWebBook($openid,$isbn,$BookInfo);
                    if($res){
                        $r_data['msg'] = '分享成功！';
                        $r_data['code'] = 1;
                    }else{
                        $r_data['msg'] = '分享失败！';
                        $r_data['code'] = 1;
                    }
                    break;
                case 'register':
                    //进行信息绑定 web_id 用户记录
                    $register_msg['username'] = $username;
                    $register_msg['phone'] = $phone;
                    $register_msg['addr'] = $addr;
                    $web_id = $webBookUserInfoTable->data($register_msg)->add();
                    //更新微信表中的web_id
                    $map['openid'] = $openid;
                    $res = $weixinUserTable->where($map)->setField('web_id',$web_id);
                    if($res){
                        $r_data['msg'] = '注册成功！';
                        $r_data['code'] = 1;
                    }else{
                        $r_data['msg'] = '注册失败！';
                        $r_data['code'] = 0;
                    }

                    break;
                case 'login':
                    //进行信息绑定 web_id
                    $map['username'] = $username;
                    $map['password'] = md5($password);
                    $searchWebBookUser = $webBookUserInfoTable->where($map)->find();
                    if(empty($searchWebBookUser)){
                        $r_data['msg'] = '请检查您的身份信息是否正确！';
                        $r_data['code'] = 0;
                    }else{
                        //将用户信息绑定到对应的数据表中
                        $web_data['web_id'] = $searchWebBookUser['book_user_info_id'];
                        $new_map['openid'] = $openid;
                        $res = $weixinUserTable->where($new_map)->save($web_data);
                        if($res){
                            $r_data['msg'] = '登陆成功!';
                            $r_data['code'] = 1;
                        }else{
                            $r_data['msg'] = '登陆失败!';
                            $r_data['code'] = 0;
                        }
                    }
                    break;
                default:

                    break;
            }
            //$data['code'] = 1;
            //$data['msg'] = 'success';
            $this->ajaxReturn($r_data,'JSON');
        }
        //网站端用户信息查询
        function getWebBookUserInfo($id){
            $map['book_user_info_id'] = $id;
            $webBookUserInfoTable = M('book_user_info');
            $BookUserInfo = $webBookUserInfoTable->where($map)->find();
            return $BookUserInfo;
        }

        //用户注册
        public function register(){

        }
        //用户信息修改
        public function msgModify(){

        }
        //图书借阅
        public function borrow(){
            $ISBN = $_GET['ISBN'];
            $openid = $_GET['openid'];
            //首先检查数据库中是否保存有书本信息以及人员信息;
            $BookInfo = $this->getBookInfo($ISBN);
            $UserInfo = $this->getWeixinUserInfo($openid);
            $this->assign('BookInfo',$BookInfo);
            $this->assign('UserInfo',$UserInfo);
            $this->display();
        }
        //图书归还
        public function back(){
            $ISBN = $_GET['ISBN'];
            $openid = $_GET['openid'];
            //首先获取数据库中是否保存有书本信息以及人员信息;
            $BookInfo = $this->getBookInfo($ISBN);
            $UserInfo = $this->getWeixinUserInfo($openid);
            $this->assign('BookInfo',$BookInfo);
            $this->assign('UserInfo',$UserInfo);
            $this->display();
        }
        //图书详情页
        public function bookInfo(){
            $ISBN = $_GET['ISBN'];
            $openid = $_GET['openid'];
            $BookInfo = $this->getBookInfo($ISBN);
            $UserInfo = $this->getWeixinUserInfo($openid);
            $this->assign('BookInfo',$BookInfo);
            $this->assign('UserInfo',$UserInfo);
            $this->display();
        }
        //个人借阅详情页
        public function userInfo(){
            //获取个人信息
            $openid = $_GET['openid'];
            $UserInfo = $this->getWeixinUserInfo($openid);
            $this->assign('UserInfo',$UserInfo);
            $this->display();
        }
        //更新个人注册信息
        public function updateUserInfo(){
            $bookUser = M('weixin_book_user');
            $data['name'] = $_POST['name'];
            $data['phone'] = $_POST['phone'];
            $data['location'] = $_POST['location'];

        }

        //更新网站端的pw_book_user_info表（对于新注册用户）
        function saveBookUserInfo(){
            $bookUserInfoTable = M('book_user_info');
            $data['username'] = '';
            $data['flag'] = 1;
            $data['phone'] = '';
            $data['addr'] = '';
            $data['weixin'] = '';
            $data['password'] = '';
            $bookUserInfoTable->data($data)->add();
        }

        //扫码推送事件
        function scancodeWaitmsg($type,$result){
            switch($type){
                case 'barcode':
                    $pos = strpos($result,',');
                    $data['barcode'] = substr($result,$pos+1);
                    break;
                case 'qrcode':
                    $data['url'] = $result;
                    break;
            }
            return $data;
        }

        //weixin_user_info表（微信用户基础信息表）
        function saveWeixinUserInfo($openid,$user_info_data){
            $weixinUserInfo = M('weixin_user_info');
            $data['openid'] = "$openid";
            $data['create_time'] = date('Y-m-d H:i:s',time());
            $data['nickname'] = $user_info_data->nickname;
            $data['sex'] = $user_info_data->sex;
            $data['province'] = $user_info_data->province;
            $data['city'] = $user_info_data->city;
            $data['country'] = $user_info_data->country;
            $data['headimgurl'] = $user_info_data->headimgurl;
            $data['is_subscribe'] = 1;
            $res = $weixinUserInfo->data($data)->add();
            if($res){
                return true;
            }else{
                return false;
            }
        }
        //查询用户信息是否已经存在（对于用户）
        function searchWeixinUserInfo($openid){
            $weixinUserInfo = M('weixin_user_info');
            $map['openid'] = "$openid";
            $search_user = $weixinUserInfo->where($map)->find();
            if($search_user){
                return $search_user;
            }else{
                return false;
            }
        }
        //保存用户图书信息
        function saveBookInfo($openid,$ISBN,$BookInfo){
            $bookInfo = M('weixin_book_info');
            //首先根据图书ISBN进行查找
            $map['isbn'] = "$ISBN";
            $res = $bookInfo->where($map)->find();
            if($res){
                return 'existed';
            }else{
                $data['isbn'] = $ISBN;
                $data['first_openid'] = "$openid";
                $data['title'] = $BookInfo['title'];
                $data['author'] = $BookInfo['author'];
                $data['author_intro'] = $BookInfo['author_intro'];
                $data['summary'] = $BookInfo['summary'];
                $data['catalog'] = $BookInfo['catalog'];
                $data['image'] = $BookInfo['image'];
                $data['ebook_url'] = $BookInfo['ebook_url'];
                $data['title'] = $BookInfo['title'];
                $data['create_time'] = date('Y-m_d H:i:s',time());
                $data['update_time'] = date('Y-m_d H:i:s',time());
                $bookInfo->data($data)->add();
                return 'success';
            }
        }
        //保存web端图书信息
        function saveWebBook($openid,$ISBN,$BookInfo){
            $webBookTable = M('book');
            //微信端查询
            $weixinUserInfoTable = M('weixin_user_info');
            $map['openid'] = $openid;
            $searchWeixinUser = $weixinUserInfoTable->where($map)->find();
            $id = $searchWeixinUser['web_id'];
            //web端查询
            $webBookUserTable = M('book_user_info');
            $searchWebUser = $webBookUserTable->where("book_user_info_id=$id")->find();
            //存储book表
            $data['owner_name'] = $searchWebUser['username'];
            $data['keeper_name'] = $searchWebUser['username'];
            $data['contribute_time'] = $BookInfo['author'];
            $data['status'] = 1;
            $data['isbn'] = $ISBN;
            $data['con_name'] = '';
            $res = $webBookTable->data($data)->add();
            if($res){
                return 'success';
            }
        }
        //保存web端图书详情信息
        function saveWebBookMsg($ISBN,$BookInfo){
            $webBookInfoTable = M('bookinfo');
            //首先根据图书ISBN进行查找
            $map['isbn'] = "$ISBN";
            $res = $webBookInfoTable->where($map)->find();
            if($res){
                return 'existed';
            }else{
                $data['isbn'] = $ISBN;
                $data['title'] = $BookInfo['title'];
                $data['author'] = $BookInfo['author'];
                $data['translator'] = $BookInfo['translator'];
                $data['publisher'] = $BookInfo['publisher'];
                $data['image'] = $BookInfo['image'];
                $data['tags'] = $BookInfo['tags'];
                $res = $webBookInfoTable->data($data)->add();
                if($res){
                    return 'success';
                }

            }
        }
        //保存用户借阅信息
        function saveBookUser($ISBN,$openid,$action){
            $bookUser = M('weixin_book_user');
            $data['isbn'] = $ISBN;
            $data['openid'] = "$openid";
            $data['action'] = $action;
            if($action=='borrow'){
                $data['return_time'] = date('Y-m-d H:i:s',strtotime("+1 month"));
                $data['is_return'] = 0;
            }
            $data['create_time'] = date('Y-m-d H:i:s',time());
            $data['update_time'] = date('Y-m-d H:i:s',time());
            $bookUser->data($data)->add();
        }
        //保存图书馆中图书本数信息
        function saveBookList($ISBN,$action){
            $bookList = M('weixin_book_list');
            $map['isbn'] = "$ISBN";
            $res = $bookList->where($map)->find();
            if($res){   //书本已存在
                if($action=='share'){   //分享
                    $share_data['total_count'] = $res['total_count']+1;
                    $share_data['surplus_count'] = $res['surplus_count']+1;
                    $share_data['update_time'] = date('Y-m-d H:i:s',time());
                    $bookList->where($map)->save($share_data);
                    $back_data['msg'] = '分享成功';
                }elseif($action=='borrow'){     //借阅
                    if($res['surplus_count']=0){
                        $back_data['msg'] = '当前此书已全部外借';
                    }else{
                        $borrow_data['surplus_count'] = $res['surplus_count']-1;
                        $borrow_data['update_time'] = date('Y-m-d H:i:s',time());
                        $bookList->where($map)->save($borrow_data);
                        $back_data['msg'] = '借阅成功';
                    }
                }else{      //归还
                    $return_data['surplus_count'] = $res['surplus_count']+1;
                    $return_data['update_time'] = date('Y-m-d H:i:s',time());
                    $bookList->where($map)->save($return_data);
                    $back_data['msg'] = '归还成功';
                }
            }else{      //书本未存在
                $new_date['isbn'] = $ISBN;
                $new_date['total_count'] = 1;
                $new_date['surplus_count'] = 1;
                $new_date['create_time'] = date('Y-m-d H:i:s',time());
                $new_date['update_time'] = date('Y-m-d H:i:s',time());
                $bookList->data($new_date)->add();
                $back_data['msg'] = '分享成功';
            }
            return $back_data;
        }

        //获取token(此处主要为从数据库中进行数据的调取)
        function getWeixinToken()
        {
            $weixin_conf_data = M('weixin_conf_data');
            $conf = $weixin_conf_data->where('id=1')->find();
            $token_time = $conf['time'];
            $now_time = time();
            $tip = $now_time - $token_time;
            if ($tip < 7000) {  //直接输出
                //判断token是否有效
                $access_token = $conf['access_token'];
                $url = 'https://api.weixin.qq.com/cgi-bin/groups/get?access_token='.$access_token;
                $res = $this->api_request($url);
                if (empty($access_token) || $res->errcode == 40001) {
                    //重新获取
                    $AppID = $conf['appid'];
                    $AppSecret = $conf['appsecret'];
                    $res = $this->getWeixinAccessToken($AppID, $AppSecret);
                    $access_token = $res->access_token;
                    $new_data['access_token'] = $access_token;
                    $new_data['time'] = time();
                    $res = $weixin_conf_data->where('id=1')->save($new_data);
                }
            } else {  //重新获取
                $AppID = $conf['appid'];
                $AppSecret = $conf['appsecret'];
                $res = $this->getWeixinAccessToken($AppID, $AppSecret);
                $access_token = $res->access_token;
                $new_data['access_token'] = $access_token;
                $new_data['time'] = time();
                $res = $weixin_conf_data->where('id=1')->save($new_data);
            }
            return $access_token;
        }

        //获取access_token
        function getWeixinAccessToken($appid, $appsecret)
        {
            $url = 'https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=' . $appid . '&secret=' . $appsecret;
            $result = $this->api_request($url);
            return $result;
        }

        //根据openid获取用户基本信息
        function getUserInfo($access_token,$openid){
            $request_url = 'https://api.weixin.qq.com/cgi-bin/user/info?access_token='.$access_token.'&openid='.$openid.'&lang=zh_CN';
            $res = $this->api_request($request_url);
            return $res;
        }


        /*
        * 微信公众号接口调用函数(通过是否传入data判断其为get请求还是post请求)
        * 对于多种API调用均适合
        */
        function api_request($url,$data=null){
            //初始化cURL方法
            $ch = curl_init();
            //设置cURL参数（基本参数）
            $opts = array(
                //在局域网内访问https站点时需要设置以下两项，关闭ssl验证！
                //此两项正式上线时需要更改（不检查和验证认证）
                CURLOPT_SSL_VERIFYPEER => false,
                CURLOPT_SSL_VERIFYHOST => false,
                CURLOPT_TIMEOUT        => 30,
                CURLOPT_RETURNTRANSFER => true,
                CURLOPT_URL            => $url,
                /*CURLOPT_POST           => true,
                CURLOPT_POSTFIELDS     => $data*/
            );
            curl_setopt_array($ch,$opts);
            //post请求参数
            if(!empty($data)){
                curl_setopt($ch,CURLOPT_POST,true);
                curl_setopt($ch,CURLOPT_POSTFIELDS,$data);
            }
            //执行cURL操作
            $output = curl_exec($ch);
            if(curl_errno($ch)){    //cURL操作发生错误处理。
                var_dump(curl_error($ch));
                die;
            }
            //关闭cURL
            curl_close($ch);
            $res = json_decode($output);
            return($res);   //返回json数据
        }

        //微信公众账号回复函数
        //回复文本消息
        function responseText($postObj,$Content){
            $FromUserName = $postObj->ToUserName;
            $ToUserName   = $postObj->FromUserName;
            $MsgType = 'text';
            $CreateTime = time();
            $template = "<xml>
                            <ToUserName><![CDATA[%s]]></ToUserName>
                            <FromUserName><![CDATA[%s]]></FromUserName>
                            <CreateTime>%s</CreateTime>
                            <MsgType><![CDATA[%s]]></MsgType>
                            <Content><![CDATA[%s]]></Content>
                            </xml>";
            $info = sprintf($template,$ToUserName,$FromUserName,$CreateTime,$MsgType,$Content);
            echo $info;
        }
        //回复图文消息
        function responseNews($postObj,$array){
            $ToUserName = $postObj->FromUserName;
            $FromUserName = $postObj->ToUserName;
            $CreateTime = time();
            $MsgType = 'news';
            $template = "<xml>
                            <ToUserName><![CDATA[%s]]></ToUserName>
                            <FromUserName><![CDATA[%s]]></FromUserName>
                            <CreateTime>%s</CreateTime>
                            <MsgType><![CDATA[%s]]></MsgType>
                            <ArticleCount>".count($array)."</ArticleCount>
                            <Articles>";
            foreach($array as $key=>$value){
                $template .="<item>
                                <Title><![CDATA[".$value['title']."]]></Title>
                                <Description><![CDATA[".$value['description']."]]></Description>
                                <PicUrl><![CDATA[".$value['picUrl']."]]></PicUrl>
                                <Url><![CDATA[".$value['url']."]]></Url>
                                </item>";
            }
            $template .="</Articles>
                            </xml> ";
            $info = sprintf( $template, $ToUserName, $FromUserName, $CreateTime, $MsgType );
            echo $info;
        }


        //豆瓣图书API(豆瓣API提供了根据ISBN来查询书本信息的服务)（接口）
        function searchBookInfo($ISBN){
            header('Content-type: text/html; charset=utf-8');
            $request_url = 'https://api.douban.com/v2/book/isbn/'.$ISBN;
            $res = $this->api_request($request_url);
            $BookInfo['title'] = $res->title;
            $author = $res->author;
            $BookInfo['author'] = $author[0];
            $BookInfo['author_intro'] = $res->author_intro;
            $BookInfo['summary'] = $res->summary;
            $BookInfo['image'] = $res->image;
            $BookInfo['catalog'] = $res->catalog;
            $BookInfo['ebook_url'] = $res->ebook_url;
            $BookInfo['translator'] = $res->translator;
            $BookInfo['publisher'] = $res->publisher;
            //添加标签
            $tags = $res->tags;
            $BookInfo['tags'] = '';
            foreach($tags as $key=>$value){
                $BookInfo['tags'] .= $value->name.' ';
            }
            return $BookInfo;
        }
        //获取用户详情(接口)
        /*function getWeixinUserInfo($access_token,$openid){
            $request_url = 'https://api.weixin.qq.com/cgi-bin/user/info?access_token='.$access_token.'&openid='.$openid.'&lang=zh_CN';
            $user_info_data = api_request($request_url);
            $data['nickname'] = $user_info_data->nickname;
            $data['sex'] = $user_info_data->sex;
            $data['province'] = $user_info_data->province;
            $data['city'] = $user_info_data->city;
            $data['country'] = $user_info_data->country;
            $data['headimgurl'] = $user_info_data->headimgurl;
            return $data;
        }*/
        //获取图书详情(数据库)
        function getBookInfo($ISBN){
            $bookInfo = M('weixin_book_info');
            $map['isbn'] = $ISBN;
            $res = $bookInfo->where($map)->find();
            return $res;
        }
        //获取用户详情（数据库）
        function getWeixinUserInfo($openid){
            $userInfo = M('weixin_user_info');
            $map['openid'] = $openid;
            $res = $userInfo->where($map)->find();
            return $res;
        }
        //获取图书用户详情
        function getBookUser($ISBN,$openid){
            $bookUser = M('weixin_book_user');
            $map['isbn'] = $ISBN;
            $map['openid'] = $openid;
            $res = $bookUser->where($map)->find();
            return $res;
        }
        //获取图书本数详情
        function getBookList($ISBN){
            $bookList = M('weixin_book_list');
            $map['isbn'] = $ISBN;
            $res = $bookList->where($map)->find();
            return $res;
        }


    }
