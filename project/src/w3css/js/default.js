/**
 * Created by rxliuli on 17-7-18.
 */

//region 回到顶部/底部

$(document).ready(function () {
    //页面每次重新加载都回到最底端(为了消除浏览器有时候无法回到顶端的问题还是暂且加上吧)
    // $(document).scrollTop(10000);
});

//endregion

//region 导航栏菜单

$(document).ready(function () {
    var $dropNav = $(".drop-nav");
    //导航栏菜单元素鼠标单击事件
    $(".nav-menu a.rx-row").click(function () {
        //重置所有其他的菜单选项
        $(this).siblings().find(" span").removeClass("rx-delta-top").addClass("rx-delta-bottom");
        $(this).find(" span").toggleClass("rx-delta-bottom").toggleClass("rx-delta-top");
        // alert($(this).index() - 10);
        // alert($(".drop-nav").eq($(this).index() - 10).is(":hidden"));
        if ($dropNav.eq($(this).index() - 10).is(":hidden")) {
            $dropNav.hide();
            $dropNav.eq($(this).index() - 10).css("display", "flex").fadeIn();
        } else
            $dropNav.eq($(this).index() - 10).hide();
    });

    //具体关闭按钮单击事件
    $dropNav.find(".btn-close").click(function () {
        //隐藏全部的具体内容并重置所有的"三角形符号"
        $(this).parent().hide();
        $(this).siblings().find(" span").removeClass("rx-delta-top").addClass("rx-delta-bottom");
        $(".nav-menu .rx-row span").removeClass("rx-delta-top").addClass("rx-delta-bottom");
    });

    //定义 导航栏的副本(包括所有绑定的事件)
    var $navMenuClone = $(".nav-menu").clone(true).css({
        display: "none",
        position: "fixed",
        zIndex: "1",
        top: "0px",
        width: "100%"
    });
    $(".nav-menu").eq(0).after($navMenuClone);
    //网页滚动时保持导航栏位于顶端不动
    $(window).scroll(function () {
        if ($(window).scrollTop() > $(".nav-menu").get(0).offsetTop) {
            $navMenuClone.show();
            $dropNav.css({
                "position": "fixed",
                "top": $navMenuClone.height()
            })
        }
        else {
            $navMenuClone.hide();
            $dropNav.css({
                "position": "absolute",
                "top": "auto"
            })
        }
    });
});

//endregion

//region 将侧边栏菜单的高度设置为可视高度

$(document).ready(function () {
    function myWindowLoad() {
        // alert($(" main").attr("class") === "rx-row");
        var $myArticle = $(" main article");
        //将代码块和内容区域的高度都设置为窗口高度
        $myArticle.height($(window).height());
    }

    myWindowLoad();
    $(window).resize(myWindowLoad);

    //侧边栏菜单固定
    var $leftMenuClone = $(".left-menu").clone(true).css({
        display: "none",
        position: "fixed",
        top: $(".nav-menu").height()
    });
    $leftMenuClone.find("a:first").css({
        backgroundColor: "#4caf50",
        color: "white"
    });

    $(".left-menu").after($leftMenuClone);
    $(window).scroll(function () {
        if ($(window).scrollTop() > $(".nav-menu").get(0).offsetTop) {
            $leftMenuClone.show();
        }
        else {
            $leftMenuClone.hide();
        }
    });
});

//endregion
