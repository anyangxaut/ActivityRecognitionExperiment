# --------------------------------------------------------
# Host:                         127.0.0.1
# Server version:               5.1.44-community
# Server OS:                    Win32
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2015-06-19 16:17:04
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

# Dumping database structure for activityrecognitionnofusion
CREATE DATABASE IF NOT EXISTS `activityrecognitionnofusion` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `activityrecognitionnofusion`;


# Dumping structure for table activityrecognitionnofusion.featureextraction
CREATE TABLE IF NOT EXISTS `featureextraction` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `RKN_accX_mean` text NOT NULL COMMENT '单位：mg',
  `RKN_accY_mean` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ_mean` text NOT NULL COMMENT '单位：mg',
  `HIP_accX_mean` text NOT NULL COMMENT '单位：mg',
  `HIP_accY_mean` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ_mean` text NOT NULL COMMENT '单位：mg',
  `LUA_accX_mean` text NOT NULL COMMENT '单位：mg',
  `LUA_accY_mean` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ_mean` text NOT NULL COMMENT '单位：mg',
  `RKN_accX_variance` text NOT NULL COMMENT '单位：mg',
  `RKN_accY_variance` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ_variance` text NOT NULL COMMENT '单位：mg',
  `HIP_accX_variance` text NOT NULL COMMENT '单位：mg',
  `HIP_accY_variance` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ_variance` text NOT NULL COMMENT '单位：mg',
  `LUA_accX_variance` text NOT NULL COMMENT '单位：mg',
  `LUA_accY_variance` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ_variance` text NOT NULL COMMENT '单位：mg',
  `RKN_accX_RKN_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accX_RKN_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accX_HIP_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accX_HIP_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accX_HIP_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accX_LUA_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accX_LUA_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accX_LUA_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accY_RKN_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accY_RKN_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accY_HIP_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accY_HIP_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accY_HIP_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accY_LUA_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accY_LUA_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accY_LUA_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ_RKN_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ_RKN_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ_HIP_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ_HIP_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ_HIP_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ_LUA_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ_LUA_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ_LUA_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accX_RKN_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accX_RKN_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accX_RKN_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accX_HIP_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accX_HIP_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accX_LUA_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accX_LUA_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accX_LUA_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accY_RKN_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accY_RKN_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accY_RKN_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accY_HIP_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accY_HIP_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accY_LUA_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accY_LUA_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accY_LUA_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ_RKN_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ_RKN_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ_RKN_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ_HIP_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ_HIP_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ_LUA_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ_LUA_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ_LUA_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accX_RKN_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accX_RKN_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accX_RKN_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accX_HIP_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accX_HIP_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accX_HIP_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accX_LUA_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accX_LUA_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accY_RKN_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accY_RKN_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accY_RKN_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accY_HIP_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accY_HIP_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accY_HIP_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accY_LUA_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accY_LUA_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ_RKN_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ_RKN_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ_RKN_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ_HIP_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ_HIP_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ_HIP_accZ_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ_LUA_accX_correlation` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ_LUA_accY_correlation` text NOT NULL COMMENT '单位：mg',
  `RKN_accX_energy` text NOT NULL COMMENT '单位：mg',
  `RKN_accY_energy` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ_energy` text NOT NULL COMMENT '单位：mg',
  `HIP_accX_energy` text NOT NULL COMMENT '单位：mg',
  `HIP_accY_energy` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ_energy` text NOT NULL COMMENT '单位：mg',
  `LUA_accX_energy` text NOT NULL COMMENT '单位：mg',
  `LUA_accY_energy` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ_energy` text NOT NULL COMMENT '单位：mg',
  `locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；3：Sit；4：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='特征提取后的数据信息（均值，方差，相关系数，能量）';

# Data exporting was unselected.


# Dumping structure for table activityrecognitionnofusion.lie
CREATE TABLE IF NOT EXISTS `lie` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Locomotion=5(Lie)';

# Data exporting was unselected.


# Dumping structure for table activityrecognitionnofusion.preprocessingdata
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


# Dumping structure for table activityrecognitionnofusion.sit
CREATE TABLE IF NOT EXISTS `sit` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Locomotion=4(Sit)';

# Data exporting was unselected.


# Dumping structure for table activityrecognitionnofusion.stand
CREATE TABLE IF NOT EXISTS `stand` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Locomotion=1(Stand)';

# Data exporting was unselected.


# Dumping structure for table activityrecognitionnofusion.walk
CREATE TABLE IF NOT EXISTS `walk` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Locomotion=2(Walk)';

# Data exporting was unselected.
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
