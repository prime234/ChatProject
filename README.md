### ChatProject

***基于TCP+Java+C/S模式实现网络聊天程序。***

- 本系统实现的功能有：

  * 用户名字登录
  * 用户之间的群聊
  * 用户之间的私聊
  * 动态刷新并显示好友列表
  * 显示在线人数
  * 服务器可以显示用户在线状态
  * 能够进行退出处理
  * 用户上下线提醒

- 模块功能：

  * 服务器：
           *  Server类模块：与客户端建立连接，通过套接字获得名字，在控制端显示提示信息
                *  ServerHander模块：提供线程管理，实现多用户的管理。针对每一个用户发过来的消息，进行相应处理，再转发给客户。
         *  Sendallclient模块：给每个用户发送消息。
                Sendclientlist模块：每当有用户上线，更新每个用户的用户列表。

  * 客户端：

    * Client类模块：与服务端建立连接，用户登录，实现消息的群聊，私聊，用户列表的更新等功能。

    * ServiceFrame窗口界面模块：客户端窗口界面，实现用户友好，方便用户使用，在窗口上有消息显示，用户列表，在线人数等视图信息。

* 测试数据及系统运行结果

  ①　在客户端控制台输入名字进入聊天室。如

  <img src="https://cdn.jsdelivr.net/gh/prime234/Picture-PicGo/images/chat1.jpg" alt="img" style="zoom:67%;" /> 

  <img src="https://cdn.jsdelivr.net/gh/prime234/Picture-PicGo/images/chat2.jpg" alt="img" style="zoom:67%;" /> 

  <img src="https://cdn.jsdelivr.net/gh/prime234/Picture-PicGo/images/chat3.jpg" alt="img" style="zoom:67%;" /> 

  <img src="https://cdn.jsdelivr.net/gh/prime234/Picture-PicGo/images/chat4.jpg" alt="img" style="zoom:67%;" /> 

  ②　服务器控制台显示并记录登录，退出信息。

  <img src="https://cdn.jsdelivr.net/gh/prime234/Picture-PicGo/images/chat5.jpg" alt="img" style="zoom:80%;" /> 

  ③　聊天界面显示客户端名字，好友列表，在线人数等信息。

  <img src="https://cdn.jsdelivr.net/gh/prime234/Picture-PicGo/images/chat6.jpg" alt="img" style="zoom:80%;" /> 

  ④　私聊，群聊显示，若选择所有人则为群聊，选择某一个人，则为私聊。如下图，群聊消息，在所有客户端都显示，私聊信息只会在发送者与接受者中显示。

  <img src="https://cdn.jsdelivr.net/gh/prime234/Picture-PicGo/images/chat7.jpg" alt="img" style="zoom:80%;" /> 

  <img src="https://cdn.jsdelivr.net/gh/prime234/Picture-PicGo/images/chat8.jpg" alt="img" style="zoom:80%;" /> 

  ⑤　人数动态刷新。

  <img src="https://cdn.jsdelivr.net/gh/prime234/Picture-PicGo/images/chat9.jpg" alt="img" style="zoom:67%;" /> 

  <img src="https://cdn.jsdelivr.net/gh/prime234/Picture-PicGo/images/chat10.jpg" alt="img" style="zoom:80%;" /> 

  <img src="https://cdn.jsdelivr.net/gh/prime234/Picture-PicGo/images/chat11.jpg" alt="img" style="zoom:67%;" /> 

  <img src="https://cdn.jsdelivr.net/gh/prime234/Picture-PicGo/images/chat12.jpg" alt="img" style="zoom:67%;" />

   

