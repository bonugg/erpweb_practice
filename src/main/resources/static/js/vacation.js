$(".meeting_td").css("display", "none");
$(".business_td").css("display", "none");
$('#vacation').on('click', function () {
    $(".vacation_td").css("display", "");
    $(".meeting_td").css("display", "none");
    $(".business_td").css("display", "none");
});
$('#meeting').on('click', function () {
    $(".meeting_td").css("display", "");
    $(".vacation_td").css("display", "none");
    $(".business_td").css("display", "none");
});
$('#business').on('click', function () {
    $(".business_td").css("display", "");
    $(".vacation_td").css("display", "none");
    $(".meeting_td").css("display", "none");
});
if ($('#hidden_p').text() === "승인") {
    $('#access_btn').prop('disabled', true);
}
if ($('#hidden_p').text() === "반려") {
    $('#cancle_btn').prop('disabled', true);
}


const showvacationview = (VNO) => {
    location.href = "/vacationAcessView?VNO=" + VNO;
}
const showmeetingview = (VNO) => {
    location.href = "/meetingAcessView?VNO=" + VNO;
}
const showbusinessiew = (VNO) => {
    location.href = "/businessAcessView?VNO=" + VNO;
}
const Accessva = (VNO, DEPT, CLASSIFY) => {
    location.href = "/Access?VNO=" + VNO + "&DEPT=" + DEPT + "&CLASSIFY=" + CLASSIFY;
}
const Cancle = (VNO, CLASSIFY) => {
    location.href = "/Cancle?VNO=" + VNO + "&CLASSIFY=" + CLASSIFY;
}


const leave_type = document.getElementById('leave_type');
const start_date = document.getElementById('start');
const end_date = document.getElementById('end');


leave_type.addEventListener('change', function() {
    if (leave_type.value === '반차') {
        start_date.type = 'datetime-local';  // 반차인 경우, input 타입을 datetime-local로 변경
        end_date.type = 'datetime-local';  // 반차인 경우, input 타입을 datetime-local로 변경
    } else {
        start_date.type = 'date';  // 그 외의 경우, input 타입을 date로 변경
        end_date.type = 'date';  // 그 외의 경우, input 타입을 date로 변경
    }
});