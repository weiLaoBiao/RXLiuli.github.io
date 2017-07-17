/**
 * Created by rxliuli on 17-7-15.
 */

//region 回到顶部/底部

$(document).ready(function () {
    //页面每次重新加载都回到最底端(为了消除浏览器有时候无法回到顶端的问题还是暂且加上吧)
    // $(document).scrollTop(10000);
});

//endregion

//region 导航栏菜单

$(document).ready(function () {
    var navArr = ["#nav_tutorials", "#nav_references", "#nav_examples"];
    //导航栏菜单元素鼠标单击事件
    $(".top-nav-menu a:gt(0)").click(function () {
        //重置所有其他的菜单选项
        $(this).siblings().find(" span").removeClass("caret-up").addClass("caret-down");
        $(this).find(" span").toggleClass("caret-down").toggleClass("caret-up");
        // alert($(navArr[$(this).index() - 1]).css("display"));
        //判断当前点击的菜单选项对应的具体内容是否隐藏
        if ($(navArr[$(this).index() - 1]).css("display") === "none") {
            //如果确实隐藏就将所有内容隐藏然后显示当前的
            $("#nav_tutorials, #nav_references, #nav_examples").hide();
            $(navArr[$(this).index() - 1]).fadeIn();
        }
        /*否则直接隐藏当前选项对应的具体内容*/
        else $(navArr[$(this).index() - 1]).hide();
    });
    //具体关闭按钮单击事件
    $("#nav_tutorials .icon-close, #nav_references .icon-close, #nav_examples .icon-close").click(function () {
        //隐藏全部的具体内容并重置所有的"三角形符号"
        $(this).parent().hide();
        $(".top-nav-menu a span").removeClass("caret-up").addClass("caret-down");
    });
});

//endregion

//region 侧边栏

$(document).ready(function () {
    var $sideMenu = $("#side-menu");
    //导航栏菜单元素单击事件
    $(".top-nav-menu a:first").click(function () {
        $sideMenu.stop();
        if (parseInt($sideMenu.css("width")) > 0) {
            $sideMenu.animate({width: "0", opacity: "0"}, 400, "swing");
        }
        else {
            $sideMenu.animate({width: "250px", opacity: "1"}, 400);
        }
        // $("#side-menu").toggle();
    });
    //侧边栏菜单关闭按钮单击事件
    $sideMenu.find(".icon-close").click(function () {
        $sideMenu.stop();
        $sideMenu.animate({width: "0"}, 400);

        // $("#side-menu").css("width", "0");
    });
});

//endregion

