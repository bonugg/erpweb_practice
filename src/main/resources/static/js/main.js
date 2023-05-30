document.addEventListener('DOMContentLoaded', function () {
    $(function () {
        const request = $.ajax({
            url: "/production/monthPlan",
            method: "GET",
            dataType: "json"
        });
        request.done(function (data) {
            const calendarEl = document.getElementById('calendar');
            $('#business').css("backgroundColor", "lightgray");
            $('#vacation').css("backgroundColor", "lightgray");
            $('#meeting').css("backgroundColor", "lightgray");

            let filteredVacationData = data.filter(function (event) {//휴가가 아닌 데이터 필터링
                return event.classify === '휴가';
            }).map(function (event) {
                return {
                    ...event,
                    backgroundColor: '#008000',
                    borderColor : '#008000'
                };
            });

            let nonVacationData = data.filter(function (event) {
                return event.classify === '출장';
            }).map(function (event) {
                return {
                    ...event,
                    backgroundColor: 'indianred',
                    borderColor : 'indianred'
                };
            });

            let nonVacationData2 = data.filter(function (event) {
                return event.classify === '회의';
            }).map(function (event) {
                return {
                    ...event,
                    backgroundColor: 'orange',
                    borderColor : 'orange'
                };
            });


            let newEventData = nonVacationData.concat(filteredVacationData).concat(nonVacationData2)
                .map(function(event) {
                    const newEvent = Object.assign({}, event); // 객체 복제
                    const endDate = new Date(newEvent.end); // 'end' 날짜 문자열을 Date 객체로 변환
                    endDate.setDate(endDate.getDate() + 1); // 날짜를 1일 추가
                    newEvent.end = endDate.toISOString().substring(0, 10); // ISO 8601 문자열로 변환 후, 문자열 일부를 추출
                    return newEvent;
                });

            let calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                headerToolbar: {
                    left: 'today prev',
                    center: 'title',
                    right: 'next'
                },
                firstDay: 1, //월요일부터 시작
                locale: "ko",
                dayMaxEvents: true, // 이벤트가 오버되면 높이 제한 (+ 몇 개식으로 표현)
                events: newEventData,
            });
            calendar.render();

            // #vation 버튼 클릭 이벤트 핸들러 등록
            $('#vacation').on('click', function () {
                let filteredEvents = data.filter(function (event) {
                    return event.classify === '휴가';
                }).map(function (event) {
                    return {
                        ...event,
                        backgroundColor: '#008000',
                        borderColor : '#008000'
                    };
                }).map(function(event) {
                        const newEvent = Object.assign({}, event); // 객체 복제
                        const endDate = new Date(newEvent.end); // 'end' 날짜 문자열을 Date 객체로 변환
                        endDate.setDate(endDate.getDate() + 1); // 날짜를 1일 추가
                        newEvent.end = endDate.toISOString().substring(0, 10); // ISO 8601 문자열로 변환 후, 문자열 일부를 추출
                        return newEvent;
                    });

                $('#vacation').css("backgroundColor", "white");
                $('#meeting').css("backgroundColor", "lightgray");
                $('#business').css("backgroundColor", "lightgray");
                $('#all').css("backgroundColor", "lightgray");
                calendar.removeAllEvents();
                calendar.addEventSource(filteredEvents);
            });
            $('#meeting').on('click', function () {
                let filteredEvents = data.filter(function (event) {
                    return event.classify === '회의';
                }).map(function (event) {
                    return {
                        ...event,
                        backgroundColor: 'orange',
                        borderColor : 'orange'
                    };
                }).map(function(event) {
                    const newEvent = Object.assign({}, event); // 객체 복제
                    const endDate = new Date(newEvent.end); // 'end' 날짜 문자열을 Date 객체로 변환
                    endDate.setDate(endDate.getDate() + 1); // 날짜를 1일 추가
                    newEvent.end = endDate.toISOString().substring(0, 10); // ISO 8601 문자열로 변환 후, 문자열 일부를 추출
                    return newEvent;
                });
                $('#meeting').css("backgroundColor", "white");
                $('#vacation').css("backgroundColor", "lightgray");
                $('#business').css("backgroundColor", "lightgray");
                $('#all').css("backgroundColor", "lightgray");
                calendar.removeAllEvents();
                calendar.addEventSource(filteredEvents);
            });
            $('#business').on('click', function () {
                let filteredEvents = data.filter(function (event) {
                    return event.classify === '출장';
                }).map(function (event) {
                    return {
                        ...event,
                        backgroundColor: 'indianred',
                        borderColor : 'indianred'
                    };
                }).map(function(event) {
                    const newEvent = Object.assign({}, event); // 객체 복제
                    const endDate = new Date(newEvent.end); // 'end' 날짜 문자열을 Date 객체로 변환
                    endDate.setDate(endDate.getDate() + 1); // 날짜를 1일 추가
                    newEvent.end = endDate.toISOString().substring(0, 10); // ISO 8601 문자열로 변환 후, 문자열 일부를 추출
                    return newEvent;
                });
                $('#business').css("backgroundColor", "white");
                $('#all').css("backgroundColor", "lightgray");
                $('#vacation').css("backgroundColor", "lightgray");
                $('#meeting').css("backgroundColor", "lightgray");
                calendar.removeAllEvents();
                calendar.addEventSource(filteredEvents);
            });
            $('#all').on('click', function () {
                $('#all').css("backgroundColor", "white");
                $('#business').css("backgroundColor", "lightgray");
                $('#vacation').css("backgroundColor", "lightgray");
                $('#meeting').css("backgroundColor", "lightgray");
                calendar.removeAllEvents();
                calendar.addEventSource(newEventData);
            });
        });
    });
});