# EZMessage(开发中...)

作者(Author):夕下奕林(ElinZhou)
版本(Version):1.0

##简介

当一个集群中有多个节点需要统一分配数据或资源时,可引入该框架,在集群中添加一个主节点,由主节点统一调度各类需要分配的数据及资源


###功能:

1. 从节点向主节点发送各种类型的消息(具体消息类型见后文),主节点接受后,调用消息解析对象(存在的情况下)
2. 主节点根据消息数据的实时状态,主动将数据推送至从节点
3. 拥有心跳机制,从节点定时向主节点发送心跳包,主节点如果在一定间隔(暂定为5s)内为收到对应从节点的心跳,则断开连接.如为非机械故障,从节点将自行尝试重新连接主节点


###消息类型:

1. 系统消息:暂未框架内部主从之间调度使用,不支持用户自行解析使用
2. 客户消息:纯文本消息
3. 回调消息:除了能携带文本以外,还可以指定在消息返回后回调的方法


###解析消息方式
1. 实现com.elin4it.ezmessage.MessageResolve.MessageResolve.resolve(Message)方法
该方法的入参为所接受到的从节点消息,可通过该消息的具体类型来获取其中的内容
发送该消息的从节点的详细信息可从com.elin4it.ezmessage.SalveContextManage中获取

2. 将其注入到com.elin4it.ezmessage.SimperReceiver对象中

