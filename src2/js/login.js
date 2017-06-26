/**
 * Created by rxliuli on 17-6-20.
 */

//region 控制合作网站的显示和隐藏

$(document).ready(function () {
    $(".unfold").click(function () {
        $("#hiddenContent").slideToggle(200);
    });
});

//endregion

//region 控制自动登录的提示的显示和隐藏

$(document).ready(function () {
    //当 id #login-pc 下面的第一个符合交集选择器 form > div label input 的元素的改变时
    $("#login-pc").find("form > div label input").change(function () {
        $(".help-warning").fadeToggle(500);
    });
});

//endregion