<?php
namespace WeChat\Controller;
use Think\Controller;
header('Content-Type:text/html; charset=utf-8');


class IndexController extends Controller {
    public function stastic(){
//        userStates('index');
        $date=date('Y m d',time());
        $p=M('passoncount');
        $nums=$p->where(array('date'=>$date))->select();
        $pages=array();
        $pagesnum=array();
        foreach ($nums as $id => $item) {
            array_push($pages, $item['pagename']);
            array_push($pagesnum, $item['count']);
        }
        $pages=pagesname($pages);
        $pages=urldecode(json_encode($pages));
        // $pages=json_encode($pages);
        $pagesnum=json_encode($pagesnum);
        // $date='today';
        $visitLog=getVisitLog(cookie('username'));
        $this->visitLog=$visitLog;
        $this->date=$date;
        $this->pages=$pages;
        $this->pagesnum=$pagesnum;
        $this->display();
    }
    public function showLogs(){
        $bl=M('booklog');
        $logs=$bl->order('log_id  desc')->limit(10)->select();
        $logstr=toLogStr($logs);
        $num=count($logstr);
        echo json_encode($logstr[rand(0,$num-1)]);
    }
    public function index(){
        $AppID = 'wx8c19e3b897302e8d';          //Ó¦ÓÃÎ¨Ò»±êÊ¶
            $AppSecret = '6d718da8092508624930b0b92b2999ea';
            $redirect_uri = 'http://www.makerway.space/passon/index.php/Home/Index/bindready';  //ÖØ¶¨ÏòµØÖ·£¬ÐèÒª½øÐÐUrlEncode
            $redirect_uri = urlencode($redirect_uri);

            $respponse_type = 'code';
            $scope = 'snsapi_login';    //Ó¦ÓÃÊÚÈ¨×÷ÓÃÓò£¬ÓµÓÐ¶à¸ö×÷ÓÃÓòÓÃ¶ººÅ£¨,£©·Ö¸ô£¬ÍøÒ³Ó¦ÓÃÄ¿Ç°½öÌîÐ´snsapi_login¼´¿É
            $state = rand(0,10000).'session';
            //µÚÒ»²½£ºÇëÇóCODE
            $url_one = 'https://open.weixin.qq.com/connect/qrconnect?appid='.$AppID.'&redirect_uri='.$redirect_uri.'&response_type='.$respponse_type.'&scope='.$scope.'&state='.$state.'#wechat_redirect';  //Î¢ÐÅµÇÂ¼µØÖ·
            $this->assign('url',$url_one);
            //header("Location:'$url_one'");
            //µÚ¶þ²½£ºÍ¨¹ýcode»ñÈ¡access_token
            // if (isset($_GET['code'])){
            //     echo $_GET['code'];
            // }else{
            //     echo "NO CODE";
            // }
            // //$grant_type = 'authorization_code';
            // $url_two = 'https://api.weixin.qq.com/sns/oauth2/access_token?appid='.$AppID.'&secret='.$AppSecret.'&code='.$code.'&grant_type=authorization_code';
            // $res_two = $this->https_request($url_two);
            // $access_token = $res_two->access_token;
            // $refresh_token = $res_two->refresh_token;
            // $openid = $res_two->openid;
            // $scope = $res_two->scope;
            // //µÚÈý²½£ºÍ¨¹ýaccess_tokenµ÷ÓÃ½Ó¿Ú£¨»ñÈ¡ÓÃ»§¸öÈËÐÅÏ¢£¨UnionID»úÖÆ£©£©
            // $url_three = 'https://api.weixin.qq.com/sns/userinfo?access_token='.$access_token.'&openid='.$openid;
            // $res_three = $this->https_request($url_three);
            // $nickname = $res_three->nickname;       //Î¢ÐÅêÇ³Æ
            // $headimgurl = $res_three->headimgurl;   //Î¢ÐÅÍ·Ïñ
        passonCount('index');
        userStates('index');
        $this->display();
    }
    function getcode(){
            if (isset($_GET['code'])){
                echo $_GET['code'];
            }else{
                echo "NO CODE";
            }
            $code = $_GET['code'];
            $AppID = 'wx8c19e3b897302e8d';          //Ó¦ÓÃÎ¨Ò»±êÊ¶
            $AppSecret = '6d718da8092508624930b0b92b2999ea';
            //$grant_type = 'authorization_code';
            $url_two = 'https://api.weixin.qq.com/sns/oauth2/access_token?appid='.$AppID.'&secret='.$AppSecret.'&code='.$code.'&grant_type=authorization_code';
            $res_two = $this->https_request($url_two);
            /*$access_token = $res_two->access_token;
            $refresh_token = $res_two->refresh_token;
            $openid = $res_two->openid;
            $scope = $res_two->scope;*/
            return $res_two;
            //µÚÈý²½£ºÍ¨¹ýaccess_tokenµ÷ÓÃ½Ó¿Ú£¨»ñÈ¡ÓÃ»§¸öÈËÐÅÏ¢£¨UnionID»úÖÆ£©£©
            // $url_three = 'https://api.weixin.qq.com/sns/userinfo?access_token='.$access_token.'&openid='.$openid;
            // $res_three = $this->https_request($url_three);
            // $nickname = $res_three->nickname;       //Î¢ÐÅêÇ³Æ
            // $headimgurl = $res_three->headimgurl;   //Î¢ÐÅÍ·Ïñ
    }
    function https_request($url,$data=null){
            //³õÊ¼»¯cURL·½·¨
            $ch = curl_init();
            //ÉèÖÃcURL²ÎÊý£¨»ù±¾²ÎÊý£©
            $opts = array(
                //ÔÚ¾ÖÓòÍøÄÚ·ÃÎÊhttpsÕ¾µãÊ±ÐèÒªÉèÖÃÒÔÏÂÁ½Ïî£¬¹Ø±ÕsslÑéÖ¤£¡
                //´ËÁ½ÏîÕýÊ½ÉÏÏßÊ±ÐèÒª¸ü¸Ä£¨²»¼ì²éºÍÑéÖ¤ÈÏÖ¤£©
                CURLOPT_SSL_VERIFYPEER => false,
                CURLOPT_SSL_VERIFYHOST => false,
                CURLOPT_TIMEOUT        => 30,
                CURLOPT_RETURNTRANSFER => true,
                CURLOPT_URL            => $url,
                /*CURLOPT_POST           => true,
                CURLOPT_POSTFIELDS     => $data*/
            );
            curl_setopt_array($ch,$opts);
            //postÇëÇó²ÎÊý
            if(!empty($data)){
                curl_setopt($ch,CURLOPT_POST,true);
                curl_setopt($ch,CURLOPT_POSTFIELDS,$data);
            }
            //Ö´ÐÐcURL²Ù×÷
            $output = curl_exec($ch);
            if(curl_errno($ch)){    //cURL²Ù×÷·¢Éú´íÎó´¦Àí¡£
                var_dump(curl_error($ch));
                die;
            }
            //¹Ø±ÕcURL
            curl_close($ch);
            $res = json_decode($output);
            return($res);   //·µ»ØjsonÊý¾Ý
        }
    public function bindready(){
        $bind = M('book_bind');
        $res_two = $this->getcode();
        dump($res_two);
        print_r($res_two->access_token);
        $access_token = $res_two->access_token;
        $refresh_token = $res_two->refresh_token;
        $openid = $res_two->openid;
        $scope = $res_two->scope;
        $url_three = 'https://api.weixin.qq.com/sns/userinfo?access_token='.$access_token.'&openid='.$openid;
        $res_three = $this->https_request($url_three);
        dump($res_three);
        $nickname = $res_three->nickname;
        $headimgurl = $res_three->headimgurl;
    }    
    public function bind(){
    
        $this->display();
    }
    public function bindUser(){
        $oldUser = I('oldUser');
        $phonenum = I('phonenum');
        $address = I('address');
        $user = M('book_user_info');
        $bind = M('book_bind');
        $openId = cookie('openId');
        $username = cookie('nickname');
        if($oldUser){
            $data = Array(
                'username'=>$oldUser
            );
            $result = $user->where($data)->find();
            if(!$result){
                $this->error("用户名不存在",U(bind),3);
            }else{
                $userId = $result['book_user_info_id'];
                $binddata = Array(
                    'openId'=>$openId,
                    'userId'=>$userId
                );
                $bind->add($binddata);
            }
        }else{
            $newdata = Array(
                'username'=>$username,
                'phone'=>$phonenum,
                'addr'=>$address,
                'flag'=>1
            );
            $user->add($newdata);
            $newuser = Array(
                'username'=>$username
            );
            $newresult = $user->where($newuser)->find();
            $binddatanew = Array(
                'openId'=>$openId,
                'userId'=>$newresult['book_user_info_id']
            );
            $bind->add($binddatanew);
        }
        $this->redirect(index);
    }
    public function register(){
        passonCount('register');
        userStates('register');
        $this->display();
    }
    public function shareRegister(){
        passonCount('Register');
        userStates('Register');
        $this->display();
    }
    public function forget(){
        passonCount('forget');
        userStates('forget');
        $this->display();
    }
    public function pcForget(){
        $username =  I("username");
        $password =  I("password");
        $password =  md5($password);
        $phonenum = I("phonenum");
        $m=M('book_user_info');
        $data=array(
            "username"=>$username,
            "password"=>$password,
            "phonenum"=>$phonenum
        );
        $name = Array(
            "username"=>$username
        );
        $datanum = $m->where($name)->find();
        $data = $m->where($name)->save($data);
        if($datanum){
            if($data){
                $this->success("修改成功",U(Login),3);
            }else{
                $this->error("修改失败",U(Register),3);
            }
        }else{
            $this->error("用户名不存在",U(Register),3);
        }
    }
    public function login(){
        passonCount('login');
        userStates('login');
        $this->display();
    }
    public function shareLogin(){
        passonCount('login');
        userStates('login');
        $this->display();
    }
    public function search(){
        passonCount('search');
        userStates('search');
        $search = I("search");
        $array = [];
        $bookinfo = M("bookinfo");
        $book = M("book");
        if($search != "有缘书"){
            $where['title|tags'] = array('like',"%$search%");
            $searchinfo = $bookinfo->where($where)->select();
            if($searchinfo){
                $this->flag = 1;
            }else{
                $this->flag = 0;
            }
            foreach($searchinfo as $key => $bi){
                $searchmessage = Array(
                    'isbn'=>$bi['isbn']
                );
                $searchlist=$book->where($searchmessage)->select();
                foreach($searchlist as $key =>$b){
                    $b['bookinfo']=$bi;
                    array_push($array,$b);
                }
                $this->book=$array;
            }
        }else{
//            $condition= 'isbn order by rand()';
//            $where['title|tags'] = array('like',"%$search%");
            $searchinfo = $bookinfo->order('rand()')->limit(5)->select();
            if($searchinfo){
                $this->flag = 1;
            }else{
                $this->flag = 0;
            }
            foreach($searchinfo as $key => $bi){
                $searchmessage = Array(
                    'isbn'=>$bi['isbn']
                );
                $searchlist=$book->where($searchmessage)->select();
//                print_r($searchlist);
                foreach($searchlist as $key =>$b){
                    $b['bookinfo']=$bi;
                    array_push($array,$b);
                }
            }
            // $this->flag = 0;
            $this->book=$array;
        }
        $this->search = $search;
        $this->display();
    }
    public function person(){
        passonCount('person');
        userStates('person');
        if(cookie('username')){
            $log = M('booklog');
            $book = M('book');
            $bookinfo = M('bookinfo');
            $usermessage = M('book_user_info');
            $user = $usermessage->where(Array("username" => cookie('username')))->find();
//            $user = Array(
//                "username"=>$usermessage['username'],
//                "phone"=>$usermessage['phone'],
//                "addr"=>$usermessage['addr']
//            );
            $booklog = Array(
                'keeper_name'=>cookie('username')
            );
            $booklog = $log->where($booklog)->order('log_id desc')->select();
            foreach($booklog as $key=>$value){
                $bookid = Array(
                    "book_id"=>$value['book_id']
                );
                $bookmessage = $book->where($bookid)->find();
                $isbn = Array(
                    'isbn'=>$bookmessage['isbn']
                );
                $booktitle = $bookinfo->where($isbn)->find();
                $booklog[$key]['booklog']=$booktitle;
            }
            //////////////积分
            $honer = M('book_honer');
            $result = $honer->where(Array('username'=>cookie('username')))->find();
            if($result){
                $this->integral=$result['integral'];
            }else{
                $this->integral=0;
            }
            /////
            ///分享书本数
            $datanum = $book->where(Array('owner_name'=>cookie('username')))->select();
            if($datanum){
                $this->booknum=count($datanum);
            }else{
                $this->booknum=0;
            }
            ///
            $this->user= $user;
            $this->booklog=$booklog;
            $this->display();
        }else{
            $this->redirect(index);
        }
    }
    public function message(){
        passonCount('message');
        userStates('message');
        if(cookie('username')){
            $log = M('booklog');
            $book = M('book');
            $bookinfo = M('bookinfo');
            $usermessage = M('book_user_info');
            $user = $usermessage->where(Array("username" => cookie('username')))->find();
//            $user = Array(
//                "username"=>$usermessage['username'],
//                "phone"=>$usermessage['phone'],
//                "addr"=>$usermessage['addr']
//            );
            $booklog = Array(
                'keeper_name'=>cookie('username')
            );
            $booklog = $log->where($booklog)->order('log_id desc')->select();
            foreach($booklog as $key=>$value){
                $bookid = Array(
                    "book_id"=>$value['book_id']
                );
                $bookmessage = $book->where($bookid)->find();
                $isbn = Array(
                    'isbn'=>$bookmessage['isbn']
                );
                $booktitle = $bookinfo->where($isbn)->find();
                $booklog[$key]['booklog']=$booktitle;
            }
            //////////////积分
            $honer = M('book_honer');
            $result = $honer->where(Array('username'=>cookie('username')))->find();
            if($result){
                $this->integral=$result['integral'];
            }else{
                $this->integral=0;
            }
            /////
            ///分享书本数
            $datanum = $book->where(Array('owner_name'=>cookie('username')))->select();
            if($datanum){
                $this->booknum=count($datanum);
            }else{
                $this->booknum=0;
            }
            ///
            $this->booklog=$booklog;
            $this->user= $user;
            $this->display();
        }else{
            $this->redirect(index);
        }
    }
    public function usmessage1(){
        $phone = I('phonenum');
        $message = M('book_user_info');
        $user = Array(
            'username'=>cookie('username'),
            'phone'=>$phone
        );
        $data = $message->where(Array('username'=>cookie('username')))->save($user);
        if($data){
            $this->success("保存成功！",U(message),3);
        }else{
            $this->error("保存失败！",U(message),3);
        }
    }
    public function usmessage2(){
        $email = I('email');
        $message = M('book_user_info');
        $user = Array(
            'username'=>cookie('username'),
            'email'=>$email
        );
        $data = $message->where(Array('username'=>cookie('username')))->save($user);
        if($data){
            $this->success("保存成功！",U(message),3);
        }else{
            $this->error("保存失败！",U(message),3);
        }
    }
    public function usmessage3(){
        $addr = I('addr');
        $message = M('book_user_info');
        $user = Array(
            'username'=>cookie('username'),
            'addr'=>$addr
        );
        $data = $message->where(Array('username'=>cookie('username')))->save($user);
        if($data){
            $this->success("保存成功！",U(message),3);
        }else{
            $this->error("保存失败！",U(message),3);
        }
    }
    public function usemessage(){
        $username = cookie('username');
        $password =  I("password");
        $password =  md5($password);
        $fpassword =  I("fpassword");
        $fpassword =  md5($fpassword);
        $m=M('book_user_info');
        $data=array(
            "username"=>$username,
            "password"=>$password
        );
        $name = Array(
            "username"=>$username,
            "password"=>$fpassword
        );
        $datanum = $m->where($name)->find();
        $data = $m->where($name)->save($data);
        if($datanum){
            if($data){
                $this->success("修改成功",U(message),3);
            }else{
                $this->error("修改失败",U(message),3);
            }
        }else{
            $this->error("原密码错误",U(message),3);
        }
    }
    public function personshare(){
        passonCount('personshare');
        userStates('personshare');
        if(cookie('username')){
//            import("Org.Util.AjaxPage");
            $m=M('book');

            $log = M('booklog');

//            $book = M('book');
//            $bookinfo = M('bookinfo');

            $usermessage = M('book_user_info');
            $map=array('owner_name'=>cookie('username'));
            $user = $usermessage->where(Array("username" => cookie('username')))->find();
            $count = M('book')->where($map)->count();
            $page = new \Think\Page($count ,2);
            $limit = $page->firstRow . ',' . $page->listRows;
            $mybooks =$m->where($map)->order('book_id DESC')->limit($limit)->select();
            $this->page = $page->show();
//            $mybooks=$m->where($map)->order('book_id desc')->select();
            $bi=M('bookinfo');
            foreach ($mybooks as $id=>$book) {
                $mapbi=array('isbn'=>$book['isbn']);
                $bookin=$bi->where($mapbi)->find();
                $mybooks[$id]['bookinfo']=$bookin;
                // print_r($book);
            }

            $booklog = Array(
                'keeper_name'=>cookie('username')
            );
            $booklog = $log->where($booklog)->order('log_id desc')->select();
            foreach($booklog as $key=>$value){
                $bookid = Array(
                    "book_id"=>$value['book_id']
                );
                $bookmessage = $m->where($bookid)->find();
                $isbn = Array(
                    'isbn'=>$bookmessage['isbn']
                );
                $booktitle = $bi->where($isbn)->find();
                $booklog[$key]['booklog']=$booktitle;
            }
            //////////////积分
            $honer = M('book_honer');
            $result = $honer->where(Array('username'=>cookie('username')))->find();
            if($result){
                $this->integral=$result['integral'];
            }else{
                $this->integral=0;
            }
            /////
            ///分享书本数
            $datanum = $m->where(Array('owner_name'=>cookie('username')))->select();
            if($datanum){
                $this->booknum=count($datanum);
            }else{
                $this->booknum=0;
            }
            ///
            $this->booklog=$booklog;

            $this->mybooks=$mybooks;
            $this->user=$user;
            $this->display();
        }else{
            $this->redirect(index);
        }
    }
    public function myattention(){
        passonCount('myattention');
        userStates('myattention');
        //////////////////////////关注
        $array = [];
        $atte = M('bookattention');
        $m = M('book');
        $bi = M('bookinfo');
        $log = M('booklog');
        $usermessage = M('booksusers');
        $map = Array(
            'username'=>cookie('username')
        );
        $user = $usermessage->where(Array("username" => cookie('username')))->find();
        $count=$atte->where($map)->count();
        $page = new \Think\Page($count ,2);
        $limit = $page->firstRow . ',' . $page->listRows;
        $bookatten=$atte->where($map)->order('book_id desc')->limit($limit)->select();
        foreach ($bookatten as $key=>$value) {
            $bookid = Array(
                "book_id"=>$value['book_id']
            );
            $bookat = $m->where($bookid)->find();
//            array_push($array,$bookat);
//            foreach ($array as $id=>$book) {
                $isbn = Array(
                    'isbn'=>$bookat['isbn']
                );
                $bookattention = $bi->where($isbn)->find();

                $bookat['bookinfo']=$bookattention;
                // print_r($book);
//            }
            array_push($array,$bookat);
        }
        $booklog = Array(
            'keeper_name'=>cookie('username')
        );
        $booklog = $log->where($booklog)->order('log_id desc')->select();
        foreach($booklog as $key=>$value){
            $bookid = Array(
                "book_id"=>$value['book_id']
            );
            $bookmessage = $m->where($bookid)->find();
            $isbn = Array(
                'isbn'=>$bookmessage['isbn']
            );
            $booktitle = $bi->where($isbn)->find();
            $booklog[$key]['booklog']=$booktitle;
        }
        //////////////积分
        $honer = M('book_honer');
        $result = $honer->where(Array('username'=>cookie('username')))->find();
        if($result){
            $this->integral=$result['integral'];
        }else{
            $this->integral=0;
        }
        /////
        ///分享书本数
        $datanum = $m->where(Array('owner_name'=>cookie('username')))->select();
        if($datanum){
            $this->booknum=count($datanum);
        }else{
            $this->booknum=0;
        }
        ///
        $this->user= $user;
        $this->booklog=$booklog;
//         print_r($array);
        $this->booklist=$array;
        $this->page = $page->show();
//        $this->atten = $array;
        ///////////
        $this->display();
    }
    public function share(){
        passonCount('share');
        userStates('share');
        $m=M('book');
        $flag=I('flag');
        $atten=M('bookattention');
        if($flag == 0){
            $count = M('book')->count();
            $page = new \Think\Page($count ,9);
            $limit = $page->firstRow . ',' . $page->listRows;
            $booklist = M('book')->order('book_id DESC')->limit($limit)->select();
//        $booklist=$m->order('book_id desc')->select();
//        $this->board = $board;
            $this->page = $page->show();
            $bi=M('bookinfo');
            foreach ($booklist as $id=>$book) {
                $mapbi=array('isbn'=>$book['isbn']);
                $bookid=array(
                    'book_id'=>$book['book_id'],
                    'username'=>cookie('username')
                );
                $bookin=$bi->where($mapbi)->find();
                $bookatten=$atten->where($bookid)->find();
                if($bookatten){
                    $booklist[$id]['bookatten']=1;
                }else{
                    $booklist[$id]['bookatten']=0;
                }
                $booklist[$id]['bookinfo']=$bookin;
                // print_r($book);
            }
            $this->flag = 1;
        }elseif($flag == 1){
            $status = Array(
                'status'=> array('not in',array(2,4,5))
            );
            $count = M('book')->where($status)->count();
            $page = new \Think\Page($count ,9);
            $limit = $page->firstRow . ',' . $page->listRows;
            $booklist = M('book')->where($status)->order('book_id DESC')->limit($limit)->select();
//        $booklist=$m->order('book_id desc')->select();
//        $this->board = $board;
            $this->page = $page->show();
            $bi=M('bookinfo');
            foreach ($booklist as $id=>$book) {
                $mapbi=array(
                    'isbn'=>$book['isbn'],
                );
                $bookid=array(
                    'book_id'=>$book['book_id'],
                    'username'=>cookie('username')
                );
                $bookin=$bi->where($mapbi)->find();
                $bookatten=$atten->where($bookid)->find();
                if($bookatten){
                    $booklist[$id]['bookatten']=1;
                }else{
                    $booklist[$id]['bookatten']=0;
                }
                $booklist[$id]['bookinfo']=$bookin;
                // print_r($book);
            }
            $this->flag = 0;
        }
        $this->booklist=$booklist;
        // print_r($booklist);
        $this->display();
    }
    public function attention(){
//        $atte = I("attention");
        $atte = $_POST['val'];
        $book_id = $_POST['book_id'];
        $username = cookie('username');
//        $book_id = I("bookid");
        $attention = M('bookattention');
        $data = Array(
            "book_id"=>$book_id,
            "username"=>$username
        );
//        $data['book_id']=$book_id;
//        $data['username']=$username;
        if($atte == "关注"){
            $attention->add($data);
        }elseif($atte == "已关注"){
            $attention->where($data)->delete();
        }
//        $this->redirect(share);
    }
    public function squre(){
        passonCount('squre');
        userStates('squre');
        $m=M('book');
        $count = M('book')->count();
        $page = new \Think\Page($count ,4);
        $limit = $page->firstRow . ',' . $page->listRows;
        $booklist = M('book')->order('book_id DESC')->limit($limit)->select();
//        $booklist=$m->order('book_id desc')->select();
        $this->page = $page->show();
        $bi=M('bookinfo');
//        $a= "借阅";
        foreach ($booklist as $id=>$book) {
            $mapbi=array('isbn'=>$book['isbn']);
            $bookin=$bi->where($mapbi)->find();
            $booklist[$id]['bookinfo']=$bookin;
//            if($book['status']==1){
//                $book['status']=$a;
//            }
            // print_r($book);
        }
            $this->booklist=$booklist;
        $this->display();
    }
    public function mysqlUser(){
        $username =  I("user");
        $password =  I("password");
        $phonenum =  I("phonenum");
        $address =  I("address");
        $password = md5($password);
        $m=M('book_user_info');
        $map=array('username'=>$username);
        $mapp=array('phone'=>$phonenum);
        $data = $m->where($map)->find();
        $data2 = $m->where($mapp)->find();
        print_r($data2);
        if ($data) {
            $this->error('用户名已存在，请重新注册',U(register),2);
        }else if($data2){
            $this->error('手机号已绑定其它用户名，请重新注册',U(register),20);
        }else{
            $data=array(
                "username"=>$username,
                "password"=>$password,
                'phone'=>$phonenum,
                'addr'=>$address,
                'flag'=>1
            );
            if($m->add($data)){
                $this->success("注册成功",U(login),3);
            }else{
                $this->error("注册失败",U(register),3);
            }
        }

//        $this->success("成功",U(index),5);
//        $this->redirect(login);
    }
    public function shareMysqlUser(){
        $username =  I("user");
        $password =  I("password");
        $phonenum =  I("phonenum");
        $address =  I("address");
        $password = md5($password);
        $m=M('book_user_info');
        $map=array('username'=>$username);
        $mapp=array('phone'=>$phonenum);
        if ($m->where($map)->find()) {
            $this->error('用户名已存在，请重新注册',U(shareRegister),2);
        }else if($m->where($mapp)->find()){
            $this->error('手机号已绑定其它用户名，请重新注册',U(shareRegister),2);
        }else{
            $data=array(
                "username"=>$username,
                "password"=>$password,
                'phone'=>$phonenum,
                'addr'=>$address,
                'flag'=>1
            );
            if($m->add($data)){
                $this->success("注册成功",U(shareLogin),3);
            }else{
                $this->error("注册失败",U(shareRegister),3);
            }
        }

//        $this->success("成功",U(index),5);
//        $this->redirect(login);
    }
    public function bookinfoMysqlUser(){
        $username =  I("user");
        $password =  I("password");
        $phonenum =  I("phonenum");
        $book_id =  $_GET["book_id"];
        $address =  I("address");
        $password = md5($password);
        $m=M('book_user_info');
        $map=array('username'=>$username);
        $mapp=array('phone'=>$phonenum);
        $data = $m->where($map)->find();
        $data2 = $m->where($mapp)->find();
        if ($data) {
            $this->error('用户名已存在，请重新注册',U(bookinfoRegister,array('book_id'=>$book_id)),2);
        }else if($data2){
            $this->error('手机号已绑定其它用户名，请重新注册',U(bookinfoRegister,array('book_id'=>$book_id)),2);
        }else{
            $data=array(
                "username"=>$username,
                "password"=>$password,
                'phone'=>$phonenum,
                'addr'=>$address,
                'flag'=>1
            );
            if($m->add($data)){
                $this->success("注册成功",U(bookinfoLogin,array('book_id'=>$book_id)),3);
            }else{
                $this->error("注册失败",U(bookinfoRegister,array('book_id'=>$book_id)),3);
            }
        }

//        $this->success("成功",U(index),5);
//        $this->redirect(login);
    }
    public function commentMysqlUser(){
        $username =  I("user");
        $password =  I("password");
        $phonenum =  I("phonenum");
        $book_id =  $_GET["book_id"];
        $address =  I("address");
        $password = md5($password);
        $m=M('book_user_info');
        $map=array('username'=>$username);
        $mapp=array('phone'=>$phonenum);
        $data = $m->where($map)->find();
        $data2 = $m->where($mapp)->find();
        if ($data) {
            $this->error('用户名已存在，请重新注册',U(commentRegister,array('book_id'=>$book_id)),2);
        }else if($data2){
            $this->error('手机号已绑定其它用户名，请重新注册',U(commentRegister,array('book_id'=>$book_id)),2);
        }else{
            $data=array(
                "username"=>$username,
                "password"=>$password,
                'phone'=>$phonenum,
                'addr'=>$address,
                'flag'=>1
            );
            if($m->add($data)){
                $this->success("注册成功",U(commentLogin,array('book_id'=>$book_id)),3);
            }else{
                $this->error("注册失败",U(commentRegister,array('book_id'=>$book_id)),3);
            }
        }

//        $this->success("成功",U(index),5);
//        $this->redirect(login);
    }
    public function login_user(){
        $username =  I("user");
        $password =  I("password");
        $password =  md5($password);
        $m=M('book_user_info');
        $data=array("username"=>$username,
                    "password"=>$password
                    );
        $data = $m->where($data)->select();
        $data1 = $m->where($data)->find();
            if(!$data){
                $this->error("登录失败!",U(login),3);
            }
            else{
                cookie("username",$data1["username"],time()+60*60*24*30 ,'/');
                $this->redirect('../Home/index');
//                $this->success("成功",U(index),50);
            }
    }
    public function shareLogin_user(){
        $username =  I("user");
        $password =  I("password");
        $password =  md5($password);
        $m=M('book_user_info');
        $data=array("username"=>$username,
            "password"=>$password
        );
        $data = $m->where($data)->select();
        $data1 = $m->where($data)->find();
        if(!$data){
            $this->error("登录失败!",U(shareLogin),3);
        }
        else{
            cookie("username",$data1["username"],time()+60*60*24*30 ,'/');
            $this->redirect(share);
//                $this->success("成功",U(index),50);
        }
    }
    public function bookcenter(){
    	// $hello="你好，我的世界 ！";
    	// $this->hello=$hello;
        // getBookByIsbn('9787111380931');
        // print_r($array);
        if (cookie('username')) {
            echo "用户：".cookie('username');
        }else{
            echo "您并没有登录，请登录";
        }
    	$this->display();
    	// $members=M("members");
    	// $user=$members->where('uid=1')->find();
    	// print_r($user['password']);
    	// echo '<br>'.md5('newcz123');
        //$value=cookie();
        // print_r($value);
    	// echo Wekit::getLoginUser();
        // echo "会不会乱编";

    }
    //登陆检测，如果已经登陆就无再登陆

