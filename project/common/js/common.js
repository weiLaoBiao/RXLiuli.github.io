/**
 * Created by rxliuli on 17-7-3.
 */

//toggle 流事件

$(document).ready(function () {
    $(".toggle-btn").toggle(
        function () {
            alert("dfsl")
            $(".toggle-content").css("backgroundColor", "red");
        },
        function () {
            $(".toggle-content").css("backgroundColor", "darkcyan");
        },
        function () {
            $(".toggle-content").css("backgroundColor", "blue");
        }
    );
});

//endregion

//region 回到顶部

$(document).ready(function () {
    //点击回到顶部事件
    $("img.backToTop").click(function () {
        backToTop();
    });


    //页面每次重新加载都回到最顶端(为了消除浏览器有时候无法回到顶端的问题还是暂且加上吧)
    backToTop();
    function backToTop() {
        var $html = $(" html");
        $html.animate({scrollTop: 0}, $html.attr("height"));
    }
});

//endregion

//region 分页

$(document).ready(function () {
    var len = $("ul.pagination li").length;
    //点击第几个
    $("ul.pagination li:gt(0):lt(" + (len - 2) + ")").click(function () {
        paginationChange($(this).index());
    });

    //点击上一个和下一个
    $("ul.pagination li:first a").click(function () {
        paginationChange($("ul.pagination li a.active03").parent().index() - 1);
    });
    $("ul.pagination li:last a").click(function () {
        paginationChange($("ul.pagination li a.active03").parent().index() + 1);
    });

    //实际上移动到第 n 个标签
    function paginationChange(n) {
        if (n < 1) n = len - 2;
        else if (n > len - 2) n = 1;
        $("ul.pagination li a").removeClass("active03");
        $("ul.pagination li:eq(" + n + ") a").addClass("active03");
        $("ul.pagination-content li").hide();
        $("ul.pagination-content li:eq(" + n + ")").fadeIn();
    }
});

//endregion

//region 动画菜单图标

$(document).ready(function () {
    $(".animated-menu-icon").click(function () {
        $(this).find(" div").toggleClass("change");
    });
});

//endregion

//region jQuery 拖放

$(document).ready(function () {
    var boo = false;
    var patch;
    //当鼠标被按下发生
    $(".my-drag").mousedown(function () {

    });
    //鼠标移动时
    $(document).mousemove(function (event) {

    });
    //鼠标放开
    $(document).mouseup(function () {

    });
});

//endregion

//region form 表单

$(document).ready(function () {
    //网页加载完成后自动获得焦点
    $(".form input[type='text']").focus();
    //设置键盘绑定
    $(" form").keydown(function (event) {
        if (event.keyCode === 13) $(".form input[type='submit']").submit();
    });
    //设置表单验证
    $(".form input[type='text']").change(function () {
        if ($(this).val().trim() === "") $(this).next().html("用户名不能为空！");
    });
    $(".form input[type='password']").change(function () {
        var regExpPwd = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,18}$/;

        if ($(this).val().trim() === "")
            $(this).css("box-shadow", "0 0 5px 0 red").next().html("密码不能为空！");
        else if (!regExpPwd.test($(this).val()))
            $(this).css("box-shadow", "0 0 5px 0 red").next().html("密码至少8位并且不能同时为字母或数字！");
    });
    //记住密码
    $(".form input[type='checkbox']").change(function () {
        //这只目前看来唯一获取 checkbox 是否选中的方法
        // alert($(this).is(":checked"))
        $(this).next().html($(this).is(":checked") ? "在公用电脑上请勿选择！" : "")
    });
});

//endregion

//region 过滤文本

$(document).ready(function () {
    //使用 contains 判断是否包含文本
    $(".filter li:contains('e')").css("backgroundColor", "darkcyan");
    //使用区间(lt,gt)
    $(".filter li:gt(0):lt(3)").css("fontSize", "30px");
    //使用取反 not()
    $(".filter li:not(:contains('d'))").css("color", "grey");
});

//endregion

