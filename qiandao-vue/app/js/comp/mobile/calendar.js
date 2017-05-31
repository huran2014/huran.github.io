/**
 * @fileoverview 运营活动，签到活动页日历组件
 * @authors liyifei@le.com
 * @date 2016/03
 */
define('comp/mobile/calendar', function(require, exports, module) {
    var Calendar = {
        // 元素
        _initDom: function() {
            // 签到日历
            this.plSignNode = $('#pl-sign-cal');
        },
        _initVars: function() {
            // 当前日期数据
            this.curDate = new Date();
            this.fixYear = this.curYear = this.curDate.getFullYear();
            this.fixYearAbbr = this.curYearAbbr = this.curDate.getYear();
            this.fixMonth = this.curMonth = this.curDate.getMonth() + 1;
            this.curDay = this.curDate.getDate();
        },
        // 计算日期
        _calDate: function() {
            // 年月
            this.plSignNode.find('[data-role="text-date"]').html(this.curYear + '年' + this.curMonth + '月');
            // 渲染日历
            var days = this._getDaysInOneMonth(this.curYear, this.curMonth);
            var weekday = this._getFirstDay(this.curYear, this.curMonth);
            this._renderDate(days, weekday);
        },
        // 算某个月的总天数
        _getDaysInOneMonth: function(year, month) {
            month = parseInt(month, 10);
            var d = new Date(year, month, 0);
            return d.getDate();
        },
        // 算某个月第一天是星期几
        _getFirstDay: function(year, month) {
            month = month - 1;
            var d = new Date(year, month, 1);
            return d.getDay();
        },
        // 日期补0
        _fixDate: function(s) {
            return s < 10 ? '0' + s : s;
        },
        //奖指定日期的类名改为className
        _changeClass: function(date, className, render) {
            // render是额外奖励的结构
            var calendarTableNode = this.plSignNode.find('[data-role="table-calendar"]');
            var calendarTableNodeChild = calendarTableNode.find('td[data-dateid="' + date + '"]');
            // 有class名字，有渲染模版，额外奖励
            if (className != '' && render) {
                calendarTableNodeChild.removeClass();
                calendarTableNodeChild.addClass(className);
                // 添加额外奖励
                calendarTableNodeChild.attr('data-role', 'gift');
                $(render).appendTo(calendarTableNodeChild);
            } else if (className != '' && !render) {
                // 有class名字，没有渲染模版，签到记录
                calendarTableNodeChild.removeClass();
                calendarTableNodeChild.addClass(className);
            } else if (!calendarTableNodeChild.hasClass('gifting') && !calendarTableNodeChild.hasClass('gift')) {
                // 签到节日
                $(render).appendTo(calendarTableNodeChild);
            }
            return date;
        },
        // 渲染日历
        _renderDate: function(days, weekday, classData) {
            var calendarTableNode = this.plSignNode.find('[data-role="table-calendar"]');
            var a = 1,
                dateid;
            classData = classData || {};
            var i, j;
            for (j = 0; j < 6; j++) {
                for (i = 0; i < 7; i++) {
                    if (j == 0 && i < weekday) {
                        calendarTableNode.find('tr').eq(j).find('td').eq(i).html('<span></span>');
                        calendarTableNode.find('tr').eq(j).find('td').eq(i).removeAttr('data-dateid').removeClass();
                    } else {
                        calendarTableNode.find('tr').eq(j).find('td').eq(i).removeClass();
                        if (a <= days) {
                            dateid = this.curYear + '/' + this._fixDate(this.curMonth) + '/' + this._fixDate(a);
                            calendarTableNode.find('tr').eq(j).find('td').eq(i).html('<span>' + a + '</span>');
                            calendarTableNode.find('tr').eq(j).find('td').eq(i).attr('data-dateid', dateid);
                            a++;
                        } else {
                            calendarTableNode.find('tr').eq(j).find('td').eq(i).html('<span></span>');
                            calendarTableNode.find('tr').eq(j).find("td").eq(i).removeAttr('data-dateid');
                        }
                    }
                }
            }
        },
        // 判断是否显示点击按钮
        _disClick: function() {
            var nextNode = this.plSignNode.find('[data-role="btn-calendar-next"]');
            if (this.fixYear == this.curYear && this.fixMonth < this.curMonth + 1) {
                nextNode.addClass('hide');
            } else if (this.curMonth == 1 && this.fixYear < this.curYear) {
                nextNode.addClass('hide');
            } else {
                nextNode.removeClass('hide');
            }

        },
        // 事件绑定
        _bindEvent: function() {
            var _this = this;
            // 日历翻月
            // 上一个月
            _this.plSignNode.find('[data-role="btn-calendar-prev"]').on('click', function(e) {
                _this.curMonth--;
                if (_this.curMonth === 0) {
                    _this.curMonth = 12;
                    _this.curYear--;
                }
                _this._disClick();
                _this._calDate();

                _this.onRenderSignRecord.call(_this);
                _this.onRenderExtra.call(_this);
            });
            // 下一个月
            _this.plSignNode.find('[data-role="btn-calendar-next"]').on('click', function(e) {
                _this.curMonth++;
                if (_this.curMonth === 13) {
                    _this.curMonth = 1;
                    _this.curYear++;
                }
                _this._disClick();
                _this._calDate();

                _this.onRenderSignRecord.call(_this);
                _this.onRenderExtra.call(_this);
            });
        },
        // 渲染签到记录回调
        onRenderSignRecord: function() {},
        // 渲染连续签到额外奖励回调
        onRenderExtra: function() {},
        // 初始化入口
        init: function() {
            this._initDom();
            this._initVars();
            this._disClick();
            this._calDate();
            this._bindEvent();
        }
    };
    module.exports = Calendar;
});
