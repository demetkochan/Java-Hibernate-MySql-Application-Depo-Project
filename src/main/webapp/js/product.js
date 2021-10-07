
let select_id = 0
$('#product_add_form').submit( ( event ) => {
    event.preventDefault();

    const ptitle = $("#ptitle").val()
    const purchase_price = $("#purchase_price").val()
    const sale_price = $("#sale_price").val()
    const pcode = $("#pcode").val()
    const ptax = $("#ptax").val()
    const punit = $("#punit").val()
    const pamount = $("#pamount").val()
    const pdetail = $("#pdetail").val()


    const obj = {
        pro_title: ptitle,
        pro_purchase_price: purchase_price,
        pro_sale_price: sale_price,
        pro_code: pcode,
        pro_KDV: ptax,
        pro_unit: punit,
        pro_amount: pamount,
        pro_detail: pdetail

    }

    if ( select_id != 0 ) {
        // update
        obj["pro_id"] = select_id;
    }

    $.ajax({
        url: './product-post',
        type: 'POST',
        data: { obj: JSON.stringify(obj) },
        dataType: 'JSON',
        success: function (data) {
            if ( data > 0 ) {
                alert("İşlem Başarılı")
                fncResetProduct();
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

function allProduct() {

    $.ajax({
        url: './product-get',
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
        let new_KDV = ""
        if(itm.pro_KDV == 1){
            new_KDV = '%1'
        }
        else if(itm.pro_KDV== 2){
            new_KDV = '%8'
        }
        else if(itm.pro_KDV == 3){
            new_KDV = '%18'
        }

        const kdv = new_KDV ;

        let new_unit = ""
        if(itm.pro_unit == 1){
           new_unit= 'Adet'
        }
        else if (itm.pro_unit == 2){
            new_unit ='KG'
        }
        else if(itm.pro_unit == 3){
            new_unit ='Metre'
        }
        else if(itm.pro_unit == 4){
            new_unit ='Paket'
        }
        else if(itm.pro_unit == 5){
            new_unit = 'Litre'
        }
        const unit = new_unit

        html += `<tr role="row" class="odd">
            <td>`+itm.pro_id+`</td>
            <td>`+itm.pro_title+`</td>
            <td>`+itm.pro_purchase_price+`</td>
            <td>`+itm.pro_sale_price+`</td>
            <td>`+itm.pro_code+`</td>
            <td>`+ kdv +`</td>
            <td>`+unit+`</td>
            <td>`+itm.pro_amount+`</td>
            <td class="text-right" >
              <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                <button onclick="fncProductDelete(`+itm.pro_id+`)" type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
                <button onclick="fncProductDetail(`+i+`)" data-bs-toggle="modal" data-bs-target="#productDetailModel" type="button" class="btn btn-outline-primary "><i class="far fa-file-alt"></i></button>
                <button onclick="fncProductUpdate(`+i+`)" type="button" class="btn btn-outline-primary "><i class="fas fa-pencil-alt"></i></button>
              </div>
            </td>
          </tr>`;
    }
    $('#productRow').html(html);
}

function codeGenerator() {
    const date = new Date();
    const time = date.getTime();
    const key = time.toString().substring(4);
    $('#pcode').val( key )
    $('#ccode').val( key )

}
allProduct();

function fncResetProduct() {

    select_id = 0;
    $('#product_add_form').trigger("reset");

    codeGenerator()
    allProduct();
}

function fncProductDelete( pro_id ) {
    let answer = confirm("Silmek istediğinizden emin misniz?");
    if ( answer ) {

        $.ajax({
            url: './product-delete?pro_id='+pro_id,
            type: 'DELETE',
            dataType: 'text',
            success: function (data) {
                if ( data != "0" ) {
                    fncResetProduct();
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

function fncProductDetail(i){
    const itm = globalArr[i];

    let new_KDV = ""
    if(itm.pro_KDV == 1){
        new_KDV = '%1'
    }
    else if(itm.pro_KDV== 2){
        new_KDV = '%8'
    }
    else if(itm.pro_KDV == 3){
        new_KDV = '%18'
    }

    const kdv = new_KDV ;

    let new_unit = ""
    if(itm.pro_unit == 1){
        new_unit= 'Adet'
    }
    else if (itm.pro_unit == 2){
        new_unit ='KG'
    }
    else if(itm.pro_unit == 3){
        new_unit ='Metre'
    }
    else if(itm.pro_unit == 4){
        new_unit ='Paket'
    }
    else if(itm.pro_unit == 5){
        new_unit = 'Litre'
    }
    const unit = new_unit


    $("#pro_title").text(itm.pro_title + " "  + " - " + itm.pro_id);
    $("#pro_purchase_price").text(itm.pro_purchase_price );
    $("#pro_sale_price").text(itm.pro_sale_price);
    $("#pro_code").text(itm.pro_code);
    $("#pro_KDV").text(kdv);
    $("#pro_unit").text(unit);
    $("#pro_amount").text(itm.pro_amount);
    $("#pro_detail").text(itm.pro_detail);


}

function fncProductUpdate(i){

    const itm = globalArr[i];


    select_id = itm.pro_id

    $("#ptitle").val(itm.pro_title)
    $("#purchase_price").val(itm.pro_purchase_price)
    $("#sale_price").val(itm.pro_sale_price)
    $("#pcode").val(itm.pro_code)
    $("#ptax").val(itm.pro_KDV)
    $("#punit").val(itm.pro_unit)
    $("#pamount").val(itm.pro_amount)
    $("#pdetail").val(itm.pro_detail)






}




