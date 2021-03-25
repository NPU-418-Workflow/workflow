/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50643
 Source Host           : localhost:3306
 Source Schema         : workflow

 Target Server Type    : MySQL
 Target Server Version : 50643
 File Encoding         : 65001

 Date: 25/03/2021 10:58:22
*/
USE workflow;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity_history_instance
-- ----------------------------
DROP TABLE IF EXISTS `activity_history_instance`;
CREATE TABLE `activity_history_instance`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '历史记录主键',
  `act_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ai_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运行时记录名称',
  `ai_assigner_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运行时执行人类型',
  `ai_assigner_id` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运行时执行人标识',
  `bf_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂接业务表单主键',
  `ai_status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运行时记录状态',
  `ai_create_time` timestamp(0) NULL DEFAULT NULL COMMENT '活动开始时间',
  `ai_update_time` timestamp(0) NULL DEFAULT NULL COMMENT '活动更新时间',
  `usertask_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'xml活动编号',
  `ai_category` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动类型(会签、可回退、普通等等)',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '时间戳',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '时间戳',
  `pi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属流程实例主键',
  `pd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程定义id',
  `active_ti_num` int(11) NULL DEFAULT NULL COMMENT '当前活动未完成实例个数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of activity_history_instance
-- ----------------------------
INSERT INTO `activity_history_instance` VALUES ('1217634887455432705', '1217634887430266882', '请假申请环节', '0', '\"1\"', NULL, '01', '2020-01-16 10:29:14', '2020-01-16 10:29:14', 'startevent1', '01', '2020-01-16 10:29:14', '2020-01-16 10:29:14', '1217634887178608642', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217634887627399169', '1217634887430266882', '请假申请环节', '0', '\"1\"', NULL, '02', '2020-01-16 10:29:14', '2020-01-16 10:29:14', 'startevent1', '01', '2020-01-16 10:29:14', '2020-01-16 10:29:14', '1217634887178608642', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217634887677730817', '1217634887673536513', '动态指定抄送目标环节（演示即席）', '0', '[\"1\"]', '4', '01', '2020-01-16 10:29:14', '2020-01-16 10:29:14', 'ut001', '02', '2020-01-16 10:29:14', '2020-01-16 10:29:14', '1217634887178608642', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217634941910081538', '1217634887673536513', '动态指定抄送目标环节（演示即席）', '0', '[\"1\"]', '4', '02', '2020-01-16 10:29:14', '2020-01-16 10:29:14', 'ut001', '02', '2020-01-16 10:29:27', '2020-01-16 10:29:27', '1217634887178608642', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217634942010744833', '1217634942002356226', '指定成员抄送审阅', '0', '[\"4\"]', '2', '01', '2020-01-16 10:29:27', '2020-01-16 10:29:27', 'ut002', '01', '2020-01-16 10:29:27', '2020-01-16 10:29:27', '1217634887178608642', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217634985891553281', '1217634942002356226', '指定成员抄送审阅', '0', '[\"4\"]', '2', '02', '2020-01-16 10:29:27', '2020-01-16 10:29:27', 'ut002', '01', '2020-01-16 10:29:38', '2020-01-16 10:29:38', '1217634887178608642', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217634985975439361', '1217634985971245057', '经理级审批环节（任意1个）', '1', '[\"2\"]', '3', '01', '2020-01-16 10:29:38', '2020-01-16 10:29:38', 'ut003', '01', '2020-01-16 10:29:38', '2020-01-16 10:29:38', '1217634887178608642', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217634986038353921', '1217634986029965314', 'hx,yy会签审批环节', '0', '[\"2\",\"3\"]', '3', '01', '2020-01-16 10:29:38', '2020-01-16 10:29:38', 'ut004', '02', '2020-01-16 10:29:38', '2020-01-16 10:29:38', '1217634887178608642', '1217633983259922433', 2);
INSERT INTO `activity_history_instance` VALUES ('1217635071933505537', '1217634985971245057', '经理级审批环节（任意1个）', '1', '[\"2\"]', '3', '02', '2020-01-16 10:29:38', '2020-01-16 10:29:38', 'ut003', '01', '2020-01-16 10:29:58', '2020-01-16 10:29:58', '1217634887178608642', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217652216570503169', '1217634986029965314', 'hx,yy会签审批环节', '0', '[\"2\",\"3\"]', '3', '02', '2020-01-16 10:29:38', '2020-01-16 10:29:38', 'ut004', '02', '2020-01-16 11:38:06', '2020-01-16 11:38:06', '1217634887178608642', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217652264570118145', '1217652264561729538', '抄送给张三环节', '0', '[\"4\"]', '2', '01', '2020-01-16 11:38:17', '2020-01-16 11:38:17', 'ut005', '01', '2020-01-16 11:38:17', '2020-01-16 11:38:17', '1217634887178608642', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217652371495510018', '1217652264561729538', '抄送给张三环节', '0', '[\"4\"]', '2', '02', '2020-01-16 11:38:17', '2020-01-16 11:38:17', 'ut005', '01', '2020-01-16 11:38:43', '2020-01-16 11:38:43', '1217634887178608642', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217652371579396097', '1217652371571007489', '李总审批环节', '0', '[\"5\"]', '3', '01', '2020-01-16 11:38:43', '2020-01-16 11:38:43', 'ut007', '01', '2020-01-16 11:38:43', '2020-01-16 11:38:43', '1217634887178608642', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217653171013738497', '1217652371571007489', '李总审批环节', '0', '[\"5\"]', '3', '02', '2020-01-16 11:38:43', '2020-01-16 11:38:43', 'ut007', '01', '2020-01-16 11:41:54', '2020-01-16 11:41:54', '1217634887178608642', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217653187572850689', '1217652264561729538', '抄送给张三环节', '0', '[\"4\"]', '2', '01', '2020-01-16 11:38:17', '2020-01-16 11:41:57', 'ut005', '01', '2020-01-16 11:38:17', '2020-01-16 11:41:57', '1217634887178608642', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217653288877875201', '1217652264561729538', '抄送给张三环节', '0', '[\"4\"]', '2', '02', '2020-01-16 11:38:17', '2020-01-16 11:41:57', 'ut005', '01', '2020-01-16 11:42:22', '2020-01-16 11:42:22', '1217634887178608642', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217653288982732802', '1217652371571007489', '李总审批环节', '0', '[\"5\"]', '3', '01', '2020-01-16 11:38:43', '2020-01-16 11:42:22', 'ut007', '01', '2020-01-16 11:38:43', '2020-01-16 11:42:22', '1217634887178608642', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217653330879635458', '1217652371571007489', '李总审批环节', '0', '[\"5\"]', '3', '02', '2020-01-16 11:38:43', '2020-01-16 11:42:22', 'ut007', '01', '2020-01-16 11:42:32', '2020-01-16 11:42:32', '1217634887178608642', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217692492961849346', '1217692492945072130', '请假申请环节', '0', '\"1\"', NULL, '01', '2020-01-16 14:18:09', '2020-01-16 14:18:09', 'startevent1', '01', '2020-01-16 14:18:09', '2020-01-16 14:18:09', '1217692492471115778', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217692493154787330', '1217692492945072130', '请假申请环节', '0', '\"1\"', NULL, '02', '2020-01-16 14:18:09', '2020-01-16 14:18:09', 'startevent1', '01', '2020-01-16 14:18:09', '2020-01-16 14:18:09', '1217692492471115778', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217692493226090497', '1217692493217701890', '动态指定抄送目标环节（演示即席）', '0', '[\"1\"]', '4', '01', '2020-01-16 14:18:09', '2020-01-16 14:18:09', 'ut001', '02', '2020-01-16 14:18:09', '2020-01-16 14:18:09', '1217692492471115778', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217693449284128770', '1217692493217701890', '动态指定抄送目标环节（演示即席）', '0', '[\"1\"]', '4', '02', '2020-01-16 14:18:09', '2020-01-16 14:18:09', 'ut001', '02', '2020-01-16 14:21:57', '2020-01-16 14:21:57', '1217692492471115778', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217693449548369921', '1217693449539981313', '指定成员抄送审阅', '0', '[\"4\"]', '2', '01', '2020-01-16 14:21:57', '2020-01-16 14:21:57', 'ut002', '01', '2020-01-16 14:21:57', '2020-01-16 14:21:57', '1217692492471115778', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217693568758878209', '1217693449539981313', '指定成员抄送审阅', '0', '[\"4\"]', '2', '02', '2020-01-16 14:21:57', '2020-01-16 14:21:57', 'ut002', '01', '2020-01-16 14:22:25', '2020-01-16 14:22:25', '1217692492471115778', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217693568846958593', '1217693568842764290', '经理级审批环节（任意1个）', '1', '[\"2\"]', '3', '01', '2020-01-16 14:22:25', '2020-01-16 14:22:25', 'ut003', '01', '2020-01-16 14:22:25', '2020-01-16 14:22:25', '1217692492471115778', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217693568893095938', '1217693568884707330', 'hx,yy会签审批环节', '0', '[\"2\",\"3\"]', '3', '01', '2020-01-16 14:22:25', '2020-01-16 14:22:25', 'ut004', '02', '2020-01-16 14:22:25', '2020-01-16 14:22:25', '1217692492471115778', '1217633983259922433', 2);
INSERT INTO `activity_history_instance` VALUES ('1217693671590629378', '1217693568842764290', '经理级审批环节（任意1个）', '1', '[\"2\"]', '3', '02', '2020-01-16 14:22:25', '2020-01-16 14:22:25', 'ut003', '01', '2020-01-16 14:22:50', '2020-01-16 14:22:50', '1217692492471115778', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217693739672571906', '1217693568884707330', 'hx,yy会签审批环节', '0', '[\"2\",\"3\"]', '3', '02', '2020-01-16 14:22:25', '2020-01-16 14:22:25', 'ut004', '02', '2020-01-16 14:23:06', '2020-01-16 14:23:06', '1217692492471115778', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217693739966173185', '1217693739961978882', '抄送给张三环节', '0', '[\"4\"]', '2', '01', '2020-01-16 14:23:06', '2020-01-16 14:23:06', 'ut005', '01', '2020-01-16 14:23:06', '2020-01-16 14:23:06', '1217692492471115778', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217693780118245377', '1217693739961978882', '抄送给张三环节', '0', '[\"4\"]', '2', '02', '2020-01-16 14:23:06', '2020-01-16 14:23:06', 'ut005', '01', '2020-01-16 14:23:15', '2020-01-16 14:23:15', '1217692492471115778', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217693780181159938', '1217693780172771329', '李总审批环节', '0', '[\"5\"]', '3', '01', '2020-01-16 14:23:16', '2020-01-16 14:23:16', 'ut007', '01', '2020-01-16 14:23:16', '2020-01-16 14:23:16', '1217692492471115778', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217693842986668034', '1217693780172771329', '李总审批环节', '0', '[\"5\"]', '3', '02', '2020-01-16 14:23:16', '2020-01-16 14:23:16', 'ut007', '01', '2020-01-16 14:23:30', '2020-01-16 14:23:30', '1217692492471115778', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217694275125809154', '1217694275117420545', '请假申请环节', '0', '\"1\"', NULL, '01', '2020-01-16 14:25:14', '2020-01-16 14:25:14', 'startevent1', '01', '2020-01-16 14:25:14', '2020-01-16 14:25:14', '1217694275071283201', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217694275176140801', '1217694275117420545', '请假申请环节', '0', '\"1\"', NULL, '02', '2020-01-16 14:25:14', '2020-01-16 14:25:14', 'startevent1', '01', '2020-01-16 14:25:14', '2020-01-16 14:25:14', '1217694275071283201', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217694275205500930', '1217694275201306626', '动态指定抄送目标环节（演示即席）', '0', '[\"1\"]', '4', '01', '2020-01-16 14:25:14', '2020-01-16 14:25:14', 'ut001', '02', '2020-01-16 14:25:14', '2020-01-16 14:25:14', '1217694275071283201', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217694296973938689', '1217694275201306626', '动态指定抄送目标环节（演示即席）', '0', '[\"1\"]', '4', '02', '2020-01-16 14:25:14', '2020-01-16 14:25:14', 'ut001', '02', '2020-01-16 14:25:19', '2020-01-16 14:25:19', '1217694275071283201', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217694297053630465', '1217694297045241857', '指定成员抄送审阅', '0', '[\"4\"]', '2', '01', '2020-01-16 14:25:19', '2020-01-16 14:25:19', 'ut002', '01', '2020-01-16 14:25:19', '2020-01-16 14:25:19', '1217694275071283201', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217694342331142146', '1217694297045241857', '指定成员抄送审阅', '0', '[\"4\"]', '2', '02', '2020-01-16 14:25:19', '2020-01-16 14:25:19', 'ut002', '01', '2020-01-16 14:25:30', '2020-01-16 14:25:30', '1217694275071283201', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217694342381473794', '1217694342377279490', '经理级审批环节（任意1个）', '1', '[\"2\"]', '3', '01', '2020-01-16 14:25:30', '2020-01-16 14:25:30', 'ut003', '01', '2020-01-16 14:25:30', '2020-01-16 14:25:30', '1217694275071283201', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217694342423416834', '1217694342419222530', 'hx,yy会签审批环节', '0', '[\"2\",\"3\"]', '3', '01', '2020-01-16 14:25:30', '2020-01-16 14:25:30', 'ut004', '02', '2020-01-16 14:25:30', '2020-01-16 14:25:30', '1217694275071283201', '1217633983259922433', 2);
INSERT INTO `activity_history_instance` VALUES ('1217694458916016129', '1217694342377279490', '经理级审批环节（任意1个）', '1', '[\"2\"]', '3', '02', '2020-01-16 14:25:30', '2020-01-16 14:25:30', 'ut003', '01', '2020-01-16 14:25:57', '2020-01-16 14:25:57', '1217694275071283201', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217694543875837954', '1217694342419222530', 'hx,yy会签审批环节', '0', '[\"2\",\"3\"]', '3', '02', '2020-01-16 14:25:30', '2020-01-16 14:25:30', 'ut004', '02', '2020-01-16 14:26:18', '2020-01-16 14:26:18', '1217694275071283201', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217694543997472770', '1217694543993278465', '抄送给张三环节', '0', '[\"4\"]', '2', '01', '2020-01-16 14:26:18', '2020-01-16 14:26:18', 'ut005', '01', '2020-01-16 14:26:18', '2020-01-16 14:26:18', '1217694275071283201', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217694597923639297', '1217694543993278465', '抄送给张三环节', '0', '[\"4\"]', '2', '02', '2020-01-16 14:26:18', '2020-01-16 14:26:18', 'ut005', '01', '2020-01-16 14:26:30', '2020-01-16 14:26:30', '1217694275071283201', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217694597986553857', '1217694597973970946', '李总审批环节', '0', '[\"5\"]', '3', '01', '2020-01-16 14:26:30', '2020-01-16 14:26:30', 'ut007', '01', '2020-01-16 14:26:30', '2020-01-16 14:26:30', '1217694275071283201', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217694794309341186', '1217694597973970946', '李总审批环节', '0', '[\"5\"]', '3', '02', '2020-01-16 14:26:30', '2020-01-16 14:26:30', 'ut007', '01', '2020-01-16 14:27:17', '2020-01-16 14:27:17', '1217694275071283201', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217695262842458114', '1217695262838263809', '请假申请环节', '0', '\"5\"', NULL, '01', '2020-01-16 14:29:09', '2020-01-16 14:29:09', 'startevent1', '01', '2020-01-16 14:29:09', '2020-01-16 14:29:09', '1217695262792126466', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217695262913761281', '1217695262838263809', '请假申请环节', '0', '\"5\"', NULL, '02', '2020-01-16 14:29:09', '2020-01-16 14:29:09', 'startevent1', '01', '2020-01-16 14:29:09', '2020-01-16 14:29:09', '1217695262792126466', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217695262947315713', '1217695262943121409', '动态指定抄送目标环节（演示即席）', '0', '[\"1\"]', '4', '01', '2020-01-16 14:29:09', '2020-01-16 14:29:09', 'ut001', '02', '2020-01-16 14:29:09', '2020-01-16 14:29:09', '1217695262792126466', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217695559463636994', '1217695262943121409', '动态指定抄送目标环节（演示即席）', '0', '[\"1\"]', '4', '02', '2020-01-16 14:29:09', '2020-01-16 14:29:09', 'ut001', '02', '2020-01-16 14:30:20', '2020-01-16 14:30:20', '1217695262792126466', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217695559509774337', '1217695559505580034', '指定成员抄送审阅', '0', '[\"4\"]', '2', '01', '2020-01-16 14:30:20', '2020-01-16 14:30:20', 'ut002', '01', '2020-01-16 14:30:20', '2020-01-16 14:30:20', '1217695262792126466', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217695603243782145', '1217695559505580034', '指定成员抄送审阅', '0', '[\"4\"]', '2', '02', '2020-01-16 14:30:20', '2020-01-16 14:30:20', 'ut002', '01', '2020-01-16 14:30:30', '2020-01-16 14:30:30', '1217695262792126466', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217695603289919489', '1217695603285725185', '经理级审批环节（任意1个）', '1', '[\"2\"]', '3', '01', '2020-01-16 14:30:30', '2020-01-16 14:30:30', 'ut003', '01', '2020-01-16 14:30:30', '2020-01-16 14:30:30', '1217695262792126466', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217695603336056834', '1217695603331862530', 'hx,yy会签审批环节', '0', '[\"2\",\"3\"]', '3', '01', '2020-01-16 14:30:30', '2020-01-16 14:30:30', 'ut004', '02', '2020-01-16 14:30:30', '2020-01-16 14:30:30', '1217695262792126466', '1217633983259922433', 2);
INSERT INTO `activity_history_instance` VALUES ('1217695701969309697', '1217695603285725185', '经理级审批环节（任意1个）', '1', '[\"2\"]', '3', '02', '2020-01-16 14:30:30', '2020-01-16 14:30:30', 'ut003', '01', '2020-01-16 14:30:54', '2020-01-16 14:30:54', '1217695262792126466', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217695738694635522', '1217695603331862530', 'hx,yy会签审批环节', '0', '[\"2\",\"3\"]', '3', '02', '2020-01-16 14:30:30', '2020-01-16 14:30:30', 'ut004', '02', '2020-01-16 14:31:02', '2020-01-16 14:31:02', '1217695262792126466', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217695738812076034', '1217695738807881730', '抄送给张三环节', '0', '[\"4\"]', '2', '01', '2020-01-16 14:31:02', '2020-01-16 14:31:02', 'ut005', '01', '2020-01-16 14:31:02', '2020-01-16 14:31:02', '1217695262792126466', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217695773008236545', '1217695738807881730', '抄送给张三环节', '0', '[\"4\"]', '2', '02', '2020-01-16 14:31:02', '2020-01-16 14:31:02', 'ut005', '01', '2020-01-16 14:31:11', '2020-01-16 14:31:11', '1217695262792126466', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217695773054373889', '1217695773050179586', '李总审批环节', '0', '[\"5\"]', '3', '01', '2020-01-16 14:31:11', '2020-01-16 14:31:11', 'ut007', '01', '2020-01-16 14:31:11', '2020-01-16 14:31:11', '1217695262792126466', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217695820332568577', '1217695773050179586', '李总审批环节', '0', '[\"5\"]', '3', '02', '2020-01-16 14:31:11', '2020-01-16 14:31:11', 'ut007', '01', '2020-01-16 14:31:22', '2020-01-16 14:31:22', '1217695262792126466', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217698872921853954', '1217698872921853953', '请假申请环节', '0', '\"1\"', NULL, '01', '2020-01-16 14:43:30', '2020-01-16 14:43:30', 'startevent1', '01', '2020-01-16 14:43:30', '2020-01-16 14:43:30', '1217698872691167234', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217698873001545729', '1217698872921853953', '请假申请环节', '0', '\"1\"', NULL, '02', '2020-01-16 14:43:30', '2020-01-16 14:43:30', 'startevent1', '01', '2020-01-16 14:43:30', '2020-01-16 14:43:30', '1217698872691167234', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217698873039294466', '1217698873039294465', '动态指定抄送目标环节（演示即席）', '0', '[\"1\"]', '4', '01', '2020-01-16 14:43:30', '2020-01-16 14:43:30', 'ut001', '02', '2020-01-16 14:43:30', '2020-01-16 14:43:30', '1217698872691167234', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217699065583013889', '1217698873039294465', '动态指定抄送目标环节（演示即席）', '0', '[\"1\"]', '4', '02', '2020-01-16 14:43:30', '2020-01-16 14:43:30', 'ut001', '02', '2020-01-16 14:44:16', '2020-01-16 14:44:16', '1217698872691167234', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217699065654317058', '1217699065650122753', '指定成员抄送审阅', '0', '[\"4\"]', '2', '01', '2020-01-16 14:44:16', '2020-01-16 14:44:16', 'ut002', '01', '2020-01-16 14:44:16', '2020-01-16 14:44:16', '1217698872691167234', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217699172294496257', '1217699065650122753', '指定成员抄送审阅', '0', '[\"4\"]', '2', '02', '2020-01-16 14:44:16', '2020-01-16 14:44:16', 'ut002', '01', '2020-01-16 14:44:41', '2020-01-16 14:44:41', '1217698872691167234', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217699172353216514', '1217699172344827905', '经理级审批环节（任意1个）', '1', '[\"2\"]', '3', '01', '2020-01-16 14:44:41', '2020-01-16 14:44:41', 'ut003', '01', '2020-01-16 14:44:41', '2020-01-16 14:44:41', '1217698872691167234', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217699172411936769', '1217699172407742466', 'hx,yy会签审批环节', '0', '[\"2\",\"3\"]', '3', '01', '2020-01-16 14:44:41', '2020-01-16 14:44:41', 'ut004', '02', '2020-01-16 14:44:41', '2020-01-16 14:44:41', '1217698872691167234', '1217633983259922433', 2);
INSERT INTO `activity_history_instance` VALUES ('1217700295025790978', '1217699172344827905', '经理级审批环节（任意1个）', '1', '[\"2\"]', '3', '02', '2020-01-16 14:44:41', '2020-01-16 14:44:41', 'ut003', '01', '2020-01-16 14:49:09', '2020-01-16 14:49:09', '1217698872691167234', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217700404421627905', '1217699172407742466', 'hx,yy会签审批环节', '0', '[\"2\",\"3\"]', '3', '02', '2020-01-16 14:44:41', '2020-01-16 14:44:41', 'ut004', '02', '2020-01-16 14:49:35', '2020-01-16 14:49:35', '1217698872691167234', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217700404526485506', '1217700404522291202', '抄送给张三环节', '0', '[\"4\"]', '2', '01', '2020-01-16 14:49:35', '2020-01-16 14:49:35', 'ut005', '01', '2020-01-16 14:49:35', '2020-01-16 14:49:35', '1217698872691167234', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217700938255863809', '1217700404522291202', '抄送给张三环节', '0', '[\"4\"]', '2', '02', '2020-01-16 14:49:35', '2020-01-16 14:49:35', 'ut005', '01', '2020-01-16 14:51:42', '2020-01-16 14:51:42', '1217698872691167234', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217700938310389762', '1217700938302001154', '李总审批环节', '0', '[\"5\"]', '3', '01', '2020-01-16 14:51:42', '2020-01-16 14:51:42', 'ut007', '01', '2020-01-16 14:51:42', '2020-01-16 14:51:42', '1217698872691167234', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217701126110351362', '1217700938302001154', '李总审批环节', '0', '[\"5\"]', '3', '02', '2020-01-16 14:51:42', '2020-01-16 14:51:42', 'ut007', '01', '2020-01-16 14:52:27', '2020-01-16 14:52:27', '1217698872691167234', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217701126198431745', '1217700404522291202', '抄送给张三环节', '0', '[\"4\"]', '2', '01', '2020-01-16 14:49:35', '2020-01-16 14:52:27', 'ut005', '01', '2020-01-16 14:49:35', '2020-01-16 14:52:27', '1217698872691167234', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217701186780958721', '1217700404522291202', '抄送给张三环节', '0', '[\"4\"]', '2', '02', '2020-01-16 14:49:35', '2020-01-16 14:52:27', 'ut005', '01', '2020-01-16 14:52:41', '2020-01-16 14:52:41', '1217698872691167234', '1217633983259922433', 0);
INSERT INTO `activity_history_instance` VALUES ('1217701186827096066', '1217700938302001154', '李总审批环节', '0', '[\"5\"]', '3', '01', '2020-01-16 14:51:42', '2020-01-16 14:52:41', 'ut007', '01', '2020-01-16 14:51:42', '2020-01-16 14:52:41', '1217698872691167234', '1217633983259922433', 1);
INSERT INTO `activity_history_instance` VALUES ('1217701248001019905', '1217700938302001154', '李总审批环节', '0', '[\"5\"]', '3', '02', '2020-01-16 14:51:42', '2020-01-16 14:52:41', 'ut007', '01', '2020-01-16 14:52:56', '2020-01-16 14:52:56', '1217698872691167234', '1217633983259922433', 0);

-- ----------------------------
-- Table structure for activity_instance
-- ----------------------------
DROP TABLE IF EXISTS `activity_instance`;
CREATE TABLE `activity_instance`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ai_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动名称（当前环节业务描述）',
  `ai_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动状态(0运行中1已完成)',
  `ai_assigner_id` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动参与者，可以是外键',
  `ai_assigner_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动参与者种类（0个人1职位）',
  `bf_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动关联表单外键',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '活动开启时间',
  `update_time` timestamp(0) NULL DEFAULT '0000-00-00 00:00:00' COMMENT '参与者处理时间',
  `usertask_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'xml活动编号',
  `ai_category` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动类型(会签、可回退、普通等等)',
  `pi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属流程实例标识，外键',
  `pd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程定义id',
  `active_ti_num` int(11) NULL DEFAULT NULL COMMENT '当前活动未完成实例个数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of activity_instance
-- ----------------------------

-- ----------------------------
-- Table structure for business_form
-- ----------------------------
DROP TABLE IF EXISTS `business_form`;
CREATE TABLE `business_form`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `form_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单名称（中文）',
  `form_url` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单访问路径',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of business_form
