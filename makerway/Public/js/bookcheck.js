function check(){
      var $inputtext = $(".input_text").val();
      if($inputtext == '' || $inputtext == null ){
          alert("评论不能为空！");
          return false;
      }
        return true;
}
function check1(){
    var $write = $(".write").val();
    if($write == '' || $write == null ){
        alert("评论不能为空！");
        return false;
    }
    return true;
}