### 背景
此项目仅用于个人开发测试使用，主要用于图片上传

### 描述
- 上传后会在指定文件夹内按照天进行存储，每天的第一次上传均会生成一个文件夹。
- 为了避免上传文件名重复，每次上传后会重新生成新的文件名【时间戳+三位随机数】
- 此项目未对扩展名进行校验，可以上传任意文件，若需要则可以自行添加校验

### 使用说明
**只需要配置application.yml即可，启动项目即可使用:**
```yaml
server:
  port: 8082

# 后续可以将此项目注册到服务中心，配置信息配到配置中心，地址可以通过网关再路由到本项目
prop:
  upload-folder: /home/mystatic/image/ # 文件存储路径,存储文件在主机真实的路径；以上配置为虚拟机的路径
  img-folder: /myImage/getImage/ # 访问地址 http://localhost:8082/myImage/getImage/***.jpg

#设置最大上传大小为10Mb
spring: 
  http:
    multipart:
      max-file-size: 10Mb
```
- 配置端口(目前配置文件为8082)
- 配置文件实际存储位置(主机图片存储的真实路径)。例：D:/photos/
- 配置图片访问路径。例：/myImage/getImage/ 每次访问http://IP:端口/myImage/getImage/时，则会自动指向实际存储位置
- 配置上传文件的大小
**访问说明**
- 上传图片接口：http://IP:端口/upload/singlefile
- 图片查看路径(上传后有返回data数据，也有log日志输出) http://IP:端口/myImage/getImage/XXX.png
- 上传接口返回json参数
```json
{
"code": 0,
"msg": "success",
"data": "图片访问地址，例：http://IP:端口/myImage/getImage/XXX.png"
}
```

### 备注
可以将此项目注册到服务中心，配置信息配到配置中心，地址可以通过网关做过滤-->路径重写-->再路由到本项目
- 避免地址暴露
- 在网关可以做校验、限流等
- 方便扩展，可做负载均衡