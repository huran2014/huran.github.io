define('comp/mobile/signCalendar', function(require, exports, module) {
    var tpl = require('template/mobile/tpl');
    var globalVars = require('mods/global/vars');
    var channel = require('mods/global/channel');
    var router = require('mods/mobile/router');
    var calendar = require('comp/mobile/calendar');
    var timestampToDate = require('mods/util/transDate');


    var curDate = new Date();
    var data = {
        // 日期
        fixYear: curDate.getFullYear(),
        curYear: curDate.getFullYear(),
        fixMonth: curDate.getMonth() + 1,
        curMonth: curDate.getMonth() + 1,
        curDay: curDate.getDate(),
        dateKey: '',
        // 日历数组
        calArray: [],
        // 下一个月按钮是否隐藏
        nextHide: true,
        // 签到记录缓存
        cacheSignRecord: []
    };
    var comp = Vue.component('app-signCalendar', {
        template: tpl.signCalendar,
        data: function() {
            return data;
        },
        methods: {
            onClose: function() {
                router.replace({
                    path: '/'
                });
            },
            // 点击上一月按钮
            onPrevBtn: function() {
                this.curMonth--;
                if (this.curMonth === 0) {
                    this.curMonth = 12;
                    this.curYear--;
                }
                this.disClick();
                this.renderCal();
            },
            // 点击上一月按钮
            onNextBtn: function() {
                this.curMonth++;
                if (this.curMonth === 13) {
                    this.curMonth = 1;
                    this.curYear++;
                }
                this.disClick();
                this.renderCal();
            },
            // 判断是否显示点击按钮
            disClick: function() {
                if (this.fixYear == this.curYear && this.fixMonth < this.curMonth + 1) {
                    this.nextHide = true;
                } else if (this.curMonth == 1 && this.fixYear < this.curYear) {
                    this.nextHide = true;
                } else {
                    this.nextHide = false;
                }

            },
            // 算某个月的总天数
            getDaysInOneMonth: function(year, month) {
                month = parseInt(month, 10);
                var d = new Date(year, month, 0);
                return d.getDate();
            },
            // 算某个月第一天是星期几
            getFirstDay: function(year, month) {
                month = month - 1;
                var d = new Date(year, month, 1);
                return d.getDay();
            },
            // 日期补0
            fixDate: function(s) {
                return s < 10 ? '0' + s : s;
            },
            // 生成日历数组
            renderDate: function(days, weekday, classData) {
                var date = 1,
                    dateid;
                var i, j;
                this.calArray = [];
                for (j = 0; j < 6; j++) {
                    this.calArray[j] = [];
                    for (i = 0; i < 7; i++) {
                        if (j == 0 && i < weekday) {
                            this.calArray[j].push('');
                        } else if (date <= days) {
                            dateid = this.curYear + '/' + this.fixDate(this.curMonth) + '/' + this.fixDate(date);
                            this.calArray[j].push({
                                'dateid': dateid,
                                'date': date
                            });
                            date++;
                        } else {
                            this.calArray[j].push('');
                        }

                    }
                }
            },
            renderCal: function() {
                var days = this.getDaysInOneMonth(this.curYear, this.curMonth);
                var weekday = this.getFirstDay(this.curYear, this.curMonth);
                this.renderDate(days, weekday);
            },
            // 获取历史纪录
            reqSignRecordApi: function() {
                var dateKey = this.curYear + '|' + this.curMonth;
                this.dateKey = dateKey;
                var _this = this;
                if (!this.cacheSignRecord[dateKey]) {
                    $.ajax({
                        url: globalVars.apiDomain + '/Sign/Index/record/year/' + this.curYear.toString().substr(2) + '/month/' + this.curMonth,
                        type: 'GET',
                        dataType: 'jsonp',
                        success: function(res) {
                            var signRecordData = res.data;
                            // 记录缓存
                            $.each(signRecordData, function(index, value) {
                                value.dateline = timestampToDate(value.dateline);
                            });
                            _this.cacheSignRecord[dateKey] = signRecordData;

                        },
                        error: function() {
                            return;
                        }
                    });
                }

            }
        },
        created: function() {
            // 构建日历数组
            this.renderCal();
            this.reqSignRecordApi();
        }
    });
    module.exports = comp;
});
