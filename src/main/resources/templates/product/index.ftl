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
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label>名称</label><input  name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label><input  name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label><input name="productStock" type="number"  class="form-control" value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label><input name="productDescription" type="text"   class="form-control" value="${(productInfo.productDescription)!''}"/>
                        </div>
                        <div class="form-group">
                            <img src="${(productInfo.productIcon)!''}" alt="">
                            <label>图片</label><input name="productIcon" type="text"   class="form-control" value="${(productInfo.productIcon)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                            <#--  如果这个类目存在的话，并且  productInfo.categoryType == category.categoryType-->
                                            <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                            selected
                                            </#if>
                                    >
                                        ${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <input type="text" value="${(productInfo.productId)!''}" name="productId" hidden>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>


</div>



</body>

</html>

