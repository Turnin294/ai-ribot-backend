AI-Ribot (小哈 AI 机器人后端)
基于 Spring Boot 3.4.1 和 Spring AI 构建的全栈 AI 助手后端。支持流式对话、上下文记忆、联网搜索以及基于向量数据库（PGVector）的智能客服 RAG 系统。

🚀 核心功能
流式对话 (SSE)：支持与大模型进行流式交互，实时返回推理内容和回答。

长效记忆管理：自定义 CustomChatMemoryAdvisor，从数据库（PostgreSQL）动态拉取历史消息，实现有状态的对话。

联网搜索能力：集成 SearXNG 聚合搜索引擎，支持实时爬取网页内容并进行增强生成的联网对话。

RAG 智能客服：

支持 Markdown 文件上传、解析与向量化存储。

利用 PGVector 实现相似度检索，严格基于知识库回答用户问题。

异步处理机制：文件向量化采用异步事件监听模式，配备自定义线程池提升并发处理能力。

🛠️ 技术栈
核心框架：Spring Boot 3.4.1, Spring AI 1.1.1

Java 版本：JDK 21

数据库：PostgreSQL (带 PGVector 插件)

ORM 框架：MyBatis Plus

网络请求：OkHttp 4.12.0

日志/工具：Log4j2, Hutool, Lombok, Jsoup

📋 快速开始
1. 环境准备
PostgreSQL: 需安装 pgvector 扩展。

SearXNG: 建议使用 Docker 部署在 localhost:8888。

2. 数据库配置
执行以下 SQL 初始化向量表及基础表结构：

SQL
CREATE EXTENSION IF NOT EXISTS vector;
-- 向量存储表由 Spring AI 自动管理，名称配置为 t_vector_store
3. 修改配置
编辑 src/main/resources/application-dev.yml，填入你的 API Key 和路径：

YAML
spring:
  ai:
    openai:
      base-url: https://dashscope.aliyuncs.com/compatible-mode
      api-key: your-api-key-here
  datasource:
    url: jdbc:p6spy:postgresql://localhost:5432/robot
    username: postgres
    password: your-password

customer-service:
  md-storage-path: D:\\airobot_md  # 修改为你本地的存储路径
4. 启动项目
Bash
mvn clean install
mvn spring-boot:run
接口说明
对话模块
流式对话：POST /chat/completion

chatId: 对话唯一标识

networkSearch: 是否开启联网搜索 (Boolean)

新建对话：POST /chat/new

智能客服模块
知识上传：POST /customer-service/md/upload (仅支持 .md 文件)

客服对话：POST /customer-service/completion

🧩 核心逻辑说明
Advisor 链式处理：通过 CustomerServiceAdvisor 或 NetworkSearchAdvisor 动态拦截请求，完成 RAG 检索或搜索结果聚合，最终重组 Prompt 发送给大模型。

安全性与跨域：已配置全局 CORS，允许前端跨域请求并携带凭证。
