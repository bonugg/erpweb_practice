$(document).ready(function () {
    $('.on_div').each(function () {
        let text = $(this).text().trim();

        if (text === '0') {
            $(this).find('.on_img').css('background-color', 'indianred');
        } else if (text === '1') {
            $(this).find('.on_img').css('background-color', 'green');
        } else if (text === '2') {
            $(this).find('.on_img').css('background-color', 'orange');
        }
    });
    $('.off_div').each(function () {
        let text = $(this).text().trim();

        if (text === '0') {
            $(this).find('.off_img').css('background-color', 'indianred');
        } else if (text === '1') {
            $(this).find('.off_img').css('background-color', 'green');
        }
        else if (text === '2') {
            $(this).find('.off_img').css('background-color', 'orange');
        }
    });
});
