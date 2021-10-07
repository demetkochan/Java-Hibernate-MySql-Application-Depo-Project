let selected_id = 0;


$('#add_payIn_form').submit( ( event ) => {
    event.preventDefault();

    const cname = $("#cname").val()
    const bname = $("#demet_id").val()
    const payInTotal = $("#payInTotal").val()
    const payInDetail = $("#payInDetail").val()



    const obj = {

        pay_customer_id: cname,
        pay_receipt_no: bname,
        pay_price:payInTotal,
        pay_detail: payInDetail

    }

    if(selected_id !=0){
        obj["pay_id"] = selected_id;
    }

    $.ajax({
        url: './payInPost-Servlet',
        type: 'POST',
        data: { obj: JSON.stringify(obj) },
        dataType: 'JSON',
        success: function (data) {
            if ( data > 0 ) {
                alert("İşlem Başarılı")
                allPay(selectedCustomer)



            }else {
                alert("İşlem sırasında hata oluştu!");
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem işlemi sırısında bir hata oluştu!");
        }
    })



})



function allPay(cid) {
    alert(cid)
    $.ajax({
        url: './payIn-Servlet?cid='+cid,
        type: 'GET',
        dataType: 'Json',
        success: function (data) {
            console.log(data)

            //createRow(data);
        },
        error: function (err) {
            console.log(err)
        }
    })

}

let globalArr = []
function createRow( data ) {
    globalArr = data;
    let html = ``
    for (let i = 0; i < data.length; i++) {
        const itm = data[i];
        console.log(itm);

        html += `<tr role="row" class="odd">
            <td>`+itm[0].pay_id+`</td>
            <td>`+itm[1].cu_name+` </td>
            <td>`+itm[1].cu_surname+` </td>
            <td>`+itm[0].pay_receipt_no+`</td>
            <td>`+itm[0].pay_price+`</td>
            <td class="text-right" >
              <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                <button onclick="fncPayInDelete(`+itm[0].pay_id+`)" type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
                <button type="button" class="btn btn-outline-primary "><i class="far fa-file-alt"></i></button>
                <button type="button" class="btn btn-outline-primary "><i class="fas fa-pencil-alt"></i></button>
              </div>
            </td>
          </tr>`;
    }
    $('#payRaw').html(html);
}







    //sol tarafta select edilen optionın id si aldık, kullanıcı id si ile ajax isteğine çıkılır get motodu ile

$('#cname').on('change', function() {


    var cid = this.value;

    $.ajax({
        url: './payIn-Servlet?cid='+cid,
        type: 'GET',
        dataType: 'Json',
        success: function (data) {
           console.log(data)
            createRow(data);

            //$("#bname").attr('disabled', false);

            var totalPrice = 0;
            var boxReceipt = null;
            $.each(data,function(key, value)
            {
                boxReceipt = value.box_receipt;
                totalPrice += value.box_count * value.pro_sale_price;

                console.log(value.cu_name)
                console.log(value.box_id)



            });

            $("#box_no").append('<option value=' + boxReceipt + ' data-price= '+ totalPrice + '  >' + boxReceipt + '</option>');
            $(".selectpicker").selectpicker("refresh");



        },
        error: function (err) {
            console.log(err)
        }
    })



    //alert( this.value );
});


$('#box_no').on('change', function() {

    $("#payInTotal").attr('disabled', true);
    var cid = $('#box_no option:selected').data('price');

    $('#payInTotal').val(cid);
    alert(cid)


    //alert( this.value );
});


function fncPayInDelete( pay_id ) {
    let answer = confirm("Silmek istediğinizden emin misniz?");
    if ( answer ) {

        $.ajax({
            url: './payin-delete?pay_id='+pay_id,
            type: 'DELETE',
            dataType: 'text',
            success: function (data) {
                if ( data != "0" ) {
                    fncResetPayOut()
                }else {
                    alert("Silme sırasında bir hata oluştu!");
                }
            },
            error: function (err) {
                console.log(err)
            }
        })
    }
}








