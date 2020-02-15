term是代表完全匹配，也就是精确查询，搜索前不会再对搜索词进行分词，所以我们的搜索词必须是文档分词集合中的一个

TermsBuilder:构造聚合函数

AggregationBuilders:创建聚合函数工具类

BoolQueryBuilder:拼装连接(查询)条件

QueryBuilders:简单的静态工厂”导入静态”使用。主要作用是查询条件(关系),如区间\精确\多值等条件

NativeSearchQueryBuilder:将连接条件和聚合函数等组合

SearchQuery:生成查询

elasticsearchTemplate.query:进行查询

Aggregations:Represents a set of computed addAggregation.代表一组添加聚合函数统计后的数据

Bucket:满足某个条件(聚合)的文档集合
————————————————
版权声明：本文为CSDN博主「MJ丶」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/mj86534210/article/details/79910909