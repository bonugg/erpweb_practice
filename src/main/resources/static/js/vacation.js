const showUserList = (VNO) => {
    location.href = "/vacationAcessView?VNO=" + VNO;
}
const Accessva = (VNO, DEPT) => {
    location.href = "/Access?VNO=" + VNO + "&DEPT=" + DEPT;
}
const Cancle = (VNO, DEPT) => {
    location.href = "/Cancle?VNO=" + VNO + "&DEPT=" + DEPT;
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