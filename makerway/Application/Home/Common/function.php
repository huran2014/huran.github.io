<?php
function ordernum($array,$username){
    foreach ($array as $id => $item) {
        if ($item['keeper_name']==$username) {
            return intval($id)+1;
        }
    }
    return 0;
    $b=M('book');
    $bi=M('bookinfo');
    $book=$b->where(array('book_id',$item['book_id']))->find();
    $bookin=$bi->where(array('isbn',$book['isbn']))->find();
    $title='《'.$bookin['title'].'》';
}
function getVisitLog($username='visiter'){
    $pn=M('passonnums');
    return $pn->where(array('username'=>$username))->order('id desc')->select();
}
function getTitle($bookid){
    $b=M('book');
    $bi=M('bookinfo');
    $book=$b->where(array('book_id'=>$bookid))->find();
    $bookin=$bi->where(array('isbn'=>$book['isbn']))->find();
    $title='《'.$bookin['title'].'》';
    return $title;
}

function toLogStr($logs){
    $logStr=array();
    foreach ($logs as $key => $item) {
        if ($item['status']==2) {
            $title=getTitle($item['book_id']);
            $str=$item['keeper_name'].' 在'.$item['start_time'].' 借书--'.$title;
            array_push($logStr, $str);
        }
        else if ($item['status']==3) {
            $title=getTitle($item['book_id']);
            $str=$item['keeper_name'].' 在'.$item['end_time'].' 还书--'.$title;
            array_push($logStr, $str);
        }
        else if ($item['status']==1) {
            $title=getTitle($item['book_id']);
            $str=$item['keeper_name'].' 在'.$item['start_time'].' 分享了 '.$title;
            array_push($logStr, $str);
        }
        else if ($item['status']==4) {
            $title=getTitle($item['book_id']);
            $str=$item['keeper_name'].' 在'.$item['start_time'].' 预订--'.$title;
            array_push($logStr, $str);
        }
//        else if ($item['status']==5) {
//            $title=getTitle($item['book_id']);
//            $str=$item['keeper_name'].' 在'.$item['end_time'].' 得到了预订的书--'.$title;
//            array_push($logStr, $str);
//        }
        else if ($item['status']==5) {
            $title=getTitle($item['book_id']);
            $str=$item['keeper_name'].' 在'.$item['start_time'].' 续借书--'.$title;
            array_push($logStr, $str);
        }
//        else if ($item['status']==7) {
//            $title=getTitle($item['book_id']);
//            $str=$item['keeper_name'].' 在'.$item['end_time'].' 还书--'.$title;
//            array_push($logStr, $str);
//        }
    }
    return $logStr;
}

function pagesname($pages){
    $dictionary=array(
        'index'=>'首页',
        'share'=>'共享库',
        'bookInfo'=>'书的信息',
        'login'=>'登录',
        'shareLogin'=>'分享库登录',
        'bindready'=>'微信登录',
        'register'=>'注册',
        'shareRegister'=>'分享库注册',
        'contribute'=>'我要贡献',
        'bookkeeps'=>'我的保管',
        'bookorder'=>'我的预约',
        'mykeeps'=>'我的阅读',
        'comment'=>'书本评论',
        'forget'=>'忘记密码',
        'ensure'=>'书籍确认',
        'person'=>'个人中心',
        'guestShare'=>'guest中心',
        'guestPersonshare'=>'guest共享',
        'guestKeeps'=>'guest借阅',
        'guestOrder'=>'guest预约',
        'guestBookkeeps'=>'guest保管',
        'guestAttention'=>'guest关注',
        'personshare'=>'我的分享',
        'myattention'=>'我的关注',
        'message'=>'个人信息',
        'myKeeps'=>'我的保管',
        'logout'=>'注销',
        'phoneBorrow'=>'借书',
        'borrow'=>'手机登录',
        'phoneRegister'=>'手机注册',
        'phoneMain'=>'手机书本信息',
        'phoneRenew'=>'续借',
        'phoneReturn'=>'还书',
        'phoneOrder'=>'预约',
        'search'=>'搜索库',
        'squre'=>'广场',
        'qr'=>'查看二维码',
    );
    foreach ($pages as $key => $item) {
        if ($dictionary[$item]) {
            $pages[$key]=urlencode($dictionary[$item]);
            // $pages[$key]=$dictionary[$item];
        }
    }
    return $pages;
}

