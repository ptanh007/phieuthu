$(function() {
    $.fn.datepicker.defaults.format = "dd/mm/yyyy";




});
$(".only-positive-number").on("keypress", function(event) {
    if (event.keyCode < 48 || event.keyCode > 57) {
        event.preventDefault();
    }
});

function formatNumber(num) {
    return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
}