//region 装载机

//使用 jQuery 实现装载机
$(document).ready(function () {
    var timer;
    var angle = 0;

    //启动和停止
    $(".loader-jQuery").click(function () {
        if (timer === undefined) spin();
        else {
            clearInterval(timer);
            timer = undefined;
        }
    });

    //实际转动的方法
    function spin() {
        timer = setInterval(function () {
            angle += 1;
            $(".loader-jQuery").css({
                transform: "rotate(" + angle + "deg)"
            });
        }, 5);
    }
});

//endregion

//region 下拉菜单（悬浮或点击）

$(document).ready(function () {
    //悬浮下拉框
    $(".drop-down").hover(function () {
        $(".drop-down-content").slideToggle(500);
    });
    //点击下拉框
    // $(".drop-down").click(function () {
    //     $(".drop-down-content").slideToggle(500);
    // });
});

//endregion

//region 进度条

function progress(n) {
    var $myBar = $(".my-bar");
    //使用 jQuery 动画直接实现
    // $myBar.css("width", "0");
    // $myBar.animate({
    //     width: n + "%"
    // }, n * 100);

    //使用 setInterval 实现
    var i = 0;
    var timer = setInterval(function () {
        $myBar.css("width", i + "%");
        $myBar.html(i.toFixed(1) + "%");
        i += 0.1;
        if (i > 80) clearInterval(timer);
    }, 10)
}

//endregion

//region 模态图像

$(document).ready(function () {
    $(".modal-img").click(function () {
        $(".modal-02").fadeIn().find(".modal-content-02").attr("src", $(this).attr("src"));
        // $(".modal-content-02").attr("src", "images/img_fjords_wide.jpg");
    });
    $(".close-02").click(function () {
        $(".modal-02").fadeOut();
    });
});

//endregion

//region 模态盒

$(document).ready(function () {
    //打开模态盒
    $(".modal-btn").click(function () {
        var $modal = $(".modal");
        var $modalContent = $(".modal-content");
        $($modal).show();
        //jQuery 动画
        $modalContent.css({
            top: "-200px",
            opacity: "0"
        });
        $modal.css({
            opacity: "0"
        });
        $modalContent.animate({
            top: "100px",
            opacity: "1"
        }, 400);
        $modal.animate({
            opacity: "1"
        }, 400);
    });
    //关闭
    $(".close").click(function () {
        $(".modal").hide();
    });
});

//endregion

//region 标签页

$(document).ready(function () {
    var $tab = $(".tabs li");
    var $tabContent = $(".tabs-content li");
    tabChange(0);

    //切换
    $tab.mouseover(function () {
        // alert($(this).index())
        tabChange($(this).index());
    });

    //标签切换的具体实现
    function tabChange(n) {
        //链式调用
        $tab.removeClass("tabsHover").eq(n).addClass("tabsHover");
        $tabContent.fadeOut(0).eq(n).fadeIn();
        // $(tab).eq(n).addClass("tabsHover");
        // $(tabContent).eq(n).fadeIn();
    }
});

//endregion

//region 手风琴

$(document).ready(function () {
    //点击事件
    $(".accordion").click(function () {
        // alert($(this).next().css("display"));
        if ($(this).next().css("display") === "block") return;

        $(".accordion").toggleClass("active02", "active01");
        $(this).toggleClass("active01", "active02");
        // $(".accordion").attr("class", "accordion active01");
        // $(this).attr("class", "accordion active02");
        // $(".accordion").removeClass("active02");
        // $(".accordion").addClass("active01");
        // $(this).removeClass("active01");
        // $(this).addClass("active02");

        $(".panel").slideUp();
        $(this).next().slideDown();
    });

    //一开始就将第一个设置为减号
    var $first = $(".my-accordions li:first-child .accordion");
    $first.removeClass("active01");
    $first.addClass("active02");
});

//endregion

//region 幻灯片（背景图片实现）