function passonCount($pagename,$des=null){
    $p=M('passoncount');
    $date=date('Y m d',time());
    $map=array(
        'pagename'=>$pagename,
        'date'=>$date
    );
    if ($p->where($map)->find()) {
        $data=$p->where($map)->find();
        $data['count']+=1;
        $p->save($data);
    }else{
        $data=array(
            'pagename'=>$pagename,
            'count'=>1,
            'date'=> $date,
            'des'=>$des);
        $p->add($data);
    }
}

function userStates($pagename){
    $u=M('passonnums');
    $timeseconds=time();
    $date=date("Y-m-d",$timeseconds);
    $time=date("h:i:s a");
    $data=array();
    if (cookie('username')) {
        $data['username']=cookie('username');
    }else{
        $data['username']='visiter';
    }
    $data['ip']=get_client_ip();
    $data['pagename']=$pagename;
    $data['timeseconds']=strval($timeseconds);
    $data['date']=$date;
    $data['time']=$time;
    $u->add($data);

}

function getqr1($book_id,$username,$title){
    // $site="http://192.168.1.105/wind87/passon/index.php/Home/Index/borrow/bookid/".$book_id.".html";
    $site="http://www.makerway.space/makerway/index.php/Home/Index/borrow/bookid/".$book_id.".html";
    $url="http://api.wwei.cn/wwei.html?apikey=20151202006757&data=".$site;
    $hm=file_get_contents($url);
    $hmarray=json_decode($hm,true);
    $imstring=$hmarray['data']['qr_filepath'];
    $qrim=imagecreatefromstring(file_get_contents($imstring));

    $username="贡献者：".$username;
    $title="《".$title."》";

    $head="MAKER WAY";
    $des="一起分享智慧";

    // $username=iconv("utf-8","gb2312","贡献者：".$username);
    // $title=iconv("gb2312","utf8","《".$title."》");
    // $title=mb_convert_encoding($title,'html-entities','UTF-8');

    header("Content-type: image/png");
    $titleim = imagecreatetruecolor(200,50);
    $textcolor = imagecolorallocate($titleim,255,255,255);
    $font = "Public/font/msyh.ttf";
    // $text='中国';
    // $title = mb_convert_encoding($title,'UTF-8','GB2312');


    // imagejpeg($im);

    $usernameim = imagecreatetruecolor(200,50);

    if (strlen($title)<35) {
        imagettftext($titleim,10,0,20,20,$textcolor,$font,$title);
        // imagettftext($usernameim,10,0,20,20,$textcolor,$font,$username);
    } else {
        imagettftext($titleim,7,0,20,20,$textcolor,$font,$title);
        // imagettftext($usernameim,7,0,20,20,$textcolor,$font,$username);
    }
    if (strlen($title)<20) {
        // imagettftext($titleim,10,0,20,20,$textcolor,$font,$title);
        imagettftext($usernameim,10,0,20,20,$textcolor,$font,$username);
    } else {
        // imagettftext($titleim,7,0,20,20,$textcolor,$font,$title);
        imagettftext($usernameim,8,0,20,20,$textcolor,$font,$username);
    }


    $headim = imagecreatetruecolor(200,50);
    imagettftext($headim,12,0,20,20,$textcolor,$font,$head);
    $desim = imagecreatetruecolor(200,40);
    imagettftext($desim,8,0,20,20,$textcolor,$font,$des);
    // $titleim=imagecreate(200, 30);
    // imagecolorallocate( $titleim, 255, 255, 255 );
    // $Color = imagecolorallocate( $titleim, 0, 0, 0 );
    // // ImageTTFText($titleim,12,0,10,20,$black,"simkai.ttf",$title);
    // imagestring($titleim,16, 0, 0, $title, $Color);

    // $usernameim=imagecreate(200, 30);
    // imagecolorallocate( $usernameim, 255, 255, 255 );
    // $Color = imagecolorallocate( $usernameim, 0, 0, 0 );
    // imagestring($usernameim,16, 0, 0, $username, $Color);

    $imo=imagecreatetruecolor(200, 330);
    imagecolorallocate( $imo, 255, 255, 255 );
    imagecopy($imo, $qrim, 0, 60, 0, 0, 200, 200);
    if (strlen($title)<35) {
        imagecopy($imo, $titleim, 0, 260, 20, 0, 200, 40);
    } else {
        imagecopy($imo, $titleim, 0, 260, 20, 0, 200, 40);
    }
    imagecopy($imo, $usernameim, 0, 290, 16, 0, 200, 40);
    imagecopy($imo, $headim, 30, 10, 0, 0, 200, 30);
    imagecopy($imo, $desim, 50, 30, 0, 0, 200, 30);

    return $imo;
}
function getQr($book_id,$username,$title){
     $site="http://www.makerway.space/makerway/index.php/Home/Index/borrow/bookid/".$book_id.".html";
//    $site="http://www.makerway.space/passon/index.php/Home/Index/borrow/bookid/".$book_id.".html";
    $url="http://api.wwei.cn/wwei.html?apikey=20151202006757&data=".$site;
    $hm=file_get_contents($url);
    $hmarray=json_decode($hm,true);
    $imstring=$hmarray['data']['qr_filepath'];
    $qrim=imagecreatefromstring(file_get_contents($imstring));

    $title="《".$title."》";

    header("Content-type: image/png");
    $titleim = imagecreate(300,40);
    $white=imagecolorallocate($titleim,255,255,255);
    $black = imagecolorallocate($titleim, 0, 0, 0);
    $fonttitle="Public/font/msyh.ttf";
    $font = "Public/font/manhua.ttf";
    $usernameim = imagecreate(300,50);
    imagecolorallocate($usernameim,255,255,255);
    $blackq = imagecolorallocate($usernameim, 0, 0, 0);
    imagettftext($titleim,12,0,20,20,$black,$fonttitle,$title);
    imagettftext($usernameim,18,0,20,20,$blackq,$font,$username);

    imagecolortransparent($usernameim,$white);//将背景色换成透明色
    imagecolortransparent($titleim,$white);

    $imo=imagecreatefromstring(file_get_contents('Public/images/qr.png'));

    imagecopy($imo, $qrim, 455, 58, 0, 0, 200, 200);
    imagecopy($imo, $titleim, 450, 275, 20, 0, 300, 40);
    imagecopy($imo, $usernameim, 540, 337, 16, 0, 300, 40);

    return $imo;
}

