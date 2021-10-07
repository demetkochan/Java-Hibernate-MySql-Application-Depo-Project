let select_id = 0;
$('#add_payout_form').submit( ( event ) => {

    event.preventDefault();

    const payOutTitle =  $("#payOutTitle").val()
    const payOutType = $("#payOutType").val()
    const payOutTotal =  $("#payOutTotal").val()
    const payOutDetail = $("#payOutDetail").val()



    const  obj = {
        payout_title: payOutTitle,
        payout_type: payOutType,
        payout_price: payOutTotal,
        payout_detail: payOutDetail,
        date: '2021-09-03'

    }

    if(select_id !=0){
        obj["payout_id"] = select_id;
    }

    $.ajax({
        url: './payout-post',
        type: 'POST',
        data: { obj: JSON.stringify(obj) },
        dataType: 'JSON',
        success: function (data) {
            if ( data > 0 ) {
                alert("İşlem Başarılı")
                fncResetPayOut();

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

function allPayOut() {

    $.ajax({
        url: './payout-get',
        type: 'GET',
        dataType: 'Json',
        success: function (data) {
            createRow(data);
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
        let new_pay_type = ""
        if(itm.payout_type == 1){
            new_pay_type = 'Nakit'
        }
        else if(itm.payout_type == 2){
            new_pay_type = 'Kredi Kartı'
        }
        else if(itm.payout_type == 3){
            new_pay_type = 'Havale'
        }
        else if(itm.payout_type == 4){
            new_pay_type = 'EFT'
        }
        else if(itm.payout_type == 5){
            new_pay_type = 'Banka Çeki'
        }

        const pay_type = new_pay_type;

        html += `<tr role="row" class="odd">
            <td>`+itm.payout_id+`</td>
            <td>`+itm.payout_title+`</td>
            <td>`+pay_type+`</td>
            <td>`+itm.payout_price+`</td>
            <td>`+itm.payout_detail+`</td>
            <td class="text-right" >
              <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                <button onclick="fncPayOutDelete(`+itm.payout_id+`)" type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
                <button onclick="fncPayOutDetail(`+i+`)" data-bs-toggle="modal" data-bs-target="#payOutDetailModal" type="button" class="btn btn-outline-primary "><i class="far fa-file-alt"></i></button>
                <button onclick="fncPayOutUpdate(`+i+`)"type="button" class="btn btn-outline-primary "><i class="fas fa-pencil-alt"></i></button>
              </div>
            </td>
          </tr>`;
    }
    $('#payoutRaw').html(html);
}
allPayOut();

function fncResetPayOut() {
    select_id = 0;
    $('#add_payout_form').trigger("reset");
    allPayOut();
}


function fncPayOutDelete( payout_id ) {
    let answer = confirm("Silmek istediğinizden emin misniz?");
    if ( answer ) {

        $.ajax({
            url: './payout-delete?payout_id='+payout_id,
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


function fncPayOutDetail(i){
    const itm = globalArr[i];

    let new_pay_type = ""
    if(itm.payout_type == 1){
        new_pay_type = 'Nakit'
    }
    else if(itm.payout_type == 2){
        new_pay_type = 'Kredi Kartı'
    }
    else if(itm.payout_type == 3){
        new_pay_type = 'Havale'
    }
    else if(itm.payout_type == 4){
        new_pay_type = 'EFT'
    }
    else if(itm.payout_type == 5){
        new_pay_type = 'Banka Çeki'
    }

    const pay_type = new_pay_type;


    $("#payout_id").text(itm.payout_id);
    $("#payout_title").text(itm.payout_title);
    $("#payout_type").text(pay_type);
    $("#payout_price").text(itm.payout_price);
    $("#payout_detail").text(itm.payout_detail == "" ? '---------' : itm.payout_detail);



}

function fncPayOutUpdate(i){

    const itm = globalArr[i];


    select_id = itm.payout_id

    $("#payOutTitle").val(itm.payout_title)
    $("#payOutType").val(itm.payout_type)
    $("#payOutTotal").val(itm.payout_price)
    $("#payOutDetail").val(itm.payout_detail)




}
