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
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label>类目名称</label><input  name="categoryName" type="text" class="form-control" value="${(productCategory.categoryName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>类目类型</label><input  name="categoryType" type="text" class="form-control" value="${(productCategory.categoryType)!''}"/>
                        </div>

                        <input type="text" value="${(productCategory.categoryId)!''}" name="categoryId" hidden>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>


</div>



</body>

</html>

