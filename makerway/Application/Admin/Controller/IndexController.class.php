<?php
namespace Admin\Controller;
use Think\Controller;
class IndexController extends Controller {
    public function index(){
        // $this->show('<style type="text/css">*{ padding: 0; margin: 0; } div{ padding: 4px 48px;} body{ background: #fff; font-family: "微软雅黑"; color: #333;font-size:24px} h1{ font-size: 100px; font-weight: normal; margin-bottom: 12px; } p{ line-height: 1.8em; font-size: 36px } a,a:hover{color:blue;}</style><div style="padding: 24px 48px;"> <h1>:)</h1><p>欢迎使用 <b>ThinkPHP</b>！</p><br/>版本 V{$Think.version}</div><script type="text/javascript" src="http://ad.topthink.com/Public/static/client.js"></script><thinkad id="ad_55e75dfae343f5a1"></thinkad><script type="text/javascript" src="http://tajs.qq.com/stats?sId=9347272" charset="UTF-8"></script>','utf-8');
        $this->display();       
    }
    public function books(){
    	$b=M('book');
    	$bl=M('booklog');
    	$bi=M('bookinfo');
    	$books=$b->order('book_id asc')->select();
    	foreach ($books as $id => $book) {
    		$booklog=$bl->where(array('book_id'=>$book['book_id']))->order('log_id asc')->select();
    		$books[$id]['booklog']=$booklog;
    		$bookinfo=$bi->where(array('isbn'=>$book['isbn']))->find();
    		$books[$id]['bookinfo']=$bookinfo;
    	}
    	$this->books=$books;
    	$this->display();
    }
    public function deleteBook(){
    	$b=M('book');
    	$bl=M('booklog');
    	if ($b->where(I())->delete()) {
    		$bl->where(I())->delete();
    		$this->success('删除书成功',U(books),3);
    	}else{
    		$this->error('删除书失败',U(books),3);
    	}
    }
    public function users(){
    	$b=M('book');
    	$bl=M('booklog');
    	$bi=M('bookinfo');
    	$bu=M('book_user_info');
    	$users=$bu->order('book_user_info_id asc')->select();
    	foreach ($users as $key => $user) {
    		$books=$b->where(array('owner_name'=>$user['username']))->select();
    		$users[$key]['books']=$books;
    		foreach ($users[$key]['books'] as $id => $book) {
    		$booklog=$bl->where(array('book_id'=>$book['book_id']))->order('log_id asc')->select();
    		$users[$key]['books'][$id]['booklog']=$booklog;
    		$bookinfo=$bi->where(array('isbn'=>$book['isbn']))->find();
    		$users[$key]['books'][$id]['bookinfo']=$bookinfo;
	    	}
    	}
    	$this->users=$users;
    	$this->display();
    }
    public function deleteUser(){
    	$b=M('book');
    	$bl=M('booklog');
    	$bi=M('bookinfo');
    	$bu=M('book_user_info');
    	$user=$bu->where(I())->find();

    	if($bu->where(I())->delete()){
    		$books=$b->where(array('owner_name'=>$user['username']))->select();
    		$b->where(array('owner_name'=>$user['username']))->delete();
    		foreach ($books as $key => $book) {
    			$bl->where(array('book_id'=>$book['book_id']))->delete();
    		}
    		$this->success('删除用户成功',U(users),3);
    	}else{
    		$this->error('删除用户失败',U(users),3);
    	}

    }

}