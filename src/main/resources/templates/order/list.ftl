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
                                    订单id
                                </th>
                                <th>
                                    姓名
                                </th>
                                <th>
                                    手机号
                                </th>
                                <th>
                                    地址
                                </th>
                                <th>
                                    金额
                                </th>
                                <th>
                                    订单状态
                                </th>
                                <th>
                                    支付方式
                                </th>
                                <th>
                                    支付状态
                                </th>
                                <th>
                                    创建时间
                                </th>
                                <th>
                                    操作
                                </th>
                            </tr>
                            </thead>
                            <#--    循环遍历后端传过来的 ModelAndView里的数据orderDTOPage.content 的数据赋值   orderDTO        -->
                            <#list orderDTOPage.content as orderDTO>

                                <tbody>
                                <tr>
                                    <td>
                                        ${orderDTO.orderId}
                                    </td>
                                    <td>
                                        ${orderDTO.buyerName}
                                    </td>
                                    <td>
                                        ${orderDTO.buyerPhone}
                                    </td>
                                    <td>
                                        ${orderDTO.buyerAddress}
                                    </td>
                                    <td>
                                        ${orderDTO.orderAmount}
                                    </td>
                                    <td>
                                        ${orderDTO.getOrderStatusEnum().message}
                                    </td>
                                    <td>
                                        微信
                                    </td>
                                    <td>
                                        ${orderDTO.getPatStatusEnum().message}
                                    </td>
                                    <td>
                                        ${orderDTO.createTime}
                                    </td>
                                    <td>
                                        <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a>
                                    </td>
                                    <td>
                                        <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
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
                            <#list 1..orderDTOPage.getTotalPages() as index>
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
                            <#if currentPage gte orderDTOPage.getTotalPages()>
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


    <#-- 弹窗  -->
    <div class="modal fade" id="modal-container-401674" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h4 class="modal-title" id="myModalLabel">
                                    提醒
                                </h4>
                            </div>
                            <div class="modal-body">
                                你有新的订单！
                            </div>
                            <div class="modal-footer">
                                <button type="button" onclick="javascript:document.getElementById('notice').onpause;" class="btn btn-default" data-dismiss="modal">关闭</button>
                                <button type="button" onclick="location.reload()" class="btn btn-primary">查看新的订单</button>
                            </div>
                        </div>
                    </div>
                </div>

    <#--播放音乐-->
    <audio id="notice" loop="loop">
        <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
    </audio>

    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script>
        var webSocket = null;
        //判断浏览器有没有此功能
        if("WebSocket" in window){
            webSocket = new WebSocket('ws://shawlen.natapp1.cc/sell/webSocket');
        }else{
            alert('该浏览器不支持');
        }

        webSocket.onopen = function (event) {
            console.log("建立连接");
        }

        webSocket.onclose = function (event) {
            console.log("关闭连接");
        }
        
        webSocket.onmessage = function (event) {
            console.log("收到消息："+ event.data);
            //弹窗提醒，或者播放音乐
            $('#modal-container-401674').modal('show');
            document.getElementById('notice').play();
        }

        webSocket.onerror = function (event) {
            console.log("webSocket通信发生错误！");
        }

        //关闭窗口时webSocket要关闭
        window.onbeforeunload = function (event) {
            webSocket.close();
        }

    </script>


    </body>

</html>

