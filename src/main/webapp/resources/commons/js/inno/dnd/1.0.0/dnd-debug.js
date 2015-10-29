define("inno/dnd/1.0.0/dnd-debug",[ "$", "base", "class", "events" ], function(require, exports, module) {
    var Dnd = null;
    var $ = require("$"), Base = require("base");
    /*
     * static private variable(module global variable)
    */
    var draggingPre = false, // 标识预拖放
    dragging = null, // 标识当前拖放的代理元素
    dropping = null, // 标识当前的目标元素
    diffX = 0, diffY = 0, // diffX, diffY记录鼠标点击离源节点的距离
    obj = null, // 存储当前拖放的dnd
    proxy =null, // 存储拖拽时创建的clone对象
    dataTransfer = {},
    that=null;
    // 存储拖放信息,在dragstart可设置,在drop中可读取
    Dnd = Base.extend({
        attrs: {
            element: {
                value: null,
                getter: function(val){
                    return $(val);
                }
            },
            containment: null,
            axis: false,
            visible: false,
            proxy: null,
            drop: null,
            revert: false,
            revertDuration: 500,
            disabled: false,
            dragCursor: "move",
            dropCursor: "copy",
            zIndex: 9999
        },
        initialize: function(config) {
            Dnd.superclass.initialize.call(this, config);
            console.log(this.get("element"));
            this.bindDrag();
            // 在源节点上存储dnd
            //element.data("dnd", this);
        },
        bindDrag:function(){
            var elem =this.get("element");
            
            elem.on("mousedown",handleEvent);
            elem.on("mousemove",handleEvent);
            elem.on("mouseup", handleEvent);
            elem.on("keydown", handleEvent);
            that =this;
        },
        // 拖放期间不能设置配置
        set: function(option, value) {
            if (obj !== this) {
                Dnd.superclass.set.call(this, option, value, {
                    override: true
                });
            }
        },
        // 开启页面Dnd功能,绑定鼠标,ecs事件
        open: function() {
            $(document).on("mousedown", handleEvent);
            $(document).on("mousemove", handleEvent);
            $(document).on("mouseup", handleEvent);
            $(document).on("keydown", handleEvent);
        },
        // 关闭页面Dnd功能,解绑鼠标,esc事件
        close: function() {
            $(document).off("mousedown", handleEvent);
            $(document).off("mousemove", handleEvent);
            $(document).off("mouseup", handleEvent);
            $(document).off("keydown", handleEvent);
        }
    });
    /*
     * 核心部分,处理鼠标,esc事件,实现拖放逻辑
    */
    function handleEvent(event) {
        var dnd = null;
        switch (event.type) {
          case "mousedown":
            if (event.which === 1) {

                handleConfig(this);
                obj = $(this);
                diffX = event.pageX - obj.offset().left;
                diffY = event.pageY - obj.offset().top;
                draggingPre=true;
                // dnd = $(event.target).data("dnd");
                // 判断是否为可拖放元素 用构造函数实例化或者 
                // 通过data-dnd=true触发, 此时不支持dataTransfer和一系列事件
                // if (dnd === true) {
                //     dnd = new Dnd(event.target, $(event.target).data("config"));
                // } else if (dnd instanceof Dnd === true) {
                //     dnd = $(event.target).data("dnd");
                // } else {
                //     return;
                // }
                // 源节点不允许拖放则返回
                // if (dnd.get("disabled") === true) return;
                // 处理配置合法性
                // handleConfig(dnd);
                // console.log(dnd);
                // obj = dnd;
                // diffX = event.pageX - obj.get("element").offset().left;
                // diffY = event.pageY - obj.get("element").offset().top;
                // draggingPre = true;
                // 阻止默认选中文本
                // event.preventDefault();
            }
            break;

          case "mousemove":

            if(draggingPre ===true){
                executeDragStart();
            }

            if(dragging !== null){
                var xleft = event.pageX - diffX, xtop = event.pageY - diffY;
                console.log(event.pageX ,diffX);
                console.log(event.pageY,diffY);
                dragging.css("left", diffX);
                dragging.css("top", diffY);
                event.preventDefault();
            }
            // if (draggingPre === true) {
            //     // 开始拖放
            //     executeDragStart();
            // }
            // if (dragging !== null) {
            //     // 根据边界和方向一起判断是否drag并执行
            //     executeDrag({
            //         pageX: event.pageX,
            //         pageY: event.pageY
            //     });
            //     // 根据dragging和dropping位置来判断
            //     // 是否要dragenter, dragleave和dragover并执行
            //     executeDragEnterLeaveOver();
            //     // 阻止默认选中文本
            //     event.preventDefault();
            // }
            break;

          case "mouseup":
            // if (dragging !== null) {
            //     dragging.css("cursor", "default");
            //     dragging.focus();
            //     dragging = null;
            //     // 根据dropping判断是否drop并执行
            //     executeDrop();
            //     // 根据revert判断是否要返回并执行
            //     executeRevert();
            //     // 此处传递的dragging为源节点element
            //     obj.trigger("dragend", obj.get("element"), dropping);
            //     obj = null;
            //     dropping = null;
            // } else if (draggingPre === true) {
            //     // 点击而非拖放时
            //     obj.get("proxy").remove();
            //     draggingPre = false;
            //     obj = null;
            // }
            break;

          case "keydown":
            // if (dragging !== null && event.which === 27) {
            //     dragging.css("cursor", "default");
            //     dragging.focus();
            //     dragging = null;
            //     // 返回源节点初始位置
            //     executeRevert(true);
            //     // 此处传递的dragging为源节点element
            //     obj.trigger("dragend", obj.get("element"), dropping);
            //     obj = null;
            //     dropping = null;
            // }
            break;
        }
    }
    /*
     * 显示proxy, 按照设置显示或隐藏源节点element
     * 开始拖放
    */
    function executeDragStart() {
        console.log(proxy)
         var element = obj,  visible = that.get("visible"), dragCusor = that.get("dragCursor"), zIndex = that.get("zIndex");
        // // 按照设置显示或隐藏element
        if (visible === false) {
            element.css("visibility", "hidden");
        }
        proxy.css("z-index", zIndex);
        proxy.css("visibility", "visible");
        proxy.css("cursor", dragCusor);
        proxy.focus();
        dragging = proxy;
        // dataTransfer = {};
        // draggingPre = false;
        // dragging = proxy;
        // obj.trigger("dragstart", dataTransfer, dragging, dropping);
    }
    /*
     * 根据边界和方向一起判断是否drag并执行
    */
    function executeDrag(event) {
        var container = obj.get("containment"), axis = obj.get("axis"), xleft = event.pageX - diffX, xtop = event.pageY - diffY;
        // 是否在x方向上移动并执行
        if (axis !== "y") {
            if (container === null || xleft >= container.offset().left && xleft + dragging.outerWidth() <= container.offset().left + container.outerWidth()) {
                dragging.css("left", xleft);
            } else {
                if (xleft <= container.offset().left) {
                    dragging.css("left", container.offset().left);
                } else {
                    dragging.css("left", container.offset().left + container.outerWidth() - dragging.outerWidth());
                }
            }
        }
        // 是否在y方向上移动并执行
        if (axis !== "x") {
            if (container === null || xtop >= container.offset().top && xtop + dragging.outerHeight() <= container.offset().top + container.outerHeight()) {
                dragging.css("top", xtop);
            } else {
                if (xtop <= container.offset().top) {
                    dragging.css("top", container.offset().top);
                } else {
                    dragging.css("top", container.offset().top + container.outerHeight() - dragging.outerHeight());
                }
            }
        }
        obj.trigger("drag", dragging, dropping);
    }
    /*
     * 根据dragging和dropping位置来判断是否要dragenter,dragleave和dragover并执行
    */
    function executeDragEnterLeaveOver() {
        var element = obj.get("element"), drop = obj.get("drop"), dragCursor = obj.get("dragCursor"), dropCursor = obj.get("dropCursor"), xleft = dragging.offset().left + diffX, xtop = dragging.offset().top + diffY;
        if (drop !== null) {
            if (dropping === null) {
                $.each(drop, function(index, elem) {
                    // 注意检测drop不是element或者proxy本身
                    if (elem.nodeType === 1 && elem !== element.get(0) && elem !== dragging.get(0) && isContain(elem, xleft, xtop) === true) {
                        dragging.css("cursor", dropCursor);
                        dragging.focus();
                        dropping = $(elem);
                        obj.trigger("dragenter", dragging, dropping);
                        return false;
                    }
                });
            } else {
                if (isContain(dropping, xleft, xtop) === false) {
                    dragging.css("cursor", dragCursor);
                    dragging.focus();
                    obj.trigger("dragleave", dragging, dropping);
                    dropping = null;
                } else {
                    obj.trigger("dragover", dragging, dropping);
                }
            }
        }
    }
    /*
     * 根据dropping判断是否drop并执行
     * 当dragging不在dropping内且不需要revert时,将dragging置于dropping中央
    */
    function executeDrop() {
        var element = obj.get("element"), xdragging = obj.get("proxy"), revert = obj.get("revert");
        if (dropping !== null) {
            // 放置时不完全在drop中并且不需要返回的放置中央
            if (isContain(dropping, xdragging) === false && revert === false) {
                xdragging.css("left", dropping.offset().left + (dropping.outerWidth() - xdragging.outerWidth()) / 2);
                xdragging.css("top", dropping.offset().top + (dropping.outerHeight() - xdragging.outerHeight()) / 2);
            }
            // 此处传递的dragging为源节点element
            obj.trigger("drop", dataTransfer, element, dropping);
        }
    }
    /*
     * 根据revert判断是否要返回并执行
     * 若有指定放置元素且dropping为null,则自动回到原处
     * flag为true表示必须返回的,目前用于esc
    */
    function executeRevert(flag) {
        var element = obj.get("element"), xdragging = obj.get("proxy"), drop = obj.get("drop"), revert = obj.get("revert"), revertDuration = obj.get("revertDuration"), visible = obj.get("visible"), xleft = xdragging.offset().left - element.offset().left, xtop = xdragging.offset().top - element.offset().top;
        if (revert === true || flag === true || dropping === null && drop !== null) {
            //代理元素返回源节点初始位置
            element.attr("style", element.data("style"));
            if (visible === false) {
                element.css("visibility", "hidden");
            }
            xdragging.animate({
                left: element.offset().left,
                top: element.offset().top
            }, revertDuration, function() {
                element.css("visibility", "");
                xdragging.remove();
            });
        } else {
            // 源节点移动到代理元素处
            if (element.css("position") === "relative") {
                xleft = (isNaN(parseInt(element.css("left"))) ? 0 : parseInt(element.css("left"))) + xleft;
                xtop = (isNaN(parseInt(element.css("top"))) ? 0 : parseInt(element.css("top"))) + xtop;
            } else if (element.css("position") === "absolute") {
                xleft = xdragging.offset().left;
                xtop = xdragging.offset().top;
            } else {
                element.css("position", "relative");
            }
            if (visible === false) {
                element.css("left", xleft);
                element.css("top", xtop);
                element.css("visibility", "");
                xdragging.remove();
            } else {
                element.animate({
                    left: xleft,
                    top: xtop
                }, revertDuration, function() {
                    xdragging.remove();
                });
            }
        }
    }
    /*
     * 检查配置合法性
     * 不合法的配置采用默认配置
     * 每次拖放时都检查一次,防止用户修改配置
    */
    function handleConfig(dnd) {
        var element = $(dnd), flag = false, value;

        proxy = null;
        // containment不能为element本身
        // element也不能在containment外       
       
        proxy = element.clone();
        proxy.css("position", "absolute");
        proxy.css("margin", "0");
        proxy.css("left", element.offset().left);
        proxy.css("top", element.offset().top);
        proxy.css("visibility", "hidden");
        proxy.appendTo("body");

    }
    /*
     * 判断元素B是否位于元素A内部 or 点(B, C)是否位于A内
     * error为了补全IE9,IE10对offset浮点值的差异, 
     * 目前只是在判断container是否合法时使用error=1.0
    */
    function isContain(A, B, C) {
        var error = C;
        if (typeof B !== "number") {
            if (typeof error !== "number") {
                error = 0;
            }
            return $(A).offset().left - error <= $(B).offset().left && $(A).offset().left + $(A).outerWidth() >= $(B).offset().left + $(B).outerWidth() - error && $(A).offset().top - error <= $(B).offset().top && $(A).offset().top + $(A).outerHeight() >= $(B).offset().top + $(B).outerHeight() - error;
        } else {
            return $(A).offset().left <= B && $(A).offset().left + $(A).outerWidth() >= B && $(A).offset().top <= C && $(A).offset().top + $(A).outerHeight() >= C;
        }
    }

    module.exports = Dnd;
});