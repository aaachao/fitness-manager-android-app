/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.26-log : Database - fitness_mysql
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fitness_mysql` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `fitness_mysql`;

/*Table structure for table `calories` */

DROP TABLE IF EXISTS `calories`;

CREATE TABLE `calories` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `courseId` int(11) DEFAULT NULL,
  `coachId` int(11) DEFAULT NULL,
  `calories` int(11) DEFAULT NULL,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/*Data for the table `calories` */

insert  into `calories`(`Id`,`userId`,`courseId`,`coachId`,`calories`,`time`) values (1,5,44,12,0,'2019-05-18 00:00:00'),(2,5,44,12,0,'2019-05-18 00:00:00'),(3,5,44,12,0,'2019-05-18 00:00:00'),(4,5,44,12,0,'2019-05-18 00:00:00'),(5,5,44,12,0,'2019-05-18 00:00:00'),(6,5,44,12,720,'2019-05-18 00:00:00'),(7,5,44,12,240,'2019-05-18 00:00:00'),(8,5,44,12,4,'2019-05-18 00:00:00'),(9,5,44,12,2,'2019-05-18 13:00:00'),(10,5,56,NULL,0,'2019-05-18 01:00:00'),(11,5,56,NULL,3,'2019-05-18 00:54:09'),(12,5,44,12,4,'2019-05-18 07:21:04'),(13,5,56,NULL,1,'2019-05-18 16:49:03'),(14,5,44,12,2,'2019-05-18 18:23:59'),(15,5,44,12,2,'2019-05-18 18:51:04'),(16,5,44,12,5,'2019-05-28 13:01:18'),(17,1,58,NULL,300,'2019-06-02 08:41:46'),(18,1,58,NULL,241,'2019-06-02 09:41:09'),(19,1,44,12,15,'2019-06-09 20:18:33'),(20,1,56,12,11,'2019-06-09 20:19:20'),(21,1,59,18,315,'2019-06-09 20:44:11'),(22,1,59,18,315,'2019-06-09 20:44:12'),(23,1,61,17,18,'2019-06-10 13:23:14'),(24,1,56,NULL,10,'2019-06-10 13:25:44'),(25,1,58,18,12,'2019-06-10 13:38:39');

/*Table structure for table `coachfitness` */

DROP TABLE IF EXISTS `coachfitness`;

CREATE TABLE `coachfitness` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `coachId` int(11) NOT NULL,
  `fitnessId` int(11) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

/*Data for the table `coachfitness` */

insert  into `coachfitness`(`Id`,`coachId`,`fitnessId`) values (6,17,58),(12,16,44),(13,12,44),(25,18,58),(26,11,44),(27,12,58),(31,12,67),(32,12,56);

/*Table structure for table `comments` */

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
  `commentId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `newsId` int(11) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `replyUser` varchar(20) DEFAULT '',
  `commentTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`commentId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `comments` */

