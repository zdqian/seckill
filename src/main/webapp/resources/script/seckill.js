// 存放主要交互逻辑js代码
// 模块化
var seckill={
		// 封装秒杀相关ajax的url
		URL : {
			now : function(){
				return '/seckill/time/now';
			},
			exposer : function(seckillId){
				return '/seckill/'+ seckillId + '/exposer';
			},
			execute : function(seckillId,md5){
				return '/seckill/'+seckillId+'/'+md5+'/execute';
			}
		},
		
		// 手机验证和登录
		validatePhone : function(phone){
		// console.log("validate phone");
			if (phone && phone.length == 11 && !isNaN(phone)) {
				return true;
			} else {
				return false;
			}
		},
		
		handleSeckillkill : function(seckillId, node){
			node.hide()
				.html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
			$.post(seckill.URL.exposer(seckillId),{},function(result){
				// 在回调函数中，执行交互流程
				if (result && result['success']) {
					var exposer = result['data'];
					if (exposer['exposed']) {
						// 开启秒杀
						var md5 = exposer['md5'];
						var killUrl = seckill.URL.execute(seckillId, md5);
						console.log('killUrl: ' + killUrl);
						
						// 绑定一次点击事件，防止重复点击
						$('#killBtn').one('click', function(){
							// 执行秒杀请求
							// 1.先禁用按钮
							$(this).addClass('disabled');
							// 2.发送秒杀请求执行秒杀
							$.post(killUrl,{},function(result){
								if (result && result['success']) {
									var killResult = result['data'];
									var state = killResult['state'];
									var stateInfo = killResult['stateInfo'];
									console.log("stateInfo: " + stateInfo);
									// 3.显示秒杀结果
									node.html('<span class="label label-success">' + stateInfo +'</span>');
								}
							});
						});
						node.show();
					} else {
						// 秒杀未开始（客户端计时有偏差，服务器端为准）
						var now = exposer['now'];
						var start = exposer['start'];
						var end = exposer['end'];
						// 重新计算计时逻辑
						seckill.countdown(seckillId, now, start, end);
					}
				} else {
					console.log('result: ' + result);
				}
			});
		},
		
		countdown : function(seckillId, nowTime, startTime, endTime){
			var seckillBox = $('#seckill-box');
			if (nowTime > endTime) {
				// 秒杀结束
				seckillBox.html('秒杀结束了');
			} else if (nowTime < startTime) {
				// 秒杀未开始
				var killTIme = new Date(startTime + 1000);
				seckillBox.countdown(killTime,function(event){// 回调函数，每次时间改变都会调用
					// 时间格式
					var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
					seckillBox.html(format);
					// 时间完成时回调事件
				}).on('finish.countdown', function(){
					// 获取秒杀地址，控制实现逻辑，秒杀开始
					seckill.handleSeckillkill(seckillId,seckillBox);
				});
			} else {
				// 秒杀开始
				seckill.handleSeckillkill(seckillId,seckillBox);
			}
		},
		
		// 详情页秒杀逻辑
		detail : {
			// 详情页初始化
			init : function(params){
				// 手机验证和登录，计时交互
				// 交互流程: 在cookie中查找手机号，因为后台没做登录
				var killPhone = $.cookie('killPhone');
				var seckillId = params['seckillId'];
				var startTime = params['startTime'];// 毫秒
				var endTime = params['endTime'];
				
//				console.log("init:" + seckillId);
				if (!seckill.validatePhone(killPhone)) {
					// 绑定手机号 控制输出
					var killPhoneModal = $('#killPhoneModal');
					killPhoneModal.modal({
						// 显示弹出层
						show:true,// 显示弹出层
						backdrop:'static',// 禁止位置关闭
						keyboard:false// 关闭键盘响应
					});
					
					$('.btn').click(function(){
						var inputPhone = $('#killPhoneKey').val();
						console.log("inputPhone: " + inputPhone);
						if (seckill.validatePhone(inputPhone)) {
							// 电话写入cookie
							$.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});// 有效期，在路径下有效
							// 刷新页面
							window.location.reload();
							console.log("inputPhone2: " + inputPhone);
						} else {
							$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号有误！</label>').show(300);
						}
					});
				}
				// 已经登录
				// 计时交互：
				$.get(seckill.URL.now(), {}, function(result){
					if (result && result['success']) {
						var nowTime = result['data'];
						// 时间判断
						seckill.countdown(seckillId, nowTime, startTime, endTime);
					} else {
						console.log('result: ' + result);
					}
				});
			}
		}
}