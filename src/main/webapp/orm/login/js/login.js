define(function(require, exports) {
	var $ = require("$");
	var test = require('./test')
	// 验证
	test.say();
	$("input[name=account]").blur(
		function() {
			var account = $("input[name=account]").val();
			account = $.trim(account);  
			if (account.length < 1) {
				message = '用户名不能为空 。';
				$(".msgText").html(message);
				return;
			}
			$(".msgText").html("");
		}
	);
	$("input[name=password]").blur(
		function() {
			var password = $("input[name=password]").val();
			password = $.trim(password);  
			if (password.length < 1) {
				message = '密码不能为空。';
				$(".msgText").html(message);
				return;
			}
			$(".msgText").html("");
		}
	);
	
	document.onkeypress = function() {
        var iKeyCode = -1;
        if (arguments[0]) {
            iKeyCode = arguments[0].which;
        }
        else {
            iKeyCode = event.keyCode;
        }
        if (iKeyCode == 13) {
        	check_button();
        }
    };
	
	check_button = function(){
		var account = $("input[name=account]").val();
		var password = $("input[name=password]").val();
		if (account.length < 1) {
			message = '用户名不能为空 。';
			$(".msgText").html(message);
			return false;
		}else if (password.length < 1) {
			message = '密码不能为空。';
			$(".msgText").html(message);
			return false;
		}else{
			$(".msgText").html("");
			$("#logonsubmit").submit();
		}
	};
	
	var flag = true;
	var wenxintishi = $("#wenxintishi");
	var laqname = $("#laqname");
	var browser = "";
	var version = "";
	var versionSearchString = "";
	var dataBrowser = [
			        {
			            string: navigator.userAgent,
			            subString: "Chrome",
			            identity: "Chrome"
			        },
			        {   string: navigator.userAgent,
			            subString: "OmniWeb",
			            versionSearch: "OmniWeb/",
			            identity: "OmniWeb"
			        },
			        {
			            string: navigator.vendor,
			            subString: "Apple",
			            identity: "Safari",
			            versionSearch: "Version"
			        },
			        {
			            prop: window.opera,
			            identity: "Opera",
			            versionSearch: "Version"
			        },
			        {
			            string: navigator.vendor,
			            subString: "iCab",
			            identity: "iCab"
			        },
			        {
			            string: navigator.vendor,
			            subString: "KDE",
			            identity: "Konqueror"
			        },
			        {
			            string: navigator.userAgent,
			            subString: "Firefox",
			            identity: "Firefox"
			        },
			        {
			            string: navigator.vendor,
			            subString: "Camino",
			            identity: "Camino"
			        },
			        {       
			            string: navigator.userAgent,
			            subString: "Netscape",
			            identity: "Netscape"
			        },
			        {
			            string: navigator.userAgent,
			            subString: "MSIE",
			            identity: "Explorer",
			            versionSearch: "MSIE"
			        },
			        {
			            string: navigator.userAgent,
			            subString: "Gecko",
			            identity: "Mozilla",
			            versionSearch: "rv"
			        },
			        {      
			            string: navigator.userAgent,
			            subString: "Mozilla",
			            identity: "Netscape",
			            versionSearch: "Mozilla"
			        }
			    ];
    init = function () {
        browser = searchString(dataBrowser) || "An unknown browser";
        version = searchVersion(navigator.userAgent)
            || searchVersion(navigator.appVersion)
            || "an unknown version";
    };
    searchString = function (data) {
        for (var i=0;i<data.length;i++)  {
            var dataString = data[i].string;
            var dataProp = data[i].prop;
            versionSearchString = data[i].versionSearch || data[i].identity;
            if (dataString) {
                if (dataString.indexOf(data[i].subString) != -1)
                    return data[i].identity;
            }
            else if (dataProp)
                return data[i].identity;
        }
    };
    searchVersion = function (dataString) {
        var index = dataString.indexOf(versionSearchString);
        if (index == -1) return;
        return parseFloat(dataString.substring(index+versionSearchString.length+1));
    };
    init();
    // 判断客户端使用的浏览器是否支持系统
	function isWxts() {
		if ((browser == "Explorer" || browser == "Mozilla") && parseInt(version) > 7) { // IE8.0+浏览器
			flag = false;
			return flag;
		}
		if (browser == "Chrome") { // 谷歌浏览器
			flag = false;
			return flag;
		}
		if (browser == "Firefox") { // 火狐浏览器
			flag = false;
			return flag;
		}
		return flag;
	}

	if (isWxts()) {
		wenxintishi.show();
		var txt = "您当前使用的浏览器为" + browser + version + "，为了确保您正常访问系统，请使用以下浏览器:";
		laqname.html(txt);
	} else {
		wenxintishi.hide();
	}
});