-- ----------------------------
INSERT INTO `business_form` VALUES ('1', '请假申请表单', '/leave.html', '2020-01-15 14:14:13', NULL);
INSERT INTO `business_form` VALUES ('2', '请假抄送表单', '/send.html', '2020-01-15 14:14:34', NULL);
INSERT INTO `business_form` VALUES ('3', '请假审批表单', '/approval.html', '2020-01-15 23:04:12', NULL);
INSERT INTO `business_form` VALUES ('4', '请假即席信息提交表单', '/personinfo.html', '2020-01-15 15:56:10', NULL);

-- ----------------------------
-- Table structure for element
-- ----------------------------
DROP TABLE IF EXISTS `element`;
CREATE TABLE `element`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '无意义主键',
  `element_no` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '元素编号',
  `element_process_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '元素所属流程主键',
  `token_number` int(11) NULL DEFAULT NULL COMMENT '元素实际发放令牌数量',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '流程元素记录创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '流程元素记录更新时间',
  `element_role` int(255) NULL DEFAULT NULL COMMENT '目前的作用是区分网关作用为分支/聚合',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of element
-- ----------------------------

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gi_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位名称',
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `gi_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES ('1', '普通员工组', '1', '01');
INSERT INTO `group` VALUES ('2', '经理组', '1', '02');
INSERT INTO `group` VALUES ('3', '总经理组', '1', '03');