    //注销
    public function logout(){
        passonCount('logout');
        userStates('logout');
        if(cookie('username')){
            cookie('username',null);
            $this->redirect(index);
        }else{
            $this->redirect(index);
        }
    }
    public function contribute(){
        passonCount('contribute');
        userStates('contribute');
        $username = cookie('username');
        if($username == "" || $username == null){
            $this->error("您未登录!",U(index),3);
        }else{
            $this->display();
        }
    }
    //贡献的界面 会检测当前用户
    public function contribute1(){
        if(cookie('username')){
            // echo cookie('uid');
             $this->display();
         }else{
            $this->redirect(index);
        }
    }
    //处理贡献的界面
    public function doContribute(){
       // print_r(I());
       $isbn=I('isbn');
       $con_name=I('con_name');
       $username=cookie('username');
       $time=date('Y-m-d l h:i:s A',time());
       $data=array('owner_name'=>$username,
                   'keeper_name'=>$username,
                    'isbn' =>$isbn,
                    'status'=>1,
                    'contribute_time'=>$time,
                    'con_name'=>$con_name
                    );
       $m=M('book');
        //////////////积分
        $honer = M('book_honer');
        $result = $honer->where(Array('username'=>$username))->find();
        $datahoner = Array(
            'username'=>$username,
            'integral'=>intval($result['integral'])+10
        );
        if($result){
            $honer->where(Array('username'=>$username))->save($datahoner);
        }else{
            $honer->add($datahoner);
        }
        /////
       if ($m->add($data)) {
          $this->redirect(personshare);
       }else{
          $this->redirect(personshare);
       }
    }
    //////////二维码
    public function qr(){
        passonCount('qr');
        userStates('qr');
        $book_id=I('book_id');
        // print_r(I());
        $book=M('book')->field('isbn,owner_name,con_name')->where(array('book_id'=>$book_id))->find();
        $title=M('bookinfo')->field('title')->where(array('isbn'=>$book[isbn]))->find();
        // print_r($book['owner_name']);
        // print_r($title['title']);
        if ($book['con_name']!="") {
            $imo=getQr($book_id,$book['con_name'],$title['title']);
        }else{
            $imo=getQr($book_id,$book['owner_name'],$title['title']);
        }
        header("Content-type: image/png");
        imagepng( $imo ,'Public/bookimgs/'.$book_id.'.png');
        $this->book_id=$book_id;
        $this->display();
    }
    //物品中心
    public function all(){
       $m=M('book');
       $booklist=$m->order('book_id desc')->select();
       $bi=M('bookinfo');
       foreach ($booklist as $id=>$book) {
          $mapbi=array('isbn'=>$book['isbn']);
          $bookin=$bi->where($mapbi)->find();
          $booklist[$id]['bookinfo']=$bookin;
          // print_r($book);
       }
       $this->booklist=$booklist;
       // print_r($booklist);
       $this->display();
    }
    //我的贡献
    public function myContributes(){
        passonCount('myContributes');
        userStates('myContributes');
        if(cookie('username')){
           $m=M('book');
           $map=array('owner_name'=>cookie('username'));
           $mybooks=$m->where($map)->order('book_id desc')->select();
           $bi=M('bookinfo');
        foreach ($mybooks as $id=>$book) {
           $mapbi=array('isbn'=>$book['isbn']);
           $bookin=$bi->where($mapbi)->find();
           $mybooks[$id]['bookinfo']=$bookin;
           // print_r($book);
        }
           $this->mybooks=$mybooks;
           $this->display();
         }else{
            $this->redirect(index);
        }

    }
    //我的保管
    public function myKeeps(){
        passonCount('myKeeps');
        userStates('myKeeps');
        if(cookie('username')){
            $m=M('book');
            $usermessage=M('book_user_info');
//            $mykeeps = M('book')->order('book_id DESC')->limit($limit)->select();
//        $booklist=$m->order('book_id desc')->select();
//        $this->board = $board;
            $user = $usermessage->where(Array("username" => cookie('username')))->find();
            $map=array(
               'keeper_name'=>cookie('username'),
               'status'=>2
           );
            $count=$m->where($map)->count();
            $page = new \Think\Page($count ,2);
            $limit = $page->firstRow . ',' . $page->listRows;
           $mykeeps=$m->where($map)->order('book_id desc')->limit($limit)->select();
//            $count = count($mykeeps);
//            $page = new \Think\Page($count ,2);
           $bi=M('bookinfo');
            $log = M('booklog');
            foreach ($mykeeps as $id=>$book) {
               $mapbi=array('isbn'=>$book['isbn']);
               $bookin=$bi->where($mapbi)->find();
                $bookid = Array(
                    'keeper_name'=>cookie('username'),
                    'book_id'=>$book['book_id'],
                    'status'=> array('not in',array(3,4))
                );
                $log1=$log->where($bookid)->order('log_id desc')->limit(1)->find();
               $mykeeps[$id]['bookinfo']=$bookin;
               $mykeeps[$id]['booklog']=$log1;

            }
            $booklog = Array(
                'keeper_name'=>cookie('username')
            );
            $booklog = $log->where($booklog)->order('log_id desc')->select();
            foreach($booklog as $key=>$value){
                $bookid = Array(
                    "book_id"=>$value['book_id']
                );
                $bookmessage = $m->where($bookid)->find();
                $isbn = Array(
                    'isbn'=>$bookmessage['isbn']
                );
                $booktitle = $bi->where($isbn)->find();
                $booklog[$key]['booklog']=$booktitle;
            }
            //////////////积分
            $honer = M('book_honer');
            $result = $honer->where(Array('username'=>cookie('username')))->find();
            if($result){
                $this->integral=$result['integral'];
            }else{
                $this->integral=0;
            }
            /////
            ///分享书本数
            $datanum = $m->where(Array('owner_name'=>cookie('username')))->select();
            if($datanum){
                $this->booknum=count($datanum);
            }else{
                $this->booknum=0;
            }
            ///
            $this->booklog=$booklog;
            $this->page = $page->show();
            $this->mykeeps=$mykeeps;
            $this->user=$user;
            $this->display();
         }else{
            $this->redirect(index);
        }
    }
    public function bookorder(){
        passonCount('bookorder');
        userStates('bookorder');
        if(cookie('username')){
            $m=M('book');
            $bookorder=M('bookorder');
            $usermessage=M('booksusers');

            $user = $usermessage->where(Array("username" => cookie('username')))->find();
            $map=array(
                'ordername'=>cookie('username')
            );
            $count=$bookorder->where($map)->count();
            $page = new \Think\Page($count ,2);
            $limit = $page->firstRow . ',' . $page->listRows;
            $mykeep=$bookorder->where($map)->order('order_id desc')->limit($limit)->select();
            
            $bi=M('bookinfo');
            $log = M('booklog');
            $array1 = [];
            foreach($mykeep as $key=>$order){
                $ordername = Array('book_id'=>$order['book_id']);
                $mykeeps = $m->where($ordername)->find();
                array_push($array1,$mykeeps);
            }
            foreach ($array1 as $id=>$book) {
                $mapbi=array('isbn'=>$book['isbn']);
                $bookin=$bi->where($mapbi)->find();
                $bookid = Array(
                    'keeper_name'=>cookie('username'),
                    'book_id'=>$book['book_id'],
                    'status'=> 4
                );
                $log1=$log->where($bookid)->order('log_id desc')->limit(1)->find();
                $array1[$id]['bookinfo']=$bookin;
                $array1[$id]['booklog']=$log1;
            }

            $booklog = Array(
                'keeper_name'=>cookie('username')
            );
            $booklog = $log->where($booklog)->order('log_id desc')->select();
            foreach($booklog as $key=>$value){
                $bookid = Array(
                    "book_id"=>$value['book_id']
                );
                $bookmessage = $m->where($bookid)->find();
                $isbn = Array(
                    'isbn'=>$bookmessage['isbn']
                );
                $booktitle = $bi->where($isbn)->find();
                $booklog[$key]['booklog']=$booktitle;
            }
            //////////////积分
            $honer = M('book_honer');
            $result = $honer->where(Array('username'=>cookie('username')))->find();
            if($result){
                $this->integral=$result['integral'];
            }else{
                $this->integral=0;
            }
            /////
            ///分享书本数
            $datanum = $m->where(Array('owner_name'=>cookie('username')))->select();
            if($datanum){
                $this->booknum=count($datanum);
            }else{
                $this->booknum=0;
            }
            ///
            $this->booklog=$booklog;
            $this->page = $page->show();
            $this->mykeeps=$array1;
            $this->user=$user;
            $this->display();
        }else{
            $this->redirect(index);
        }
    }
    //////保管中
    public function bookkeeps(){
        passonCount('bookkeeps');
        userStates('bookkeeps');
        if(cookie('username')){
            $m=M('book');
            $usermessage=M('book_user_info');
//            $mykeeps = M('book')->order('book_id DESC')->limit($limit)->select();
//        $booklist=$m->order('book_id desc')->select();
//        $this->board = $board;

            $user = $usermessage->where(Array("username" => cookie('username')))->find();
            $map=array(
                'keeper_name'=>cookie('username'),
                'status'=>3
            );
            $count=$m->where($map)->count();
            $page = new \Think\Page($count ,2);
            $limit = $page->firstRow . ',' . $page->listRows;
            $mykeeps=$m->where($map)->order('book_id desc')->limit($limit)->select();
//            $count = count($mykeeps);
//            $page = new \Think\Page($count ,2);
            $bi=M('bookinfo');
            $log = M('booklog');
            foreach ($mykeeps as $id=>$book) {
                $mapbi=array('isbn'=>$book['isbn']);
                $bookin=$bi->where($mapbi)->find();
                $bookid = Array(
                    'keeper_name'=>cookie('username'),
                    'book_id'=>$book['book_id'],
                    'status'=> 3
                );
                $log1=$log->where($bookid)->order('log_id desc')->limit(1)->find();
                $mykeeps[$id]['bookinfo']=$bookin;
                $mykeeps[$id]['booklog']=$log1;
            }
            $booklog = Array(
                'keeper_name'=>cookie('username')
            );
            $booklog = $log->where($booklog)->order('log_id desc')->select();
            foreach($booklog as $key=>$value){
                $bookid = Array(
                    "book_id"=>$value['book_id']
                );
                $bookmessage = $m->where($bookid)->find();
                $isbn = Array(
                    'isbn'=>$bookmessage['isbn']
                );
                $booktitle = $bi->where($isbn)->find();
                $booklog[$key]['booklog']=$booktitle;
            }
            //////////////积分
            $honer = M('book_honer');
            $result = $honer->where(Array('username'=>cookie('username')))->find();
            if($result){
                $this->integral=$result['integral'];
            }else{
                $this->integral=0;
            }
            /////
            ///分享书本数
            $datanum = $m->where(Array('owner_name'=>cookie('username')))->select();
            if($datanum){
                $this->booknum=count($datanum);
            }else{
                $this->booknum=0;
            }
            ///
            $this->booklog=$booklog;
            $this->page = $page->show();
            $this->mykeeps=$mykeeps;
            $this->user=$user;
            $this->display();
        }else{
            $this->redirect(index);
        }
    }
    //物品的主页
    public function bookInfo(){
        passonCount('bookInfo');
        userStates('bookInfo');
        $m=M('book');
        $user = M('book_user_info');
//        $booklist=$m->order('book_id desc')->select();
        $book_id=intval(I('bookid'));
        $map=array('book_id'=>$book_id);
        $book=$m->where($map)->select();
        $book1=$m->where($map)->find();
        $book2=$m->where($map)->find();
        $bi=M('bookinfo');
        $log = M('booklog');
        $s = $book2['isbn'];
        $b=getArrayByIsbn($s);
        $mapbi=array('isbn'=>$book2['isbn']);

        //看过此书的人还看过那些书
//        $logdata = Array(
//            "book_id"=>$book_id,
//            "status"=>2
//        );
//        $booklog = $log->Distinct(true)->field('keeper_name')->where($logdata)->select();
        $booklog2 = $log->where($map)->order('log_id desc')->select();
//        $array = [];
//        foreach( $booklog as $key =>$value){
//            $keepname = Array(
//                "keeper_name"=>$value['keeper_name'],
//                "status"=>2,
//                "book_id"=> array('not in',"$book_id")
//            );
//            $booklist = $log->Distinct(true)->field('book_id')->where($keepname)->select();
//            foreach($booklist as $key => $value){
//                $bookid = Array(
//                    "book_id"=>$value['book_id']
//                );
//                $bookisbn = $m->Distinct(true)->field('isbn')->where($bookid)->select();
//                foreach($bookisbn as $key => $value){
//                    $bookinfo = Array(
//                        "isbn"=>$value['isbn']
//                    );
//                    $bookinfo2 = $bi->where($bookinfo)->find();
//                    array_push($array,$bookinfo2);
//                }
//            }
//        }
//        $result=array();
//        for($i=0;$i<count($array);$i++){
//            $source=$array[$i];
//            if(array_search($source,$array)==$i && $source<>"" ){
//                $result[]=$source;
//            }
//        }
//        $this->bookinfo2= $result;

        //分享这本书的人还分享过那些书
        $array=[];
        $logdata = Array(
            "book_id"=>$book_id,
        );
        $booklog = $m->where($logdata)->find();
        $owner_name = Array(
            'owner_name'=>$booklog['owner_name']
        );
        $bookmessage = $m->where($owner_name)->select();
        foreach($bookmessage as $key => $value){
                    $bookinfo = Array(
                        "isbn"=>$value['isbn']
                    );
                    $bookinfo2 = $bi->where($bookinfo)->find();
                    array_push($array,$bookinfo2);
                }
        $this->bookinfo2= $array;
///////////////////////////////////////////////////

        //加载评论
        $comment = M('bookcomment');
        $num = M('bookmnum');
        $cComment = M('book_comment');
        $book_id=intval(I('bookid'));
        $commentmessage = Array(
            'book_id'=>$book_id,
        );
        $data = $comment->where($commentmessage)->select();
        $datanum = $num->where($commentmessage)->find();
        foreach($data as $key=>$value) {   //循环读取
            $comdata = Array(
                'comment_id' => $value['id']
            );
            $data2 = $cComment->where($comdata)->select();
            $data[$key]['comment1'] = $data2;
        }
        $this->bookcomment=$data;
        if(!$datanum){
            $this->num=0;
        }else{
            $this->num=$datanum['comment_num'];
        }
        /////////////////////////
        /////关注
        $bookatten = M('bookattention');
        $bookat = Array(
            'book_id'=>$book_id,
            'username'=>cookie('username')
        );
        $result = $bookatten->where($bookat)->find();
        if($result){
            $this->result=1;
        }else{
            $this->result=0;
        }
        ////
        $usermessage = $user->where(Array("username"=>$book1['keeper_name']))->find();
        $bookin=$bi->where($mapbi)->find();
        if ($book['owner_name']==cookie('username')) {
            $this->flag=1;
        }else{$this->flag=0;}
        $this->user = $usermessage;
        $this->booklist=$book;
        $this->book=$book1;
        $this->bookin=$bookin;
        $this->bookinfo=$b;

        $this->booklog=$booklog2;
        $website="http://www.makerway.space/passon/index.php/Home/Index/borrow/bookid/".$map['book_id'].".html";
        $this->website=$website;
        $this->display();
    }
    public function bookinfoLogin(){
        passonCount('login');
        userStates('login');
        $book_id = I('book_id');
        $this->book_id=intval($book_id);
        $this->display();
    }
    public function commentLogin(){
        passonCount('login');
        userStates('login');
        $book_id = I('book_id');
        $this->book_id=intval($book_id);
        $this->display();
    }
    public function bookinfoRegister(){
        passonCount('register');
        userStates('register');
        $book_id = I('book_id');
        $this->book_id=intval($book_id);
        $this->display();
    }
    public function commentRegister(){
        passonCount('register');
        userStates('register');
        $book_id = I('book_id');
        $this->book_id=intval($book_id);
        $this->display();
    }
    public function bookinfoLogin_user(){
        $username =  I("user");
        $password =  I("password");
        $password =  md5($password);
        $book_id =  $_GET['book_id'];
        $book_id =  intval($book_id);
        $m=M('book_user_info');
        $data=array(
            "username"=>$username,
            "password"=>$password
        );
        $data = $m->where($data)->select();
        $data1 = $m->where($data)->find();
        if(!$data){
            $this->error("登录失败!",U(bookinfoLogin,array('book_id'=>$book_id)),3);
        }
        else{
            cookie("username",$data1["username"],time()+60*60*24*30 ,'/');
            $this->redirect(bookinfo,array('bookid'=>$book_id));
//                $this->success("成功",U(index),50);
        }
    }
    public function commentLogin_user(){
        $username =  I("user");
        $password =  I("password");
        $password =  md5($password);
        $book_id =  $_GET['book_id'];
        $book_id =  intval($book_id);
        $m=M('book_user_info');
        $data=array(
            "username"=>$username,
            "password"=>$password
        );
        $data = $m->where($data)->select();
        $data1 = $m->where($data)->find();
        if(!$data){
            $this->error("登录失败!",U(commentLogin,array('book_id'=>$book_id)),3);
        }
        else{
            cookie("username",$data1["username"],time()+60*60*24*30 ,'/');
            $this->redirect(comment,array('bookid'=>$book_id));
//                $this->success("成功",U(index),50);
        }
    }
    public function comment(){
        passonCount('comment');
        userStates('comment');
        $m=M('book');
        $user = M('book_user_info');
//        $booklist=$m->order('book_id desc')->select();
        $book_id=intval(I('bookid'));
        $map=array('book_id'=>$book_id);
        $book=$m->where($map)->select();
        $book1=$m->where($map)->find();
        $book2=$m->where($map)->find();
        $bi=M('bookinfo');
        $log = M('booklog');
        $s = $book2['isbn'];
        $b=getArrayByIsbn($s);
        $mapbi=array('isbn'=>$book2['isbn']);

        //看过此书的人还看过那些书
//        $logdata = Array(
//            "book_id"=>$book_id,
//            "status"=>2
//        );
//        $booklog = $log->Distinct(true)->field('keeper_name')->where($logdata)->select();
        $booklog2 = $log->where($map)->order('log_id desc')->select();
//        $array = [];
//        foreach( $booklog as $key =>$value){
//            $keepname = Array(
//                "keeper_name"=>$value['keeper_name'],
//                "status"=>2,
//                "book_id"=> array('not in',"$book_id")
//            );
//            $booklist = $log->Distinct(true)->field('book_id')->where($keepname)->select();
//            foreach($booklist as $key => $value){
//                $bookid = Array(
//                    "book_id"=>$value['book_id']
//                );
//                $bookisbn = $m->Distinct(true)->field('isbn')->where($bookid)->select();
//                foreach($bookisbn as $key => $value){
//                    $bookinfo = Array(
//                        "isbn"=>$value['isbn']
//                    );
//                    $bookinfo2 = $bi->where($bookinfo)->find();
//                    array_push($array,$bookinfo2);
//                }
//            }
//        }
//        $result=array();
//        for($i=0;$i<count($array);$i++){
//            $source=$array[$i];
//            if(array_search($source,$array)==$i && $source<>"" ){
//                $result[]=$source;
//            }
//        }
//        $this->bookinfo2= $result;

        //分享这本书的人还分享过那些书
        $array=[];
        $logdata = Array(
            "book_id"=>$book_id,
        );
        $booklog = $m->where($logdata)->find();
        $owner_name = Array(
            'owner_name'=>$booklog['owner_name']
        );
        $bookmessage = $m->where($owner_name)->select();
        foreach($bookmessage as $key => $value){
            $bookinfo = Array(
                "isbn"=>$value['isbn']
            );
            $bookinfo2 = $bi->where($bookinfo)->find();
            array_push($array,$bookinfo2);
        }
        $this->bookinfo2= $array;
///////////////////////////////////////////////////
        $book_zan = M('book_zan');
        //加载评论和赞数
        $comment = M('bookcomment');
        $num = M('bookmnum');
        $cComment = M('book_comment');
        $commentnum = M('book_commentnum');
        $book_id=intval(I('bookid'));
        $commentmessage = Array(
            'book_id'=>$book_id,
        );
        $data = $comment->where($commentmessage)->select();
        $datanum = $num->where($commentmessage)->find();
        foreach($data as $key=>$value) {   //循环读取
            $comdata = Array(
                'comment_id' => $value['id']
            );
            $zan_data = Array(
                'username'=>cookie('username'),
                'comment_id' => $value['id']
            );
            $data2 = $cComment->where($comdata)->select();
            $data3 = $commentnum->where($comdata)->find();
            $data4 = $book_zan->where($zan_data)->find();
            $data[$key]['comment1'] = $data2;
            $data[$key]['commentnum'] = $data3;
            $data[$key]['commentzan'] = $data4;
        }
        $this->bookcomment=$data;
        if(!$datanum){
            $this->num=0;
        }else{
            $this->num=$datanum['comment_num'];
        }
        /////////////////////////
        /////关注
        $bookatten = M('bookattention');
        $bookat = Array(
            'book_id'=>$book_id,
            'username'=>cookie('username')
        );
        $result = $bookatten->where($bookat)->find();
        if($result){
            $this->result=1;
        }else{
            $this->result=0;
        }
        ////
        //书的评分
        $bookfen = M('bookfen');
        $fen = Array(
            'username'=>cookie('username'),
            'book_id'=>$book_id
        );
        $result = $bookfen->where($fen)->find();
        if($result){
            $this->flag1=1;
            $this->fen=$result;
        }else{
            $this->flag1=0;
        }
        /////
        ///赞同
        //
        $usermessage = $user->where(Array("username"=>$book1['keeper_name']))->find();
        $bookin=$bi->where($mapbi)->find();
        if ($book['owner_name']==cookie('username')) {
            $this->flag=1;
        }else{$this->flag=0;}
        $this->user = $usermessage;
        $this->booklist=$book;
        $this->book=$book1;
        $this->bookin=$bookin;
        $this->bookinfo=$b;
        $this->booklog=$booklog2;
        $website="http://www.makerway.space/passon/index.php/Home/Index/borrow/bookid/".$map['book_id'].".html";
        $this->website=$website;
        $this->display();
    }
    public function commentnum(){
        $comment_id = $_POST['comment_id'];
        $num = $_POST['num'];
        $val= $_POST['val'];
        $book_zan = M('book_zan');
        $comment = M('book_commentnum');
        $data = Array(
            'comment_id'=>$comment_id,
            'comment_zan'=>intval($num)+1
        );
        $data1 = Array(
            'comment_id'=>$comment_id,
            'comment_zan'=>intval($num)-1
        );
        $data2 = Array(
            'comment_id'=>$comment_id,
            'username'=>cookie('username'),
            'flag'=>1
        );
        $data3 = Array(
            'comment_id'=>$comment_id,
            'username'=>cookie('username'),
            'flag'=>0
        );
        $result = $comment->where(Array('comment_id'=>$comment_id))->find();
        $result2 = $book_zan->where(Array('comment_id'=>$comment_id,'username'=>cookie('username')))->find();
        if($val == ""){
            if($result){
                $comment->where(Array('comment_id'=>$comment_id))->save($data);
            }else{
                $comment->add($data);
            }
            if($result2){
                $book_zan->where(Array('comment_id'=>$comment_id,'username'=>cookie('username')))->save($data2);
            }else{
                $book_zan->add($data2);
            }
        }elseif($val == "取消赞同"){
            $book_zan->where(Array('comment_id'=>$comment_id,'username'=>cookie('username')))->save($data3);
            $comment->where(Array('comment_id'=>$comment_id))->save($data1);
        }
    }
    public function bookfen(){
        $fen = $_POST['fen'];
        $book_id = $_POST['book_id'];
        $username = cookie('username');
        $bookfen = M('bookfen');
        $data = Array(
            'username'=>$username,
            'fen'=>$fen,
            'book_id'=>$book_id
        );
        if($fen!=''&& $fen!=null){
            $data1 = $bookfen->where(Array('username'=>$username,'book_id'=>$book_id))->find();
            if($data1){
                $bookfen->where(Array('username'=>$username,'book_id'=>$book_id))->save($data);
            }else{
                $bookfen->add($data);
            }
        }
    }
    public function bookcomment(){
        $comment = M('bookcomment');
        $num = M('bookmnum');
        $book_id=intval(I('bookid'));
        $username = cookie('username');
        $textarea=I('textarea');
        $commentmessage = Array(
            'book_id'=>$book_id,
            'username'=>$username,
            'comment'=>$textarea,
            'time'=>date('Y-m-d l h:i:s A',time())
        );
        $datanum = $num->where(Array('book_id'=>$book_id))->find();
        $commentnum = Array(
            'book_id'=>$book_id,
            'comment_num'=>intval($datanum['comment_num'])+1
        );
        $data = $comment->add($commentmessage);
        if(!$datanum){
            $num->add($commentnum);
        }else{
            $num->where(array('book_id'=>$book_id))->save($commentnum);
        }
        if($data){
            $this->redirect(comment,array('bookid'=>$book_id));
        }
    }
    public function cComment()
    {
        $comment = M('book_comment');
        $num = M('book_commentnum');
        $comment_id = I('comment');
        $book_id = intval(I('bookid'));
        $input = I('input');
        $data = Array(
            'comment_id' => $comment_id,
            'username' => cookie('username'),
            'comment' => $input,
            'time' => date('Y-m-d l h:i:s A', time())
        );
        if($input){
            $commentnum = 1;
        }else{
            $commentnum = 0;
        }
        $datanum1 = $num->where(Array('comment_id'=>$comment_id))->find();
        $datanum =Array(
            'comment_id'=>$comment_id,
            'comment_num'=>$commentnum+intval($datanum1['comment_num'])
        );
        if(!$datanum1){
            $num->add($datanum);
        }else{
            $num->where(Array('comment_id'=>$comment_id))->save($datanum);
        }
        if($input){
            $data = $comment->add($data);
        }
        if($data){
            $this->redirect(comment,array('bookid'=>$book_id));
        }
    }
    //借书的页面，在这里真写借书信息
//    public function borrow(){
//        // print_r(I('bookid'));
//        $m=M('book');
//        $map=array('book_id'=>I('bookid'));
//        $book=$m->where($map)->find();
//        $bi=M('bookinfo');
//        $mapbi=array('isbn'=>$book['isbn']);
//        $bookin=$bi->where($mapbi)->find();
//        $this->book=$book;
//         $this->bookin=$bookin;
//        $this->display();
//    }
    //手机端登录页面
    public function borrow(){
        passonCount('borrow');
        userStates('borrow');
        $m=M('book');
        $bookid = I('bookid');
        cookie("bookid",$bookid,time()+60*60*24*30);
//        print_r($bookid);
        $bookid = cookie('bookid');
//        print_r($bookid);
        $map=array('book_id'=>$bookid);
        $book=$m->where($map)->find();
        $bi=M('bookinfo');
        $mapbi=array('isbn'=>$book['isbn']);
        $bookin=$bi->where($mapbi)->find();
        $this->book=$book;
        $this->bookin=$bookin;
        $this->display();
    }
    public function phoneRegister(){
        passonCount('phoneRegister');
        userStates('phoneRegister');
        $this->display();
    }
    public function phoneForget(){
        passonCount('forget');
        userStates('forget');
        $this->display();
    }
    public function pRegister(){
        $username =  I("username");
        $password =  I("password");
        $phonenum = I("phonenum");
        $address =  I("address");
        $password = md5($password);
        $m=M('book_user_info');
        $map=array('username'=>$username);
        $mapp=array('phone'=>$phonenum);
        if ($m->where($map)->find()) {
            $this->error('用户名已存在，请重新注册',U(phoneRegister),2);
        }else if($m->where($mapp)->find()){
            $this->error('手机号已绑定其它用户名，请重新注册',U(phoneRegister),2);
        }else{
            $data=array(
                "username"=>$username,
                "password"=>$password,
                'phone'=>$phonenum,
                'addr'=>$address,
                'flag'=>1
            );
            if($m->add($data)){
                $this->success("注册成功",U(phonelogin),3);
            }else{
                $this->error("注册失败",U(phoneRegister),3);
            }
        }
    }
    public function pForget(){
        $username =  I("username");
        $password =  I("password");
        $password =  md5($password);
        $phonenum = I("phonenum");
        $m=M('book_user_info');
        $data=array(
            "username"=>$username,
            "password"=>$password,
            "phonenum"=>$phonenum
        );
        $name = Array(
            "username"=>$username
        );
        $datanum = $m->where($name)->find();
        $data = $m->where($name)->save($data);
        if($datanum){
            if($data){
                $this->success("修改成功",U(phoneLogin),3);
            }else{
                $this->error("修改失败",U(phoneForget),3);
            }
        }else{
            $this->error("用户名不存在",U(phoneRegister),3);
        }
    }
    public function pLogin(){
        $username =  I("username");
        $password =  I("password");
        $password =  md5($password);
        cookie("username",$username,time()+60*60*24*30);
        $m=M('book_user_info');
        $data=array("username"=>$username,
            "password"=>$password
        );
        $data = $m->where($data)->select();
//        $data1 = $m->where($data)->find();
        if(!$data){
            $this->error("登录失败",U(phoneLogin),3);
//            $this->redirect(phoneLogin);
        }
        else{
            $this->redirect(phoneMain);
//            $this->success("登录成功",U(phoneMain),3);
//                $this->success("成功",U(index),50);
        }
    }
    //手机借书的主界面
    public function phoneMain(){
        passonCount('phoneMain');
        userStates('phoneMain');
        // print_r(I('bookid'));
        $m=M('book');
        $bookid = cookie('bookid');
        $username = cookie("username");
//        print_r("ww");
        $map=array('book_id'=>$bookid);
        $book=$m->where($map)->find();
        $bi=M('bookinfo');
        $mapbi=array('isbn'=>$book['isbn']);
        $bookin=$bi->where($mapbi)->find();
//        print_r($book['keeper_name']);
        $this->book=$book;
        $this->bookin=$bookin;
        $this->username=$username;
        $this->display();
    }
    //手机借阅
    public function phoneBorrow(){
        passonCount('phoneBorrow');
        userStates('phoneBorrow');
        // print_r(I('bookid'));
        $this->display();
    }
    //手机还书
    public function phoneReturn(){
        passonCount('phoneReturn');
        userStates('phoneReturn');
        $book_id = cookie('bookid');
        $this->bookid=$book_id;
        // print_r(I('bookid'));
        $this->display();
    }
    //手机续借
    public function phoneRenew(){
        passonCount('phoneRenew');
        userStates('phoneRenew');
        // print_r(I('bookid'));
        $this->display();
    }
    //手机预约
    public function phoneOrder(){
        passonCount('phoneOrder');
        userStates('phoneOrder');
        $booklog = M(booklog);
        $bookorder = M(bookorder);
        $bookordernum =M(bookmnum);
        $book = M(book);
        $bookid = cookie('bookid');
        $username = cookie('username');
        $bookorder1 = $bookorder->where(array('book_id'=>$bookid,"ordername"=>$username))->find();
        $data = $bookordernum->where(array('book_id'=>$bookid))->find();
//        $bookstatus = $book->where(array('book_id'=>$bookid))->find();
        //预约信息待定 存在找出最新的书籍状态（不包括预约信息）
        $message = Array(
            'book_id'=>$bookid,
            'status'=> array('not in','4')
//            'status'=> 2
        );
//        $message1 = Array(
//            'book_id'=>$bookid,
//            'status'=> 5
//        );
//        $message2 = Array(
//            'book_id'=>$bookid,
//            'status'=> 3
//        );
        $datalog = $booklog->where($message)->order('log_id desc')->limit(1)->find();
//        $datalog1 = $booklog->where($message1)->order('log_id desc')->limit(1)->find();
//        $datalog3 = $booklog->where($message2)->order('log_id desc')->limit(1)->find();
//        print_r($datalog);
        if(!$bookorder1){
            if(!$data){
                $data1 = Array(
                    'order_num'=>0
                );
                $this->message = $data1;
            }else{
                $this->message = $data;
            }
            if(!$datalog || ($data['order_num']==0 && $datalog['status'] ==3)){
                $datalog2 = Array(
                    'end_time'=>anytime
                );
                $this->booklog = $datalog2;
            }else if($datalog && $datalog['status']==2){
                $time = strtotime($datalog['end_time']);
                $time1 = $time +$data['order_num']*30*24*3600;
                $data2 = Array(
                    'end_time'=>date('Y-m-d l h:i:s A',$time1)
                );
                $this->booklog = $data2;
            }else if($datalog && $datalog['status']==5){
                $time = strtotime($datalog['end_time']);
                $time1 = $time +$data['order_num']*30*24*3600;
                $data2 = Array(
                    'end_time'=>date('Y-m-d l h:i:s A',$time1)
                );
                $this->booklog = $data2;
            }else if($datalog && $datalog['status']==3){
                $time = strtotime($datalog['end_time']);
                $time1 = $time +$data['order_num']*30*24*3600;
                $data2 = Array(
                    'end_time'=>date('Y-m-d l h:i:s A',$time1)
                );
                $this->booklog = $data2;
            }else{

            }
            $this->display();
        }else{
            $this->error("您已经预约过了！",U(phoneoe),3);
        }


//        $book = M(book);
//        $bookstatus = $book['status'];

//        print_r($datalog);
//        print_r($data['order_num']);

    }
    //书籍破损情况，booklog等
    public function phonedb(){
        $bookid = cookie('bookid');
        $usetime = I('time');
        $damage_s = I('damage');
        $damage_c = I('damage_c');
        $time1 = time();
        $username = cookie('username');
        $time2 = $time1 + intval($usetime)*3600*24;
        cookie("end_time",$time2,time()+60*60*24*30);
        $booklog = M('booklog');
        $context = M('bookcontext');
        $book = M('book');
        $booknum = M('bookmnum');
        // $dataintegral = M('bookintegral');
        $bookorder = M('bookorder');
        $keeper_name = $book->where(array('book_id'=>$bookid))->find();
        $datanum = Array(
            'book_id' => $bookid
        );
        $datanum1 = Array(
            'book_id' => $bookid,
            'ordername'=>$username
        );
        $data = Array(
            'book_id'=> $bookid,
//            'damage_s'=>$damage_s,
            'damage_c'=>$damage_c,
            'username'=>$username,
            'damage_s'=>$damage_s,
            'forword_username'=>$keeper_name['keeper_name']
        );
        // $integral= Array(
        //     'username'=>$keeper_name['keeper_name'],
        //     'integral'=>$damage_s
        // );
        $bookLog = Array(
            'book_id'=> $bookid,
            'keeper_name'=>$username,
            'start_time'=>date('Y-m-d l h:i:s A',$time1),
            'end_time'=>date('Y-m-d l h:i:s A',$time2),
            'status'=>2
        );
        $bookdata=array(
            'book_id'=>$bookid,
            'keeper_name'=>$username,
            'status'=>2
        );
//////////////积分
        $honer = M('book_honer');
        $result = $honer->where(Array('username'=>$username))->find();
        $datahoner = Array(
            'username'=>$username,
            'integral'=>intval($result['integral'])+5
        );
        if($result){
            $honer->where(Array('username'=>$username))->save($datahoner);
        }else{
            $honer->add($datahoner);
        }
        /////
        // $integralnum = $dataintegral->where(Array('username'=>$keeper_name['keeper_name']))->find();
        // if(!$integralnum){
        //     $dataintegral->add($integral);
        // }else{
        //     $dataintegral->save($integral);
        // }
//           $bookuser = M(bookorder);
//            $bookid = cookie('bookid');
//            $username = cookie('username');
//        $data = Array(
//            'book_id'=>$bookid,
//            'keeper_name'=>$username
//        );
//            $userdata = Array(
//                'book_id'=>$bookid,
//                'ordername'=>$username
//            );
//            $datanum = Array(
//                'book_id'=>$bookid
//            );
//        $bookmessage = $booklog->where($data)->select();
            $bookusers = $bookorder->where($datanum1)->find();
            $bookusers1 = $bookorder->where($datanum)->order('order_id asc')->limit(1)->find();
            $bookorders = $booknum->where($datanum)->find();
            if($bookusers){
                $datanew = Array(
                    'book_id'=>$bookid,
                    'order_num'=>$bookorders['order_num']-1
                );
                $bookorder->where($datanum1)->delete();
                $booknum->where(array('book_id'=>$bookid))->save($datanew);
            }


        $borrowlog = $book->where($bookdata)->find();
//        $bookdatanum = $booknum->where($datanum)->find();
//        $bookdataorder = $bookorder->where($datanum1)->find();

        if(!$borrowlog && !$bookorders['order_num'] && !$bookusers){
            $book->where(array('book_id'=>$bookid))->save($bookdata);
            $booklog->add($bookLog);
            $context ->add($data);
            $this->redirect(phoneSuccess);
        }else if(!$borrowlog && $bookorders['order_num'] && $bookusers1['ordername']==$username){
            $book->where(array('book_id'=>$bookid))->save($bookdata);
            $booklog->add($bookLog);
            $context ->add($data);
            $this->redirect(phoneSuccess);
        }else if(!$borrowlog && $bookorders['order_num'] && $bookusers1['ordername']!=$username){
            $this->error("您前面还有预约的用户，请耐心等待！",U(phoneExit),3);
        }else if(!$borrowlog && $bookorders['order_num'] && !$bookusers){
            $this->$this->error("此书有预约的用户，请先去预约才可以借书！",U(phoneExit),3);
        }else{
            $this->error("您已经借阅此书！",U(phoneExit),3);
        }
    }
    //还书等信息
    public function pbookreturn(){
//        $book_zan = I('book_zan');
//        $book_s = I('book_s');
        $book_c = I('book_c');
        if($book_c == null || $book_c == ""){
            $book_s = 0;
        }else{
            $book_s = 1;
        }
        $username = cookie('username');
        $bookid = cookie('bookid');
        $comment = M('bookcomment');
        $num = M('bookmnum');
        $booklog = M('booklog');
        $book = M('book');
        $num1 = $num->where(array('book_id'=>$bookid))->find();
//        $zan_num = $num1['zan_num'];
        $comment_num = $num1['comment_num'];
        $comment_num = $comment_num + $book_s;
//        $zan_num = $zan_num + intval($book_zan);
        $data = Array(
            'book_id'=>$bookid,
//            'zan_num'=>$zan_num,
            'comment_num'=>$comment_num
        );
        $data1 = Array(
            'book_id'=>$bookid,
            'username'=>$username,
            'comment'=>$book_c,
            'time'=>date('Y-m-d l h:i:s A',time())
        );
        $bookLog = Array(
            'book_id'=>$bookid,
            'start_time'=>date('Y-m-d l h:i:s A',time()),
            'end_time'=>date('Y-m-d l h:i:s A',time()),
            'keeper_name'=>$username,
            'status'=>3
        );
        $bookmessage = Array(
            'book_id'=>$bookid,
            'keeper_name'=>$username,
            'status'=>3
        );
        if(!$num1){
            $num->add($data);
        }else{
            $num->where(array('book_id'=>$bookid))->save($data);
        }
        $borrowlog = $book->where($bookmessage)->find();
        if(!$borrowlog){
//            $bookuser = M(bookorder);
//            $bookid = cookie('bookid');
//            $username = cookie('username');
////        $data = Array(
////            'book_id'=>$bookid,
////            'keeper_name'=>$username
////        );
//            $userdata = Array(
//                'book_id'=>$bookid,
//                'ordername'=>$username
//            );
//            $datanum = Array(
//                'book_id'=>$bookid
//            );
////        $bookmessage = $booklog->where($data)->select();
//            $bookusers = $bookuser->where($userdata)->find();
//            $bookorders = $num->where($datanum)->find();
//            if($bookusers){
//                $datanew = Array(
//                    'book_id'=>$bookid,
//                    'order_num'=>$bookorders['order_num']-1
//                );
//                $bookuser->where($userdata)->delete();
//                $num->where(array('book_id'=>$bookid))->save($datanew);
//            }
            $book->where(array('book_id'=>$bookid))->save($bookmessage);
            $booklog->add($bookLog);
            if($book_c == null || $book_c == ""){

            }else{
                $comment->add($data1);
            }
            $this->redirect(phoners);
        }else{
            $this->error("您已经归还此书！",U(phonere),3);
        }
    }
    //预约信息
    public function pbookorder(){
        $booklog = M('booklog');
        $book = M('book');
        $bookorder = M('bookorder');
        $booknum = M('bookmnum');
        $bookid = cookie('bookid');
        $username = cookie('username');
        $booknum1 = $booknum->where(array('book_id'=>$bookid))->find();
        $bookorder1 = $bookorder->where(array('book_id'=>$bookid,'ordername'=>$username))->find();
        $data = Array(
            'book_id'=>$bookid,
            'order_name'=>$username,
            'keeper_name'=>$username,
            'start_time'=>date('Y-m-d l h:i:s A',time()),
            'status'=>4
        );
        $bookdata = Array(
            'book_id'=>$bookid,
            'ordername'=>$username
        );
        $order_num = intval($booknum1['order_num'])+1;
        $booknum2 = Array(
            'book_id'=>$bookid,
            'order_num'=>$order_num
        );
        if(!$bookorder1){
            $bookorder->add($bookdata);
            if(!$booknum1){
                $booknum->add($booknum2);
            }else{
                $booknum->where(array('book_id'=>$bookid))->save($booknum2);
            }
            $booklog ->add($data);
            $this->redirect(phoneos);
        }else{
            $this->error("您已经预约过了！",U(phoneoe),3);
        }
//        $booknum->save($booknum2);

    }
    //续借信息
    public function pbookrenew(){
        $time = I('time');
        $time = intval($time)*3600*24;
        $bookid = cookie('bookid');
        $username = cookie('username');
        $booklog = M('booklog');
        $book = M('book');
        $data = Array(
            'book_id'=>$bookid,
//            'status'=>2
        );
        $bookreturn = $book->where($data)->find();
//        $datalog = $booklog->where($bookid)->order('log_id desc')->limit(1)->select();
        $booklog1 = $booklog->where($data)->order('log_id desc')->limit(1)->find();
        if(($username == $bookreturn['keeper_name'])&&($bookreturn['status']==2)){
            $end_time = strtotime($booklog1['end_time'])+$time;
            $data1 = Array(
                'book_id'=>$bookid,
                'keeper_name'=>$username,
                'start_time'=>$booklog1['end_time'],
                'end_time'=>date('Y-m-d l h:i:s A',$end_time),
                'status'=>5
            );
            $data2 = Array(
                'book_id'=>$bookid,
                'keeper_name'=>$username,
                'status'=>5
            );
            $end_time1 = date('Y-m-d l h:i:s A',$end_time);
            cookie("end_time",$end_time1,time()+60*60*24*30);
            $check = $book->where($data2)->find();
            if(!$check){
                $booklog->add($data1);
                $book->where(array('book_id'=>$bookid))->save($data2);
                $this->redirect(phonebs);
            }else{
                $this->error("您已经续借过了！",U(phonenewe),3);
            }
        }else{
            $this->error("您已经续借过了！",U(phonenewe),3);
        }
    }
    //借书、还书、续借、预约成功等信息
    public function phoneSuccess(){
        $end_time = cookie("end_time");
        $end_time1 = Array(
            "end_time"=>date('Y-m-d l h:i:s A',$end_time)
        );
        $this->end_time= $end_time1;
        $this->display();
    }
    public function phonebs(){
        $end_time = cookie("end_time");
        $data = Array(
            "end_time"=>$end_time
        );
        $this->end_time= $data;
        $this->display();
    }
    public function phoners(){
        $this->display();
    }
    public function phoneExit(){
        $this->display();
    }
    public function phoneCheck(){
//        $booklog = M(booklog);
        $booknum = M('bookmnum');
        $bookuser = M('bookorder');
        $bookid = cookie('bookid');
        $username = cookie('username');
//        $data = Array(
//            'book_id'=>$bookid,
//            'keeper_name'=>$username
//        );
        $userdata = Array(
            'book_id'=>$bookid,
            'ordername'=>$username
        );
        $datanum = Array(
            'book_id'=>$bookid
            );
//        $bookmessage = $booklog->where($data)->select();
        $bookusers = $bookuser->where($userdata)->find();
        $bookorders = $booknum->where($datanum)->find();
        if($bookusers){
            $datanew = Array(
                'book_id'=>$bookid,
                'order_num'=>$bookorders['order_num']-1
            );
            $bookuser->where($userdata)->delete();
            $booknum->where(array('book_id'=>$bookid))->save($datanew);
        }
        $this->redirect(phoneMore);
    }
    public function phoneMore(){
        $this->display();
    }
    //借书处理 验证用户是否合法、验证图书的状态、完成借书还书操作
    //两个表，一个book表，主要是book的核心信息
    //一个booklog表，主要是借阅纪录
    //status 为1 表示书闲置，可以被借 为2 表示书正在使用，只能还书 book表中只有这两个状态
    //booklog 表中还有第三个状态 3 表示完成还书操作

