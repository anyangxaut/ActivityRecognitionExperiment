# ActivityRecognitionFlow

对原始数据集进行预处理，窗口划分，特征提取，特征融合，分类识别

数据集：Opportunity Data Set（http://archive.ics.uci.edu/ml/datasets/OPPORTUNITY+Activity+Recognition）

预处理：删除含有缺失数据（"NaN"）或是Locomotion标签为0的数据item

窗口划分：窗口大小64，重叠率50%

特征提取：均值，方差，相关系数，能量

特征融合：LDA（线性判别分析）

分类识别：KNN，LDA

