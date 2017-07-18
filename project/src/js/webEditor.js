/**
 * Created by rxliuli on 17-7-17.
 */

//region 将编辑器和内容都设置为窗口高度

$(document).ready(function () {
    function myWindowLoad() {
        // alert($(" main").attr("class") === "rx-row");
        var $myArticle = $(" main section article");
        //将代码块和内容区域的高度都设置为窗口高度
        if ($(" main").attr("class") === "rx-row") {
            $myArticle.width($(window).width() / 2 - 20);
            $myArticle.height($(window).height() - 60);
        } else {
            $myArticle.height($(window).height() / 2 - 60);
            $myArticle.width($(window).width() - 10);
        }
    }

    //窗口大小改变时
    $(window).resize(myWindowLoad);

    //切换水平/垂直布局
    $(".web-horizontal").click(function () {
        $(" main").attr("class", "rx-row");
        myWindowLoad();
    });
    $(".web-vertical").click(function () {
        $(" main").attr("class", "rx-col");
        myWindowLoad();
    });

    //运行
    $(".btn-run").click(function () {
        $(".web-result").html($(".web-codes").val());
    });

    //网页初始化
    //加载窗口
    myWindowLoad();
    //模拟点击
    $(".btn-run").trigger("click");
});

//endregion