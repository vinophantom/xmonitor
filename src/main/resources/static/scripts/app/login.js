
'use strict';

const currRootUrl = window.location.host;
const currProtocol = window.location.protocol;



function enterPress(e){if(e.keyCode === 13)$('#login-btn').click();}

$(function() {

    const form = $(".my-login-validation");

    $.fn.serializeJson=function(){
        var serializeObj={};
        var array=this.serializeArray();
        var str=this.serialize();
        $(array).each(function(){
            if(serializeObj[this.name]){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value;
            }
        });
        return serializeObj;
    };


    $("input[type='password'][data-eye]").each(function(i) {
        var $this = $(this),
            id = 'eye-password-' + i,
            el = $('#' + id);

        $this.wrap($("<div/>", {
            style: 'position:relative',
            id: id
        }));

        $this.css({
            paddingRight: 60
        });
        $this.after($("<div/>", {
            html: 'Show',
            class: 'btn btn-primary btn-sm',
            id: 'passeye-toggle-'+i,
        }).css({
            position: 'absolute',
            right: 10,
            top: ($this.outerHeight() / 2) - 12,
            padding: '2px 7px',
            fontSize: 12,
            cursor: 'pointer',
        }));

        $this.after($("<input/>", {
            type: 'hidden',
            id: 'passeye-' + i
        }));

        const invalid_feedback = $this.parent().parent().find('.invalid-feedback');

        if(invalid_feedback.length) {
            $this.after(invalid_feedback.clone());
        }

        $this.on("keyup paste", function() {
            $("#passeye-"+i).val($(this).val());
        });
        $("#passeye-toggle-"+i).on("click", function() {
            if($this.hasClass("show")) {
                $this.attr('type', 'password');
                $this.removeClass("show");
                $(this).removeClass("btn-outline-primary");
            }else{
                $this.attr('type', 'text');
                $this.val($("#passeye-"+i).val());
                $this.addClass("show");
                $(this).addClass("btn-outline-primary");
            }
        });
    });

    form.submit(function() {
        const form = $(this);
        if (form[0].checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        form.addClass('was-validated');
    });

    function getPwdCookieKey() {
        return "$$$" + form.serializeJson().username + "$$$";
    }

    function savePwdToCookie(password) {
        if(password)
            Cookies.set(getPwdCookieKey(), password);
        else
            Cookies.set("pwd", $("#password").val());
    }
    function delPwdFromCookie() {
        Cookies.remove("pwd");
    }

    $("[name=username]").change(function() {
        const  pwd = Cookies.get(getPwdCookieKey());
        if(pwd && pwd !== "") $("[name=password]").val(pwd);
    });

    $("#login-btn").click(function() {

        if (!form.get(0).checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
            form.addClass("was-validated");
        }
        const formObj = form.serializeJson();
        $.ajax({
            url: currProtocol + "//" + currRootUrl + "/login",
            type: "post",
            data: formObj,
            success: function (result) {
                if(result.code === 0) {
                    if($("#remember").is(":checked")) savePwdToCookie();
                    else delPwdFromCookie();
                    window.location.href = currProtocol + "//" + currRootUrl + "/"
                } else {
                    alert(result.msg);
                }
            }
        });
    });
    
});
