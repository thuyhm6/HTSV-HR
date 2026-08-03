标签说明
1.deptTree（部门树）
	name：		必填项，生成文本框id和name
	selected：	选填项，默认选中的部门
	limit：		选填项，人事或者考勤权限选择，可选值 hr/ar ，默认为hr

2.button （按钮）
	btnname：	选填项，按钮图片名字
	onclick：	选填项，点击事件
	limit：		选填项，权限选择，可选值 all/insert/delete/update/select/other ,默认为all
	
		eg:	
		1.<ait:button/> 或者 <ait:button limit="all"/>
			依据拥有的 增、删、改、查 权限生成 增、删、改、查 按钮，点击事件分别为insert()、delete()、update()、select()
			
		2.<ait:button limit="insert"/>
			依据 增加 权限生成 增加 按钮，点击事件为insert()，delete/update/select 类似
			
		3.<ait:button btnname="test" limit="insert"/>
			依据 增加 权限生成 test 按钮(PS:需要在\WebRoot\resources\images\button 文件夹下添加图片 test_zh.gif 和 test_en.gif),点击事件为test()，delete/update/select 类似
			
		4.<ait:button btnname="test" limit="insert" onclick="sss()"/>
			依据 增加 权限生成 test 按钮(PS:需要在\WebRoot\resources\images\button 文件夹下添加图片 test_zh.gif 和 test_en.gif),点击事件为sss()，delete/update/select 类似
			
		5.<ait:button btnname="test" limit="other"/>
			不参考权限，生成test按钮(PS:需要在\WebRoot\resources\images\button 文件夹下添加图片 test_zh.gif 和 test_en.gif),点击事件为test()
		
		6.<ait:button btnname="test" limit="other" onclick="sss()"/>
			不参考权限，生成test按钮(PS:需要在\WebRoot\resources\images\button 文件夹下添加图片 test_zh.gif 和 test_en.gif),点击事件为sss()
			
			
			
			
			
			
			
			