$(document).ready(function () {
    //保存图片的名字的字符串数组
    var bgImgs = ["img_fjords_wide.jpg", "img_lights_wide.jpg",
        "img_mountains_wide.jpg", "img_nature_wide.jpg"];
    var index = 0;
    var timer;

    //自动切换的方法
    myInterval();
    function myInterval() {
        clearInterval(timer);
        timer = setInterval(function () {
            showSlides(index + 1)
        }, 3000)
    }

    //鼠标悬浮时暂停轮播
    $(".slide-show-container-02 *").hover(function () {
        clearInterval(timer);
    }, function () {
        myInterval();
    });

    //上一张和下一张的方法
    $(".prev-02").click(function () {
        showSlides(index - 1);
        myInterval();
    });
    $(".next-02").click(function () {
        showSlides(index + 1);
        myInterval();
    });

    //使用下面的小圆点导航
    $(".dots-02 span").click(function () {
        showSlides($(this).index());
        myInterval();
    });

    //具体的切换代码
    function showSlides(n) {
        index = n;
        if (n >= bgImgs.length) index = 0;
        else if (n < 0) index = bgImgs.length - 1;
        var $dots = $(".dots-02 span");
        $dots.css("background-color", "white");
        $(".slide-show-container-02").css("background-image", "url(images/" + bgImgs[index] + ")");
        $dots.eq(index).css("background-color", "#717171");
        $(".number-text-02").html(index + 1 + " / " + bgImgs.length);
    }
});

//endregion

//region 幻灯片

$(document).ready(function () {
    //当前现实的图片的下标（从0开始！）
    var slideIndex = 0;
    //计时器
    var timer;
    //默认放映幻灯片
    myInterval();

    //图片轮播的方法
    function myInterval() {
        clearInterval(timer);
        timer = setInterval(function () {
            plusSlides(1);
        }, 3000);
    }

    //左移和右移
    $(".prev").click(function () {
        plusSlides(-1);
    });
    $(".next").click(function () {
        plusSlides(1)
    });
    function plusSlides(n) {
        showSlides(slideIndex += n);
        myInterval();
    }

    //跳转指定的页面
    $(".dots span").click(function () {
        showSlides(slideIndex = $(this).index());
        myInterval();
    });

    //鼠标悬浮时暂停轮播
    $(".slide-show-container *").hover(function () {
        clearInterval(timer);
    }, function () {
        myInterval();
    });

    //具体的图片切换的代码
    function showSlides(n) {
        var $slides = $(".my-slides");
        var $dots = $(".dots span");
        if (n >= $slides.length) slideIndex = 0;
        else if (n < 0) slideIndex = $slides.length - 1;
        $slides.hide();
        $dots.css("background-color", "white");
        $slides.eq(slideIndex).show();
        $dots.eq(slideIndex).css("background-color", "#717171");
    }
});

//endregion

//region 给 jQuery 添加新的方法
(function ($) {

    $.fn.extend({
        rotate: function (deg) {

            // cache dom element
            var $this = $(this);

            // make deg random if not set
            if (deg === null) {
                deg = Math.floor(Math.random() * 359);
            }

            // rotate dom element
            $this.css({
                '-webkit-transform': 'rotate(' + deg + 'deg)',
                '-moz-transform': 'rotate(' + deg + 'deg)',
                '-ms-transform': 'rotate(' + deg + 'deg)',
                '-o-transform': 'rotate(' + deg + 'deg)',
                'transform': 'rotate(' + deg + 'deg)'
            });

            // make chainable
            return $this;
        }
    });

})(jQuery);
//endregion

//region 问题

$(document).ready(function () {
    //jQuery 动画是依次执行的
// $(".animated-menu-icon").animate({
//     width: "1000px",
//     height: "500px",
//     backgroundColor: "red"
// }, 10000, function () {
//     alert("lfsd")
// })

    //html() 和 text()

    // $(".animated-menu-icon").html("<div>fsdl</div>")
    // $(".animated-menu-icon").text("<div>fsdl</div>")
    //区别: html()可以插入 html 标签,而 text() 则永远都是内容
});

//endregion