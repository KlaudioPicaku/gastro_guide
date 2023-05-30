$(document).ready(function () {
    if ($('.select').length > 0) {
        $('.select').select2({
            allowClear: true,
            theme:"classic",
            placeholder:"Select an option"
        });
    }
});