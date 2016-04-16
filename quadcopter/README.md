# quadcopter
Github使用过程

首先要获取上传权限
创建ssh：   

     接下来打开终端(不知道终端在哪儿的，就直接在spotlight里搜terminal)：   

$cd ～/.ssh  //检查是否已经存在ssh
     如果存在，先将已有的ssh备份，或者将新建的ssh生成到另外的目录下

     如果不存在，通过默认的参数直接生成ssh:
复制代码

$ssh-keygen -t rsa -C xxxxx@gmail.com（注册github时的email）

        Generating public/private rsa key pair.
        
        Enter file in which to save the key (/Users/twer/.ssh/id_rsa): 
        
        Created directory '/Users/twer/.ssh'.
        
        Enter passphrase (empty for no passphrase): 
        
        Enter same passphrase again: 
        
        Your identification has been saved in /Users/twer/.ssh/id_rsa.
        
        Your public key has been saved in /Users/twer/.ssh/id_rsa.pub.
        
        The key fingerprint is:
        
        18:16:11:c9:01:6c:48:09:7f:27:c6:43:0d:7f:3f:84 xxxxx@gmail.com
        
        The key's randomart image is:
        
        +—[ RSA 2048]——+
        |.o.++===         |
        |.ooo.+. .       |
        |  ..* = E .      |
        |   o = + o       |
        |      . S o      |
        |           .     |
        |                 |
        |                 |
        |                 |
       +————————+
       
复制代码
 在这里会要求输入key 目录和 密码，可根据自己的情况输入，如果不想再输密码了，就直接回车就可以了。

注：这里生成的key可以在多个网站上使用（例如github和bitbucket），只要本地的和对应网站的key保持一致就可以了如果要修改ssh生成目录，在粗体位置处输入要生成的路径，选择默认的话，会生成在 ～/.ssh下

配置

  git config ————————global user.name "Your Name"
  
  git config ————————global user.email your_email@gmail.com
  
  打开你生成的id_rsa.pub文件，将其中内容拷贝至管理人员，等他告诉你权限已获得。
  
  打开终端，先测试一下你的帐号跟github连上没有：ssh -T git@github.com 如果出现如下提示，表示你连已经连上了.(因为有了第一步，所以不用自己做过多的连接github的操作了，另外，下一次要连接github的时候记得打开第一步的工具).
Hi MiracleHe! You've successfully authenticated, but GitHub does not provide shell access.

1、下载github上的库

git clone https://github.com/minelabwot/quadcopter.git

2、将修改文件拷贝到相应文件夹下

3、在该文件夹下将修改的文件添加到github缓存

git add *(*为所要添加的文件，这个操作要在文件所在文件夹下)

4、添加注释（方便以后对程序做一个了解）

git commit -m ‘…..’ （….为所要做的标示）

5、push一下，同步本地和网上的库

git push git@github.com:minelabwot/quracoper.git


将网上的库和本地同步，pull一下

git pull

查看这次的修改
git status


如果要新建文件夹。

mkdir microduino

touch microduino/README

git add microduino/

git commit -m ‘microduino’

git push git@github.com:minelabwot/quracoper.git
