#根据动态条件查询examine
#三表关联查询所有数据，返回所有字段
SELECT
	* 
FROM
	examine ex,
	sys_user su,
	sys_office so 
WHERE
	ex.del_flag = 0 
	AND ex.examine_user_id = su.id 
	AND su.office_id = so.id;

# 返回部分字段，指定查询条件,分页查询
SELECT
	ex.id,
	ex.type,
	ex.create_date,
	ex.score,
	su.NAME user_name,
	so.NAME office_name 
FROM
	examine ex,
	sys_user su,
	sys_office so 
WHERE
	ex.del_flag = 0 
	AND ex.examine_user_id = su.id 
	AND su.office_id = so.id 
	AND ex.type = 1 
	AND so.id = 56 
	AND su.NAME LIKE CONCAT( '%', '工作', '%' ) 
	LIMIT 5;
	
	
#根据条件动态查询资质信息
#1.查询所有的资质信息
SELECT
	qu.id,
	qu.type,
	qu.`check`,
	qu.description,
	qu.create_date,
	qu.update_date,
	uu.NAME upload_user_name,
	cu.NAME check_user_name 
FROM
	qualification qu
	LEFT JOIN sys_user uu ON qu.upload_user_id = uu.id
	LEFT JOIN sys_user cu ON qu.check_user_id = cu.id 
WHERE
	qu.del_flag = 0 
	
#2.添加查询条件
SELECT
	qu.id,
	qu.type,
	qu.`check`,
	qu.description,
	qu.create_date,
	qu.update_date,
	uu.NAME upload_user_name,
	cu.NAME check_user_name 
FROM
	qualification qu
	LEFT JOIN sys_user uu ON qu.upload_user_id = uu.id
	LEFT JOIN sys_user cu ON qu.check_user_id = cu.id 
WHERE
	qu.del_flag = 0 
	AND qu.type = 1 
	AND qu.`check` = 0 
	AND DATE( qu.create_date ) >= '2019-01-01' 
	AND DATE( qu.create_date ) <= '2019-01-11'


#获取日期时间的年月日
select  DATE(SYSDATE()) from dual;


#根据条件查询法律法规
SELECT
	statute.id,
	statute.type,
	statute.title,
	statute.pub_date,
	statute.stem_from 
FROM
	statute 
WHERE
	type = 1
	
#根据动态条件查询工单列表
#查询所有工单
SELECT
	wo.*,
	cu.NAME create_user_name,
	co.NAME create_office_name,
	tu.NAME transport_user_name,
	ru.NAME recipient_user_name 
FROM
	work_order wo
	LEFT JOIN sys_user cu ON wo.create_user_id = cu.id
	LEFT JOIN sys_office co ON cu.office_id = co.id #查询发起用户和发起单位
	LEFT JOIN sys_user tu ON wo.transport_user_id = tu.id
	LEFT JOIN sys_office `to` ON tu.office_id = `to`.id #查询运输用户和运输单位
	LEFT JOIN sys_user ru ON wo.recipient_user_id = ru.id
	LEFT JOIN sys_office ro ON ru.office_id = ro.id 
WHERE
	wo.del_flag =0
	
#根据条件查询工单列表
SELECT
	wo.*,
	cu.NAME create_user_name,
	co.NAME create_office_name,
	tu.NAME transport_user_name,
	ru.NAME recipient_user_name 
FROM
	work_order wo
	LEFT JOIN sys_user cu ON wo.create_user_id = cu.id
	LEFT JOIN sys_office co ON cu.office_id = co.id #查询发起用户和发起单位
	LEFT JOIN sys_user tu ON wo.transport_user_id = tu.id
	LEFT JOIN sys_office `to` ON tu.office_id = `to`.id #查询运输用户和运输单位
	LEFT JOIN sys_user ru ON wo.recipient_user_id = ru.id
	LEFT JOIN sys_office ro ON ru.office_id = ro.id 
WHERE
	wo.del_flag = 0 
	AND wo.`status` = 1 
	AND DATE( wo.create_date ) >= '2016-09-01' 
	AND DATE( wo.create_date ) <= '2016-11-30' 
	AND ( co.id = 56 OR `to`.id = 56 OR ro.id = 56 )



#查询电子台账详单

#1.查询电子台账信息
SELECT
	wo.`code`,
	wo.id,
	wo.`status`,
	cu.NAME create_user_name,
	cu.phone create_user_phone,
	co.NAME create_office_name,
	tu.NAME transport_user_name,
	tu.phone transport_user_phone,
	`to`.NAME transport_office_name,
	ru.NAME recipient_user_name,
	ru.phone recipient_user_phone,
	ro.name recipient_office_name
FROM
	work_order wo
	LEFT JOIN sys_user cu ON wo.create_user_id = cu.id
	LEFT JOIN sys_office co ON cu.office_id = co.id #查询发起用户和发起单位
	LEFT JOIN sys_user tu ON wo.transport_user_id = tu.id
	LEFT JOIN sys_office `to` ON tu.office_id = `to`.id #查询运输用户和运输单位
	LEFT JOIN sys_user ru ON wo.recipient_user_id = ru.id
	LEFT JOIN sys_office ro ON ru.office_id = ro.id 
WHERE
	wo.del_flag = 0 
and
	wo.id = 11



#2.查询电子台账的详细危废品
SELECT
	de.*,
	wt.CODE waste_type_code,
	wt.NAME waste_type_name,
	wa.CODE waste_code 
FROM
	detail de,
	waste_type wt,
	waste wa 
WHERE
	de.del_flag = 0 
	AND de.work_order_id = 11 
	AND de.waste_type_id = wt.id 
	AND de.waste_id = wa.id




#3.查询转运记录
SELECT
	tr.*,
	su.NAME user_name,
	su.phone user_phone 
