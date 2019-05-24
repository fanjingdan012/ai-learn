遗传算法lab
#	背包问题
##	1 基因表示和初始化
###	基因表示
用bitmap的形式表示每一个物品是否要放入背包，1表示放入，0表示不放入。如基因“10011”表示5样物品中，选择第1,4,5样物品放入背包。
###	初始化
随机产生个体，数量为指定种群个数，为方便起见，种群个数一般设定为偶数个，保证个体的基因长度为物品个数。产生后的个体加入到population中
##	2 选择操作
###	计算所有个体的适应度
即void fitnessAll()函数。
首先计算出每一个个体的适应度，调用每一个IndividualKnapSack的fitnessFunction()函数，返回每个个体的适应度，然后计算出这一代适应度的总和，最后用每一个随机产生个体的适应度除以总的适应度，获得每一个个体被选择到的概率。赋值给每一个个体的probability
Fitness就是这个个体基因表示的带的物体的价值数，如果超重了，则fitness为0
###	用概率拼盘进行选择
即void select()函数。
产生种群个体数量个随机数，用上一步fitnessAll()产生的probability来计算叠加出每一个个体的概率范围，用lowBound和highBound表示，如果概率值落在范围内，则这个个体被选中，跳出循环，产生下一个随机数。
选出的个体将被加入到newPopulation中。
##	3 杂交操作
即void crossover()函数。
在newPopulation中每两个个体组成一对，产生两个后代，对两个个体的基因比较，将适应度较高的个体为0，且另一个个体为1的位串作为杂交的目标基因段，产生随机数，决定是否杂交，如果决定杂交，且杂交后没有超重就实施杂交操作，形成两个新的个体。
##	4 变异操作
即void mutation()函数。
在杂交完成的newPopulation中每个个体的每一位基因进行变异操作，生成一个随机数，如果随机数小于设定的变异概率（pm），就将这一位变异，从1变成0或者0变成1，产生新的基因。
##	5 运行
即void run ()函数。
这个函数将所有的遗传算法的步骤串起来。从第一代开始产生初始的种群。然后进行一代一代的循环，对每一代进行
this.fitnessAll();
this.select();
this.crossover();
this.mutation();
操作，并在每一代结束以后统计并输出每一代的平均适应度、最大适应度等信息，如果代数没有到，但是已经到达比较好的fitness标准，也会终止循环，最后输出fitness最好的个体的基因和解释（使用它的translate()函数）
## 6 实验结果（3次）
![test1-1](https://github.com/fanjingdan012/ai-learn/blob/master/genetic-algorithm/pic/test1-1.png)
![test1-2](https://github.com/fanjingdan012/ai-learn/blob/master/genetic-algorithm/pic/test1-2.png)
![test1-3](https://github.com/fanjingdan012/ai-learn/blob/master/genetic-algorithm/pic/test1-3.png)


#	旅行商问题
##	1 基因表示和初始化
###	基因表示
将所有城市按序编号，用int数组表示行走的城市序列。
###	初始化
产生不同的随机数，直到覆盖所有城市编号，按照顺序排列作为初始种群基因。0作为默认起点和终点。
##	2 选择操作
###	计算所有个体的适应度
即void fitnessAll()函数。
首先计算出每一个个体的适应度，调用每一个IndividualKnapSack的fitnessFunction()函数，返回每个个体的适应度，然后计算出这一代适应度的总和，最后用每一个随机产生个体的适应度除以总的适应度，获得每一个个体被选择到的概率。赋值给每一个个体的probability
Fitness是10000-总的路线长度

###	用概率拼盘进行选择
即void select()函数。
产生种群个体数量个随机数，用上一步fitnessAll()产生的probability来计算叠加出每一个个体的概率范围，用lowBound和highBound表示，如果概率值落在范围内，则这个个体被选中，跳出循环，产生下一个随机数。
选出的个体将被加入到newPopulation中。
##	3 杂交操作
即void crossover()函数。
在newPopulation中每两个个体组成一对，产生两个后代，然后对两个基因比对，如果产生一段首尾相同，而且中间走过的城市集也相同的基因作为目标基因段，findCrossover函数即是寻找目标基因段的函数，然后产生一个随机数，如果随机数小于设定的杂交概率（pc），就把目标基因段互换。形成两个新的个体。
##	4 变异操作
即void mutation()函数。
在杂交完成的newPopulation中每个个体的每一位基因进行变异操作，生成一个随机数，如果随机数小于设定的变异概率（pm）而且这种变异不会导致路不通（弧的两个端点出入度小于2），就将这一位变异，从1变成0或者0变成1，产生新的基因。
##	5 运行
即void run ()函数。
这个函数将所有的遗传算法的步骤串起来。从第一代开始产生初始的种群。然后进行一代一代的循环，对每一代进行
this.fitnessAll();
this.select();
this.crossover();
this.mutation();
操作，并在每一代结束以后统计并输出每一代的平均适应度、最大适应度等信息，如果代数没有到，但是已经到达比较好的fitness标准，也会终止循环，最后输出fitness最好的个体的基因和解释（使用它的translate()函数）
## 6 实验结果（3次）
![test2-1](https://github.com/fanjingdan012/ai-learn/blob/master/genetic-algorithm/pic/test2-1.png)
![test2-2](https://github.com/fanjingdan012/ai-learn/blob/master/genetic-algorithm/pic/test2-2.png)
![test2-3](https://github.com/fanjingdan012/ai-learn/blob/master/genetic-algorithm/pic/test2-3.png)
