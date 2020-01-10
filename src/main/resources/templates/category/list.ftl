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
                                类目id
                            </th>
                            <th>
                                商品名称
                            </th>
                            <th>
                                类目编号
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
                        <#--    循环遍历后端传过来的 ModelAndView里的数据productCategoryList遍历       -->
                        <#list productCategoryList as productCategory>

                            <tbody>
                            <tr>
                                <td>
                                    ${productCategory.categoryId}
                                </td>
                                <td>
                                    ${productCategory.categoryName}
                                </td>
                                <td>
                                    ${productCategory.categoryType}
                                </td>
                                <td>
                                    ${productCategory.createTime}
                                </td>
                                <td>
                                    ${productCategory.updateTime}
                                </td>
                                <td>
                                    <a href="/sell/seller/category/index?categoryId=${productCategory.categoryId}">修改</a>
                                </td>
                            </tr>
                            </tbody>
                        </#list>
                    </table>
                </div>

            </div>
        </div>
    </div>


</div>



</body>

</html>

