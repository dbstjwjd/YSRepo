$(function () {
    $('.link').hover(
        function() {
            var colors = ['#0a53be', 'deeppink', '#00804b', 'orange'];
            var randomColor = colors[Math.floor(Math.random() * colors.length)];
            $(this).css('color', randomColor);
        },
        function() {
            $(this).css('color', '');
        }
    );
});