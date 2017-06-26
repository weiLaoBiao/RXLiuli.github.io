/**
 * Created by rxliuli on 17-6-23.
 */

//region 废弃

<!--直接使用 js -->
//                function foo() {
//                    var x = document.getElementsByClassName("help-dropdown-menu")[0];
//                    var y = x.getElementsByTagName("ul")[0];
//                    y.style.display = "block";
//                }
//                function foo2() {
//                    var x = document.getElementsByClassName("help-dropdown-menu")[0];
//                    var y = x.getElementsByTagName("ul")[0];
//                    y.style.display = "none";
//                }

//使用 jQuery 简化代码
// $(document).ready(function () {
//     $(".help-dropdown-menu").hover(function () {
//         $(".help-dropdown-menu ul").toggle()
//     })
//     // $(".help-dropdown-menu").mouseover(function () {
//     //     $(".help-dropdown-menu ul").toggle()
//     // })
//     // function () {
//     //     $(".help-dropdown-menu ul").show();
//     // }, function () {
//     //     $(".help-dropdown-menu ul").hide();
//     // }
// });

//endregion

//控制下拉菜单的显示和隐藏
$(document).ready(function () {
    $(".help-dropdown-menu").hover(function () {
        $(".help-dropdown-menu ul").slideToggle(300);
    })
});
