<html>

<#include "../common/header.ftl">

<body>

<div id="wrapper" class="toggled">

    <#--  边栏-->
    <#include "../common/nav.ftl" >

    <#--  主体内容 -->
    <div id="page-content-wrapper">

        <div class="container-fliud">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-condensed">
                        <thead>
                        <tr>
                            <th>
                                商品id
                            </th>
                            <th>
                                商品名称
                            </th>
                            <th>
                                图片
                            </th>
                            <th>
                                单价
                            </th>
                            <th>
                                库存
                            </th>
                            <th>
                                描述
                            </th>
                            <th>
                                类目
                            </th>
                            <th>
                                创建时间
                            </th>
                            <th>
                                修改时间
                            </th>
                            <th>
                                操作
                            </th>
                        </tr>
                        </thead>
                        <#--    循环遍历后端传过来的 ModelAndView里的数据orderDTOPage.content 的数据赋值   orderDTO        -->
                        <#list productInfoPage.content as productInfo>

                            <tbody>
                            <tr>
                                <td>
                                    ${productInfo.productId}
                                </td>
                                <td>
                                    ${productInfo.productName}
                                </td>
                                <td>
                                    <img height="100" width="100" src="${productInfo.productIcon}">
                                </td>
                                <td>
                                    ${productInfo.productPrice}
                                </td>
                                <td>
                                    ${productInfo.productStock}
                                </td>
                                <td>
                                    ${productInfo.productDescription}
                                </td>
                                <td>
                                    ${productInfo.categoryType}
                                </td>
                                <td>
                                    ${productInfo.createTime}
                                </td>
                                <td>
                                    ${productInfo.updateTime}
                                </td>
                                <td>
                                    <a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a>
                                </td>
                                <td>
                                    <#if productInfo.getProductStatusEnum().message == "在架">
                                        <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}">下架</a>
                                    <#else>
                                        <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">上架</a>
                                    </#if>
                                </td>
                            </tr>

                            </tbody>
                        </#list>
                    </table>
                </div>

                <#--     分页       -->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">

                        <#--   如果当前页currentPage小于(lte) 1  ;disabled为 -->
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                        </#if>


                        <#--    下面写法是从1至总页数 赋值给index  -->
                        <#list 1..productInfoPage.getTotalPages() as index>
                            <!--当前页不可点击-->
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                                <!--如果循环到了当前页加上限制显示条数(该例为前后各显示10条)，之后的循环不在进行-->
                            <#elseif index gt currentPage+3>
                                <li><a href="#">...</a></li>
                                <#break >
                                <!--如果循环在当前页减去限制显示条数(该例为前后各显示10条)，
                                  处理放在下一个else之中，当前循环不进行任何操作-->
                            <#elseif index lt currentPage-3>
                                <#continue >
                                <!--处理放在这。正好到限制页的时候。显示...-->
                            <#elseif index == currentPage-3>
                                <li><a href="#">...</a></li>
                            <#else >
                                <li><a href="list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>


                        <#--   如果当前页currentPage大于(gte) orderDTOPage.getTotalPages()总页数   -->
                        <#if currentPage gte productInfoPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>


</div>



</body>

</html>

