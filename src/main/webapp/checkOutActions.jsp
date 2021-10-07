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
            Kasa
            <small class="h6">Kasa Hareketleri</small>
        </h3>

        <div class="row">

            <div class="col-sm-4 mb-3">
                <div class="card cardBackground1" id="card">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;">Toplam Kasaya Giren</h5>
                            <h4><strong> ${ dbUtil.payInTotal()} </strong></h4>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 mb-3">
                <div class="card cardBackground2" id="card">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;">Toplam Kasadan Çıkan</h5>
                            <h4><strong> ${ dbUtil.payOutTotal()} </strong></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 mb-3">
                <div class="card cardBackground3" id="card">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;"> Kasada Kalan</h5>
                            <h4><strong> ${ dbUtil.payInTotal() - dbUtil.payOutTotal()}  </strong></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 mb-3">
                <div class="card cardBackground4" id="card">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;"> Bugün Kasaya Giriş</h5>
                            <h4><strong> ${ dbUtil.payInTodayTotal()}  </strong></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 mb-3">
                <div class="card cardBackground4" id="card">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;"> Bugün Kasadan Çıkan</h5>
                            <h4><strong> ${ dbUtil.payOutTodayTotal()} </strong></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 mb-3">
                <a href="payIn.jsp">
                    <div class="card cardBackground4" id="card">
                        <div class="card-body">
                            <div style="display: flex; justify-content: space-between;">
                                <h5 style="align-self: center;"> Kasa Yönetimi</h5>
                                <i class="fas fa-link fa-2x" style="color: white; align-self: center;"></i>
                            </div>
                        </div>
                    </div>
                </a>
            </div>



        </div>

        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Arama / Rapor</div>

            <form class="row p-3">

                <div class="col-md-3 mb-3">
                    <label for="cname" class="form-label">Müşteriler</label>
                    <select id="cname" name="cname_select" class="selectpicker CustomerSelect" data-width="100%" data-show-subtext="true" data-live-search="true">
                        <option value="">Tümü</option>
                        <c:forEach items="${ dbUtil.customerList()}" var="item">
                            <option value="${item.cu_id}" data-subtext="<c:out value="${item.cu_code}"></c:out>"><c:out value="${item.cu_name }"></c:out> <c:out value="${item.cu_surname }"></c:out></option>

                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-3 mb-3">
                    <label for="ctype" class="form-label">Tür</label>
                    <select class="form-select" name="ctype" id="ctype">
                        <option data-subtext="">Seçim Yapınız</option>
                        <option value="1">Giriş</option>
                        <option value="2">Çıkış</option>
                    </select>
                </div>

                <div class="col-md-3 mb-3">
                    <label for="startDate" class="form-label">Başlama Tarihi</label>
                    <input type="date" name="startDate" id="startDate" class="form-control" />
                </div>

                <div class="col-md-3 mb-3">
                    <label for="endDate" class="form-label">Bitiş Tarihi</label>
                    <input type="date" name="endDate" id="endDate" class="form-control" />
                </div>

                <div class="col-md-3">
                    <button onclick="allPayOutAction()" id="button" type="button" class="col btn btn-outline-primary">Gönder</button>
                </div>
            </form>
        </div>

        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Arama Sonuçları</div>
            <div class="table-responsive">
                <table class="align-middle mb-0 table table-borderless table-striped table-hover">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Başlık</th>
                        <th>Fiyat</th>
                        <th>Detay</th>
                        <th>Türü</th>
                        <th>Tarih</th>
                    </tr>
                    </thead>
                    <tbody id="checkOutRaw">
                    <!-- for loop  -->

                    </tbody>
                </table>
            </div>
        </div>




    </div>
</div>

<jsp:include page="inc/js.jsp"></jsp:include>

<script src="js/checkOutAction.js"></script>



<script>


    function allPayOutAction() {
        console.log(this)
        var select = document.getElementById('ctype');
        var selectedType = select.options[select.selectedIndex].value;

        var startDate = document.getElementById('startDate').value;

        var endDate = document.getElementById('endDate').value;

        var cid = $('select[name=cname_select] option').filter(':selected').val()


        if(cid.length==0){

            cid = 0;
        }

            $.ajax({
                url: './checkPayOutAction-get?startDate='+startDate +'&endDate='+endDate + '&cid='+cid+ '&selectedType='+selectedType,
                type: 'GET',
                dataType: 'Json',
                success: function (data) {
                    alert("işlem başarılı")

                    if(selectedType==2){
                        createRow(data);

                    }else{
                        createCustomer(data);
                    }

                },
                error: function (err) {
                    console.log(err)
                }
            })








    }


</script>
</body>

</html>