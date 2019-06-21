//修改信息的第一步回显
function getById(path,url,par) {
    var urls=url+$("#"+par).text();
    if (confirm("是否进行"+path+"操作")) {
        location.href = urls;
    }else {
        alert('您取消了该操作');
    }
}

//公共的删除
function DelStuId(url,par) {
    var param = {};
    param.id = $("#"+par).text();
    $.get(url,param,function (data) {
        if (data.fl){
            layer.msg(data.msg, {icon: 6 , time:1500} ,function() {
                window.location.reload();
            });
        } else {
            layer.msg(data.msg, {icon: 5 , time:1500, anim: 6} ,function() {
                window.location.reload();
            });
        }
        },"json");
}


// 提交表单的数据
function submitOn(url,form){
    // 1.得到表单数据
    var param = fromToObject(form);
    console.log(param);
    // 2.通过异步把数据传递后后台
    if (pd(form)){
        $.post(url,param,function(data){
            showMsg(data);
        },"JSON");
    }
    else {
        layer.msg('星号标记项不能为空', {icon: 5 , time:1500, anim: 6});
    }
}

//弹出提示
function showMsg(data){
    if(data.fl){
        layer.msg(data.msg, {icon: 6 , time:1500} ,function() {
            window.location.reload();
        });
    }else{
        layer.msg(data.msg, {icon: 5 , time:1500, anim: 6} ,function() {
            window.location.reload();
        });
    }
}

// 把form对象转成对象
function fromToObject(form){
    // param用来装表单中的数据
    var param = {};
    // 1.获取表单的数据
    var jsonArray = serialize(form);
    // 2.把jsonArray中的数据放到param对象中
    for(var i =0;i<jsonArray.length;i++){
        param[jsonArray[i].name] = jsonArray[i].value;
    }
    return param;
}

//表单序列化
function serialize(form) {
   return $('#'+form+'').serializeArray();
}

//判断非空
function pd(form) {
    var jsonArray = serialize(form);
    for (var i=0;i<jsonArray.length;i++){
        if (jsonArray[i].value == null || jsonArray[i].value == ""){
            return false;
        }
    }
    return true;
}

//批量删除
function batchDel(idArray,url){
    // 1.获取用户选择的Id
    $(":checkbox:checked").each(function(index,item){
        idArray.push(item.value); // 把用户选择的id放到数组中
    });
    if (idArray.length == 0){
        layer.msg('无法执行操作，没有选择', {icon: 5 , time:1500, anim: 6} ,function() {
            return false;
            window.location.reload();
        });
    }else {
        // 2.创建一个对象
        var param = {};
        param.ids = idArray; // 把数组封装道对象中
        // 3.异步把数据传递过去
        debugger
        $.post(url,param,function(data){
            showMsg(data);
        },"JSON");
    }
};
