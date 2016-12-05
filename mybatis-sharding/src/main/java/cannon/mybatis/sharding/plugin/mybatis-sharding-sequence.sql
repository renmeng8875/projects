/**
 * 表名为逻辑表，创建时请转换成物理表
 */
CREATE TABLE `test_sequence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `logic_table` varchar(32) NOT NULL,
  `sequence` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `logic_name_index` (`logic_table`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;