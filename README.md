消息同步

### 消息类型:

1. 系统消息:暂未框架内部主从之间调度使用,不支持用户自行解析使用
2. 客户消息:纯文本消息
3. 回调消息:除了能携带文本以外,还可以指定在消息返回后回调的方法


### 解析消息方式
1. 实现com.elin4it.ezmessage.MessageResolve.MessageResolve.resolve(Message)方法
该方法的入参为所接受到的从节点消息,可通过该消息的具体类型来获取其中的内容
发送该消息的从节点的详细信息可从com.elin4it.ezmessage.SalveContextManage中获取

2. 将其注入到com.elin4it.ezmessage.SimperReceiver对象中