-- ----------------------------
-- Table structure for leave
-- ----------------------------
DROP TABLE IF EXISTS `leave`;
CREATE TABLE `leave`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ui_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `durations` int(11) NULL DEFAULT NULL COMMENT '时长',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of leave
-- ----------------------------
INSERT INTO `leave` VALUES ('1217634886159372289', '1', 5);
INSERT INTO `leave` VALUES ('1217692491263168513', '1', 5);
INSERT INTO `leave` VALUES ('1217694274639286274', '1', 5);
INSERT INTO `leave` VALUES ('1217695262485958657', '5', 5);
INSERT INTO `leave` VALUES ('1217698872326279170', '1', 5);

-- ----------------------------
-- Table structure for process_definition
-- ----------------------------
DROP TABLE IF EXISTS `process_definition`;
CREATE TABLE `process_definition`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程定义主键',
  `pd_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程定义名称(中文，从xml中抽取)',
  `pd_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应的流程资源文件no，即唯一标识，由画图软件自动生成，从xml中抽取',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '流程定义时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '流程定义更新时间',
  `pt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程模板主键',
  `start_form` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始流程时挂接的表单地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of process_definition
-- ----------------------------
INSERT INTO `process_definition` VALUES ('1217007218975985665', '无意义测试审批项目', 'myDefinition', '2020-01-14 16:55:07', '2020-01-14 16:55:07', '5', '1');
INSERT INTO `process_definition` VALUES ('1217633983259922433', '请假审批', 'myDefinition', '2020-01-16 10:25:39', '2020-01-16 10:25:39', '6', '1');

-- ----------------------------
-- Table structure for process_history_instance
-- ----------------------------
DROP TABLE IF EXISTS `process_history_instance`;
CREATE TABLE `process_history_instance`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pi_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pi_status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前记录动作',
  `pi_starter` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pi_businesskey` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  `pi_create_time` timestamp(0) NULL DEFAULT NULL,
  `pi_update_time` timestamp(0) NULL DEFAULT NULL,
  `pi_endtime` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of process_history_instance
-- ----------------------------
INSERT INTO `process_history_instance` VALUES ('1217653337624076290', '1217634887178608642', 'gwx请假申请', '1217633983259922433', '02', '1', '1217634886159372289', '2020-01-16 11:42:33', '2020-01-16 11:42:33', '2020-01-16 10:29:14', '2020-01-16 11:42:33', '2020-01-16 11:42:33');
INSERT INTO `process_history_instance` VALUES ('1217693843049582593', '1217692492471115778', 'gwx请假申请', '1217633983259922433', '02', '1', '1217692491263168513', '2020-01-16 14:23:30', '2020-01-16 14:23:30', '2020-01-16 14:18:08', '2020-01-16 14:23:30', '2020-01-16 14:23:30');
INSERT INTO `process_history_instance` VALUES ('1217694794602942466', '1217694275071283201', 'gwx请假申请', '1217633983259922433', '02', '1', '1217694274639286274', '2020-01-16 14:27:17', '2020-01-16 14:27:17', '2020-01-16 14:25:13', '2020-01-16 14:27:17', '2020-01-16 14:27:17');
INSERT INTO `process_history_instance` VALUES ('1217695820391288833', '1217695262792126466', 'lz请假申请', '1217633983259922433', '03', '5', '1217695262485958657', '2020-01-16 14:31:22', '2020-01-16 14:31:22', '2020-01-16 14:29:09', '2020-01-16 14:31:22', '2020-01-16 14:31:22');
INSERT INTO `process_history_instance` VALUES ('1217701248068128770', '1217698872691167234', 'gwx请假申请', '1217633983259922433', '03', '1', '1217698872326279170', '2020-01-16 14:52:56', '2020-01-16 14:52:56', '2020-01-16 14:43:30', '2020-01-16 14:52:56', '2020-01-16 14:52:56');