FROM
	transfer tr,
	sys_user su 
WHERE
	tr.del_flag = 0 
	AND tr.work_order_id = 11 
	AND tr.oprate_user_id = su.id 
ORDER BY
	tr.create_date DESC

#根据区域名查询区域信息或根据区域id查询其所有子级区域
#1.根据区域名查询区域信息
SELECT
	sub.*,
	parent.NAME parent_name 
FROM
	sys_area sub
	LEFT JOIN sys_area parent ON sub.parent_id = parent.id 
WHERE
	sub.del_flag = 0 
	AND sub.NAME LIKE concat( '%', '山', '%' )

#2.根据区域id查询其所有子级区域
SELECT
	sub.*,
	parent.NAME parent_name 
FROM
	sys_area sub
	LEFT JOIN sys_area parent ON sub.parent_id = parent.id 
WHERE
	sub.del_flag = 0 
	AND sub.parent_ids LIKE concat( '%', ',1931,', '%' )

#删除区域和子区域
UPDATE sys_area 
SET del_flag = 0 
WHERE
	id = 1931 
	OR parent_ids LIKE concat( '%', ',1931,', '%' )


#根据区域id查询区域和父区域名
SELECT
	sub.*,
	parent.NAME parent_name 
FROM
	sys_area sub
	LEFT JOIN sys_area parent ON sub.parent_id = parent.id 
WHERE
	sub.del_flag = 0 
and
	sub.id = 1931
	
	
#更新区域信息   更新佛山的上级区域为广州
#1.更新本区域的上级区域
UPDATE sys_area 
SET parent_id = '1932',
parent_ids = '0,1,1931,1932,' 
WHERE
	id = 1976 
	
	
#2.更新本区域的所有子级区域的parent_ids
#replace(str,from,to)  替换(需要替换的字段,原字符串，新字符串)
#新字符串：父区域的parent_ids+父区域的id+","
UPDATE sys_area 
SET parent_ids = REPLACE ( parent_ids, '0,1,1931,', '0,1,1931,1932,' ) 
WHERE
	parent_ids LIKE concat( '%', ',1976,', '%' )
	

#查询角色列表 
SELECT
	sr.*,
	so.NAME office_name 
FROM
	sys_role sr,
	sys_office so 
WHERE
	sr.del_flag = 0 
	AND sr.office_id = so.id 
	AND sr.data_scope = 1 
	AND sr.office_id = 56 
	AND sr.NAME LIKE CONCAT( '%', '管理', '%' ) 
	--  and
--  sr.remarks like CONCAT('%','管理','%')


#角色分配人员
#1.查询角色已分配人员
SELECT
	su.id,
	su.NAME 
FROM
	sys_role sr,
	sys_user_role sur,
	sys_user su 
WHERE
	sr.del_flag = 0 
	AND su.del_flag = 0 
	AND sur.del_flag = 0
	AND sr.id = sur.role_id 
	AND sur.user_id = su.id 
	AND sr.id =2


#2.找出公司未分配指定角色的人员
SELECT
	* 
FROM
	sys_user 
WHERE
	office_id = 2 
	AND del_flag = 0 
	AND id NOT IN (
SELECT
	su.id
FROM
	sys_role sr,
	sys_user_role sur,
	sys_user su 
WHERE
	sr.del_flag = 0 
	AND su.del_flag = 0 
	AND sur.del_flag = 0
	AND sr.id = sur.role_id 
	AND sur.user_id = su.id 
	AND sr.id = 2 
	)


#根据角色id查询角色信息和公司名
SELECT
	sr.*,
	so.NAME office_name 
FROM
	sys_role sr,
	sys_office so 
WHERE
	sr.del_flag = 0 
	and so.del_flag = 0
	AND sr.office_id = so.id 
	AND sr.id = 27
	
#根据角色id查询权限信息SELECT
	sre.id,
	sre.`name`,
	sre.common,
	sre.icon,
	sre.sort,
	sre.parent_id,
	sre.type,
	sre.url,
	sre.description,
	sre.`status`,
	sre.parent_ids,
	sre.create_date,
	sre.update_date,
	sre.create_by,
	sre.update_by,
	sre.del_flag,
	sre.permission_str 
FROM
	sys_role AS sro,
	sys_role_resource AS srr,
	sys_resource AS sre 
WHERE
	sro.id = srr.role_id 
	AND srr.resource_id = sre.id 
	AND sro.del_flag = 0 
	AND srr.del_flag = 0 
	AND sre.del_flag = 0
	and sro.id = 27

#根据角色id查询已授权公司
SELECT
	sof.* 
FROM
	sys_role sr,
	sys_role_office sro,
	sys_office sof 
WHERE
	sr.del_flag = 0 
	AND sro.del_flag = 0 
	AND sof.del_flag = 0 
	AND sr.id = 27 
	AND sr.id = sro.role_id 
	AND sro.office_id = sof.id

#根据用户id查询器角色信息
SELECT
	sr.* 
FROM
	sys_role sr,
	sys_user_role sur,
	sys_user su 
WHERE
	sr.id = sur.role_id 
	AND sur.user_id = su.id 
	AND sr.del_flag = 0 
	AND sur.del_flag = 0 
	AND su.del_flag = 0 
	AND su.id =40

#根据用户的角色id查询权限信息  在服务端去重
SELECT
	sre.* 
FROM
	sys_resource sre,
	sys_role_resource srr,
	sys_role sro 
WHERE
	sre.del_flag = 0 
	AND srr.del_flag = 0 
	AND sro.del_flag = 0 
	AND sre.id = srr.resource_id 
	AND srr.role_id = sro.id 
	AND sro.id IN ( 2, 24, 25 )
