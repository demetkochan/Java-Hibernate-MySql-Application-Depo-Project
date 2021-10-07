let select_id = 0
$('#order_add_form').submit( ( event ) => {

    event.preventDefault();


    const cname = $("#cname").val()
    const pname = $("#pname").val()
    const box_count = $("#box_count").val()
    const bNo = $("#bNo").val()

    const obj = {

        box_customer_id : cname,
        box_product_id : pname,
        box_count: box_count,
        box_receipt: bNo,



    }
    if(select_id !=0){
        obj["box_id"] = select_id;
    }


    $.ajax({
        url: './order-post',
        type: 'POST',
        data: { obj: JSON.stringify(obj) },
        dataType: 'JSON',
        success: function (data) {
            if ( data > 0 ) {
                alert("İşlem Başarılı")
                allOrder(selectedCustomer)



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


function allOrder(cid) {

    $.ajax({
        url: './order-get?cid='+cid,
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

        html += `<tr role="row" class="odd">
                        <td>`+itm.box_id+`</td>
                        <td>`+itm.cu_name+" "+ itm.cu_surname+`</td>
                        <td>`+itm.pro_title+`</td>
                        <td>`+itm.pro_sale_price+`</td>
                        <td>`+itm.box_count+`</td>
                        <td>`+itm.box_receipt+`</td>
                       
                        <td class="text-right" >
                            <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                                <button onclick="fncOrderDelete(`+itm.box_id+`)" type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
                            </div>
                        </td>
                    </tr>`;
    }
    $('#orderRaw').html(html);
}



function fncOrderDelete(box_id){

    let answer = confirm("Silmek istediğinizden emin misniz?");

    if ( answer ) {

        $.ajax({
            url: './order-delete?box_id='+box_id,
            type: 'DELETE',
            dataType: 'text',
            success: function (data) {
                if ( data != "0" ) {
                    allOrder(selectedCustomer);
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

let selectedCustomer = 0;
$("#cname").on("change",function (){
    selectedCustomer = (this.value)
    allOrder(this.value)
})



