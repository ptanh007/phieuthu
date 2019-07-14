-- we don't know how to generate schema thudb (class Schema) :(
create table hibernate_sequence
(
	next_val bigint null
)
engine=MyISAM
;

create table phieu_thu
(
	id bigint auto_increment,
	ho_ten varchar(255) charset utf8 null,
	ngay_sinh varchar(255) charset utf8 null,
	lop varchar(10) charset utf8 null,
	tien_phai_nop decimal(14) not null,
	thu decimal(14) not null,
	tien_con_lai decimal(14) default '0' not null,
	hoa_don int default '0' not null,
	ngay_gio_thu date not null,
	ngay_gio_tao datetime not null,
	deleted tinyint(1) default '0' not null,
	so_phieu_thu int not null,
	so_hoa_don varchar(10) null,
	constraint phieu_thu_id_uindex
		unique (id)
)
;

create index phieu_thu_lop_index
	on phieu_thu (lop)
;

alter table phieu_thu
	add primary key (id)
;

create table phieu_thu_2
(
	id bigint not null,
	thu decimal(14) null,
	ngay_gio_thu date null,
	ngay_gio_tao datetime null,
	constraint phieu_thu_2_id_uindex
		unique (id)
)
;

alter table phieu_thu_2
	add primary key (id)
;

create table year_sequence
(
	year int not null
		primary key,
	next_val int null
)
;

insert into hibernate_sequence(next_val) values (1);