-- ----------------------------
-- Table structure for process_instance
-- ----------------------------
DROP TABLE IF EXISTS `process_instance`;
CREATE TABLE `process_instance`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程实例主键',
  `pi_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程实例名称(自动拼接用户名+模板名称)',
  `pd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程定义标识(外键)',
  `pi_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程执行状态0未开启1运行中2已完成',
  `pi_starter` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程发起人(具体用户ID)',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '流程创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '流程状态更新时间',
  `endtime` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '流程结束时间',
  `pi_businesskey` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程关联的业务数据主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of process_instance
-- ----------------------------

-- ----------------------------
-- Table structure for process_params_record
-- ----------------------------
DROP TABLE IF EXISTS `process_params_record`;
CREATE TABLE `process_params_record`  (
  `pp_record_value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程参数对应的一条记录值，审批任务的值默认为0/1',
  `pp_relation_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属流程参数关系的id',
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `ti_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数所属任务实例标志(数据库主键)(任务级参数)',
  `ai_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数所属活动实例标识(数据库主键)(活动级参数)',
  `pi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数所属流程实例标识(数据库主键)(流程级参数)',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该参数记录状态0有效1无效，主要是针对驳回后产生新的参数记录',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '流程参数记录创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '流程参数记录更新时间',
  `pp_record_level` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标识当前参数级别',
  `engine_pp_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pp_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of process_params_record
-- ----------------------------
INSERT INTO `process_params_record` VALUES ('5', NULL, '1217634887581261826', '1217634887468015617', NULL, NULL, '01', NULL, '2020-01-16 10:29:14', NULL, 'S001', '03');
INSERT INTO `process_params_record` VALUES ('5', NULL, '1217634887593844737', NULL, '1217634887430266882', NULL, '01', NULL, '2020-01-16 10:29:14', NULL, 'S001', '03');
INSERT INTO `process_params_record` VALUES ('4', NULL, '1217634941863944194', '1217634887690313729', NULL, NULL, '01', NULL, '2020-01-16 10:29:27', NULL, 'E004', '02');
INSERT INTO `process_params_record` VALUES ('4', NULL, '1217634941880721409', NULL, '1217634887673536513', NULL, '01', NULL, '2020-01-16 10:29:27', NULL, 'E004', '02');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217635071740567553', '1217634985983827970', NULL, NULL, '01', NULL, '2020-01-16 10:29:58', NULL, 'E001', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217635071753150466', NULL, '1217634985971245057', NULL, '01', NULL, '2020-01-16 10:29:58', NULL, 'E001', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217635117747888130', '1217634986046742530', NULL, NULL, '01', NULL, '2020-01-16 10:30:09', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('2', NULL, '1217635117773053954', NULL, '1217634986029965314', NULL, '01', NULL, '2020-01-16 11:38:05', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217652216495005697', '1217634986059325442', NULL, NULL, '01', NULL, '2020-01-16 11:38:06', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('0', NULL, '1217653170959212545', '1217652371587784706', NULL, NULL, '01', NULL, '2020-01-16 11:41:54', NULL, 'E003', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217653330837692417', '1217653288991121410', NULL, NULL, '01', NULL, '2020-01-16 11:42:32', NULL, 'E003', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217653330854469633', NULL, '1217652371571007489', NULL, '01', NULL, '2020-01-16 11:42:32', NULL, 'E003', '01');
INSERT INTO `process_params_record` VALUES ('5', NULL, '1217692493104455682', '1217692492978626562', NULL, NULL, '01', NULL, '2020-01-16 14:18:09', NULL, 'S001', '03');
INSERT INTO `process_params_record` VALUES ('5', NULL, '1217692493117038593', NULL, '1217692492945072130', NULL, '01', NULL, '2020-01-16 14:18:09', NULL, 'S001', '03');
INSERT INTO `process_params_record` VALUES ('4', NULL, '1217693449208631298', '1217692493238673410', NULL, NULL, '01', NULL, '2020-01-16 14:21:57', NULL, 'E004', '02');
INSERT INTO `process_params_record` VALUES ('4', NULL, '1217693449225408514', NULL, '1217692493217701890', NULL, '01', NULL, '2020-01-16 14:21:57', NULL, 'E004', '02');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217693671540297729', '1217693568855347202', NULL, NULL, '01', NULL, '2020-01-16 14:22:50', NULL, 'E001', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217693671552880641', NULL, '1217693568842764290', NULL, '01', NULL, '2020-01-16 14:22:50', NULL, 'E001', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217693696508989442', '1217693568905678849', NULL, NULL, '01', NULL, '2020-01-16 14:22:56', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('2', NULL, '1217693696534155266', NULL, '1217693568884707330', NULL, '01', NULL, '2020-01-16 14:23:05', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217693739639017473', '1217693568914067458', NULL, NULL, '01', NULL, '2020-01-16 14:23:06', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217693842802118657', '1217693780193742849', NULL, NULL, '01', NULL, '2020-01-16 14:23:30', NULL, 'E003', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217693842969890818', NULL, '1217693780172771329', NULL, '01', NULL, '2020-01-16 14:23:30', NULL, 'E003', '01');
INSERT INTO `process_params_record` VALUES ('5', NULL, '1217694275159363585', '1217694275130003458', NULL, NULL, '01', NULL, '2020-01-16 14:25:14', NULL, 'S001', '03');
INSERT INTO `process_params_record` VALUES ('5', NULL, '1217694275167752193', NULL, '1217694275117420545', NULL, '01', NULL, '2020-01-16 14:25:14', NULL, 'S001', '03');
INSERT INTO `process_params_record` VALUES ('4', NULL, '1217694296944578562', '1217694275209695233', NULL, NULL, '01', NULL, '2020-01-16 14:25:19', NULL, 'E004', '02');
INSERT INTO `process_params_record` VALUES ('4', NULL, '1217694296957161473', NULL, '1217694275201306626', NULL, '01', NULL, '2020-01-16 14:25:19', NULL, 'E004', '02');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217694458895044609', '1217694342385668097', NULL, NULL, '01', NULL, '2020-01-16 14:25:57', NULL, 'E001', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217694458903433218', NULL, '1217694342377279490', NULL, '01', NULL, '2020-01-16 14:25:57', NULL, 'E001', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217694479182893057', '1217694342431805441', NULL, NULL, '01', NULL, '2020-01-16 14:26:02', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('2', NULL, '1217694479199670273', NULL, '1217694342419222530', NULL, '01', NULL, '2020-01-16 14:26:17', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217694543846477825', '1217694342431805442', NULL, NULL, '01', NULL, '2020-01-16 14:26:18', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217694794221260801', '1217694597990748161', NULL, NULL, '01', NULL, '2020-01-16 14:27:17', NULL, 'E003', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217694794242232321', NULL, '1217694597973970946', NULL, '01', NULL, '2020-01-16 14:27:17', NULL, 'E003', '01');
INSERT INTO `process_params_record` VALUES ('5', NULL, '1217695262880206849', '1217695262846652418', NULL, NULL, '01', NULL, '2020-01-16 14:29:09', NULL, 'S001', '03');
INSERT INTO `process_params_record` VALUES ('5', NULL, '1217695262896984066', NULL, '1217695262838263809', NULL, '01', NULL, '2020-01-16 14:29:09', NULL, 'S001', '03');
INSERT INTO `process_params_record` VALUES ('4', NULL, '1217695559270699009', '1217695262951510017', NULL, NULL, '01', NULL, '2020-01-16 14:30:20', NULL, 'E004', '02');
INSERT INTO `process_params_record` VALUES ('4', NULL, '1217695559283281921', NULL, '1217695262943121409', NULL, '01', NULL, '2020-01-16 14:30:20', NULL, 'E004', '02');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217695652317138946', '1217695603340251138', NULL, NULL, '01', NULL, '2020-01-16 14:30:42', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('2', NULL, '1217695652325527554', NULL, '1217695603331862530', NULL, '01', NULL, '2020-01-16 14:31:02', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217695701474381825', '1217695603302502401', NULL, NULL, '01', NULL, '2020-01-16 14:30:54', NULL, 'E001', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217695701952532481', NULL, '1217695603285725185', NULL, '01', NULL, '2020-01-16 14:30:54', NULL, 'E001', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217695738673664002', '1217695603344445442', NULL, NULL, '01', NULL, '2020-01-16 14:31:02', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217695820143824897', '1217695773226340353', NULL, NULL, '01', NULL, '2020-01-16 14:31:22', NULL, 'E003', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217695820315791361', NULL, '1217695773050179586', NULL, '01', NULL, '2020-01-16 14:31:22', NULL, 'E003', '01');
INSERT INTO `process_params_record` VALUES ('5', NULL, '1217698872980574209', '1217698872926048257', NULL, NULL, '01', NULL, '2020-01-16 14:43:30', NULL, 'S001', '03');
INSERT INTO `process_params_record` VALUES ('5', NULL, '1217698872993157122', NULL, '1217698872921853953', NULL, '01', NULL, '2020-01-16 14:43:30', NULL, 'S001', '03');
INSERT INTO `process_params_record` VALUES ('4', NULL, '1217699065553653761', '1217698873051877377', NULL, NULL, '01', NULL, '2020-01-16 14:44:16', NULL, 'E004', '02');
INSERT INTO `process_params_record` VALUES ('4', NULL, '1217699065562042370', NULL, '1217698873039294465', NULL, '01', NULL, '2020-01-16 14:44:16', NULL, 'E004', '02');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217700294996430850', '1217699172365799426', NULL, NULL, '01', NULL, '2020-01-16 14:49:09', NULL, 'E001', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217700295009013762', NULL, '1217699172344827905', NULL, '01', NULL, '2020-01-16 14:49:09', NULL, 'E001', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217700348628996097', '1217699172416131073', NULL, NULL, '01', NULL, '2020-01-16 14:49:22', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('2', NULL, '1217700348637384705', NULL, '1217699172407742466', NULL, '01', NULL, '2020-01-16 14:49:34', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217700404396462082', '1217699172424519682', NULL, NULL, '01', NULL, '2020-01-16 14:49:35', NULL, 'E002', '03');
INSERT INTO `process_params_record` VALUES ('0', NULL, '1217701126085185537', '1217700938490744834', NULL, NULL, '01', NULL, '2020-01-16 14:52:27', NULL, 'E003', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217701247975854081', '1217701186831290369', NULL, NULL, '01', NULL, '2020-01-16 14:52:56', NULL, 'E003', '01');
INSERT INTO `process_params_record` VALUES ('1', NULL, '1217701247984242690', NULL, '1217700938302001154', NULL, '01', NULL, '2020-01-16 14:52:56', NULL, 'E003', '01');

-- ----------------------------
-- Table structure for process_params_relation
-- ----------------------------
DROP TABLE IF EXISTS `process_params_relation`;
CREATE TABLE `process_params_relation`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `pp_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '画流程人员指定的必填项名称',
  `pp_level` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该参数为流程参数01、活动参数02、任务参数03(备用)',
  `pp_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据类型',
  `pd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应的流程id',
  `task_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '与所属流程的活动(环节)相关联，但并不是关联的表中的id，而是流程描述xml中的id',
  `engine_pp_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程引擎中读取该参数时使用的名称(备用)',
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  `business_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务表单参数名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of process_params_relation
-- ----------------------------
INSERT INTO `process_params_relation` VALUES ('1217633984518225921', 'dynamicassignee01', '02', '02', '1217633983259922433', 'ut001', 'E004', '2020-01-16 10:25:39', '2020-01-16 10:25:39', 'businessdynamicassignee01');
INSERT INTO `process_params_relation` VALUES ('1217633985277394945', 'judge', '02', '01', '1217633983259922433', 'ut003', 'E001', '2020-01-16 10:25:39', '2020-01-16 10:25:39', 'businessjudge');
INSERT INTO `process_params_relation` VALUES ('1217633985466138625', 'pass', '02', '03', '1217633983259922433', 'ut004', 'E002', '2020-01-16 10:25:39', '2020-01-16 10:25:39', 'businessjudge');
INSERT INTO `process_params_relation` VALUES ('1217633985487110145', 'ok', '02', '01', '1217633983259922433', 'ut007', 'E003', '2020-01-16 10:25:39', '2020-01-16 10:25:39', 'businessjudge');
INSERT INTO `process_params_relation` VALUES ('1217633985503887361', 'durations', '02', '03', '1217633983259922433', 'startevent1', 'S001', '2020-01-16 10:25:39', '2020-01-16 10:25:39', 'durations');

-- ----------------------------
-- Table structure for process_template
-- ----------------------------
DROP TABLE IF EXISTS `process_template`;
CREATE TABLE `process_template`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '流程描述文件主键',
  `pt_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '流程描述文件内容',
  `pt_filename` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程描述文件名称',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '模板资源上传时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '模板资源更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of process_template
