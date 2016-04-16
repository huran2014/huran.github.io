function check(){
    var $isbn = $(".isbn-input").val();
    if($isbn == "" || $isbn == null){
        alert("请输入ISBN!");
        return false;
    }
    return true;
}