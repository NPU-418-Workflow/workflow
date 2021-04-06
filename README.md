# workflow project

这是由NPU-418工作流研究小组自主研发的基于BPMN的工作流系统。可以驱动BPMN流程模板运转，实现流程自动化:robot:

*项目还处于alpha版本，功能有限，有待于进一步的开发*

## 工作目录介绍

- app：一个请假流程应用服务
- common：公共类
- extensions：一些依赖项目
- form：用户表单服务
- infrastructure: 项目运行环境
- scheduler：核心调度服务
- template：BPMN流程模板文件

## 运行测试方法

前提：
- JDK 11+
- Maven 3.6+
- Docker

步骤：
1. clone 项目到本地，并打开项目目录
2. 确保 docker 环境启动，输入`docker-compose -f .\infrastructure\docker-compose.yaml up -d`启动运行环境
3. 编译打包项目 `mvn clean package`
4. 分别在 scheduler, form, app 三个目录下运行 `java -jar .\target\quarkus-app\quarkus-run.jar`运行项目

由于目前项目没有前端，推荐使用 [Postman](https://www.postman.com/) 等 API 测试工具进行功能测试

项目待完成的工作可以参考[工作流系统开发板](https://github.com/NPU-418-Workflow/workflow/projects/1)

## 如何参与
- 如果有问题或发现BUG：直接在[issues](https://github.com/NPU-418-Workflow/workflow/issues)中提出一个新的 issue 就行
- 如果想贡献代码：
  - 首先，你需要 fork 一个分支到你自己的 repos 中
  - 从你的分支中 clone 到本地进行开发
  - 开发完成后 push 到你的分支
  - 提交 Pull Request 到 NPU-418-Workflow/workflow

*建议参与开发的同学在 IDE 中安装 Alibaba Java Coding Guidelines 插件并根据插件提示保持良好的代码规范。同时在阅读代码时如果发现有代码书写可以改进的地方也可以修改并提交PR*