-- ----------------------------
INSERT INTO `process_template` VALUES ('0', '<definitions no=\"myDefinition\" name=\"流程定义1\">\r\n  <process no=\"myProcess\" name=\"流程1\">\r\n    <startEvent no=\"startevent1\" name=\"Start\"></startEvent>\r\n    <userTask no=\"ut001\" name=\"任务1\" assignee =\"71978fd12990411cba835c41ab9600bb\" pageKey=\"p01\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <userTask no=\"ut002\" name=\"任务2\" assignee =\"d02ef6d28d544da283418c3d79b41255\" pageKey=\"p02\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <parallelGateway no=\"pgw001\" name=\"Parallel Gateway\"></parallelGateway>\r\n    <userTask no=\"ut003\" name=\"任务3\" paramList=\"ut003,day,03,E001\" assignee =\"09ac57a4d43845a78715348f28fa75c2\" pageKey=\"p03\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <userTask no=\"ut004\" name=\"任务4\" paramList=\"ut004,pass,03,E002\" assignee =\"91e16809c6454ef19114d0dd1424f78a,ff9ad2bb7c414872843b713c00abfecc\" pageKey=\"p04\" taskType=\"02\" assigneeType=\"0\"></userTask>\r\n    <parallelGateway no=\"pgw002\" name=\"Parallel Gateway\"></parallelGateway>\r\n    <exclusiveGateway no=\"egw001\" name=\"Exclusive Gateway\"></exclusiveGateway>\r\n    <userTask no=\"ut005\" name=\"任务5\" assignee =\"3b6804397692495a8e6f708529932201\" pageKey=\"p05\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <userTask no=\"ut006\" name=\"任务6\" assignee =\"59618d71e28f4d5eab090e5526189afa\" pageKey=\"p06\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <userTask no=\"ut007\" name=\"任务7\" paramList=\"ut007,ok,01,E003\" assignee =\"2c3f76bb973141af89e766166cdf1b16\" pageKey=\"p07\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <userTask no=\"ut008\" name=\"任务8\" assignee =\"db9380dc694143a58b93c0016460606e\" pageKey=\"p08\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <exclusiveGateway no=\"egw002\" name=\"Exclusive Gateway\"></exclusiveGateway>\r\n    <endEvent no=\"endevent1\" name=\"End\"></endEvent>\r\n    <endEvent no=\"endevent2\" name=\"End\"></endEvent>\r\n    <sequenceFlow no=\"fw001\"  sourceRef=\"startevent1\" targetRef=\"ut001\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw002\"  sourceRef=\"ut001\" targetRef=\"ut002\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw003\"  sourceRef=\"ut002\" targetRef=\"pgw001\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw004\"  sourceRef=\"pgw001\" targetRef=\"ut003\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw005\"  sourceRef=\"pgw001\" targetRef=\"ut004\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw006\"  sourceRef=\"ut004\" targetRef=\"pgw002\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw007\"  sourceRef=\"ut003\" targetRef=\"pgw002\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw008\"  sourceRef=\"pgw002\" targetRef=\"egw001\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw009\"  sourceRef=\"egw001\" targetRef=\"ut005\">\r\n	    <conditionExpression paramList=\"ut003,day,03,E001;ut004,pass,03,E002\" ><![CDATA[ E001>0 && E002 >0 ]]></conditionExpression>\r\n    </sequenceFlow>\r\n    <sequenceFlow no=\"fw010\"  sourceRef=\"egw001\" targetRef=\"ut006\">\r\n    	<conditionExpression paramList=\"ut004,pass,03,E002\" ><![CDATA[ E002 == 0]]></conditionExpression>\r\n    </sequenceFlow>\r\n    <sequenceFlow no=\"fw011\"  sourceRef=\"ut005\" targetRef=\"ut007\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw012\"  sourceRef=\"ut007\" targetRef=\"egw002\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw013\"  sourceRef=\"egw002\" targetRef=\"endevent1\">\r\n    	<conditionExpression paramList=\"ut007,ok,01,E003\" ><![CDATA[ E003==true ]]></conditionExpression>\r\n    </sequenceFlow>\r\n    <sequenceFlow no=\"fw016\"  sourceRef=\"egw002\" targetRef=\"ut005\">\r\n    	<conditionExpression paramList=\"ut007,ok,01,E003\" ><![CDATA[E003==false]]></conditionExpression>\r\n    </sequenceFlow>\r\n    <sequenceFlow no=\"fw014\"  sourceRef=\"ut006\" targetRef=\"ut008\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw015\"  sourceRef=\"ut008\" targetRef=\"endevent2\"></sequenceFlow>\r\n  </process>\r\n\r\n</definitions>', '新架构流程图', '2019-12-19 17:09:13', NULL);
INSERT INTO `process_template` VALUES ('1', '<definitions no=\"myDefinition\" name=\"流程定义1\">\n  <process no=\"myProcess\" name=\"流程1\">\n    <startEvent no=\"startevent1\" name=\"Start\"></startEvent>\n    <userTask no=\"ut001\" name=\"任务1\" assignee =\"71978fd12990411cba835c41ab9600bb\" pageKey=\"p01\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut002\" name=\"任务2\" assignee =\"d02ef6d28d544da283418c3d79b41255\" pageKey=\"p02\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <parallelGateway no=\"pgw001\" name=\"Parallel Gateway\" relatedGateWay=\"pgw002\"></parallelGateway>\n    <userTask no=\"ut003\" name=\"任务3\" paramList=\"ut003,day,03,E001\" assignee =\"09ac57a4d43845a78715348f28fa75c2\" pageKey=\"p03\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut004\" name=\"任务4\" paramList=\"ut004,pass,03,E002\" assignee =\"91e16809c6454ef19114d0dd1424f78a,ff9ad2bb7c414872843b713c00abfecc\" pageKey=\"p04\" taskType=\"02\" assigneeType=\"0\"></userTask>\n    <parallelGateway no=\"pgw002\" name=\"Parallel Gateway\"></parallelGateway>\n    <exclusiveGateway no=\"egw001\" name=\"Exclusive Gateway\"></exclusiveGateway>\n    <userTask no=\"ut005\" name=\"任务5\" assignee =\"3b6804397692495a8e6f708529932201\" pageKey=\"p05\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut006\" name=\"任务6\" assignee =\"59618d71e28f4d5eab090e5526189afa\" pageKey=\"p06\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut007\" name=\"任务7\" paramList=\"ut007,ok,01,E003\" assignee =\"2c3f76bb973141af89e766166cdf1b16\" pageKey=\"p07\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut008\" name=\"任务8\" assignee =\"db9380dc694143a58b93c0016460606e\" pageKey=\"p08\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <exclusiveGateway no=\"egw002\" name=\"Exclusive Gateway\"></exclusiveGateway>\n    <endEvent no=\"endevent1\" name=\"End\"></endEvent>\n    <endEvent no=\"endevent2\" name=\"End\"></endEvent>\n    <sequenceFlow no=\"fw001\"  sourceRef=\"startevent1\" targetRef=\"ut001\"></sequenceFlow>\n    <sequenceFlow no=\"fw002\"  sourceRef=\"ut001\" targetRef=\"ut002\"></sequenceFlow>\n    <sequenceFlow no=\"fw003\"  sourceRef=\"ut002\" targetRef=\"pgw001\"></sequenceFlow>\n    <sequenceFlow no=\"fw004\"  sourceRef=\"pgw001\" targetRef=\"ut003\"></sequenceFlow>\n    <sequenceFlow no=\"fw005\"  sourceRef=\"pgw001\" targetRef=\"ut004\"></sequenceFlow>\n    <sequenceFlow no=\"fw006\"  sourceRef=\"ut004\" targetRef=\"pgw002\"></sequenceFlow>\n    <sequenceFlow no=\"fw007\"  sourceRef=\"ut003\" targetRef=\"pgw002\"></sequenceFlow>\n    <sequenceFlow no=\"fw008\"  sourceRef=\"pgw002\" targetRef=\"egw001\"></sequenceFlow>\n    <sequenceFlow no=\"fw009\"  sourceRef=\"egw001\" targetRef=\"ut005\">\n	    <conditionExpression paramList=\"ut003,day,03,E001;ut004,pass,03,E002\" ><![CDATA[ E001>0 && E002 >0 ]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw010\"  sourceRef=\"egw001\" targetRef=\"ut006\">\n    	<conditionExpression paramList=\"ut004,pass,03,E002\" ><![CDATA[ E002 == 0]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw011\"  sourceRef=\"ut005\" targetRef=\"ut007\"></sequenceFlow>\n    <sequenceFlow no=\"fw012\"  sourceRef=\"ut007\" targetRef=\"egw002\"></sequenceFlow>\n    <sequenceFlow no=\"fw013\"  sourceRef=\"egw002\" targetRef=\"endevent1\">\n    	<conditionExpression paramList=\"ut007,ok,01,E003\" ><![CDATA[ E003==true ]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw016\"  sourceRef=\"egw002\" targetRef=\"ut005\">\n    	<conditionExpression paramList=\"ut007,ok,01,E003\" ><![CDATA[E003==false]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw014\"  sourceRef=\"ut006\" targetRef=\"ut008\"></sequenceFlow>\n    <sequenceFlow no=\"fw015\"  sourceRef=\"ut008\" targetRef=\"endevent2\"></sequenceFlow>\n  </process>\n\n</definitions>', '汇报用流程图', '2019-10-25 17:04:08', NULL);
INSERT INTO `process_template` VALUES ('2', '<definitions no=\"myDefinition2\" name=\"流程定义2\">\n  <process no=\"myProcess2\" name=\"流程2\">\n    <startEvent no=\"startevent1\" name=\"Start\"></startEvent>\n    <userTask no=\"ut1001\" name=\"任务1\" paramList=\"ut1001,day,03,E1001\" assignee=\"b3fb7fe4c319433fbe981403bee8109f\" pageKey=\"p1001\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <exclusiveGateway no=\"egw1001\" name=\"Exclusive Gateway\"></exclusiveGateway>\n    <userTask no=\"ut1002\" name=\"任务2\" assignee=\"9fff2880310c4983bf25753fe349a2bc\" pageKey=\"p1002\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut1003\" name=\"任务3\" assignee=\"094fcc1109d24ab8b16a70ad6341013d\" pageKey=\"p1003\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <parallelGateway no=\"pgw1001\" name=\"Parallel Gateway\" relatedGateWay=\"pgw1004\"></parallelGateway>\n    <parallelGateway no=\"pgw1002\" name=\"Parallel Gateway\" relatedGateWay=\"pgw1003\"></parallelGateway>\n    <userTask no=\"ut1004\" name=\"任务4\" paramList=\"ut1004,pass,01,E1002\" assignee=\"55bf482a9213412e9fd51e115d830337\" pageKey=\"p1004\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut1005\" name=\"任务5\" assignee=\"b3f93ff03d1a4491affdd44aa970a4d7,8d5ddb92b7af4dfd9810cc42972bc746\" pageKey=\"p1005\" taskType=\"02\" assigneeType=\"0\"></userTask>\n    <exclusiveGateway no=\"egw1002\" name=\"Exclusive Gateway\"></exclusiveGateway>\n    <userTask no=\"ut1006\" name=\"任务6\" paramList=\"ut1006,ok,01,E1003\" assignee=\"49cc54b8aaae4608bb9a4bf52db724fe\" pageKey=\"p1006\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut1007\" name=\"任务7\" paramList=\"ut1007,ticket,03,E1004\" assignee=\"9d2563c8b88f4f32a2a6064a9f220c2f\" pageKey=\"p1007\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut1008\" name=\"任务8\" assignee=\"4d36cec9a29a49bdae826b31dc4cbc49\" pageKey=\"p1008\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut1009\" name=\"任务9\" assignee=\"a3848ffe63e74ffaae884d8c3858de20\" pageKey=\"p1009\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <parallelGateway no=\"pgw1003\" name=\"Parallel Gateway\"></parallelGateway>\n    <parallelGateway no=\"pgw1004\" name=\"Parallel Gateway\"></parallelGateway>\n    <userTask no=\"ut1010\" name=\"任务10\" assignee=\"37c90f78b200455aa6e3da80e7f79349\" pageKey=\"p1010\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <endEvent no=\"endevent1001\" name=\"End\"></endEvent>\n    <userTask no=\"ut1011\" name=\"任务11\" assignee=\"e85e0186f6444e108f6ac932b5ba0404\" pageKey=\"p1011\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <sequenceFlow no=\"fw1001\" sourceRef=\"startevent1\" targetRef=\"ut1001\"></sequenceFlow>\n    <sequenceFlow no=\"fw1002\" sourceRef=\"ut1001\" targetRef=\"egw1001\"></sequenceFlow>\n    <sequenceFlow no=\"fw1003\" sourceRef=\"egw1001\" targetRef=\"ut1002\">\n    	<conditionExpression paramList=\"ut1001,day,03,E1001\" ><![CDATA[ E1001<0 ]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw1004\" sourceRef=\"egw1001\" targetRef=\"ut1003\">\n    	<conditionExpression paramList=\"ut1001,day,03,E1001\" ><![CDATA[ E1001>0 ]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw1005\" sourceRef=\"ut1002\" targetRef=\"pgw1001\"></sequenceFlow>\n    <sequenceFlow no=\"fw1006\" sourceRef=\"pgw1001\" targetRef=\"ut1004\"></sequenceFlow>\n    <sequenceFlow no=\"fw1007\" sourceRef=\"pgw1001\" targetRef=\"ut1005\"></sequenceFlow>\n    <sequenceFlow no=\"fw1008\" sourceRef=\"ut1004\" targetRef=\"egw1002\"></sequenceFlow>\n    <sequenceFlow no=\"fw1009\" sourceRef=\"egw1002\" targetRef=\"ut1008\">\n    	<conditionExpression paramList=\"ut1004,pass,01,E1002\" ><![CDATA[ E1002==true ]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw1010\" sourceRef=\"egw1002\" targetRef=\"ut1009\">\n    	<conditionExpression paramList=\"ut1004,pass,01,E1002\" ><![CDATA[ E1002==false ]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw1011\" sourceRef=\"ut1008\" targetRef=\"pgw1004\"></sequenceFlow>\n    <sequenceFlow no=\"fw1012\" sourceRef=\"ut1009\" targetRef=\"pgw1004\"></sequenceFlow>\n    <sequenceFlow no=\"fw1013\" sourceRef=\"ut1005\" targetRef=\"pgw1004\"></sequenceFlow>\n    <sequenceFlow no=\"fw1014\" sourceRef=\"pgw1004\" targetRef=\"ut1011\"></sequenceFlow>\n    <endEvent no=\"endevent1002\" name=\"End\"></endEvent>\n    <sequenceFlow no=\"fw1015\" sourceRef=\"ut1011\" targetRef=\"endevent1002\"></sequenceFlow>\n    <sequenceFlow no=\"fw1016\" sourceRef=\"ut1003\" targetRef=\"pgw1002\"></sequenceFlow>\n    <sequenceFlow no=\"fw1017\" sourceRef=\"pgw1002\" targetRef=\"ut1006\"></sequenceFlow>\n    <sequenceFlow no=\"fw1018\" sourceRef=\"pgw1002\" targetRef=\"ut1007\"></sequenceFlow>\n    <sequenceFlow no=\"fw1019\" sourceRef=\"ut1006\" targetRef=\"pgw1003\"></sequenceFlow>\n    <sequenceFlow no=\"fw1020\" sourceRef=\"ut1007\" targetRef=\"pgw1003\"></sequenceFlow>\n    <exclusiveGateway no=\"egw1003\" name=\"Exclusive Gateway\"></exclusiveGateway>\n    <sequenceFlow no=\"fw1021\" sourceRef=\"pgw1003\" targetRef=\"egw1003\"></sequenceFlow>\n    <sequenceFlow no=\"fw1022\" sourceRef=\"egw1003\" targetRef=\"ut1010\">\n    	<conditionExpression paramList=\"ut1006,ok,01,E1003;ut1007,ticket,03,E1004\" ><![CDATA[ E1003==true && E1004>0 ]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw1023\" name=\"驳回\" sourceRef=\"egw1003\" targetRef=\"ut1003\">\n    	<conditionExpression paramList=\"ut1006,ok,01,E1003\" ><![CDATA[ E1003==false]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw1024\" sourceRef=\"ut1010\" targetRef=\"endevent1001\"></sequenceFlow>\n  </process>\n</definitions>', '复杂版流程图', '2019-10-17 11:57:15', NULL);
INSERT INTO `process_template` VALUES ('3', '<definitions no=\"myDefinition\" name=\"流程定义1\">\r\n  <process no=\"myProcess\" name=\"流程1\">\r\n    <startEvent no=\"startevent1\" name=\"Start\"></startEvent>\r\n    <userTask no=\"ut001\" name=\"任务1\" assignee =\"71978fd12990411cba835c41ab9600bb\"  paramList=\"ut001,dynamicassignee01,02,E004\" pageKey=\"p01\" taskType=\"01\" assigneeType=\"0\" ></userTask>\r\n    <userTask no=\"ut002\" name=\"任务2\" assignee =\"d02ef6d28d544da283418c3d79b41255\" dynamicAssignee = \"ut001,dynamicassignee01,02,E004\" dynamicAssigneeType=\"0\" pageKey=\"p02\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <parallelGateway no=\"pgw001\" name=\"Parallel Gateway\"></parallelGateway>\r\n    <userTask no=\"ut003\" name=\"任务3\" paramList=\"ut003,day,03,E001\" assignee =\"09ac57a4d43845a78715348f28fa75c2\" pageKey=\"p03\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <userTask no=\"ut004\" name=\"任务4\" paramList=\"ut004,pass,03,E002\" assignee =\"91e16809c6454ef19114d0dd1424f78a,ff9ad2bb7c414872843b713c00abfecc\" pageKey=\"p04\" taskType=\"02\" assigneeType=\"0\"></userTask>\r\n    <parallelGateway no=\"pgw002\" name=\"Parallel Gateway\"></parallelGateway>\r\n    <exclusiveGateway no=\"egw001\" name=\"Exclusive Gateway\"></exclusiveGateway>\r\n    <userTask no=\"ut005\" name=\"任务5\" assignee =\"3b6804397692495a8e6f708529932201\" pageKey=\"p05\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <userTask no=\"ut006\" name=\"任务6\" assignee =\"59618d71e28f4d5eab090e5526189afa\" pageKey=\"p06\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <userTask no=\"ut007\" name=\"任务7\" paramList=\"ut007,ok,01,E003\" assignee =\"2c3f76bb973141af89e766166cdf1b16\" pageKey=\"p07\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <userTask no=\"ut008\" name=\"任务8\" assignee =\"db9380dc694143a58b93c0016460606e\" pageKey=\"p08\" taskType=\"01\" assigneeType=\"0\"></userTask>\r\n    <exclusiveGateway no=\"egw002\" name=\"Exclusive Gateway\"></exclusiveGateway>\r\n    <endEvent no=\"endevent1\" name=\"End\"></endEvent>\r\n    <endEvent no=\"endevent2\" name=\"End\"></endEvent>\r\n    <sequenceFlow no=\"fw001\"  sourceRef=\"startevent1\" targetRef=\"ut001\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw002\"  sourceRef=\"ut001\" targetRef=\"ut002\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw003\"  sourceRef=\"ut002\" targetRef=\"pgw001\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw004\"  sourceRef=\"pgw001\" targetRef=\"ut003\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw005\"  sourceRef=\"pgw001\" targetRef=\"ut004\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw006\"  sourceRef=\"ut004\" targetRef=\"pgw002\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw007\"  sourceRef=\"ut003\" targetRef=\"pgw002\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw008\"  sourceRef=\"pgw002\" targetRef=\"egw001\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw009\"  sourceRef=\"egw001\" targetRef=\"ut005\">\r\n	    <conditionExpression paramList=\"ut003,day,03,E001;ut004,pass,03,E002\" ><![CDATA[ E001>0 && E002 >0 ]]></conditionExpression>\r\n    </sequenceFlow>\r\n    <sequenceFlow no=\"fw010\"  sourceRef=\"egw001\" targetRef=\"ut006\">\r\n    	<conditionExpression paramList=\"ut004,pass,03,E002\" ><![CDATA[ E002 == 0]]></conditionExpression>\r\n    </sequenceFlow>\r\n    <sequenceFlow no=\"fw011\"  sourceRef=\"ut005\" targetRef=\"ut007\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw012\"  sourceRef=\"ut007\" targetRef=\"egw002\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw013\"  sourceRef=\"egw002\" targetRef=\"endevent1\">\r\n    	<conditionExpression paramList=\"ut007,ok,01,E003\" ><![CDATA[ E003==true ]]></conditionExpression>\r\n    </sequenceFlow>\r\n    <sequenceFlow no=\"fw016\"  sourceRef=\"egw002\" targetRef=\"ut005\">\r\n    	<conditionExpression paramList=\"ut007,ok,01,E003\" ><![CDATA[E003==false]]></conditionExpression>\r\n    </sequenceFlow>\r\n    <sequenceFlow no=\"fw014\"  sourceRef=\"ut006\" targetRef=\"ut008\"></sequenceFlow>\r\n    <sequenceFlow no=\"fw015\"  sourceRef=\"ut008\" targetRef=\"endevent2\"></sequenceFlow>\r\n  </process>\r\n\r\n</definitions>', '即席流程图', '2019-12-20 16:50:12', NULL);
INSERT INTO `process_template` VALUES ('4', '<definitions no=\"myDefinition\" name=\"流程定义1\">\n  <process no=\"myProcess\" name=\"流程1\">\n    <startEvent no=\"startevent1\" name=\"Start\" paramList=\"startevent1,durations,01,S001\"></startEvent>\n    <userTask no=\"ut001\" name=\"任务1\" assignee =\"71978fd12990411cba835c41ab9600bb\"  paramList=\"ut001,dynamicassignee01,02,E004\" pageKey=\"p01\" taskType=\"01\" assigneeType=\"0\" ></userTask>\n    <userTask no=\"ut002\" name=\"任务2\" assignee =\"d02ef6d28d544da283418c3d79b41255\" dynamicAssignee = \"ut001,dynamicassignee01,02,E004\" dynamicAssigneeType=\"0\" pageKey=\"p02\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <parallelGateway no=\"pgw001\" name=\"Parallel Gateway\"></parallelGateway>\n    <userTask no=\"ut003\" name=\"任务3\" paramList=\"ut003,day,03,E001\" assignee =\"09ac57a4d43845a78715348f28fa75c2\" pageKey=\"p03\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut004\" name=\"任务4\" paramList=\"ut004,pass,03,E002\" assignee =\"91e16809c6454ef19114d0dd1424f78a,ff9ad2bb7c414872843b713c00abfecc\" pageKey=\"p04\" taskType=\"02\" assigneeType=\"0\"></userTask>\n    <parallelGateway no=\"pgw002\" name=\"Parallel Gateway\"></parallelGateway>\n    <exclusiveGateway no=\"egw001\" name=\"Exclusive Gateway\"></exclusiveGateway>\n    <userTask no=\"ut005\" name=\"任务5\" assignee =\"3b6804397692495a8e6f708529932201\" pageKey=\"p05\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut006\" name=\"任务6\" assignee =\"59618d71e28f4d5eab090e5526189afa\" pageKey=\"p06\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut007\" name=\"任务7\" paramList=\"ut007,ok,01,E003\" assignee =\"2c3f76bb973141af89e766166cdf1b16\" pageKey=\"p07\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut008\" name=\"任务8\" assignee =\"db9380dc694143a58b93c0016460606e\" pageKey=\"p08\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <exclusiveGateway no=\"egw002\" name=\"Exclusive Gateway\"></exclusiveGateway>\n    <endEvent no=\"endevent1\" name=\"End\"></endEvent>\n    <endEvent no=\"endevent2\" name=\"End\"></endEvent>\n    <sequenceFlow no=\"fw001\"  sourceRef=\"startevent1\" targetRef=\"ut001\"></sequenceFlow>\n    <sequenceFlow no=\"fw002\"  sourceRef=\"ut001\" targetRef=\"ut002\"></sequenceFlow>\n    <sequenceFlow no=\"fw003\"  sourceRef=\"ut002\" targetRef=\"pgw001\"></sequenceFlow>\n    <sequenceFlow no=\"fw004\"  sourceRef=\"pgw001\" targetRef=\"ut003\"></sequenceFlow>\n    <sequenceFlow no=\"fw005\"  sourceRef=\"pgw001\" targetRef=\"ut004\"></sequenceFlow>\n    <sequenceFlow no=\"fw006\"  sourceRef=\"ut004\" targetRef=\"pgw002\"></sequenceFlow>\n    <sequenceFlow no=\"fw007\"  sourceRef=\"ut003\" targetRef=\"pgw002\"></sequenceFlow>\n    <sequenceFlow no=\"fw008\"  sourceRef=\"pgw002\" targetRef=\"egw001\"></sequenceFlow>\n    <sequenceFlow no=\"fw009\"  sourceRef=\"egw001\" targetRef=\"ut005\">\n	    <conditionExpression paramList=\"ut003,day,03,E001;ut004,pass,03,E002\" ><![CDATA[ E001>0 && E002 >0 ]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw010\"  sourceRef=\"egw001\" targetRef=\"ut006\">\n    	<conditionExpression paramList=\"ut004,pass,03,E002\" ><![CDATA[ E002 == 0]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw011\"  sourceRef=\"ut005\" targetRef=\"ut007\"></sequenceFlow>\n    <sequenceFlow no=\"fw012\"  sourceRef=\"ut007\" targetRef=\"egw002\"></sequenceFlow>\n    <sequenceFlow no=\"fw013\"  sourceRef=\"egw002\" targetRef=\"endevent1\">\n    	<conditionExpression paramList=\"ut007,ok,01,E003\" ><![CDATA[ E003==true ]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw016\"  sourceRef=\"egw002\" targetRef=\"ut005\">\n    	<conditionExpression paramList=\"ut007,ok,01,E003\" ><![CDATA[E003==false]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw014\"  sourceRef=\"ut006\" targetRef=\"ut008\"></sequenceFlow>\n    <sequenceFlow no=\"fw015\"  sourceRef=\"ut008\" targetRef=\"endevent2\"></sequenceFlow>\n  </process>\n\n</definitions>', '请假流程图', '2020-01-13 09:57:29', NULL);
INSERT INTO `process_template` VALUES ('5', '<definitions no=\"myDefinition\" name=\"无意义测试审批项目\">\n  <process no=\"myProcess\" name=\"流程1\">\n    <startEvent no=\"startevent1\" name=\"请假申请环节\" paramList=\"startevent1,durations,01,S001\" pageKey=\"1\"></startEvent>\n    <userTask no=\"ut001\" name=\"任务1\" assignee =\"71978fd12990411cba835c41ab9600bb\"  paramList=\"ut001,dynamicassignee01,02,E004\" pageKey=\"p01\" taskType=\"01\" assigneeType=\"0\" ></userTask>\n    <userTask no=\"ut002\" name=\"任务2\" assignee =\"d02ef6d28d544da283418c3d79b41255\" dynamicAssignee = \"ut001,dynamicassignee01,02,E004\" dynamicAssigneeType=\"0\" pageKey=\"p02\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <parallelGateway no=\"pgw001\" name=\"Parallel Gateway\"></parallelGateway>\n    <userTask no=\"ut003\" name=\"任务3\" paramList=\"ut003,day,03,E001\" assignee =\"09ac57a4d43845a78715348f28fa75c2\" pageKey=\"p03\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut004\" name=\"任务4\" paramList=\"ut004,pass,03,E002\" assignee =\"91e16809c6454ef19114d0dd1424f78a,ff9ad2bb7c414872843b713c00abfecc\" pageKey=\"p04\" taskType=\"02\" assigneeType=\"0\"></userTask>\n    <parallelGateway no=\"pgw002\" name=\"Parallel Gateway\"></parallelGateway>\n    <exclusiveGateway no=\"egw001\" name=\"Exclusive Gateway\"></exclusiveGateway>\n    <userTask no=\"ut005\" name=\"任务5\" assignee =\"3b6804397692495a8e6f708529932201\" pageKey=\"p05\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut006\" name=\"任务6\" assignee =\"59618d71e28f4d5eab090e5526189afa\" pageKey=\"p06\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut007\" name=\"任务7\" paramList=\"ut007,ok,01,E003\" assignee =\"2c3f76bb973141af89e766166cdf1b16\" pageKey=\"p07\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut008\" name=\"任务8\" assignee =\"db9380dc694143a58b93c0016460606e\" pageKey=\"p08\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <exclusiveGateway no=\"egw002\" name=\"Exclusive Gateway\"></exclusiveGateway>\n    <endEvent no=\"endevent1\" name=\"End\"></endEvent>\n    <endEvent no=\"endevent2\" name=\"End\"></endEvent>\n    <sequenceFlow no=\"fw001\"  sourceRef=\"startevent1\" targetRef=\"ut001\"></sequenceFlow>\n    <sequenceFlow no=\"fw002\"  sourceRef=\"ut001\" targetRef=\"ut002\"></sequenceFlow>\n    <sequenceFlow no=\"fw003\"  sourceRef=\"ut002\" targetRef=\"pgw001\"></sequenceFlow>\n    <sequenceFlow no=\"fw004\"  sourceRef=\"pgw001\" targetRef=\"ut003\"></sequenceFlow>\n    <sequenceFlow no=\"fw005\"  sourceRef=\"pgw001\" targetRef=\"ut004\"></sequenceFlow>\n    <sequenceFlow no=\"fw006\"  sourceRef=\"ut004\" targetRef=\"pgw002\"></sequenceFlow>\n    <sequenceFlow no=\"fw007\"  sourceRef=\"ut003\" targetRef=\"pgw002\"></sequenceFlow>\n    <sequenceFlow no=\"fw008\"  sourceRef=\"pgw002\" targetRef=\"egw001\"></sequenceFlow>\n    <sequenceFlow no=\"fw009\"  sourceRef=\"egw001\" targetRef=\"ut005\">\n	    <conditionExpression paramList=\"ut003,day,03,E001;ut004,pass,03,E002\" ><![CDATA[ E001>0 && E002 >0 ]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw010\"  sourceRef=\"egw001\" targetRef=\"ut006\">\n    	<conditionExpression paramList=\"ut004,pass,03,E002\" ><![CDATA[ E002 == 0]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw011\"  sourceRef=\"ut005\" targetRef=\"ut007\"></sequenceFlow>\n    <sequenceFlow no=\"fw012\"  sourceRef=\"ut007\" targetRef=\"egw002\"></sequenceFlow>\n    <sequenceFlow no=\"fw013\"  sourceRef=\"egw002\" targetRef=\"endevent1\">\n    	<conditionExpression paramList=\"ut007,ok,01,E003\" ><![CDATA[ E003==true ]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw016\"  sourceRef=\"egw002\" targetRef=\"ut005\">\n    	<conditionExpression paramList=\"ut007,ok,01,E003\" ><![CDATA[E003==false]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw014\"  sourceRef=\"ut006\" targetRef=\"ut008\"></sequenceFlow>\n    <sequenceFlow no=\"fw015\"  sourceRef=\"ut008\" targetRef=\"endevent2\"></sequenceFlow>\n  </process>\n\n</definitions>', '无意义测试审批项目', '2020-01-14 15:22:52', NULL);
INSERT INTO `process_template` VALUES ('6', '<definitions no=\"myDefinition\" name=\"请假审批\">\n  <process no=\"myProcess\" name=\"流程1\">\n    <startEvent no=\"startevent1\" name=\"请假申请环节\" paramList=\"startevent1,durations,03,S001\" pageKey=\"1\"></startEvent>\n    <userTask no=\"ut001\" name=\"动态指定抄送目标环节（演示即席）\" assignee =\"1\"  paramList=\"ut001,dynamicassignee01,02,E004\" pageKey=\"4\" taskType=\"02\" assigneeType=\"0\" ></userTask>\n    <userTask no=\"ut002\" name=\"指定成员抄送审阅\" dynamicAssignee = \"ut001,dynamicassignee01,02,E004\" dynamicAssigneeType=\"0\" pageKey=\"2\" taskType=\"01\"></userTask>\n    <parallelGateway no=\"pgw001\" name=\"Parallel Gateway\"></parallelGateway>\n    <userTask no=\"ut003\" name=\"经理级审批环节（任意1个）\" paramList=\"ut003,judge,01,E001\" assignee =\"2\" pageKey=\"3\" taskType=\"01\" assigneeType=\"1\"></userTask>\n    <userTask no=\"ut004\" name=\"hx,yy会签审批环节\" paramList=\"ut004,pass,03,E002\" assignee =\"2,3\" pageKey=\"3\" taskType=\"02\" assigneeType=\"0\"></userTask>\n    <parallelGateway no=\"pgw002\" name=\"Parallel Gateway\"></parallelGateway>\n    <exclusiveGateway no=\"egw001\" name=\"Exclusive Gateway\"></exclusiveGateway>\n    <userTask no=\"ut005\" name=\"抄送给张三环节\" assignee =\"4\" pageKey=\"2\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut006\" name=\"抄送任务环节\" assignee =\"5\" pageKey=\"2\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut007\" name=\"李总审批环节\" paramList=\"ut007,ok,01,E003\" assignee =\"5\" pageKey=\"3\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <userTask no=\"ut008\" name=\"抄送任务环节\" assignee =\"5\" pageKey=\"2\" taskType=\"01\" assigneeType=\"0\"></userTask>\n    <exclusiveGateway no=\"egw002\" name=\"Exclusive Gateway\"></exclusiveGateway>\n    <endEvent no=\"endevent1\" name=\"End\"></endEvent>\n    <endEvent no=\"endevent2\" name=\"End\"></endEvent>\n    <sequenceFlow no=\"fw001\"  sourceRef=\"startevent1\" targetRef=\"ut001\"></sequenceFlow>\n    <sequenceFlow no=\"fw002\"  sourceRef=\"ut001\" targetRef=\"ut002\"></sequenceFlow>\n    <sequenceFlow no=\"fw003\"  sourceRef=\"ut002\" targetRef=\"pgw001\"></sequenceFlow>\n    <sequenceFlow no=\"fw004\"  sourceRef=\"pgw001\" targetRef=\"ut003\"></sequenceFlow>\n    <sequenceFlow no=\"fw005\"  sourceRef=\"pgw001\" targetRef=\"ut004\"></sequenceFlow>\n    <sequenceFlow no=\"fw006\"  sourceRef=\"ut004\" targetRef=\"pgw002\"></sequenceFlow>\n    <sequenceFlow no=\"fw007\"  sourceRef=\"ut003\" targetRef=\"pgw002\"></sequenceFlow>\n    <sequenceFlow no=\"fw008\"  sourceRef=\"pgw002\" targetRef=\"egw001\"></sequenceFlow>\n    <sequenceFlow no=\"fw009\"  sourceRef=\"egw001\" targetRef=\"ut005\">\n        <conditionExpression paramList=\"ut003,judge,01,E001;ut004,pass,03,E002;startevent1,durations,03,S001\" ><![CDATA[ S001 > 2 && E002 > 1 && E001 == true]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw010\"  sourceRef=\"egw001\" targetRef=\"ut006\">\n        <conditionExpression paramList=\"startevent1,durations,03,S001;ut004,pass,03,E002\"><![CDATA[ S001 < 3 && E002 < 2]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw011\"  sourceRef=\"ut005\" targetRef=\"ut007\"></sequenceFlow>\n    <sequenceFlow no=\"fw012\"  sourceRef=\"ut007\" targetRef=\"egw002\"></sequenceFlow>\n    <sequenceFlow no=\"fw013\"  sourceRef=\"egw002\" targetRef=\"endevent1\">\n        <conditionExpression paramList=\"ut007,ok,01,E003\" ><![CDATA[ E003==true ]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw016\"  sourceRef=\"egw002\" targetRef=\"ut005\">\n        <conditionExpression paramList=\"ut007,ok,01,E003\" ><![CDATA[E003==false]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow no=\"fw014\"  sourceRef=\"ut006\" targetRef=\"ut008\"></sequenceFlow>\n    <sequenceFlow no=\"fw015\"  sourceRef=\"ut008\" targetRef=\"endevent2\"></sequenceFlow>\n  </process>\n\n</definitions>', '请假审批', '2020-01-16 00:33:44', NULL);

