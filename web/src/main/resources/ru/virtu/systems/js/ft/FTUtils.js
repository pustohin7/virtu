;(function () {
    if (typeof (Wicket.FTUtils) === "undefined") {
        Wicket.FTUtils = {};
    }

    Wicket.FTUtils.updatePageParameter = function(name, value) {
        if (window.history.replaceState) {
            var params = new URLSearchParams(window.location.search);
            if (value != null) {
                params.set(name, value);
            } else {
                params.delete(name);
            }
            window.history.replaceState({}, '', window.location.pathname + '?' + params.toString());
        }
    };

    Wicket.FTUtils.scrollToHeader = function() {
        $('html, body').animate({
            scrollTop: $("header").outerHeight()
        }, 500)
    };

    Wicket.FTUtils.updateImageURL = function(event, inputId, imgId) {
        if (event.files && event.files[0]) {
            var img = $('#' + imgId);
            var reader = new FileReader();

            reader.onload = function(e) {
                img.attr('src', e.target.result);
                img.parent().show();

                var label = $('label[for="' + inputId + '"]');
                label.hide();
            };

            reader.readAsDataURL(event.files[0]);
        }
    };

    Wicket.FTUtils.removeUploadedImage = function(inputId, imgId) {
        var img = $('#' + imgId);
        img.parent().hide();
        img.attr('src', '//:0');

        var label = $('label[for="' + inputId + '"]');
        label.show();

        var fileInput = $('#' + inputId);
        fileInput.val('');
    }
})();