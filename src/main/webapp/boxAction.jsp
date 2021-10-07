<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<%@ page import="entities.Admin" %>

<jsp:useBean id="dbUtil" class="utils.DBUtil"></jsp:useBean>

<% Admin adm = dbUtil.isLogin(request, response); %>

<html lang="en">

<head>
    <title>Yönetim</title>
    <jsp:include page="inc/css.jsp"></jsp:include>
</head>

<body>
<div class="wrapper d-flex align-items-stretch">
    <jsp:include page="inc/sideBar.jsp"></jsp:include>
    <!-- Page Content  -->
    <div id="content" class="p-4 p-md-5">
        <jsp:include page="inc/topMenu.jsp"></jsp:include>
        <h3 class="mb-3">
            Satış Yap
            <small class="h6">Satış Yönetim Paneli</small>
        </h3>

        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Yeni Satış</div>

            <form class="row p-3" id="order_add_form">


                <div class="col-md-3 mb-3">
                    <label for="cname" class="form-label">Müşteriler</label>
                    <select id="cname" name="cname_select" class="selectpicker CustomerSelect" data-width="100%" data-show-subtext="true" data-live-search="true">
                        <option data-subtext="">Seçim Yapınız</option>
                        <c:forEach items="${ dbUtil.customerList()}" var="item">
                            <option value="${item.cu_id}" data-subtext="<c:out value="${item.cu_code}"></c:out>"><c:out value="${item.cu_name }"></c:out> <c:out value="${item.cu_surname }"></c:out></option>

                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-3 mb-3">
                    <label for="pname" class="form-label">Ürünler</label>
                    <select id="pname" class="selectpicker" data-width="100%" data-show-subtext="true" data-live-search="true">
                        <option data-subtext="">Seçim Yapınız</option>
                        <c:forEach items="${ dbUtil.ProductList()}" var="item">
                            <option value="${item.pro_id}" data-subtext="<c:out value="${item.pro_code}"></c:out>"><c:out value="${item.pro_title }"></c:out> </option>

                        </c:forEach>
                    </select>
                </div>


                <div class="col-md-3 mb-3">
                    <label for="box_count" class="form-label">Adet</label>
                    <input type="number" min="1" max="100" name="box_count" id="box_count" class="form-control" />
                </div>


                <div class="col-md-3 mb-3">
                    <label for="bNo" class="form-label">Fiş No</label>
                    <input type="text" min="1" max="100" name="bNo" id="bNo" class="form-control" />
                </div>

                <div class="btn-group col-md-3 " role="group">
                    <button type="submit" class="btn btn-outline-primary mr-1">Ekle</button>
                </div>
            </form>
        </div>


        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Sepet Ürünleri</div>


            <div class="table-responsive">
                <table class="align-middle mb-0 table table-borderless table-striped table-hover">
                    <thead>
                    <tr>
                        <th>BId</th>
                        <th>Müşteri</th>
                        <th>Ürün</th>
                        <th>Fiyat</th>
                        <th>Adet</th>
                        <th>Fiş No</th>
                        <th class="text-center" style="width: 55px;" >Sil</th>
                    </tr>
                    </thead>
                    <tbody id="orderRaw">
                    <!-- for loop  -->

                    </tbody>
                </table>
            </div>
        </div>

        <div class="btn-group col-md-3 " role="group">
            <button onclick="completePayment()" type="submit" class="btn btn-outline-primary mr-1">Satışı Tamamla</button>
        </div>

    </div>
</div>

<jsp:include page="inc/js.jsp"></jsp:include>

<script src="js/boxAction.js"></script>

<script>


    function completePayment(){

        var customer_id = $('select[name=cname_select] option').filter(':selected').val()

        $.ajax({
            url: './paymentComplete-post',
            type: 'POST',
            data: { user_id: customer_id },
            dataType: 'JSON',
            success: function (data) {
                if ( data > 0 ) {
                    alert("İşlem Başarılı")


                }else {
                    alert("İşlem sırasında hata oluştu!");
                }
            },
            error: function (err) {
                console.log(err)
                alert("İşlem işlemi sırısında bir hata oluştu!");
            }
        })



    }


</script>


</body>

</html>