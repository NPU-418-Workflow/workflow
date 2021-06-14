# workflow project

![GitHub Workflow Status](https://img.shields.io/github/workflow/status/NPU-418-Workflow/workflow/Java%20CI%20with%20Maven)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/0c3ed5c9cdd94d6abd98c4434ad0a586)](https://www.codacy.com/gh/NPU-418-Workflow/workflow/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=NPU-418-Workflow/workflow&amp;utm_campaign=Badge_Grade)
![GitHub](https://img.shields.io/github/license/NPU-418-Workflow/workflow)

这是由NPU-418工作流研究小组自主研发的基于BPMN的工作流系统。可以驱动BPMN流程模板运转，实现流程自动化:robot:

Native Image 测试版本

主分支版本无法打包成 native image, 猜测是由于序列化包所导致。

目前将所有组件融合进一个应用中可以成功构建 native image。

```bash
mvn clean package -Pnative
# 构建完成后直接运行可执行文件，e.g. windows下直接运行exe文件
# 注意先启动 docker compose 环境组件
.\target\scheduler-1.0.0-SNAPSHOT-runner.exe
```