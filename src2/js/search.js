/**
 * Created by rxliuli on 17-6-24.
 */

//region main-03 选择类别

$(document).ready(function () {
    $(".main-03 li div a").click(function () {
        //获取所有的兄弟元素（不包括自己）并设置颜色为灰色
        $(this).siblings().css({"background-color": "white", "color": "grey"});
        $(this).css({"backgroundColor": "orangered", "color": "white"});
    });
});

//endregion

//region 浏览历史清空

$(document).ready(function () {
    $(".browsing-history-title  a").click(function () {
        $(".browsing-history ul").slideUp(1000); //滑动效果
        // $(".browsing-history ul").fadeOut(1000);
        // $(".browsing-history ul").css({"display": "none"});
    })
});

//endregion

//region 上面的四个排序方式

// $(document).ready(function () {
//     $(".commodity-list-title > ul > li").click(function () {
//         $(".commodity-list-title ul li").css({
//             "background-color": "white",
//             "color": "black"
//         });
//         $(this).find(" ul li ").css({
//             "border-left-color": "orangered",
//             "border-right-color": "orangered",
//             "border-top-color": "black"
//         });
//         $(this).css({
//             "background-color": "orangered",
//             "color": "white"
//         });
//     })
// });

//endregion

//region 收藏

$(document).ready(function () {
    $(".commodity-list-row div p:first-child").click(function () {
        $(this).find("img:nth-child(1)").toggle();
        $(this).find("img:nth-child(2)").toggle();
    })

});


//endregion

//region 加入购物车

$(document).ready(function () {
    $(".commodity-list-row li > div p:last-child").click(function () {
        alert("您选择的商品【 " +
            this.parentNode.parentNode.childNodes[3].childNodes[3].innerHTML +
            " 】已加入到购物车中，请尽快前往付款结账！");
    })
});

//endregion

//region 翻页

$(document).ready(function () {
    $(".page-number").click(function () {
        $(".page-number").css({"background-color": "white"});
        this.style.backgroundColor = "orangered";
    })
});

//endregion
