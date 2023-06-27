# easyblog

EasyBlog后端

## 快速开始

### 运行 EasyBlog

您可以使用以下easyblog部署指南查看更多信息，并部署一个稳定的开箱即用的easyblog服务器。

#### 1. 本地克隆项目代码
```shell
git clone https://github.com/easyblog-org/easyblog.git
```

#### 2. 执行启动脚本startup.sh
项目提供了快捷的启动脚本，使用启动脚本可以快速将服务部署到你的服务器上，你可以使用启动参数`-d true`来选择将项目运行在Docker容器中，反正则运行在真实物理机上，同时你也可以使用 `-p 参数指定启动端口，默认启动端口是 8001`
```shell
cd easyblog
./startup.sh -d true -p 8001
```

