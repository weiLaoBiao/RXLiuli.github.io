/**
 * Created by rxliuli on 17-6-25.
 */

//region 鼠标悬浮在小图上时加载大图

$(document).ready(function () {
    $(".img-small li").mouseover(function () {
        //获取元素是当前兄弟元素的第几个
        var index = $(".img-small li").index(this);
//                        alert(index);
        var elem = $(".img-big li").eq(index);
        //                        alert(elem.innerHTML);
//                        $(elem).siblings().css({"display": "none"});
//                        $(elem).css({"display": "block"});
        $(".img-big li[display!='none']").hide(200);
        $(elem).show(200);
    })
});

//endregion

//region 选择尺寸和颜色(未完成)

$(document).ready(function () {
    $(".commodity-option > li > ul li").click(function () {

    })
});

//endregion

//region 分享和关注

$(document).ready(function () {
    $(".share-attention li:first-child").click(function () {
        $(".share-attention li:first-child div").fadeToggle(1000);
    })
});

//关注
$(document).ready(function () {
    $(".share-attention li:last-child").click(function () {
        $(".share-attention li:last-child img").fadeToggle(1);
    })
});

//endregion

//region 加入购物车

$(document).ready(function () {
    $(".commodity-pay p").click(function () {
        alert("您选择商品已加入到您的购物车，请尽快付款结账！")
    })
});

//endregion

//region 其他用户还搜索隐藏和显示

$(document).ready(function () {
    $(".browsing-history-title  a").click(function () {
        $(".browsing-history ul").slideToggle(1000); //滑动效果
        var text = $(".browsing-history-title a");
        text.html(text.html() === "隐藏" ? "显示" : "隐藏");
    })
});

//endregion

//region 搭配购买

$(document).ready(function () {
    $('.main-04-01 input[type="checkbox"]').change(function () {
            computeSum()
        }
    )
});
$(document).ready(function () {
    $('.main-04-01 form input[type="text"]').change(function () {
        computeSum()
    })
});
function computeSum() {
//                        alert($(".compute-sum:nth-child(1) div label b").html())
//                        alert($(".compute-sum:nth-child(3) div label b").html())
//                        alert($(".compute-sum:nth-child(5) div label b").html())
//                        alert($(".commodity-price b").html().substring(1))

    var price1 = $('.compute-sum:nth-child(1) div label input').prop('checked') ?
        parseInt($(".compute-sum:nth-child(1) div label b").html().substring(1)) : 0;
    var price2 = $('.compute-sum:nth-child(3) div label input').prop('checked') ?
        parseInt($(".compute-sum:nth-child(3) div label b").html().substring(1)) : 0;
    var price3 = $('.compute-sum:nth-child(5) div label input').prop('checked') ?
        parseInt($(".compute-sum:nth-child(5) div label b").html().substring(1)) : 0;
    var price4 = parseInt($(".commodity-price b").html().substring(1));
    //单份套餐
    var priceSum = price1 + price2 + price3 + price4;
    //数量
//                        alert((price1 + price2 + price3 + price4));
    var num = parseInt($('.main-04-01 form input[type="text"]').val());
    var sum = isNaN(num) ? priceSum : priceSum * num;
    //给价格设置文本
    $('.main-04-01 ul li:last-child h1 b').html("￥" + sum);
}

//endregion

//region 翻页

$(document).ready(function () {
    $(".page-number").click(function () {
        $(".page-number").css({"background-color": "white"});
        this.style.backgroundColor = "orangered";
    })
});

//endregion