function getArrayByIsbn($s){
    $url="https://api.douban.com/v2/book/isbn/:".$s;
    $hm= file_get_contents($url);
    $bk=json_decode($hm,true);
    return $bk;
}
/*
 * $url 图片地址
 * $filepath 图片保存地址
 * return 返回下载的图片路径和名称
 */
function getimg($url, $filepath,$filename) {

    if ($url == '') {
        return false;
    }
    $ext = strrchr($url, '.');

    if ($ext != '.gif' && $ext != '.jpg' && $ext != '.png') {
        return false;
    }

    //判断路经是否存在
    !is_dir($filepath)?mkdir($filepath):null;

    //获得随机的图片名，并加上后辍名
    // $filetime = time();
    // $filename = date("YmdHis",$filetime).rand(100,999).'.'.substr($url,-3,3);
    $filename = $filename.'.'.substr($url,-3,3);
    $filename=iconv("utf-8","gb2312",$filename);

    //读取图片
    $img = file_get_contents($url);

    // $img = file_get_contents('http://www.baidu.com/img/baidu_logo.gif');
    // file_put_contents('Public/bookimgs/baidu.gif',$img);
    // echo '<img src="baidu.gif">';
    //指定打开的文件
    // $fp = @ fopen($filepath.'/'.$filename, 'a');
    //写入图片到指定的文本
    // fwrite($fp, $img);
    // fclose($fp);
    file_put_contents($filepath.'/'.$filename,$img);
    return '/'.$filepath.'/'.$filename;
}
function getnicknameByusername($username){
    $user = M('booksusers');
    $bind = M('book_bind');
    $wechatuser = M('book_wechat_userinfo');
    $userresult = $user->where(Array('username'=>$username))->find();
    $idresult = $bind->where(Array('userid'=>$userresult['book_user_info_id']))->find();
    if($idresult){
        $wechatinfo = $wechatuser->where(Array('openid'=>$idresult['openid']))->find();
        $username = $wechatinfo['nickname'];
        return $username;
    }else{
        return $username;
    }
}
function getusernameBynickname($username){
    $user = M('booksusers');
    $bind = M('book_bind');
    $wechatuser = M('book_wechat_userinfo');
    $wechatinfo = $wechatuser->where(Array('nickname'=>$username))->find();
    $idresult = $bind->where(Array('openid'=>$wechatinfo['openid']))->find();
    if($idresult){
        $userresult = $user->where(Array('book_user_info_id'=>$idresult['userid']))->find();
        $username = $userresult['username'];
        return $username;
    }else{
        return $username;
    }
}