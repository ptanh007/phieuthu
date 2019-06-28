$(function() {
    $.fn.datepicker.defaults.format = "dd/mm/yyyy";




});
$(".only-positive-number").on("keypress", function(event) {
    if (event.keyCode < 48 || event.keyCode > 57) {
        event.preventDefault();
    }
});

$("#lop").blur(function(event){
    var lopStr = $(event.target).val();
    if(!lopStr.match(/[B1TD|B1|B|C|D|P]\d{1,2}-\d{1,2}/g)){
        $("#tienphainop").text('0');
        $("#thu").val('0');
        $("#tienconlai").text('0');
        console.log("Khong hop le");
    } else {
        if(lopStr.startsWith("B1TD")) {
            tienthuBanDau("9.500.000");
            console.log("lopBTD");
        } else if (lopStr.startsWith("B1")>0) {
            tienthuBanDau("7.000.000");
        } else if (lopStr.startsWith("B")>0) {
            if(parseInt(lopStr.substr(1,2)) % 2 == 0){
                tienthuBanDau("7.000.000");
            }else {
                tienthuBanDau("7.500.000");
            }
        } else if (lopStr.startsWith("D")>0) {
            tienthuBanDau("10.000.000");
        } else if (lopStr.startsWith("C")>0) {
            tienthuBanDau("10.000.000");
        }
    }
});

$("#thu").blur(function(event){
    var conlai = $("#tienphainop").val().replace(".","") - $("#thu").val().replace(".","");
    $("#tienconlai").val(conlai);
    $("#tienconlaiTxt").text(conlai);
});

function tienthuBanDau(hocphi){
    $("#tienphainop").val(hocphi);
    $("#thu").val(hocphi);
    $("#tienconlai").val('0');
    $("#tienconlaiTxt").text('0');
}