-- ----------------------------
-- Table structure for task_history_instance
-- ----------------------------
DROP TABLE IF EXISTS `task_history_instance`;
CREATE TABLE `task_history_instance`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '历史记录主键',
  `ti_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运行时的任务实例主键',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  `ai_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应活动运行时主键',
  `ti_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `ti_status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务状态0正在运行1已完成',
  `bf_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务关联表单外键',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '时间戳',
  `ti_create_time` timestamp(0) NULL DEFAULT NULL COMMENT '运行时任务开始时间',
  `ti_update_time` timestamp(0) NULL DEFAULT NULL COMMENT '运行时任务状态更新时间',
  `ti_endtime` timestamp(0) NULL DEFAULT NULL COMMENT '运行时任务结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of task_history_instance
-- ----------------------------
INSERT INTO `task_history_instance` VALUES ('1217634887547707393', '1217634887468015617', '2020-01-16 10:29:14', '1217634887430266882', '请假申请环节', '02', NULL, '2020-01-16 10:29:14', '2020-01-16 10:29:14', '2020-01-16 10:29:14', NULL);
INSERT INTO `task_history_instance` VALUES ('1217634941830389761', '1217634887690313729', '2020-01-16 10:29:27', '1217634887673536513', '动态指定抄送目标环节（演示即席）', '02', '4', '2020-01-16 10:29:27', '2020-01-16 10:29:14', '2020-01-16 10:29:14', NULL);
INSERT INTO `task_history_instance` VALUES ('1217634985845415937', '1217634942182711298', '2020-01-16 10:29:38', '1217634942002356226', '指定成员抄送审阅', '02', '2', '2020-01-16 10:29:38', '2020-01-16 10:29:27', '2020-01-16 10:29:27', NULL);
INSERT INTO `task_history_instance` VALUES ('1217635071715401729', '1217634985983827970', '2020-01-16 10:29:58', '1217634985971245057', '经理级审批环节（任意1个）', '02', '3', '2020-01-16 10:29:58', '2020-01-16 10:29:52', '2020-01-16 10:29:38', NULL);
INSERT INTO `task_history_instance` VALUES ('1217635117722722305', '1217634986046742530', '2020-01-16 10:30:09', '1217634986029965314', 'hx,yy会签审批环节', '02', '3', '2020-01-16 10:30:09', '2020-01-16 10:29:38', '2020-01-16 10:29:38', NULL);
INSERT INTO `task_history_instance` VALUES ('1217652216297873409', '1217634986059325442', '2020-01-16 11:38:06', '1217634986029965314', 'hx,yy会签审批环节', '02', '3', '2020-01-16 11:38:06', '2020-01-16 10:29:38', '2020-01-16 10:29:38', NULL);
INSERT INTO `task_history_instance` VALUES ('1217652371445178369', '1217652264591089666', '2020-01-16 11:38:43', '1217652264561729538', '抄送给张三环节', '02', '2', '2020-01-16 11:38:43', '2020-01-16 11:38:17', '2020-01-16 11:38:17', NULL);
INSERT INTO `task_history_instance` VALUES ('1217653170934046722', '1217652371587784706', '2020-01-16 11:41:54', '1217652371571007489', '李总审批环节', '02', '3', '2020-01-16 11:41:54', '2020-01-16 11:38:43', '2020-01-16 11:38:43', NULL);
INSERT INTO `task_history_instance` VALUES ('1217653288844320770', '1217653187623182338', '2020-01-16 11:42:22', '1217652264561729538', '抄送给张三环节', '02', '2', '2020-01-16 11:42:22', '2020-01-16 11:41:57', '2020-01-16 11:41:57', NULL);
INSERT INTO `task_history_instance` VALUES ('1217653330812526593', '1217653288991121410', '2020-01-16 11:42:32', '1217652371571007489', '李总审批环节', '02', '3', '2020-01-16 11:42:32', '2020-01-16 11:42:22', '2020-01-16 11:42:22', NULL);
INSERT INTO `task_history_instance` VALUES ('1217692493070901250', '1217692492978626562', '2020-01-16 14:18:09', '1217692492945072130', '请假申请环节', '02', NULL, '2020-01-16 14:18:09', '2020-01-16 14:18:09', '2020-01-16 14:18:09', NULL);
INSERT INTO `task_history_instance` VALUES ('1217693449158299650', '1217692493238673410', '2020-01-16 14:21:57', '1217692493217701890', '动态指定抄送目标环节（演示即席）', '02', '4', '2020-01-16 14:21:57', '2020-01-16 14:18:09', '2020-01-16 14:18:09', NULL);
INSERT INTO `task_history_instance` VALUES ('1217693568540774401', '1217693449569341442', '2020-01-16 14:22:25', '1217693449539981313', '指定成员抄送审阅', '02', '2', '2020-01-16 14:22:25', '2020-01-16 14:21:57', '2020-01-16 14:21:57', NULL);
INSERT INTO `task_history_instance` VALUES ('1217693671519326209', '1217693568855347202', '2020-01-16 14:22:50', '1217693568842764290', '经理级审批环节（任意1个）', '02', '3', '2020-01-16 14:22:50', '2020-01-16 14:22:45', '2020-01-16 14:22:25', NULL);
INSERT INTO `task_history_instance` VALUES ('1217693696488017921', '1217693568905678849', '2020-01-16 14:22:56', '1217693568884707330', 'hx,yy会签审批环节', '02', '3', '2020-01-16 14:22:56', '2020-01-16 14:22:25', '2020-01-16 14:22:25', NULL);
INSERT INTO `task_history_instance` VALUES ('1217693739605463042', '1217693568914067458', '2020-01-16 14:23:06', '1217693568884707330', 'hx,yy会签审批环节', '02', '3', '2020-01-16 14:23:06', '2020-01-16 14:22:25', '2020-01-16 14:22:25', NULL);
INSERT INTO `task_history_instance` VALUES ('1217693780088885249', '1217693740142333954', '2020-01-16 14:23:15', '1217693739961978882', '抄送给张三环节', '02', '2', '2020-01-16 14:23:15', '2020-01-16 14:23:06', '2020-01-16 14:23:06', NULL);
INSERT INTO `task_history_instance` VALUES ('1217693842781147137', '1217693780193742849', '2020-01-16 14:23:30', '1217693780172771329', '李总审批环节', '02', '3', '2020-01-16 14:23:30', '2020-01-16 14:23:16', '2020-01-16 14:23:16', NULL);
INSERT INTO `task_history_instance` VALUES ('1217694275146780674', '1217694275130003458', '2020-01-16 14:25:14', '1217694275117420545', '请假申请环节', '02', NULL, '2020-01-16 14:25:14', '2020-01-16 14:25:14', '2020-01-16 14:25:14', NULL);
INSERT INTO `task_history_instance` VALUES ('1217694296923607041', '1217694275209695233', '2020-01-16 14:25:19', '1217694275201306626', '动态指定抄送目标环节（演示即席）', '02', '4', '2020-01-16 14:25:19', '2020-01-16 14:25:14', '2020-01-16 14:25:14', NULL);
INSERT INTO `task_history_instance` VALUES ('1217694342310170626', '1217694297062019074', '2020-01-16 14:25:30', '1217694297045241857', '指定成员抄送审阅', '02', '2', '2020-01-16 14:25:30', '2020-01-16 14:25:19', '2020-01-16 14:25:19', NULL);
INSERT INTO `task_history_instance` VALUES ('1217694458869878786', '1217694342385668097', '2020-01-16 14:25:57', '1217694342377279490', '经理级审批环节（任意1个）', '02', '3', '2020-01-16 14:25:57', '2020-01-16 14:25:49', '2020-01-16 14:25:30', NULL);
INSERT INTO `task_history_instance` VALUES ('1217694479161921538', '1217694342431805441', '2020-01-16 14:26:02', '1217694342419222530', 'hx,yy会签审批环节', '02', '3', '2020-01-16 14:26:02', '2020-01-16 14:25:30', '2020-01-16 14:25:30', NULL);
INSERT INTO `task_history_instance` VALUES ('1217694543829700610', '1217694342431805442', '2020-01-16 14:26:18', '1217694342419222530', 'hx,yy会签审批环节', '02', '3', '2020-01-16 14:26:18', '2020-01-16 14:25:30', '2020-01-16 14:25:30', NULL);
INSERT INTO `task_history_instance` VALUES ('1217694597898473473', '1217694544001667073', '2020-01-16 14:26:30', '1217694543993278465', '抄送给张三环节', '02', '2', '2020-01-16 14:26:30', '2020-01-16 14:26:18', '2020-01-16 14:26:18', NULL);
INSERT INTO `task_history_instance` VALUES ('1217694794162540545', '1217694597990748161', '2020-01-16 14:27:17', '1217694597973970946', '李总审批环节', '02', '3', '2020-01-16 14:27:17', '2020-01-16 14:26:30', '2020-01-16 14:26:30', NULL);
INSERT INTO `task_history_instance` VALUES ('1217695262867623938', '1217695262846652418', '2020-01-16 14:29:09', '1217695262838263809', '请假申请环节', '02', NULL, '2020-01-16 14:29:09', '2020-01-16 14:29:09', '2020-01-16 14:29:09', NULL);
INSERT INTO `task_history_instance` VALUES ('1217695559232950274', '1217695262951510017', '2020-01-16 14:30:20', '1217695262943121409', '动态指定抄送目标环节（演示即席）', '02', '4', '2020-01-16 14:30:20', '2020-01-16 14:29:09', '2020-01-16 14:29:09', NULL);
INSERT INTO `task_history_instance` VALUES ('1217695603222810625', '1217695559513968642', '2020-01-16 14:30:30', '1217695559505580034', '指定成员抄送审阅', '02', '2', '2020-01-16 14:30:30', '2020-01-16 14:30:20', '2020-01-16 14:30:20', NULL);
INSERT INTO `task_history_instance` VALUES ('1217695652291973122', '1217695603340251138', '2020-01-16 14:30:42', '1217695603331862530', 'hx,yy会签审批环节', '02', '3', '2020-01-16 14:30:42', '2020-01-16 14:30:30', '2020-01-16 14:30:30', NULL);
INSERT INTO `task_history_instance` VALUES ('1217695701457604609', '1217695603302502401', '2020-01-16 14:30:54', '1217695603285725185', '经理级审批环节（任意1个）', '02', '3', '2020-01-16 14:30:54', '2020-01-16 14:30:49', '2020-01-16 14:30:30', NULL);
INSERT INTO `task_history_instance` VALUES ('1217695738656886786', '1217695603344445442', '2020-01-16 14:31:02', '1217695603331862530', 'hx,yy会签审批环节', '02', '3', '2020-01-16 14:31:02', '2020-01-16 14:30:30', '2020-01-16 14:30:30', NULL);
INSERT INTO `task_history_instance` VALUES ('1217695772991459330', '1217695738816270337', '2020-01-16 14:31:11', '1217695738807881730', '抄送给张三环节', '02', '2', '2020-01-16 14:31:11', '2020-01-16 14:31:02', '2020-01-16 14:31:02', NULL);
INSERT INTO `task_history_instance` VALUES ('1217695820122853377', '1217695773226340353', '2020-01-16 14:31:22', '1217695773050179586', '李总审批环节', '02', '3', '2020-01-16 14:31:22', '2020-01-16 14:31:11', '2020-01-16 14:31:11', NULL);
INSERT INTO `task_history_instance` VALUES ('1217698872959602689', '1217698872926048257', '2020-01-16 14:43:30', '1217698872921853953', '请假申请环节', '02', NULL, '2020-01-16 14:43:30', '2020-01-16 14:43:30', '2020-01-16 14:43:30', NULL);
INSERT INTO `task_history_instance` VALUES ('1217699065532682242', '1217698873051877377', '2020-01-16 14:44:16', '1217698873039294465', '动态指定抄送目标环节（演示即席）', '02', '4', '2020-01-16 14:44:16', '2020-01-16 14:43:30', '2020-01-16 14:43:30', NULL);
INSERT INTO `task_history_instance` VALUES ('1217699172273524737', '1217699065658511361', '2020-01-16 14:44:41', '1217699065650122753', '指定成员抄送审阅', '02', '2', '2020-01-16 14:44:41', '2020-01-16 14:44:16', '2020-01-16 14:44:16', NULL);
INSERT INTO `task_history_instance` VALUES ('1217700294979653634', '1217699172365799426', '2020-01-16 14:49:09', '1217699172344827905', '经理级审批环节（任意1个）', '02', '3', '2020-01-16 14:49:09', '2020-01-16 14:48:49', '2020-01-16 14:44:41', NULL);
INSERT INTO `task_history_instance` VALUES ('1217700348612218881', '1217699172416131073', '2020-01-16 14:49:22', '1217699172407742466', 'hx,yy会签审批环节', '02', '3', '2020-01-16 14:49:22', '2020-01-16 14:44:41', '2020-01-16 14:44:41', NULL);
INSERT INTO `task_history_instance` VALUES ('1217700404375490562', '1217699172424519682', '2020-01-16 14:49:35', '1217699172407742466', 'hx,yy会签审批环节', '02', '3', '2020-01-16 14:49:35', '2020-01-16 14:44:41', '2020-01-16 14:44:41', NULL);
INSERT INTO `task_history_instance` VALUES ('1217700938234892290', '1217700404530679809', '2020-01-16 14:51:42', '1217700404522291202', '抄送给张三环节', '02', '2', '2020-01-16 14:51:42', '2020-01-16 14:49:35', '2020-01-16 14:49:35', NULL);
INSERT INTO `task_history_instance` VALUES ('1217701126064214017', '1217700938490744834', '2020-01-16 14:52:27', '1217700938302001154', '李总审批环节', '02', '3', '2020-01-16 14:52:27', '2020-01-16 14:51:42', '2020-01-16 14:51:42', NULL);
INSERT INTO `task_history_instance` VALUES ('1217701186764181505', '1217701126198431746', '2020-01-16 14:52:41', '1217700404522291202', '抄送给张三环节', '02', '2', '2020-01-16 14:52:41', '2020-01-16 14:52:27', '2020-01-16 14:52:27', NULL);
INSERT INTO `task_history_instance` VALUES ('1217701247959076865', '1217701186831290369', '2020-01-16 14:52:56', '1217700938302001154', '李总审批环节', '02', '3', '2020-01-16 14:52:56', '2020-01-16 14:52:41', '2020-01-16 14:52:41', NULL);

-- ----------------------------
-- Table structure for task_instance
-- ----------------------------
DROP TABLE IF EXISTS `task_instance`;
CREATE TABLE `task_instance`  (
  `ti_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `ti_assigner` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务参与者/执行人',
  `ti_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务状态01正在运行02已完成03该次活动已结束04已移到历史库',
  `bf_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务关联表单外键',
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `ai_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属活动实例标识',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '任务开始时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '任务状态更新时间',
  `endtime` timestamp(0) NULL DEFAULT '0000-00-00 00:00:00' COMMENT '任务结束时间',
  `pd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ti_assigner_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `usertask_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of task_instance
-- ----------------------------

-- ----------------------------
-- Table structure for token
-- ----------------------------
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '无意义主键',
  `element_no` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '持有令牌元素编号',
  `pd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程定义主键',
  `pi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程实例主键',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父token主键',
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  `endtime` timestamp(0) NULL DEFAULT NULL,
  `child_num` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of token
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `ui_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `gi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色',
  `ui_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'gwx', '1', '1', '01');
INSERT INTO `user` VALUES ('2', 'yy', '1', '2', '02');
INSERT INTO `user` VALUES ('3', 'hx', '1', '2', '03');
INSERT INTO `user` VALUES ('4', 'z3', '1', '3', '04');
INSERT INTO `user` VALUES ('5', 'lz', '1', '3', '05');

SET FOREIGN_KEY_CHECKS = 1;
