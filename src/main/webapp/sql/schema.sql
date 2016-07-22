-- 数据库创建脚本
-- create database seckill character set utf8;

create table `seckill`(
`seckill_id` BIGINT not null auto_increment comment '商品库存id',
`name` varchar(120) not null comment '商品名称',
`number` int not null comment '库存数量',
`start_time` timestamp not null default current_timestamp comment '秒杀开启时间',
`end_time` timestamp not null default current_timestamp comment '秒杀结束时间',
`create_time` timestamp not null default current_timestamp comment '创建时间',
primary key (seckill_id),
-- 便于查询给出索引
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 comment='秒杀库存表';

INSERT INTO seckill(NAME,number,start_time,end_time)
VALUES
('1000秒杀iPhone6s',100,'2016-07-20 00:00:00','2016-7-24 00:00:00'),
('500秒杀iPhone6s',200,'2016-07-20 00:00:00','2016-7-24 00:00:00'),
('300秒杀iPhone6s',300,'2016-07-20 00:00:00','2016-7-24 00:00:00'),
('200秒杀iPhone6s',400,'2016-07-20 00:00:00','2016-7-24 00:00:00');
select * from seckill;
-- 秒杀成功明细表
-- 用户登陆认证相关信息，简化为一个字段手机号
CREATE TABLE success_killed(
`seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
`user_phone` BIGINT NOT NULL COMMENT '用户手机号',
`state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态提示:-1无效 0：成功 1：已付款 -2:系统异常 -3:数据篡改',
`create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
PRIMARY KEY (seckill_id,user_phone),/*联合主键*/
KEY idx_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- 连接数据库控制台
-- mysql -uroot -p