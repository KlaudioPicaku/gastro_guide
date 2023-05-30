$(document).ready(function() {

//        $("form").submit(function() {
//            $(".date-picker").each(function() {
//                var dateValue = $(this).val();
//                var formattedDate = moment(dateValue, "YYYY-MM-DD").format("YYYY-MM-DD");
//                $(this).val(formattedDate);
//            });
//        });


        if ($(".date-picker").length > 0) {
            $(".date-picker").datepicker({
                autoclose: true,
                todayHighlight: true,
                todayBtn: "linked",
//                format: "yyyy-MM-dd",
                clearBtn: true
            }).datepicker('update', new Date());
        }
    });