insert  into `comments`(`commentId`,`userId`,`newsId`,`comment`,`replyUser`,`commentTime`,`status`) values (15,5,21,'这个经验好','','2019-05-28 18:19:54',0),(16,8,21,'厉害','','2019-05-28 18:20:01',0),(17,1,20,'学到了','','2019-05-28 18:20:08',0),(18,1,19,'这条健身经验对我来说太有用了','','2019-06-10 13:24:04',0),(19,1,21,'哇','','2019-06-10 13:24:24',0);

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `courseId` int(11) NOT NULL AUTO_INCREMENT,
  `courseName` varchar(255) CHARACTER SET utf8 NOT NULL,
  `coachId` int(11) DEFAULT NULL,
  `coursedata` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `image` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `calories` int(11) DEFAULT NULL,
  PRIMARY KEY (`courseId`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=latin1;

/*Data for the table `course` */

insert  into `course`(`courseId`,`courseName`,`coachId`,`coursedata`,`status`,`image`,`calories`) values (44,'高温瑜伽',NULL,'        BikramYoga。强调在温度高达摄氏四十度上下的教室里练习体位法，以大量流汗为乐，偶有人因身体不佳受不了而产生呕吐虚脱等症状。因为BikramYoga有专利问题，高温瑜珈练法有的称为HotYoga（热瑜珈）以规避，名称不同内容一样，但是业者会以更好的温湿度调控设备作区隔。',0,'72d882d5-4753-4749-b11d-895bcc0f63e8.png',300),(56,'拉丁舞',NULL,'        拉丁舞是以运动肩部、腹部、腰部、臀部为主的一种舞蹈艺术。参加运动的包括腹直肌、腹内斜肌、腹外斜肌、竖脊肌、背阔肌等上百块肌肉。从上世纪60年代至今，许多科研人员对体育舞蹈的生理和心理作用做过研究，平均每跳一曲拉丁舞，腰部的扭转有160—180次。',0,'79c624fd62a537b49ae8ef97ba864edc.png',279),(58,'羽毛球',NULL,'        无论是进行有规则的羽毛球比赛还是作为一般性的健身活动，都要在场地上不停地进行脚步移动、跳跃、转体、挥拍，合理地运用各种击球技术和步法将球在场上往返对击，从而增大了上肢、下肢和腰部肌肉的力量，加快了锻炼者全身血液循环，增强了心血管系统和呼吸系统的功能。',0,'a0f1a2672479e86437f412a1eaff7e12.png',273),(59,'哑铃',17,'        可以锻炼上肢肌肉及腰、腹部肌肉。如做仰卧起坐的时候在颈后部双手紧握哑铃，可以增加腹肌练习的负荷；手握哑铃做体侧屈或转体运动，可以锻炼腹内、外斜肌；手握哑铃的直臂前举、侧平举等可以锻炼肩部和胸部肌肉.',1,'43e0ab1ca7371c048e76f47019b33484.png',321),(60,'室内自行车',16,'        自行车是克服心脏功能毛病的最佳工具之一。世界上有半数以上的人是死于心脏病的。骑单车不只能藉腿部的运动压缩血液流动，以及把血液从血管末梢抽回心脏，事实上却同时强化了微血管组织，这叫“附带循环”。强化血管可以使你不受年龄的威胁，青春永驻。',1,'ea5a411776b0960bf0f30631733efe49.png',317),(61,'搏击',17,'        对于脂肪堆积过多的中青年人，特别是长时间久坐、致使脂肪堆积在腰腹部的办公一族而言，有氧搏击堪称效果十足的“瘦身”运动。因为它对人体体能的消耗特别大，对心肺功能的要求比较高。因为训练需要大量供血供氧，而在这一过程中，人体的脂肪细胞不断进行活化和分解代谢。',1,'eda5cd4d8e032ab14046b921c79920bf.png',400),(62,'跑步',18,'        跑步是一项有氧运动（跑步速度会影响心率，但一般而言认为跑步的心率应控制在有氧心率区间内），通过跑步，我们能提高肌力，令肌肉量适当地恢复正常的水平，同时提高体内的基础代谢水平，加速脂肪的燃烧，养成易瘦体质。',1,'2c866d6c210dbda8061fa47281a1cc17.png',286),(63,'篮球',18,'        篮球活动涵盖了跑、跳、投等多种身体运动形式，且运动强度较大，因此，它能全面、有效、综合地促进身体素质和人体机能的全面发展，保持和提高人的生命活力，为人的一切活动打下坚实的身体（物质）基础，从而提高生活的质量。',1,'2625ab8ca6ff74d98a96b75793b5963a.png',370);

/*Table structure for table `dailycheck` */

DROP TABLE IF EXISTS `dailycheck`;

CREATE TABLE `dailycheck` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `checkDate` date NOT NULL,
  `checkTime` time DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `dailycheck` */

insert  into `dailycheck`(`id`,`userId`,`checkDate`,`checkTime`,`status`) values (1,1,'2019-05-01','22:29:43',0),(2,1,'2019-05-02','22:29:46',0),(3,1,'2019-05-03','07:31:44',0),(4,1,'2019-05-04','19:56:04',0),(5,1,'2019-05-06','14:28:58',0),(6,1,'2019-05-20','20:52:05',0),(7,1,'2019-05-21','08:01:21',0),(9,1,'2019-05-23','16:53:48',0),(10,19,'2019-05-22','17:58:41',0),(11,19,'2019-05-21','17:58:41',0),(12,19,'2019-05-20','18:40:31',0),(13,19,'2019-05-23','18:48:24',0),(14,19,'2019-05-19','18:54:13',0),(21,19,'2019-05-24','22:14:41',0),(22,1,'2019-06-02','08:42:30',0),(23,1,'2019-06-10','09:32:45',0);

/*Table structure for table `depend` */

DROP TABLE IF EXISTS `depend`;

CREATE TABLE `depend` (
  `dependId` int(11) NOT NULL AUTO_INCREMENT,
  `courseId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `coachId` int(11) DEFAULT NULL,
  PRIMARY KEY (`dependId`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=latin1;

/*Data for the table `depend` */

insert  into `depend`(`dependId`,`courseId`,`userId`,`coachId`) values (153,44,5,12),(159,60,5,12),(160,58,5,NULL),(161,44,1,12),(168,61,1,17),(169,59,1,17),(172,63,1,18),(175,58,1,12),(176,56,1,12);

/*Table structure for table `favors` */

DROP TABLE IF EXISTS `favors`;

CREATE TABLE `favors` (
  `favorId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `newsId` int(11) NOT NULL,
  `favorTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`favorId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `favors` */

insert  into `favors`(`favorId`,`userId`,`newsId`,`favorTime`,`status`) values (1,1,1,'2017-05-05 16:44:47',0),(2,2,1,'2017-05-05 16:44:54',0),(3,1,5,'2017-05-06 13:30:07',0),(4,1,4,'2017-05-06 15:41:31',0),(5,0,6,'2017-05-06 21:25:57',0),(6,1,6,'2017-05-06 21:26:47',0),(7,5,21,'2019-05-20 20:33:37',0),(8,8,20,'2019-05-20 20:33:56',0),(9,8,19,'2019-05-20 20:34:11',0),(10,1,21,'2019-05-21 08:10:53',0),(11,1,20,'2019-05-21 08:11:01',0);

/*Table structure for table `news` */

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `newsId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `releaseTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`newsId`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `news` */

insert  into `news`(`newsId`,`userId`,`title`,`content`,`image`,`releaseTime`,`status`) values (19,8,'健身时间的选择','1、早饭后日出之后运动,运动前30-60分钟吃100克易消化食物,少许牛奶。 \r\n2、早饭一个半小时之后运动。 \r\n3、晚饭前两小时开始，运动前30-60分钟吃100克易消化食物。 \r\n4、晚饭后一个半小时，并且离睡觉一个小时之外，按自己习惯选择。 ','a980a353ddc42e204d7992db6b504ea1.png','2019-05-28 17:19:11',0),(20,3,'健身小技巧','不节食或暴饮暴食。如果你只是为了保持健康的体态，请按时适量进食。增肌和塑性的伙伴们需要足够的营养帮助肌肉恢复以及增长，不然辛苦的训练换不来等价的回报。同时，不要指望大强度的训练能够在短时间帮助你抵消一顿没有节制的晚餐，那只会让你的训练失去应有的价值','0ca37c36003bc28fee89e991ee8e39ac.jpg','2019-06-09 20:14:48',0),(21,5,'自己总结了一个经验','训练前一定要热身，身体疲劳不要勉强，先休息，不要过度训练，不要受伤。','5553fd233edd7431c15108fb5a5672cd.png','2019-05-28 18:19:13',0);

/*Table structure for table `notice` */

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notice` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `notice` */

insert  into `notice`(`id`,`notice`) values (1,'由于热水厂区域6月1日停汽，热水厂无法提供热水，特此通知：本俱乐部于6月1日全天洗浴用水暂停使用，由此给您带来的不便敬请谅解');

/*Table structure for table `supervise` */

DROP TABLE IF EXISTS `supervise`;

CREATE TABLE `supervise` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `coachId` int(11) DEFAULT NULL,
  `courseId` int(11) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `data` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `supervise` */

/*Table structure for table `training` */

DROP TABLE IF EXISTS `training`;

CREATE TABLE `training` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `trainTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `duration` int(2) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `training` */

insert  into `training`(`id`,`userId`,`trainTime`,`duration`,`status`) values (1,1,'2017-05-07 09:01:46',8,0),(2,1,'2017-05-07 09:01:48',8,0),(3,1,'2017-05-07 08:43:19',8,0),(4,1,'2017-05-07 08:56:40',8,0),(5,1,'2017-05-07 09:01:27',8,0);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `registerTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(2) NOT NULL DEFAULT '0',
  `role` varchar(10) NOT NULL DEFAULT 'user',
  `name` varchar(20) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `weight` double(11,1) DEFAULT NULL,
  `data` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`userId`,`username`,`password`,`phone`,`registerTime`,`status`,`role`,`name`,`image`,`weight`,`data`) values (1,'123','123','13212341234','2019-06-09 21:18:25',0,'user','田长青','9fc492f7cd8f5d98002d403d0b316efd.png',50.0,'坚持健身'),(2,'a1','123','13112283210','2019-06-09 21:16:08',0,'user','徐熙颜','3143a7b8cfa9fa5235b66c56e8ad002a.png',40.3,'坚持健身'),(3,'qw1','123','13918567432','2019-06-09 21:16:08',0,'user','黄洋达','91e5bfb86a4fc626760f7454122f7410.png',46.5,'坚持健身'),(4,'admin','123','13293710820','2019-06-09 21:16:54',0,'admin','赵午光','9ee5999c2b4ddfb27e425a3e24462444.png',47.1,'坚持健身'),(5,'12345','123','13497976135','2019-06-09 21:14:57',0,'user','钱浩均','230d9abc77a4190b62d88beb8d6cf532.png',40.5,'坚持健身'),(6,'2','123','13451330841','2019-06-09 21:14:58',0,'user','曹兰','3143a7b8cfa9fa5235b66c56e8ad002a.png',62.3,'坚持健身'),(7,'3','123','13516321509','2019-06-09 21:14:59',0,'user','马维远','31878cee5b8542fc2f53a0c65b77f043.png',60.5,'坚持健身'),(8,'d','123','13900310141','2019-06-09 21:14:59',0,'user','赵桦','311984a5de49628ffbdd218f922d9672.png',52.3,'坚持健身'),(10,'2015','123','15235169715','2019-06-09 21:15:00',0,'admin','董文军','a9ea8e9d5df80961f1b5cffbb23f3146.png',57.1,'坚持健身'),(11,'134','123','13731250991','2019-06-09 21:15:00',0,'teacher','柳书丹','be2af65e6a5b227b21c64c0936990e50.png',60.8,'坚持健身'),(12,'333','123','15731344631','2019-06-09 21:15:01',0,'teacher','罗沁','a346b88fda31a76566430801d1648960.png',68.2,'坚持健身'),(14,'151','123','15183151312','2019-06-09 21:15:02',0,'user','刘勇','ef5c7b22c51beff2e07d2563c07dcba8.png',70.3,'坚持健身'),(15,'1234567','123','13215928731','2019-06-09 21:15:02',0,'teacher','刘兴海','4b4c82fd0432c5c385956e3e0e7be664.png',50.5,'坚持健身'),(16,'1647','123','15123876453','2019-06-09 21:15:03',0,'teacher','王文通','ff16ebedd3320391f5212c2262a0daa1.png',48.3,'坚持健身'),(17,'197','123','13412415197','2019-06-09 21:15:03',0,'teacher','张艾嘉','2087581f69c64547cc943ec35e823b3c.png',50.9,'坚持健身'),(18,'111','123','13832156312','2019-06-09 21:15:04',0,'teacher','胡立阳','511743aec634614f2c547ad6f07a1a00.png',67.7,'坚持健身'),(19,'aaa','123','13512358123','2019-06-09 21:15:33',0,'user','林衡','0c7cac2c764af92a99f10a5518eb069b.png',60.0,'努力'),(20,'14','123','15672332156','2019-06-09 21:15:15',0,'user','叶一茜','c00ada0f835df69ceead72116bd53098.png',76.9,'坚持健身');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
