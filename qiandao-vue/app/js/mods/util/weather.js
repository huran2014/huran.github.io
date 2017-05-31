// 根据返回的code码获取不同的天气图片
define('mods/util/weather', function(require, exports, module) {
    // 获取天气图片
    var weather = function(code) {
        var imgCode;
        switch (code) {
            case '5':
                imgCode = 1; // 冰雹
                break;
            case '1':
            case '2':
                imgCode = 2; // 多云
                break;
            case '29':
            case '30':
            case '31':
            case '32':
            case '49':
            case '18':
            case '20':
                imgCode = 3; // 风
                break;
            case '53':
            case '54':
            case '55':
            case '56':
            case '57':
            case '58':
                imgCode = 4; //霾
                break;
            case '0':
                imgCode = 5; // 晴
                break;
            case '6':
            case '13':
            case '14':
            case '15':
            case '16':
            case '17':
            case '26':
            case '27':
            case '28':
                imgCode = 7; // 雪
                break;
            case '3':
            case '4':
            case '7':
            case '8':
            case '9':
            case '10':
            case '11':
            case '12':
            case '19':
            case '21':
            case '22':
            case '23':
            case '24':
            case '25':
                imgCode = 8; // 雨
                break;
            default:
                break;
        }
        return imgCode;
    };
    module.exports = weather;
});
