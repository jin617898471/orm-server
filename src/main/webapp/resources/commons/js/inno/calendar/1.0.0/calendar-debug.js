define("inno/calendar/1.0.0/calendar-debug",["$","calendar","./calendar-debug.css"],function(require,exports,module){
	var $=require("$"),
		AraleCalendar=require("calendar");

	require("./calendar-debug.css");

	var Calendar=AraleCalendar.extend({
		attrs: {
            
        },
        setup: function () {
            Calendar.superclass.setup.call(this);
        },
	});

	module.exports =Calendar;
});