    public function doBorrow(){
        $data=array('username'=>I('username'),
                    'password'=>I('password'));
        $data['password']=md5($data['password']);
        // print_r($data);
        $bookid=intval(I('bookid'));
        // echo $bookid;
        // $map=
        $m=M('members');
        if($m->where($data)->find()) {
            // echo '存在用户，成功！';
            //用户合法，是存在的用户
            $b=M('book');
            $map=array('book_id'=>$bookid
                );
            $bu=$b->where($map)->find();
            $u=$m->where($data)->find();
            //如果图书没有被借走，则可以完成借书操作
            if ($bu['status']==1) {
                $bookdata=array(
                    'book_id'=>$bookid,
                    'keeper_name'=>$u['username'],
                    'status'=>2);
                $b->save($bookdata);
                $blog=M('booklog');
                $bookLog=array('book_id'=>$bookid,
                    'keeper_name'=>$u['username'],
                    'start_time'=>date('Y-m-d l h:i:s A',time()),
                    'status'=>2);
                $blog->add($bookLog);
                // echo "借书成功";
                $this->success("借书成功",U(bookInfo,array('bookid'=>$bookid)));
            }elseif ($bu['status']==2) {
                //如果图书被借走了，现在只能完成还书操作
                if($u['username']==$bu['keeper_name']){
                    //是借走图书的人来还书
                    $bookdata=array(
                    'book_id'=>$bookid,
                    'status'=>1);
                    $b->save($bookdata);
                    $blog=M('booklog');
                    $map=array('book_id'=>$bookid,'keeper_name'=>$u['username'],'status'=>2);
                    $bookLog=array(
                    'end_time'=>date('Y-m-d l h:i:s A',time()),
                    'status'=>3);
                    $blog->where($map)->save($bookLog);
                    // echo "还书成功";
                    $this->success("还书成功",U(bookInfo,array('bookid'=>$bookid)));
                }else{
                    //不是借走图书的人来还书
                    // echo "您手上并没有此书，不能还书";
                    $this->success("您手上并没有此书，不能还书",U(bookInfo,array('bookid'=>$bookid)));
                }
            }
        }else{
            $this->error('用户名输错，请重新输入',U(borrow,array('bookid'=>$bookid)));
        }
    }
    public function ensure(){
        passonCount('ensure');
        userStates('ensure');
        // echo "查看书的信息";
        $s=I('isbn');
        $b=getArrayByIsbn($s);
        $b['author']=implode('-', $b['author']);
        $bi=M('bookinfo');
        $array = "";
        if($b){
            // echo '<pre>';
            foreach( $b['tags'] as $key=>$value){
                $array = $array." ".$value['title'];
            }
            $this->book=$b;
            $bidata=array(
                'isbn'=>$b['isbn13'],
                'title'=>$b['title'],
                'author'=>$b['author'],
                'translator'=>$b['translator'],
                'publisher'=>$b['publisher'],
                // 'summary'=>$b['summary'],
                'image'=>$b['image'],
                'tags'=>$array
                );
            if ($bi->where(array('isbn'=>$b['isbn13']))->find()) {
                $bi->save($bidata);
            }else{
                $bi->add($bidata);
            }
            $this->display();
        }else{
            $this->redirect(contribute);
        }
    }
    public function ensurechange(){
        passonCount('ensure');
        userStates('ensure');
        // echo "查看书的信息";
        $s=I('isbn');
        $b=getArrayByIsbn($s);
        $b['author']=implode('-', $b['author']);
        $bi=M('bookinfo');
        $array = "";
        if($b){
            // echo '<pre>';
            foreach( $b['tags'] as $key=>$value){
                $array = $array." ".$value['title'];
            }
            $this->book=$b;
            $bidata=array(
                'isbn'=>$b['isbn13'],
                'tags'=>$array
            );
            if ($bi->where(array('isbn'=>$b['isbn13']))->find()) {
                $bi->save($bidata);
            }else{
                $bi->add($bidata);
            }
//            $this->display();
        }else{
            $this->redirect(contribute1);
        }
    }
    public function contributechange(){
        passonCount('contribute');
        userStates('contribute');
        $username = cookie('username');
        if($username == "" || $username == null){
            $this->error("您未登录!",U(index),3);
        }else{
            $this->display();
        }
    }
}