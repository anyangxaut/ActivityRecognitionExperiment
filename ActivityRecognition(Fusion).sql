# --------------------------------------------------------
# Host:                         127.0.0.1
# Server version:               5.1.44-community
# Server OS:                    Win32
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2015-06-19 16:16:10
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

# Dumping database structure for activityrecognitionfusion
CREATE DATABASE IF NOT EXISTS `activityrecognitionfusion` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `activityrecognitionfusion`;


# Dumping structure for table activityrecognitionfusion.datafusion
CREATE TABLE IF NOT EXISTS `datafusion` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `Locomotion` int(10) NOT NULL COMMENT '动作类型',
  `FeatureId` int(10) NOT NULL COMMENT '特征序列号',
  `Sensor1` text NOT NULL COMMENT '传感器1的权值',
  `Sensor2` text NOT NULL COMMENT '传感器2的权值',
  `Sensor3` text NOT NULL COMMENT '传感器3的权值',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='使用lda线性判别分析方法计算数据融合中各个传感器的权值';

# Data exporting was unselected.


# Dumping structure for table activityrecognitionfusion.dsfusion
CREATE TABLE IF NOT EXISTS `dsfusion` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `AccX_mean` text NOT NULL COMMENT '单位：mg',
  `AccY_mean` text NOT NULL COMMENT '单位：mg',
  `AccZ_mean` text NOT NULL COMMENT '单位：mg',
  `AccX_variance` text NOT NULL COMMENT '单位：mg',
  `AccY_variance` text NOT NULL COMMENT '单位：mg',
  `AccZ_variance` text NOT NULL COMMENT '单位：mg',
  `AccX_AccY_correlation` text NOT NULL COMMENT '单位：mg',
  `AccY_AccZ_correlation` text NOT NULL COMMENT '单位：mg',
  `AccX_AccZ_correlation` text NOT NULL COMMENT '单位：mg',
  `AccX_energy` text NOT NULL COMMENT '单位：mg',
  `AccY_energy` text NOT NULL COMMENT '单位：mg',
  `AccZ_energy` text NOT NULL COMMENT '单位：mg',
  `locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；3：Sit；4：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='DS数据融合后的数据信息（均值，方差，相关系数，能量）';

# Data exporting was unselected.


# Dumping structure for table activityrecognitionfusion.featureextraction
CREATE TABLE IF NOT EXISTS `featureextraction` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `SensorId` int(10) NOT NULL COMMENT '传感器ID',
  `AccX_mean` text NOT NULL COMMENT '单位：mg',
  `AccY_mean` text NOT NULL COMMENT '单位：mg',
  `AccZ_mean` text NOT NULL COMMENT '单位：mg',
  `AccX_variance` text NOT NULL COMMENT '单位：mg',
  `AccY_variance` text NOT NULL COMMENT '单位：mg',
  `AccZ_variance` text NOT NULL COMMENT '单位：mg',
  `AccX_AccY_correlation` text NOT NULL COMMENT '单位：mg',
  `AccY_AccZ_correlation` text NOT NULL COMMENT '单位：mg',
  `AccX_AccZ_correlation` text NOT NULL COMMENT '单位：mg',
  `AccX_energy` text NOT NULL COMMENT '单位：mg',
  `AccY_energy` text NOT NULL COMMENT '单位：mg',
  `AccZ_energy` text NOT NULL COMMENT '单位：mg',
  `locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；3：Sit；4：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='特征提取后的数据信息（均值，方差，相关系数，能量）';

# Data exporting was unselected.


# Dumping structure for table activityrecognitionfusion.fusionresult
CREATE TABLE IF NOT EXISTS `fusionresult` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `WeightType` int(10) NOT NULL COMMENT '权值类型',
  `AccX_mean` text NOT NULL COMMENT '单位：mg',
  `AccY_mean` text NOT NULL COMMENT '单位：mg',
  `AccZ_mean` text NOT NULL COMMENT '单位：mg',
  `AccX_variance` text NOT NULL COMMENT '单位：mg',
  `AccY_variance` text NOT NULL COMMENT '单位：mg',
  `AccZ_variance` text NOT NULL COMMENT '单位：mg',
  `AccX_AccY_correlation` text NOT NULL COMMENT '单位：mg',
  `AccY_AccZ_correlation` text NOT NULL COMMENT '单位：mg',
  `AccX_AccZ_correlation` text NOT NULL COMMENT '单位：mg',
  `AccX_energy` text NOT NULL COMMENT '单位：mg',
  `AccY_energy` text NOT NULL COMMENT '单位：mg',
  `AccZ_energy` text NOT NULL COMMENT '单位：mg',
  `locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；3：Sit；4：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='特征融合后的数据信息（均值，方差，相关系数，能量）';

# Data exporting was unselected.


# Dumping structure for table activityrecognitionfusion.lie
CREATE TABLE IF NOT EXISTS `lie` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `SensorId` int(10) NOT NULL COMMENT '传感器ID',
  `AccX` text NOT NULL COMMENT '单位：mg',
  `AccY` text NOT NULL COMMENT '单位：mg',
  `AccZ` text NOT NULL COMMENT '单位：mg',
  `Locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；4：Sit；5：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Locomotion=5(lie)';

# Data exporting was unselected.


# Dumping structure for table activityrecognitionfusion.preprocessingdata
CREATE TABLE IF NOT EXISTS `preprocessingdata` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `Time` text NOT NULL COMMENT '单位：ms',
  `RKN_accX` text NOT NULL COMMENT '单位：mg',
  `RKN_accY` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ` text NOT NULL COMMENT '单位：mg',
  `HIP_accX` text NOT NULL COMMENT '单位：mg',
  `HIP_accY` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ` text NOT NULL COMMENT '单位：mg',
  `LUA_accX` text NOT NULL COMMENT '单位：mg',
  `LUA_accY` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ` text NOT NULL COMMENT '单位：mg',
  `Locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；4：Sit；5：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='经过预处理后的数据（预处理：删除含有缺失数据或是Locomotion标签为0的数据item）';

# Data exporting was unselected.


# Dumping structure for table activityrecognitionfusion.sit
CREATE TABLE IF NOT EXISTS `sit` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `SensorId` int(10) NOT NULL COMMENT '传感器ID',
  `AccX` text NOT NULL COMMENT '单位：mg',
  `AccY` text NOT NULL COMMENT '单位：mg',
  `AccZ` text NOT NULL COMMENT '单位：mg',
  `Locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；4：Sit；5：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Locomotion=4(Sit)';

# Data exporting was unselected.


# Dumping structure for table activityrecognitionfusion.stand
CREATE TABLE IF NOT EXISTS `stand` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `SensorId` int(10) NOT NULL COMMENT '传感器ID',
  `AccX` text NOT NULL COMMENT '单位：mg',
  `AccY` text NOT NULL COMMENT '单位：mg',
  `AccZ` text NOT NULL COMMENT '单位：mg',
  `Locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；4：Sit；5：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Locomotion=1(Stand)';

# Data exporting was unselected.


# Dumping structure for table activityrecognitionfusion.walk
CREATE TABLE IF NOT EXISTS `walk` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `SensorId` int(10) NOT NULL COMMENT '传感器ID',
  `AccX` text NOT NULL COMMENT '单位：mg',
  `AccY` text NOT NULL COMMENT '单位：mg',
  `AccZ` text NOT NULL COMMENT '单位：mg',
  `Locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；4：Sit；5：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Locomotion=2(Walk)';

# Data exporting was unselected.
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
