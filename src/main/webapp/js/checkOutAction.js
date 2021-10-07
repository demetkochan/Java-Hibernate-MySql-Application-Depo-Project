
let globalArr = []
function createRow( data ) {
    globalArr = data;
    let html = ``
    for (let i = 0; i < data.length; i++) {
        const itm = data[i];
        console.log(itm);

        html += `<tr role="row" class="odd">
            <td>`+itm.payout_id+`</td>
            <td>`+itm.payout_title+` </td>
            <td>`+itm.payout_price+` </td>
            <td>`+itm.payout_detail+`</td>
            <td>`+itm.payout_type+`</td>
            <td>`+itm.date+`</td>
            
          </tr>`;
    }
    $('#checkOutRaw').html(html);
}

let globalArrType = []
function createCustomer( data ) {
    globalArrType = data;
    let html = ``
    for (let i = 0; i < data.length; i++) {
        const itm = data[i];
        console.log(itm);

        html += `<tr role="row" class="odd">
            <td>`+itm[0].pay_id+`</td>
            <td>`+itm[1].cu_name+ " " +  itm[1].cu_surname+` </td>
            <td>`+itm[0].pay_price+`</td>
            <td>`+itm[0].pay_detail+`</td>
            <td>-------</td>
            <td>`+itm[0].date+`</td>
            
            
          </tr>`;
    }
    $('#checkOutRaw').html(html);
}