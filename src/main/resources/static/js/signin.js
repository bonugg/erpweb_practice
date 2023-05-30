$('#DEPT').on('change', function() {
    registerCheck();
});
$('#CNO').on('change', function() {
    registerCheck();
});
$('#NAME').on('change', function() {
    registerCheck();
});
$('#PWD').on('change', function() {
    registerCheck();
});
$('#PWDCHECK').on('change', function() {
    registerCheck();
});
$('#EMAIL').on('change', function() {
    registerCheck();
});

registerCheck();
/* 회원가입 유효성 체크 */
function registerCheck() {
    const regCno = /^2023-?([0-9]{4})$/;
    const regEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    const regPassword = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
    const regName = /^[가-힣a-zA-Z]{2,15}$/;
    let isDept = false;
    let isName = false;
    let isPwd = false;
    let isPwdChk = false;
    let isEmail = false;
    let CNO = $('#CNO').val();

    if($.trim($('#DEPT').val()) == '') {
        isDept = false;
    }else {
        $('#DEPT').css("border", "1px solid gray");
        $('#error_dept').text("").show();
        isDept = true;
    }
    console.log(isDept)

    if($.trim($('#NAME').val()) == '') {
        isName = false;
    }else if(!(regName.test($.trim($('#NAME').val())))){
        $('#NAME').css("border", "1px solid red");
        $('#error_name').text('올바른 이름을 입력하세요.').css('color', 'red').show();
        isName = false;
    }else {
        $('#NAME').css("border", "1px solid gray");
        $('#error_name').text("").show();
        isName = true;
    }


    if(CNO == "") {
        $('#error_cno').val("0");
    }else if (!(regCno.test(CNO))) {
        $('#CNO').css("border", "1px solid red");
        $('#error_cno').text('등록된 사번이 아닙니다.').css('color', 'red').show();
        $('#error_cno').val("0");
    }
    else {
        if(CNO.trim().length != 0) {
            $.ajax({
                type: 'POST',
                data: CNO,
                url: '/CnoCheck',
                dataType: 'json',
                contentType: 'application/json; charset=UTF-8',
                success: function (count) {
                    if (count > 0) {
                        $('#CNO').css("border", "1px solid red");
                        $('#error_cno').text('이미 등록된 사번입니다.').css('color', 'red').show();
                        $('#error_cno').val("0");
                        $("#submit").attr("disabled", "disabled");
                    } else {
                        $('#CNO').css("border", "1px solid gray");
                        $('#error_cno').text("").show();
                        $('#error_cno').val("1");
                        if(isDept && isName && isPwd && isPwdChk && isEmail && $('#error_cno').val() == "1"){
                            $("#submit").removeAttr("disabled");
                        }
                    }
                },
                error: function (error) {
                    console.log("1")
                }
            });
        }
    }

    if($.trim($('#PWD').val()) == '') {
        $('#PWD').css("border", "1px solid gray");
        $('#error_pwd').text('하나 이상의 영문, 숫자, 특수문자를 포함하는 8~20글자').css('color', 'black').show();
        isPwd = false;
    }else if(!(regPassword.test($.trim($('#PWD').val())))){
        $('#PWD').css("border", "1px solid red");
        $('#error_pwd').text('올바른 비밀번호를 입력하세요.').css('color', 'red').show();
        isPwd = false;
    }else {
        $('#PWD').css("border", "1px solid gray");
        $('#error_pwd').text("").show();
        isPwd = true;
    }

    if($.trim($('#PWDCHECK').val()) == '') {
        isPwdChk = false;
    }else if(!($.trim($('#PWD').val()) == $.trim($('#PWDCHECK').val()))){
        $('#PWDCHECK').css("border", "1px solid red");
        $('#error_pwdchk').text('비밀번호가 같지 않습니다.').css('color', 'red').show();
        isPwdChk = false;
    }else {
        $('#PWDCHECK').css("border", "1px solid gray");
        $('#error_pwdchk').text("").show();
        isPwdChk = true;
    }

    if($.trim($('#EMAIL').val()) == '') {
        isEmail = false;
    }else if(!(regEmail.test($.trim($('#EMAIL').val())))){
        $('#EMAIL').css("border", "1px solid red");
        $('#error_email').text('올바른 이메일을 입력하세요.').css('color', 'red').show();
        isEmail = false;
    }else {
        $('#EMAIL').css("border", "1px solid gray");
        $('#error_email').text("").show();
        isEmail = true;
    }

    if(isDept && isName && isPwd && isPwdChk && isEmail){
    }else {
        $("#submit").attr("disabled", "disabled");
    }

}