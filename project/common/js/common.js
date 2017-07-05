/**
 * Created by rxliuli on 17-7-3.
 */
//region 模态盒

$(document).ready(function () {
    //打开模态盒
    $(".modal-btn").click(function () {
        var modal = $(".modal");
        var modalContent = $(".modal-content");
        $(modal).show();
        //jQuery 动画
        $(modalContent).css({
            top: "-200px",
            opacity: "0"
        });
        $(modal).css({
            opacity: "0"
        });
        $(modalContent).animate({
            top: "100px",
            opacity: "1"
        }, 500);
        $(modal).animate({
            opacity: "1"
        }, 500);
    });
    //关闭
    $(".close").click(function () {
        $(".modal").hide();
    });
});

//endregion

//region 标签页

$(document).ready(function () {
    var tab = $(".tabs li");
    var tabContent = $(".tabs-content li");
    tabChange(0);

    //切换
    $(tab).mouseover(function () {
        // alert($(this).index())
        tabChange($(this).index());
    });

    //标签切换的具体实现
    function tabChange(n) {
        //链式调用
        $(tab).removeClass("tabsHover").eq(n).addClass("tabsHover");
        $(tabContent).fadeOut(0).eq(n).fadeIn();
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
    var first = $(".my-accordions li:first-child .accordion");
    $(first).removeClass("active01");
    $(first).addClass("active02");
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
        var dots = $(".dots-02 span");
        $(dots).css("background-color", "white");
        $(".slide-show-container-02").css("background-image", "url(images/" + bgImgs[index] + ")");
        $(dots).eq(index).css("background-color", "#717171");
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
        var slides = $(".my-slides");
        var dots = $(".dots span");
        if (n >= slides.length) slideIndex = 0;
        else if (n < 0) slideIndex = slides.length - 1;
        $(slides).hide();
        $(dots).css("background-color", "white");
        $(slides).eq(slideIndex).show();
        $(dots).eq(slideIndex).css("background-color", "#717171");
    }
});

//endregion
